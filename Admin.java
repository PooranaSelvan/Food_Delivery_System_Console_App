public final class Admin extends Person {
     int adminId;
     static int adminGlobalId = 1;

     // Text Formatting & Decorations
     String redColor = "\u001B[91m";
     String resetColor = "\u001B[0m";
     String cyanColor = "\u001B[96m";
     String greenColor = "\u001B[92m";
     String textBold = "\u001B[1m";

     // Error Messages
     String invalidInputMessage = redColor+textBold+"\nInvalid Input!\n"+resetColor;

     Admin(String name, String password, String phone, String email, String location){
          super(name, password, phone, email, location, "admin");
          this.adminId = adminGlobalId++;
     }

     public void addHotel(App app, Hotel hotel){
          app.hotels.add(hotel);
     }

     public void removeHotel(App app, int HotelId){
          if(app.hotels.isEmpty()){
               System.out.println(redColor+"There is No Hotels To Remove!"+resetColor);
               return;
          }

          app.hotels.remove(HotelId - 1);
     }

     public void addDeliveryAgent(App app, DeliveryAgent deliveryAgent){
          app.deliveryAgents.add(deliveryAgent);
     }

     public void removeDeliveryAgent(App app, int deliveryAgentId){
          if(app.deliveryAgents.isEmpty()){
               System.out.println(redColor+"There is No Delivery Agents to Remove!"+resetColor);
               return;
          }

          app.deliveryAgents.remove(deliveryAgentId - 1);
     }

     public void displayAllCustomers(App app){
          if(app.customers.isEmpty()){
               System.out.println(redColor+"There is No Customers to Display!"+resetColor);
               return;
          }
          for(int i = 0; i < app.customers.size(); i++){
               System.out.println(app.customers.get(i).displayCustomerDetails()+greenColor+" Orders Count : "+app.customers.get(i).orderHistory.size()+" |"+resetColor);
          }
     }

     public void displayAllDeliveryAgents(App app){
          if(app.deliveryAgents.isEmpty()){
               System.out.println(redColor+"There is No Delivery Agents to Display!"+resetColor);
               return;
          }

          for (DeliveryAgent d : app.deliveryAgents) {
               System.out.println(d.getTotalEarnings());
          }
     }

     public void displayAllHotels(App app){
          if(app.hotels.isEmpty()){
               System.out.println(redColor+"There is No Admins to Display!"+resetColor);
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
               System.out.println(redColor+"There is No Customer To Remove!"+resetColor);
               return;
          }

          app.customers.remove(customerId - 1);
     }
}