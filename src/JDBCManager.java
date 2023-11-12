
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database Connection Class
 */
public class JDBCManager {
    private static Connection conn = null;
    private static  String dbUrl = "jdbc:derby:"+ Paths.get(System.getProperty("user.dir"),"database").toString() +";create=true";
    private static String username = "root";
    private static  String password = "root";


    static {
        try {
            //Load Driver
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get Connection
     * @return
     */
    public static Connection getConn() {
        try {
            return conn = DriverManager.getConnection(dbUrl, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
