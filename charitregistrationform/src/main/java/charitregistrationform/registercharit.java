package charitregistrationform;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/regForm")
public class registercharit extends HttpServlet {
    // JDBC variables
    private static final String DB_URL = "jdbc:mysql://localhost:3306/charit_28"; // replace with your DB URL
    private static final String DB_USER = "root"; // replace with your DB username
    private static final String DB_PASSWORD = "manager"; // replace with your DB password

    // Database connection
    private Connection connection = null;

    @Override
    public void init() throws ServletException {
        // Initialize database connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL JDBC driver
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("DB connection error: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form data
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String designation = request.getParameter("designation");
        String message = request.getParameter("message");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String pincode = request.getParameter("pincode");

        // SQL insert query
        String insertQuery = "INSERT INTO data (first_name, last_name, email, password, designation, message, address, city, pincode) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            // Set parameters in the SQL query
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setString(4, password); // Remember: Store password securely in production (hashed/encrypted)
            statement.setString(5, designation);
            statement.setString(6, message);
            statement.setString(7, address);
            statement.setString(8, city);
            statement.setString(9, pincode);

            // Execute the query
            int result = statement.executeUpdate();

            // Send response based on result
            if (result > 0) {
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<html><body><h2>Registration Successful!</h2><a href='login.jsp'>Go to Login</a></body></html>");
            } else {
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<html><body><h2>Registration Failed. Please try again.</h2><a href='registration.jsp'>Back to Registration</a></body></html>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error occurred.");
        }
    }

    @Override
    public void destroy() {
        // Close the DB connection
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
