package vttp.batch5.csf.assessment.server.repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import vttp.batch5.csf.assessment.server.models.LineItem;
import vttp.batch5.csf.assessment.server.models.Menu;
import vttp.batch5.csf.assessment.server.models.Order;
import vttp.batch5.csf.assessment.server.models.PaymentDetails;

@Repository
public class OrdersRepository {

  @Autowired
  private MongoTemplate mongoTemplate;

  private final String MENUS_COLLECTION = "menus";
  private final String ORDERS_COLLECTION = "orders";

  // Task 2.2
  // You may change the method's signature
  // Write the native MongoDB query in the comment below
  //
  //  db.menus.find({}).sort({name : 1})
  //
  public List<Menu> getMenu() {
    Query query = Query.query(Criteria.where(null)).with(Sort.by(Sort.Direction.ASC, "name"));

    List<Document> results = mongoTemplate.find(query, Document.class, MENUS_COLLECTION);

    System.out.println("Count of results: " + results.size());

    List<Menu> menus = new ArrayList<>();

    for (Document d : results) {
      menus.add(Menu.fromDoc(d));
    }

    return menus;
  }

  // Task 4
  // Write the native MongoDB query for your access methods in the comment below
  //
  /*
    db.orders.insert({
      _id: "ORDER_ID",
      order_id: "ORDER_ID",
      payment_id: "PAYMENT_ID",
      username: "USERNAME",
      total: 0,
      timestamp: Date(),
      items: [
          {id: "ITEM_ID", price: 0.0, quantity: 0}
      ]
    }) 

   */
  public void addOrder(Order order, PaymentDetails paymentDeets) {
    Document toInsert = new Document();

    toInsert.put("_id", order.getOrderId());
    toInsert.put("order_id", order.getOrderId());
    toInsert.put("payment_id", paymentDeets.getPaymentId());
    toInsert.put("username", order.getUsername());
    toInsert.put("total", order.getTotalPrice());
    toInsert.put("timestamp", new Date(paymentDeets.getTimestamp()));
    
    List<LineItem> items = order.getItems();

    List<Document> itemsDoc = new ArrayList<>();

    for (LineItem li : items) {
      itemsDoc.add(li.toDoc());
    }

    toInsert.put("items", itemsDoc);
    
    mongoTemplate.insert(toInsert, ORDERS_COLLECTION);

    System.out.println("Added order to mongo");
  }
}
