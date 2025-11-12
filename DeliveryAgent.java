import java.util.ArrayList;

public final class DeliveryAgent extends Person {
     int deliveryAgentId;
     static int globalId = 1;
     boolean isAvailable;
     double totalEarnings;
     ArrayList<Order> orders = new ArrayList<>();
     ArrayList<Order> comppletedOrders = new ArrayList<>();

     DeliveryAgent(String name, String password, String phone, String email, String location){
          super(name, password, phone, email, location, "deliveryagent");
          this.deliveryAgentId = globalId++;
          this.isAvailable = true;
          this.totalEarnings = 0;
     }


     public void getUnassignedOrders(App app){
          if(app.orders.isEmpty()){
               System.out.println("No Orders Found!");
               return;
          }

          for(int i = 0; i < app.orders.size(); i++){
               Order order = app.orders.get(i);

               if(order.deliveryAgent == null){
                    System.out.println("| Order Id : "+order.orderId+" | Customer Name : "+order.customer.name+" | Order Status : "+order.orderStatus+" | Total Amount : "+order.totalAmount+" |");
               }
          }
     }

     public void getAssignedOrders(){

          if(orders.isEmpty()){
               System.out.println("No Orders Found!");
               return;
          }
          
          for(int i = 0; i < orders.size(); i++){
               Order order = orders.get(i);

               if(order.orderStatus.equalsIgnoreCase("Order Placed")){
                    System.out.println("| Order Id : "+order.orderId+" | Customer Name : "+order.customer.name+" | Order Status : "+order.orderStatus+" | Total Amount : "+order.totalAmount+" |");
               }
          }
     }

     public void acceptOrder(Order order){

          if(!orders.contains(order)){
               System.out.println("This order Is Not Assigned For You!");
               return;
          }

          orders.add(order);
          isAvailable = false;
          order.updateOrderStatus("Order Assigned to Delivery Agent!");
     }

     public void updateDeliveryStatus(Order order, String orderStatus){

          if(order.orderStatus.equalsIgnoreCase("delivered")){
               System.out.println("This Order is Already Delivered!");
               return;
          }

          order.updateOrderStatus(orderStatus);

          if(orderStatus.equalsIgnoreCase("delivered")){
               totalEarnings += order.totalAmount;
               isAvailable = true;

               orders.remove(order);
               comppletedOrders.add(order);
               System.out.println("Order has Been Successfully Delivered and You have Earned total "+order.totalAmount);
               return;
          }

          System.out.println("Your Order Has Been Updated Successfully!");
     }

     public void checkCompletedOrders(){
          if(comppletedOrders.isEmpty()){
               System.out.println("There is no Orders You Completed!");
               return;
          }

          for(int i = 0; i < comppletedOrders.size(); i++){
               System.out.println(comppletedOrders.get(i).orderDetails());
          }
     }

     public String getTotalEarnings(){
          return this.viewProfile()+" Total Earnings : "+totalEarnings;
     }
}
