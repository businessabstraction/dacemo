package stardog;

import com.stardog.stark.IRI;

import java.util.ArrayList;

/**
 * An interface to any potential TripleStore.
 */
interface TriplesDBConnection {
    /**
     * Checks whether the specified TriplesDBConnection is open.
     * @return the state of the connection.
     */
    boolean canConnect();

    /**
     * Run a basic SPARQL SELECT Query over the given connection.
     * @param query the string query to be executed.
     * @return the result of the query as a ArrayList of IRIs.
     */
    ArrayList<IRI> selectQuery(String query);
}
