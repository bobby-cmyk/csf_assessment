package vttp.batch5.csf.assessment.server.repositories;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp.batch5.csf.assessment.server.models.Order;
import vttp.batch5.csf.assessment.server.models.PaymentDetails;

// Use the following class for MySQL database
@Repository
public class RestaurantRepository {

    @Autowired
    private JdbcTemplate sqlTemplate;

    public void addOrder(Order order, PaymentDetails paymentDeets) {

        final String SQL_INSERT_ORDER = "INSERT INTO place_orders (order_id, payment_id, order_date, total, username) VALUES (?, ?, ?, ?, ?)";

        sqlTemplate.update(SQL_INSERT_ORDER,
            order.getOrderId(),
            paymentDeets.getPaymentId(),
            new Timestamp(paymentDeets.getTimestamp()),
            order.getTotalPrice(),
            order.getUsername());

        System.out.println("Added order to mysql");
    }

    public boolean validateAccount(String username, String password) throws NoSuchAlgorithmException {

        if (!userExist(username)) {
            // User does not exist
            System.out.println("User does not exist: " + username);

            return false;
        }

        if (!(retrieveHashedPassword(username)
                .equals(generateHashedPassword(password)))) {
            
            System.out.println("Incorrect password for: " + username);
            
            return false;
        }
        
        System.out.println("Authenticated: " + username);
        return true;
    }

    private boolean userExist(String username) {
        
        final String SQL_COUNT_BY_USERNAME = "SELECT COUNT(username) AS count FROM customers WHERE username = ?";

        SqlRowSet rs = sqlTemplate.queryForRowSet(SQL_COUNT_BY_USERNAME, username);

        rs.next();

        int count = rs.getInt("count");

        return count > 0;
    }

    private String retrieveHashedPassword(String username) {
        final String SQL_SELECT_PASSWORD_BY_USERNAME = "SELECT password FROM customers WHERE username = ?";

        SqlRowSet rs = sqlTemplate.queryForRowSet(SQL_SELECT_PASSWORD_BY_USERNAME, username);

        rs.next();

        return rs.getString("password");
    }

    private String generateHashedPassword(String password) throws NoSuchAlgorithmException {
        //https://dev.mysql.com/doc/refman/8.4/en/encryption-functions.html#function_sha2
        MessageDigest md = MessageDigest.getInstance("SHA-224");
        byte[] digest = md.digest(password.getBytes(StandardCharsets.UTF_8));
        
        StringBuilder hashtext = new StringBuilder();
        for (byte b : digest) {
            hashtext.append(String.format("%02x", b));
        }
        
        return hashtext.toString();
    }
}
