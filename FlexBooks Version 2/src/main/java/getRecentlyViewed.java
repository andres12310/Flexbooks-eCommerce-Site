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
public class getRecentlyViewed extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        PrintWriter out = response.getWriter();
            try {
                
                /* TODO output your page here. You may use following sample code. */
//                Class.forName("com.mysql.jdbc.Driver");
                //Class.forName("org.gjt.mm.mysql.Driver");
                
                Class.forName("com.mysql.cj.jdbc.Driver");
                ArrayList<String> items;
                //Class.forName("com.mysql.fabric.jdbc.FabricMySQLDriver");
//                String ID = request.getParameter("id");
//                RequestDispatcher rd=request.getRequestDispatcher("/home.html");  
//                rd.include(request, response);
                System.out.println("2home page data");
                out.flush();

                try {
                    HttpSession session = request.getSession(false);
                    if (session.getAttribute("itemsViewed") != null){
                        items = (ArrayList<String>)session.getAttribute( "itemsViewed" );
                        System.out.println("items array: " + items);
                    }
                    else{
                        items = new ArrayList<String>();
                        
                    }
                    
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/flexbooksdb?zeroDateTimeBehavior=CONVERT_TO_NULL", "root", "");                    
                    System.out.println("connection opened");
                    Statement statement = con.createStatement();
                    
                    int items_len = items.size() > 5 ? 5 : items.size();
                    if (items_len > 0)
                        out.print(
                        "<div class=\"container2\" style=\"display:flex; margin-top:10px;\">" +
                        "<div class=\"grid\">" +
                        "<h1 align=\"right\">Recently Viewed Textbooks</h1>"
                        );
                    for (int i = 0; i < items_len; ++i){
       
                        ResultSet rs = statement.executeQuery("SELECT ID, title,image,price,author FROM textbooks WHERE ID=" + Integer.parseInt(items.get(i)));
                        while (rs.next()){
                            //title, image, price, author, edition, type, page_count, publisher, language, isbn_10, isbn_13, dimensions, weight, quantity
                            int ID = rs.getInt("ID");
                            String title = rs.getString("title");
                            String image = rs.getString("image"); 
                            String price = rs.getString("price");
                            String author = rs.getString("author");
                            if (author.length() > 40)
                                author = author.substring(0,40) + "...";
                            if (title.length() > 40)
                                title = title.substring(0,40) + "...";
                            String result = String.format("%s;%s;%s;%s;", title,image, price, author);
                            out.print("<div class=\"cell\"><a id ='"+Integer.toString(ID)+"' href='./itempage.html' onclick='setVar("+Integer.toString(ID)+")'>"+ 
                            "<img class='zoom' id = 'image1' src="+image+" alt='book', height='270', width='270'> </a>" +
                            "<h2>"+title+"</h2> <h5>"+author+"</h5> <h5>"+price+"</h5>" +"</div>");
                            out.flush();
                            
                            if (i == items_len-1)
                                out.print(
                                "   </div> " +
                                "</div>");

                        }

                    }

                
                out.flush();
                statement.close();
                con.close();
                System.out.println("connection closed");
//            out.println("Title;./images/CampbellBiology.jpg;Price;Author;Edition;Type;Pagecount;publisher;language;isbn-10;isb-13;simensions;weightt;quantity");
                } catch (SQLException ex) {
                    Logger.getLogger(getHomePageData.class.getName()).log(Level.SEVERE, null, ex);
                    out.println("Error 1:" + ex);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(getHomePageData.class.getName()).log(Level.SEVERE, null, ex);
                out.println("Error 2: " + ex);
            }
              //out.println("Title;./images/CampbellBiology.jpg;Priceeeeee;Author;Edition;Type;Pagecount;publisher;language;isbn-10;isb-13;simensions;weightt;quantity");
              
            
//        }
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

