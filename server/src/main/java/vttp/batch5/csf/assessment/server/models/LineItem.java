package vttp.batch5.csf.assessment.server.models;

import org.bson.Document;

import jakarta.json.JsonObject;

public class LineItem {
    private String id;
    private Double price;
    private Integer quantity;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getSubTotal() {
        return quantity * price;
    }

    public static LineItem fromJson(JsonObject obj) {
        LineItem li = new LineItem();
        li.setId(obj.getString("id"));
        li.setPrice(obj.getJsonNumber("price").doubleValue());
        li.setQuantity(obj.getInt("quantity"));
        return li;
    }

    public Document toDoc() {
        Document doc = new Document();
        doc.put("id", id);
        doc.put("price", price);
        doc.put("quantity", quantity);
        return doc;
    }
}
