import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ProductServlet", value = "/display-discount")
public class ProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        float price= Float.parseFloat(request.getParameter("price"));
        float discount= Float.parseFloat(request.getParameter("discount"));

        double discountAmount= price * discount * 0.01;
        double discountPrice= price - discountAmount;

        PrintWriter writer= response.getWriter();
        writer.println("<html>");

        writer.println("Discount Amount: " + discountAmount);
        writer.println("<br>");
        writer.println("Discount price: " + discountPrice);

        writer.println("</html>");


    }
}
