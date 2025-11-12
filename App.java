import java.util.ArrayList;
import java.util.Scanner;

public class App {
     ArrayList<Customer> customers = new ArrayList<>();
     ArrayList<Hotel> hotels = new ArrayList<>();
     ArrayList<DeliveryAgent> deliveryAgents = new ArrayList<>();
     ArrayList<Order> orders = new ArrayList<>();
     ArrayList<Admin> admins = new ArrayList<>();
     ArrayList<Person> users = new ArrayList<>();

     static Scanner input = new Scanner(System.in);
     
     public static void main(String[] args) {
          App app = new App();
          DataBase db = new DataBase();


          // Load Users
          app.customers = db.getCustomers();
          app.hotels = db.getHotels();
          app.deliveryAgents = db.getDeliveryAgents();
          app.orders = db.getOrders(app.customers, app.hotels, app.deliveryAgents);
          app.admins = db.getAdmins();
          app.users = db.getUsers();

          if (app.users.isEmpty()) {
               // Users
               Admin a = new Admin("selvan", "3", "123456789", "a", "Tenkasi");
               DeliveryAgent d1 = new DeliveryAgent("Ramu", "2", "987654321", "d", "Tenkasi");
               DeliveryAgent d2 = new DeliveryAgent("Somu", "2", "1234567890", "d2", "Tenkasi");
               Customer c = new Customer("PooranSelvan", "1", "1234567890", "c", "Tenkasi", "North Street, Tenkasi");
           
               app.admins.add(a);
               app.users.add(a);
           
               app.deliveryAgents.add(d1);
               app.users.add(d1);
               app.deliveryAgents.add(d2);
               app.users.add(d2);
           
               app.customers.add(c);
               app.users.add(c);
           
               // Hotel
               Item i1 = new Item("Idly", 20, "South Indian", "Soft Idlyy");
               Item i2 = new Item("Dosa", 100, "South Indian", "Crispy Dosa");
               Item i3 = new Item("Poori", 50, "South Indian", "Floffy Poori");
               Item i4 = new Item("Tea", 10, "Drinks", "Hot Tea");
               Item i5 = new Item("Coffee", 10, "Drinks", "Hot Coffee");
               Item i6 = new Item("Cold Coffee", 100, "Drinks", "Cold Iced Coffee");

               Hotel h1 = new Hotel("Aaahaaa Restaurant", "Tenkasi");
               h1.addFoodItem(i1);
               h1.addFoodItem(i2);
               h1.addFoodItem(i3);
               app.hotels.add(h1);

               Hotel h2 = new Hotel("Rajini Murugan Tea Kadai", "Tenkasi");
               h2.addFoodItem(i4);
               h2.addFoodItem(i5);
               h2.addFoodItem(i6);
               app.hotels.add(h2);
          }

          int userChoice = 0;


          // Welcome Message
          System.out.println("\n\u001B[96m\u001B[1m"+"┌──────────────────────────────────────────────────┐");
          System.out.println("│        Welcome to ZOSW Food Delivery App         │");
          System.out.println("└──────────────────────────────────────────────────┘"+"\u001B[0m\n");


          while (userChoice != 3) {
               System.out.print("1. SignUp\n2. Login\n3. Exit\nEnter your Choice : ");
               userChoice = input.nextInt();
               input.nextLine();

               switch (userChoice) {
                    case 1:
                         System.out.println();
                         System.out.print("Enter your Name : ");
                         String cusName = input.nextLine();

                         System.out.print("Enter the Password : ");
                         String cusPassword = input.nextLine();

                         System.out.print("Enter the Phone Number : ");
                         String cusPhoneNum = input.nextLine();

                         System.out.print("Enter the Email : ");
                         String cusEmail = input.nextLine();

                         System.out.print("Enter the Location : ");
                         String cusLocation = input.nextLine();

                         System.out.print("Enter the Address : ");
                         String cusAddress = input.nextLine();

                         Customer newCustomer = new Customer(cusName, cusPassword, cusPhoneNum, cusEmail, cusLocation, cusAddress);

                         app.customers.add(newCustomer);
                         app.users.add(newCustomer);

                         // Welcome Message
                         System.out.println("\n\u001B[96m\u001B[1m"+"┌──────────────────────────────────────────────────┐");
                         System.out.println("│        Welcome to ZOSW Food Delivery App         │");
                         System.out.println("└──────────────────────────────────────────────────┘"+"\u001B[0m\n");
                         System.out.println("You can Login Now!\n");
                         System.out.println();
                         break;
                    case 2:
                         System.out.println();

                         System.out.print("Enter the Email : ");
                         String email = input.nextLine();

                         System.out.print("Enter the Password : ");
                         String password = input.nextLine();

                         boolean userFound = false;

                         Customer customer = null;
                         Admin admin = null;
                         DeliveryAgent deliveryAgent = null;
                         Person person = null;

                         String userRole = "";

                         if(app.users.isEmpty()){
                              System.out.println("\u001B[101m"+"There is No Users To Login!\nStart SignUp!"+"\u001B[0m");
                              return;
                         }


                         for(int i = 0; i < app.users.size(); i++){
                              // System.out.println("Coming Loop!");
                              if(app.users.get(i).email.equalsIgnoreCase(email)){
                                   userFound = true;
                                   person = app.users.get(i);

                                   if(person.role.equalsIgnoreCase("customer")){
                                        if(app.findCustomer(person) != null){
                                             customer = app.findCustomer(person);
                                             userRole = "customer";
                                        }
                                   } else if(person.role.equalsIgnoreCase("deliveryagent")){
                                        if(app.findDeliveryAgent(person) != null){
                                             deliveryAgent = app.findDeliveryAgent(person);
                                             userRole = "deliveryagent";
                                        }
                                   } else if(person.role.equalsIgnoreCase("admin")){
                                        if(app.findAdmin(person) != null){
                                             admin = app.findAdmin(person);
                                             userRole = "admin";
                                        }
                                   }
                                   break;
                              }
                         }

                         if(!userFound){
                              System.out.println("\n\u001B[91m"+"Invalid Email Or Password!"+"\u001B[0m\n");
                         } else {
                              if(userRole.equalsIgnoreCase("customer")){
                                   if(customer.login(email, password)){
                                        app.showCustomerMenu(customer);
                                   } else {
                                        System.out.println("\n\u001B[91m"+"Invalid Credentials"+"\u001B[0m\n");
                                   }
                              } else if(userRole.equalsIgnoreCase("deliveryagent")){
                                   if(deliveryAgent.login(email, password)){
                                        app.showDeliveryAgentMenu(deliveryAgent);
                                   } else {
                                        System.out.println("\n\u001B[91m"+"Invalid Credentials"+"\u001B[0m\n");
                                   }
                              } else if(userRole.equalsIgnoreCase("admin")){
                                   if(admin.login(email, password)){
                                        app.showAdminMenu(admin, app);
                                   } else {
                                        System.out.println("\n\u001B[91m"+"Invalid Credentials"+"\u001B[0m\n");
                                   }
                              } else {
                                   System.out.println("Unknown Role!");
                              }
                         }

                         break;
                    case 3:
                         System.out.println("\n\u001B[92m"+"Thank you For Visiting!"+"\u001B[0m\n");
                         // Save data
                         db.saveCustomers(app.customers);
                         db.saveDeliveryAgents(app.deliveryAgents);
                         db.saveHotels(app.hotels);
                         db.saveOrders(app.orders);
                         db.saveUsers(app.users);
                         db.saveAdmins(app.admins);
                         break;
                    default:
                         System.out.println("\n\u001B[91m"+"Enter the Valid Choice!"+"\u001B[0m\n");
                         break;
               }
          }
     }


     // Customer Methods
     public void showCustomerMenu(Customer customer){
          System.out.println();
          
          boolean isOrder = true;
          int userChoice = 0;

          while (userChoice != 8) {
               System.out.print("1. View Hotels\n2. View Menu\n3. Add to Cart\n4. View Cart\n5. Place Order\n6. Track Order\n7. Order History\n8. Logout\nEnter your Choice : ");
               userChoice = input.nextInt();
               input.nextLine();

               switch (userChoice) {
                    case 1:
                         System.out.println();

                         if(hotels.isEmpty()){
                              System.out.println("\n\u001B[91m"+"There is No Hotels to Display!"+"\u001B[0m\n");
                              break;
                         }

                         customer.viewHotels(hotels);
                         System.out.println();
                         break;
                    case 2:
                         System.out.println();

                         if(hotels.isEmpty()){
                              System.out.println("\n\u001B[91m"+"There is No Hotels to Display Menu!"+"\u001B[0m\n");
                              break;
                         }

                         showMenu(customer);
                         System.out.println();
                         break;
                    case 3:
                         System.out.println();

                         if(hotels.isEmpty()){
                              System.out.println("\n\u001B[91m"+"There is No Hotels to Order Food!"+"\u001B[0m\n");
                              break;
                         }

                         showMenuNaddCart(customer, isOrder);
                         System.out.println();
                         break;
                    case 4:
                         System.out.println();
                         customer.viewCart();
                         System.out.println();
                         break;
                    case 5:
                         System.out.println();
                         customer.placeOrder(this);
                         System.out.println();
                         break;
                    case 6:
                         System.out.println();
                         customer.trackOrder();
                         System.out.println();
                         break;
                    case 7:
                         System.out.println();
                         customer.viewOrderHistory();
                         System.out.println();
                         break;
                    case 8:
                         System.out.println();
                         System.out.println("\n\u001B[92m"+"Logging Out..!"+"\u001B[0m\n");
                         System.out.println();
                         return;
                    default:
                         System.out.println("\n\u001B[91m"+"Enter the Valid Option Number!"+"\u001B[0m\n");
               }
          }

          System.out.println();
     }

     public void showMenu(Customer customer){
          customer.viewHotels(hotels);
          System.out.print("Enter the Hotel Id to See the Hotel Menu : ");
          int hotelId = input.nextInt();
          input.nextLine();

          
          Hotel newHotel = null;
          for (Hotel h : hotels) {
               if (h.hotelId == hotelId) {
                    newHotel = h;
                   break;
               }
          }
          if (newHotel == null) {
              System.out.println("\n\u001B[91m"+"Enter a valid Hotel ID!"+"\u001B[0m\n");
              return;
          }

          newHotel.displayMenu();
     }

     public void showMenuNaddCart(Customer customer, boolean isOrder){
          customer.viewHotels(hotels);
          System.out.print("Enter the Hotel Id to See the Hotel Menu : ");
          int hotelId = input.nextInt();
          input.nextLine();

          Hotel orderHotel = null;
          for (Hotel h : hotels) {
               if (h.hotelId == hotelId) {
                    orderHotel = h;
                   break;
               }
          }
          if (orderHotel == null) {
              System.out.println("\n\u001B[91m"+"Enter a valid Hotel ID!"+"\u001B[0m\n");
              return;
          }

          boolean orderAgain = true;

          while (orderAgain) {
               orderHotel.displayMenu();

               System.out.print("Enter the Menu Number to Add to Cart : ");
               int menuNum = input.nextInt();
               input.nextLine();

               Item orderItem = null;

               for(int i = 0; i < orderHotel.menu.size(); i++){
                    if(orderHotel.menu.get(i).itemId == menuNum){
                         orderItem = orderHotel.menu.get(i);
                         break;
                    }
               }

               if(orderItem == null){
                    System.out.println("\n\u001B[91m"+"Item Not Found!"+"\u001B[0m\n");
                    return;
               }

               customer.addToCart(orderItem, orderHotel);
               
               String userChoice = "";


               System.out.print("Do you Want to Order More? [yes(y)/no(n)] : ");
               userChoice = input.nextLine().toLowerCase();

               if(userChoice.equalsIgnoreCase("n") || userChoice.equalsIgnoreCase("no")){
                    orderAgain = false;
               }
          }
     }





     // Delivery Agent Methods
     public void showDeliveryAgentMenu(DeliveryAgent deliveryAgent){
          System.out.println();

          System.out.println("====================================================");
          System.out.println("Welcome Delivery Agent!  : "+deliveryAgent.name);
          System.out.println("====================================================\n");

          int userChoice = 0;

          while (userChoice != 7) {
               System.out.print("1. Unassigned Orders\n2. Assigned Orders\n3. Accept Order\n4. Update Delivery Status\n5. See Total Earnings\n6. Completed Orders\n7. Logout\nEnter your Choice : ");
               userChoice = input.nextInt();
               input.nextLine();

               switch (userChoice) {
                    case 1:
                         System.out.println();
                         deliveryAgent.getUnassignedOrders(this);
                         System.out.println();
                         break;
                    case 2:
                         System.out.println();
                         deliveryAgent.getAssignedOrders();
                         System.out.println();
                         break;
                    case 3:
                         System.out.println();
                         ArrayList<Order> unAssignedOrders = new ArrayList<>();

                         if(orders.isEmpty()){
                              System.out.println("\n\u001B[91m"+"There is No Order To Accept!"+"\u001B[0m\n");
                         } else {
                              for(int i = 0; i < orders.size(); i++){
                                   if(orders.get(i).deliveryAgent == null){
                                        unAssignedOrders.add(orders.get(i));
                                   }
                              }

                              if(unAssignedOrders.isEmpty()){
                                   System.out.println("\n\u001B[91m"+"There is No UnAssigned Orders!"+"\u001B[0m\n");
                              } else {
                                   processOrders(unAssignedOrders, deliveryAgent);
                              }
                         }
                         System.out.println();
                         break;
                    case 4:
                         System.out.println();
                         if(deliveryAgent.orders.isEmpty()){
                              System.out.println("\n\u001B[91m"+"No Assigned Orders to Update!"+"\u001B[0m\n");
                         } else {
                              updateOrder(deliveryAgent);
                         }
                         System.out.println();
                         break;
                    case 5:
                         System.out.println();
                         System.out.println(deliveryAgent.getTotalEarnings());
                         System.out.println();
                         break;
                    case 6:
                         System.out.println();
                         deliveryAgent.checkCompletedOrders();
                         System.out.println();
                         break;
                    case 7:
                         System.out.println();
                         System.out.println("\n\u001B[92m"+"Logging Out..!"+"\u001B[0m\n");
                         System.out.println();
                         return;
                    default:
                         System.out.println("\n\u001B[91m"+"Enter the Valid Option Number!"+"\u001B[0m\n");
               }
          }

     }


     public void processOrders(ArrayList<Order> unAssignedOrders, DeliveryAgent deliveryAgent){
          
          for (int i = 0; i < unAssignedOrders.size(); i++) {
               System.out.println(unAssignedOrders.get(i).orderDetails());
          }

          System.out.print("Enter the Order Id to Accept Order : ");
          int orderId = input.nextInt();
          input.nextLine();

          Order selectedOrder = null;
          for (Order o : unAssignedOrders) {
               if (o.orderId == orderId) {
                   selectedOrder = o;
                   break;
               }
          }

          if(selectedOrder == null){
               System.out.println("\n\u001B[91m"+"Order Not Found!"+"\u001B[0m\n");
               return;
          }

          selectedOrder.deliveryAgent = deliveryAgent;
          deliveryAgent.orders.add(selectedOrder);
          deliveryAgent.isAvailable = false;

          System.out.println("Order Id : "+selectedOrder.orderId+" has been Assigned Successfully!");
     }


     public void updateOrder(DeliveryAgent deliveryAgent){
          for(int i = 0; i < deliveryAgent.orders.size(); i++){
               System.out.println(deliveryAgent.orders.get(i).orderDetails());
          }

          System.out.print("Enter the Order Id to Update Order Status : ");
          int orderId = input.nextInt();
          input.nextLine();

          Order targetOrder = null;
          for (Order order : deliveryAgent.orders) {
               if (order.orderId == orderId) {
                   targetOrder = order;
                   break;
               }
          }

          if(targetOrder == null) {
               System.out.println("\n\u001B[91m"+"Order Not Found!"+"\u001B[0m\n");
               return;
          }

          if(targetOrder.orderStatus.equalsIgnoreCase("delivered")){
               System.out.println("This order has already delivered!");
               return;
          }

          System.out.print("Enter New Order Status : (Out Of Delivery, Delivered) : ");
          String orderStatus = input.nextLine();

          deliveryAgent.updateDeliveryStatus(targetOrder, orderStatus);
     }





     // Admin Methods
     public void showAdminMenu(Admin admin, App app){
          System.out.println();

          System.out.println("======================================");
          System.out.println("Welcome Admin : "+admin.name);
          System.out.println("======================================");

          int userChoice = 0;

          while (userChoice != 11) {
               System.out.print("1. Display All Users\n2. Display All Hotels\n3. Diplay All Menu\n4. Display All DeliveryAgents\n5. Add Hotel\n6. Add Menu For Hotel\n7. Add Delivery Agent\n8. Remove Hotel\n9. Remove Delivery Agent\n10. Remove User\n11. Logout\nEnter your Choice : ");
               userChoice = input.nextInt();
               input.nextLine();

               switch (userChoice) {
                    case 1:
                         System.out.println();

                         if(customers.isEmpty()){
                              System.out.println("\n\u001B[91m"+"There is No Customers to Display!"+"\u001B[0m\n");
                              break;
                         }

                         admin.displayAllCustomers(app);
                         System.out.println();
                         break;
                    case 2:
                         System.out.println();

                         if(hotels.isEmpty()){
                              System.out.println("\n\u001B[91m"+"There is No Hotels to Display!"+"\u001B[0m\n");
                              break;
                         }

                         admin.displayAllHotels(app);
                         System.out.println();
                         break;
                    case 3:
                         System.out.println();
                         admin.displayAllHotels(app);

                         System.out.print("Enter the Hotel Id to Display its Menu : ");
                         int showMenuHotelId = input.nextInt();

                         Hotel showMenuHotel = null;

                         for(int i = 0; i < hotels.size(); i++){
                              if(hotels.get(i).hotelId == showMenuHotelId){
                                   showMenuHotel = hotels.get(i);
                              }
                         }

                         if(showMenuHotel == null){
                              System.out.println("Hotel Not Found!");
                              break;
                         }

                         admin.displayAllMenu(showMenuHotel);

                         System.out.println();
                         break;
                    case 4:
                         System.out.println();

                         if(deliveryAgents.isEmpty()){
                              System.out.println("\n\u001B[91m"+"There is No Delivery Agents to Display!"+"\u001B[0m\n");
                              break;
                         }

                         admin.displayAllDeliveryAgents(app);
                         System.out.println();
                         break;
                    case 5:
                         System.out.println();
                         System.out.print("Enter the Name of the Hotel : ");
                         String hotelName = input.nextLine();

                         System.out.print("Enter the Hotel Location : ");
                         String hotelLocation = input.nextLine();

                         Hotel hotel = new Hotel(hotelName, hotelLocation);
                         admin.addHotel(app, hotel);
                         System.out.println();
                         break;
                    case 6:
                         System.out.println();
                         if(hotels.isEmpty()){
                              System.out.println("There Is No Hotel to Add Menu!");
                              break;
                         }

                         admin.displayAllHotels(app);

                         System.out.print("Enter the Hotel Id to Add Menu For It : ");
                         int menuHotelId = input.nextInt();
                         input.nextLine();

                         Hotel menuHotel = null;
                         for (Hotel h : hotels) {
                              if (h.hotelId == menuHotelId) {
                                   menuHotel = h;
                                 break;
                              }
                         }
                         if (menuHotel == null) {
                             System.out.println("\n\u001B[91m"+"Enter a valid Hotel ID!"+"\u001B[0m\n");
                             return;
                         }

                         System.out.print("How Many Menu Items You Want to Add? : ");
                         int itemCount = input.nextInt();
                         input.nextLine();

                         if(itemCount < 0){
                              System.out.println("\n\u001B[91m"+"Enter the Valid Item Count!"+"\u001B[0m\n");
                              break;
                         }

                         if(itemCount > 5){
                              System.out.println("\n\u001B[91m"+"You can Only add Upto 5 Items at a Time!"+"\u001B[0m\n");
                              break;
                         }

                         for(int i = 0; i < itemCount; i++){
                              System.out.print("Enter the Name of the Item : ");
                              String itemName = input.nextLine();

                              System.out.print("Enter the Price of the Item : ");
                              int itemPrice = input.nextInt();
                              input.nextLine();

                              System.out.print("Enter the Item's Category : ");
                              String itemCategory = input.nextLine();

                              System.out.print("Enter the Item's Description : ");
                              String itemDescription = input.nextLine();

                              Item newItem = new Item(itemName, itemPrice, itemCategory, itemDescription);
                              menuHotel.addFoodItem(newItem);

                              System.out.println("\n\u001B[92m"+itemName+" has been Successfully Added to Hotel : "+menuHotel.hotelName+"\u001B[0m\n");
                         }

                         System.out.println();
                         break;
                    case 7:
                         System.out.println();
                         System.out.print("Enter the Name : ");
                         String dName = input.nextLine();

                         System.out.print("Enter the Password : ");
                         String dPass = input.nextLine();

                         System.out.print("Enter the Phone Number : ");
                         String dPhone = input.nextLine();

                         System.out.print("Enter the Email : ");
                         String dEmail = input.nextLine();

                         System.out.print("Enter the Location : ");
                         String dLocation = input.nextLine();

                         DeliveryAgent d = new DeliveryAgent(dName, dPass, dPhone, dEmail, dLocation);

                         deliveryAgents.add(d);

                         System.out.print("");
                         System.out.println();
                         break;
                    case 8:
                         System.out.println();

                         if(hotels.isEmpty()){
                              System.out.println("\n\u001B[91m"+"There is No Hotel to Remove!"+"\u001B[0m\n");
                              break;
                         }

                         for (Hotel h : hotels) {
                              System.out.println(h.getHotelDetails());
                         }

                         System.out.print("Enter the Hotel Id to Remove : ");
                         int hotelId = input.nextInt();
                         input.nextLine();

                         Hotel removeHotel = null;
                         for (Hotel h : hotels) {
                              if (h.hotelId == hotelId) {
                                   removeHotel = h;
                                   break;
                              }
                         }
                         if (removeHotel == null) {
                             System.out.println("\n\u001B[91m"+"Enter a valid Hotel ID!"+"\u001B[0m\n");
                             return;
                         }

                         hotels.remove(removeHotel);

                         System.out.println();
                         break;
                    case 9:
                         System.out.println();

                         if(deliveryAgents.isEmpty()){
                              System.out.println("\n\u001B[91m"+"There is No Delivery Agents to Remove!"+"\u001B[0m\n");
                              break;
                         }

                         for (DeliveryAgent da : deliveryAgents) {
                              System.out.println(da.getTotalEarnings());
                         }

                         System.out.print("Enter the Delivery Agent Id to Remove : ");
                         int deliveryAgentId = input.nextInt();
                         input.nextLine();

                         DeliveryAgent removedAgent = null;
                    

                         for(int i = 0; i < deliveryAgents.size(); i++){
                              if(deliveryAgents.get(i).deliveryAgentId == deliveryAgentId){
                                   removedAgent = deliveryAgents.remove(i);
                                   break;
                              }
                         }

                         if (removedAgent == null) {
                              System.out.println("\n\u001B[91mInvalid Delivery Agent ID!\u001B[0m\n");
                              break;
                         }
                          

                         for(int i = 0; i < users.size(); i++){
                              if(users.get(i).email.equalsIgnoreCase(removedAgent.email)){
                                   users.remove(i);
                                   break;
                              }
                         }

                         System.out.println();
                         break;
                    case 10:
                         System.out.println();

                         if(customers.isEmpty()){
                              System.out.println("\n\u001B[91m"+"There is No Customers to Remove!"+"\u001B[0m\n");
                              break;
                         }

                         for (Customer u : customers) {
                              System.out.println(u.viewProfile());
                         }

                         System.out.print("Enter the Customer Id to Remove : ");
                         int customerId = input.nextInt();
                         input.nextLine();

                         Customer removedCustomer = null;

                         for(int i = 0; i < customers.size(); i++){
                              if(customers.get(i).customerId == customerId){
                                   removedCustomer = customers.remove(i);
                                   break;
                              }
                         }

                         for(int i = 0; i < users.size(); i++){
                              if(users.get(i).email.equalsIgnoreCase(removedCustomer.email)){
                                   users.remove(i);
                                   break;
                              }
                         }

                         System.out.println();
                         break;
                    case 11:
                         System.out.println();
                         System.out.println("\n\u001B[92m"+"Logging Out..!"+"\u001B[0m\n");
                         System.out.println();
                         return;
                    default:
                         System.out.println("\n\u001B[91m"+"Enter the Valid Option Number!"+"\u001B[0m\n");
               }
          }

          System.out.println();
     }



     // Find Methods
     public Customer findCustomer(Person person){
          for(int i = 0; i < customers.size(); i++){
               if(person.email.equalsIgnoreCase(customers.get(i).email)){
                    return customers.get(i);
               }
          }

          return null;
     }

     public DeliveryAgent findDeliveryAgent(Person person){
          for(int i = 0; i < deliveryAgents.size(); i++){
               if(person.email.equalsIgnoreCase(deliveryAgents.get(i).email)){
                    return deliveryAgents.get(i);
               }
          }

          return null;
     }

     public Admin findAdmin(Person person){
          for(int i = 0; i < admins.size(); i++){
               if(person.email.equalsIgnoreCase(admins.get(i).email)){
                    return admins.get(i);
               }
          }

          return null;
     }
}