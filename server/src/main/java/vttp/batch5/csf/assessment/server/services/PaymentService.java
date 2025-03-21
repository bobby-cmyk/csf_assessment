package vttp.batch5.csf.assessment.server.services;

import java.io.StringReader;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.batch5.csf.assessment.server.models.Order;
import vttp.batch5.csf.assessment.server.models.PaymentDetails;

@Service
public class PaymentService {
    
    private final RestTemplate restTemplate = new RestTemplate();

    private final String endpoint = "https://payment-service-production-a75a.up.railway.app/api/payment";

    private final String OFFICIAL_NAME = "Ong Hao Ting Aiken";

    public PaymentDetails makePayment(Order order) {

        JsonObject paymentReq = Json.createObjectBuilder()
            .add("order_id", order.getOrderId())
            .add("payer", order.getUsername())
            .add("payee", OFFICIAL_NAME)
            .add("payment", order.getTotalPrice())
            .build();

        RequestEntity<String> req = RequestEntity
            .post(endpoint)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .header("X-Authenticate", order.getUsername())
            .body(paymentReq.toString(), String.class);

        System.out.println("Start exchanging");
        ResponseEntity<String> resp = restTemplate.exchange(req, String.class);
        System.out.println("Received response");

        try (JsonReader reader = Json.createReader(new StringReader(resp.getBody()))) {
            
            JsonObject result = reader.readObject();

            System.out.print("Payment was successful");
            System.out.println(result.toString());
            
            PaymentDetails pd = PaymentDetails.fromJson(result);

            return pd;
        }   

    }

    
}
