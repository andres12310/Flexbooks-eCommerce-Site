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
public class checkout extends HttpServlet {

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
            
            // help from: https://www.studytonight.com/servlet/request-dispatcher.php
            // allows us to import html file into the servlet
            RequestDispatcher rd = request.getRequestDispatcher("./checkout.html");
            rd.include(request, response);
            HttpSession session = request.getSession(true);
            ArrayList<String> array = (ArrayList<String>)session.getAttribute( "itemsInCart" );
            //Hashtable<String, String> book_storage = new Hashtable<String, String>(); // {isbn13:quantity}
            //out.println("Items in cart: ");
            //out.println(array);
            //out.println("<br><br>");
            double subtotal = 0.0;
            
            // database shit
            if (!array.isEmpty()) {
                try { // open db driver
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    try { // connect to db
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/flexbooksdb?zeroDateTimeBehavior=CONVERT_TO_NULL", "root", "");
                        System.out.println("connection opened");
                        
                        Set<String> converted_array = new HashSet<String>(array);
                        session.setAttribute("converted_array", converted_array);

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
                                //book_storage.put(isbn_13, Integer.toString(order_quantity));
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
                                System.out.println(result);
                            }
                            statement.close();
                        }
                        
                    // create form 
                    out.println("</div></div><div class=\"container2\" style=\"display:flex; margin-top:10px;\">" + 
                            "<div class='grid'>" + 
                            "<h1 align=\"left\">Purchase Form</h1><br><br>" +
                            "<form name=\"purchase-form\" action=\"./validation\" method=\"post\">" +
				"<label for=\"firstName\">First Name:</label><input type=\"text\" name=\"firstName\" placeholder=\"Peter\"><br>" +
				"<label for=\"lastName\">Last Name:</label><input type=\"text\" name=\"lastName\" placeholder=\"Anteater\"><br>" +
				"<label for=\"email\">Email:</label><input type=\"text\" name=\"email\" placeholder=\"peter.the.anteater@website.com\"><br>" +
				"<label for=\"phoneNo\">Phone Number:</label><input type=\"number\" name=\"phoneNo\" placeholder=\"0000000000\"><br>" +
				"<label for=\"address\">Address:</label><input type=\"text\" name=\"address\" placeholder=\"123 Street Name\"><br>" +
				"<label for=\"aptNo\">Apt/Ste Number:</label><input type=\"text\" name=\"aptNo\" style=\"width:50px\" placeholder=\"0\"><br>" +
				"<label for=\"zipCode\">Zip Code:</label><input type=\"text\" name=\"zipCode\" placeholder=\"00000 or 00000-0000\"><br>" +
				"<label for=\"city\">City:</label><input type=\"text\" name=\"city\" placeholder=\"Irvine\"><br>" +
				"<label for=\"state\">State:</label><select name=\"state\">" + 
					"<option value =\"default\"> </option>" + 
					"<option value=\"AL\">Alabama (AL)</option>" +
					"<option value=\"AK\">Alaska (AK)</option>" +
					"<option value=\"AZ\">Arizona (AZ)</option>" +
					"<option value=\"AR\">Arkansas (AR)</option>" +
					"<option value=\"CA\">California (CA)</option>" +
					"<option value=\"CO\">Colorado (CO)</option>" +
					"<option value=\"CT\">Connecticut (CT)</option>" +
					"<option value=\"DE\">Delaware (DE)</option>" +
					"<option value=\"DC\">District Of Columbia (DC)</option>" +
					"<option value=\"FL\">Florida (FL)</option>" +
					"<option value=\"GA\">Georgia (GA)</option>" +
					"<option value=\"HI\">Hawaii (HI)</option>" +
					"<option value=\"ID\">Idaho (ID)</option>" +
					"<option value=\"IL\">Illinois (IL)</option>" +
					"<option value=\"IN\">Indiana (IN)</option>" +
					"<option value=\"IA\">Iowa (IA)</option>" +
					"<option value=\"KS\">Kansas (KS)</option>" +
					"<option value=\"KY\">Kentucky (KY)</option>" +
					"<option value=\"LA\">Louisiana (LA)</option>" +
					"<option value=\"ME\">Maine (ME)</option>" +
					"<option value=\"MD\">Maryland (MD)</option>" +
					"<option value=\"MA\">Massachusetts (MA)</option>" +
					"<option value=\"MI\">Michigan (MI)</option>" +
					"<option value=\"MN\">Minnesota (MN)</option>" +
					"<option value=\"MS\">Mississippi (MS)</option>" +
					"<option value=\"MO\">Missouri (MO)</option>" +
					"<option value=\"MT\">Montana (MT)</option>" +
					"<option value=\"NE\">Nebraska (NE)</option>" +
					"<option value=\"NV\">Nevada (NV)</option>" +
					"<option value=\"NH\">New Hampshire (NH)</option>" +
					"<option value=\"NJ\">New Jersey (NJ)</option>" +
					"<option value=\"NM\">New Mexico (NM)</option>" +
					"<option value=\"NY\">New York (NY)</option>" +
					"<option value=\"NC\">North Carolina (NC)</option>" +
					"<option value=\"ND\">North Dakota (ND)</option>" +
					"<option value=\"OH\">Ohio (OH)</option>" +
					"<option value=\"OK\">Oklahoma (OK)</option>" +
					"<option value=\"OR\">Oregon (OR)</option>" +
					"<option value=\"PA\">Pennsylvania (PA)</option>" +
					"<option value=\"RI\">Rhode Island (RI)</option>" +
					"<option value=\"SC\">South Carolina (SC)</option>" +
					"<option value=\"SD\">South Dakota (SD)</option>" +
					"<option value=\"TN\">Tennessee (TN)</option>" +
					"<option value=\"TX\">Texas (TX)</option>" +
					"<option value=\"UT\">Utah (UT)</option>" +
					"<option value=\"VT\">Vermont</option>" +
					"<option value=\"VA\">Virginia</option>" +
					"<option value=\"WA\">Washington</option>" +
					"<option value=\"WV\">West Virginia</option>" +
					"<option value=\"WI\">Wisconsin</option>" +
					"<option value=\"WY\">Wyoming</option></select><br>" +
				"<label for=\"shipping\">Shipping Speed:</label><select name=\"shipping\">" +
					"<option value=\"default\">Select a speed</option>" +
					"<option value=\"Overnight\">Overnight (1 day)</option>" +
					"<option value=\"Expedited\">Expedited (2 days)</option>" +
					"<option value=\"Ground Shipping\">Ground Shipping (5-6 days)</option></select><br>" +
				"<label for=\"cardnum\">Credit Card Number:</label><input type=\"number\" name=\"cardnum\" placeholder=\"1111222233334444\"><br>" +
				"<label for=\"expDate\">Card Expiration Date:</label><input type=\"number\" style=\"width:50px;\" name=\"expDate\" placeholder=\"MMYY\"><br>" +
				"<label for=\"cvv\">CVV:</label><input type=\"number\" style=\"width:40px;\" name=\"cvv\" size=\"2\" placeholder=\"0000\"><br>" +
				"<hr>" +
				"<p id=\"subtotal\" value=0 style=\"text-align: right\">Total: $" + subtotal + "</p>" +
				"<center><input type=\"submit\" name=\"validate\" value=\"Purchase\"></center>" +
				"<br></form>");
                    //Statement statement2 = con.createStatement();
                    //ResultSet rs2 = statement2.executeQuery(sql);
                    //String firstName = request.getParameter("firstName");
                    //System.out.println("First name: "+ firstName);
                    
                    
                    con.close();
                    System.out.println("connection closed");  
                    } catch(SQLException ex){
                        Logger.getLogger(getItemPageData.class.getName()).log(Level.SEVERE, null, ex);
                        out.println("Error 1:" + ex);
                    }
                } catch(ClassNotFoundException ex) {
                    Logger.getLogger(getItemPageData.class.getName()).log(Level.SEVERE, null, ex);
                    out.println("Error 2: " + ex);
                }
            }
            session.setAttribute("subtotal", subtotal);
            out.println("</div></div>");
            
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
