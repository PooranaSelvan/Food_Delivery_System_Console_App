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
     String yellowColor = "\n\u001B[93m";
     String topLine = "┌──────────────────────────────────────────────────┐";
     String bottomLine = "└──────────────────────────────────────────────────┘";
     String sideLine = "│";
     String textBold = "\u001B[1m";

     // Error Messages
     String invalidInputMessage = redColor + textBold + "\nInvalid Input!\n" + resetColor;

     public static void main(String[] args) {
          App app = new App();
          // Load Users
          app.loadAllData();

          if (app.users.isEmpty()) {
               // Users
               Admin a = new Admin("Poorana Selvan", "poorana@123", "1234567890", "pooranaselvan1@gmail.com",
                         "Tenkasi");
               DeliveryAgent d1 = new DeliveryAgent("Ramu", "ramu@123", "1234567890", "ramu123@gmail.com", "Tenkasi");
               DeliveryAgent d2 = new DeliveryAgent("Somu", "somu@123", "1234567890", "somu123@gmail.com", "Tenkasi");
               Customer c = new Customer("Vijay", "vijay@123", "1234567890", "vijay123@gmail.com", "Tenkasi",
                         "North Street, Tenkasi");

               app.admins.add(a);
               app.users.add(a);

               app.deliveryAgents.add(d1);
               app.users.add(d1);
               app.deliveryAgents.add(d2);
               app.users.add(d2);

               app.customers.add(c);
               app.users.add(c);

               // Hotel Menu - Hotel 1
               Item i1 = new Item("Idly", 20, "Veg", "Idly is soft and healthy");
               Item i2 = new Item("Dosa", 70, "Veg", "Crispy dosa tastes amazingly fresh");
               Item i3 = new Item("Poori", 30, "Veg", "Poori makes breakfast feel special");
               Item i4 = new Item("Vada", 10, "Veg", "Everyone enjoys freshly fried vada");
               Item i5 = new Item("Chicken Curry", 120, "Non Veg", "Aromatic chicken kolambu enhances meals");
               Item i6 = new Item("Egg Curry", 100, "Non Veg", "Everyone enjoys flavorful egg curry");

               // Hotel 2
               Item i7 = new Item("Coffee", 15, "Drinks", "Coffee makes mornings feel alive");
               Item i8 = new Item("Tea", 15, "Drinks", "Hot tea refreshes the mind");
               Item i9 = new Item("Butter Biscuit", 10, "Snacks", "Tea suits well with biscuits");
               Item i10 = new Item("Cold Coffee", 100, "Drinks", "Smooth cold coffee energizes me");

               Hotel h1 = new Hotel("Tiffin Terror House", "Tenkasi");
               h1.addFoodItem(i1);
               h1.addFoodItem(i2);
               h1.addFoodItem(i3);
               h1.addFoodItem(i4);
               h1.addFoodItem(i5);
               h1.addFoodItem(i6);
               app.hotels.add(h1);

               Hotel h2 = new Hotel("Filter House", "Tenkasi");
               h2.addFoodItem(i7);
               h2.addFoodItem(i8);
               h2.addFoodItem(i9);
               h2.addFoodItem(i10);
               app.hotels.add(h2);
          }

          int userChoice = 0;

          // Welcome Message
          System.out.println(app.yellowColor+app.textBold + "┌──────────────────────────────────────────────────┐\n"+
                                                            "│      Welcome to 'Suvai Now' Food Delivery App    │\n"+
                                                            "└──────────────────────────────────────────────────┘" + app.resetColor);

          while (userChoice != 3) {
               System.out.println();
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
                                   char c[] = System.console().readPassword();
                                   password = new String(c);

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

                         if (app.users.isEmpty()) {
                              System.out.println(
                                        app.redColor + "There is No Users To Login!\nStart SignUp!" + app.resetColor);
                              break;
                         }

                         for (int i = 0; i < app.users.size(); i++) {
                              // System.out.println("Coming Loop!");
                              if (app.users.get(i).email.equalsIgnoreCase(email)) {
                                   userFound = true;
                                   person = app.users.get(i);

                                   if (person.role.equalsIgnoreCase("customer")) {
                                        if (app.findCustomer(person) != null) {
                                             customer = app.findCustomer(person);
                                             userRole = "customer";
                                        }
                                   } else if (person.role.equalsIgnoreCase("deliveryagent")) {
                                        if (app.findDeliveryAgent(person) != null) {
                                             deliveryAgent = app.findDeliveryAgent(person);
                                             userRole = "deliveryagent";
                                        }
                                   } else if (person.role.equalsIgnoreCase("admin")) {
                                        if (app.findAdmin(person) != null) {
                                             admin = app.findAdmin(person);
                                             userRole = "admin";
                                        }
                                   }
                                   break;
                              }
                         }

                         if (!userFound) {
                              System.out.println(app.redColor + "Invalid Email Or Password!" + app.resetColor);
                         } else {
                              if (userRole.equalsIgnoreCase("customer")) {
                                   if (customer.login(email, password)) {
                                        app.showCustomerMenu(customer);
                                   } else {
                                        System.out.println(app.redColor + "Invalid Credentials" + app.resetColor);
                                   }
                              } else if (userRole.equalsIgnoreCase("deliveryagent")) {
                                   if (deliveryAgent.login(email, password)) {
                                        app.showDeliveryAgentMenu(deliveryAgent);
                                   } else {
                                        System.out.println(app.redColor + "Invalid Credentials" + app.resetColor);
                                   }
                              } else if (userRole.equalsIgnoreCase("admin")) {
                                   if (admin.login(email, password)) {
                                        app.showAdminMenu(admin, app);
                                   } else {
                                        System.out.println(app.redColor + "Invalid Credentials" + app.resetColor);
                                   }
                              } else {
                                   System.out.println(app.redColor + "Unknown Role!" + app.resetColor);
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
                                   System.out.println(app.invalidInputMessage);
                                   input.nextLine();
                              }
                         }

                         while (true) {
                              try {
                                   System.out.print("Enter the Password : ");
                                   char c[] = System.console().readPassword();
                                   cusPassword = new String(c);

                                   if (!app.isValidPassword(cusPassword)) {
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
                                   System.out.print("Enter the Confirm Password : ");
                                   char c[] = System.console().readPassword();
                                   confirmPassword = new String(c);
                                   
                                   if(!cusPassword.equals(confirmPassword)){
                                        System.out.println(app.redColor+"Confirm Password Doesn't Match!"+app.resetColor);
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
                                   System.out.print("Enter the Phone Number : ");
                                   cusPhoneNum = input.nextLine();

                                   if (!app.isValidPhoneNumber(cusPhoneNum)) {
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
                                   emailCheckLoop: while (true) {
                                        System.out.print("Enter the Email : ");
                                        cusEmail = input.nextLine();

                                        if (!app.isValidEmail(cusEmail)) {
                                             continue;
                                        }

                                        for (Person u : app.users) {
                                             if (u.email.equalsIgnoreCase(cusEmail)) {
                                                  System.out.println(app.redColor + "This Email is Already Taken!"
                                                            + app.resetColor);
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

                                   if (!app.isValidLocation(cusLocation)) {
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

                                   if (!app.isValidAddress(cusAddress)) {
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
                         System.out.println(
                  "\n\u001B[96m\u001B[1m" + "┌──────────────────────────────────────────────────┐");
                         System.out.println("│     Welcome to 'Suvai Now' Food Delivery App     │");
                         System.out.println("└──────────────────────────────────────────────────┘" + "\u001B[0m\n");
                         System.out.println(app.cyanColor + app.textBold + "You can Login Now!\n" + app.resetColor);
                         System.out.println();
                         break;
                    case 3:
                         System.out.println(app.greenColor + "Thank you For Visiting!" + app.resetColor);

                         // Save data
                         app.saveAllData();
                         break;
                    default:
                         System.out.println(app.redColor + "Enter the Valid Choice!" + app.resetColor);
                         break;
               }
          }
     }

     // Customer Methods
     public void showCustomerMenu(Customer customer) {
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

                         if (hotels.isEmpty()) {
                              System.out.println(redColor + "There is No Hotels to Display!" + resetColor);
                              break;
                         }

                         customer.viewHotels(hotels);
                         System.out.println();
                         break;
                    case 2:
                         System.out.println();

                         if (hotels.isEmpty()) {
                              System.out.println(redColor + "There is No Hotels to Display Menu!" + resetColor);
                              break;
                         }

                         showMenu(customer);
                         System.out.println();
                         break;
                    case 3:
                         System.out.println();

                         if (hotels.isEmpty()) {
                              System.out.println(redColor + "There is No Hotels to Order Food!" + resetColor);
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
                         customer.trackOrder(this);
                         System.out.println();
                         break;
                    case 7:
                         System.out.println();
                         customer.viewOrderHistory(this);
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
                         saveAllData();
                         System.out.println();
                         return;
                    default:
                         System.out.println(redColor + "Enter the Valid Option Number!" + resetColor);
               }
          }

          System.out.println();
     }

     public void showMenu(Customer customer) {
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
               System.out.println(redColor + "Enter a valid Hotel ID!" + resetColor);
               return;
          }

          newHotel.displayMenu();
     }

     public void showMenuNaddCart(Customer customer, boolean isOrder) {
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
               System.out.println(redColor + "Enter a valid Hotel ID!" + resetColor);
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

               for (int i = 0; i < orderHotel.menu.size(); i++) {
                    if (orderHotel.menu.get(i).itemId == menuNum) {
                         orderItem = orderHotel.menu.get(i);
                         break;
                    }
               }

               if (orderItem == null) {
                    System.out.println(redColor + "Item Not Found!" + resetColor);
                    return;
               }

               boolean orderResponse = customer.addToCart(orderItem, orderHotel, this);

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
     public void showDeliveryAgentMenu(DeliveryAgent deliveryAgent) {
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

                         if (orders.isEmpty()) {
                              System.out.println(redColor + "There is No Order To Accept!" + resetColor);
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
                         System.out.println(greenColor + "Thank You !\nVisit Again !" + resetColor);
                         saveAllData();
                         System.out.println();
                         return;
                    default:
                         System.out.println(redColor + "Enter the Valid Option Number!" + resetColor);
               }
          }

     }

     public void processOrders(DeliveryAgent deliveryAgent) {

          loadAllData();

          ArrayList<Order> unAssignedOrders = new ArrayList<>();

          unAssignedOrders.clear();
          for (int i = 0; i < orders.size(); i++) {
               if (orders.get(i).deliveryAgent == null && orders.get(i).orderStatus.equalsIgnoreCase("Order Placed") && orders.get(i).hotel != null) {
                    unAssignedOrders.add(orders.get(i));
               }
          }

          if (unAssignedOrders.isEmpty()) {
               System.out.println(redColor + "There is No UnAssigned Orders!" + resetColor);
               return;
          }

          for (int i = 0; i < unAssignedOrders.size(); i++) {
               Order order = unAssignedOrders.get(i);
               if(order != null){
                    System.out.println(greenColor+"│ Order Id : "+order.orderId+resetColor+" │ Customer Name : "+order.customer.name+" │ Order Status : "+order.orderStatus+greenColor+" │ Total Amount : "+order.totalAmount+resetColor);
               }
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

          if (selectedOrder == null) {
               System.out.println(redColor + "Order Not Found!" + resetColor);
               return;
          }

          if (selectedOrder.deliveryAgent != null) {
               System.out.println(redColor + "This order is already assigned to another agent!" + resetColor);
               return;
          }

          selectedOrder.deliveryAgent = deliveryAgent;
          selectedOrder.orderStatus = "Order Assigned to Delivery Agent";
          deliveryAgent.orders.add(selectedOrder);
          deliveryAgent.isAvailable = false;

          // Saving In File
          saveAllData();

          System.out.println(greenColor + "Order Id : " + selectedOrder.orderId + " has been Assigned Successfully!"
                    + resetColor);
     }

     public void updateOrder(DeliveryAgent deliveryAgent) {
          for (int i = 0; i < deliveryAgent.orders.size(); i++) {
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

          if (targetOrder == null) {
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
                         System.out.print(
                                   "Enter New Order Status : (1. Out For Delivery, 2. Delivered)\nEnter the Option Number : ");
                         userInput = input.nextInt();

                         if (userInput == 1) {
                              orderStatus = "Out For Delivery";
                         } else if (userInput == 2) {
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
     public void showAdminMenu(Admin admin, App app) {
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
                         adminDeliveryAgentMenu(admin, app);
                         break;
                    case 3:
                         adminCustomerMenu(admin);
                         break;
                    case 4:
                         System.out.println();
                         System.out.println(greenColor + "Thank You !\nVisit Again !" + resetColor);
                         saveAllData();
                         System.out.println();
                         return;
                    default:
                         System.out.println(redColor + "Enter the Valid Option Number!" + resetColor);
               }
          }

          System.out.println();
     }

     public void adminCustomerMenu(Admin admin){
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
                         System.out.println(invalidInputMessage);
                         input.nextLine();
                    }
               }
               input.nextLine();

               switch (userChoice) {
                    case 1:
                         System.out.println();

                         if (customers.isEmpty()) {
                              System.out.println(redColor + "There is No Customers to Display!" + resetColor);
                              break;
                         }

                         System.out.println("==========================================================");
                         System.out.println(greenColor + "All Customers : " + resetColor);
                         admin.displayAllCustomers(this);
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

                         Customer removedCustomer = null;

                         for (int i = 0; i < customers.size(); i++) {
                              if (customers.get(i).customerId == customerId) {
                                   removedCustomer = customers.remove(i);
                                   break;
                              }
                         }

                         if (removedCustomer == null) {
                              System.out.println(redColor + "Customer Not Found!" + resetColor);
                              break;
                         }

                         for (int i = 0; i < users.size(); i++) {
                              if (users.get(i).email.equalsIgnoreCase(removedCustomer.email)) {
                                   users.remove(i);
                                   break;
                              }
                         }

                         // Saving In File
                         saveAllData();

                         System.out.println(greenColor + removedCustomer.name+" has been Successfully removed as Customer" + resetColor);

                         System.out.println();
                         break;
                    case 3:
                         saveAllData();
                         break;
                    default:
                         break;
               }
          }
     }

     public void adminDeliveryAgentMenu(Admin admin, App app){
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
                         System.out.println(invalidInputMessage);
                         input.nextLine();
                    }
               }
               input.nextLine();

               switch (userChoice) {
                    case 1:
                         System.out.println();

                         if (deliveryAgents.isEmpty()) {
                              System.out.println(redColor + "There is No Delivery Agents to Display!" + resetColor);
                              break;
                         }

                         System.out.println("==========================================================");
                         System.out.println(greenColor + "All Delivery Agents : " + resetColor);
                         admin.displayAllDeliveryAgents(app);
                         System.out.println("==========================================================");
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
                                   System.out.println(invalidInputMessage);
                                   input.nextLine();
                              }
                         }

                         while (true) {
                              try {
                                   System.out.print("Enter the Password : ");
                                   char c[] = System.console().readPassword();
                                   dPass = new String(c);

                                   if (!isValidPassword(dPass)) {
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
                                   System.out.print("Enter the Phone Number : ");
                                   dPhone = input.nextLine();

                                   if (!isValidPhoneNumber(dPhone)) {
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
                                   emailCheckLoop: while (true) {
                                        System.out.print("Enter the Email : ");
                                        dEmail = input.nextLine();

                                        if (!isValidEmail(dEmail)) {
                                             continue;
                                        }

                                        for (Person p : users) {
                                             if (p.email.equalsIgnoreCase(dEmail)) {
                                                  System.out.println(redColor + "This Email is Already Taken!"+resetColor);
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

                                   if (!isValidLocation(dLocation)) {
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

                         System.out.print(
                                   greenColor + dName + " has Successfully Added as Delivery Agent!" + resetColor);
                         System.out.println();
                         break;
                    case 3:
                         System.out.println();

                         if (deliveryAgents.isEmpty()) {
                              System.out.println(redColor + "There is No Delivery Agents to Remove!" + resetColor);
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

                         for (int i = 0; i < deliveryAgents.size(); i++) {
                              if (deliveryAgents.get(i).deliveryAgentId == deliveryAgentId) {
                                   removedAgent = deliveryAgents.remove(i);
                                   break;
                              }
                         }

                         if (removedAgent == null) {
                              System.out.println(redColor + "Invalid Delivery Agent ID!" + resetColor);
                              break;
                         }

                         for (int i = 0; i < users.size(); i++) {
                              if (users.get(i).email.equalsIgnoreCase(removedAgent.email)) {
                                   users.remove(i);
                                   break;
                              }
                         }

                         // Saving In File
                         saveAllData();

                         System.out.println(greenColor + removedAgent.name+" has Successfully Removed as Delivery Agent" + resetColor);

                         System.out.println();
                         break;
                    case 4:
                         saveAllData();
                         break;
                    default:
                         break;
               }
          }
     }


     public void adminHotelMenu(Admin admin){
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
                         System.out.println(invalidInputMessage);
                         input.nextLine();
                    }
               }
               input.nextLine();

               switch (userChoice) {
                    case 1:
                         System.out.println();

                         if (hotels.isEmpty()) {
                              System.out.println(redColor + "There is No Hotels to Display!" + resetColor);
                              break;
                              }

                         System.out.println("==========================================================");
                         System.out.println(greenColor + "All Hotels : " + resetColor);
                         admin.displayAllHotels(this);
                         System.out.println("==========================================================");
                         System.out.println();
                         break;
                    case 2:
                         System.out.println();
                         admin.displayAllHotels(this);

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

                         for (int i = 0; i < hotels.size(); i++) {
                              if (hotels.get(i).hotelId == showMenuHotelId) {
                                   showMenuHotel = hotels.get(i);
                              }
                         }

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
                                   hotelNameCheckLoop: while (true) {
                                        System.out.print("Enter the Name of the Hotel : ");
                                        hotelName = input.nextLine();

                                        if (!isValidName(hotelName)) {
                                             continue hotelNameCheckLoop;
                                        }

                                        for (Hotel h : hotels) {
                                             if (h.hotelName.equalsIgnoreCase(hotelName)) {
                                                  System.out.println(redColor + "Hotel With Same Name Already Exists!"+resetColor);
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

                                   if (!isValidLocation(hotelLocation)) {
                                        continue;
                                   }

                                   break;
                              } catch (InputMismatchException e) {
                                   System.out.println(invalidInputMessage);
                                   input.nextLine();
                              }
                         }

                         Hotel hotel = new Hotel(hotelName, hotelLocation);
                         admin.addHotel(this, hotel);

                         // Saving In File
                         saveAllData();

                         System.out.println(greenColor + "Hotel Added Successfully!" + resetColor);

                         System.out.println();
                         break;
                    case 4:
                         System.out.println();
                         if (hotels.isEmpty()) {
                              System.out.println(redColor + "There Is No Hotel to Add Menu!" + resetColor);
                              break;
                         }

                         admin.displayAllHotels(this);
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
                                        itemNameCheckLoop: while (true) {
                                             System.out.print("Enter the Name of the Item : ");
                                             itemName = input.nextLine();

                                             if(itemName.equalsIgnoreCase("")){
                                                  System.out.println(invalidInputMessage);
                                                  continue;
                                             }

                                             for (Item item : menuHotel.menu) {
                                                  if (item.itemName.equalsIgnoreCase(itemName)) {
                                                       System.out.println(redColor + "Item With Same Name is Already Exists!"+resetColor);
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
                                        System.out.println(invalidInputMessage);
                                        input.nextLine();
                                   }
                              }
                              input.nextLine();

                              while (true) {
                                   try {
                                        String userInput = "";
                                        System.out.print("Enter the Item's Category [Veg(v) / Non-Veg(nv) / Drinks / Snacks] : ");
                                        userInput = input.nextLine();

                                        if(!userInput.equalsIgnoreCase("nv") && !userInput.equalsIgnoreCase("v") && !userInput.equalsIgnoreCase("veg") && !userInput.equalsIgnoreCase("non-veg") && !userInput.equalsIgnoreCase("non veg") && !userInput.equalsIgnoreCase("drinks") && !userInput.equalsIgnoreCase("snacks")){
                                             System.out.println(redColor+"Enter the Valid Option!"+resetColor);
                                             continue;
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
                                        System.out.println(invalidInputMessage);
                                        input.nextLine();
                                   }
                              }

                              Item newItem = new Item(itemName, itemPrice, itemCategory, itemDescription);
                              menuHotel.addFoodItem(newItem);

                              // Saving In File
                              saveAllData();

                              System.out.println(greenColor + itemName + " has been Successfully Added to Hotel : "+menuHotel.hotelName + resetColor);
                         }

                         System.out.println();
                         break;
                    case 5:
                         System.out.println();

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
                                   System.out.println(invalidInputMessage);
                                   input.nextLine();
                              }
                         }
                         input.nextLine();

                         Hotel removingHotel = null;
                         for (Hotel h : hotels) {
                              if (h.hotelId == hotelId) {
                                   removingHotel = h;
                                   break;
                              }
                         }
                         if (removingHotel == null) {
                              System.out.println(redColor + "Enter a valid Hotel ID!" + resetColor);
                              return;
                         }

                         hotels.remove(removingHotel);

                         // Save In File
                         saveAllData();

                         System.out.println(greenColor + removingHotel.hotelName + " has Successfully Removed from Hotels"+ resetColor);

                         System.out.println();
                         break;
                    case 6:
                         System.out.println();
                         admin.displayAllHotels(this);

                         int removeMenuHotelId;
                         while (true) {
                              try {
                                   System.out.print("Enter the Hotel Id to Display its Menu Item: ");
                                   removeMenuHotelId = input.nextInt();

                                   break;
                              } catch (InputMismatchException e) {
                                   System.out.println(invalidInputMessage);
                                   input.nextLine();
                              }
                         }
                         input.nextLine();

                         Hotel removeMenuHotel = null;

                         for (int i = 0; i < hotels.size(); i++) {
                              if (hotels.get(i).hotelId == removeMenuHotelId) {
                                   removeMenuHotel = hotels.get(i);
                                   break;
                              }
                         }

                         if (removeMenuHotel == null) {
                              System.out.println(redColor + "Hotel Not Found!" + resetColor);
                              break;
                         }

                         System.out.println("==========================================================");
                         System.out.println(greenColor + "All Menu of Hotel " + removeMenuHotel.hotelName + resetColor);
                         admin.displayAllMenu(removeMenuHotel);
                         System.out.println("==========================================================");


                         System.out.print("Enter the Menu Id to Remove the Menu from the Hotel : ");
                         int removeMenuId = input.nextInt();

                         Item removeHotelMenu = null;

                         for(int i = 0; i < removeMenuHotel.menu.size(); i++){
                              if(removeMenuHotel.menu.get(i).itemId == removeMenuId){
                                   removeHotelMenu = removeMenuHotel.menu.get(i);
                                   break;
                              }
                         }

                         if (removeHotelMenu == null) {
                              System.out.println(redColor + "Menu Item with ID " + removeMenuId + " not found!" + resetColor);
                              break;
                         }

                         removeMenuHotel.menu.remove(removeHotelMenu);

                         System.out.println(removeHotelMenu.itemName+" has removed Successfully from Hotel "+removeMenuHotel.hotelName);
                         saveAllData();
                         System.out.println();
                         break;
                    case 7:
                         saveAllData();
                         break;     
                    default:
                         System.out.println(redColor+"Enter the Valid Option!"+resetColor);
               }
          }
     }





     // Saving All data
     public void loadAllData() {
          try {
               customers = db.getCustomers();
               hotels = db.getHotels();
               deliveryAgents = db.getDeliveryAgents();
               orders = db.getOrders(customers, hotels, deliveryAgents);
               admins = db.getAdmins();
               users = db.getUsers();
          } catch (Exception e) {
               System.out.println(e.getMessage());
          }
     }

     public void saveAllData() {
          try {
               db.saveCustomers(customers);
               db.saveDeliveryAgents(deliveryAgents);
               db.saveHotels(hotels);
               db.saveOrders(orders);
               db.saveUsers(users);
               db.saveAdmins(admins);
          } catch (Exception e) {
               System.out.println(e.getMessage());
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
          
          String emailArr[] = email.split("@");

          if(emailArr[0].length() < 2){
               System.out.println(
                    "\u001B[91m╭───────────────────────────────────────╮\n" +
                              "│         Invalid Email Format          │\n" +
                              "╰───────────────────────────────────────╯\u001B[0m");
               return false;
          }

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

     // Find Methods
     public Customer findCustomer(Person person) {
          for (int i = 0; i < customers.size(); i++) {
               if (person.email.equalsIgnoreCase(customers.get(i).email)) {
                    return customers.get(i);
               }
          }

          return null;
     }

     public DeliveryAgent findDeliveryAgent(Person person) {
          for (int i = 0; i < deliveryAgents.size(); i++) {
               if (person.email.equalsIgnoreCase(deliveryAgents.get(i).email)) {
                    return deliveryAgents.get(i);
               }
          }

          return null;
     }

     public Admin findAdmin(Person person) {
          for (int i = 0; i < admins.size(); i++) {
               if (person.email.equalsIgnoreCase(admins.get(i).email)) {
                    return admins.get(i);
               }
          }

          return null;
     }
}