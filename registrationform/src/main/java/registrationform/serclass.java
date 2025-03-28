
package registrationform;

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
public class serclass extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String myname1 = req.getParameter("name1");
        String myemail1 = req.getParameter("email1");
        String mypass1 = req.getParameter("pass1");
        String mygender1 = req.getParameter("gender1");
        String mycity1 = req.getParameter("city1");
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/charit_aiml", "root", "manager");
            ps = conn.prepareStatement("insert into register values(?,?,?,?,?)");
            ps.setString(1, myname1);
            ps.setString(2, myemail1);
            ps.setString(3, mypass1);
            ps.setString(4, mygender1);
            ps.setString(5, mycity1);
            
            int i = ps.executeUpdate();
            if (i > 0) {
                resp.setContentType("text/html");
                out.print("<h3 style='color:red'>User registered successfully</h3>");
                RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");
                rd.include(req, resp);
            } else {
                resp.setContentType("text/html");
                out.print("<h3 style='color:red'>User not registered due to some error</h3>");
                RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");
                rd.include(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setContentType("text/html");
            out.print("<h3 style='color:red'>Exception occurred: " + e.getMessage() + "</h3>");
            RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");
            rd.include(req, resp);
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
