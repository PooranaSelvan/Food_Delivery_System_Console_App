import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

final class Admin extends Person {
     int adminId;
    Logger logger = LogManager.getLogger("Admin");

     // Text Formatting & Decorations
     String redColor = "\u001B[91m";
     String resetColor = "\u001B[0m";
     String greenColor = "\u001B[92m";
     String textBold = "\u001B[1m";

     Admin(String name, String password, String phone, String email){
          super(name, password, phone, email, "None");
     }

     public void addHotel(DataBase db, Hotel hotel) throws SQLException {
          db.saveHotel(hotel);
          logger.info("Added a Hotel");
     }

     public void removeHotel(DataBase db, int hotelId) throws SQLException {
          db.deleteHotel(hotelId);
         logger.info("Removed a Hotel");
     }

     public void addDeliveryAgent(DataBase db, DeliveryAgent deliveryAgent) throws SQLException {
         db.saveDeliveryAgent(deliveryAgent);
         logger.info("Added a DeliveryAgent");
     }

     public void removeDeliveryAgent(DataBase db, int deliveryAgentId) throws SQLException {
          db.deleteDeliveryAgent(deliveryAgentId);
         logger.info("Removed a DeliveryAgent");
     }

     public void displayAllCustomers(DataBase db) throws SQLException {
          ArrayList<Customer> customers = db.getCustomers();

          if(customers.isEmpty()){
               System.out.println(redColor+"There is No Customers to Display!"+resetColor);
               return;
          }
         for (Customer c : customers) {
             ArrayList<Order> customerOrders = db.getCustomerOrders(c.customerId);
             System.out.println(c.displayDetails() + greenColor + " Orders Count : " + customerOrders.size() + " |" + resetColor);
         }
     }

     public void displayAllDeliveryAgents(DataBase db) throws SQLException {
          ArrayList<DeliveryAgent> deliveryAgents = db.getDeliveryAgents();

          if(deliveryAgents.isEmpty()){
               System.out.println(redColor+"There is No Delivery Agents to Display!"+resetColor);
               return;
          }

          System.out.println("==========================================================");
          System.out.println(greenColor + "All Delivery Agents : " + resetColor);
          for (DeliveryAgent d : deliveryAgents) {
               System.out.println(d.getTotalEarnings(db));
          }
          System.out.println("==========================================================");
     }

     public void displayAllHotels(DataBase db) throws SQLException {
         ArrayList<Hotel> hotels = db.getHotels();

          if(hotels.isEmpty()){
               System.out.println(redColor+"There is No Hotels to Display!"+resetColor);
               return;
          }

          for (Hotel h : hotels) {
               System.out.println(h.displayDetails());
          }
     }

     public void displayAllMenu(Hotel hotel){
          for(int i = 0; i < hotel.menu.size(); i++){
               System.out.println(hotel.menu.get(i).displayDetails());
          }
     }

     public void removeCustomer(DataBase db, int customerId) throws SQLException {
         db.deleteCustomer(customerId);
         logger.info("Removed a Customer");
     }
}