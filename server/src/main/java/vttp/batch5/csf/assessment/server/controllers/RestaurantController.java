package vttp.batch5.csf.assessment.server.controllers;

import java.io.StringReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.ws.rs.core.Response;
import vttp.batch5.csf.assessment.server.models.Menu;
import vttp.batch5.csf.assessment.server.models.Order;
import vttp.batch5.csf.assessment.server.models.PaymentDetails;
import vttp.batch5.csf.assessment.server.services.RestaurantService;

@RestController
@RequestMapping(path="/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {

  @Autowired
  private RestaurantService restaurantSvc;

  // Task 2.2
  // You may change the method's signature
  @GetMapping("/menu")
  public ResponseEntity<String> getMenus() {
    
    List<Menu> menus = restaurantSvc.getMenu();

    JsonArrayBuilder ab = Json.createArrayBuilder();

    for (Menu m : menus) {
      ab.add(m.toJsonObj());
    }
  
    return ResponseEntity.ok(ab.build().toString());
  }

  // Task 4
  // Do not change the method's signature
  @PostMapping("/food_order")
  public ResponseEntity<String> postFoodOrder(@RequestBody String payload) {
    try (JsonReader reader = Json.createReader(new StringReader(payload))) {
      JsonObject payloadObj = reader.readObject();

      Order order = Order.fromJson(payloadObj);

      String username = order.getUsername();
      String password = order.getPassword();
      
      // If validation fails
      if (!restaurantSvc.validateAccount(username, password)) {
        JsonObject resp = Json.createObjectBuilder()
          .add("message", "Invalid username and/or password")
          .build();
        return ResponseEntity.status(401).body(resp.toString());
      }

      try {
        PaymentDetails paymentDeets = restaurantSvc.placeOrder(order);

        JsonObject resp = paymentDeets.toJson();

        return ResponseEntity.ok(resp.toString());
      }
      catch (Exception e) {
        JsonObject resp = Json.createObjectBuilder()
          .add("message", e.getMessage())
          .build();
        return ResponseEntity.status(500).body(resp.toString());
      }
    }
    catch (Exception e) {
      JsonObject resp = Json.createObjectBuilder()
          .add("message", e.getMessage())
          .build();
        return ResponseEntity.status(500).body(resp.toString());
    }
  }
}
