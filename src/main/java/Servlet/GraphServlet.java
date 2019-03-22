package Servlet;

import Bean.D3Object;
import Bean.Node;
import com.google.gson.Gson;
import com.stardog.stark.IRI;
import stardog.StardogTriplesDBConnection;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Servlet",urlPatterns = "Graph")
public class GraphServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String out = "id111,1,label111,1";

        response.getOutputStream().print(out);

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        StardogTriplesDBConnection connection = new StardogTriplesDBConnection("magic", "http://localhost:5820", "admin", "admin");
        if (connection.canConnect()){
            ArrayList<IRI> result = connection.selectQuery(
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
                    "SELECT ?s WHERE {" +
                    "    ?s rdfs:subClassOf <http://www.dacemo.org/dacemo/Person> " +
                    "}"
            );

            ArrayList<Node> nodes = new ArrayList<>();
            Gson json = new Gson();
            for (IRI obj: result){
                Node node = new Node(obj.toString(), 1);
                nodes.add(node);
            }
            D3Object d3Object = new D3Object(nodes);    // Translate result into JSON format

            String blah = json.toJson(d3Object);

            response.getOutputStream().print(blah);
        }
    }
}