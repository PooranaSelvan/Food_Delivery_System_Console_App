public class Queries {

//    User
    public final static String insertUser = "INSERT INTO users (name, password, phone, email, location) VALUES (?, ?, ?, ?, ?)";
    public final static String selectUserByEmail = "SELECT * FROM users WHERE email = ?";
    public final static String selectUserByPhone = "SELECT * FROM users WHERE phone = ?";
    public final static String deleteUser = "DELETE FROM users WHERE userId = ?";

//    Customer
    public final static String insertCustomer = "INSERT INTO customers (customerId, address) VALUES (?, ?)";
    public final static String selectCustomers = "SELECT u.*, c.address FROM users u JOIN customers c ON u.userId = c.customerId";
    public final static String selectCustomerById = "SELECT u.*, c.address FROM users u JOIN customers c ON u.userId = c.customerId WHERE u.userId = ?";
    public final static String deleteCustomer = "DELETE FROM customers WHERE customerId = ?";

//    Delivery Agent
    public final static String insertDeliveryAgent = "INSERT INTO deliveryAgents (agentId, totalEarnings, isAvailable) VALUES (?, ?, ?)";
    public final static String selectDeliveryAgents = "SELECT u.*, d.totalEarnings, d.isAvailable FROM users u JOIN deliveryAgents d ON u.userId = d.agentId";
    public final static String selectDeliveryAgentById = "SELECT u.*, d.totalEarnings, d.isAvailable FROM users u JOIN deliveryAgents d ON u.userId = d.agentId WHERE u.userId = ?";
    public final static String updateDeliveryAgentEarnings = "UPDATE deliveryAgents SET totalEarnings = ? WHERE agentId = ?";
    public final static String updateDeliveryAgentStatus = "UPDATE deliveryAgents SET isAvailable = ? WHERE agentId = ?";
    public final static String deleteDeliveryAgent = "DELETE FROM deliveryAgents WHERE agentId = ?";

//    Admin
    public final static String insertAdmin = "INSERT INTO admins (adminId) VALUES (?)";
    public final static String selectAdmins = "SELECT u.*, a.adminId FROM users u JOIN admins a on u.userId = a.adminId";
    public final static String selectAdminByEmail = "SELECT u.* FROM users u JOIN admins a ON u.userId = a.adminId WHERE u.email = ?";
    public final static String calculateRevenue = "SELECT SUM(totalAmount) as revenue FROM orders";

//    Hotel
    public final static String insertHotel = "INSERT INTO hotels (name, location, rating, isOpen) VALUES (?, ?, ?, ?)";
    public final static String selectHotels = "SELECT * FROM hotels";
    public final static String selectHotelById = "SELECT * FROM hotels where hotelId = ?";
    public final static String deleteHotel = "DELETE FROM hotels WHERE hotelId = ?";

//    Items
    public final static String insertItem = "INSERT INTO items (hotelId, name, price, category, description) VALUES (?, ?, ?, ?, ?)";
    public final static String selectItemsByHotelId = "SELECT * FROM items WHERE hotelId = ?";
    public final static String deleteItemsByHotelId = "DELETE FROM items WHERE hotelId = ?";
    public final static String deleteItems = "DELETE FROM items where itemId = ?";
    public final static String selectItemById = "SELECT * FROM items WHERE itemId = ?";

//    Order
    public final static String insertOrder = "INSERT INTO orders (customerId, hotelId, totalAmount) VALUES (?, ?, ?)";
    public final static String selectOrders = "SELECT o.*, oda.agentId, oda.orderStatus FROM orders o LEFT JOIN order_delivery_agents oda ON o.orderId = oda.orderId";
    public final static String selectOrderById = "SELECT o.*, oa.agentId, oa.orderStatus FROM orders o LEFT JOIN order_delivery_agents oa ON o.orderId = oa.orderId WHERE o.orderId = ?";
    public final static String selectUnassignedOrders = "SELECT o.* FROM orders o LEFT JOIN order_delivery_agents oda ON o.orderId = oda.orderId WHERE oda.agentId IS NULL";
    public final static String selectOrdersByAgentId = "SELECT o.*, oda.orderStatus FROM orders o JOIN order_delivery_agents oda ON o.orderId = oda.orderId WHERE oda.agentId = ? ORDER BY o.createdAt DESC";
    public final static String selectOrdersByCusId = "SELECT * FROM orders WHERE customerId = ? ORDER BY createdAt DESC";


//    Order Items
    public final static String insertOrderItem = "INSERT INTO order_items (orderId, itemId, quantity, price) VALUES (?, ?, ?, ?)";
    public final static String selectOrderItems = "SELECT oi.*, i.name, i.category, i.description FROM order_items oi JOIN items i ON oi.itemId = i.itemId WHERE oi.orderId = ?";

//    Order Delivery Agent
    public final static String insertOrderAgent = "INSERT INTO order_delivery_agents (orderId, agentId, orderStatus) VALUES (?, ?, ?)";
    public final static String updateOrderStatus = "UPDATE order_delivery_agents SET orderStatus = ? WHERE orderId = ?";
    public final static String getDeliveryAgentByOrderId = "SELECT agentId FROM order_delivery_agents WHERE orderId = ?";
}
