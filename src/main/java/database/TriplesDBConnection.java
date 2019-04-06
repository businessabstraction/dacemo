package database;

import database.format.SPARQLResultTable;

/**
 * A common interface for all backend triplestores and databases.
 */
interface TriplesDBConnection {
    /**
     * @return whether the given database can be connected to.
     */
    boolean canConnect();

    /**
     * Runs the given SPARQL query
     * @param query the query as defined by the SPARQL Syntax
     * @return the SPARQLResultTable of query results.
     */
    SPARQLResultTable selectQuery(String query);

    /**
     * Executes a SPARQL query that describes a given IRI
     * @param iri the IRI to be described.
     * @return the result table that corresponds to finding all outgoing properties of the given node.
     * Ex. :Programmer a owl:Class ;
     *         rdfs:subclassOf :Person ;
     *         rdfs:label "Programmer" .
     *    will be converted to the equivalent SPARQLResultTable (prefixes not expanded):
     *    ------------------------------------------------
     *    | sub         | pred            | obj          |
     *    |----------------------------------------------|
     *    | :Programmer | rdfs:type       | owl:Class    |
     *    | :Programmer | rdfs:subclassOf | :Person      |
     *    | :Programmer | rdfs:label      | "Programmer" |
     *    ------------------------------------------------
     */
    SPARQLResultTable describeQuery(String iri);
}
