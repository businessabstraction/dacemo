package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Servlet",urlPatterns = "Graph")
public class GraphServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String output =
                "      [\n" +
                "        {\"id\": \"Myriel\", \"group\": 1},\n" +
                "        {\"id\": \"Napoleon\", \"group\": 1}\n" +
                "        ]\n" ;

        String outputTest = "id111,1,label111,1"+
                            "id112,1,label112,1"+
                            "id113,1,label113,1"+
                            ";"+"id111" + "id112" +"0.1";

        String out = "id111,1,label111,1";

        response.getOutputStream().print(out);

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.getOutputStream().print("POST");
    }
}