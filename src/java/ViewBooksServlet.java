import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/viewbooks")
public class ViewBooksServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String role = (String) request.getSession().getAttribute("role");

        out.println("<h2>Library Books</h2>");
        out.println("<table border='1'>");
        out.println("<tr><th>ID</th><th>Title</th><th>Author</th><th>Year</th></tr>");

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) {
                out.println("<tr><td colspan='4' style='color:red;'>Database connection failed</td></tr>");
                return;
            }

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM books");
            ResultSet rs = stmt.executeQuery();

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                out.println("<tr>");
                out.println("<td>" + rs.getInt("id") + "</td>");
                out.println("<td>" + rs.getString("title") + "</td>");
                out.println("<td>" + rs.getString("author") + "</td>");
                out.println("<td>" + rs.getInt("year") + "</td>");
                out.println("</tr>");
            }

            if (!hasData) {
                out.println("<tr><td colspan='4' style='color:orange;'>No books found in the database</td></tr>");
            }
        } catch (Exception e) {
            out.println("<tr><td colspan='4' style='color:red;'>Error: " + e.getMessage() + "</td></tr>");
            e.printStackTrace();
        }

        out.println("</table>");
        if ("admin".equals(role)) {
             out.println("<br><a href='homepage.html'>Back to home</a>");
            
        }

       
        if ("user".equals(role)) {
            out.println("<br><a href='LogoutServlet'>Thank you</a>");
        }
    }
}
  
