package database;

import com.complexible.stardog.api.Connection;
import com.complexible.stardog.api.ConnectionConfiguration;
import com.complexible.stardog.api.GraphQuery;
import com.complexible.stardog.api.SelectQuery;
import com.stardog.stark.*;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.GraphQueryResult;
import com.stardog.stark.query.SelectQueryResult;
import database.format.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
     * Runs a basic SPARQL DESCRIBE query over each iri.
     * @param iris the Arraylist of IRIs that will be expanded.
     * @return the Set of statements that describe each iri.
     */
    public Set<GenericStatement> describeQuery(List<GenericIRI> iris){
        Set<Statement> stardogStatements = new HashSet<>();
        Set<GenericStatement> statements = new HashSet<>();

        for (GenericIRI iri : iris){
            GraphQuery query = connection.graph("DESCRIBE " + iri.toString());
            GraphQueryResult result = query.execute();
            stardogStatements.addAll(result.toGraph());
        }

        for (Statement stardogStatement : stardogStatements){
            GenericIRI subject = new GenericIRI(stardogStatement.subject().toString());
            GenericIRI predicate = new GenericIRI(stardogStatement.predicate().toString());
            String objectString = stardogStatement.object().toString();
            GenericValue object = objectString.matches("https?:.*") ?
                    new GenericIRI(objectString) :
                    new GenericLiteral(objectString);

            statements.add(new GenericStatement(subject, predicate, object));
        }

        return statements;
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
