import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/library_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root75";
    private static final int MAX_RETRIES = 10;   // Number of retry attempts
    private static final int RETRY_DELAY = 3000; // Delay between retries in milliseconds (3 seconds)

    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL JDBC Driver

        int retries = MAX_RETRIES;

        while (retries > 0) {
            try {
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database connected successfully!");
                return conn;
            } catch (SQLException e) {
                retries--;
                System.out.println("Database not ready, retrying in 3 seconds... (" + retries + " attempts left)");
                Thread.sleep(RETRY_DELAY);
            }
        }

        throw new Exception("Cannot connect to database after multiple attempts");
    }

    // Test main method (optional)
    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("Connection test successful!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
