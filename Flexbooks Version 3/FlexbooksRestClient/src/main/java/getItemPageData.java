/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

//import com.inf124.flexbooksrestservice.model.Book;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference; 
import org.glassfish.jersey.client.ClientConfig;


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

        ClientConfig config = new ClientConfig();

        Client client = ClientBuilder.newClient(config);

        WebTarget target = client.target(getBaseURI());
        
        ObjectMapper objectMapper = new ObjectMapper();
        
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()){
            try {
                
                /* TODO output your page here. You may use following sample code. */
                
                Class.forName("com.mysql.cj.jdbc.Driver");
                //Class.forName("com.mysql.fabric.jdbc.FabricMySQLDriver");
                String ID = request.getParameter("id");
                System.out.println(ID);

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
                String jsonResponse =
                        target.path("v1").path("api").path("books").path(ID).
                                request(). //send a request
                                accept(MediaType.APPLICATION_JSON). //specify the media type of the response
                                get(String.class); // use the get method and return the response as a string
                Book book = objectMapper.readValue(jsonResponse, new TypeReference<Book>(){});
                String title = book.getTitle();
                String image = book.getImage();
                String price = book.getPrice();
                String author = book.getAuthor();
                int edition = book.getEdition();
                String type = book.getType();
                String pageCount = book.getPageCount();
                String publisher = book.getPublisher();
                String language = book.getLanguage();
                String isbn_10 = book.getIsbn10();
                String isbn_13 = book.getIsbn13();
                String dimensions = book.getProductDimensions();
                String weight = book.getShippingWeight();
                String quantity = Integer.toString(book.getQuantity());
                String result = String.format("%s;%s;%s;%s;%d;%s;%s;%s;%s;%s;%s;%s;%s;%s", title,image, price, author, edition, type, pageCount, publisher, language, isbn_10, isbn_13, dimensions, weight, quantity);
                out.println(result);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(getItemPageData.class.getName()).log(Level.SEVERE, null, ex);
                out.println("Error 2: " + ex);
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
    
    private static URI getBaseURI() {

        //Change the URL here to make the client point to your service.
        return UriBuilder.fromUri("http://localhost:8080/FlexbooksRestService").build();
    }
    
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
