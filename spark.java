package bopow;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;  

@WebServlet(name = "WelcomeServlet", urlPatterns = {"/welcome"})
public class spark extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	
        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();
  pw.println("<style>body { background-image: url('https://media.istockphoto.com/id/1061786950/photo/sky-in-the-pink-and-blue-colors-effect-of-light-pastel-colored-of-sunset-clouds-cloud-on-the.webp?b=1&s=170667a&w=0&k=20&c=NHX4Y9h374Mjp73IolBTVXGJGtqCjjt_Hiq5cAoWcTI='); background-size: cover; background-color: rgba(255, 255, 255, 0.5)}</style>");
  pw.println("<style>");
  pw.println("body {");
  pw.println("  height: 100vh;"); // Set the body height to full viewport height
  pw.println("  flex-direction: column;"); // Arrange items vertically
  pw.println("  justify-content: center;"); // Center align horizontally
  pw.println("  align-items: center;"); // Center align vertically
  
  pw.println("position: absolute;"
  		+ "    top:30%;"
  		+ "    left:30%");
  pw.println("}");
  pw.println("</style>");
  
  

  
        int bas = Integer.parseInt(req.getParameter("bas"));
        pw.println("<h1><center> Your Basic Salary Is " + bas +"<br></center></h1>");

        int hra = Integer.parseInt(req.getParameter("hra"));
        pw.println("<h1><center> Your Actual House Rent Allowance Is " + hra+"<br></center></h1>");

        int r = Integer.parseInt(req.getParameter("r"));
        pw.println("<h1><center> Your Actual Rent Is " + r +"<br></center></h1>");
        
        int q=0;
        double w = (r - (0.1) * bas);
        double k1 = (0.5) * bas;

        

        if ((k1 > hra) && (w > hra)) {
            q = hra;
            System.out.println(hra);
        } else if ((k1 > w) && (hra > w)) {
            q = (int) w;
            System.out.println(w);
        } else if ((w > k1) && (hra > k1)) {
            q = (int) k1;
            System.out.println(k1);
        }

        // No need to reassign q from request parameter here
        pw.println("<h1><center> Exempted House Rent Allowance " + q +"<br> </center></h1>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/proj", "root", "Monkey@7894");

            PreparedStatement stmt = con.prepareStatement("insert into TheHRA values(?,?,?,?)");
            stmt.setInt(1, bas);
            stmt.setInt(2, hra);
            stmt.setInt(3, r);
            stmt.setInt(4, q);

            int i = stmt.executeUpdate();
            

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

		pw.close();
    }
}
