package database;

import com.complexible.stardog.api.Connection;
import com.complexible.stardog.api.ConnectionConfiguration;
import com.complexible.stardog.api.SelectQuery;
import com.stardog.stark.IRI;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.Values;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import database.format.GenericIRI;
import database.format.GenericLiteral;
import database.format.GenericValue;
import database.format.SPARQLResultTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that allows access to the data within a StarDog database.
 */
public class StardogTriplesDBConnection implements TriplesDBConnection {
    private Connection connection;

    /**
     * creates a new non-admin connection to the database.
     * @param dbName the name of the Stardog db you will connect to.
     * @param dbURL the URL of the database you will connect to
     * @param user username for access to the database
     * @param pass password for access to the database
     */
    public StardogTriplesDBConnection(String dbName, String dbURL, String user, String pass){
        ConnectionConfiguration config = ConnectionConfiguration.to(dbName).server(dbURL).credentials(user, pass);
        connection = config.connect();
    }

    /**
     * Checks if the current connection to the database is open.
     * @return whether the connection to the DB is successful.
     */
    @Override
    public boolean canConnect() {
        return connection.isOpen();
    }

    /**
     * Runs a basic SPARQL SELECT executeQuery over the given database
     * @param query the query to be executed
     * @return the results of the query as a table.
     */
    @Override
    public SPARQLResultTable selectQuery(String query) {
        SelectQuery selectQuery = connection.select(query);

        return executeQuery(selectQuery);
    }

    /**
     * Executes a SPARQL query that describes a given IRI
     * @param iri the IRI to be described.
     * @return the result table that corresponds to finding all outgoing properties of the given node.
     * Ex. :Programmer a owl:Class ;
     *         rdfs:subclassOf :Person ;
     *         rdfs:label "Programmer" .
     *    will be converted to the equivalent SPARQLResultTable (prefixes not expanded):
     *    ------------------------------------------------
     *    | subject     | predicate       | object       |
     *    |----------------------------------------------|
     *    | :Programmer | rdfs:type       | owl:Class    |
     *    | :Programmer | rdfs:subclassOf | :Person      |
     *    | :Programmer | rdfs:label      | "Programmer" |
     *    ------------------------------------------------
     */
    @Override
    public SPARQLResultTable describeQuery(String iri){
        SelectQuery selectQuery = connection.select(
                "SELECT ?subject ?predicate ?object " +
                   "WHERE { " +
                   "    ?subject ?predicate ?object " +
                   "}"
        );
        selectQuery.parameter("subject", Values.iri(iri));

        return executeQuery(selectQuery);
    }

    /**
     * Executes a SPARQL query that gives the rdfs:describe of a given iri.
     * @param iri the iri to be described.
     * @return the description as a String.
     */
    @Override
    public String nodeDescribeQuery(String iri) {
        SelectQuery selectQuery = connection.select("" +
                "PREFIX dcm: <http://www.dacemo.org/dacemo/>" +
                "SELECT ?description " +
                "WHERE {" +
                "    ?subject dcm:description ?description" +
                "}"
        );
        selectQuery.parameter("subject", Values.iri(iri));

        SPARQLResultTable table = executeQuery(selectQuery);

        List<GenericValue> descriptions = table.getValuesOfAttribute("description");

        if (descriptions.size() == 0) return "";
        else {
            String description = descriptions.get(0).get();
            int endOfStringIndex = description.lastIndexOf("\"");

            return description.substring(1, endOfStringIndex);
        }
    }

    /**
     * Run the specified query through StarDog.
     * @param query the query to run.
     * @return the result of the query as a SPARQLResultTable.
     */
    private SPARQLResultTable executeQuery(SelectQuery query){
        try (SelectQueryResult selectQueryResult = query.execute()) {
            List<String> variables = selectQueryResult.variables();
            SPARQLResultTable table = new SPARQLResultTable(variables);

            while (selectQueryResult.hasNext()) {
                List<GenericValue> record = new ArrayList<>();
                BindingSet selectQueryResultRecord = selectQueryResult.next();

                for (String variable : variables) {
                    Value stardogValue = selectQueryResultRecord.get(variable);
                    GenericValue value;

                    if (stardogValue instanceof Literal) value = new GenericLiteral(stardogValue.toString());
                    else if (stardogValue instanceof IRI) value = new GenericIRI(stardogValue.toString());
                    else value = new GenericIRI(""); // TODO: 21/03/2019 Tommy: Is this equivalent to a BNN?

                    record.add(value);
                }

                table.addRecord(record);
            }

            return table;
        }
    }
}
