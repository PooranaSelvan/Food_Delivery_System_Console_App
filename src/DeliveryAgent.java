import java.sql.SQLException;
import java.util.ArrayList;

final class DeliveryAgent extends Person {
     int deliveryAgentId;
     boolean isAvailable;
     double totalEarnings;
     ArrayList<Order> orders = new ArrayList<>();
     ArrayList<Order> completedOrders = new ArrayList<>();

     // Text Formatting & Decorations
     String redColor = "\u001B[91m";
     String resetColor = "\u001B[0m";
     String greenColor = "\u001B[92m";

     DeliveryAgent(String name, String password, String phone, String email, String location){
          super(name, password, phone, email, location);
          this.isAvailable = true;
          this.totalEarnings = 0;
     }


     public boolean getUnassignedOrders(DataBase db) throws SQLException {
          ArrayList<Order> unAssignedOrders = db.getUnassignedOrders();

//         System.out.println("Total Unassigned Orders : "+unAssignedOrders.size());

          if(unAssignedOrders.isEmpty()){
               System.out.println(redColor+"No Orders Found!"+resetColor);
               return false;
          }

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
               return false;
          }

          return true;
     }

     public boolean getAssignedOrders(DataBase db) throws SQLException {
         ArrayList<Order> assignedOrders = db.getOrderByAgentId(this.deliveryAgentId);

          if(assignedOrders.isEmpty()){
               System.out.println(redColor+"No Orders Found!"+resetColor);
               return false;
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
               return false;
          }

          return true;
     }

     public void updateDeliveryStatus(Order order, String orderStatus, DataBase db) throws SQLException {
//          System.out.println("Inside Delivery Agent : "+orderStatus);

          if(order.orderStatus.equalsIgnoreCase("DELIVERED")){
               System.out.println(greenColor+"This Order is Already Delivered!"+resetColor);
               return;
          }

          order.updateOrderStatus(orderStatus);
          db.updateOrderStatus(order.orderId, orderStatus);

          if(orderStatus.equalsIgnoreCase("DELIVERED")){
               isAvailable = true;

               if(orders != null){
                   orders.remove(order);
               }

               if(completedOrders != null){
                   completedOrders.add(order);
               }

               DeliveryAgent updatedAgent = db.getDeliveryAgentById(deliveryAgentId);

               if(updatedAgent != null){
                   double taxAmount = calculateTax(order.totalAmount);
                   totalEarnings = (updatedAgent.totalEarnings - taxAmount);
                   double finalEarnings = order.totalAmount - taxAmount;

                   System.out.println("\n" + greenColor);
                   System.out.println("╔══════════════════════════════════════╗");
                   System.out.println("║          DELIVERY COMPLETE           ║");
                   System.out.println("╠══════════════════════════════════════╣");
                   System.out.println("║  Order Id: " + order.orderId);
                   System.out.println("║  Initial Amount:    ₹" + order.totalAmount);
                   System.out.println("║  Tax Reduced:      -₹" + taxAmount);
                   System.out.println("╠══════════════════════════════════════╣");
                   System.out.println("║                                      ║");
                   System.out.println("║  Final Earnings:    ₹" + finalEarnings);
                   System.out.println("╚══════════════════════════════════════╝");
                   System.out.println(greenColor + "\nThank you for your delivery!" + resetColor);
               }
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

          System.out.println("╔══════════════════════════════════════════════════════════════════╗");
          System.out.println("║                        COMPLETED ORDERS                          ║");
          System.out.println("╠══════════════════════════════════════════════════════════════════╣");

          boolean isCompleted = false;

          for (Order o : allOrders) {
              if (o.orderStatus.equalsIgnoreCase("DELIVERED")) {
                  double finalEarnings = o.totalAmount - calculateTax(o.totalAmount);
                  System.out.println("║   Hotel:    " + o.hotel.hotelName);
                  System.out.println("║   Status:   " + o.orderStatus);
                  System.out.println("║   Amount:   $" + finalEarnings);
                  System.out.println("║   Date:     " + o.getDate(o.createdAt));
                  isCompleted = true;
              }
          }

          if (!isCompleted) {
              System.out.println("║   " + redColor + "No Orders Completed!");
              System.out.println("║   Start Your First Order Now!");
              System.out.println("║                                                                  ");
              System.out.println("╚══════════════════════════════════════════════════════════════════╝" + resetColor);
              return;
          }

         System.out.println("╚══════════════════════════════════════════════════════════════════╝" + resetColor);
     }

     public String getTotalEarnings(DataBase db) throws SQLException {
          DeliveryAgent d = db.getDeliveryAgentById(deliveryAgentId);

          if(d != null){
              totalEarnings = d.totalEarnings;
          }

          return greenColor+"Your Total Earnings is "+totalEarnings+resetColor;
     }

     private double calculateTax(double totalAmount){
            double tax = 0.10;

            if(totalAmount < 100){
                tax = 0.05;
            } else if(totalAmount > 500){
                tax = 0.15;
            }

            return totalAmount * tax;
     }
}
