import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final int MAX_RETRIES = 10;   // Number of retry attempts
    private static final int RETRY_DELAY = 3000; // Delay between retries in milliseconds (3 seconds)

    public static Connection getConnection() throws Exception {
        // Get DATABASE_URL from environment variables
        String dbUrl = System.getenv("DATABASE_URL");
        if (dbUrl == null || dbUrl.isEmpty()) {
            throw new Exception("DATABASE_URL environment variable not set");
        }

        URI uri = new URI(dbUrl);
        String user = uri.getUserInfo().split(":")[0];
        String password = uri.getUserInfo().split(":")[1];
        String host = uri.getHost();
        int port = uri.getPort();
        String dbName = uri.getPath().substring(1); // Remove leading '/'

       String jdbcUrl = "jdbc:mysql://" + host + ":" + port + "/" + dbName +
                 "?useSSL=true&requireSSL=true&verifyServerCertificate=false&serverTimezone=UTC";


        Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL JDBC Driver

        int retries = MAX_RETRIES;
        while (retries > 0) {
            try {
                Connection conn = DriverManager.getConnection(jdbcUrl, user, password);
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
