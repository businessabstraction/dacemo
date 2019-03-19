import com.stardog.stark.IRI;
import com.stardog.stark.query.BindingSet;
import stardog.StardogTriplesDBConnection;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.ArrayList;

public class ServerContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        StardogTriplesDBConnection connection = new StardogTriplesDBConnection("magic", "http://localhost:5820", "admin", "admin");
        if (!connection.canConnect()){
            System.out.println("Cannot connect to the database. ");
            // TODO: 17/03/2019 send this result to the frontend?
        } else {
            ArrayList<IRI> result = connection.selectQuery(
                    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
                    "SELECT ?s WHERE {" +
                    "    ?s rdfs:subClassOf <http://www.dacemo.org/dacemo/Person> " +
                    "}"
            );

            System.out.println(result);
        }
    }

    /**
     * The server is about to be shut down, run this code.
     * @param servletContextEvent the event that caused the shutdown.
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {}
}
