package vttp.batch5.csf.assessment.server.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.batch5.csf.assessment.server.models.Menu;
import vttp.batch5.csf.assessment.server.repositories.OrdersRepository;

@Service
public class RestaurantService {

  @Autowired
  private OrdersRepository ordersRepo;

  // Task 2.2
  // You may change the method's signature
  public List<Menu> getMenu() {
    return ordersRepo.getMenu();
  }
  
  // TODO: Task 4


}
