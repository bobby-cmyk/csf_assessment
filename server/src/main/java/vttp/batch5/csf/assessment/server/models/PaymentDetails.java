package vttp.batch5.csf.assessment.server.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class PaymentDetails {
    private String paymentId;
    private String orderId;
    private Long timestamp;
    private Double total;

    public String getPaymentId() {
        return paymentId;
    }
    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public Long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
    public Double getTotal() {
        return total;
    }
    public void setTotal(Double total) {
        this.total = total;
    }

    public static PaymentDetails fromJson (JsonObject obj) {
        PaymentDetails pd = new PaymentDetails();
        pd.setPaymentId(obj.getString("payment_id"));
        pd.setOrderId(obj.getString("order_id"));
        pd.setTimestamp(obj.getJsonNumber("timestamp").longValue());
        return pd;
    }

    public JsonObject toJson() {
        JsonObject obj = Json.createObjectBuilder()
            .add("paymentId", paymentId)
            .add("orderId", orderId)
            .add("total", total)
            .add("timestamp", timestamp)
            .build();
        return obj;
    }
    
}
