import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public final class Customer extends Person {
     int customerId;
     String address;
     ArrayList<Item> cart = new ArrayList<>();
     ArrayList<Order> orderHistory = new ArrayList<>();
     static int globalId = 1;
     static Scanner input = new Scanner(System.in);
     Hotel hotel;

     // Text Formatting & Decorations
     String redColor = "\n\u001B[91m";
     String resetColor = "\u001B[0m\n";
     String cyanColor = "\n\u001B[96m";
     String greenColor = "\n\u001B[92m";
     String textBold = "\u001B[1m";
     String invalidInputMessage = redColor+textBold+"\nInvalid Input!\n"+resetColor;


     Customer(String name, String password, String phone, String email, String location, String address){
          super(name, password, phone, email, location, "customer");
          this.customerId = globalId++;
          this.address = address;
     }

     public void viewHotels(ArrayList<Hotel> hotels){
          if (hotels == null || hotels.isEmpty()) {
               System.out.println(redColor+"No hotels available!"+resetColor);
               return;
          }

          System.out.println("\n=============================================================================");
          System.out.println(greenColor+textBold+"All Hotels : "+resetColor);
          for(int i = 0; i < hotels.size(); i++){
               if(hotels.get(i) != null){
                    System.out.println(hotels.get(i).getHotelDetails());
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


          System.out.println("\n==========================================");
          System.out.println(greenColor+"Your Cart Items : "+resetColor);
          for(int i = 0; i < cart.size(); i++){
               if(cart.get(i) != null){
                    System.out.println(cart.get(i).displayItemInfo());
               }
          }
          System.out.println("==========================================\n");
     }

     public boolean addToCart(Item item, Hotel hotel, App app){
          if(cart.isEmpty()){
               this.hotel = hotel;
          }

          if(!cart.isEmpty() && this.hotel.hotelId != hotel.hotelId && this.hotel != null){
               System.out.println(redColor+"You can Only able to Order from One Hotel!"+resetColor);

               while (true) {
                    String userChoice;
                    while (true) {
                         try {
                              System.out.print("Are you Sure want to Reset the Cart and Order from this hotel? [y/n]");
                              userChoice = input.nextLine();
     
                              break;
                         } catch (InputMismatchException e) {
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
                                   System.out.println(app.invalidInputMessage);
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

               cart.add(orderedItem);
          }
          // System.out.println(cart.size());

          System.out.println(greenColor+item.itemName+(item.quantity > 1 ? "*"+qty : "")+" has been Added to Cart!"+resetColor);

          app.saveAllData();

          return true;
     }

     public void placeOrder(App app){
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
          order.orderStatus = "Order Placed";
          orderHistory.add(order);
          app.orders.add(order);

          System.out.println(greenColor+"Order Has Been Placed Successfully!");
          System.out.println("Your Total Amount is : "+order.totalAmount+resetColor);

          app.saveAllData();

          cart.clear();
          this.hotel = null;
     }

     public void trackOrder(App app){
          String orderRes = "";

          app.loadAllData();


          this.orderHistory.clear();
          for (Order order : app.orders) {
               if (order.customer != null && order.customer.customerId == this.customerId) {
                   this.orderHistory.add(order);
               }
          }


          if(orderHistory.isEmpty()){
               System.out.println(redColor+"\nNo Orders Found\nStart Your First Order\n"+resetColor);
               return;
          }

          for(int i = 0; i < orderHistory.size(); i++){
               Order order = orderHistory.get(i);

               String deliveryAgentName = (order.deliveryAgent == null) ? "Not Assigned" : order.deliveryAgent.name;

               if(!order.orderStatus.equalsIgnoreCase("delivered")){
                    orderRes += "| Order Id : "+order.orderId+" | Ordered Hotel : "+order.hotel.hotelName+" | Delivery Agent Name : "+deliveryAgentName+" | Order Status : "+order.orderStatus+" | Total Amount : "+order.totalAmount+" |\n";
               }
          }

          if(orderRes.length() < 1){
               System.out.println(greenColor+"All Your Orders Has been Delivered!\nCheck Order History"+resetColor);
          } else {
               System.out.println(orderRes);
          }
     }

     public void viewOrderHistory(App app){
          app.loadAllData();

          System.out.println(greenColor+"Order History : "+resetColor);

          this.orderHistory.clear();
          for (Order order : app.orders) {
               if (order.customer != null && order.customer.customerId == this.customerId) {
                   this.orderHistory.add(order);
               }
          }

          if(orderHistory.isEmpty()){
               System.out.println(redColor+"\nNo Orders Found\nStart Your First Order\n"+resetColor);
               return;
          }

          for(int i = 0; i < orderHistory.size(); i++){
               if(orderHistory.get(i) != null){
                    System.out.println(orderHistory.get(i).orderDetails());
               }
          }
     }

     public String displayCustomerDetails(){
          return "Customer Id : "+customerId+" "+this.viewProfile();
     }
}
