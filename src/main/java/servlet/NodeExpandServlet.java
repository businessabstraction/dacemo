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

@WebServlet(name = "NodeExpandServlet",urlPatterns = "/servlet/NodeExpandServlet")
public class NodeExpandServlet extends HttpServlet {
    /**
     * servlet responsible for getting all outgoing nodes of the given node and sending them to the frontend.
     * @param request the http request the servlet recieved.
     * @param response the http response the servlet will send back.
     * @throws IOException if the request/response is malformed.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nodename = request.getParameter("nodename");

        StardogTriplesDBConnection connection = new StardogTriplesDBConnection("xyzcompany", "http://localhost:5820", "admin", "admin");
        if (connection.canConnect()){
            SPARQLResultTable result = connection.describeQueryMinimal(nodename);

            //Get the SPARQL result by nodename, then convert the result into JSON Object and send it to server
            Data2Json data2Json = new Data2Json(result);
            try {
                JSONObject jsonObject = data2Json.getJsonData();
                response.getOutputStream().print(jsonObject.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
