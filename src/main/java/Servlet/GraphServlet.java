package Servlet;

import Bean.D3Object;
import Bean.Node;
import DAO.Data2Json;
import com.github.jsonldjava.core.RDFDataset;
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

            /**
             * Graph initialization
             */
            String clickedNode = "https:/www./docemo.org/owl/examples/iteration-0/Muggle";
            SPARQLResultTable description = connection.describeQuery(clickedNode);
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