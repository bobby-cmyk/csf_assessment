package vttp.batch5.csf.assessment.server.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;

public class Order {
    private String orderId;
    private String username;
    private String password;
    private List<LineItem> items;

    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public List<LineItem> getItems() {
        return items;
    }
    public void setItems(List<LineItem> items) {
        this.items = items;
    }

    public Double getTotalPrice() {
        Double totalPrice = 0.0;

        for (LineItem li : items) {
            totalPrice += li.getSubTotal();
        }

        return totalPrice;
    }

    public static Order fromJson(JsonObject obj) {
        Order o = new Order();

        String orderId = UUID.randomUUID().toString().substring(0,8);
        
        o.setOrderId(orderId);
        o.setUsername(obj.getString("username"));
        o.setPassword(obj.getString("password"));
        JsonArray arr = obj.getJsonArray("items");
        
        List<LineItem> items = new ArrayList<>();

        for (JsonValue li : arr) {
            JsonObject liObj = li.asJsonObject();
            items.add(LineItem.fromJson(liObj));
        }

        o.setItems(items);

        return o;
    }
}
