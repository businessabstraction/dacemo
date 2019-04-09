package Servlet;

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

@WebServlet(name = "NodeExpandServlet",urlPatterns = "NodeExpand")
public class NodeExpandServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nodename = request.getParameter(""); // TODO: 9/04/2019 Lifu: Find the value of the request parameter from frontend Team.
        StardogTriplesDBConnection connection = new StardogTriplesDBConnection("iteration0", "http://localhost:5820", "admin", "admin");

        if (connection.canConnect()){
            SPARQLResultTable description = connection.describeQuery(nodename);

            /*
             * Created the node arraylist and predicate arralist for storing the data based on the SPARQL reqults
             */
            ArrayList<Node> objectNodes = new ArrayList<>();
            ArrayList<Link> predicates = new ArrayList<>();
            /*
             * Get subject node
             */
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

            /*
             * Add id, group, label and level information of each object node to the JsonObject
             */
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
            /*
             * Add id, group, label and level information of each predicate link to the JsonObject
             */
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
            /*
             * Add id, group, label and level information the subject node to the JsonObject
             */
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
            /*
             *  add subject, predicates and objects to the list of Json object
             */
            jsonObjects.add(subjList);
            jsonObjects.add(predList);
            jsonObjects.add(objList);

            //TODO currently I use the String to transfer the data to the frontend. More Json things need to be done.
            /*
             * send the list of three lists of Json objects to server
             */
            response.getOutputStream().print(jsonObjects.toString());
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) {}
}
