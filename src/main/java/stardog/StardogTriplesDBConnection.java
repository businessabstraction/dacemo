package stardog;

import com.complexible.stardog.api.*;
import com.stardog.stark.IRI;
import com.stardog.stark.query.Binding;
import com.stardog.stark.query.SelectQueryResult;

import java.util.ArrayList;

/**
 * Specific implementation of a TripleStore connection using Stardog.
 */
public class StardogTriplesDBConnection implements TriplesDBConnection {
    private Connection connection;

    /**
     * Constructor for a non-admin connection to Stardog.
     * @param dbName the name of the database to connect to.
     * @param dbURL the url of the Stardog server - for now it is a localhost.
     * @param user connection to the database using this username.
     * @param pass connection to the database using this password.
     */
    public StardogTriplesDBConnection(String dbName, String dbURL, String user, String pass){
        ConnectionConfiguration config = ConnectionConfiguration.to(dbName).server(dbURL).credentials(user, pass);
        connection = config.connect();
    }

    /**
     * Checks the connection status of the given StarDogTriplesDBConnection.
     * @return whether the connection to the DB is open.
     */
    @Override
    public boolean canConnect() {
        return connection.isOpen();
    }

    /**
     * Execute the given SPARQL Query through the connection.
     * @param query the String representation of the given query.
     * @return the result of the SELECT query as an ArrayList of the IRIs.
     */
    @Override
    public ArrayList<IRI> selectQuery(String query) {
        ArrayList<IRI> queryResult = new ArrayList<>();

        SelectQuery selectQuery = connection.select(query);

        try (SelectQueryResult result = selectQuery.execute()) {
            result.forEachRemaining(value -> queryResult.add(value.binding("s").flatMap(Binding::iri).get()));
        }

        return queryResult;
    }
}
