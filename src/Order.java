import java.util.ArrayList;

class Order {
     int orderId;
     Customer customer;
     Hotel hotel;
     DeliveryAgent deliveryAgent;
     ArrayList<Item> items = new ArrayList<>();
     double totalAmount;
     String orderStatus;


     Order(Customer customer, Hotel hotel, ArrayList<Item> items){
          this.hotel = hotel;
          this.customer = customer;
          this.items = items;
          this.orderStatus = "ORDERED";
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
          String customerName = (customer == null) ? "Unknown" : customer.name;
          String hotelName = (hotel == null) ? "Unknown" : hotel.hotelName;
          String deliveryAgentName = (deliveryAgent == null) ? "Not Assigned" : deliveryAgent.name;

          return "| Order Id : "+orderId+" | Customer Name : "+customerName+" | Hotel Name : "+hotelName+" | Delivery Agent : "+deliveryAgentName+" | Order Status : "+orderStatus+" | Total Amount : "+totalAmount+" |";
     }
}
