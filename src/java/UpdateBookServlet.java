import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@WebServlet("/updatebook")
public class UpdateBookServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Debugging: Print all received request parameters
        System.out.println("=== Received Parameters ===");
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            System.out.println(paramName + " = " + request.getParameter(paramName));
        }
        System.out.println("==========================");

        // Get parameters
        String idParam = request.getParameter("id");
        String title = request.getParameter("title");
        String author = request.getParameter("author");

        // Debugging: Print values before validation
        System.out.println("idParam: " + idParam);
        System.out.println("title: " + title);
        System.out.println("author: " + author);

        // Validate input
        if (idParam == null || idParam.trim().isEmpty()) {
            out.println("<h3>Error: Book ID is required!</h3>");
            return;
        }
        if (title == null || title.trim().isEmpty()) {
            out.println("<h3>Error: Title is required!</h3>");
            return;
        }
        if (author == null || author.trim().isEmpty()) {
            out.println("<h3>Error: Author is required!</h3>");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);

            // Database Update
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "UPDATE books SET title=?, author=? WHERE id=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, title);
                stmt.setString(2, author);
                stmt.setInt(3, id);

                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    out.println("<h3>Book updated successfully!</h3>");
                } else {
                    out.println("<h3>Book not found!</h3>");
                }
            }
        } catch (NumberFormatException e) {
            out.println("<h3>Error: Invalid Book ID format!</h3>");
            e.printStackTrace();
        } catch (Exception e) {
            out.println("<h3>Error updating book: " + e.getMessage() + "</h3>");
            e.printStackTrace();
        }
    }
}
