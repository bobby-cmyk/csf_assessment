package vttp.batch5.csf.assessment.server.services;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.batch5.csf.assessment.server.models.Menu;
import vttp.batch5.csf.assessment.server.models.Order;
import vttp.batch5.csf.assessment.server.models.PaymentDetails;
import vttp.batch5.csf.assessment.server.repositories.OrdersRepository;
import vttp.batch5.csf.assessment.server.repositories.RestaurantRepository;

@Service
public class RestaurantService {

  @Autowired
  private OrdersRepository ordersRepo;

  @Autowired
  private RestaurantRepository restaurantRepo;

  @Autowired
  private PaymentService paySvc;

  // Task 2.2
  // You may change the method's signature
  public List<Menu> getMenu() {
    return ordersRepo.getMenu();
  }
  
  // Task 4
  public boolean validateAccount(String username, String password) throws NoSuchAlgorithmException {

    System.out.println("Username: " + username);
    System.out.println("Password: " + password);
    return restaurantRepo.validateAccount(username, password);
  }

  public PaymentDetails placeOrder(Order order) {

    PaymentDetails paymentDeets = paySvc.makePayment(order);

    restaurantRepo.addOrder(order, paymentDeets);
  
    ordersRepo.addOrder(order, paymentDeets);

    paymentDeets.setTotal(order.getTotalPrice());

    return paymentDeets;
  }
}
