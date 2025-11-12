public sealed class Person permits Admin, Customer, DeliveryAgent {
     String name;
     String password;
     String phone;
     String email;
     String location;
     String role;


     Person(String name, String password, String phone, String email, String location, String role){
          this.name = name.toLowerCase();
          this.password = password.toLowerCase();
          this.phone = phone.toLowerCase();
          this.email = email.toLowerCase();
          this.location = location.toLowerCase();
          this.role = role.toLowerCase();
     }

     public boolean login(String email, String password){
          return this.email.equalsIgnoreCase(email) && this.password.equalsIgnoreCase(password);
     }

     public boolean logout(){
          System.out.println("Successfully Logged Out!");
          return true;
     }

     public String viewProfile(){
          return "| Name : "+name+" | Email : "+email+" | Phone : "+phone+" | Location : "+location+" | Role : "+role+" |";
     }
}
