import com.inf124.flexbooksrestservice.model.Book;
import com.inf124.flexbooksrestservice.model.Order;
import com.inf124.flexbooksrestservice.service.OrderService;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
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
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.glassfish.jersey.client.ClientConfig;

/**
 *
 * @author deezydo
 */
public class ConfirmationServlet extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target(getBaseURI());
        ObjectMapper objectMapper = new ObjectMapper(); // This object is from the jackson library
        
        ArrayList<String> items;
        HttpSession session = request.getSession(false);
        
        if (session.getAttribute("itemsInCart") != null) {
            items = (ArrayList<String>)session.getAttribute( "itemsInCart" );
            System.out.println("(checkout) items in cart: " + items);
        }
        else {
            items = new ArrayList<>();   
        }
        
        PrintWriter out;
        out = response.getWriter();
        String jsonResponse;
        Set<String> converted_items = new HashSet<>(items);
        double total_price = (double)session.getAttribute("total_price");
        int total_quantity = 0;
        
        request.getRequestDispatcher("./confirmation.html").include(request, response);
        
        for (String ID: converted_items) {
            System.out.println("(checkout) Current ID: " + ID);
            jsonResponse = target.path("v1").path("api").path("books").path(ID).
                    request().
                    accept(MediaType.APPLICATION_JSON).
                    get(String.class);
            System.out.println("(checkout) JSON Response: " + jsonResponse);
            
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
            int order_quantity = Collections.frequency(items, ID);
            
            // print HTML to page
            out.println("<div class='row'><div class='column'>" +
                    "<a id=' " + ID + "' href='./itempage.html' onclick='setVar(this.id)'>" + 
                    "<img class='zoom' src='" + image + "' alt='book', height='270' width='270'></a></div>");
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
                    "</h5></div></div>");
            
            // calculate total quantity
            total_quantity += order_quantity;
        }
        
        // update db       
        Order order = new Order();
        order.setISBN("978-0321982384");
        order.setQuantity(Integer.toString(total_quantity));
        order.setFirstName(request.getParameter("firstName"));
        order.setLastName(request.getParameter("lastName"));
        order.setEmail(request.getParameter("email"));
        order.setPhoneNumber(request.getParameter("phoneNo"));
        order.setAddress(request.getParameter("address"));
        order.setApt(request.getParameter("aptNo"));
        order.setCity(request.getParameter("city"));
        order.setState(request.getParameter("state"));
        order.setZipCode(request.getParameter("zipCode"));
        order.setShippingSpeed(request.getParameter("shipping"));
        order.setCreditCard(request.getParameter("cardnum"));
        order.setCardExpiration("expDate");
        order.setCVV(request.getParameter("cvv"));
        OrderService.addOrder(order);
        
        
        // print confirmation details
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
                "<strong>Total Books Ordered: </strong>" + Integer.toString(total_quantity) + "<br>" +
                "<strong>Total Price: </strong>$" + Double.toString(total_price) + "</h5><br>" + 
                "<h5 align=\"center\" style=\"text-decoration:underline;\">Thank you, " + request.getParameter("firstName") + " " + request.getParameter("lastName") + "!</h1></div>");
        
        
        session.removeAttribute("itemsInCart");
        
        
        
    }
    
    
    private static URI getBaseURI() {

        //Change the URL here to make the client point to your service.
        return UriBuilder.fromUri("http://localhost:8080/FlexbooksRestService").build();
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

