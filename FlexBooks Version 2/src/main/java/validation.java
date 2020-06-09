/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author asus
 */
public class validation extends HttpServlet {

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
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String phoneNo = request.getParameter("phoneNo");
            String address = request.getParameter("address");
            String aptNo = request.getParameter("aptNo");
            String city = request.getParameter("city");
            String state = request.getParameter("state");
            String zipCode = request.getParameter("zipCode");
            String shipping = request.getParameter("shipping");
            String cardnum = request.getParameter("cardnum");
            String expDate = request.getParameter("expDate");
            String cvv = request.getParameter("cvv");
            
            
            
            // tells user what they need to correct in the form purposes
            String alertMessage = "";
            
            // validate firstName
            if (!Pattern.matches("^[a-zA-Z]+(['-][a-zA-Z]+)*$", firstName)) {
                alertMessage += "- First name<br>";
            }
            // validating lastName string
            if (!Pattern.matches("^[a-zA-Z]+([\\s'-][a-zA-Z]+)*$", lastName)) {
                    alertMessage += "- Last Name<br>";
            }
            // validating email string
            if (!Pattern.matches("^[a-zA-Z0-9]([a-zA-Z0-9]*([\\._-][a-zA-Z0-9]+)?)*@[a-zA-Z0-9]([a-zA-Z0-9]*([-][a-zA-Z0-9])?)*\\.[a-zA-Z]{2,3}$", email)) {
                    alertMessage += "- Email<br>";
            }
            // validating phoneNo number
            if (!Pattern.matches("^1?[2-9][\\d]{9}$", phoneNo)) {
                    alertMessage += "- Phone Number<br>";
            }
            // validating address string
            if (!Pattern.matches("^[1-9][\\d]{0,4}(\\s([1-9][\\d]*)*[a-zA-Z]+.?)+$", address)) {
                    alertMessage += "- Street Address<br>";
            }
            // validating aptNo string
            if (!Pattern.matches("^(\\s*|[a-zA-Z0-9]+(-?[a-zA-Z0-9])*)$", aptNo)) {
                    alertMessage += "- Apartment Number<br>";
            }
            // validating city string
            if (!Pattern.matches("^[a-zA-Z]{3,}([-\\s][a-zA-Z]{3,})*$", city)) {
                    alertMessage += "- City<br>";
            }
            // validating state option
            if (Pattern.matches("^default$", state)) {
                    alertMessage += "- State<br>";
            }
            // validating zipCode number
            if (!Pattern.matches("^\\d{4,5}(-\\d{4})?$", zipCode)){
                    alertMessage += "- Zip Code<br>";
            }
            // validating shipping string
            if (Pattern.matches("^default$", shipping)){
                    alertMessage += "- Shipping Speed<br>";
            }
            // validating cardnum number
            if (!Pattern.matches("^[\\d]{16}$", cardnum)){
                    alertMessage += "- Card Number<br>";
            }
            // validating expDate number
            if (!Pattern.matches("^((0[5-9]|1[0-2])2[0-4]|(0[1-9]|1[0-2])2[1-4])$", expDate)){
                    alertMessage += "- Credit Card Expiration Date<br>";
            }
            // validating cvv number
            if (!Pattern.matches("^[\\d]{3,4}$", cvv)){
                    alertMessage += "- CVV Number<br>";
            }
                        
            if (alertMessage.length() > 0) {
                out.println("<head><title>Error | Off-Da-Hook Flexbooks</title>"
                        + "<link href=\"root.css\" rel=\"stylesheet\" type=\"text/css\">"
                        + "<link href=\"home_aboutUs.css\" rel=\"stylesheet\" type=\"text/css\">"
                        + "</head><body class=\"background_image\">" 
                        + "<div align=center><img style=\"width:80%; border:none;\" src=\"./images/topBanner.png\"></div>"
                        + "<div class=\"top_menu\">" 
                        + "  <a href=\"home\">Home</a>\n"  
                        + "  <a href=\"aboutUs.html\">About Us</a>\n" 
                        + "</div>\n" 
                        + "<div class=\"container1\" style=\"display:flex; margin-top:10px;\">"
                        + "	<div class=\"grid\">"
                        + "		<h1>Whoopsies!</h1>"
                        + "              <h1 style=\"text-decoration:none\">There was an error with your purchase form.</h1><br><br>");
                    out.println("<table class=\"description-text\" align=\"center\">\n" 
                            + "				<tr>					\n" 
                            + "					<td><center><p style=\"color:#84a9a7;\"><strong><u>Please go back and fix the following entries:</u></strong></p>"
                            + "<h5>" + alertMessage + "</h5></center>");
            }
            else {
                    // successful validation send to db
                    // forward to confirmation page
                    RequestDispatcher rd=request.getRequestDispatcher("confirmation");  
                    rd.forward(request, response);  
            }
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
