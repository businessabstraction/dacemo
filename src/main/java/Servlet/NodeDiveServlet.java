package Servlet;

import DAO.Data2Json;
import database.StardogTriplesDBConnection;
import database.format.SPARQLResultTable;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "NodeDiveServlet",urlPatterns = "/Servlet/NodeDiveServlet")
public class NodeDiveServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String divenode = request.getParameter("divenodename");
        StardogTriplesDBConnection connection = new StardogTriplesDBConnection("iteration0", "http://localhost:5820", "admin", "admin");
        if (connection.canConnect()){

            SPARQLResultTable result = connection.describeQuery(divenode);
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