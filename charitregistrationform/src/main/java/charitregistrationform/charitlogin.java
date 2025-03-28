package charitregistrationform;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class charitlogin extends HttpServlet {

    // Database credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/charit_28";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "manager";

    // SQL query for checking user credentials
    private static final String LOGIN_QUERY = "SELECT * FROM data WHERE email = ? AND password = ?";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // If email or password is empty, redirect back to login page with error
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            response.sendRedirect("login.jsp?error=Please enter both email and password.");
            return;
        }

        // Validate the login credentials against the database
        boolean loginSuccessful = validateLogin(email, password);

        if (loginSuccessful) {
            // Set the email as a request attribute before forwarding to profile.jsp
            request.setAttribute("userEmail", email);

            // Forward the request to profile.jsp with the email set in the request
            RequestDispatcher dispatcher = request.getRequestDispatcher("profile.jsp");
            dispatcher.forward(request, response);
        } else {
            // Redirect to login page with an error message if login fails
            response.sendRedirect("login.jsp?error=Invalid email or password.");
        }
    }

    private boolean validateLogin(String email, String password) {
        boolean isValid = false;

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(LOGIN_QUERY)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            // If a row is returned, it means credentials are valid
            if (resultSet.next()) {
                isValid = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isValid;
    }
}
