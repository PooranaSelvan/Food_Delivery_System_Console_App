import java.util.ArrayList;
import java.util.Scanner;

public class Hotel {
     int hotelId;
     String hotelName;
     String location;
     static int globalId = 1;
     ArrayList<Item> menu  = new ArrayList<>();
     ArrayList<Order> orders = new ArrayList<>();
     Scanner input = new Scanner(System.in);


     Hotel(String hotelName, String location){
          this.hotelId = globalId++;
          this.hotelName = hotelName;
          this.location = location;
     }
     
     public void displayMenu(){
          System.out.println("\n==================================================");
          System.out.println("Menu Of Hotel : "+hotelName);
          for(int i = 0; i < menu.size(); i++){
               System.out.println(menu.get(i).displayItemInfo());
          }
          System.out.println("==================================================\n");
     }

     public void addFoodItem(Item item){
          menu.add(item);
     }

     public void removeFoodItem(int index){
          menu.remove(index);
     }

     public void updateFoodItem(int index){
          Item item = menu.get(index);

          System.out.print("Enter the Option Number you Want to Change : \n1. Name\n2. Price\n3. Category\n4. Description\nEnter your Choice : ");
          int userChoice = input.nextInt();
          input.nextLine();

          switch (userChoice) {
               case 1:
                    System.out.println();
                    System.out.print("Enter the New Name : ");
                    String name = input.nextLine();

                    item.itemName = name;

                    System.out.println("\n==========================");
                    System.out.println("Name Changed Successfully!");
                    System.out.println("==========================\n");
                    break;
               case 2:
                    System.out.println();
                    System.out.print("Enter the New Price : ");
                    double price = input.nextDouble();

                    item.itemPrice = price;

                    System.out.println("\n===========================");
                    System.out.println("Price Changed Successfully!");
                    System.out.println("===========================\n");
                    break;
               case 3:
                    System.out.println();
                    System.out.print("Enter the New Category name : ");
                    String categoryName = input.nextLine();

                    item.itemCategory = categoryName;

                    System.out.println("\n====================================");
                    System.out.println("Category Name Changed Successfully!");
                    System.out.println("====================================\n");
                    break;
               case 4:
                    System.out.println();
                    System.out.print("Enter the New Description : ");
                    String description = input.nextLine();

                    item.description = description;

                    System.out.println("\n==================================");
                    System.out.println("Description Changed Successfully!");
                    System.out.println("==================================\n");
                    break;
               default:
                    System.out.println("\nEnter the Valid Option Number!\n");
          }

          System.out.println("After Changed");
          System.out.println("===================================================");
          System.out.println(item.displayItemInfo());
          System.out.println("===================================================");
     }

     public void acceptOrder(Order order){
          order.updateOrderStatus("Order Accepted!");
     }

     public void rejectOrder(Order order){
          order.updateOrderStatus("Order Rejected!");
     }

     public String getHotelDetails(){
          return "| Hotel Id : "+hotelId+" | Hotel Name : "+hotelName+" | Location : "+location+" |";
     }

     public void updateOrderStatus(Order order, String orderStatus){
          order.updateOrderStatus(orderStatus);
     }
}
