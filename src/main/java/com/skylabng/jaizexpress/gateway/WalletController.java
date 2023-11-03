package com.skylabng.jaizexpress.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.skylabng.jaizexpress.enduser.EndUserInternalAPI;
import com.skylabng.jaizexpress.enduser.EndUserPayload;
import com.skylabng.jaizexpress.enums.PaymentStatus;
import com.skylabng.jaizexpress.enums.TransactionStatus;
import com.skylabng.jaizexpress.enums.TransactionTarget;
import com.skylabng.jaizexpress.enums.TransactionType;
import com.skylabng.jaizexpress.klikpay.callbacklog.CallbackLogInternalAPI;
import com.skylabng.jaizexpress.klikpay.callbacklog.CallbackLogPayload;
import com.skylabng.jaizexpress.klikpay.models.CallbackPayload;
import com.skylabng.jaizexpress.payload.PagedPayload;
import com.skylabng.jaizexpress.payload.ReloadWalletPayload;
import com.skylabng.jaizexpress.payment.PaymentInternalAPI;
import com.skylabng.jaizexpress.payment.PaymentPayload;
import com.skylabng.jaizexpress.transaction.TransactionInternalAPI;
import com.skylabng.jaizexpress.transaction.TransactionPayload;
import com.skylabng.jaizexpress.utils.PagingUtil;
import com.skylabng.jaizexpress.wallet.WalletExternalAPI;
import com.skylabng.jaizexpress.wallet.WalletInternalAPI;
import com.skylabng.jaizexpress.wallet.WalletPayload;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.UUID;

@RestController
@Tag(name = "Wallet API", description = "API for wallet operations")
@RequestMapping("/api/wallets")
public class WalletController {
    private final WalletExternalAPI api;
    private final WalletInternalAPI internalAPI;
    private final EndUserInternalAPI userAPI;
    private final TransactionInternalAPI transactionAPI;
    private final PaymentInternalAPI paymentAPI;
    private final CallbackLogInternalAPI callbackAPI;

    public WalletController(WalletExternalAPI api, WalletInternalAPI internalAPI, EndUserInternalAPI userAPI, TransactionInternalAPI transactionAPI, PaymentInternalAPI paymentAPI, CallbackLogInternalAPI callbackAPI) {
        this.api = api;
        this.internalAPI = internalAPI;
        this.userAPI = userAPI;
        this.transactionAPI = transactionAPI;
        this.paymentAPI = paymentAPI;
        this.callbackAPI = callbackAPI;
    }

    @Operation(
            summary = "List All Wallets",
            description = "Returns a paged list of all the user Wallets. You can call this endpoint with page parameter to move between pages.")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagedPayload<WalletPayload>> find(
            Principal principal,
            @RequestParam(name = "page", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(name = "search", required = false, defaultValue = StringUtils.EMPTY) String searchCriteria,
            @RequestParam(name = "searchtext", required = false, defaultValue = "") String searchText,
            @RequestParam(name = "pagesize", required = false, defaultValue = "1000") int pageSize,
            @RequestParam(name = "sortby", required = false, defaultValue = "date") String sortBy,
            @RequestParam(name = "sortdirection", required = false, defaultValue = "desc") String sortDirection,
            @RequestParam(name = "createdBy", required = false, defaultValue = "") String createdBy) throws Exception {

        PageRequest pageRequest = PagingUtil.getPageRequestObject(pageNumber, pageSize, sortBy, sortDirection);
        PagedPayload<WalletPayload> response = api.getPagedList( pageRequest );

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Reload wallet with new balance",
            description = "Call this API to book/buy ticket for a new trip. When both origin and destination are present and purchase mode is AGENT," +
                    "then the full trip will be created. If only origin is available, it means that the trip is initiated with a Card amd will be " +
                    "completed at the destination.")
    @PostMapping( value = "/reload", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<PaymentPayload> reloadWallet(Principal principal, @RequestBody ReloadWalletPayload payload ) throws Exception {
        EndUserPayload user = userAPI.getUserByUsername( principal.getName() );

        WalletPayload wallet = api.getWalletById( payload.walletId() );

        TransactionPayload transaction = new TransactionPayload();
        transaction.setAmount( payload.amount() );
        transaction.setUserId( user.id() );
        transaction.setTarget( TransactionTarget.WALLET );
        transaction.setType( TransactionType.CR );
        transaction.setBalanced( false );
        transaction.setWallet( wallet.id() );
        transaction.setStatus( TransactionStatus.WAITING );

        transaction = transactionAPI.save( transaction );

        if( transaction.getId() == null ){
            throw new RuntimeException("Failed to initiate reload transaction.");
        }

        PaymentPayload payment = paymentAPI.initiatePayment( transaction );

        return ResponseEntity.ok( payment );
    }

    @PostMapping("/callback")
    @Hidden
    public ResponseEntity<?> klikPayCallback(
            @Valid @RequestBody CallbackPayload payload) throws JsonProcessingException, Exception {

        System.out.println("Ref: " + payload.reference_id());
        PaymentStatus payStat = payload.status().equalsIgnoreCase("true") ? PaymentStatus.PAID : PaymentStatus.FAILED;

        TransactionPayload transaction = transactionAPI.getTransactionById( UUID.fromString(payload.reference_id()) );

        PaymentPayload payment = paymentAPI.getTransactionPayment(UUID.fromString(payload.reference_id()));
        String[] possibleSuccess = new String[] { "true", "success", "processed" };
        boolean success = ArrayUtils.contains( possibleSuccess, String.valueOf(payload.status()) );
        if( !success ) {
            return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).build();
        }

        paymentAPI.save( payment.withCallback(payload.reference_id(), payStat, new Date()) );

        boolean isBalanced = transactionAPI.balanceTransaction( transaction );
        if( !isBalanced ) {
            return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).build();
        }

        callbackAPI.save(
                new CallbackLogPayload(
                        null,
                        payload.status(),
                        payload.amount(),
                        payload.currency(),
                        payload.trace_id(),
                        payload.reference_id(),
                        null,
                        null
                ));

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
