package database;

import database.format.GenericIRI;
import database.format.GenericLiteral;
import database.format.GenericValue;
import database.format.SPARQLResultTable;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class StardogTriplesDBConnectionTest {
    private static StardogTriplesDBConnection connection;

    /**
     * Creates the required connections to the database and adds the test database if it does not exist.
     */
    @BeforeClass
    public static void setUp(){
        connection = new StardogTriplesDBConnection("test", "http://localhost:5820", "admin", "admin");

        database.StardogTriplesDBAdminConnection admin =
                new StardogTriplesDBAdminConnection("http://localhost:5820", "admin", "admin");

        if (!admin.existsDB("test")){
            admin.createDB("test", "src/test/resources/database/test_ontology.ttl");
        }
    }

    /**
     * Tests for basic SPARQL queries.
     */
    @Test public void basicQueryTest(){
        SPARQLResultTable expected = new SPARQLResultTable("s");
        getRecordsFromFile("basicQueryExpecteds.txt").forEach(expected::addRecord);

        SPARQLResultTable actual = connection.selectQuery(
                "SELECT DISTINCT ?s WHERE { " +
                "    ?s ?p ?o " +
                "}"
        );

        assertEquals(expected.toTestableString(), actual.toTestableString());
    }

    /**
     * Tests for basic descriptive queries.
     */
    @Test public void basicDescriptionTest(){
        SPARQLResultTable actual = connection.describeQuery("http://something.org/reads");
        SPARQLResultTable expected = new SPARQLResultTable("subject", "predicate", "object");
        getRecordsFromFile("basicDescriptionExpecteds.txt").forEach(expected::addRecord);

        assertEquals(expected.toTestableString(), actual.toTestableString());
    }

    /**
     * Tests for descriptive queries that contain literals.
     */
    @Test public void literalDescriptionTest(){
        SPARQLResultTable actual = connection.describeQuery("http://something.org/Person");
        SPARQLResultTable expected = new SPARQLResultTable("subject", "predicate", "object");
        getRecordsFromFile("literalDescriptionExpecteds.txt").forEach(expected::addRecord);

        assertEquals(expected.toTestableString(), actual.toTestableString());
    }

    /**
     * Helper method for loading the expected results of a SPARQL query.
     * @param fileName the fileName to load from.
     * @return a matrix of GenericValues, similar to a SPARQLResultsTable
     */
    private List<List<GenericValue>> getRecordsFromFile(String fileName){
        ArrayList<List<GenericValue>> records = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/database/expecteds/" + fileName))) {
            String line;

            while ((line = reader.readLine()) != null){
                String[] fields = line.split("\\|\\|");
                ArrayList<GenericValue> record = new ArrayList<>();

                for (String field : fields){
                    GenericValue genericValue = field.matches("https?://.*") ?
                            new GenericIRI(field) :
                            new GenericLiteral(field);

                    record.add(genericValue);
                }

                records.add(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return records;
    }
}
