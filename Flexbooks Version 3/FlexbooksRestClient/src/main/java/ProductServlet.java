/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */ 
 
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference; 
import org.glassfish.jersey.client.ClientConfig;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.List;
import javax.servlet.annotation.WebServlet;

import com.inf124.flexbooksrestservice.model.Book;


/**
 *
 * @author 
 */
public class ProductServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {


        ClientConfig config = new ClientConfig();

        Client client = ClientBuilder.newClient(config);

        WebTarget target = client.target(getBaseURI());


        String jsonResponse =
                target.path("v1").path("api").path("books").
                        request(). //send a request
                        accept(MediaType.APPLICATION_JSON). //specify the media type of the response
                        get(String.class); // use the get method and return the response as a string

        System.out.println(jsonResponse);

        ObjectMapper objectMapper = new ObjectMapper(); // This object is from the jackson library


        List<Book> bookList = objectMapper.readValue(jsonResponse, new TypeReference<List<Book>>(){});

        PrintWriter out = response.getWriter();

        out.print("<html><head>" +
                "<title>REST Client</title>" +
                "</head>" +
                "<body>"
        );

        for(Book book : bookList) {
            String author = book.getAuthor();
            if (author.length() > 40)
                author = author.substring(0,40) + "...";
            out.print("<div class=\"cell\">" +
            "<a id=" + book.getId() + "\" href=\"./itempage.html\" onclick=\"setVar(" + book.getId() + ")\">\n" +
                "<img class=\"zoom\" src=\"" + book.getImage() + "\" alt=\"book\", height=\"270\" width=\"270\">\n" +
            "</a>" +
            "<h2>"+book.getTitle()+"</h2>\n" +
            "<h5>"+author+"</h5>\n" +
            "<h5>"+book.getPrice()+"</h5>\n" +
            "</div>");
        }


    }

    private static URI getBaseURI() {

        //Change the URL here to make the client point to your service.
        return UriBuilder.fromUri("http://localhost:8080/FlexbooksRestService").build();
    }


}