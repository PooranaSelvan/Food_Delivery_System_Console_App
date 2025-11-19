public class Item {
     int itemId;
     String itemName;
     double itemPrice;
     String itemCategory;
     String description;
     static int globalId = 1;
     int quantity;

     // Text Formatting & Decorations
     String redColor = "\u001B[91m";
     String resetColor = "\u001B[0m";
     String cyanColor = "\u001B[96m";
     String greenColor = "\u001B[92m";
     String textBold = "\u001B[1m";

     Item(String itemName, double itemPrice, String itemCategory, String description){
          this.itemId = globalId++;
          this.itemName = itemName;
          this.itemPrice = itemPrice;
          this.itemCategory = itemCategory;
          this.description = description;
          this.quantity = 1;
     }

     public String displayItemInfo(){
          return "| Id : "+itemId+" |"+greenColor+" Name : "+itemName+" | Category : "+itemCategory+resetColor+" | Description : "+description+(quantity > 1 ? " | Quantity : "+quantity : "")+" |"+greenColor+" Item Price : "+itemPrice+resetColor;
     }
}
