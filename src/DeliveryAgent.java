import java.sql.SQLException;
import java.util.ArrayList;

final class DeliveryAgent extends Person {
     int deliveryAgentId;
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
     String invalidInputMessage = redColor+textBold+"\nInvalid Input!\n"+resetColor;

     DeliveryAgent(String name, String password, String phone, String email, String location){
          super(name, password, phone, email, location);
          this.isAvailable = true;
          this.totalEarnings = 0;
     }


     public void getUnassignedOrders(DataBase db) throws SQLException {
          ArrayList<Order> unAssignedOrders = db.getUnassignedOrders();

//         System.out.println("Total Unassigned Orders : "+unAssignedOrders.size());

          if(unAssignedOrders.isEmpty()){
               System.out.println(redColor+"No Orders Found!"+resetColor);
               return;
          }

//          for (Order o : unAssignedOrders){
//              System.out.println(o.orderDetails());
//          }

          boolean isNotAssigned = false;
          for (Order order : unAssignedOrders) {
             String customerName = (order.customer == null) ? "Unknown" : order.customer.name;

             if (order.orderStatus.equalsIgnoreCase("ORDERED")) {
                 System.out.println("| Order Id : " + order.orderId + " | Customer Name : " + customerName + " | " + greenColor + "Total Amount : ₹" + order.totalAmount + " |" + resetColor);
                 isNotAssigned = true;
             }
          }

          if(!isNotAssigned){
               System.out.println(redColor+"There is No UnAssigned Orders!"+resetColor);
          }
     }

     public void getAssignedOrders(DataBase db) throws SQLException {

         ArrayList<Order> assignedOrders = db.getOrderByAgentId(this.deliveryAgentId);

          if(assignedOrders.isEmpty()){
               System.out.println(redColor+"No Orders Found!"+resetColor);
               return;
          }
          
          boolean isAssigned = false;
          for (Order order : assignedOrders) {
             if (!order.orderStatus.equalsIgnoreCase("delivered")) {
                 System.out.println("| Order Id : " + order.orderId + " |" + greenColor + " Customer Name : " + ((order.customer == null) ? "Not Assigned" : order.customer.name) + resetColor + " | Order Status : " + order.orderStatus + greenColor + " | Total Amount : " + order.totalAmount + resetColor + " |");
                 isAssigned = true;
             }
          }

          if(!isAssigned){
               System.out.println(redColor+"There is No Assigned Orders!"+resetColor);
          }
     }

     public void updateDeliveryStatus(Order order, String orderStatus, DataBase db) throws SQLException {
//          System.out.println("Inside Delivery Agent : "+orderStatus);

          if(order.orderStatus.equalsIgnoreCase("delivered")){
               System.out.println(greenColor+"This Order is Already Delivered!"+resetColor);
               return;
          }

          order.updateOrderStatus(orderStatus);
          db.updateOrderStatus(order.orderId, orderStatus);

          if(orderStatus.equalsIgnoreCase("delivered")){
               totalEarnings += order.totalAmount;
               isAvailable = true;

               orders.remove(order);
               comppletedOrders.add(order);
               System.out.println(greenColor+"Order has Been Successfully Delivered and You have Earned total "+order.totalAmount+resetColor);

               db.updateDeliveryAgentEarnings(deliveryAgentId, totalEarnings);
               return;
          }

          System.out.println(greenColor+"Your Order Has Been Updated Successfully!"+resetColor);
     }

     public void checkCompletedOrders(DataBase db) throws SQLException {
         ArrayList<Order> allOrders = db.getOrderByAgentId(deliveryAgentId);

          if(allOrders.isEmpty()){
               System.out.println(redColor+"There is no Orders You Completed!"+resetColor);
               return;
          }

          System.out.println("=============================================================================");
          System.out.println(greenColor+"Completed Orders\n"+resetColor);

          int totalAmount = 0;
         for (Order o : allOrders) {
             System.out.println(o.orderDetails());
             totalAmount += (int) o.totalAmount;
         }

          System.out.println(greenColor+"\nTotal Amount : ₹"+totalAmount+resetColor);
          System.out.println("=============================================================================");
     }

     public String getTotalEarnings(){
          return "Agent Id : "+deliveryAgentId+" "+this.viewProfile()+greenColor+" Total Earnings : "+totalEarnings+resetColor;
     }
}
