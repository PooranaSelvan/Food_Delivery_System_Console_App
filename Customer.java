import java.util.ArrayList;
import java.util.Scanner;

public final class Customer extends Person {
     int customerId;
     String address;
     ArrayList<Item> cart = new ArrayList<>();
     ArrayList<Order> orderHistory = new ArrayList<>();
     static int globalId = 1;
     static Scanner input = new Scanner(System.in);
     Hotel hotel;


     Customer(String name, String password, String phone, String email, String location, String address){
          super(name, password, phone, email, location, "customer");
          this.customerId = globalId++;
          this.address = address;
     }

     public void viewHotels(ArrayList<Hotel> hotels){
          if (hotels == null || hotels.isEmpty()) {
               System.out.println("No hotels available!");
               return;
          }

          System.out.println("\n=====================================================");
          System.out.println("All Hotels : ");
          for(int i = 0; i < hotels.size(); i++){
               if(hotels.get(i) != null){
                    System.out.println(hotels.get(i).getHotelDetails());
               }
          }
          System.out.println("=====================================================\n");
     }

     public void viewMenu(Hotel hotel){
          if(hotel == null){
               System.out.println("Hotel Not Found!");
               return;
          }

          System.out.println("Welcome to Hotel : "+hotel.hotelName);

          hotel.displayMenu();
     }

     public void viewCart(){
          if(cart.isEmpty()){
               System.out.println("There is No Items In the Cart!");
               return;
          }


          System.out.println("\n=====================================");
          System.out.println("Your Cart Items : ");
          for(int i = 0; i < cart.size(); i++){
               if(cart.get(i) != null){
                    System.out.println(cart.get(i).displayItemInfo());
               }
          }
          System.out.println("=====================================\n");
     }

     public void addToCart(Item item, Hotel hotel){
          if(cart.isEmpty()){
               this.hotel = hotel;
          }

          if(!cart.isEmpty() && this.hotel.hotelId != hotel.hotelId){
               System.out.println("You can Only able to Order from One Hotel!");
               return;
          }

          System.out.print("Enter the Quantity for "+item.itemName+" : ");
          int qty = input.nextInt();
          input.nextLine();

          if(qty <= 0){
               System.out.println("Enter the Valid Quantity!");
               return;
          }

          Item orderedItem = new Item(item.itemName, item.itemPrice, item.itemCategory, item.description);
          orderedItem.quantity = qty;
          orderedItem.itemPrice = item.itemPrice;

          cart.add(orderedItem);

          System.out.println(item.itemName+(item.quantity > 1 ? "*"+qty : "")+" has been Added to Cart!");
     }

     public void placeOrder(App app){
          if(cart.isEmpty()){
               System.out.println("Your Cart Is Empty!");
               return;
          }

          Order order = new Order(this, this.hotel, cart);
          order.calculateTotal();
          orderHistory.add(order);
          app.orders.add(order);

          System.out.println("Order Has Been Placed Successfully!");
          System.out.println("Your Total Amount is : "+order.totalAmount);

          cart.clear();
          this.hotel = null;
     }

     public void trackOrder(){
          String orderRes = "";
          if(orderHistory.isEmpty()){
               System.out.println("\nNo Orders Found\nStart Your First Order\n");
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
               System.out.println("All Your Orders Has been Delivered!\nCheck Order History");
          } else {
               System.out.println(orderRes);
          }
     }

     public void viewOrderHistory(){
          if(orderHistory.isEmpty()){
               System.out.println("\nNo Orders Found\nStart Your First Order\n");
               return;
          }

          for(int i = 0; i < orderHistory.size(); i++){
               if(orderHistory.get(i) != null){
                    System.out.println(orderHistory.get(i).orderDetails());
               }
          }
     }
}
