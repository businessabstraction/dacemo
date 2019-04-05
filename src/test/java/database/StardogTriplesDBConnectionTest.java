package database;

import database.format.GenericIRI;
import database.format.SPARQLResultTable;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class StardogTriplesDBConnectionTest {
    private static StardogTriplesDBConnection connection;

    @BeforeClass
    public static void setUp(){
        connection = new StardogTriplesDBConnection("test", "http://localhost:5820", "admin", "admin");
    }

    // TODO: 6/04/2019 Refactor to use test resources
    @Test public void basicQuery(){
        SPARQLResultTable expected = new SPARQLResultTable(Collections.singletonList("s"));
        expected.addRecord(Collections.singletonList(new GenericIRI("https://www.test.org/dacemo/")));
        expected.addRecord(Collections.singletonList(new GenericIRI("http://something.org/Person")));
        expected.addRecord(Collections.singletonList(new GenericIRI("http://something.org/Book")));
        expected.addRecord(Collections.singletonList(new GenericIRI("http://books.org/properties/Metro2033")));
        expected.addRecord(Collections.singletonList(new GenericIRI("http://something.org/reads")));
        expected.addRecord(Collections.singletonList(new GenericIRI("http://something.org/Event")));

        SPARQLResultTable actual = connection.selectQuery(
                "SELECT DISTINCT ?s WHERE { " +
                "    ?s ?p ?o " +
                "}"
        );

        assertEquals(expected.toString().trim(), actual.toString().trim());
    }

    @Test public void basicDescription(){
        SPARQLResultTable expected = new SPARQLResultTable(Arrays.asList("subject", "predicate", "object"));
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

        assertEquals(expected.toString().trim(), actual.toString().trim());
    }
}
