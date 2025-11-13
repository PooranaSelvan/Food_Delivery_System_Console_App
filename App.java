import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
     ArrayList<Customer> customers = new ArrayList<>();
     ArrayList<Hotel> hotels = new ArrayList<>();
     ArrayList<DeliveryAgent> deliveryAgents = new ArrayList<>();
     ArrayList<Order> orders = new ArrayList<>();
     ArrayList<Admin> admins = new ArrayList<>();
     ArrayList<Person> users = new ArrayList<>();

     static Scanner input = new Scanner(System.in);
     DataBase db = new DataBase();

     // Text Formatting & Decorations
     String redColor = "\u001B[91m";
     String resetColor = "\u001B[0m";
     String cyanColor = "\u001B[96m";
     String topLine = "┌──────────────────────────────────────────────────┐";
     String bottomLine = "└──────────────────────────────────────────────────┘";
     String sideLine = "│";
     String textBold = "\u001B[1m";

     // Error Messages
     String invalidInputMessage = redColor+textBold+"\nInvalid Input!\n"+resetColor;

     
     public static void main(String[] args) {
          App app = new App();


          // Load Users
          app.customers = app.db.getCustomers();
          app.hotels = app.db.getHotels();
          app.deliveryAgents = app.db.getDeliveryAgents();
          app.orders = app.db.getOrders(app.customers, app.hotels, app.deliveryAgents);
          app.admins = app.db.getAdmins();
          app.users = app.db.getUsers();

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
               System.out.println();
               while (true) {
                    try {
                         System.out.print("1. SignUp\n2. Login\n3. Exit\nEnter your Choice : ");
                         userChoice = input.nextInt();

                         break;
                    } catch (InputMismatchException e) {
                         System.out.println(app.invalidInputMessage);
                         input.nextLine();
                         userChoice = 0;
                    }
               }

               input.nextLine();

               switch (userChoice) {
                    case 1:
                         System.out.println();
                         String cusName;
                         String cusPassword;
                         String cusPhoneNum;
                         String cusEmail;
                         String cusLocation;
                         String cusAddress;

                         while (true) {
                              try {
                                   System.out.print("Enter your Name : ");
                                   cusName = input.nextLine();

                                   break;
                              } catch (InputMismatchException e) {
                                   System.out.println(app.invalidInputMessage);
                                   input.nextLine();
                              }
                         }

                         while (true) {
                              try {
                                   System.out.print("Enter the Password : ");
                                   cusPassword = input.nextLine();

                                   break;
                              } catch (InputMismatchException e) {
                                   System.out.println(app.invalidInputMessage);
                                   input.nextLine();
                              }
                         }

                         while (true) {
                              try {
                                   System.out.print("Enter the Phone Number : ");
                                   cusPhoneNum = input.nextLine();

                                   break;
                              } catch (InputMismatchException e) {
                                   System.out.println(app.invalidInputMessage);
                                   input.nextLine();
                              }
                         }

                         while (true) {
                              try {
                                   emailCheckLoop:
                                   while (true) {
                                        System.out.print("Enter the Email : ");
                                        cusEmail = input.nextLine();


                                        for (Customer c : app.customers) {
                                             if(c.email.equalsIgnoreCase(cusEmail)){
                                                  System.out.println("This Email is Already Taken!");
                                                  continue emailCheckLoop;
                                             }
                                        }

                                        for (Person u : app.users) {
                                             if(u.email.equalsIgnoreCase(cusEmail)){
                                                  System.out.println("This Email is Already Taken!");
                                                  continue emailCheckLoop;
                                             }
                                        }

                                        break;
                                   }


                                   break;
                              } catch (InputMismatchException e) {
                                   System.out.println(app.invalidInputMessage);
                                   input.nextLine();
                              }
                         }

                         while (true) {
                              try {
                                   System.out.print("Enter the Location : ");
                                   cusLocation = input.nextLine();

                                   break;
                              } catch (InputMismatchException e) {
                                   System.out.println(app.invalidInputMessage);
                                   input.nextLine();
                              }
                         }

                         while (true) {
                              try {
                                   System.out.print("Enter the Address : ");
                                   cusAddress = input.nextLine();

                                   break;
                              } catch (InputMismatchException e) {
                                   System.out.println(app.invalidInputMessage);
                                   input.nextLine();
                              }
                         }

                         Customer newCustomer = new Customer(cusName, cusPassword, cusPhoneNum, cusEmail, cusLocation, cusAddress);

                         app.customers.add(newCustomer);
                         app.users.add(newCustomer);
                         
                         // saving in file
                         app.saveAllData();

                         // Welcome Message
                         System.out.println("\n\u001B[96m\u001B[1m"+"┌──────────────────────────────────────────────────┐");
                         System.out.println("│        Welcome to ZOSW Food Delivery App         │");
                         System.out.println("└──────────────────────────────────────────────────┘"+"\u001B[0m\n");
                         System.out.println(app.cyanColor+app.textBold+"You can Login Now!\n"+app.resetColor);
                         System.out.println();
                         break;
                    case 2:
                         System.out.println();
                         String email;
                         String password;

                         while (true) {
                              try {
                                   System.out.print("Enter the Email : ");
                                   email = input.nextLine();

                                   break;
                              } catch (InputMismatchException e) {
                                   System.out.println(app.invalidInputMessage);
                                   input.nextLine();
                              }
                         }
                         
                         while (true) {
                              try {
                                   System.out.print("Enter the Password : ");
                                   password = input.nextLine();

                                   break;
                              } catch (InputMismatchException e) {
                                   System.out.println(app.invalidInputMessage);
                                   input.nextLine();
                              }
                         }

                         boolean userFound = false;

                         Customer customer = null;
                         Admin admin = null;
                         DeliveryAgent deliveryAgent = null;
                         Person person = null;

                         String userRole = "";

                         if(app.users.isEmpty()){
                              System.out.println("\u001B[101m"+"There is No Users To Login!\nStart SignUp!"+"\u001B[0m");
                              break;
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
                         app.saveAllData();
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

               while (true) {
                    try {
                         System.out.print("1. View Hotels\n2. View Menu\n3. Add to Cart\n4. View Cart\n5. Place Order\n6. Track Order\n7. Order History\n8. Logout\nEnter your Choice : ");
                         userChoice = input.nextInt();

                         break;
                    } catch (InputMismatchException e) {
                         System.out.println(invalidInputMessage);
                         input.nextLine();
                         userChoice = 0;
                    }
               }
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

                         // Saving in File
                         saveAllData();

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
          int hotelId;

          while (true) {
               try {
                    System.out.print("Enter the Hotel Id to See the Hotel Menu : ");
                    hotelId = input.nextInt();

                    break;
               } catch (InputMismatchException e) {
                    System.out.println(invalidInputMessage);
                    input.nextLine();
               }
          }
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
          int hotelId;

          while (true) {
               try {
                    System.out.print("Enter the Hotel Id to See the Hotel Menu : ");
                    hotelId = input.nextInt();

                    break;
               } catch (InputMismatchException e) {
                    System.out.println(invalidInputMessage);
                    input.nextLine();
               }
          }
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
          int menuNum;

          while (orderAgain) {
               orderHotel.displayMenu();


               while (true) {
                    try {
                         System.out.print("Enter the Menu Number to Add to Cart : ");
                         menuNum = input.nextInt();

                         break;
                    } catch (InputMismatchException e) {
                         System.out.println(invalidInputMessage);
                         input.nextLine();
                    }
               }
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

               while (true) {
                    try {
                         System.out.print("Do you Want to Order More? [yes(y)/no(n)] : ");
                         userChoice = input.nextLine().toLowerCase();

                         break;
                    } catch (InputMismatchException e) {
                         System.out.println(invalidInputMessage);
                         input.nextLine();
                    }
               }
               

               if(userChoice.equalsIgnoreCase("y") || userChoice.equalsIgnoreCase("yes")){
                    continue;
               } else {
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

               while (true) {
                    try {
                         System.out.print("1. Unassigned Orders\n2. Assigned Orders\n3. Accept Order\n4. Update Delivery Status\n5. See Total Earnings\n6. Completed Orders\n7. Logout\nEnter your Choice : ");
                         userChoice = input.nextInt();

                         break;
                    } catch (InputMismatchException e) {
                         System.out.println(invalidInputMessage);
                         input.nextLine();
                         userChoice = 0;
                    }
               }
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

          int orderId;
          while (true) {
               try {
                    System.out.print("Enter the Order Id to Accept Order : ");
                    orderId = input.nextInt();

                    break;
               } catch (InputMismatchException e) {
                    System.out.println(invalidInputMessage);
                    input.nextLine();
               }
          }
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

          // Saving In File
          saveAllData();

          System.out.println("Order Id : "+selectedOrder.orderId+" has been Assigned Successfully!");
     }


     public void updateOrder(DeliveryAgent deliveryAgent){
          for(int i = 0; i < deliveryAgent.orders.size(); i++){
               System.out.println(deliveryAgent.orders.get(i).orderDetails());
          }

          int orderId;
          while (true) {
               try {
                    System.out.print("Enter the Order Id to Update Order Status : ");
                    orderId = input.nextInt();

                    break;
               } catch (InputMismatchException e) {
                    System.out.println(invalidInputMessage);
                    input.nextLine();
               }
          }
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

          String orderStatus = "";
          int userInput = 0;
          while (true) {
               try {
                    while(userInput != 1 && userInput != 2){
                         System.out.print("Enter New Order Status : (1. Out Of Delivery, 2. Delivered)\nEnter the Option Number : ");
                         userInput = input.nextInt();
                         
                         if(userInput == 1){
                              orderStatus = "Out of Delivery";
                         } else if(userInput == 2){
                              orderStatus = "Delivered";
                         } else {
                              System.out.println(invalidInputMessage);
                         }
                    }

                    break;
               } catch (InputMismatchException e) {
                    System.out.println(invalidInputMessage);
                    input.nextLine();
               }
          }

          

          deliveryAgent.updateDeliveryStatus(targetOrder, orderStatus);

          // Saving In File
          saveAllData();

     }





     // Admin Methods
     public void showAdminMenu(Admin admin, App app){
          System.out.println();

          System.out.println("======================================");
          System.out.println("Welcome Admin : "+admin.name);
          System.out.println("======================================");

          int userChoice = 0;

          while (userChoice != 11) {

               while (true) {
                    try {
                         System.out.print("1. Display All CUstomers\n2. Display All Hotels\n3. Diplay All Menu\n4. Display All DeliveryAgents\n5. Add Hotel\n6. Add Menu For Hotel\n7. Add Delivery Agent\n8. Remove Hotel\n9. Remove Delivery Agent\n10. Remove User\n11. Logout\nEnter your Choice : ");
                         userChoice = input.nextInt();

                         break;
                    } catch (InputMismatchException e) {
                         System.out.println(invalidInputMessage);
                         input.nextLine();
                         userChoice = 0;
                    }
               }
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

                         int showMenuHotelId;
                         while (true) {
                              try {
                                   System.out.print("Enter the Hotel Id to Display its Menu : ");
                                   showMenuHotelId = input.nextInt();
          
                                   break;
                              } catch (InputMismatchException e) {
                                   System.out.println(invalidInputMessage);
                                   input.nextLine();
                              }
                         }
                         input.nextLine();

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
                         String hotelName;
                         String hotelLocation;


                         while (true) {
                              try {
                                   System.out.print("Enter the Name of the Hotel : ");
                                   hotelName = input.nextLine();
          
                                   break;
                              } catch (InputMismatchException e) {
                                   System.out.println(invalidInputMessage);
                                   input.nextLine();
                              }
                         }

                         while (true) {
                              try {
                                   System.out.print("Enter the Hotel Location : ");
                                   hotelLocation = input.nextLine();
          
                                   break;
                              } catch (InputMismatchException e) {
                                   System.out.println(invalidInputMessage);
                                   input.nextLine();
                              }
                         }

                         Hotel hotel = new Hotel(hotelName, hotelLocation);
                         admin.addHotel(app, hotel);
                         
                         // Saving In File
                         saveAllData();
                         
                         System.out.println();
                         break;
                    case 6:
                         System.out.println();
                         if(hotels.isEmpty()){
                              System.out.println("There Is No Hotel to Add Menu!");
                              break;
                         }

                         admin.displayAllHotels(app);
                         int menuHotelId;

                         while (true) {
                              try {
                                   System.out.print("Enter the Hotel Id to Add Menu For It : ");
                                   menuHotelId = input.nextInt();
          
                                   break;
                              } catch (InputMismatchException e) {
                                   System.out.println(invalidInputMessage);
                                   input.nextLine();
                              }
                         }
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

                         int itemCount;
                         while (true) {
                              try {
                                   System.out.print("How Many Menu Items You Want to Add? : ");
                                   itemCount = input.nextInt();
          
                                   break;
                              } catch (InputMismatchException e) {
                                   System.out.println(invalidInputMessage);
                                   input.nextLine();
                              }
                         }
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
                              String itemName;
                              int itemPrice;
                              String itemCategory;
                              String itemDescription;


                              while (true) {
                                   try {
                                        System.out.print("Enter the Name of the Item : ");
                                        itemName = input.nextLine();
               
                                        break;
                                   } catch (InputMismatchException e) {
                                        System.out.println(invalidInputMessage);
                                        input.nextLine();
                                   }
                              }
                              
                              while (true) {
                                   try {
                                        System.out.print("Enter the Price of the Item : ");
                                        itemPrice = input.nextInt();
               
                                        break;
                                   } catch (InputMismatchException e) {
                                        System.out.println(invalidInputMessage);
                                        input.nextLine();
                                   }
                              }
                              input.nextLine();

                              while (true) {
                                   try {
                                        String userInput = "";
                                        while(!userInput.equalsIgnoreCase("v") && !userInput.equalsIgnoreCase("nv")){
                                             System.out.print("Enter the Item's Category [Veg(v) / Non-Veg(nv)] : ");
                                             userInput = input.nextLine();
                                        }
                                        itemCategory = userInput;
               
                                        break;
                                   } catch (InputMismatchException e) {
                                        System.out.println(invalidInputMessage);
                                        input.nextLine();
                                   }
                              }

                              while (true) {
                                   try {
                                        System.out.print("Enter the Item's Description : ");
                                        itemDescription = input.nextLine();
               
                                        break;
                                   } catch (InputMismatchException e) {
                                        System.out.println(invalidInputMessage);
                                        input.nextLine();
                                   }
                              }

                              Item newItem = new Item(itemName, itemPrice, itemCategory, itemDescription);
                              menuHotel.addFoodItem(newItem);
                              
                              // Saving In File
                              saveAllData();

                              System.out.println("\n\u001B[92m"+itemName+" has been Successfully Added to Hotel : "+menuHotel.hotelName+"\u001B[0m\n");
                         }

                         System.out.println();
                         break;
                    case 7:
                         System.out.println();
                         String dName;
                         String dPass;
                         String dPhone;
                         String dEmail;
                         String dLocation;


                         while (true) {
                              try {
                                   System.out.print("Enter the Name : ");
                                   dName = input.nextLine();
          
                                   break;
                              } catch (InputMismatchException e) {
                                   System.out.println(invalidInputMessage);
                                   input.nextLine();
                              }
                         }

                         while (true) {
                              try {
                                   System.out.print("Enter the Password : ");
                                   dPass = input.nextLine();
          
                                   break;
                              } catch (InputMismatchException e) {
                                   System.out.println(invalidInputMessage);
                                   input.nextLine();
                              }
                         }

                         while (true) {
                              try {
                                   System.out.print("Enter the Phone Number : ");
                                   dPhone = input.nextLine();
          
                                   break;
                              } catch (InputMismatchException e) {
                                   System.out.println(invalidInputMessage);
                                   input.nextLine();
                              }
                         }

                         while (true) {
                              try {
                                   System.out.print("Enter the Email : ");
                                   dEmail = input.nextLine();
          
                                   break;
                              } catch (InputMismatchException e) {
                                   System.out.println(invalidInputMessage);
                                   input.nextLine();
                              }
                         }
                         
                         while (true) {
                              try {
                                   System.out.print("Enter the Location : ");
                                   dLocation = input.nextLine();

                                   break;
                              } catch (InputMismatchException e) {
                                   System.out.println(invalidInputMessage);
                                   input.nextLine();
                              }
                         }     

                         DeliveryAgent d = new DeliveryAgent(dName, dPass, dPhone, dEmail, dLocation);

                         deliveryAgents.add(d);
                         users.add(d);
                         
                         // Saving in File
                         saveAllData();

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

                         int hotelId;
                         while (true) {
                              try {
                                   System.out.print("Enter the Hotel Id to Remove : ");
                                   hotelId = input.nextInt();

                                   break;
                              } catch (InputMismatchException e) {
                                   System.out.println(invalidInputMessage);
                                   input.nextLine();
                              }
                         }
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
                         
                         // Save In File
                         saveAllData();

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

                         int deliveryAgentId;
                         while (true) {
                              try {
                                   System.out.print("Enter the Delivery Agent Id to Remove : ");
                                   deliveryAgentId = input.nextInt();

                                   break;
                              } catch (InputMismatchException e) {
                                   System.out.println(invalidInputMessage);
                                   input.nextLine();
                              }
                         }
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

                         // Saving In File
                         saveAllData();

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

                         int customerId;
                         while (true) {
                              try {
                                   System.out.print("Enter the Customer Id to Remove : ");
                                   customerId = input.nextInt();

                                   break;
                              } catch (InputMismatchException e) {
                                   System.out.println(invalidInputMessage);
                                   input.nextLine();
                              }
                         }
                         input.nextLine();

                         Customer removedCustomer = null;

                         for(int i = 0; i < customers.size(); i++){
                              if(customers.get(i).customerId == customerId){
                                   removedCustomer = customers.remove(i);
                                   break;
                              }
                         }

                         if(removedCustomer == null){
                              System.out.println("Customer Not Found!");
                              break;
                         }

                         for(int i = 0; i < users.size(); i++){
                              if(users.get(i).email.equalsIgnoreCase(removedCustomer.email)){
                                   users.remove(i);
                                   break;
                              }
                         }
                         
                         // Saving In File
                         saveAllData();

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



     // Saving All data
     public void saveAllData(){
          db.saveCustomers(customers);
          db.saveDeliveryAgents(deliveryAgents);
          db.saveHotels(hotels);
          db.saveOrders(orders);
          db.saveUsers(users);
          db.saveAdmins(admins);
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