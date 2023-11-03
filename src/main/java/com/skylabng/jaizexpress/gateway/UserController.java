package com.skylabng.jaizexpress.gateway;

import com.skylabng.jaizexpress.enduser.EndUserExternalAPI;
import com.skylabng.jaizexpress.enduser.EndUserPayload;
import com.skylabng.jaizexpress.payload.PagedPayload;
import com.skylabng.jaizexpress.payload.ReloadWalletPayload;
import com.skylabng.jaizexpress.payload.UserDetailsPayload;
import com.skylabng.jaizexpress.payment.PaymentPayload;
import com.skylabng.jaizexpress.utils.PagingUtil;
import com.skylabng.jaizexpress.wallet.WalletInternalAPI;
import com.skylabng.jaizexpress.wallet.WalletPayload;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@Tag(name = "Users", description = "API for managing system users")
@RequestMapping("/api/users")
//@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials = "true")
public class UserController {
    private final EndUserExternalAPI api;

    public UserController(EndUserExternalAPI api) {
        this.api = api;
    }

    @Operation(
            summary = "List All Users",
            description = "Returns a paged list of all the users in the system. " +
                    "You can call this endpoint with page parameter to move between pages.")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagedPayload<EndUserPayload>> find(
            Principal principal,
            @RequestParam(name = "page", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(name = "search", required = false, defaultValue = StringUtils.EMPTY) String searchCriteria,
            @RequestParam(name = "searchtext", required = false, defaultValue = "") String searchText,
            @RequestParam(name = "pagesize", required = false, defaultValue = "1000") int pageSize,
            @RequestParam(name = "sortby", required = false, defaultValue = "date") String sortBy,
            @RequestParam(name = "sortdirection", required = false, defaultValue = "desc") String sortDirection,
            @RequestParam(name = "createdBy", required = false, defaultValue = "") String createdBy) throws Exception {

        PageRequest pageRequest = PagingUtil.getPageRequestObject(pageNumber, pageSize, sortBy, sortDirection);
        PagedPayload<EndUserPayload> response = api.getUserList(pageRequest);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Reload wallet with new balance",
            description = "Call this API to book/buy ticket for a new trip. When both origin and destination are present and purchase mode is AGENT," +
                    "then the full trip will be created. If only origin is available, it means that the trip is initiated with a Card amd will be " +
                    "completed at the destination.")
    @GetMapping( value = "/details", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<UserDetailsPayload> getUserDetails(Principal principal) throws Exception {
        EndUserPayload user = api.getUserByUsername(principal.getName());

        UserDetailsPayload response = api.getUserDetails( user.id() );
        return ResponseEntity.ok(response);
    }
}
