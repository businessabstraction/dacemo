package Servlet;

import Bean.D3Object;
import Bean.Link;
import Bean.Node;
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
import java.util.ArrayList;

@WebServlet(name = "GraphServlet",urlPatterns = "/Servlet/GraphServlet")
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

            String clickedNode = "https:/www./docemo.org/owl/examples/iteration-0/Muggle";
            SPARQLResultTable description = connection.describeQuery(clickedNode);

//            System.out.println(description.getValuesOfAttribute("subject"));




            /**
             * Created the node arraylist and created the node object based on the SPARQL reqults
             */
            ArrayList<Node> objectNodes = new ArrayList<>();
            ArrayList<Link> predicates = new ArrayList<>();
            /*
            * Get subject node
            * */
            GenericValue subject = description.getValuesOfAttribute("subject").get(0);
            Node subjectNode = new Node(subject.get(), 1);
            /*
            * Get predicates
            * */
            for (GenericValue value : description.getValuesOfAttribute("predicate")){
                Link link = new Link(value.get(), 1);
                predicates.add(link);
            }
            /*
             * Get object nodes
             * */

            for (GenericValue value : description.getValuesOfAttribute("object")){
                Node node = new Node(value.get(), 1);
                objectNodes.add(node);
            }


            ArrayList<ArrayList<JSONObject>> jsonObjects = new ArrayList<>();   // A list that contains three lists: subject, predicates, objects
            ArrayList<JSONObject> objList = new ArrayList<>();
            ArrayList<JSONObject> predList = new ArrayList<>();
            ArrayList<JSONObject> subjList = new ArrayList<>();

            for (Node node : objectNodes) {
                try {
                    JSONObject obj = new JSONObject();
                    obj.put("level", node.getLevel());
                    obj.put("label", node.getLabel());
                    obj.put("group", node.getGroup());
                    obj.put("id", node.getId());
                    objList.add(obj);
                } catch (JSONException e) {
                    System.out.println("Fail to convert to JSON");
                }
            }
            for (Link link : predicates) {
                try {
                    JSONObject obj = new JSONObject();
                    obj.put("level", link.getLevel());
                    obj.put("label", link.getLabel());
                    obj.put("group", link.getGroup());
                    obj.put("id", link.getId());
                    predList.add(obj);
                } catch (JSONException e) {
                    System.out.println("Fail to convert to JSON");
                }

            }
            try {
                JSONObject obj = new JSONObject();
                obj.put("level", subjectNode.getLevel());
                obj.put("label", subjectNode.getLabel());
                obj.put("group", subjectNode.getGroup());
                obj.put("id", subjectNode.getId());
                subjList.add(obj);
            } catch (JSONException e) {
                System.out.println("Fail to convert to JSON");
            }
            jsonObjects.add(subjList);
            jsonObjects.add(predList);
            jsonObjects.add(objList);

            //TODO currently I use the String to transfer the data to the frontend. More Json things need to be done.
            response.getOutputStream().print(jsonObjects.toString());
        }
    }
}