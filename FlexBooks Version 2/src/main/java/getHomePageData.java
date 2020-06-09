/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 *
 * @author ukwa
 */
public class getHomePageData extends HttpServlet{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession session = request.getSession(true);
        
        try(PrintWriter out = response.getWriter()){
            try {
                out.print(
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "	<title>Welcome to Off-Da-Hook Flexbooks</title>\n" +
                "	<link href=\"root.css\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "	<link href=\"home_aboutUs.css\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "	<script src=\"file.js\"></script>\n" +
                "</head>");
                out.flush();
                out.print(
                "<body class=\"background_image\">" +
                "<div align=center><img style=\"width:80%; border:none;\" src=\"./images/topBanner.png\"></div>" +
                "<div class=\"top_menu\">" +
                "  <a class=\"current\" href=\"home\">Home</a>" +
                "  <a href=\"aboutUs.html\">About Us</a>\n" +
                "  </div>");
                out.flush();
                
                RequestDispatcher rd=request.getRequestDispatcher("getRecentlyViewed");  
                rd.include(request, response);
                
                out.flush();
                
                out.print(
                "<div class=\"container1\" style=\"display:flex; margin-top:10px;\">\n" +
                "   <div class=\"grid\">\n" +
                "       <h1 align=\"left\">Our Collection</h1>");
                
                Class.forName("com.mysql.cj.jdbc.Driver");
                try {
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/flexbooksdb?zeroDateTimeBehavior=CONVERT_TO_NULL", "root", "");
                    Statement statement = con.createStatement();
                    ResultSet rs = statement.executeQuery("SELECT ID,title,image,price,author FROM Textbooks");
                    while (rs.next()){
                        String author = rs.getString("author");
                        if (author.length() > 40)
                                author = author.substring(0,40) + "...";
                        String title = rs.getString("title");
                        if (title.length() > 40)
                                title = title.substring(0,40) + "...";
                        out.print(
                         "<div class=\"cell\">" +
"			<a id=" + rs.getString("id") + "\" href=\"./itempage.html\" onclick=\"setVar(" + rs.getString("id") + ")\">\n" +
"				<img class=\"zoom\" src=\"" + rs.getString("image") + "\" alt=\"book\", height=\"270\" width=\"270\">\n" +
"			</a>" +
"				<h2>"+title+"</h2>\n" +
"				<h5>"+author+"</h5>\n" +
"				<h5>"+rs.getString("price")+"</h5>\n" +
"                       </div>");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(getHomePageData.class.getName()).log(Level.SEVERE, null, ex);
                    out.println("Error 1:" + ex);
                }
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(getHomePageData.class.getName()).log(Level.SEVERE, null, ex);
                out.println("Error 2: " + ex);
            }
                out.print(
                    "</div> " +
                "</div>");
                
                out.print(
                "<div class=\"container3\" style=\"display:flex; margin-top:10px;\">" +
                    "<div class=\"grid\">" +
                    "   <p><b>Created By:</b> Ukwa Akkum (68576133), Angela Do (86236858), Andres Garcia (19936980), Andy Yang (32972683)</p>\n" +
                    "</div>" +
                "</div>" +
                        "</body></html>");
                out.flush();
                
                System.out.println("1home page data");



            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
