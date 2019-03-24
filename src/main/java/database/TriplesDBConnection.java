package database;

import database.format.GenericIRI;
import database.format.GenericStatement;
import database.format.GenericValue;
import database.format.SPARQLResultTable;

import java.util.List;
import java.util.Set;

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
     * @return the matrix of query results.
     */
    SPARQLResultTable selectQuery(String query);

    /**
     * Describes the list of GenericIRIs (aka, returns all the properties of the given IRI.
     * @param iris the array of IRIs that will be described.
     * @return the statements that describe each iri.
     */
    Set<GenericStatement> describeQuery(List<GenericIRI> iris);
}
