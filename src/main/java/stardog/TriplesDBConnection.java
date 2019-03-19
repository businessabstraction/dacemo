package stardog;

interface TriplesDBConnection {
    boolean canConnect();
    void query(String query);
}
