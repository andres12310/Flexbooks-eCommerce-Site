/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author asus
 */
public class getItemPageData extends HttpServlet {

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
        
        try (PrintWriter out = response.getWriter()){
            try {
                
                /* TODO output your page here. You may use following sample code. */
//                Class.forName("com.mysql.jdbc.Driver");
                //Class.forName("org.gjt.mm.mysql.Driver");
                
                Class.forName("com.mysql.cj.jdbc.Driver");
                //Class.forName("com.mysql.fabric.jdbc.FabricMySQLDriver");
                String ID = request.getParameter("id");
                System.out.println(ID);

                try {
                    HttpSession session = request.getSession(true);
                    if (session.getAttribute("itemsViewed") != null){
                        ArrayList<String> array = (ArrayList<String>)session.getAttribute( "itemsViewed" );
                        if(!array.contains(ID)){
                            array.add(0, ID);
                        }
                        else { 
                            array.remove(array.indexOf(ID));
                            array.add(0,ID);
                        }
                        if (array.size() > 5){
                            array.remove(5);
                        }
                        System.out.println("array: " + array);
                    }
                    else{
                        ArrayList<String> newArray = new ArrayList<String>(); 
                        newArray.add(ID);
                        System.out.println("new array: " + newArray);
                        session.setAttribute("itemsViewed", newArray);
                        
                    }
                    
                    session.setAttribute("currentItemID", ID);
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/flexbooksdb?zeroDateTimeBehavior=CONVERT_TO_NULL", "root", "");
                    System.out.println("connection opened");
                    Statement statement = con.createStatement();
                    ResultSet rs = statement.executeQuery("SELECT * FROM textbooks WHERE ID=" + ID);
                    while (rs.next()){
                        //title, image, price, author, edition, type, page_count, publisher, language, isbn_10, isbn_13, dimensions, weight, quantity
                        //int ID = rs.getInt("ID");
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
                        out.println(result);
                        System.out.println(result);
                        
                    }
                    
                    statement.close();
                    con.close();
                    System.out.println("connection closed");
//            out.println("Title;./images/CampbellBiology.jpg;Price;Author;Edition;Type;Pagecount;publisher;language;isbn-10;isb-13;simensions;weightt;quantity");
                } catch (SQLException ex) {
                    Logger.getLogger(getItemPageData.class.getName()).log(Level.SEVERE, null, ex);
                    out.println("Error 1:" + ex);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(getItemPageData.class.getName()).log(Level.SEVERE, null, ex);
                out.println("Error 2: " + ex);
            }
              //out.println("Title;./images/CampbellBiology.jpg;Priceeeeee;Author;Edition;Type;Pagecount;publisher;language;isbn-10;isb-13;simensions;weightt;quantity");

            
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
