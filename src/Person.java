sealed class Person permits Admin, Customer, DeliveryAgent {
     int userId;
     String name;
     String password;
     String phone;
     String email;
     String role;
     String location;


     Person(String name, String password, String phone, String email, String location){
          this.name = name.toLowerCase();
          this.password = password;
          this.phone = phone.toLowerCase();
          this.email = email.toLowerCase();
          this.location = location.toLowerCase();
     }

     public String viewProfile(){
          return "| Name : "+name+" | Email : "+email+" | Phone : "+phone+" |";
     }
}
