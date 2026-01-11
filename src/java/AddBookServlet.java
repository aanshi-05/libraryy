import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.CallableStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/addbook")
public class AddBookServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String title = request.getParameter("title");
        String author = request.getParameter("author");
        int year = Integer.parseInt(request.getParameter("year"));
        int available = Integer.parseInt(request.getParameter("available"));

        String query = "{ CALL AddBook(?, ?, ?, ?) }"; // Calling the stored procedure

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(query)) {

            stmt.setString(1,title);
            stmt.setString(2, author);
            stmt.setInt(3, year);
            stmt.setInt(4, available);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                out.println("<p>Book added successfully!</p>");
                out.println("<a href='viewbooks.html'>View Books</a>");
            } else {
                out.println("<p>Error: Book not added. Please try again.</p>");
            }
        } catch (Exception e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
            e.printStackTrace();
        }
    }
}
