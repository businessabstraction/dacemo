package stardog;

import com.complexible.stardog.api.ConnectionConfiguration;


public class StardogTriplesDBConnection implements TriplesDBConnection {
    private static final String DB_NAME = "firstDB";
    private static final String DB_URL = "http://localhost:5820";
    private static final String USER = "admin";
    private static final String PASS = "admin";

    public StardogTriplesDBConnection(){}

    @Override
    public boolean canConnect() {
        ConnectionConfiguration config = ConnectionConfiguration.to(DB_NAME).server(DB_URL).credentials(USER, PASS);
        return config.connect().isOpen();
    }

    @Override
    public void query(String query) {

    }
}
