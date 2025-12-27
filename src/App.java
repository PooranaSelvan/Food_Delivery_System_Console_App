import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {
     static Scanner input = new Scanner(System.in);
     DataBase db;

     // Text Formatting & Decorations
     String redColor = "\n\u001B[91m";
     String resetColor = "\u001B[0m\n";
     String cyanColor = "\n\u001B[96m";
     String greenColor = "\n\u001B[92m";
     String yellowColor = "\n\u001B[93m";
     String topLine = "┌──────────────────────────────────────────────────┐";
     String bottomLine = "└──────────────────────────────────────────────────┘";
     String sideLine = "│";
     String textBold = "\u001B[1m";

     // Error Messages
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

                        if(!p.password.equals(password)){
                            System.out.println(app.redColor + "Invalid Email Or Password!" + app.resetColor);
                            continue;
                        }


                        switch (p.role.toLowerCase()){
                            case "customer":
                                Customer c = app.db.getCustomerById(p.userId);
                                if(c != null){
                                    app.logger.info(c.name+" : Customer Logged In!");
                                    app.showCustomerMenu(c);
                                }
                                break;
                            case "deliveryagent":
                                DeliveryAgent d = app.db.getDeliveryAgentById(p.userId);
                                if(d != null){
                                    app.logger.info(d.name+" : DeliveryAgent Logged In!");
                                    app.showDeliveryAgentMenu(d);
                                }
                                break;
                            case "admin":
                                Admin a = app.db.getAdminByEmail(p.email);
                                if(a != null){
                                    app.logger.info(a.name+" : Admin Logged In!");
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

                         Customer newCustomer = new Customer(cusName, cusPassword, cusPhoneNum, cusEmail, cusLocation, cusAddress);

                         app.db.saveCustomer(newCustomer);
                        app.logger.info(newCustomer.name+" : new Customer Signed Up!");

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
                         app.logger.info("User Exited!");
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
                         logger.info(customer.name+" Viewed All Hotels!");
                         System.out.println();
                         break;
                    case 2:
                         System.out.println();
                         showMenu(customer);
                         logger.info(customer.name+" Viewed a Hotel Menu!");
                         System.out.println();
                         break;
                    case 3:
                         System.out.println();
                         showMenuNaddCart(customer, isOrder);
                         logger.info(customer.name+" Added a Menu in Cart!");
                         System.out.println();
                         break;
                    case 4:
                         System.out.println();
                         customer.viewCart();
                         logger.info(customer.name+" Viewed a Cart!");
                         System.out.println();
                         break;
                    case 5:
                         System.out.println();
                         customer.placeOrder(db);
                         logger.info(customer.name+" Placed a New Order!");
                         System.out.println();
                         break;
                    case 6:
                         System.out.println();
                         customer.trackOrder(db);
                         logger.info(customer.name+" Tracked Their Order!");
                         System.out.println();
                         break;
                    case 7:
                         System.out.println();
                         customer.viewOrderHistory(db);
                         logger.info(customer.name+" Viewed Order History!");
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

     public void showMenuNaddCart(Customer customer, boolean isOrder) throws SQLException {
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
                         System.out.println();
                         break;
                    case 2:
                         System.out.println();
                         deliveryAgent.getAssignedOrders(db);
                         System.out.println();
                         break;
                    case 3:
                         System.out.println();
                         acceptOrder(deliveryAgent);
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
                         deliveryAgent.checkCompletedOrders(db);
                         System.out.println();
                         break;
                    case 7:
                         System.out.println();
                         System.out.println(greenColor + "Thank You !\nVisit Again !" + resetColor);
                         System.out.println();
                         return;
                    default:
                         System.out.println(redColor + "Enter the Valid Option Number!" + resetColor);
               }
          }

     }

     public void acceptOrder(DeliveryAgent deliveryAgent) throws SQLException {
          deliveryAgent.getUnassignedOrders(db);

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
          deliveryAgent.getAssignedOrders(db);

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

          while (userChoice != 4) {
               System.out.println("\n╔═════════════════╗\n║   Admin Menu    ║\n╚═════════════════╝\n");
               
               while (true) {
                    try {
                         System.out.print(cyanColor + "┌─────────────────────┐\n"
                                                    + "│ 1. Hotels           │\n│ 2. Delivery Agents  │\n│ 3. Customers        │\n│ 4. Logout           │\n"
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
                         break;
                    case 2:
                         adminDeliveryAgentMenu(admin);
                         break;
                    case 3:
                         adminCustomerMenu(admin);
                         break;
                    case 4:
                         System.out.println();
                         System.out.println(greenColor + "Thank You !\nVisit Again !" + resetColor);
                         System.out.println();
                         return;
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

                         System.out.println("==========================================================");
                         System.out.println(greenColor + "All Customers : " + resetColor);
                         admin.displayAllCustomers(db);
                         System.out.println("==========================================================");
                         System.out.println();
                         break;
                    case 2:
                         System.out.println();

                         if (customers.isEmpty()) {
                              System.out.println(redColor + "There is No Customers to Remove!" + resetColor);
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

                         admin.removeCustomer(db, customerId);

                         System.out.println(greenColor+"Customer has been Successfully removed as Customer" + resetColor);
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

                                   break;
                              } catch (InputMismatchException e) {
                                   logger.error(e.getMessage());
                                   System.out.println(invalidInputMessage);
                                   input.nextLine();
                              }
                         }

                         while (true) {
                              try {
                                   emailCheckLoop: while (true) {
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



                         DeliveryAgent d = new DeliveryAgent(dName, dPass, dPhone, dEmail, dLocation);
                         admin.addDeliveryAgent(db, d);

                         System.out.print(greenColor + dName + " has Successfully Added as Delivery Agent!" + resetColor);
                         System.out.println();
                         break;
                    case 3:
                         System.out.println();

                        ArrayList<DeliveryAgent> deliveryAgents = db.getDeliveryAgents();

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
                                   logger.error(e.getMessage());
                                   System.out.println(invalidInputMessage);
                                   input.nextLine();
                              }
                         }
                         input.nextLine();

                         admin.removeDeliveryAgent(db, deliveryAgentId);

                         System.out.println(greenColor +"Delivery Agent has Successfully Removed !" + resetColor);
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

                         System.out.println("==========================================================");
                         System.out.println(greenColor + "All Hotels : " + resetColor);
                         admin.displayAllHotels(db);
                         System.out.println("==========================================================");
                         System.out.println();
                         break;
                    case 2:
                         System.out.println();
                         admin.displayAllHotels(db);

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

                         System.out.println("==========================================================");
                         System.out.println(greenColor + "All Menu of Hotel " + showMenuHotel.hotelName + resetColor);
                         admin.displayAllMenu(showMenuHotel);
                         System.out.println("==========================================================");
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

                                        if(itemDescription.length() > 20){
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
                              db.saveItem(newItem, menuHotelId);

                              System.out.println(greenColor + itemName + " has been Successfully Added to Hotel : "+menuHotel.hotelName + resetColor);
                         }

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
                              System.out.println(h.getHotelDetails());
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

                         admin.removeHotel(db, removingHotel.hotelId);

                         System.out.println(greenColor + removingHotel.hotelName + " has Successfully Removed from Hotels"+ resetColor);

                         System.out.println();
                         break;
                    case 6:
                         System.out.println();
                         admin.displayAllHotels(db);

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

                         Item removeHotelMenu = db.getItemById(removeMenuId);

                         if (removeHotelMenu == null) {
                              System.out.println(redColor + "Menu Item with ID " + removeMenuId + " not found!" + resetColor);
                              break;
                         }

                         removeMenuHotel.removeFoodItem(removeHotelMenu);
                         db.deleteItem(removeMenuId);

                         System.out.println(removeHotelMenu.itemName+" has removed Successfully from Hotel "+removeMenuHotel.hotelName);
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