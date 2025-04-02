package in.niet.controllers;
import java.io.*;
import in.niet.dbconn.DbConnection;
import in.niet.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.*;

@WebServlet("/register")
public class Register extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve user data from form
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        // Create a User object and set the values
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        // Establish a database connection
        Connection conn = DbConnection.getConnection();
        try {
            // Create SQL query to insert user into the database
            String query = "INSERT INTO data (username, password, email) VALUES (?, ?, ?)";

            // Prepare statement to prevent SQL injection
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());

            // Execute the query
            int result = stmt.executeUpdate();

            if (result > 0) {
                // Redirect to login.html on successful registration
                response.sendRedirect("login.html");
            } else {
                // If registration failed, notify the user
                response.getWriter().write("Error in registration.");
            }

            // Close the statement
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Error: " + e.getMessage());
        } finally {
            // Close the connection after use
            DbConnection.closeConnection();
        }
    }
}


