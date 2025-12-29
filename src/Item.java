class Item implements Display{
     int itemId;
     String itemName;
     double itemPrice;
     String itemCategory;
     String description;
     int quantity;

     // Text Formatting & Decorations
     String redColor = "\u001B[91m";
     String resetColor = "\u001B[0m";
     String cyanColor = "\u001B[96m";
     String greenColor = "\u001B[92m";
     String textBold = "\u001B[1m";

     Item(String itemName, double itemPrice, String itemCategory, String description){
          this.itemName = itemName;
          this.itemPrice = itemPrice;
          this.itemCategory = itemCategory;
          this.description = description;
          this.quantity = 1;
     }

     @Override
     public String displayDetails(){
          return "| Id : "+itemId+" |"+greenColor+" Name : "+itemName+" | Category : "+itemCategory+resetColor+" | Description : "+description+(quantity > 1 ? " | Quantity : "+quantity : "")+" |"+greenColor+" Item Price : "+itemPrice+resetColor;
     }
}
