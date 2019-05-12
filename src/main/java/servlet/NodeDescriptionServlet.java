package servlet;

import database.StardogTriplesDBConnection;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "NodeDescriptionServlet",urlPatterns = "/servlet/NodeDescriptionServlet")
public class NodeDescriptionServlet extends HttpServlet {

    /**
     * Gets the rdfs:description of a given node.
     * @param request the http request the servlet recieved.
     * @param response the http response the servlet will send back.
     * @throws IOException if the request/response is malformed.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String node = request.getParameter("nodename");
        StardogTriplesDBConnection connection = new StardogTriplesDBConnection("xyzcompany", "http://localhost:5820", "admin", "admin");

        if (connection.canConnect()){
            String result = connection.nodeDescribeQuery(node);
            response.getOutputStream().print(result);
        }
    }
}