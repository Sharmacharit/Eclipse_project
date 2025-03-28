package charitregistrationform;

import java.io.IOException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/profile") // Add the proper URL pattern to map the servlet
public class charitprofile extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the email from the session (assuming user is logged in)
        HttpSession session = request.getSession(false); // false means it won't create a new session if it doesn't exist
        String userEmail = null;

        // If the session exists, get the email attribute
        if (session != null) {
            userEmail = (String) session.getAttribute("userEmail");
        }

        // If the user is not logged in (email is null), redirect to the login page
        if (userEmail == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Set the email as an attribute to be used in the JSP
        request.setAttribute("userEmail", userEmail);

        // Forward the request to the profile JSP to display the email
        RequestDispatcher dispatcher = request.getRequestDispatcher("profile.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle logout action (invalidating the session)
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();  // Invalidates the session, effectively logging the user out
        }

        // Redirect to login page after logout
        response.sendRedirect("login.jsp");
    }
}
