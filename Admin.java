public final class Admin extends Person {
     int adminId;
     static int globalId = 1;

     Admin(String name, String password, String phone, String email, String location){
          super(name, password, phone, email, location, "admin");
          this.adminId = globalId++;
     }

     public void addHotel(App app, Hotel hotel){
          app.hotels.add(hotel);
     }

     public void removeHotel(App app, int HotelId){
          if(app.hotels.isEmpty()){
               System.out.println("There is No Hotels To Remove!");
               return;
          }

          app.hotels.remove(HotelId - 1);
     }

     public void addDeliveryAgent(App app, DeliveryAgent deliveryAgent){
          app.deliveryAgents.add(deliveryAgent);
     }

     public void removeDeliveryAgent(App app, int deliveryAgentId){
          if(app.deliveryAgents.isEmpty()){
               System.out.println("There is No Delivery Agents to Remove!");
               return;
          }

          app.deliveryAgents.remove(deliveryAgentId - 1);
     }

     public void displayAllCustomers(App app){
          if(app.customers.isEmpty()){
               System.out.println("There is No Customers to Display!");
               return;
          }
          for(int i = 0; i < app.customers.size(); i++){
               System.out.println(app.customers.get(i).viewProfile());
          }
     }

     public void displayAllDeliveryAgents(App app){
          if(app.deliveryAgents.isEmpty()){
               System.out.println("There is No Delivery Agents to Display!");
               return;
          }

          for (DeliveryAgent d : app.deliveryAgents) {
               System.out.println(d.getTotalEarnings());
          }
     }

     public void displayAllHotels(App app){
          if(app.hotels.isEmpty()){
               System.out.println("There is No Admins to Display!");
               return;
          }

          for (Hotel h : app.hotels) {
               System.out.println(h.getHotelDetails());
          }
     }

     public void displayAllMenu(Hotel hotel){
          for(int i = 0; i < hotel.menu.size(); i++){
               System.out.println(hotel.menu.get(i).displayItemInfo());
          }
     }

     public void removeCustomer(App app, int customerId){
          if(app.customers.isEmpty()){
               System.out.println("There is No Customer To Remove!");
               return;
          }

          app.customers.remove(customerId - 1);
     }
}