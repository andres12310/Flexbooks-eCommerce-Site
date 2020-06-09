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
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
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
 * @author deezydo
 */
public class confirmation extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            RequestDispatcher rd = request.getRequestDispatcher("./confirmation.html");
            rd.include(request, response);
            HttpSession session = request.getSession(true);
            ArrayList<String> array = (ArrayList<String>)session.getAttribute( "itemsInCart" );
            double subtotal = (double)session.getAttribute("subtotal");
            int total_quantity = 0;
            // database shit
            if (!array.isEmpty()) {
                try { // open db driver
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    try { // connect to db
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/flexbooksdb?zeroDateTimeBehavior=CONVERT_TO_NULL", "root", "");
                        System.out.println("connection opened");
                        
                        Set<String> converted_array = new HashSet<String>(array);
                        for (String i : converted_array) {
                            String ID = i;
                            // gets amount of occurences of ID in array list
                            int order_quantity = Collections.frequency(array, ID);
                            Statement statement = con.createStatement();
                            ResultSet rs = statement.executeQuery("SELECT * FROM textbooks WHERE ID=" + ID);
                            while (rs.next()){
                                // retrieving database fields
                                String title = rs.getString("title");
                                String image = rs.getString("image"); 
                                String price = rs.getString("price");
                                String author = rs.getString("author");
                                int edition = rs.getInt("edition");
                                String type = rs.getString("type");
                                String pageCount = rs.getString("pageCount");
                                String publisher = rs.getString("title");
                                String language = rs.getString("language");
                                String isbn_10 = rs.getString("ISBN10");
                                String isbn_13 = rs.getString("ISBN13");
                                String dimensions = rs.getString("productDimensions");
                                String weight = rs.getString("shippingWeight");
                                String quantity = rs.getString("quantity");
                                String result = String.format("%s;%s;%s;%s;%d;%s;%s;%s;%s;%s;%s;%s;%s;%s", title,image, price, author, edition, type, pageCount, publisher, language, isbn_10, isbn_13, dimensions, weight, quantity);
                                // print to HTML page
                                out.println("<div class='row'><div class='column'>" +
                                        "<a id=' " + ID + "' href='./itempage.html' onclick='setVar(this.id)'>" + 
                                        "<img class='zoom' src='" + image + "' alt='book', height='270' width='270'></a></div>");
                                //out.println("<img src='" + image + "'> <br><br>");
                                out.println("<div class='column'><h5><strong>Title:</strong> " + title + "<br>" +
                                        "<strong>Price:</strong> " + price + "<br>" +
                                        "<strong>Author:</strong> " + author + "<br>" +
                                        "<strong>Edition:</strong> " + Integer.toString(edition) + "<br>" +
                                        "<strong>Type:</strong> " + type + "<br>" +
                                        "<strong>Page Count:</strong> " + pageCount + "<br>" +
                                        "<strong>Publisher:</strong> " + publisher + "<br>" +
                                        "<strong>Language:</strong> " + language + "<br>" +
                                        "<strong>ISBN-10:</strong> " + isbn_10 + "<br>" +
                                        "<strong>ISBN-13:</strong> " + isbn_13 + "<br>" +
                                        "<strong>Dimensions:</strong> " + dimensions + "<br>" +
                                        "<strong>Weight:</strong> " + weight + "<br>" + 
                                        "<strong>Order Quantity: </strong>" + Integer.toString(order_quantity) +
                                        "</h5></div></div>"
                                ); 
                                subtotal += (Double.parseDouble(price.substring(1)) * order_quantity);
                                total_quantity += order_quantity;
                                System.out.println(result);
                            }
                            statement.close();
                        }
                        Statement insert_statement = con.createStatement();
                        insert_statement.executeUpdate("INSERT INTO Orders (isbn, quantity, firstName, lastName, email, phoneNumber, address, apt, city, state, zipCode, shippingSpeed, creditCard, cardExpiration, cvv)" + 
                                "VALUES ('978-0321982384'" +
                                ", " + total_quantity + 
                                ", '" + request.getParameter("firstName") + "'" +
                                ", '" + request.getParameter("lastName") + "'" +
                                ", '" + request.getParameter("email") + "'" +
                                ", '" + request.getParameter("phoneNo") + "'" +
                                ", '" + request.getParameter("address") + "'" +
                                ", '" + request.getParameter("aptNo") + "'" +
                                ", '" + request.getParameter("city") + "'" +
                                ", '" + request.getParameter("state") + "'" +
                                ", '" + request.getParameter("zipCode") + "'" +
                                ", '" + request.getParameter("shipping") + "'" +
                                ", '" + request.getParameter("cardnum") + "'" +
                                ", '" + request.getParameter("expDate") + "'" +
                                ", '" + request.getParameter("cvv") + "')");
                            insert_statement.close();
                            System.out.println("successfully added to db");
                        
                    // create form 
                    out.println("</div></div><div class=\"container2\" style=\"display:flex; margin-top:10px;\">" + 
                            "<div class='grid'>" + 
                            "<h1 align=\"left\">Shipping Information</h1><br><br>" +
                            "<div class=\"cell\"><h5><strong>Name:</strong> " + request.getParameter("firstName") + " " + request.getParameter("lastName") + "<br>" +
                            "<strong>Email:</strong> " + request.getParameter("email") + "<br>" +
                            "<strong>Phone Number: </strong>" + request.getParameter("phoneNo") + "<br>" + 
                            "<strong>Address: </strong>" + request.getParameter("address") + "<br>" +
                            "<strong>Apt Number: </strong>" + request.getParameter("aptNo") + "<br>" +
                            "<strong>City: </strong>" + request.getParameter("city") + "<br>" +
                            "<strong>State: </strong>" + request.getParameter("state") + "<br>" +
                            "<strong>Zip Code: </strong>" + request.getParameter("zipCode") + "<br>" +
                            "<strong>Shipping: </strong>" + request.getParameter("shipping") + "<br>" + 
                            "<strong>Card Number: </strong>" + request.getParameter("cardnum") + "<br>" + 
                            "<strong>Card Exp Date: </strong>" + request.getParameter("expDate") + "<br>" + 
                            "<strong>CVV: </strong>" + request.getParameter("cvv") + "<br>" +
                            "<strong>Total Books Ordered: </strong>" + Integer.toString(total_quantity) + "</h5><br>" + 
                            "<h5 align=\"center\" style=\"text-decoration:underline;\">Thank you, " + request.getParameter("firstName") + " " + request.getParameter("lastName") + "!</h1></div>");
                               
                    con.close();
                    System.out.println("connection closed");  
                    } catch(SQLException ex){
                        Logger.getLogger(confirmation.class.getName()).log(Level.SEVERE, null, ex);
                        out.println("Error 1:" + ex);
                    }
                } catch(ClassNotFoundException ex) {
                    Logger.getLogger(confirmation.class.getName()).log(Level.SEVERE, null, ex);
                    out.println("Error 2: " + ex);
                }
            }
            out.println("</div></div>");
            session.removeAttribute("itemsInCart");
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
