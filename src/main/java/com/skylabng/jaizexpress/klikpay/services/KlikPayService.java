package com.skylabng.jaizexpress.klikpay.services;

import com.skylabng.jaizexpress.enduser.EndUserInternalAPI;
import com.skylabng.jaizexpress.enduser.EndUserPayload;
import com.skylabng.jaizexpress.enduser.model.EndUser;
import com.skylabng.jaizexpress.enums.PaymentMethod;
import com.skylabng.jaizexpress.enums.PaymentStatus;
import com.skylabng.jaizexpress.klikpay.models.CheckoutPayload;
import com.skylabng.jaizexpress.klikpay.models.KlikResponse;
import com.skylabng.jaizexpress.klikpay.models.PayloadItem;
import com.skylabng.jaizexpress.payment.PaymentInternalAPI;
import com.skylabng.jaizexpress.payment.PaymentPayload;
import com.skylabng.jaizexpress.transaction.TransactionInternalAPI;
import com.skylabng.jaizexpress.transaction.TransactionPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class KlikPayService {
    private final static String CHECKOUT_ENDPOINT = "/request-checkout";
    private final static String CURRENCY = "NGN";

    @Value("${klikpay.host}")
    private String klikPayHost;

    @Value("${klikpay.token}")
    private String klikPayToken;

    @Value("${host.name}")
    private String hostName;

    @Value("${host.scheme}")
    private String hostScheme;

    @Autowired
    @Qualifier("RestTemplateBearer")
    RestTemplate restTemplate;


    private final EndUserInternalAPI userAPI;

    public KlikPayService(EndUserInternalAPI userAPI) {
        this.userAPI = userAPI;
    }

    public PaymentPayload initiateCheckout(TransactionPayload transaction) throws Exception {
        CheckoutPayload payload = getCheckoutPayload(transaction);

        KlikResponse kr = requestCheckout( payload );
        System.out.println( kr.toString() );

        return getPaymentPayload(transaction, kr);
    }

    private CheckoutPayload getCheckoutPayload(TransactionPayload transaction) {
        EndUserPayload endUser = userAPI.getUserById( transaction.getUserId() );

        List<PayloadItem> items = new ArrayList<>();
        items.add( new PayloadItem( "Wallet Reload", 1, transaction.getAmount() ) );

        String callback = hostScheme + "://" + hostName + "/api/wallet/callback";
        String redirect = hostScheme + "://" + hostName + "/#/app/transaction-result/" + String.valueOf( transaction.getId() );

        return new CheckoutPayload(
                transaction.getAmount(),
                CURRENCY,
                String.valueOf( transaction.getId() ),
                callback,
                redirect,
                endUser.firstName(),
                endUser.lastName(),
                items
        );
    }

    private static PaymentPayload getPaymentPayload(TransactionPayload transaction, KlikResponse kr) throws Exception {
        if(kr == null) {
            throw new Exception("No response from Klikpay. Please try again.");
        }

        PaymentStatus payStat = kr.status().equalsIgnoreCase("true") ? PaymentStatus.WAITING : PaymentStatus.FAILED;

        return new PaymentPayload(
                null,
                transaction.getId(),
                transaction.getAmount(),
                kr.reference_id(),
                PaymentMethod.GATEWAY,
                kr.message(),
                kr.url(),
                payStat,
                new Date(), new Date(), new Date());
    }

    private KlikResponse requestCheckout(CheckoutPayload payload){
        String url = klikPayHost + CHECKOUT_ENDPOINT;
        String auth = "Bearer " + klikPayToken;

        HttpHeaders headers = new HttpHeaders();
        headers.set( "Authorization", auth );
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<CheckoutPayload> entity = new HttpEntity<CheckoutPayload> (payload, headers);

        return restTemplate.postForEntity(url, entity, KlikResponse.class).getBody();
    }
}
