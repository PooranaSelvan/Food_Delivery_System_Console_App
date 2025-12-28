import java.sql.SQLException;
import java.util.ArrayList;

final class Admin extends Person {
     int adminId;

     // Text Formatting & Decorations
     String redColor = "\u001B[91m";
     String resetColor = "\u001B[0m";
     String cyanColor = "\u001B[96m";
     String greenColor = "\u001B[92m";
     String textBold = "\u001B[1m";
     String invalidInputMessage = redColor+textBold+"\nInvalid Input!\n"+resetColor;

     Admin(String name, String password, String phone, String email){
          super(name, password, phone, email, "None");
     }

     public void addHotel(DataBase db, Hotel hotel) throws SQLException {
          db.saveHotel(hotel);
     }

     public void removeHotel(DataBase db, int hotelId) throws SQLException {
          db.deleteHotel(hotelId);
     }

     public void addDeliveryAgent(DataBase db, DeliveryAgent deliveryAgent) throws SQLException {
         db.saveDeliveryAgent(deliveryAgent);
     }

     public void removeDeliveryAgent(DataBase db, int deliveryAgentId) throws SQLException {
          db.deleteDeliveryAgent(deliveryAgentId);
     }

     public void displayAllCustomers(DataBase db) throws SQLException {
          ArrayList<Customer> customers = db.getCustomers();

          if(customers.isEmpty()){
               System.out.println(redColor+"There is No Customers to Display!"+resetColor);
               return;
          }
         for (Customer c : customers) {
             ArrayList<Order> customerOrders = db.getCustomerOrders(c.customerId);
             System.out.println(c.displayCustomerDetails() + greenColor + " Orders Count : " + customerOrders.size() + " |" + resetColor);
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
               System.out.println(h.getHotelDetails());
          }
     }

     public void displayAllMenu(Hotel hotel){
          for(int i = 0; i < hotel.menu.size(); i++){
               System.out.println(hotel.menu.get(i).displayItemInfo());
          }
     }

     public void removeCustomer(DataBase db, int customerId) throws SQLException {
         db.deleteCustomer(customerId);
     }
}