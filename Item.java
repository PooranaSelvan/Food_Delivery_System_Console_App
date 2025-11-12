public class Item {
     int itemId;
     String itemName;
     double itemPrice;
     String itemCategory;
     String description;
     static int globalId = 1;
     int quantity;

     Item(String itemName, double itemPrice, String itemCategory, String description){
          this.itemId = globalId++;
          this.itemName = itemName;
          this.itemPrice = itemPrice;
          this.itemCategory = itemCategory;
          this.description = description;
          this.quantity = 1;
     }

     public String displayItemInfo(){
          return "| Id : "+itemId+" | Name : "+itemName+" | Category : "+itemCategory+" | Description : "+description+(quantity > 1 ? " | Quantity : "+quantity : "")+" | Item Price : "+itemPrice;
     }
}
