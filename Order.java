import java.util.ArrayList;

public class Order {
     int orderId;
     static int globalId = 1;
     Customer customer;
     Hotel hotel;
     DeliveryAgent deliveryAgent;
     ArrayList<Item> items = new ArrayList<>();
     double totalAmount;
     String orderStatus;


     Order(Customer customer, Hotel hotel, ArrayList<Item> items){
          this.orderId = globalId++;
          this.hotel = hotel;
          this.customer = customer;
          this.items = items;
          this.orderStatus = "Order Placed";
          this.totalAmount = 0;
          this.deliveryAgent = null;
     }


     public void calculateTotal(){
          for (Item item : items) {
               totalAmount += item.itemPrice * item.quantity;
          }
     }

     public void updateOrderStatus(String orderStatus){
          this.orderStatus = orderStatus;
     }

     public String orderDetails(){
          String deliveryAgentName = (deliveryAgent == null) ? "Not Assigned" : deliveryAgent.name;

          return "| Order Id : "+orderId+" | Customer Name : "+customer.name+" | Hotel Name : "+hotel.hotelName+" | Delivery Agent : "+deliveryAgentName+" | Order Status : "+orderStatus+" | Total Amount : "+totalAmount+" |";
     }
}
