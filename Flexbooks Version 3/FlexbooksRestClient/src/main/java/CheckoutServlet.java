import com.inf124.flexbooksrestservice.model.Book;
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
public class CheckoutServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
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
        double total_price = 0.0;
        
        request.getRequestDispatcher("./checkout.html").include(request, response);
        
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
            
            // calculate total price
            total_price += (Double.parseDouble(price.substring(1)) * order_quantity);
        }
        double temp = total_price;
        total_price = Math.round(temp * 100.0)/100.0;
        
        // create form
        out.println("</div></div><div class=\"container2\" style=\"display:flex; margin-top:10px;\">" + 
                "<div class='grid'>" + 
                "<h1 align=\"left\">Purchase Form</h1><br><br>" +
                "<form name=\"purchase-form\" action=\"ValidationServlet\" method=\"POST\">" +
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
                    "<p id=\"subtotal\" value=0 style=\"text-align: right\">Total: $" + total_price + "</p>" +
                    "<center><input type=\"submit\" name=\"ValidationServlet\" value=\"Purchase\"></center>" +
                    "<br></form>");
        session.setAttribute("total_price", total_price);
        out.println("</div></div>");
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

