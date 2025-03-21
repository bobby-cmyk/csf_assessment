package vttp.batch5.csf.assessment.server.repositories;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import vttp.batch5.csf.assessment.server.models.Menu;

@Repository
public class OrdersRepository {

  @Autowired
  private MongoTemplate mongoTemplate;

  private final String MENUS_COLLECTION = "menus";

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
      System.out.println(d.toString());
      menus.add(Menu.fromDoc(d));
    }

    return menus;
  }

  // TODO: Task 4
  // Write the native MongoDB query for your access methods in the comment below
  //
  //  Native MongoDB query here
  
}
