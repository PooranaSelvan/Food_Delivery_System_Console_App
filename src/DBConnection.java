import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBConnection {
    private static DBConnection instance;
    private Connection connection;
    Logger logger = LogManager.getLogger("DBConnection");

    private static final String URL = "jdbc:mysql://localhost:3306/FoodDeliverySystem";
    private static final String USER = "root";
    private static final String PASSWORD = "poorana@zs27";

    private DBConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            logger.info("Connected to the Database!");
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Error Connecting Database {}", String.valueOf(e));
        }
    }

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }
    public Connection getConnection() {
        return connection;
    }
}