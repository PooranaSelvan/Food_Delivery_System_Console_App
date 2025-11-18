import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Hotel {
     int hotelId;
     String hotelName;
     String location;
     static int globalId = 1;
     ArrayList<Item> menu  = new ArrayList<>();
     ArrayList<Order> orders = new ArrayList<>();
     Scanner input = new Scanner(System.in);

     // Text Formatting & Decorations
     String redColor = "\u001B[91m";
     String resetColor = "\u001B[0m";
     String cyanColor = "\u001B[96m";
     String greenColor = "\u001B[92m";
     String textBold = "\u001B[1m";

     // Error Messages
     String invalidInputMessage = redColor+textBold+"\nInvalid Input!\n"+resetColor;


     Hotel(String hotelName, String location){
          this.hotelId = globalId++;
          this.hotelName = hotelName;
          this.location = location;
     }
     
     public void displayMenu(){
          System.out.println("\n=====================================================");
          System.out.println("\n"+greenColor+"Menu Of Hotel : "+hotelName+resetColor+"\n");
          for(int i = 0; i < menu.size(); i++){
               System.out.println(menu.get(i).displayItemInfo());
          }
          System.out.println("=====================================================\n");
     }

     public void addFoodItem(Item item){
          menu.add(item);
     }
     
     public void addFoodItem(ArrayList<Item> item){
          menu.addAll(item);
     }
     
     


     public void removeFoodItem(int index){
          menu.remove(index);
     }

     public void updateFoodItem(int index){
          Item item = menu.get(index);

          int userChoice;
          while (true) {
               try {
                    System.out.print("Enter the Option Number you Want to Change : \n1. Name\n2. Price\n3. Category\n4. Description\nEnter your Choice : ");
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
                    String name;
                    while (true) {
                         try {
                              System.out.print("Enter the New Name : ");
                              name = input.nextLine();
          
                              break;
                         } catch (InputMismatchException e) {
                              System.out.println(invalidInputMessage);
                              input.nextLine();
                         }
                    }
                    item.itemName = name;


                    System.out.println(greenColor+"\n==========================");
                    System.out.println("Name Changed Successfully!");
                    System.out.println("==========================\n"+resetColor);
                    break;
               case 2:
                    System.out.println();
                    double price;
                    while (true) {
                         try {
                              System.out.print("Enter the New Price : ");
                              price = input.nextDouble();
          
                              break;
                         } catch (InputMismatchException e) {
                              System.out.println(invalidInputMessage);
                              input.nextLine();
                         }
                    }
                    item.itemPrice = price;


                    System.out.println(greenColor+"\n===========================");
                    System.out.println("Price Changed Successfully!");
                    System.out.println("===========================\n"+resetColor);
                    break;
               case 3:
                    System.out.println();
                    String categoryName;
                    while (true) {
                         try {
                              System.out.print("Enter the New Category name : ");
                              categoryName = input.nextLine();
          
                              break;
                         } catch (InputMismatchException e) {
                              System.out.println(invalidInputMessage);
                              input.nextLine();
                         }
                    }
                    item.itemCategory = categoryName;


                    System.out.println(greenColor+"\n====================================");
                    System.out.println("Category Name Changed Successfully!");
                    System.out.println("====================================\n"+resetColor);
                    break;
               case 4:
                    System.out.println();
                    String description;
                    while (true) {
                         try {
                              System.out.print("Enter the New Description : ");
                              description = input.nextLine();
          
                              break;
                         } catch (InputMismatchException e) {
                              System.out.println(invalidInputMessage);
                              input.nextLine();
                         }
                    }
                    item.description = description;


                    System.out.println(greenColor+"\n==================================");
                    System.out.println("Description Changed Successfully!");
                    System.out.println("==================================\n"+resetColor);
                    break;
               default:
                    System.out.println("\nEnter the Valid Option Number!\n");
          }

          System.out.println(greenColor+"After Changed"+resetColor);
          System.out.println("===================================================");
          System.out.println(item.displayItemInfo());
          System.out.println("===================================================");
     }

     public String getHotelDetails(){
          return "| Hotel Id : "+hotelId+" | "+greenColor+"Hotel Name : "+hotelName+resetColor+" | Location : "+location+" |";
     }
}
