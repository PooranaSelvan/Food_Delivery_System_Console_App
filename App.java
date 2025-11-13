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
     String redColor = "\n\u001B[91m";
     String resetColor = "\u001B[0m\n";
     String cyanColor = "\n\u001B[96m";
     String greenColor = "\n\u001B[92m";
     String topLine = "┌──────────────────────────────────────────────────┐";
     String bottomLine = "└──────────────────────────────────────────────────┘";
     String sideLine = "│";
     String textBold = "\u001B[1m";

     // Error Messages
     String invalidInputMessage = redColor+textBold+"\nInvalid Input!\n"+resetColor;

     
     public static void main(String[] args) {
          App app = new App();


          // Load Users
          app.loadAllData();

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
                         System.out.print(app.cyanColor+"┌───────────────────────────────┐"+"\n│         1. Login              │\n│         2. SignUp             │\n│         3. Exit               │\n");
                         System.out.println("└───────────────────────────────┘"+app.resetColor);
                         System.out.print("Enter your Choice : ");
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
                         String email;
                         String password;

                         // Menu
                         System.out.println("\n╔═══════════════════════╗\n║      Login Menu       ║\n╚═══════════════════════╝\n");

                         while (true) {
                              try {
                                   System.out.print("Enter the Email : ");
                                   email = input.nextLine();

                                   if(!app.isValidEmail(email)){
                                        System.out.println(app.redColor+"Minimum 13 Letters Required for Email!"+app.resetColor);
                                        continue;
                                   }

                                   break;
                              } catch (InputMismatchException e) {
                                   System.out.println(app.invalidInputMessage);
                                   input.nextLine();
                              }
                         }
                         
                         while (true) {
                              try {
                                   System.out.print("Enter the Password : ");
                                   password = System.console().readPassword().toString();

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
                              System.out.println(app.redColor+"There is No Users To Login!\nStart SignUp!"+app.resetColor);
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
                              System.out.println(app.redColor+"Invalid Email Or Password!"+app.resetColor);
                         } else {
                              if(userRole.equalsIgnoreCase("customer")){
                                   if(customer.login(email, password)){
                                        app.showCustomerMenu(customer);
                                   } else {
                                        System.out.println(app.redColor+"Invalid Credentials"+app.resetColor);
                                   }
                              } else if(userRole.equalsIgnoreCase("deliveryagent")){
                                   if(deliveryAgent.login(email, password)){
                                        app.showDeliveryAgentMenu(deliveryAgent);
                                   } else {
                                        System.out.println(app.redColor+"Invalid Credentials"+app.resetColor);
                                   }
                              } else if(userRole.equalsIgnoreCase("admin")){
                                   if(admin.login(email, password)){
                                        app.showAdminMenu(admin, app);
                                   } else {
                                        System.out.println(app.redColor+"Invalid Credentials"+app.resetColor);
                                   }
                              } else {
                                   System.out.println(app.redColor+"Unknown Role!"+app.resetColor);
                              }
                         }

                         break;
                    case 2:
                         System.out.println();
                         String cusName;
                         String cusPassword;
                         String cusPhoneNum;
                         String cusEmail;
                         String cusLocation;
                         String cusAddress;


                         System.out.println("\n╔═══════════════════════╗\n║      SignUp Menu      ║\n╚═══════════════════════╝\n");

                         while (true) {
                              try {
                                   System.out.print("Enter your Name : ");
                                   cusName = input.nextLine();

                                   if(app.isValidName(cusName)){
                                        System.out.println(app.redColor+"Minimum 5 Letters Required For Name!"+app.resetColor);
                                        continue;
                                   }

                                   break;
                              } catch (InputMismatchException e) {
                                   System.out.println(app.invalidInputMessage);
                                   input.nextLine();
                              }
                         }

                         while (true) {
                              try {
                                   System.out.print("Enter the Password : ");
                                   cusPassword = System.console().readPassword().toString();

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

                                   if(!app.isValidPhoneNumber(cusPassword)){
                                        System.out.println(app.redColor+"Length of the Phone Number Must be 10"+app.resetColor);
                                        continue;
                                   }

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

                                        if(!app.isValidEmail(cusEmail)){
                                             System.out.println(app.redColor+"Minimum 13 Letters Required for Email!"+app.resetColor);
                                             continue;
                                        }

                                        for (Person u : app.users) {
                                             if(u.email.equalsIgnoreCase(cusEmail)){
                                                  System.out.println(app.redColor+"This Email is Already Taken!"+app.resetColor);
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

                                   if(!app.isValidLocation(cusLocation)){
                                        System.out.println(app.redColor+"Minimum 5 Letter Required for Location!"+app.resetColor);
                                        continue;
                                   }

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

                                   if(!app.isValidAddress(cusAddress)){
                                        System.out.println(app.redColor+"Minimum 15 Letters Required for Location!"+app.resetColor);
                                        continue;
                                   }

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
                    case 3:
                         System.out.println(app.greenColor+"Thank you For Visiting!"+app.resetColor);
                         
                         // Save data
                         app.saveAllData();
                         break;
                    default:
                         System.out.println(app.redColor+"Enter the Valid Choice!"+app.resetColor);
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
                         System.out.print(cyanColor+"┌───────────────────────────────┐\n"+"│ 1. View Hotels                │     \n│ 2. View Menu                  │     \n│ 3. Add to Cart                │     \n│ 4. View Cart                  │     \n│ 5. Place Order                │     \n│ 6. Track Order                │     \n│ 7. Order History              │     \n│ 8. Logout                     │     \n"+"└───────────────────────────────┘\n"+resetColor);
                         System.out.print("Enter your Choice :");
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
                              System.out.println(redColor+"There is No Hotels to Display!"+resetColor);
                              break;
                         }

                         customer.viewHotels(hotels);
                         System.out.println();
                         break;
                    case 2:
                         System.out.println();

                         if(hotels.isEmpty()){
                              System.out.println(redColor+"There is No Hotels to Display Menu!"+resetColor);
                              break;
                         }

                         showMenu(customer);
                         System.out.println();
                         break;
                    case 3:
                         System.out.println();

                         if(hotels.isEmpty()){
                              System.out.println(redColor+"There is No Hotels to Order Food!"+resetColor);
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
                         loadAllData();
                         customer.trackOrder(this);
                         System.out.println();
                         break;
                    case 7:
                         System.out.println();
                         loadAllData();
                         customer.viewOrderHistory(this);
                         System.out.println();
                         break;
                    case 8:
                         System.out.println();
                         System.out.println(greenColor+"Thank You !\nVisit Again !"+resetColor);
                         System.out.println();
                         return;
                    default:
                         System.out.println(redColor+"Enter the Valid Option Number!"+resetColor);
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
              System.out.println(redColor+"Enter a valid Hotel ID!"+resetColor);
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
              System.out.println(redColor+"Enter a valid Hotel ID!"+resetColor);
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
                    System.out.println(redColor+"Item Not Found!"+resetColor);
                    return;
               }

               System.out.println(orderHotel.hotelName);
               customer.addToCart(orderItem, orderHotel, this);
               
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

          System.out.println("\n╔═══════════════════════╗\n║  Delivery Agent Menu  ║\n╚═══════════════════════╝\n");

          int userChoice = 0;

          while (userChoice != 7) {

               while (true) {
                    try {
                         System.out.print(cyanColor+"┌───────────────────────────────┐\n"+"│ 1. Unassigned Orders          │\n│ 2. Assigned Orders            │\n│ 3. Accept Order               │\n│ 4. Update Delivery Status     │\n│ 5. See Total Earnings         |\n| 6. Completed Orders           │\n│ 7. Logout                     │\n"+"└───────────────────────────────┘\n"+resetColor);
                         System.out.print("Enter your Choice : ");
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

                         if(orders.isEmpty()){
                              System.out.println(redColor+"There is No Order To Accept!"+resetColor);
                         } else {
                              processOrders(deliveryAgent);
                         }
                         System.out.println();
                         break;
                    case 4:
                         System.out.println();
                         updateOrder(deliveryAgent);
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
                         System.out.println(greenColor+"Thank You !\nVisit Again !"+resetColor);
                         System.out.println();
                         return;
                    default:
                         System.out.println(redColor+"Enter the Valid Option Number!"+resetColor);
               }
          }

     }


     public void processOrders(DeliveryAgent deliveryAgent){

          loadAllData();

          ArrayList<Order> unAssignedOrders = new ArrayList<>();

          unAssignedOrders.clear();
          for(int i = 0; i < orders.size(); i++){
               if(orders.get(i).deliveryAgent == null && orders.get(i).orderStatus.equalsIgnoreCase("Order Placed") && orders.get(i).hotel != null){
                    unAssignedOrders.add(orders.get(i));
               }
          }
          System.out.println(orders.get(0).hotel == null);
          if(unAssignedOrders.isEmpty()){
               System.out.println(redColor+"There is No UnAssigned Orders!"+resetColor);
               return;
          }

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

          for (Order o : orders) {
               if (o.orderId == orderId) {
                   selectedOrder = o;
                   break;
               }
          }

          if(selectedOrder == null){
               System.out.println(redColor+"Order Not Found!"+resetColor);
               return;
          }

          if(selectedOrder.deliveryAgent != null){
               System.out.println(redColor+"This order is already assigned to another agent!"+resetColor);
               return;
          }

          selectedOrder.deliveryAgent = deliveryAgent;
          selectedOrder.orderStatus = "Order Assigned to Delivery Agent";
          deliveryAgent.orders.add(selectedOrder);
          deliveryAgent.isAvailable = false;

          // Saving In File
          saveAllData();

          System.out.println(greenColor+"Order Id : "+selectedOrder.orderId+" has been Assigned Successfully!"+resetColor);
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
               System.out.println(redColor+"Order Not Found!"+resetColor);
               return;
          }

          if(targetOrder.orderStatus.equalsIgnoreCase("delivered")){
               System.out.println(redColor+"This order has already delivered!"+resetColor);
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

          

          deliveryAgent.updateDeliveryStatus(targetOrder, orderStatus, this);

          // Saving In File
          saveAllData();

     }





     // Admin Methods
     public void showAdminMenu(Admin admin, App app){
          System.out.println();

          System.out.println("\n╔═════════════════╗\n║   Admin Menu    ║\n╚═════════════════╝\n");

          int userChoice = 0;

          while (userChoice != 11) {

               while (true) {
                    try {
                         System.out.print(cyanColor+"┌───────────────────────────────────┐\n"+"│ 1. Display All Customers          │\n│ 2. Display All Hotels             │\n│ 3. Diplay All Menu                │\n│ 4. Display All DeliveryAgents     │\n│ 5. Add Hotel                      │\n│ 6. Add Menu For Hotel             │\n│ 7. Add Delivery Agent             │\n│ 8. Remove Hotel                   │\n│ 9. Remove Delivery Agent          │\n│ 10. Remove User                   │\n│ 11. Logout                        │\n"+"└───────────────────────────────────┘\n"+resetColor);
                         System.out.print("Enter your Choice : ");
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
                              System.out.println(redColor+"There is No Customers to Display!"+resetColor);
                              break;
                         }

                         admin.displayAllCustomers(app);
                         System.out.println();
                         break;
                    case 2:
                         System.out.println();

                         if(hotels.isEmpty()){
                              System.out.println(redColor+"There is No Hotels to Display!"+resetColor);
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
                              System.out.println(redColor+"Hotel Not Found!"+resetColor);
                              break;
                         }

                         admin.displayAllMenu(showMenuHotel);

                         System.out.println();
                         break;
                    case 4:
                         System.out.println();

                         if(deliveryAgents.isEmpty()){
                              System.out.println(redColor+"There is No Delivery Agents to Display!"+resetColor);
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
                                   hotelNameCheckLoop:
                                   while (true) {
                                        System.out.print("Enter the Name of the Hotel : ");
                                        hotelName = input.nextLine();

                                        if(!isValidName(hotelName)){
                                             System.out.println("Minimum 5 Letters Required for Hotel Name!");
                                             continue hotelNameCheckLoop;
                                        }

                                        for (Hotel h : hotels) {
                                             if(h.hotelName.equalsIgnoreCase(hotelName)){
                                                  System.out.println(redColor+"Hotel With Same Name Already Exists!"+resetColor);
                                                  continue hotelNameCheckLoop;
                                             }
                                        }
                                        break;
                                   }
          
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

                                   if(!isValidLocation(hotelLocation)){
                                        System.out.println(redColor+"Minimum 5 Letters Required for Location!"+resetColor);
                                        continue;
                                   }
          
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

                         System.out.println(greenColor+"Hotel Added Successfully!"+resetColor);
                         
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
                             System.out.println(redColor+"Enter a valid Hotel ID!"+resetColor);
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
                              System.out.println(redColor+"Enter the Valid Item Count!"+resetColor);
                              break;
                         }

                         if(itemCount > 5){
                              System.out.println(redColor+"You can Only add Upto 5 Items at a Time!"+resetColor);
                              break;
                         }

                         for(int i = 0; i < itemCount; i++){
                              String itemName;
                              int itemPrice;
                              String itemCategory;
                              String itemDescription;


                              while (true) {
                                   try {
                                        itemNameCheckLoop:
                                        while (true) {
                                             System.out.print("Enter the Name of the Item : ");
                                             itemName = input.nextLine();

                                             for (Item item : menuHotel.menu) {
                                                  if(item.itemName.equalsIgnoreCase(itemName)){
                                                       System.out.println(redColor+"Item With Same Name is Already Exists!"+resetColor);
                                                       continue itemNameCheckLoop;
                                                  }
                                             }

                                             break;
                                        }
               
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

                                        if(itemPrice < 0){
                                             System.out.println(redColor+"Enter the Valid Price!"+resetColor);
                                             continue;
                                        }
               
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

                              System.out.println(greenColor+itemName+" has been Successfully Added to Hotel : "+menuHotel.hotelName+resetColor);
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

                                   if(!isValidName(dName)){
                                        System.out.println(redColor+"Minimum 5 Letter Required for Name!"+resetColor);
                                        continue;
                                   }
          
                                   break;
                              } catch (InputMismatchException e) {
                                   System.out.println(invalidInputMessage);
                                   input.nextLine();
                              }
                         }

                         while (true) {
                              try {
                                   System.out.print("Enter the Password : ");
                                   dPass = System.console().readPassword().toString();
          
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

                                   if(!isValidPhoneNumber(dPhone)){
                                        System.out.println(redColor+"10 Characters need to be in Phone Number!"+resetColor);
                                        continue;
                                   }
          
                                   break;
                              } catch (InputMismatchException e) {
                                   System.out.println(invalidInputMessage);
                                   input.nextLine();
                              }
                         }

                         while (true) {
                              try {
                                   emailCheckLoop:
                                   while (true) {
                                        System.out.print("Enter the Email : ");
                                        dEmail = input.nextLine();

                                        if(!isValidAddress(dEmail)){
                                             System.out.println(redColor+"Minimum 13 Character Required for Email!"+resetColor);
                                             continue;
                                        }

                                        for (Person p : users) {
                                             if(p.email.equalsIgnoreCase(dEmail)){
                                                  System.out.println(redColor+"This Email Address is Already Taken!"+resetColor);
                                                  continue emailCheckLoop;
                                             }
                                        }

                                        break;
                                   }
          
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

                                   if(!isValidLocation(dLocation)){
                                        System.out.println(redColor+"Minimum 5 Letters Required for Location!"+resetColor);
                                        continue;
                                   }

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

                         System.out.print(greenColor+dName+" has Successfully Added as Delivery Agent!"+resetColor);
                         System.out.println();
                         break;
                    case 8:
                         System.out.println();

                         if(hotels.isEmpty()){
                              System.out.println(redColor+"There is No Hotel to Remove!"+resetColor);
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
                             System.out.println(redColor+"Enter a valid Hotel ID!"+resetColor);
                             return;
                         }

                         hotels.remove(removeHotel);
                         
                         // Save In File
                         saveAllData();

                         System.out.println(greenColor+removeHotel.hotelName+" has Successfully Removed as Hotels"+resetColor);

                         System.out.println();
                         break;
                    case 9:
                         System.out.println();

                         if(deliveryAgents.isEmpty()){
                              System.out.println(redColor+"There is No Delivery Agents to Remove!"+resetColor);
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
                              System.out.println(redColor+"Invalid Delivery Agent ID!"+resetColor);
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

                         System.out.println(greenColor+removedAgent.name+" has Successfully Removed as Delivery Agent"+resetColor);

                         System.out.println();
                         break;
                    case 10:
                         System.out.println();

                         if(customers.isEmpty()){
                              System.out.println(redColor+"There is No Customers to Remove!"+resetColor);
                              break;
                         }

                         for (Customer c : customers) {
                              System.out.println(c.displayCustomerDetails());
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
                              System.out.println(redColor+"Customer Not Found!"+resetColor);
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

                         System.out.println(greenColor+removedCustomer.name+" has been Successfully removed as Customer"+resetColor);

                         System.out.println();
                         break;
                    case 11:
                         System.out.println();
                         System.out.println(greenColor+"Thank You !\nVisit Again !"+resetColor);
                         System.out.println();
                         return;
                    default:
                         System.out.println(redColor+"Enter the Valid Option Number!"+resetColor);
               }
          }

          System.out.println();
     }



     // Saving All data
     public void loadAllData(){
          customers = db.getCustomers();
          hotels = db.getHotels();
          deliveryAgents = db.getDeliveryAgents();
          orders = db.getOrders(customers, hotels, deliveryAgents);
          admins = db.getAdmins();
          users = db.getUsers();
     }
     public void saveAllData(){
          db.saveCustomers(customers);
          db.saveDeliveryAgents(deliveryAgents);
          db.saveHotels(hotels);
          db.saveOrders(orders);
          db.saveUsers(users);
          db.saveAdmins(admins);
     }


     // Validations
     public boolean isValidName(String name){
          return name.length() > 5;
     }

     public boolean isValidEmail(String email){
          if(!email.contains("@")){
               return false;
          } else if(!email.contains(".gmail.com") || !email.contains(".zoho.com") || !email.contains(".zohocorp.com")){
               return false;
          } else if(email.length() < 13){
               return false;
          }
          
          return true;
     }

     public boolean isValidPassword(String password){
          return true;
     }

     public boolean isValidPhoneNumber(String phoneNumber){
          return phoneNumber.length() == 10;
     }
     
     public boolean isValidLocation(String location){
          return location.length() > 5;
     }

     public boolean isValidAddress(String address){
          return address.length() > 10;
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