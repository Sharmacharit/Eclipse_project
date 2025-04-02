package in.niet.dbconn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    // Database credentials
    private static final String URL = "jdbc:mysql://localhost:3306/charit2"; // Change with your DB URL
    private static final String USER = "root"; // Change with your DB username
    private static final String PASSWORD = "manager"; // Change with your DB password

    // This will hold the connection object
    private static Connection connection = null;

    // Method to get the connection
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Load the JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Create a new connection to the database
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Error connecting to the database.", e);
        }

        return connection;
    }

    // Method to close the connection (to be called when done with DB operations)
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
