import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

final class Customer extends Person implements Display{
     int customerId;
     String address;
     ArrayList<Item> cart = new ArrayList<>();
     static Scanner input = new Scanner(System.in);
     Hotel hotel;
     Logger logger = LogManager.getLogger("Customer");

     // Text Formatting & Decorations
     String redColor = "\u001B[91m";
     String resetColor = "\u001B[0m";
     String greenColor = "\u001B[92m";
     String textBold = "\u001B[1m";
     String invalidInputMessage = redColor+textBold+"\nInvalid Input!\n"+resetColor;


     Customer(String name, String password, String phone, String email, String location, String address){
          super(name, password, phone, email, location);
          this.address = address;
     }

     public void viewHotels(DataBase db) throws SQLException {
         ArrayList<Hotel> hotels = db.getHotels();

          if (hotels == null || hotels.isEmpty()) {
               System.out.println(redColor+"No hotels available!"+resetColor);
               return;
          }

          System.out.println("\n=============================================================================");
          System.out.println(greenColor+textBold+"All Hotels : "+resetColor+"\n");
          for (Hotel value : hotels) {
              if (value != null) {
                 System.out.println(value.displayDetails());
              }
          }
          System.out.println("\n=============================================================================");
     }

     public void viewMenu(Hotel hotel){
          if(hotel == null){
               System.out.println("Hotel Not Found!");
               return;
          }

          System.out.println(greenColor+"Welcome to Hotel : "+hotel.hotelName+resetColor);
          hotel.displayMenu();
     }

     public void viewCart(){
          if(cart.isEmpty()){
               System.out.println(redColor+"There is No Items In the Cart!"+resetColor);
               return;
          }

          int totalAmount = 0;
          System.out.println("\n==========================================");
          System.out.println(greenColor+"Your Cart Items : "+resetColor+"\n");
          for(Item item : cart){
             if(item != null){
                 System.out.println(item.displayDetails());
                 totalAmount += (int) (item.itemPrice * item.quantity);
             }
          }
          System.out.println(greenColor+"\nYour Total Amount is : ₹"+totalAmount+resetColor);
          System.out.println("==========================================\n");
     }

     public boolean addToCart(Item item, Hotel hotel){
          if(cart.isEmpty()){
               this.hotel = hotel;
          }

          if(!cart.isEmpty() && this.hotel.hotelId != hotel.hotelId){
               System.out.println(redColor+"You can Only able to Order from One Hotel!"+resetColor);

               while (true) {
                    String userChoice;
                    while (true) {
                         try {
                              System.out.print("Are you Sure want to Reset the Cart and Order from this hotel? [y/n]");
                              userChoice = input.nextLine();
     
                              break;
                         } catch (InputMismatchException e) {
                              logger.error(e.getMessage());
                              System.out.println(invalidInputMessage);
                              input.nextLine();
                         }
                    }

                    if(userChoice.equalsIgnoreCase("y")){
                         cart.clear();
                         this.hotel = hotel;
                         break;
                    } else if(userChoice.equalsIgnoreCase("n")) {
                         System.out.println(redColor+"Item Is Not Added to Cart!"+resetColor);
                         return false;
                    } else {
                         System.out.println(invalidInputMessage);
                         continue;
                    }
               }
          }

          int qty;
          while (true) {
               try {
                    while (true) {
                         while (true) {
                              try {
                                   System.out.print("Enter the Quantity for "+item.itemName+" : ");
                                   qty = input.nextInt();

                                   break;
                              } catch (InputMismatchException e) {
                                   System.out.println(invalidInputMessage);
                                   input.nextLine();
                              }
                         }
                         
                         if(qty < 1){
                              System.out.println(redColor+"Enter the Valid Quantity!"+resetColor);
                              continue;
                         }

                         if(qty > 15){
                              System.out.println(redColor+"You can Only Order 15 at a Time!"+resetColor);
                              continue;
                         }
                         break;
                    }

                    break;
               } catch (InputMismatchException e) {
                    logger.error(e.getMessage());
                    System.out.println(invalidInputMessage);
                    input.nextLine();
               }
          }
          input.nextLine();

          // Updating if Already Have
          boolean updateItem = false;
          for (Item i : cart) {
               if(i.itemName.equalsIgnoreCase(item.itemName)){
                    updateItem = true;
                    i.quantity += qty;
                    i.itemPrice = item.itemPrice;
                    break;
               }
          }


          if(!updateItem){
               Item orderedItem = new Item(item.itemName, item.itemPrice, item.itemCategory, item.description);
               orderedItem.quantity = qty;
               orderedItem.itemId = item.itemId;

               cart.add(orderedItem);
          }
          // System.out.println(cart.size());

          System.out.println("\n"+greenColor+item.itemName+((item.quantity > 1) ? "×"+item.quantity : "")+resetColor+" has been Added to Cart!"+"\n");
          logger.info("{} has Added a Item to Cart!", name);
          return true;
     }

     public void placeOrder(DataBase db) throws SQLException {
          if(cart.isEmpty()){
               System.out.println(redColor+"Your Cart Is Empty!"+resetColor);
               return;
          }

          if(this.hotel == null){
               System.out.println(redColor+"No hotel selected for this order!"+resetColor);
               return;
          }

          Order order = new Order(this, this.hotel, cart);
          order.calculateTotal();
          order.orderStatus = "ORDERED";
          int orderId = db.saveOrder(order);

          if(orderId > 0){
              System.out.println(greenColor);
              System.out.println("╔════════════════════════════════════╗");
              System.out.println("║                BILL                ║");
              System.out.println("╠════════════════════════════════════╣");
              System.out.println("╠ ORDER ID     : "+order.orderId);
              System.out.println("╠ HOTEL NAME   : "+order.hotel.hotelName);
              System.out.println("╠ ORDER STATUS : "+order.orderStatus);
              for (Item item : cart) {
                  System.out.println("╠ " + item.quantity + "× " + item.itemName + "       : " + "₹" + item.itemPrice * item.quantity);
              }
              System.out.println("╠ TOTAL AMOUNT : ₹"+order.totalAmount);
              System.out.println("╚════════════════════════════════════╝");
              System.out.println(resetColor);

              cart.clear();
              this.hotel = null;
              logger.info("{} has Placed a New Order!", name);
          } else {
              System.out.println(redColor + "Order is Not Placed!" + resetColor);
          }
     }

     public void trackOrder(DataBase db) throws SQLException {
         ArrayList<Order> customerOrders = db.getCustomerOrders(this.customerId);

          if(customerOrders.isEmpty()){
               System.out.println(redColor+"\nNo Orders Found\nStart Your First Order\n"+resetColor);
               return;
          }

          String orderRes = "";

          for (Order order : customerOrders) {
              String deliveryAgentName = (order.deliveryAgent == null) ? "Not Assigned" : order.deliveryAgent.name;
              String hotelName = (order.hotel == null) ? "Unknown Hotel" : order.hotel.hotelName;

              if (!order.orderStatus.equalsIgnoreCase("delivered")) {
                 orderRes += "| Order Id : " + order.orderId + " | Ordered Hotel : " + hotelName + " | Delivery Agent Name : " + deliveryAgentName + " | Order Status : " + order.orderStatus + " | Total Amount : ₹" + order.totalAmount + " |\n";
              }
          }

          if(orderRes.isEmpty()){
               System.out.println(greenColor+"All Your Orders Has been Delivered!\nCheck Order History"+resetColor);
          } else {
               System.out.println(orderRes);
          }
     }

     public void viewOrderHistory(DataBase db) throws SQLException {
         ArrayList<Order> customerOrders = db.getCustomerOrders(this.customerId);

          if(customerOrders.isEmpty()){
               System.out.println(redColor+"\nNo Orders Found\nStart Your First Order\n"+resetColor);
               return;
          }

          System.out.println("=============================================================");
          System.out.println(greenColor+"Order History : "+resetColor);

          int totalAmount = 0;
          for (Order customerOrder : customerOrders) {
              if (customerOrder != null) {
                  System.out.println(customerOrder.displayDetails());
                  totalAmount += (int) customerOrder.totalAmount;
              }
          }

          System.out.println(greenColor+"Total Amount : ₹"+totalAmount+resetColor);
          System.out.println("=============================================================");
     }

     @Override
     public String displayDetails(){
         return "Customer Id : "+customerId+" "+this.viewProfile();
     }
}
