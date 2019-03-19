package stardog;

import com.complexible.stardog.api.*;
import com.stardog.stark.IRI;
import com.stardog.stark.query.Binding;
import com.stardog.stark.query.SelectQueryResult;

import java.util.ArrayList;


public class StardogTriplesDBConnection implements TriplesDBConnection {
    private Connection connection;

    public StardogTriplesDBConnection(String dbName, String dbURL, String user, String pass){
        ConnectionConfiguration config = ConnectionConfiguration.to(dbName).server(dbURL).credentials(user, pass);
        connection = config.connect();
    }

    @Override
    public boolean canConnect() {
        return connection.isOpen();
    }

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
