package servlet;

import model.Data2Json;
import database.StardogTriplesDBConnection;
import database.format.SPARQLResultTable;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "GraphServlet",urlPatterns = "/servlet/GraphServlet")
public class GraphServlet extends HttpServlet {
    /**
     * servlet responsible for initializing the graph, getting the most important concepts of the ontology and sending
     *     them to the front end.
     * @param request the http request the servlet recieved.
     * @param response the http response the servlet will send back.
     * @throws IOException if the request/response is malformed.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StardogTriplesDBConnection connection = new StardogTriplesDBConnection("multilink", "http://localhost:5820", "admin", "admin");
        if (connection.canConnect()){
            SPARQLResultTable result = connection.selectQuery(
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
                    "PREFIX dcm: <http://www.dacemo.org/dacemo/>" +
                    "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" +
                    "SELECT ?s WHERE {" +
                    "    ?s dcm:isTopConcept \"true\"^^xsd:boolean" +
                    "}"
            );

            //Graph initialization
            Data2Json data2Json = new Data2Json(result);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject = data2Json.initializeGraph();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            response.getOutputStream().print(jsonObject.toString());
        }
    }
}