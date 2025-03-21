package vttp.batch5.csf.assessment.server.models;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class Menu {
    private String id;
    private String name;
    private String description;
    private Double price;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

    public static Menu fromDoc (Document d) {
        Menu m = new Menu();
        m.setId(d.getString("_id"));
        m.setName(d.getString("name"));
        m.setDescription(d.getString("description"));
        m.setPrice(d.getDouble("price"));
        return m;
    }

    public JsonObject toJsonObj () {
        JsonObjectBuilder ob = Json.createObjectBuilder();
        ob.add("id", id);
        ob.add("name", name);
        ob.add("description", description);
        ob.add("price", price);
        return ob.build();
    }

}
