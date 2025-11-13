import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;

public class DataBase {

     final String customerFile = "./Data/customers.csv";
     final String deliveryAgentFile = "./Data/deliveryagents.csv";
     final String hotelFile = "./Data/hotels.csv";
     final String orderFile = "./Data/orders.csv";
     final String userFile = "./Data/users.csv";
     final String adminFile = "./Data/admins.csv";
     final String itemFile = "./Data/items.csv";



     // Main User
     public void saveUsers(ArrayList<Person> users){
          try {
               File f = new File(userFile);
               FileWriter fw = new FileWriter(f);
               BufferedWriter bw = new BufferedWriter(fw);

               for (Person u : users) {
                    bw.write(u.name+","+u.phone+","+u.email+","+u.password+","+u.location+","+u.role);
                    bw.newLine();
               }

               bw.close();
               fw.close();
          } catch (IOException e) {
               System.out.println(e.getMessage());
          }
     }


     public ArrayList<Person> getUsers(){
          ArrayList<Person> users = new ArrayList<>();

          try {
               File f = new File(userFile);
               FileReader fr = new FileReader(f);
               BufferedReader br = new BufferedReader(fr);

               String line = br.readLine();
               while (line != null) {
                    String[] user = line.split(",");

                    String name = user[0];
                    String phone = user[1];
                    String email = user[2];
                    String password = user[3];
                    String location = user[4];
                    String role = user[5];


                    if (role.equalsIgnoreCase("customer")) {
                         users.add(new Customer(name, password, phone, email, location, ""));
                    } else if (role.equalsIgnoreCase("deliveryagent")) {
                        users.add(new DeliveryAgent(name, password, phone, email, location));
                    } else if (role.equalsIgnoreCase("admin")) {
                        users.add(new Admin(name, password, phone, email, location));
                    }
               
                    line = br.readLine();
               }

               fr.close();
               br.close();
          } catch (IOException e) {
               System.out.println(e.getMessage());
          }

          return users;
     }





     // Customer
     public ArrayList<Customer> getCustomers(){
          ArrayList<Customer> customers = new ArrayList<>();

          try {
               File f = new File(customerFile);
               FileReader fr = new FileReader(f);
               BufferedReader br = new BufferedReader(fr);
               String line = br.readLine();

               while (line != null) {
                    String arr[] = line.split(",");
                    // System.out.println(arr[0]+" "+arr[1]+" "+arr[2]+" "+arr[3]+" "+arr[4]+" "+arr[5]+" "+arr[6]);
                    Customer c = new Customer(arr[1], arr[2], arr[3], arr[4], arr[5], arr[6]);
                    c.customerId = Integer.parseInt(arr[0]);
                    customers.add(c);

                    line = br.readLine();
               }

               fr.close();
               br.close();
          } catch (IOException e) {
               System.out.println(e.getMessage());
          }


          return customers;
     }

     public void saveCustomers(ArrayList<Customer> customers){

          try {
               File f = new File(customerFile);
               FileWriter fw = new FileWriter(f);
               BufferedWriter bw = new BufferedWriter(fw);

               for (Customer c : customers) {
                    bw.write(c.customerId+","+c.name+","+c.password+","+c.phone+","+c.email+","+c.location+","+c.address); 
                    bw.newLine();    
               }

               // System.out.println("Customers Data Saved!");

               bw.close();
               fw.close();
          } catch(IOException e){
               System.out.println(e.getMessage());
          }
     }



     // Delivery Agent
     public ArrayList<DeliveryAgent> getDeliveryAgents(){
          ArrayList<DeliveryAgent> deliveryAgents = new ArrayList<>();

          try {
               File f =  new File(deliveryAgentFile);
               FileReader fr = new FileReader(f);
               BufferedReader br = new BufferedReader(fr);
               String line = br.readLine();

               while (line != null) {
                    String arr[] = line.split(",");
                    DeliveryAgent d = new DeliveryAgent(arr[1], arr[2], arr[3], arr[4], arr[5]);
                    d.deliveryAgentId = Integer.parseInt(arr[0]);
                    if (arr.length > 6) {
                         d.totalEarnings = Double.parseDouble(arr[6]);
                    }
                    if (arr.length > 7) {
                        d.isAvailable = Boolean.parseBoolean(arr[7]);
                    }
                    deliveryAgents.add(d);

                    line = br.readLine();
               }

               fr.close();
               br.close();
          } catch (IOException e) {
               System.out.println(e.getMessage());
          }

          return deliveryAgents;
     }

     public void saveDeliveryAgents(ArrayList<DeliveryAgent> deliveryAgents){

          try {
               File f = new File(deliveryAgentFile);
               FileWriter fw = new FileWriter(f);
               BufferedWriter bw = new BufferedWriter(fw);

               for (DeliveryAgent d : deliveryAgents) {
                    bw.write(d.deliveryAgentId+","+d.name+","+d.password+","+d.phone+","+d.email+","+d.location+","+d.totalEarnings+","+d.isAvailable);
                    bw.newLine();
               }

               bw.close();
               fw.close();
          } catch(IOException e){
               System.out.println(e.getMessage());
          }
     }




     // Admin
     public ArrayList<Admin> getAdmins(){
          ArrayList<Admin> admins = new ArrayList<>();

          try {
               File f =  new File(adminFile);
               FileReader fr = new FileReader(f);
               BufferedReader br = new BufferedReader(fr);
               String line = br.readLine();

               while (line != null) {
                    String arr[] = line.split(",");
                    Admin a = new Admin(arr[1], arr[2], arr[3], arr[4], arr[5]);
                    // Admin a  = new Admin(null, null, null, null, null);
                    a.adminId = Integer.parseInt(arr[0]);
                    admins.add(a);
                    line = br.readLine();
               }

               fr.close();
               br.close();
          } catch (IOException e) {
               System.out.println(e.getMessage());
          }

          return admins;
     }

     public void saveAdmins(ArrayList<Admin> admins){
          try {
               File f = new File(adminFile);
               FileWriter fw = new FileWriter(f);
               BufferedWriter bw = new BufferedWriter(fw);

               for (Admin a : admins) {
                    bw.write(a.adminId+","+a.name+","+a.password+","+a.phone+","+a.email+","+a.location);
                    bw.newLine();
               }

               bw.close();
               fw.close();
          } catch (IOException e) {
               System.out.println(e.getMessage());
          }
     }



     // Hotel
     public ArrayList<Hotel> getHotels(){
          ArrayList<Hotel> hotels = new ArrayList<>();

          try {
               File f = new File(hotelFile);
               FileReader fr = new FileReader(f);
               BufferedReader br = new BufferedReader(fr);
               String line = br.readLine();

               while (line != null) {
                    String arr[] = line.split(",");
                    // System.out.println(arr[0]+" "+arr[1]+" "+arr[2]);
                    Hotel h = new Hotel(arr[1], arr[2]);
                    hotels.add(h);

                    line = br.readLine();
               }

               getHotelItems(hotels);

               fr.close();
               br.close();
          } catch (IOException e) {
               System.out.println(e.getMessage());
          }


          return hotels;
     }

     public void saveHotels(ArrayList<Hotel> hotels){

          try {
               File f = new File(hotelFile);
               FileWriter fw = new FileWriter(f);
               BufferedWriter bw = new BufferedWriter(fw);

               for (Hotel h : hotels) {
                    // System.out.println(h.hotelId+" "+h.hotelName+" "+h.location);
                    bw.write(h.hotelId+","+h.hotelName+","+h.location);
                    bw.newLine();
               }

               saveHotelMenu(hotels);

               bw.close();
               fw.close();
          } catch(IOException e){
               System.out.println(e.getMessage());
          }
     }




     // Items
     public ArrayList<Item> getHotelItems(ArrayList<Hotel> hotels){
          ArrayList<Item> items = new ArrayList<>();

          try {
               File f = new File(itemFile);
               FileReader fr = new FileReader(f);
               BufferedReader br = new BufferedReader(fr);
               String line = br.readLine();

               while (line != null) {
                    String arr[] = line.split(",");

                    int hotelId = Integer.parseInt(arr[0]);
                    int itemId = Integer.parseInt(arr[1]);
                    String itemName = arr[2];
                    double price = Double.parseDouble(arr[3]);
                    String itemCategory = arr[4];
                    String itemDescription = arr[5];
                    int quantity = (arr.length > 6) ? Integer.parseInt(arr[6]) : 0;

                    Item item = new Item(itemName, price, itemCategory, itemDescription);
                    item.itemId = itemId;
                    item.quantity = quantity;

                    if (itemId >= Item.globalId) {
                         Item.globalId = itemId + 1;
                    }

                    items.add(item);

                    for (Hotel h : hotels) {
                         if(h.hotelId == hotelId){
                              if (h.menu == null) {
                                   h.menu = new ArrayList<>();
                              }

                              Item newItem = new Item(item.itemName, item.itemPrice, item.itemCategory, item.description);
                              newItem.itemId = item.itemId;
                              newItem.quantity = item.quantity;
          
                              h.menu.add(newItem);
                              break;
                         }
                    }

                    line = br.readLine();
               }

               fr.close();
               br.close();
          } catch (IOException e) {
               System.out.println(e.getMessage());
          }

          return items;
     }

     public void saveHotelMenu(ArrayList<Hotel> hotels){
          try {
               File f = new File(itemFile);
               FileWriter fw = new FileWriter(f);
               BufferedWriter bw = new BufferedWriter(fw);

               for (Hotel h : hotels) {

                    if(h.menu == null){
                         continue;
                    }

                    for (Item i : h.menu) {
                         bw.write(h.hotelId+","+i.itemId+","+i.itemName+","+i.itemPrice+","+i.itemCategory+","+i.description+","+i.quantity);
                         bw.newLine();
                    }
               }

               bw.close();
               fw.close();
          } catch (IOException e) {
               System.out.println(e.getMessage());
          }
     }




     // Order
     public ArrayList<Order> getOrders(ArrayList<Customer> customers, ArrayList<Hotel> hotels, ArrayList<DeliveryAgent> deliveryAgents) {
          ArrayList<Order> orders = new ArrayList<>();
      
          try {
               File f = new File(orderFile);
               FileReader fr = new FileReader(f);
               BufferedReader br = new BufferedReader(fr);
               String line = br.readLine();
               
               while (line != null) {
                   String arr[] = line.split(",");
               
                   int orderId = Integer.parseInt(arr[0]);
                   int customerId = Integer.parseInt(arr[1]);
                   int hotelId = Integer.parseInt(arr[2]);
                   int deliveryAgentId = Integer.parseInt(arr[3]);
                   String orderStatus = arr[4];
                   double totalAmount = Double.parseDouble(arr[5]);
               
                   Customer c = getCustomer(customerId, customers);
                   Hotel h = getHotel(hotelId, hotels);
                   DeliveryAgent deliveryAgent = getDeliveryAgent(deliveryAgentId, deliveryAgents);
               
                   Order o = new Order(c, h, new ArrayList<Item>());
                   o.orderId = orderId;
                   o.orderStatus = orderStatus;
                   o.totalAmount = totalAmount;
                   o.deliveryAgent = deliveryAgent;

                    if (deliveryAgent != null) {
                         if (orderStatus.equalsIgnoreCase("delivered")) {
                              deliveryAgent.comppletedOrders.add(o);
                         } else {
                              deliveryAgent.orders.add(o);
                         }
                         // System.out.println(orderStatus);

                         if (c != null) {
                              c.orderHistory.add(o);
                         }
                    }
                    
                   orders.add(o);
                   line = br.readLine();
               }
              
               fr.close();
               br.close();
          } catch (IOException e) {
              System.out.println(e.getMessage());
          }
      
          return orders;
     }

     public void saveOrders(ArrayList<Order> orders){

          try {
               File f = new File(orderFile);
               FileWriter fw = new FileWriter(f);
               BufferedWriter bw = new BufferedWriter(fw);

               for (Order o : orders) {

                    int customerId = (o.customer != null) ? o.customer.customerId : 0;
                    int hotelId = (o.hotel != null) ? o.hotel.hotelId : 0;
                    int agentId = (o.deliveryAgent != null) ? o.deliveryAgent.deliveryAgentId : 0;

                    bw.write(o.orderId+","+customerId+","+hotelId+","+agentId+","+o.orderStatus+","+o.totalAmount);
                    bw.newLine();
               }

               bw.close();
               fw.close();
          } catch(IOException e){
               System.out.println(e.getMessage());
          }
     }


     // get oj
     public Customer getCustomer(int customerId, ArrayList<Customer> customers){
          for(int i = 0; i < customers.size(); i++){
               if(customers.get(i).customerId == customerId){
                    return customers.get(i);
               }
          }

          return null;
     }

     public DeliveryAgent getDeliveryAgent(int deliveryAgentId, ArrayList<DeliveryAgent> deliveryAgents){
          for(int i = 0; i < deliveryAgents.size(); i++){
               if(deliveryAgents.get(i).deliveryAgentId == deliveryAgentId){
                    return deliveryAgents.get(i);
               }
          }

          return  null;
     }

     public ArrayList<Item> getItems(int orderId, ArrayList<Order> orders){
          ArrayList<Item> items = new ArrayList<>();

          for(int i = 0; i < orders.size(); i++){
               if(orders.get(i).orderId == orderId){
                    items = orders.get(i).items;
                    break;
               }
          }

          return items;
     }

     public Hotel getHotel(int hotelId, ArrayList<Hotel> hotels){
          for(int i = 0; i < hotels.size(); i++){
               if(hotels.get(i).hotelId == hotelId){
                    return hotels.get(i);
               }
          }

          return null;
     }
}
