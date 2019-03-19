import stardog.StardogTriplesDBConnection;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServerContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        StardogTriplesDBConnection connection = new StardogTriplesDBConnection();
        if (!connection.canConnect()){
            System.out.println("Cannot connect to the database. ");
            // TODO: 17/03/2019 send this result to the frontend?
        } else {
            System.out.println("Hooray!");
        }
    }

    /**
     * The server is about to be shut down, run this code.
     * @param servletContextEvent the event that caused the shutdown.
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("I'm about to be destroyed.");
    }
}
