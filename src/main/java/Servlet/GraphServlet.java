package Servlet;

import Bean.D3Object;
import Bean.Node;
import com.github.jsonldjava.core.RDFDataset;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.stardog.stark.IRI;
import database.StardogTriplesDBConnection;
import database.format.GenericValue;
import database.format.SPARQLResultTable;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "Servlet",urlPatterns = "Graph")
public class GraphServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String testout = "id111,1,label111,1";
        response.getOutputStream().print(testout);

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        StardogTriplesDBConnection connection = new StardogTriplesDBConnection("iteration0", "http://localhost:5820", "admin", "admin");
        if (connection.canConnect()){
            SPARQLResultTable result = connection.selectQuery(
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
                    "PREFIX dcm: <http://www.dacemo.org/dacemo/>" +
                    "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
                    "SELECT ?s WHERE {" +
                    "    ?s dcm:isTopConcept \"true\"^^xsd:boolean" +
                    "}"
            );

            ArrayList<Node> nodes = new ArrayList<>();
            for (GenericValue value : result.getValuesOfAttribute("s")){
                Node node = new Node(value.get(), 1);
                nodes.add(node);
            }
            D3Object d3Object = new D3Object(nodes);    // Translate result into JSON format
            JSONObject obj = new JSONObject();
            ArrayList<JSONObject> jsonObjects = new ArrayList<>();
            for (Node node : d3Object.getNodes()) {
                try {
                    obj.put("level", node.getLabel());
                    obj.put("label", node.getLabel());
                    obj.put("group", node.getGroup());
                    obj.put("id", node.getId());
                    jsonObjects.add(obj);

                } catch (JSONException e) {
                    System.out.println("Fail to convert to JSON");
                }

            }
            System.out.println("JSON: "+jsonObjects);
            //TODO currently I use the String to transfer the data to the frontend. More Json things need to be done.
            String stringFormat = d3Object.toString();
            response.getOutputStream().print(stringFormat);
        }
    }
}