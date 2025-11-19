import java.util.ArrayList;

public final class DeliveryAgent extends Person {
     int deliveryAgentId;
     static int agentGlobalId = 1;
     boolean isAvailable;
     double totalEarnings;
     ArrayList<Order> orders = new ArrayList<>();
     ArrayList<Order> comppletedOrders = new ArrayList<>();

     // Text Formatting & Decorations
     String redColor = "\u001B[91m";
     String resetColor = "\u001B[0m";
     String cyanColor = "\u001B[96m";
     String greenColor = "\u001B[92m";
     String textBold = "\u001B[1m";

     // Error Messages
     String invalidInputMessage = redColor+textBold+"\nInvalid Input!\n"+resetColor;

     DeliveryAgent(String name, String password, String phone, String email, String location){
          super(name, password, phone, email, location, "deliveryagent");
          this.deliveryAgentId = agentGlobalId++;
          this.isAvailable = true;
          this.totalEarnings = 0;
     }


     public void getUnassignedOrders(App app){

          if(app.orders.isEmpty()){
               System.out.println(redColor+"No Orders Found!"+resetColor);
               return;
          }

          boolean isNotAssigned = false;
          for(int i = 0; i < app.orders.size(); i++){
               Order order = app.orders.get(i);
               String customerName = (order.customer == null) ? "Unknown" : order.customer.name;

               if(order.deliveryAgent == null && order.orderStatus.equalsIgnoreCase("Order Placed")){
                    System.out.println("| Order Id : "+order.orderId+" | Customer Name : "+customerName+" | "+greenColor+"Total Amount : ₹"+order.totalAmount+" |"+resetColor);
                    isNotAssigned = true;
               }
          }

          if(!isNotAssigned){
               System.out.println(redColor+"There is No UnAssigned Orders!"+resetColor);
          }
     }

     public void getAssignedOrders(){

          if(orders.isEmpty()){
               System.out.println(redColor+"No Orders Found!"+resetColor);
               return;
          }
          
          boolean isAssigned = false;
          for(int i = 0; i < orders.size(); i++){
               Order order = orders.get(i);

               if(!order.orderStatus.equalsIgnoreCase("delivered")){
                    System.out.println("| Order Id : "+order.orderId+" |"+greenColor+" Customer Name : "+ ((order.customer == null) ? "Not Assigned" : order.customer.name) +resetColor+" | Order Status : "+order.orderStatus+greenColor+" | Total Amount : "+order.totalAmount+resetColor+" |");
                    isAssigned = true;
               }
          }

          if(!isAssigned){
               System.out.println(redColor+"There is No Assigned Orders!"+resetColor);
          }
     }

     public void updateDeliveryStatus(Order order, String orderStatus, App app){

          if(order.orderStatus.equalsIgnoreCase("delivered")){
               System.out.println(greenColor+"This Order is Already Delivered!"+resetColor);
               app.saveAllData();
               return;
          }

          order.updateOrderStatus(orderStatus);

          if(orderStatus.equalsIgnoreCase("delivered")){
               totalEarnings += order.totalAmount;
               isAvailable = true;

               orders.remove(order);
               comppletedOrders.add(order);
               System.out.println(greenColor+"Order has Been Successfully Delivered and You have Earned total "+order.totalAmount+resetColor);

               app.saveAllData();

               return;
          }

          System.out.println(greenColor+"Your Order Has Been Updated Successfully!"+resetColor);

          app.saveAllData();
     }

     public void checkCompletedOrders(){
          if(comppletedOrders.isEmpty()){
               System.out.println(redColor+"There is no Orders You Completed!"+resetColor);
               return;
          }

          System.out.println("=============================================================================");
          System.out.println(greenColor+"Completed Orders\n"+resetColor);

          int totalAmount = 0;
          for(int i = 0; i < comppletedOrders.size(); i++){
               System.out.println(comppletedOrders.get(i).orderDetails());
               totalAmount += comppletedOrders.get(i).totalAmount;
          }

          System.out.println(greenColor+"\nTotal Amount : ₹"+totalAmount+resetColor);
          System.out.println("=============================================================================");
     }

     public String getTotalEarnings(){
          return "Agent Id : "+deliveryAgentId+" "+this.viewProfile()+greenColor+" Total Earnings : "+totalEarnings+resetColor;
     }
}
