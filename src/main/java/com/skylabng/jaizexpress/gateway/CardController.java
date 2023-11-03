package com.skylabng.jaizexpress.gateway;

import com.skylabng.jaizexpress.card.CardExternalAPI;
import com.skylabng.jaizexpress.card.CardPayload;
import com.skylabng.jaizexpress.enduser.EndUserInternalAPI;
import com.skylabng.jaizexpress.enduser.EndUserPayload;
import com.skylabng.jaizexpress.enums.CardStatus;
import com.skylabng.jaizexpress.payload.CardDetailsPayload;
import com.skylabng.jaizexpress.payload.CardTransactionPayload;
import com.skylabng.jaizexpress.payload.LinkCardPayload;
import com.skylabng.jaizexpress.payload.PagedPayload;
import com.skylabng.jaizexpress.utils.PagingUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "Cards API", description = "API for managing RFID/NFC cards")
@RequestMapping("/api/cards")
public class CardController {
    private final CardExternalAPI api;
    private final EndUserInternalAPI userAPI;

    public CardController(CardExternalAPI api, EndUserInternalAPI userAPI) {
        this.api = api;
        this.userAPI = userAPI;
    }

    @Operation(
            summary = "List All Issued Cards",
            description = "Returns a paged list of all the cards issued in the system. " +
                    "You can call this endpoint with page parameter to move between pages.")
    @GetMapping(value="/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagedPayload<CardPayload>> find(
            Principal principal,
            @RequestParam(name = "page", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(name = "search", required = false, defaultValue = StringUtils.EMPTY) String searchCriteria,
            @RequestParam(name = "searchtext", required = false, defaultValue = "") String searchText,
            @RequestParam(name = "pagesize", required = false, defaultValue = "1000") int pageSize,
            @RequestParam(name = "sortby", required = false, defaultValue = "date") String sortBy,
            @RequestParam(name = "sortdirection", required = false, defaultValue = "desc") String sortDirection,
            @RequestParam(name = "createdBy", required = false, defaultValue = "") String createdBy) throws Exception {

        PageRequest pageRequest = PagingUtil.getPageRequestObject(pageNumber, pageSize, sortBy, sortDirection);
        PagedPayload<CardPayload> response = api.getCardList(pageRequest);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Issue New Card",
            description = "Create a new card")
    @PostMapping
    public ResponseEntity<CardPayload> add(@RequestBody CardPayload payload) {
        CardPayload entity = api.save( payload.withStatus( CardStatus.ACTIVE ) );

        return ResponseEntity.ok().body(entity);
    }

    @Operation(
            summary = "List All User Cards",
            description = "Returns a list of all the cards liked to the authrnticated user in the system. ")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CardPayload>> find( Principal principal ) throws Exception {
        EndUserPayload user = userAPI.getUserByUsername( principal.getName() );
        List<CardPayload> response = api.getUserCardList( user.id() );

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Link Card To User",
            description = "Link existing card with the given card number to authenticated user. ")
    @PutMapping(value="/link", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CardPayload> linkCardToUser( Principal principal,
                                                             @RequestBody LinkCardPayload payload ) throws Exception {
        EndUserPayload user = userAPI.getUserByUsername( principal.getName() );

        CardPayload card = api.getCardByNumber( payload.cardNumber() )
                .withUserId( user.id() ).withStatus( CardStatus.LINKED );

        CardPayload entity = api.save( card );

        return ResponseEntity.ok().body( entity );
    }

    @Operation(
            summary = "Get card details",
            description = "Returns details of a given card ID. ")
    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CardDetailsPayload> getCardDetails(Principal principal,
                                                             @PathVariable(name = "id") String id ) throws Exception {

        CardDetailsPayload response = api.getCardDetails(UUID.fromString( id ) );

        return ResponseEntity.ok(response);
    }
}
