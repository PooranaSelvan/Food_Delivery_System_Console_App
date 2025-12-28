import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

class DataBase {
    private final Connection connection;

    public DataBase() {
        DBConnection dbConnection = DBConnection.getInstance();
        this.connection = dbConnection.getConnection();

        try {
            if (connection != null && !connection.isClosed()) {
                System.out.println("Database Connected Successfully!");
            } else {
                System.err.println("Failed Database Connection!");
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }


//    User Table
    public int saveUser(Person p) throws SQLException {
        int userId = 0;

        PreparedStatement ps = connection.prepareStatement(Queries.insertUser, Statement.RETURN_GENERATED_KEYS);

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

        return userId;
    }

    public Person getUserByEmail(String email) throws SQLException {
        Person p = null;

        PreparedStatement ps = connection.prepareStatement(Queries.selectUserByEmail);
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

        ps.close();

        return p;
    }

    public Person getUserByPhone(String phone) throws SQLException {
        Person p = null;

        PreparedStatement ps = connection.prepareStatement(Queries.selectUserByPhone);
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

        return p;
    }


//    Customer Table
    public void saveCustomer(Customer c) throws SQLException {
        int userId = saveUser(c);

        if(userId > 0){
            c.customerId = userId;
            c.userId = userId;

            PreparedStatement ps = connection.prepareStatement(Queries.insertCustomer);
            ps.setInt(1, c.customerId);
            ps.setString(2, c.address);

            int res = ps.executeUpdate();

            if(res == 0){
                System.out.println("Error Saving In SaveCustomer");
                return;
            }

            System.out.println("Successfully Saved Customer");
            ps.close();
        }
    }

    public ArrayList<Customer> getCustomers() throws SQLException {
        ArrayList<Customer> customers = new ArrayList<>();

        PreparedStatement ps = connection.prepareStatement(Queries.selectCustomers);
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

        return customers;
    }

    public Customer getCustomerById(int customerId) throws SQLException {
        Customer c = null;

        PreparedStatement ps = connection.prepareStatement(Queries.selectCustomerById);
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

        return c;
    }

    public void deleteCustomer(int customerId) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(Queries.deleteCustomer);
        ps.setInt(1, customerId);
        ps.executeUpdate();
        ps.close();

        PreparedStatement ps1 = connection.prepareStatement(Queries.deleteUser);
        ps1.setInt(1, customerId);
        ps1.executeUpdate();
        ps1.close();
    }


//    Delivery Agent
    public void saveDeliveryAgent(DeliveryAgent d) throws SQLException {
        int userId = saveUser(d);

        if(userId > 0){
            d.deliveryAgentId = userId;
            d.userId = userId;

            PreparedStatement ps = connection.prepareStatement(Queries.insertDeliveryAgent, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, d.deliveryAgentId);
            ps.setDouble(2, d.totalEarnings);
            ps.setBoolean(3, d.isAvailable);

            int res = ps.executeUpdate();

            if(res == 0){
                System.out.println("Error Saving In Deliver Agent");
                return;
            }

            System.out.println("Successfully Saved Delivery Agent");
        }
    }

    public ArrayList<DeliveryAgent> getDeliveryAgents() throws SQLException {
        ArrayList<DeliveryAgent> deliveryAgents = new ArrayList<>();

        PreparedStatement ps = connection.prepareStatement(Queries.selectDeliveryAgents);
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

        return deliveryAgents;
    }

    public DeliveryAgent getDeliveryAgentById(int agentId) throws SQLException {
        DeliveryAgent d = null;

        PreparedStatement ps = connection.prepareStatement(Queries.selectDeliveryAgentById);
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

        return d;
    }

    public void updateDeliveryAgentEarnings(int agentId, double earnings) throws SQLException {
        DeliveryAgent d = getDeliveryAgentById(agentId);

        PreparedStatement ps = connection.prepareStatement(Queries.updateDeliveryAgentEarnings);
        ps.setDouble(1, earnings);
        ps.setInt(2, agentId);

        int res = ps.executeUpdate();

        if(res == 0){
            return;
        }

        ps.close();
    }

    public void updateDeliveryAgentStatus(int agentId, boolean isAvailable) throws SQLException {
        DeliveryAgent d = getDeliveryAgentById(agentId);

        PreparedStatement ps = connection.prepareStatement(Queries.updateDeliveryAgentStatus);
        ps.setBoolean(1, isAvailable);
        ps.setInt(2, agentId);

        int res = ps.executeUpdate();

        if(res == 0){
            return;
        }

        ps.close();
    }

    public void deleteDeliveryAgent(int agentId) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(Queries.deleteDeliveryAgent);
        ps.setInt(1, agentId);
        ps.executeUpdate();
        ps.close();

        PreparedStatement ps1 = connection.prepareStatement(Queries.deleteUser);
        ps1.setInt(1, agentId);
        ps1.executeUpdate();
        ps1.close();
    }


//    Admin
    public void saveAdmin(Admin a) throws SQLException {
        int userId = saveUser(a);

        if(userId > 0){
            a.adminId = userId;
            a.userId = userId;

            PreparedStatement ps = connection.prepareStatement(Queries.insertAdmin);
            ps.setInt(1, userId);

            int res = ps.executeUpdate();

            if(res == 0){
                return;
            }

            ps.close();
        }
    }

    public ArrayList<Admin> getAdmins() throws SQLException {
        ArrayList<Admin> admins = new ArrayList<>();

        PreparedStatement ps = connection.prepareStatement(Queries.selectAdmins);

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

        return admins;
    }

    public Admin getAdminByEmail(String email) throws SQLException {
        Admin a = null;

        PreparedStatement ps = connection.prepareStatement(Queries.selectAdminByEmail);
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

        return a;
    }


//    Hotel
    public void saveHotel(Hotel h) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(Queries.insertHotel, Statement.RETURN_GENERATED_KEYS);

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
    }

    public ArrayList<Hotel> getHotels() throws SQLException {
        ArrayList<Hotel> hotels = new ArrayList<>();

        PreparedStatement ps = connection.prepareStatement(Queries.selectHotels);

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

        return hotels;
    }

    public Hotel getHotelById(int hotelId) throws SQLException {
        Hotel h = null;

        PreparedStatement ps = connection.prepareStatement(Queries.selectHotelById);
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

        return h;
    }

    public void deleteHotel(int hotelId) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(Queries.deleteItemsByHotelId);
        ps.setInt(1, hotelId);
        ps.executeUpdate();
        ps.close();

        PreparedStatement ps1 = connection.prepareStatement(Queries.deleteHotel);
        ps1.setInt(1, hotelId);
        ps1.executeUpdate();
        ps1.close();
    }


//    Items
    public void saveItem(Item item, int hotelId) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(Queries.insertItem, Statement.RETURN_GENERATED_KEYS);

        ps.setInt(1, hotelId);
        ps.setString(2, item.itemName);
        ps.setDouble(3, item.itemPrice);
        ITEM_CATEGORIES i = switch (item.itemCategory){
            case "VEG" -> ITEM_CATEGORIES.VEG;
            case "NON_VEG" -> ITEM_CATEGORIES.NON_VEG;
            case "DRINKS" -> ITEM_CATEGORIES.DRINKS;
            case "SNACKS" -> ITEM_CATEGORIES.SNACKS;
            default -> ITEM_CATEGORIES.VEG;
        };
        ps.setString(4, i.name());
        ps.setString(5, item.description);

        int res = ps.executeUpdate();

        if(res == 0){
            return;
        }

        ResultSet rs = ps.getGeneratedKeys();

        if(rs.next()){
            item.itemId = rs.getInt(1);
        }

        ps.close();
    }

    public ArrayList<Item> getHotelItems(int hotelId) throws SQLException {
        ArrayList<Item> items = new ArrayList<>();

        PreparedStatement ps = connection.prepareStatement(Queries.selectItemsByHotelId);
        ps.setInt(1, hotelId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            int id = rs.getInt("itemId");
            String name = rs.getString("name");
            double price = rs.getDouble("price");
            String category = rs.getString("category");
            String description = rs.getString("description");

            Item item = new Item(name, price, category, description);
            item.itemId = id;

            items.add(item);
        }
        ps.close();

        return items;
    }

    public ArrayList<Item> getOrderItems(int orderId) throws SQLException {
        ArrayList<Item> items = new ArrayList<>();

        PreparedStatement ps = connection.prepareStatement(Queries.selectOrderItems);
        ps.setInt(1, orderId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            int id = rs.getInt("itemId");
            String name = rs.getString("name");
            double price = rs.getDouble("price");
            String category = rs.getString("category");
            String description = rs.getString("description");
            int quantity = rs.getInt("quantity");

            Item item = new Item(name, price, category, description);
            item.itemId = id;
            item.quantity = quantity;

            items.add(item);
        }

        ps.close();

        return items;
    }

    public Item getItemById(int itemId) throws SQLException {
        Item item = null;

        PreparedStatement ps = connection.prepareStatement(Queries.selectItemById);
        ps.setInt(1, itemId);

        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            String name = rs.getString("name");
            double price = rs.getDouble("price");
            String category = rs.getString("category");
            String description = rs.getString("description");

            item = new Item(name, price, category, description);
            item.itemId = itemId;
        }
        ps.close();

        return item;
    }

    public void deleteItem(int itemId) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(Queries.deleteItems);
        ps.setInt(1, itemId);
        ps.executeUpdate();
        ps.close();
    }


//    Order
    public int saveOrder(Order o) throws SQLException {
        int orderId = 0;

        PreparedStatement ps = connection.prepareStatement(Queries.insertOrder, Statement.RETURN_GENERATED_KEYS);

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
        }


        for (Item i : o.items){
            saveOrderItems(orderId, i);
        }

        ps.close();

        return orderId;
    }

    public void saveOrderItems(int orderId, Item item) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(Queries.insertOrderItem);

        ps.setInt(1, orderId);
        ps.setInt(2, item.itemId);
        ps.setInt(3, item.quantity);
        ps.setDouble(4, item.itemPrice);

        int res = ps.executeUpdate();
        ps.close();
    }

    public ArrayList<Order> getOrders() throws SQLException {
        ArrayList<Order> orders = new ArrayList<>();

        PreparedStatement ps = connection.prepareStatement(Queries.selectOrders);

        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            int orderId = rs.getInt("orderId");
            int customerId = rs.getInt("customerId");
            int hotelId = rs.getInt("hotelId");
            double totalAmount = rs.getDouble("totalAmount");
            int agentId = rs.getInt("agentId");
            String orderStatus = rs.getString("orderStatus");

            Customer c = getCustomerById(customerId);
            Hotel h = getHotelById(hotelId);

            if(c != null && h != null){
                Order order = new Order(c, h, new ArrayList<Item>());
                order.orderId = orderId;
                order.totalAmount = totalAmount;
                order.orderStatus = orderStatus != null ? orderStatus : "ORDERED";

                if(agentId > 0) {
                    order.deliveryAgent = getDeliveryAgentById(agentId);
                }

                order.items = getOrderItems(orderId);

                orders.add(order);
            }
        }

        ps.close();

        return orders;
    }

    public ArrayList<Order> getUnassignedOrders() throws SQLException {
        ArrayList<Order> orders = new ArrayList<>();

        PreparedStatement ps = connection.prepareStatement(Queries.selectUnassignedOrders);
        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            Order o = getOrderById(rs.getInt("orderId"));
            if(o != null){
                orders.add(o);
            }
        }
        ps.close();

        return orders;
    }

    public ArrayList<Order> getCustomerOrders(int customerId) throws SQLException {
        ArrayList<Order> orders = new ArrayList<>();

        PreparedStatement ps = connection.prepareStatement(Queries.selectOrderByCusId);
        ps.setInt(1, customerId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            Order o = getOrderById(rs.getInt("orderId"));
            if(o != null){
                orders.add(o);
            }
        }
        ps.close();

        return orders;
    }

    public ArrayList<Order> getOrderByAgentId(int agentId) throws SQLException {
        ArrayList<Order> orders = new ArrayList<>();

        PreparedStatement ps = connection.prepareStatement(Queries.selectOrdersByAgentId);
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

        return orders;
    }


//    Delivery Agent Orders
    public void assignOrder(int orderId, int agentId) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(Queries.insertOrderAgent);
        ps.setInt(1, orderId);
        ps.setInt(2, agentId);
        ps.setString(3, "ORDERED");
        int res = ps.executeUpdate();

        ps.close();

        updateDeliveryAgentStatus(agentId, false);
    }

    public void updateOrderStatus(int orderId, String orderStatus) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(Queries.updateOrderStatus);
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
                        d.totalEarnings += o.totalAmount;
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
    }

    public Order getOrderById(int orderId) throws SQLException {
        Order o = null;

        PreparedStatement ps = connection.prepareStatement(Queries.selectOrderById);
        ps.setInt(1, orderId);

        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            int customerId = rs.getInt("customerId");
            int hotelId = rs.getInt("hotelId");
            double totalAmount = rs.getDouble("totalAmount");
            int agentId = rs.getInt("agentId");
            String orderStatus = rs.getString("orderStatus");

            o = new Order(getCustomerById(customerId), getHotelById(hotelId), getOrderItems(orderId));
            o.totalAmount = totalAmount;
            o.orderId = orderId;
            o.orderStatus = orderStatus != null ? orderStatus : "ORDERED";

            if(agentId > 0){
                o.deliveryAgent = getDeliveryAgentById(agentId);
            }
        }

        return o;
    }

    public int getDeliveryAgentByOrder(int orderId) throws SQLException {
        int agentId = 0;

        PreparedStatement ps = connection.prepareStatement(Queries.getDeliveryAgentByOrderId);
        ps.setInt(1, orderId);

        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            agentId = rs.getInt("agentId");
        }

        ps.close();

        return agentId;
    }


    private String getUserRole(int userId) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT 1 FROM customers WHERE customerId = ?");
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            ps.close();
            return  "customer";
        }
        ps.close();

        ps = connection.prepareStatement("SELECT 1 FROM deliveryAgents WHERE agentId = ?");
        ps.setInt(1, userId);
        rs = ps.executeQuery();

        if(rs.next()){
            ps.close();
            return  "deliveryagent";
        }
        ps.close();

        ps = connection.prepareStatement("SELECT 1 FROM admins WHERE adminId = ?");
        ps.setInt(1, userId);
        rs = ps.executeQuery();

        if(rs.next()){
            ps.close();
            return  "admin";
        }
        ps.close();

        return "unknown";
    }
}


enum ITEM_CATEGORIES {
    SNACKS, DRINKS, VEG, NON_VEG
}