import java.sql.*;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class DataBase {
    private final Connection connection;
    Logger logger = LogManager.getLogger("Database");

    public DataBase() {
        DBConnection dbConnection = DBConnection.getInstance();
        this.connection = dbConnection.getConnection();

        try {
            if (connection != null && !connection.isClosed()) {
                System.out.println("Database Connected Successfully!");
            } else {
                System.err.println("Failed Database Connection!");
                logger.info("DataBase Connection Failed!");
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }


//    User Table
    public int saveUser(Person p)  {
        int userId = 0;

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(Queries.insertUser, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, p.name);
            ps.setString(2, p.password);
            ps.setString(3, p.phone);
            ps.setString(4, p.email);
            ps.setString(5, p.location);

            int res = ps.executeUpdate();

            if(res == 0){
                return 0;
            }

            ResultSet rs = ps.getGeneratedKeys();

            if(rs.next()){
                userId = rs.getInt(1);
            }
            ps.close();
            logger.info("Saved New User!");
        } catch (SQLException e) {
            logger.error("Error Saving User : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }

        return userId;
    }

    public Person getUserByEmail(String email) {
        Person p = null;

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(Queries.selectUserByEmail);

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                int userId = rs.getInt("userId");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                String resEmail = rs.getString("email");
                String location = rs.getString("location");

                p = new Person(name, password, phone, resEmail, location);
                p.userId = userId;
                p.role = getUserRole(p.userId);
            } else {
                return null;
            }
            logger.info("Getting User By Email!");

            ps.close();
        } catch (SQLException e) {
            logger.error("Error Getting User By Email : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }
        return p;
    }

    public Person getUserByPhone(String phone) {
        Person p = null;

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(Queries.selectUserByPhone);

            ps.setString(1, phone);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                int userId = rs.getInt("userId");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String resEmail = rs.getString("email");
                String location = rs.getString("location");

                p = new Person(name, password, phone, resEmail, location);
                p.userId = userId;
                p.role = getUserRole(p.userId);
            } else {
                return null;
            }

            ps.close();

            logger.info("Getting User By Phone!");
        } catch (SQLException e) {
            logger.error("Error Getting User By Phone : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }

        return p;
    }


//    Customer Table
    public void saveCustomer(Customer c) {
        int userId = saveUser(c);

        if(userId > 0){
            c.customerId = userId;
            c.userId = userId;

            PreparedStatement ps = null;
            try {
                ps = connection.prepareStatement(Queries.insertCustomer);

                ps.setInt(1, c.customerId);
                ps.setString(2, c.address);

                int res = ps.executeUpdate();

                if(res == 0){
                    return;
                }

                logger.info("Saved New Customer!");
                ps.close();
            } catch (SQLException e) {
                logger.error("Error Saving Customer : {}", String.valueOf(e));
                throw new RuntimeException(e);
            }
        }
    }

    public ArrayList<Customer> getCustomers()  {
        ArrayList<Customer> customers = new ArrayList<>();

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(Queries.selectCustomers);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int id = rs.getInt("userId");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String address = rs.getString("address");
                String location = rs.getString("location");

                Customer c = new Customer(name, password, phone, email, location, address);
                c.customerId = id;
                c.userId = id;
                c.role = "customer";

                customers.add(c);
            }
            logger.info("Getting All Customers!");
            ps.close();
        } catch (SQLException e) {
            logger.error("Error Getting Customers : {}", e);
            throw new RuntimeException(e);
        }

        return customers;
    }

    public Customer getCustomerById(int customerId) {
        Customer c = null;

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(Queries.selectCustomerById);

            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                String name = rs.getString("name");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String address = rs.getString("address");
                String location = rs.getString("location");

                c = new Customer(name, password, phone, email, address, location);
                c.customerId = customerId;
                c.userId = customerId;
                c.role = "customer";
            }
            ps.close();
            logger.info("Getting Customer By Id!");
        } catch (SQLException e) {
            logger.error("Error Getting Customer By Id : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }

        return c;
    }

    public void deleteCustomer(int customerId) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(Queries.deleteCustomer);

            ps.setInt(1, customerId);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            logger.error("Error Deleting From Customer Table : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }

        PreparedStatement ps1 = null;
        try {
            ps1 = connection.prepareStatement(Queries.deleteUser);

            ps1.setInt(1, customerId);
            ps1.executeUpdate();
            ps1.close();

            logger.info("Deleted Customer & User!");
        } catch (SQLException e) {
            logger.error("Error Deleting From User Table : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }
    }


//    Delivery Agent
    public void saveDeliveryAgent(DeliveryAgent d) {
        int userId = saveUser(d);

        if(userId > 0){
            d.deliveryAgentId = userId;
            d.userId = userId;

            PreparedStatement ps = null;
            try {
                ps = connection.prepareStatement(Queries.insertDeliveryAgent, Statement.RETURN_GENERATED_KEYS);

                ps.setInt(1, d.deliveryAgentId);
                ps.setDouble(2, d.totalEarnings);
                ps.setBoolean(3, d.isAvailable);

                int res = ps.executeUpdate();

                if(res == 0){
                    return;
                }

                logger.info("Saved New DeliveryAgent!");
                ps.close();
            } catch (SQLException e) {
                logger.error("Error Saving DeliveryAgent : {}", String.valueOf(e));
                throw new RuntimeException(e);
            }
        } else {
            logger.error("User is Not Saved!");
        }
    }

    public ArrayList<DeliveryAgent> getDeliveryAgents() {
        ArrayList<DeliveryAgent> deliveryAgents = new ArrayList<>();

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(Queries.selectDeliveryAgents);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int agentId = rs.getInt("userId");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String location = rs.getString("location");
                double totalEarnings = rs.getDouble("totalEarnings");
                boolean isAvailable = rs.getBoolean("isAvailable");

                DeliveryAgent d = new DeliveryAgent(name, password, phone, email, location);
                d.deliveryAgentId = agentId;
                d.userId = agentId;
                d.totalEarnings = totalEarnings;
                d.isAvailable = isAvailable;
                d.role = "deliveryagent";


                deliveryAgents.add(d);
            }

            ps.close();
            logger.info("Getting All Delivery Agents!");
        } catch (SQLException e) {
            logger.error("Error Getting Delivery Agents : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }

        return deliveryAgents;
    }

    public DeliveryAgent getDeliveryAgentById(int agentId) {
        DeliveryAgent d = null;

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(Queries.selectDeliveryAgentById);

            ps.setInt(1, agentId);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                String name = rs.getString("name");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String location = rs.getString("location");
                double totalEarnings = rs.getDouble("totalEarnings");
                boolean isAvailable = rs.getBoolean("isAvailable");

                d = new DeliveryAgent(name, password, phone, email, location);
                d.totalEarnings = totalEarnings;
                d.isAvailable = isAvailable;
                d.deliveryAgentId = agentId;
                d.userId = agentId;
                d.role = "deliveryagent";
            }

            ps.close();
            logger.info("Getting DeliveryAgent By Id!");
        } catch (SQLException e) {
            logger.error("Error Getting DeliveryAgent By Id : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }

        return d;
    }

    public void updateDeliveryAgentEarnings(int agentId, double earnings) {
        DeliveryAgent d = getDeliveryAgentById(agentId);

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(Queries.updateDeliveryAgentEarnings);

            ps.setDouble(1, earnings);
            ps.setInt(2, agentId);

            int res = ps.executeUpdate();

            if(res == 0){
                return;
            }

            ps.close();
            logger.info("Updated DeliveryAgent's Earnings!");
        } catch (SQLException e) {
            logger.error("Error Updating DeliveryAgent's Earnings : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }
    }

    public void updateDeliveryAgentStatus(int agentId, boolean isAvailable) {
        DeliveryAgent d = getDeliveryAgentById(agentId);

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(Queries.updateDeliveryAgentStatus);

            ps.setBoolean(1, isAvailable);
            ps.setInt(2, agentId);

            int res = ps.executeUpdate();

            if(res == 0){
                return;
            }

            ps.close();
            logger.info("Updated Delivery Agents's Status!");
        } catch (SQLException e) {
            logger.error("Error Updating DeliveryAgent's Status{}", String.valueOf(e));
            throw new RuntimeException(e);
        }
    }

    public void deleteDeliveryAgent(int agentId) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(Queries.deleteDeliveryAgent);

            ps.setInt(1, agentId);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            logger.error("Error Deleting Agent from DeliveryAgent Table : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }

        PreparedStatement ps1 = null;
        try {
            ps1 = connection.prepareStatement(Queries.deleteUser);

            ps1.setInt(1, agentId);
            ps1.executeUpdate();
            ps1.close();
            logger.info("Deleted Delivery Agent!");
        } catch (SQLException e) {
            logger.error("Error Deleting Agent from User Table : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }
    }


//    Admin
    public void saveAdmin(Admin a) {
        int userId = saveUser(a);

        if(userId > 0){
            a.adminId = userId;
            a.userId = userId;

            PreparedStatement ps = null;
            try {
                ps = connection.prepareStatement(Queries.insertAdmin);

                ps.setInt(1, userId);

                int res = ps.executeUpdate();

                if(res == 0){
                    return;
                }
                ps.close();

                logger.info("Saved New Admin!");
            } catch (SQLException e) {
                logger.error("Error Saving Admin : {}", String.valueOf(e));
                throw new RuntimeException(e);
            }
        }
    }

    public ArrayList<Admin> getAdmins() {
        ArrayList<Admin> admins = new ArrayList<>();

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(Queries.selectAdmins);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int adminId = rs.getInt("adminId");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                String email = rs.getString("email");

                Admin a = new Admin(name, password, phone, email);
                a.adminId = adminId;
                a.role = "admin";

                admins.add(a);
            }

            ps.close();
            logger.info("Getting All Admins!");
        } catch (SQLException e) {
            logger.error("Error Getting Admins : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }

        return admins;
    }

    public Admin getAdminByEmail(String email) {
        Admin a = null;

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(Queries.selectAdminByEmail);

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                int adminId = rs.getInt("userId");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                String adminEmail = rs.getString("email");

                a = new Admin(name, password, phone, adminEmail);
                a.adminId = adminId;
                a.userId = adminId;
                a.role = "admin";
            }
            ps.close();
            logger.info("Getting Admin By Email!");
        } catch (SQLException e) {
            logger.error("Error Getting Admin By Email : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }

        return a;
    }

    public int calculateRevenue(){
        int totalRevenue = 0;
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(Queries.calculateRevenue);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                totalRevenue = rs.getInt("revenue");
            }
            ps.close();

            logger.info("Calculated Revenue Successfully!");
        } catch (SQLException e) {
            logger.error("Error Calculating Revenue : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }


        return totalRevenue;
    }


//    Hotel
    public void saveHotel(Hotel h) {
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(Queries.insertHotel, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, h.hotelName);
            ps.setString(2, h.location);
            ps.setDouble(3, h.rating);
            ps.setBoolean(4, h.isOpen);

            int res = ps.executeUpdate();

            if(res == 0){
                return;
            }

            ResultSet rs = ps.getGeneratedKeys();

            if(rs.next()){
                h.hotelId = rs.getInt(1);
            }

            ps.close();
            logger.info("Saved New Hotel!");
        } catch (SQLException e) {
            logger.error("Error Saving Hotel : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Hotel> getHotels() {
        ArrayList<Hotel> hotels = new ArrayList<>();

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(Queries.selectHotels);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int id = rs.getInt("hotelId");
                String name = rs.getString("name");
                String location = rs.getString("location");
                double rating = rs.getDouble("rating");
                boolean isOpen = rs.getBoolean("isOpen");

                Hotel h = new Hotel(name, location);
                h.hotelId = id;
                h.menu = getHotelItems(h.hotelId);
                h.rating = rating;
                h.isOpen = isOpen;

                hotels.add(h);
            }
            ps.close();
            logger.info("Getting All Hotels!");
        } catch (SQLException e) {
            logger.error("Error Getting Hotels : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }

        return hotels;
    }

    public Hotel getHotelById(int hotelId) {
        Hotel h = null;

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(Queries.selectHotelById);

            ps.setInt(1, hotelId);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                String name = rs.getString("name");
                String location = rs.getString("location");
                double rating = rs.getDouble("rating");
                boolean isOpen = rs.getBoolean("isOpen");

                h = new Hotel(name, location);
                h.hotelId = hotelId;
                h.menu = getHotelItems(hotelId);
                h.rating = rating;
                h.isOpen = isOpen;
            }
            ps.close();
            logger.info("Getting a Hotel By Id!");
        } catch (SQLException e) {
            logger.error("Error Getting Hotel By Id : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }

        return h;
    }

    public void deleteHotel(int hotelId) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(Queries.deleteItemsByHotelId);

            ps.setInt(1, hotelId);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            logger.error("Error Deleting Hotel Items : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }

        PreparedStatement ps1 = null;
        try {
            ps1 = connection.prepareStatement(Queries.deleteHotel);

            ps1.setInt(1, hotelId);
            ps1.executeUpdate();
            ps1.close();

            logger.info("Deleted a Hotel!");
        } catch (SQLException e) {
            logger.error("Error Deleting Hotel : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }
    }


//    Items
    public boolean saveItem(Item item, int hotelId) {
        PreparedStatement ps = null;
        try {
//            System.out.println(checkItemName(item.itemName));
            int existingItemId = getItemByName(checkItemName(item.itemName));

            if(existingItemId > 0){
                insertItemRelations(hotelId, existingItemId, item.itemPrice);
                logger.info("Saved New Item!");
                return false;
            }

            ps = connection.prepareStatement(Queries.insertItem, Statement.RETURN_GENERATED_KEYS);
            String convertedName = checkItemName(item.itemName);
            ps.setString(1, convertedName);
            ITEM_CATEGORIES i = switch (item.itemCategory){
                case "NON_VEG" -> ITEM_CATEGORIES.NON_VEG;
                case "DRINKS" -> ITEM_CATEGORIES.DRINKS;
                case "SNACKS" -> ITEM_CATEGORIES.SNACKS;
                default -> ITEM_CATEGORIES.VEG;
            };
            ps.setString(2, i.name());
            ps.setString(3, item.description);

            int res = ps.executeUpdate();

            if(res == 0){
                return false;
            }

            ResultSet rs = ps.getGeneratedKeys();

            if(rs.next()){
                item.itemId = rs.getInt(1);
            }

            insertItemRelations(hotelId, item.itemId, item.itemPrice);

            ps.close();
            logger.info("Saved New Item!");
            return true;
        } catch (SQLException e) {
            logger.error("Error Saving Items : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }
    }

    public int getItemByName(String name){
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(Queries.selectItemByName);
            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return rs.getInt("itemId");
            }

            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }

    public void insertItemRelations(int hotelId, int itemId, double itemPrice) {
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement("SELECT * from items_relations WHERE itemId = ? and hotelId = ?");
            ps.setInt(1, itemId);
            ps.setInt(2, hotelId);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                System.out.println("\nItem with Same Name Already Exists!");
                logger.error("Adding Same Item Again!");
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            ps = connection.prepareStatement(Queries.insertItemRelation);
            ps.setInt(1, itemId);
            ps.setInt(2, hotelId);
            ps.setDouble(3, itemPrice);

            int res = ps.executeUpdate();

            if(res == 0){
                logger.error("Error Inserting the Item Relation!");
                return;
            }

            logger.info("Saved new Item Relation!");
            ps.close();
        } catch (SQLException e) {
            logger.error("Error On Inserting Item Relation : ", e);
            logger.error("Error Inserting the Item Relation");
            throw new RuntimeException(e);
        }
    }

    public double selectItemRelation(int itemId){
        PreparedStatement ps = null;
        double itemPrice = 0;

        try {
            ps = connection.prepareStatement(Queries.selectItemRelationPrice);
            ps.setInt(1, itemId);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                itemPrice = rs.getDouble("itemPrice");
            }

            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return itemPrice;
    }

    public ArrayList<Item> getHotelItems(int hotelId) {
        ArrayList<Item> items = new ArrayList<>();

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(Queries.selectItemsByHotelId);

            ps.setInt(1, hotelId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int id = rs.getInt("itemId");
                String name = rs.getString("name");
                String category = rs.getString("category");
                String description = rs.getString("description");
                double itemPrice = rs.getDouble("itemPrice");

                Item item = new Item(convertToName(name), itemPrice, category, description);
                item.itemId = id;

                items.add(item);
            }
            ps.close();
            logger.info("Getting Hotel Items!");
        } catch (SQLException e) {
            logger.error("Error Getting Hotel Items : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }

        return items;
    }

    public ArrayList<Item> getOrderItems(int orderId) {
        ArrayList<Item> items = new ArrayList<>();

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(Queries.selectOrderItems);

            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int id = rs.getInt("itemId");
                String name = rs.getString("name");
                String category = rs.getString("category");
                String description = rs.getString("description");
                int quantity = rs.getInt("quantity");
                double itemPrice = selectItemRelation(id);

                Item item = new Item(convertToName(name), itemPrice, category, description);
                item.itemId = id;
                item.quantity = quantity;

                items.add(item);
            }
            ps.close();
            logger.info("Getting Items for a Order!");
        } catch (SQLException e) {
            logger.error("Error Getting Order Items : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }

        return items;
    }

    public Item getItemById(int itemId) {
        Item item = null;

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(Queries.selectItemById);

            ps.setInt(1, itemId);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                String name = rs.getString("name");
                String category = rs.getString("category");
                String description = rs.getString("description");
                double itemPrice = selectItemRelation(itemId);

                item = new Item(convertToName(name), itemPrice, category, description);
                item.itemId = itemId;
            }
            ps.close();
            logger.info("Getting a Item By its Id!");
        } catch (SQLException e) {
            logger.error("Error Getting Item By Id : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }

        return item;
    }

    public void deleteItem(int itemId, int hotelId) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(Queries.deleteItems);
            ps.setInt(1, itemId);
            ps.setInt(2, hotelId);

            ps.executeUpdate();
            ps.close();
            logger.info("Deleted a Item!");
        } catch (SQLException e) {
            logger.error("Error Deleting an Item : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }
    }


//    Order
    public int saveOrder(Order o) {
        int orderId = 0;

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(Queries.insertOrder, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, o.customer.customerId);
            ps.setInt(2, o.hotel.hotelId);
            ps.setDouble(3, o.totalAmount);

            int res = ps.executeUpdate();

            if(res == 0){
                return 0;
            }

            ResultSet rs = ps.getGeneratedKeys();

            if(rs.next()){
                orderId = rs.getInt(1);
                o.orderId = orderId;
                o.createdAt = getOrderCreatedAt(orderId);
            }


            for (Item i : o.items){
                saveOrderItems(orderId, i);
            }
            ps.close();
            logger.info("Saved New Order!");
        } catch (SQLException e) {
            logger.error("Error Saving Order : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }

        return orderId;
    }

    public String getOrderCreatedAt(int orderId){
        PreparedStatement ps = null;
        String createdAt = "";

        try {
            ps = connection.prepareStatement(Queries.selectOrderById);
            ps.setInt(1, orderId);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                createdAt = rs.getString("createdAt");
            }

            ps.close();

            logger.info("Getting Order CreatedAt");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return createdAt;
    }

    public void saveOrderItems(int orderId, Item item) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(Queries.insertOrderItem);

            ps.setInt(1, orderId);
            ps.setInt(2, item.itemId);
            ps.setInt(3, item.quantity);
            ps.setDouble(4, item.itemPrice);

            int res = ps.executeUpdate();
            ps.close();
            logger.info("Saved New Order Items!");
        } catch (SQLException e) {
            logger.error("Error Saving Order Items : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Order> getOrders() {
        ArrayList<Order> orders = new ArrayList<>();

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(Queries.selectOrders);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int orderId = rs.getInt("orderId");
                int customerId = rs.getInt("customerId");
                int hotelId = rs.getInt("hotelId");
                double totalAmount = rs.getDouble("totalAmount");
                int agentId = rs.getInt("agentId");
                String orderStatus = rs.getString("orderStatus");
                String createdAt = rs.getString("createdAt");

                Customer c = getCustomerById(customerId);
                Hotel h = getHotelById(hotelId);

                if(c != null && h != null){
                    Order order = new Order(c, h, new ArrayList<Item>());
                    order.orderId = orderId;
                    order.totalAmount = totalAmount;
                    order.orderStatus = orderStatus != null ? orderStatus : "ORDERED";
                    order.createdAt = createdAt;

                    if(agentId > 0) {
                        order.deliveryAgent = getDeliveryAgentById(agentId);
                    }

                    order.items = getOrderItems(orderId);

                    orders.add(order);
                }
            }
            ps.close();
            logger.info("Getting All Orders!");
        } catch (SQLException e) {
            logger.error("Error Getting Orders : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }

        return orders;
    }

    public ArrayList<Order> getUnassignedOrders() {
        ArrayList<Order> orders = new ArrayList<>();

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(Queries.selectUnassignedOrders);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Order o = getOrderById(rs.getInt("orderId"));
                if(o != null){
                    orders.add(o);
                }
            }
            ps.close();
            logger.info("Getting All UnAssigned Orders for DeliveryAgent!");
        } catch (SQLException e) {
            logger.error("Error Getting UnAssigned Orders : "+e);
            throw new RuntimeException(e);
        }

        return orders;
    }

    public ArrayList<Order> getCustomerOrders(int customerId) {
        ArrayList<Order> orders = new ArrayList<>();

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(Queries.selectOrdersByCusId);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Order o = getOrderById(rs.getInt("orderId"));
                if(o != null){
                    orders.add(o);
                }
            }
            ps.close();
            logger.info("Getting Orders Based ON Customer!");
        } catch (SQLException e) {
            logger.error("Error Getting Customer Orders : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }

        return orders;
    }

    public ArrayList<Order> getOrderByAgentId(int agentId) {
        ArrayList<Order> orders = new ArrayList<>();

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(Queries.selectOrdersByAgentId);

            ps.setInt(1, agentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Order o = getOrderById(rs.getInt("orderId"));

                if(o != null){
                    o.deliveryAgent = getDeliveryAgentById(agentId);
                    o.items = getOrderItems(o.orderId);

                    orders.add(o);
                }
            }
            ps.close();
            logger.info("Getting Order By DeliveryAgentId!");
        } catch (SQLException e) {
            logger.error("Error Getting Orders By Agent Id : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }

        return orders;
    }


//    Delivery Agent Orders
    public void assignOrder(int orderId, int agentId) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(Queries.insertOrderAgent);

            ps.setInt(1, orderId);
            ps.setInt(2, agentId);
            ps.setString(3, "ORDERED");
            int res = ps.executeUpdate();

            ps.close();

            updateDeliveryAgentStatus(agentId, false);
            logger.info("Assigned a New Order for a DeliveryAgent!");
        } catch (SQLException e) {
            logger.error("Error Assigning Order for Delivery Agent : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }
    }

    public void updateOrderStatus(int orderId, String orderStatus) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(Queries.updateOrderStatus);

            ps.setString(1, orderStatus);
            ps.setInt(2, orderId);

            if(orderStatus.equalsIgnoreCase("DELIVERED")){
                int agentId = getDeliveryAgentByOrder(orderId);

                if(agentId > 0){
                    updateDeliveryAgentStatus(agentId, true);

                    Order o = getOrderById(orderId);

                    if(o != null){
                        DeliveryAgent d = getDeliveryAgentById(agentId);

                        if(d != null){
                            double taxAmount = calculateTax(o.totalAmount);

                            d.totalEarnings += (o.totalAmount - taxAmount);
                            updateDeliveryAgentEarnings(agentId, d.totalEarnings);
                        }
                    }
                }
            }

            int res = ps.executeUpdate();

            if(res == 0){
                return;
            }

            ps.close();
            logger.info("updated an Order Status!");
        } catch (SQLException e) {
            logger.error("Error Updating Order Status : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }
    }

    public Order getOrderById(int orderId) {
        Order o = null;

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(Queries.selectOrderById);

            ps.setInt(1, orderId);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                int customerId = rs.getInt("customerId");
                int hotelId = rs.getInt("hotelId");
                double totalAmount = rs.getDouble("totalAmount");
                int agentId = rs.getInt("agentId");
                String orderStatus = rs.getString("orderStatus");
                String createdAt = rs.getString("createdAt");

                o = new Order(getCustomerById(customerId), getHotelById(hotelId), getOrderItems(orderId));
                o.totalAmount = totalAmount;
                o.orderId = orderId;
                o.orderStatus = orderStatus != null ? orderStatus : "ORDERED";
                o.createdAt = createdAt;

                if(agentId > 0){
                    o.deliveryAgent = getDeliveryAgentById(agentId);
                }
            }
            ps.close();
            logger.info("Getting an Order By Id!");
        } catch (SQLException e) {
            logger.error("Error Getting Order By Id : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }

        return o;
    }

    public int getDeliveryAgentByOrder(int orderId) {
        int agentId = 0;

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(Queries.getDeliveryAgentByOrderId);

            ps.setInt(1, orderId);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                agentId = rs.getInt("agentId");
            }
            ps.close();
            logger.info("Getting DeliveryAgent By Order Id!");

        } catch (SQLException e) {
            logger.error("Error Getting DeliveryAgent By Order : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }

        return agentId;
    }


    private String getUserRole(int userId) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement("SELECT 1 FROM customers WHERE customerId = ?");

            ps.setInt(1, userId);
            rs = ps.executeQuery();

            if(rs.next()){
                ps.close();
                return  "customer";
            }
            ps.close();
        } catch (SQLException e) {
            logger.error("Error Setting Customer Role : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }

        try {
            ps = connection.prepareStatement("SELECT 1 FROM deliveryAgents WHERE agentId = ?");

            ps.setInt(1, userId);
            rs = ps.executeQuery();

            if(rs.next()){
                ps.close();
                return  "deliveryagent";
            }
            ps.close();
        } catch (SQLException e) {
            logger.error("Error Setting DeliveryAgent Role : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }

        try {
            ps = connection.prepareStatement("SELECT 1 FROM admins WHERE adminId = ?");

            ps.setInt(1, userId);
            rs = ps.executeQuery();

            if(rs.next()){
                ps.close();
                return  "admin";
            }
            ps.close();
        } catch (SQLException e) {
            logger.error("Error Setting Admin Role : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }

        return "unknown";
    }

    private double calculateTax(double totalAmount){
        double tax = 0.10;

        if(totalAmount < 100){
            tax = 0.05;
        } else if(totalAmount > 500){
            tax = 0.15;
        }

        return totalAmount * tax;
    }

    private String checkItemName(String name){
        return name.toLowerCase().trim().replaceAll("[^a-z0-9\\s]", "").replaceAll("\\s+"," ");
    }

    private String convertToName(String name){
        StringBuilder res = new StringBuilder();
        String[] arr = name.split(" ");

        for (String s : arr) {
            res.append(s.substring(0, 1).toUpperCase()).append(s.substring(1)).append(" ");
        }

        return res.toString().trim();
    }
}


enum ITEM_CATEGORIES {
    SNACKS, DRINKS, VEG, NON_VEG
}