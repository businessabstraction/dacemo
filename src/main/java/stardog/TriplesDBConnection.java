package stardog;

import com.stardog.stark.IRI;

import java.util.ArrayList;

interface TriplesDBConnection {
    boolean canConnect();
    ArrayList<IRI> selectQuery(String query);
}
