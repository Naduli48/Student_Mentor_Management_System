import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Utility class responsible for creating and returning a connection to the SQLite database. Keeps database connection logic in one place.
// so it is not duplicated across other classes.
public class DBConnectionManager {

    // Path to the SQLite database file.
    // If this file does not exist, SQLite will create it automatically on the first connection attempt.
    private static final String URL = "jdbc:sqlite:smms.db";

    // Returns a new connection to the SQLite database.
    // Called by StudentRepository whenever a database operation is needed.
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}