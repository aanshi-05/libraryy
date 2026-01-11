import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/homepage")
public class HomePageServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Library Management</title>");
        out.println("<style>");
        out.println("body {font-family: Arial, sans-serif; margin: 0; padding: 0;}");
        out.println("header {background: #343a40; color: white; padding: 15px; text-align: center;}");
        out.println(".container {display: flex;}");
        out.println(".sidebar {width: 20%; background: #6c757d; padding: 20px; height: 100vh;}");
        out.println(".sidebar a {display: block; color: white; padding: 10px; text-decoration: none;}");
        out.println(".sidebar a:hover {background: #495057;}");
        out.println(".content {flex-grow: 1; padding: 20px;}");
        out.println("</style></head><body>");

        out.println("<header><h1>Library Management System</h1></header>");
        out.println("<div class='container'>");
        out.println("<div class='sidebar'>");
        out.println("<a href='homepage'>Home</a>");
        out.println("<a href='viewbooks'>View Books</a>");
        out.println("<a href='addbookform'>Add Book</a>");
        out.println("<a href='updatebookform'>Update Book</a>");
        out.println("<a href='deletebookform'>Delete Book</a>");
        out.println("</div>");

        out.println("<div class='content'>");
        
        out.println("<p>Use the sidebar to navigate through the system.</p>");
        out.println("</div>");

        out.println("</div></body></html>");
    }
}
