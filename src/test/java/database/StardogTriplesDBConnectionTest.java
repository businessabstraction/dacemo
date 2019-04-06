package database;

import database.format.GenericIRI;
import database.format.GenericLiteral;
import database.format.SPARQLResultTable;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class StardogTriplesDBConnectionTest {
    private static StardogTriplesDBConnection connection;

    @BeforeClass
    public static void setUp(){
        connection = new StardogTriplesDBConnection("test", "http://localhost:5820", "admin", "admin");
    }

    // TODO: 6/04/2019 Refactor to use test resources
    @Test public void basicQueryTest(){
        SPARQLResultTable expected = new SPARQLResultTable("s");
        expected.addRecords(
                Collections.singletonList(new GenericIRI("https://www.test.org/dacemo/")),
                Collections.singletonList(new GenericIRI("http://something.org/Person")),
                Collections.singletonList(new GenericIRI("http://something.org/Book")),
                Collections.singletonList(new GenericIRI("http://books.org/properties/Metro2033")),
                Collections.singletonList(new GenericIRI("http://something.org/reads")),
                Collections.singletonList(new GenericIRI("http://something.org/Event"))
        );

        SPARQLResultTable actual = connection.selectQuery(
                "SELECT DISTINCT ?s WHERE { " +
                "    ?s ?p ?o " +
                "}"
        );

        assertEquals(expected.toTestableString(), actual.toTestableString());
    }

    @Test public void basicDescriptionTest(){
        SPARQLResultTable expected = new SPARQLResultTable("subject", "predicate", "object");
        expected.addRecords(
                Arrays.asList(
                        new GenericIRI("http://something.org/reads"),
                        new GenericIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"),
                        new GenericIRI("http://www.w3.org/2002/07/owl#ObjectProperty")
                ),
                Arrays.asList(
                        new GenericIRI("http://something.org/reads"),
                        new GenericIRI("http://www.w3.org/2000/01/rdf-schema#subClassOf"),
                        new GenericIRI("http://something.org/Event")
                ),
                Arrays.asList(
                        new GenericIRI("http://something.org/reads"),
                        new GenericIRI("http://www.w3.org/2000/01/rdf-schema#domain"),
                        new GenericIRI("http://something.org/Person")
                ),
                Arrays.asList(
                        new GenericIRI("http://something.org/reads"),
                        new GenericIRI("http://www.w3.org/2000/01/rdf-schema#range"),
                        new GenericIRI("http://something.org/Book")
                )
        );

        SPARQLResultTable actual = connection.describeQuery("http://something.org/reads");

        assertEquals(expected.toTestableString(), actual.toTestableString());
    }

    @Test public void literalDescriptionTest(){
        SPARQLResultTable expected = new SPARQLResultTable("subject", "predicate", "object");
        expected.addRecords(
            Arrays.asList(
                    new GenericIRI("http://something.org/Person"),
                    new GenericIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"),
                    new GenericIRI("http://www.w3.org/2002/07/owl#Class")
            ),
            Arrays.asList(
                    new GenericIRI("http://something.org/Person"),
                    new GenericIRI("http://www.w3.org/2000/01/rdf-schema#subClassOf"),
                    new GenericIRI("http://something.org/Thing")
            ),
            Arrays.asList(
                    new GenericIRI("http://something.org/Person"),
                    new GenericIRI("http://www.w3.org/2000/01/rdf-schema#label"),
                    new GenericLiteral("\"Person\"")
            )
        );

        SPARQLResultTable actual = connection.describeQuery("http://something.org/Person");

        assertEquals(expected.toTestableString(), actual.toTestableString());
    }
}
