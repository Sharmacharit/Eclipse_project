package in.niet.controllers;

import java.io.IOException;
import java.sql.*;

import in.niet.dbconn.DbConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/login")
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Establish a database connection
        Connection conn = DbConnection.getConnection();
        try {
            // Create SQL query to find the user by username and password
            String query = "SELECT * FROM data WHERE username = ? AND password = ?";

            // Prepare statement to prevent SQL injection
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);

            // Execute the query
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                response.getWriter().write("Login Successful!");
            } else {
                response.getWriter().write("Invalid credentials.");
            }

            // Close the ResultSet and statement
            rs.close();
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
