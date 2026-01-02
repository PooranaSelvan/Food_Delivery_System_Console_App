import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

public class App{
     static Scanner input = new Scanner(System.in);
     DataBase db;

     // Text Formatting & Decorations
     String redColor = "\n\u001B[91m";
     String resetColor = "\u001B[0m\n";
     String cyanColor = "\n\u001B[96m";
     String greenColor = "\n\u001B[92m";
     String yellowColor = "\n\u001B[93m";
     String textBold = "\u001B[1m";
     String invalidInputMessage = redColor + textBold + "\nInvalid Input!\n" + resetColor;

     Logger logger = LogManager.getLogger("FoodDeliveryApp");

     public void makeConnection() throws SQLException {
         db = new DataBase();
         logger.info("Connected to The DataBase!");
     }


     public static void main(String[] args) throws SQLException {
          App app = new App();

          app.makeConnection();

          int userChoice = 0;

          // Welcome Message
          app.logger.info("Starting the Application!");
          System.out.println(app.yellowColor+app.textBold + "┌──────────────────────────────────────────────────┐\n"+
                                                            "│      Welcome to 'Suvai Now' Food Delivery App    │\n"+
                                                            "└──────────────────────────────────────────────────┘" + app.resetColor);

          while (userChoice != 3) {
               System.out.println();
              app.logger.info("User Tried to Login!");

               while (true) {
                    try {
                         System.out.print(app.cyanColor + "┌──────────────────────┐"
                                                      + "\n│   1. Login           │\n│   2. SignUp          │\n│   3. Exit            │\n");
                         System.out.println("└──────────────────────┘" + app.resetColor);
                         System.out.print("Enter your Choice : ");
                         userChoice = input.nextInt();

                         break;
                    } catch (InputMismatchException e) {
                         app.logger.error(e.getMessage());
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
                         System.out.println(
                                   "\n╔═══════════════════════╗\n║      Login Menu       ║\n╚═══════════════════════╝\n");

                         while (true) {
                              try {
                                   System.out.print("Enter the Email : ");
                                   email = input.nextLine();

                                   break;
                              } catch (InputMismatchException e) {
                                   app.logger.error(e.getMessage());
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
                                   app.logger.error(e.getMessage());
                                   System.out.println(app.invalidInputMessage);
                                   input.nextLine();
                              }
                         }

                         Person p = app.db.getUserByEmail(email);

                        if (p == null) {
                            System.out.println(app.redColor + "Invalid Email Or Password!" + app.resetColor);
                            continue;
                        }


                        if(!BCrypt.checkpw(password, p.password)){
                            System.out.println(app.redColor + "Invalid Email Or Password!" + app.resetColor);
                            continue;
                        }


                        switch (p.role.toLowerCase()){
                            case "customer":
                                Customer c = app.db.getCustomerById(p.userId);
                                if(c != null){
                                    app.logger.info("{} : Customer Logged In!", c.customerId);
                                    app.showCustomerMenu(c);
                                }
                                break;
                            case "deliveryagent":
                                DeliveryAgent d = app.db.getDeliveryAgentById(p.userId);
                                if(d != null){
                                    app.logger.info("{} : DeliveryAgent Logged In!", d.deliveryAgentId);
                                    app.showDeliveryAgentMenu(d);
                                }
                                break;
                            case "admin":
                                Admin a = app.db.getAdminByEmail(p.email);
                                if(a != null){
                                    app.logger.info("{} : Admin Logged In!", a.adminId);
                                    app.showAdminMenu(a);
                                }
                                break;
                            default:
                                app.logger.info("Login Failed!");
                                System.out.println("Unknown Login Happened!");
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
                         String confirmPassword;

                         System.out.println(
                                   "\n╔═══════════════════════╗\n║      SignUp Menu      ║\n╚═══════════════════════╝\n");

                         while (true) {
                              try {
                                   System.out.print("Enter your Name : ");
                                   cusName = input.nextLine();

                                   if (cusName.equalsIgnoreCase("")) {
                                        System.out.println(app.invalidInputMessage);
                                        continue;
                                   }

                                   break;
                              } catch (InputMismatchException e) {
                                   app.logger.error(e.getMessage());
                                   System.out.println(app.invalidInputMessage);
                                   input.nextLine();
                              }
                         }

                         while (true) {
                              try {
                                   System.out.print("Enter the Password : ");
                                   cusPassword = input.nextLine();

                                   if (!app.isValidPassword(cusPassword)) {
                                        continue;
                                   }

                                   break;
                              } catch (InputMismatchException e) {
                                   app.logger.error(e.getMessage());
                                   System.out.println(app.invalidInputMessage);
                                   input.nextLine();
                              }
                         }
                         
                         while (true) {
                              try {
                                   System.out.print("Enter the Confirm Password : ");
                                   confirmPassword = input.nextLine();
                                   
                                   if(!cusPassword.equals(confirmPassword)){
                                        System.out.println(app.redColor+"Confirm Password Doesn't Match!"+app.resetColor);
                                        continue;
                                   }
                                   
                                   break;
                              } catch (InputMismatchException e) {
                                   app.logger.error(e.getMessage());
                                   System.out.println(app.invalidInputMessage);
                                   input.nextLine();
                              }
                         }

                         while (true) {
                              try {
                                   System.out.print("Enter the Phone Number : ");
                                   cusPhoneNum = input.nextLine();

                                   if (!app.isValidPhoneNumber(cusPhoneNum)) {
                                        continue;
                                   }

                                  Person phonePerson = app.db.getUserByPhone(cusPhoneNum);

                                  if(phonePerson != null){
                                      System.out.println(app.redColor + "This Phone Number is Already Taken!" + app.resetColor);
                                      continue;
                                  }

                                   break;
                              } catch (InputMismatchException e) {
                                   app.logger.error(e.getMessage());
                                   System.out.println(app.invalidInputMessage);
                                   input.nextLine();
                              }
                         }

                         while (true) {
                              try {
                                   emailCheckLoop: while (true) {
                                        System.out.print("Enter the Email : ");
                                        cusEmail = input.nextLine();

                                        if (!app.isValidEmail(cusEmail)) {
                                             continue;
                                        }

                                        Person person = app.db.getUserByEmail(cusEmail);
                                        if(person != null){
                                            System.out.println("Email Already Exists!");
                                            continue emailCheckLoop;
                                        }

                                        break;
                                   }

                                   break;
                              } catch (InputMismatchException e) {
                                   app.logger.error(e.getMessage());
                                   System.out.println(app.invalidInputMessage);
                                   input.nextLine();
                              }
                         }

                        while (true) {
                            try {
                                System.out.print("Enter the Location : ");
                                cusLocation = input.nextLine();

                                if (!app.isValidLocation(cusLocation)) {
                                    continue;
                                }

                                break;
                            } catch (InputMismatchException e) {
                                app.logger.error(e.getMessage());
                                System.out.println(app.invalidInputMessage);
                                input.nextLine();
                            }
                        }

                         while (true) {
                              try {
                                   System.out.print("Enter the Address : ");
                                   cusAddress = input.nextLine();

                                   if (!app.isValidAddress(cusAddress)) {
                                        continue;
                                   }

                                   break;
                              } catch (InputMismatchException e) {
                                   app.logger.error(e.getMessage());
                                   System.out.println(app.invalidInputMessage);
                                   input.nextLine();
                              }
                         }

                         Customer newCustomer = new Customer(cusName, BCrypt.hashpw(cusPassword, BCrypt.gensalt(12)), cusPhoneNum, cusEmail, cusLocation, cusAddress);

                         app.db.saveCustomer(newCustomer);
                         app.logger.info("{} : new Customer Signed Up!", newCustomer.customerId);

                         // Welcome Message
                         System.out.println(
                  "\n\u001B[96m\u001B[1m" + "┌──────────────────────────────────────────────────┐");
                         System.out.println("│     Welcome to 'Suvai Now' Food Delivery App     │");
                         System.out.println("└──────────────────────────────────────────────────┘" + "\u001B[0m\n");
                         System.out.println(app.cyanColor + app.textBold + "You can Login Now!\n" + app.resetColor);
                         System.out.println();
                         break;
                    case 3:
                         System.out.println(app.greenColor + "Thank you For Visiting!" + app.resetColor);
                         app.logger.info("Application Closed!");
                         break;
                    default:
                         System.out.println(app.redColor + "Enter the Valid Choice!" + app.resetColor);
                         break;
               }
          }
     }

     // Customer Methods
     public void showCustomerMenu(Customer customer) throws SQLException {
          System.out.println();

          System.out.println("\n╔═══════════════════════╗\n║     Customer Menu     ║\n╚═══════════════════════╝\n");

          boolean isOrder = true;
          int userChoice = 0;

          while (userChoice != 8) {

               while (true) {
                    try {
                         System.out.print(cyanColor + "┌───────────────────────────────┐\n"
                                                    + "│ 1. View Hotels                │     \n│ 2. View Menu                  │     \n│ 3. Add to Cart                │     \n│ 4. View Cart                  │     \n│ 5. Place Order                │     \n│ 6. Track Order                │     \n│ 7. Order History              │     \n│ 8. Logout                     │     \n"
                                                    + "└───────────────────────────────┘\n" + resetColor);
                         System.out.print("Enter your Choice : ");
                         userChoice = input.nextInt();

                         break;
                    } catch (InputMismatchException e) {
                         logger.error(e.getMessage());
                         System.out.println(invalidInputMessage);
                         input.nextLine();
                         userChoice = 0;
                    }
               }
               input.nextLine();

               switch (userChoice) {
                    case 1:
                         System.out.println();
                         customer.viewHotels(db);
                         logger.info("{} Viewed All Hotels!", customer.customerId);
                         System.out.println();
                         break;
                    case 2:
                         System.out.println();
                         showMenu(customer);
                         logger.info("{} Viewed a Hotel Menu!", customer.customerId);
                         System.out.println();
                         break;
                    case 3:
                         System.out.println();
                         addToCart(customer, isOrder);
                         logger.info("{} Added a Menu in Cart!", customer.customerId);
                         System.out.println();
                         break;
                    case 4:
                         System.out.println();
                         customer.viewCart();
                         logger.info("{} Viewed a Cart!", customer.customerId);
                         System.out.println();
                         break;
                    case 5:
                         System.out.println();
                         customer.placeOrder(db);
                         logger.info("{} Placed a New Order!", customer.customerId);
                         System.out.println();
                         break;
                    case 6:
                         System.out.println();
                         customer.trackOrder(db);
                         logger.info("{} Tracked Their Order!", customer.customerId);
                         System.out.println();
                         break;
                    case 7:
                         System.out.println();
                         customer.viewOrderHistory(db);
                         logger.info("{} Viewed Order History!", customer.customerId);
                         System.out.println();
                         break;
                    case 8:
                         System.out.println();
                         String logoutOption = "";
                         
                         if(!customer.cart.isEmpty()){
                              while (true) {
                                   while (true) {
                                        try {
                                             System.out.print("You have unplaced orders in your cart. If you exit, they will be cleared. Exit anyway? [yes(y)/no(n)] : ");
                                             logoutOption = input.nextLine();
     
                                             if(logoutOption.equalsIgnoreCase("")){
                                                  System.out.println(invalidInputMessage);
                                                  continue;
                                             }
     
                                             break;
                                        } catch (InputMismatchException e) {
                                             logger.error(e.getMessage());
                                             System.out.println(invalidInputMessage);
                                        }
                                   }

                                   if(logoutOption.equalsIgnoreCase("yes") || logoutOption.equalsIgnoreCase("y")){
                                        customer.cart.clear();
                                        break;
                                   } else if(logoutOption.equalsIgnoreCase("no") || logoutOption.equalsIgnoreCase("n")){
                                        userChoice = 0;
                                        System.out.println(greenColor+"Place Your Order!"+resetColor);
                                        System.out.println();
                                        break;
                                   } else {
                                        System.out.println(invalidInputMessage);
                                        continue;
                                   }
                              } 
                         }

                         if(logoutOption.equalsIgnoreCase("no") || logoutOption.equalsIgnoreCase("n")){
                              break;
                         }

                         System.out.println(greenColor + "Thank You !\nVisit Again !" + resetColor);
                         logger.info(customer.customerId+" Logged Out!");
                         System.out.println();
                         return;
                    default:
                         System.out.println(redColor + "Enter the Valid Option Number!" + resetColor);
               }
          }

          System.out.println();
     }

     public void showMenu(Customer customer) throws SQLException {
         customer.viewHotels(db);
          int hotelId;

          while (true) {
               try {
                    System.out.print("Enter the Hotel Id to See the Hotel Menu : ");
                    hotelId = input.nextInt();

                    break;
               } catch (InputMismatchException e) {
                    logger.error(e.getMessage());
                    System.out.println(invalidInputMessage);
                    input.nextLine();
               }
          }
          input.nextLine();

          Hotel newHotel = db.getHotelById(hotelId);

          if (newHotel == null) {
               System.out.println(redColor + "Enter a valid Hotel ID!" + resetColor);
               return;
          }

          customer.viewMenu(newHotel);
     }

     public void addToCart(Customer customer, boolean isOrder) throws SQLException{
          customer.viewHotels(db);

          int hotelId;

          while (true) {
               try {
                    System.out.print("Enter the Hotel Id to See the Hotel Menu : ");
                    hotelId = input.nextInt();

                    break;
               } catch (InputMismatchException e) {
                    logger.error(e.getMessage());
                    System.out.println(invalidInputMessage);
                    input.nextLine();
               }
          }
          input.nextLine();

          Hotel orderHotel = db.getHotelById(hotelId);

          if (orderHotel == null) {
               System.out.println(redColor + "Enter a valid Hotel ID!" + resetColor);
               return;
          }

          boolean orderAgain = true;
          int menuNum;

          while (orderAgain) {
               customer.viewMenu(orderHotel);

               while (true) {
                    try {
                         System.out.print("Enter the Menu Number to Add to Cart : ");
                         menuNum = input.nextInt();

                         break;
                    } catch (InputMismatchException e) {
                         logger.error(e.getMessage());
                         System.out.println(invalidInputMessage);
                         input.nextLine();
                    }
               }
               input.nextLine();

               Item orderItem = null;

              for (Item item : orderHotel.menu) {
                  if (item.itemId == menuNum) {
                      orderItem = item;
                      break;
                  }
              }

               if (orderItem == null) {
                    System.out.println(redColor + "Item Not Found!" + resetColor);
                    return;
               }

               boolean orderResponse = customer.addToCart(orderItem, orderHotel);

               if(!orderResponse){
                    return;
               }

               String userChoice = "";
               while (true) {
                    try {
                         System.out.print("Do you Want to Order More? [yes(y)/no(n)] : ");
                         userChoice = input.nextLine().toLowerCase();

                         break;
                    } catch (InputMismatchException e) {
                         logger.error(e.getMessage());
                         System.out.println(invalidInputMessage);
                         input.nextLine();
                    }
               }

               if (userChoice.equalsIgnoreCase("y") || userChoice.equalsIgnoreCase("yes")) {
                    continue;
               } else {
                    orderAgain = false;
               }
          }
     }

     // Delivery Agent Methods
     public void showDeliveryAgentMenu(DeliveryAgent deliveryAgent) throws SQLException {
          System.out.println();

          System.out.println("\n╔═══════════════════════╗\n║  Delivery Agent Menu  ║\n╚═══════════════════════╝\n");

          int userChoice = 0;

          while (userChoice != 7) {

               while (true) {
                    try {
                         System.out.print(cyanColor + "┌───────────────────────────────┐\n"
                                   + "│ 1. Unassigned Orders          │\n│ 2. Assigned Orders            │\n│ 3. Accept Order               │\n│ 4. Update Delivery Status     │\n│ 5. See Total Earnings         |\n| 6. Completed Orders           │\n│ 7. Logout                     │\n"
                                   + "└───────────────────────────────┘\n" + resetColor);
                         System.out.print("Enter your Choice : ");
                         userChoice = input.nextInt();

                         break;
                    } catch (InputMismatchException e) {
                         logger.error(e.getMessage());
                         System.out.println(invalidInputMessage);
                         input.nextLine();
                         userChoice = 0;
                    }
               }
               input.nextLine();

               switch (userChoice) {
                    case 1:
                         System.out.println();
                         deliveryAgent.getUnassignedOrders(db);
                         logger.info("{} Viewed Unassigned Orders!", deliveryAgent.deliveryAgentId);
                         System.out.println();
                         break;
                    case 2:
                         System.out.println();
                         deliveryAgent.getAssignedOrders(db);
                         logger.info("{} Viewed Assigned Orders!", deliveryAgent.deliveryAgentId);
                         System.out.println();
                         break;
                    case 3:
                         System.out.println();
                         acceptOrder(deliveryAgent);
                         logger.info("{} Accepted a New Order!", deliveryAgent.deliveryAgentId);
                         System.out.println();
                         break;
                    case 4:
                         System.out.println();
                         updateOrder(deliveryAgent);
                         logger.info("{} Updated an Order!", deliveryAgent.deliveryAgentId);
                         System.out.println();
                         break;
                    case 5:
                         System.out.println();
                         System.out.println(deliveryAgent.getTotalEarnings(db));
                         logger.info("{} Viewed Total Earnings!", deliveryAgent.deliveryAgentId);
                         System.out.println();
                         break;
                    case 6:
                         System.out.println();
                         deliveryAgent.checkCompletedOrders(db);
                         logger.info("{} Viewed Completed Orders!", deliveryAgent.deliveryAgentId);
                         System.out.println();
                         break;
                    case 7:
                         System.out.println();
                         System.out.println(greenColor + "Thank You !\nVisit Again !" + resetColor);
                         logger.info(deliveryAgent.deliveryAgentId+" Logged Out!");
                         System.out.println();
                         return;
                    default:
                         System.out.println(redColor + "Enter the Valid Option Number!" + resetColor);
               }
          }

     }

     public void acceptOrder(DeliveryAgent deliveryAgent) throws SQLException {
          boolean isOrderAvailable = deliveryAgent.getUnassignedOrders(db);

          if(!isOrderAvailable){
              return;
          }

          int orderId;
          while (true) {
               try {
                    System.out.print("Enter the Order Id to Accept Order : ");
                    orderId = input.nextInt();

                    break;
               } catch (InputMismatchException e) {
                    logger.error(e.getMessage());
                    System.out.println(invalidInputMessage);
                    input.nextLine();
               }
          }
          input.nextLine();

          Order selectedOrder = db.getOrderById(orderId);

          if (selectedOrder == null) {
               System.out.println(redColor + "Order Not Found!" + resetColor);
               return;
          }

          if (selectedOrder.deliveryAgent != null) {
               System.out.println(redColor + "This order is already assigned to another agent!" + resetColor);
               return;
          }

          db.assignOrder(orderId, deliveryAgent.deliveryAgentId);

          System.out.println(greenColor + "Order Id : " + selectedOrder.orderId + " has been Assigned Successfully!" + resetColor);
     }

     public void updateOrder(DeliveryAgent deliveryAgent) throws SQLException {
          boolean isOrderAvailable = deliveryAgent.getAssignedOrders(db);

          if(!isOrderAvailable){
              return;
          }

          int orderId;
          while (true) {
               try {
                    System.out.print("Enter the Order Id to Update Order Status : ");
                    orderId = input.nextInt();

                    break;
               } catch (InputMismatchException e) {
                    logger.error(e.getMessage());
                    System.out.println(invalidInputMessage);
                    input.nextLine();
               }
          }
          input.nextLine();

          Order targetOrder = db.getOrderById(orderId);

          if(targetOrder == null){
              System.out.println(redColor+"Order Not Found"+resetColor);
              return;
          }

         if(targetOrder.deliveryAgent == null){
                int agentId = db.getDeliveryAgentByOrder(targetOrder.orderId);
                targetOrder.deliveryAgent = db.getDeliveryAgentById(agentId);
         }

          if (targetOrder.deliveryAgent == null || targetOrder.deliveryAgent.deliveryAgentId != deliveryAgent.deliveryAgentId) {
               System.out.println(redColor + "Order Not Found!" + resetColor);
               return;
          }

          if (targetOrder.orderStatus.equalsIgnoreCase("delivered")) {
               System.out.println(redColor + "This order has already delivered!" + resetColor);
               return;
          }

          String orderStatus = "";
          int userInput = 0;

          while (true) {
               try {
                    while (userInput != 1 && userInput != 2) {
                         System.out.print("Enter New Order Status : (1. Out For Delivery, 2. Delivered)\nEnter the Option Number : ");
                         userInput = input.nextInt();

                         if (userInput == 1) {
                              orderStatus = "ON_THE_WAY";
                         } else if (userInput == 2) {
                              orderStatus = "DELIVERED";
                              logger.info("{} Delivered a New Order!", deliveryAgent.deliveryAgentId);
                         } else {
                              System.out.println(invalidInputMessage);
                         }
                    }

                    break;
               } catch (InputMismatchException e) {
                    logger.error(e.getMessage());
                    System.out.println(invalidInputMessage);
                    input.nextLine();
               }
          }

//          System.out.println(orderStatus);
          deliveryAgent.updateDeliveryStatus(targetOrder, orderStatus, db);

     }

     // Admin Methods
     public void showAdminMenu(Admin admin) throws SQLException {
          System.out.println();

          int userChoice = 0;

          while (userChoice != 5) {
               System.out.println("\n╔═════════════════╗\n║   Admin Menu    ║\n╚═════════════════╝\n");
               
               while (true) {
                    try {
                         System.out.print(cyanColor + "┌─────────────────────┐\n"
                                                    + "│ 1. Hotels           │\n│ 2. Delivery Agents  │\n│ 3. Customers        │\n│ 4. Show Revenue     │\n│ 5. Logout           │\n"
                                                    + "└─────────────────────┘\n" + resetColor);
                         System.out.print("Enter your Choice : ");
                         userChoice = input.nextInt();

                         break;
                    } catch (InputMismatchException e) {
                         logger.error(e.getMessage());
                         System.out.println(invalidInputMessage);
                         input.nextLine();
                         userChoice = 0;
                    }
               }
               input.nextLine();

               switch (userChoice) {
                    case 1:
                         adminHotelMenu(admin);
                         logger.info("{} Viewed Hotel Menu!", admin.adminId);
                         break;
                    case 2:
                         adminDeliveryAgentMenu(admin);
                         logger.info("{} Viewed DeliveryAgent Menu!", admin.adminId);
                         break;
                    case 3:
                         adminCustomerMenu(admin);
                         logger.info("{} Viewed Customer Menu!", admin.adminId);
                         break;
                    case 4:
                         System.out.println();
                         admin.showRevenue(db);
                         System.out.println();
                         break;
                    case 5:
                         System.out.println();
                         System.out.println(greenColor + "Thank You !\nVisit Again !" + resetColor);
                         logger.info(admin.adminId+" Logged Out!");
                         System.out.println();
                         break;
                    default:
                         System.out.println(redColor + "Enter the Valid Option Number!" + resetColor);
               }
          }

          System.out.println();
     }

     public void adminCustomerMenu(Admin admin) throws SQLException {
          int userChoice = 0;

          System.out.println("\n╔═════════════════╗\n"+
                              "║    Customer     ║\n"+
                              "╚═════════════════╝\n");

          while (userChoice != 3) {

               while (true) {
                    try {
                         System.out.print(cyanColor + "┌─────────────────────────────────┐\n"
                                     + "│ 1. Display All Customers        │\n│ 2. Remove Customers             │\n│ 3. Back                         |\n"
                                     + "└─────────────────────────────────┘\n" + resetColor);
                         System.out.print("Enter your Choice : ");
                         userChoice = input.nextInt();

                         break;
                    } catch (InputMismatchException e) {
                         logger.error(e.getMessage());
                         System.out.println(invalidInputMessage);
                         input.nextLine();
                    }
               }
               input.nextLine();

               ArrayList<Customer> customers = db.getCustomers();

               switch (userChoice) {
                    case 1:
                         System.out.println();

                         if (customers.isEmpty()) {
                              System.out.println(redColor + "There is No Customers to Display!" + resetColor);
                              break;
                         }

                        System.out.println("╔══════════════════════════════════════════════════════════╗");
                        System.out.println("║                     ALL CUSTOMERS                        ║");
                        System.out.println("╠══════════════════════════════════════════════════════════╣");
                         admin.displayAllCustomers(db);
                         logger.info("{} Viewed All Customers!", admin.adminId);
                         System.out.println("╚══════════════════════════════════════════════════════════╝");
                         System.out.println();
                         break;
                    case 2:
                         System.out.println();

                         if (customers.isEmpty()) {
                              System.out.println(redColor + "There is No Customers to Remove!" + resetColor);
                              break;
                         }

                         for (Customer c : customers) {
                              System.out.println(c.displayDetails());
                         }

                         int customerId;
                         while (true) {
                              try {
                                   System.out.print("Enter the Customer Id to Remove : ");
                                   customerId = input.nextInt();


                                   boolean customerExists = false;

                                   for (Customer c : customers){
                                       if(c.customerId == customerId){
                                           customerExists = true;
                                           break;
                                       }
                                   }

                                   if(!customerExists){
                                       System.out.println(redColor+"Customer Id : "+customerId+"Not Found!"+resetColor);
                                       logger.error("Customer Not Found!");
                                       continue;
                                   }

                                   break;
                              } catch (InputMismatchException e) {
                                   logger.error(e.getMessage());
                                   System.out.println(invalidInputMessage);
                                   input.nextLine();
                              }
                         }
                         input.nextLine();

                         boolean confirmDelete = true;

                         while (true){
                             try {
                                 System.out.print("Are You sure Want to Delete this Customer? [y/n] : ");
                                 String deleteCustomerChoice = input.nextLine();

                                 if(deleteCustomerChoice.equalsIgnoreCase("n")){
                                     confirmDelete = false;
                                 } else {
                                     System.out.println(invalidInputMessage);
                                     continue;
                                 }

                                 break;
                             } catch (InputMismatchException e){
                                 logger.error(e.getMessage());
                                 System.out.println(invalidInputMessage);
                                 input.nextLine();
                             }
                         }

                         if(!confirmDelete){
                             System.out.println(redColor+"Customer Deletion Cancelled!"+resetColor);
                             logger.info("Deleting Customer is Cancelled");
                             break;
                         }

                         admin.removeCustomer(db, customerId);

                         System.out.println(greenColor+"Customer has been Successfully Removed!" + resetColor);
                         logger.info("{} Removed a Customer!", admin.adminId);
                         System.out.println();
                         break;
                    case 3:
//                         saveAllData();
                         break;
                    default:
                         break;
               }
          }
     }

     public void adminDeliveryAgentMenu(Admin admin) throws SQLException {
          int userChoice = 0;

          System.out.println("\n╔══════════════════════╗\n"+
                               "║     DeliveryAgent    ║\n"+
                               "╚══════════════════════╝\n");

          while (userChoice != 4) {
               while (true) {
                    try {
                         System.out.print(cyanColor + "┌─────────────────────────────────┐\n"
                                                    + "│ 1. Display All Delivery Agents  │\n│ 2. Add Delivery Agent           │\n│ 3. Remove DeliveryAgent         │\n│ 4. Back                         |\n"
                                                    + "└─────────────────────────────────┘\n" + resetColor);
                         System.out.print("Enter your Choice : ");
                         userChoice = input.nextInt();

                         break;
                    } catch (InputMismatchException e) {
                         logger.error(e.getMessage());
                         System.out.println(invalidInputMessage);
                         input.nextLine();
                    }
               }
               input.nextLine();

               switch (userChoice) {
                    case 1:
                         System.out.println();
                         admin.displayAllDeliveryAgents(db);
                         logger.info("{} Viewed All Delivery Agents!", admin.adminId);
                         System.out.println();
                         break;
                    case 2:
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

                                   if (dName.equalsIgnoreCase("")) {
                                        System.out.println(invalidInputMessage);
                                        continue;
                                   }

                                   break;
                              } catch (InputMismatchException e) {
                                   logger.error(e.getMessage());
                                   System.out.println(invalidInputMessage);
                                   input.nextLine();
                              }
                         }

                         while (true) {
                              try {
                                   System.out.print("Enter the Password : ");
                                   dPass = input.nextLine();

                                   if (!isValidPassword(dPass)) {
                                        continue;
                                   }

                                   break;
                              } catch (InputMismatchException e) {
                                   logger.error(e.getMessage());
                                   System.out.println(invalidInputMessage);
                                   input.nextLine();
                              }
                         }

                         while (true) {
                              try {
                                   System.out.print("Enter the Phone Number : ");
                                   dPhone = input.nextLine();

                                   if (!isValidPhoneNumber(dPhone)) {
                                        continue;
                                   }

                                   Person p = db.getUserByPhone(dPhone);

                                   if(p != null){
                                       System.out.println(redColor + "This Phone Number is Already Taken!"+resetColor);
                                       continue;
                                   }

                                   break;
                              } catch (InputMismatchException e) {
                                   logger.error(e.getMessage());
                                   System.out.println(invalidInputMessage);
                                   input.nextLine();
                              }
                         }

                         while (true) {
                              try {
                                   while (true) {
                                        System.out.print("Enter the Email : ");
                                        dEmail = input.nextLine();

                                        if (!isValidEmail(dEmail)) {
                                             continue;
                                        }

                                        Person p = db.getUserByEmail(dEmail);

                                        if(p != null){
                                            System.out.println(redColor + "This Email is Already Taken!"+resetColor);
                                            continue;
                                        }

                                        break;
                                   }

                                   break;
                              } catch (InputMismatchException e) {
                                   logger.error(e.getMessage());
                                   System.out.println(invalidInputMessage);
                                   input.nextLine();
                              }
                         }

                         while (true) {
                              try {
                                   System.out.print("Enter the Location : ");
                                   dLocation = input.nextLine();

                                   if (!isValidLocation(dLocation)) {
                                        continue;
                                   }

                                   break;
                              } catch (InputMismatchException e) {
                                   logger.error(e.getMessage());
                                   System.out.println(invalidInputMessage);
                                   input.nextLine();
                              }
                         }



                         DeliveryAgent d = new DeliveryAgent(dName, BCrypt.hashpw(dPass, BCrypt.gensalt(12)), dPhone, dEmail, dLocation);
                         admin.addDeliveryAgent(db, d);

                         System.out.print(greenColor + dName + " has Successfully Added as Delivery Agent!" + resetColor);
                         logger.info("{} Added new Delivery Agent!", admin.adminId);
                         System.out.println();
                         break;
                    case 3:
                         System.out.println();

                        ArrayList<DeliveryAgent> deliveryAgents = db.getDeliveryAgents();

                         for (DeliveryAgent da : deliveryAgents) {
                              System.out.println(da.viewProfile());
                         }

                         int deliveryAgentId;
                         while (true) {
                              try {
                                   System.out.print("Enter the Delivery Agent Id to Remove : ");
                                   deliveryAgentId = input.nextInt();

                                   boolean agentExists = false;

                                   for (DeliveryAgent da : deliveryAgents){
                                       if(da.deliveryAgentId == deliveryAgentId){
                                           agentExists = true;
                                           break;
                                       }
                                   }

                                   if(!agentExists){
                                       System.out.println(redColor+"DeliveryAgent Id : "+deliveryAgentId+"Not Found!"+resetColor);
                                       logger.error("DeliveryAgent Not Found!");
                                       continue;
                                   }

                                   break;
                              } catch (InputMismatchException e) {
                                   logger.error(e.getMessage());
                                   System.out.println(invalidInputMessage);
                                   input.nextLine();
                              }
                         }
                         input.nextLine();

                         boolean confirmDelete = true;

                         while (true){
                             try {
                                 System.out.print("Are You sure Want to Delete this DeliveryAgent? [y/n] : ");
                                 String deleteAgentChoice = input.nextLine();

                                 if(deleteAgentChoice.equalsIgnoreCase("n")){
                                     confirmDelete = false;
                                 } else {
                                     System.out.println(invalidInputMessage);
                                     continue;
                                 }

                                 break;
                             } catch (InputMismatchException e){
                                 logger.error(e.getMessage());
                                 System.out.println(invalidInputMessage);
                                 input.nextLine();
                             }
                         }

                         if(!confirmDelete){
                             System.out.println(redColor+"DeliveryAgent Deletion Cancelled!"+resetColor);
                             logger.info("Deleting DeliveryAgent is Cancelled");
                             break;
                         }

                         admin.removeDeliveryAgent(db, deliveryAgentId);

                         System.out.println(greenColor +"Delivery Agent has Successfully Removed!" + resetColor);
                         logger.info("{} Removed a Delivery Agent!", admin.adminId);
                         System.out.println();
                         break;
                    case 4:
                         break;
                    default:
                         break;
               }
          }
     }


     public void adminHotelMenu(Admin admin) throws SQLException {
          int userChoice = 0;

          System.out.println("\n╔══════════════╗\n"+
                               "║     Hotel    ║\n"+
                               "╚══════════════╝\n");

          while (userChoice != 7) {

               while (true) {
                    try {
                         System.out.print(cyanColor + "┌─────────────────────────────────┐\n"
                                                    + "│ 1. Display All Hotels           │\n│ 2. Display Hotel Menu           │\n│ 3. Add Hotel                    │\n│ 4. Add Menu Item for Hotel      │\n│ 5. Remove Hotel                 │\n| 6. Remove Hotel Menu            |\n| 7. Back                         |\n"
                                                    + "└─────────────────────────────────┘\n" + resetColor);
                         System.out.print("Enter your Choice : ");
                         userChoice = input.nextInt();

                         break;
                    } catch (InputMismatchException e) {
                         logger.error(e.getMessage());
                         System.out.println(invalidInputMessage);
                         input.nextLine();
                    }
               }
               input.nextLine();

               switch (userChoice) {
                    case 1:
                         System.out.println();
                         admin.displayAllHotels(db);
                         logger.info("{} Viewed a Hotel!", admin.adminId);
                         System.out.println();
                         break;
                    case 2:
                         System.out.println();
                         boolean isTHereHotel = admin.displayAllHotels(db);

                         if(!isTHereHotel){
                             break;
                         }

                         int showMenuHotelId;
                         while (true) {
                              try {
                                   System.out.print("Enter the Hotel Id to Display its Menu : ");
                                   showMenuHotelId = input.nextInt();

                                   break;
                              } catch (InputMismatchException e) {
                                   logger.error(e.getMessage());
                                   System.out.println(invalidInputMessage);
                                   input.nextLine();
                              }
                         }
                         input.nextLine();

                         Hotel showMenuHotel = db.getHotelById(showMenuHotelId);

                         if (showMenuHotel == null) {
                              System.out.println(redColor + "Hotel Not Found!" + resetColor);
                              break;
                         }

                         System.out.println("╔══════════════════════════════════════════════════════════╗");
                         System.out.println("║                         ALL MENU                         ║");
                         System.out.println("╠══════════════════════════════════════════════════════════╣");
                         admin.displayAllMenu(showMenuHotel);
                         System.out.println("╚══════════════════════════════════════════════════════════╝");
                         logger.info("{} Viewed a Hotel : {} Menu", admin.adminId, showMenuHotel.hotelName);
                         System.out.println();
                         break;
                    case 3:
                         System.out.println();
                         String hotelName;
                         String hotelLocation;

                         while (true) {
                              try {
                                  while (true) {
                                        System.out.print("Enter the Name of the Hotel : ");
                                        hotelName = input.nextLine();

                                        if (!isValidName(hotelName)) {
                                             continue;
                                        }
                                        break;
                                  }

                                   break;
                              } catch (InputMismatchException e) {
                                   logger.error(e.getMessage());
                                   System.out.println(invalidInputMessage);
                                   input.nextLine();
                              }
                         }

                         while (true) {
                              try {
                                   System.out.print("Enter the Hotel Location : ");
                                   hotelLocation = input.nextLine();

                                   if (!isValidLocation(hotelLocation)) {
                                        continue;
                                   }

                                   break;
                              } catch (InputMismatchException e) {
                                   logger.error(e.getMessage());
                                   System.out.println(invalidInputMessage);
                                   input.nextLine();
                              }
                         }

                         Hotel hotel = new Hotel(hotelName, hotelLocation);
                         admin.addHotel(db, hotel);

                         System.out.println(greenColor + "Hotel Added Successfully!" + resetColor);
                         logger.info("{} Added New Hotel!", admin.adminId);
                         System.out.println();
                         break;
                    case 4:
                         System.out.println();

                         admin.displayAllHotels(db);
                         int menuHotelId;

                         while (true) {
                              try {
                                   System.out.print("Enter the Hotel Id to Add Menu For It : ");
                                   menuHotelId = input.nextInt();

                                   break;
                              } catch (InputMismatchException e) {
                                   logger.error(e.getMessage());
                                   System.out.println(invalidInputMessage);
                                   input.nextLine();
                              }
                         }
                         input.nextLine();

                         Hotel menuHotel = db.getHotelById(menuHotelId);

                         if (menuHotel == null) {
                              System.out.println(redColor + "Enter a valid Hotel ID!" + resetColor);
                              return;
                         }

                         int itemCount;
                         while (true) {
                              try {
                                   System.out.print("How Many Menu Items You Want to Add? : ");
                                   itemCount = input.nextInt();

                                   break;
                              } catch (InputMismatchException e) {
                                   logger.error(e.getMessage());
                                   System.out.println(invalidInputMessage);
                                   input.nextLine();
                              }
                         }
                         input.nextLine();

                         if (itemCount < 0) {
                              System.out.println(redColor + "Enter the Valid Item Count!" + resetColor);
                              break;
                         }

                         if (itemCount > 5) {
                              System.out.println(redColor + "You can Only add Upto 5 Items at a Time!" + resetColor);
                              break;
                         }

                         for (int i = 0; i < itemCount; i++) {
                              String itemName;
                              int itemPrice;
                              String itemCategory;
                              String itemDescription;

                              while (true) {
                                   try {
                                        while (true) {
                                             System.out.print("Enter the Name of the Item : ");
                                             itemName = input.nextLine();

                                             if(itemName.equalsIgnoreCase("")){
                                                  System.out.println(invalidInputMessage);
                                                  continue;
                                             }

                                             break;
                                        }

                                        break;
                                   } catch (InputMismatchException e) {
                                        logger.error(e.getMessage());
                                        System.out.println(invalidInputMessage);
                                        input.nextLine();
                                   }
                              }

                              while (true) {
                                   try {
                                        System.out.print("Enter the Price of the Item : ");
                                        itemPrice = input.nextInt();

                                        if (itemPrice < 0) {
                                             System.out.println(redColor + "Enter the Valid Price!" + resetColor);
                                             continue;
                                        }

                                        if(itemPrice > 10000){
                                             System.out.println(redColor+"Maximum Price Limit is 10k"+resetColor);
                                             continue;
                                        }

                                        break;
                                   } catch (InputMismatchException e) {
                                        logger.error(e.getMessage());
                                        System.out.println(invalidInputMessage);
                                        input.nextLine();
                                   }
                              }
                              input.nextLine();

                              while (true) {
                                   try {
                                        String userInput = "";
                                        System.out.print("Enter the Item's Category [Veg(v) / Non-Veg(nv) / Drinks / Snacks] : ");
                                        userInput = input.nextLine().toLowerCase();

                                        if(!userInput.equalsIgnoreCase("nv") && !userInput.equalsIgnoreCase("v") && !userInput.equalsIgnoreCase("veg") && !userInput.equalsIgnoreCase("non-veg") && !userInput.equalsIgnoreCase("non veg") && !userInput.equalsIgnoreCase("drinks") && !userInput.equalsIgnoreCase("snacks")){
                                             System.out.println(redColor+"Enter the Valid Option!"+resetColor);
                                             continue;
                                        }

                                        if(userInput.equalsIgnoreCase("V") || userInput.equalsIgnoreCase("veg")){
                                            itemCategory = "VEG";
                                        } else if(userInput.equalsIgnoreCase("nv" ) || userInput.equalsIgnoreCase("non veg")){
                                            itemCategory = "NON_VEG";
                                        } else if(userInput.equalsIgnoreCase("snacks")){
                                            itemCategory = "SNACKS";
                                        } else if(userInput.equalsIgnoreCase("drinks")){
                                            itemCategory = "DRINKS";
                                        } else {
                                            itemCategory = "VEG";
                                        }

                                        break;
                                   } catch (InputMismatchException e) {
                                        logger.error(e.getMessage());
                                        System.out.println(invalidInputMessage);
                                        input.nextLine();
                                   }
                              }

                              while (true) {
                                   try {
                                        System.out.print("Enter the Item's Description : ");
                                        itemDescription = input.nextLine();

                                        if(itemDescription.length() < 5){
                                             System.out.println(redColor+"Minimum 5 Letters for Description!\nGive a Big Description!"+resetColor);
                                             continue;
                                        }

                                        if(itemDescription.length() > 50){
                                             System.out.println(redColor+"Maximum 20 Letters for Description!\nGive a Short Description!"+resetColor);
                                             continue;
                                        }

                                        break;
                                   } catch (InputMismatchException e) {
                                        logger.error(e.getMessage());
                                        System.out.println(invalidInputMessage);
                                        input.nextLine();
                                   }
                              }

                              Item newItem = new Item(itemName, itemPrice, itemCategory, itemDescription);
                              menuHotel.addFoodItem(newItem);
                              boolean isMenuSaved = db.saveItem(newItem, menuHotelId);

                              if(isMenuSaved){
                                  System.out.println(greenColor + itemName + " has been Successfully Added to Hotel : "+menuHotel.hotelName + resetColor);
                              }
                         }

                         logger.info(admin.adminId+"Added New Menu for a Hotel!");
                         System.out.println();
                         break;
                    case 5:
                         System.out.println();

                        ArrayList<Hotel> hotels = db.getHotels();

                         if (hotels.isEmpty()) {
                              System.out.println(redColor + "There is No Hotel to Remove!" + resetColor);
                              break;
                         }

                         for (Hotel h : hotels) {
                              System.out.println(h.displayDetails());
                         }

                         int hotelId;
                         while (true) {
                              try {
                                   System.out.print("Enter the Hotel Id to Remove : ");
                                   hotelId = input.nextInt();

                                   break;
                              } catch (InputMismatchException e) {
                                   logger.error(e.getMessage());
                                   System.out.println(invalidInputMessage);
                                   input.nextLine();
                              }
                         }
                         input.nextLine();

                         Hotel removingHotel = db.getHotelById(hotelId);

                         if (removingHotel == null) {
                              System.out.println(redColor + "Enter a valid Hotel ID!" + resetColor);
                              return;
                         }

                         boolean confirmDelete = true;

                         while (true){
                             try {
                                 System.out.print("Are You sure Want to Delete this Hotel? [y/n] : ");
                                 String deleteHotelChoice = input.nextLine();

                                 if(deleteHotelChoice.equalsIgnoreCase("n")){
                                     confirmDelete = false;
                                 } else if(deleteHotelChoice.equalsIgnoreCase("y")){
                                     confirmDelete = true;
                                 }
                                 else {
                                     System.out.println(invalidInputMessage);
                                     continue;
                                 }

                                 break;
                             } catch (InputMismatchException e){
                                 logger.error(e.getMessage());
                                 System.out.println(invalidInputMessage);
                                 input.nextLine();
                             }
                         }

                         if(!confirmDelete){
                             System.out.println(redColor+"Hotel Deletion Cancelled!"+resetColor);
                             logger.info("Deleting Hotel is Cancelled");
                             break;
                         }

                         admin.removeHotel(db, removingHotel.hotelId);

                         System.out.println(greenColor + removingHotel.hotelName + " has Successfully Removed from Hotels"+ resetColor);
                         logger.info("{} Removed a Hotel!", admin.adminId);
                         System.out.println();
                         break;
                    case 6:
                         System.out.println();
                         boolean isThereHotel = admin.displayAllHotels(db);

                         if(!isThereHotel){
                             break;
                         }

                         int removeMenuHotelId;
                         while (true) {
                              try {
                                   System.out.print("Enter the Hotel Id to Display its Menu Item: ");
                                   removeMenuHotelId = input.nextInt();

                                   break;
                              } catch (InputMismatchException e) {
                                   logger.error(e.getMessage());
                                   System.out.println(invalidInputMessage);
                                   input.nextLine();
                              }
                         }
                         input.nextLine();

                         Hotel removeMenuHotel = db.getHotelById(removeMenuHotelId);

                         if (removeMenuHotel == null) {
                              System.out.println(redColor + "Hotel Not Found!" + resetColor);
                              break;
                         }

                         System.out.println("==========================================================");
                         System.out.println(greenColor + "All Menu of Hotel " + removeMenuHotel.hotelName + resetColor);
                         admin.displayAllMenu(removeMenuHotel);
                         System.out.println("==========================================================");

                         int removeMenuId;

                         while (true){
                             try {
                                 System.out.print("Enter the Menu Id to Remove the Menu from the Hotel : ");
                                 removeMenuId = input.nextInt();

                                 break;
                             } catch (InputMismatchException e){
                                 logger.error(e.getMessage());
                                 System.out.println(invalidInputMessage);
                                 input.nextLine();
                             }
                         }
                         input.nextLine();

                         Item removeHotelMenu = db.getItemById(removeMenuId);

                         if (removeHotelMenu == null) {
                              System.out.println(redColor + "Menu Item with ID " + removeMenuId + " not found!" + resetColor);
                              break;
                         }

                        boolean confirmMenuDelete = true;

                        while (true){
                            try {
                                System.out.print("Are You sure Want to Delete this Hotel? [y/n] : ");
                                String deleteHotelMenuChoice = input.nextLine();

                                if(deleteHotelMenuChoice.equalsIgnoreCase("n")){
                                    confirmMenuDelete = false;
                                } else if(deleteHotelMenuChoice.equalsIgnoreCase("y")){
                                    confirmMenuDelete = true;
                                }
                                else {
                                    System.out.println(invalidInputMessage);
                                    continue;
                                }

                                break;
                            } catch (InputMismatchException e){
                                logger.error(e.getMessage());
                                System.out.println(invalidInputMessage);
                                input.nextLine();
                            }
                        }

                        if(!confirmMenuDelete){
                            System.out.println(redColor+"Hotel Menu Deletion Cancelled!"+resetColor);
                            logger.info("Deleting Hotel Menu is Cancelled");
                            break;
                        }

                         removeMenuHotel.removeFoodItem(removeHotelMenu);
                         db.deleteItem(removeMenuId, removeMenuHotelId);

                         System.out.println(removeHotelMenu.itemName+" has removed Successfully from Hotel "+removeMenuHotel.hotelName);
                         logger.info("{} Removed a Hotel Menu!", admin.adminId);
                         System.out.println();
                         break;
                    case 7:
                         break;     
                    default:
                         System.out.println(redColor+"Enter the Valid Option!"+resetColor);
               }
          }
     }

     // Validations
     public boolean isValidName(String name) {
          if (name.length() < 5) {
               System.out.println(
                         "\u001B[91m╭────────────────────────────────────────────╮\n" +
                                   "│      Name must be greater than 5 letters   │\n" +
                                   "╰────────────────────────────────────────────╯\u001B[0m");
               return false;
          }

          return true;
     }

     public boolean isValidEmail(String email) {

          if(email.indexOf("@") <= 0){
               System.out.println(
                         "\u001B[91m╭───────────────────────────────────────╮\n" +
                                   "│         Invalid Email Format          │\n" +
                                   "╰───────────────────────────────────────╯\u001B[0m");
               return false;
          }

          if(email.indexOf(".") <= 0){
               System.out.println(
                         "\u001B[91m╭───────────────────────────────────────╮\n" +
                                   "│         Invalid Email Format          │\n" +
                                   "╰───────────────────────────────────────╯\u001B[0m");
               return false;
          }

          if(email.indexOf("@") != email.lastIndexOf("@")){
               System.out.println(
                         "\u001B[91m╭───────────────────────────────────────╮\n" +
                                   "│         Contains Multiple @           │\n" +
                                   "╰───────────────────────────────────────╯\u001B[0m");
               return false;
          }

          if(email.indexOf(".") != email.lastIndexOf(".")){
               System.out.println(
                         "\u001B[91m╭───────────────────────────────────────╮\n" +
                                   "│         Contains Multiple .           │\n" +
                                   "╰───────────────────────────────────────╯\u001B[0m");
               return false;
          }

          if(email.startsWith(".") || email.endsWith(".")){
               System.out.println(
                         "\u001B[91m╭───────────────────────────────────────╮\n" +
                                   "│         Invalid Email Format          │\n" +
                                   "╰───────────────────────────────────────╯\u001B[0m");
               return false;
          }
          
          String[] emailArr = email.split("@");

          if(emailArr[0].length() < 2){
               System.out.println(
                    "\u001B[91m╭───────────────────────────────────────╮\n" +
                              "│         Invalid Email Format          │\n" +
                              "╰───────────────────────────────────────╯\u001B[0m");
               return false;
          }

          return true;
     }

     public boolean isValidPassword(String password) {

          if (password.length() < 6) {
               System.out.println(
                         "\u001B[91m╭────────────────────────────────────────────╮\n" +
                                   "│     Password must be at least 6 characters │\n" +
                                   "╰────────────────────────────────────────────╯\u001B[0m");
               return false;
          }

          if (!password.matches(".*[A-Z].*")) {
               System.out.println(
                         "\u001B[91m╭────────────────────────────────────────────╮\n" +
                                   "│  Password must contain an uppercase letter │\n" +
                                   "╰────────────────────────────────────────────╯\u001B[0m");
               return false;
          }

          if (!password.matches(".*[0-9].*")) {
               System.out.println(
                         "\u001B[91m╭────────────────────────────────────────────╮\n" +
                                   "│        Password must contain a number      │\n" +
                                   "╰────────────────────────────────────────────╯\u001B[0m");
               return false;
          }

          if (!password.matches(".*[!@#$%^&()_+\\-={}\\[\\]|:;\"'<>,.?/].*")) {
               System.out.println(
                         "\u001B[91m╭────────────────────────────────────────────╮\n" +
                                   "│    Password must contain a special symbol  │\n" +
                                   "╰────────────────────────────────────────────╯\u001B[0m");
               return false;
          }

          return true;
     }

     public boolean isValidPhoneNumber(String phoneNumber) {
          if (phoneNumber.length() != 10) {
               System.out.println(
                         "\u001B[91m╭────────────────────────────────────────────╮\n" +
                                   "│        Phone number must be 10 digits      │\n" +
                                   "╰────────────────────────────────────────────╯\u001B[0m");
               return false;
          }
          return true;
     }

     public boolean isValidLocation(String location) {
          if (location.length() <= 2) {
               System.out.println(
                         "\u001B[91m╭────────────────────────────────────────────╮\n" +
                                   "│       Location must be more than 2 letters │\n" +
                                   "╰────────────────────────────────────────────╯\u001B[0m");
               return false;
          }
          return true;
     }

     public boolean isValidAddress(String address) {
          if (address.length() <= 5) {
               System.out.println(
                         "\u001B[91m╭────────────────────────────────────────────╮\n" +
                                   "│      Address must be more than 5 letters   │\n" +
                                   "╰────────────────────────────────────────────╯\u001B[0m");
               return false;
          }
          return true;
     }
}