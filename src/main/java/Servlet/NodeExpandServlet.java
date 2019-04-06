package Servlet;

import Bean.D3Object;
import Bean.Node;
import database.StardogTriplesDBConnection;
import database.format.GenericValue;
import database.format.SPARQLResultTable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "NodeExpandServlet",urlPatterns = "NodeExpand")
public class NodeExpandServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nodename = request.getParameter("");
        StardogTriplesDBConnection connection = new StardogTriplesDBConnection("iteration0", "http://localhost:5820", "admin", "admin");
        if (connection.canConnect()){
            SPARQLResultTable result = connection.describeQuery(nodename);
            /*
             * Creatd the node arraylist and created the node object based on the SPARQL reqults
             */
            ArrayList<Node> nodes = new ArrayList<>();
            for (GenericValue value : result.getValuesOfAttribute("s")){ // TODO: 6/04/2019 Don't forget that there are different attributes!
                Node node = new Node(value.get(), 1);
                nodes.add(node);
            }
            D3Object d3Object = new D3Object(nodes);    // Translate result into JSON format

            //TODO currently I use the String to transfer the data to the frontend. More Json things need to be done.
            String stringFormat = d3Object.toString();
            response.getOutputStream().print(stringFormat);
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) {}
}
