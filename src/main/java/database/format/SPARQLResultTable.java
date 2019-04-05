package database.format;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A format for easy access to the results of a SPARQL SELECT query.
 */
public class SPARQLResultTable {
    private List<String> variables;
    private List<List<GenericValue>> table;

    /**
     * Constructors for a SPARQLResultTable
     * @param variables the list of variables used in the SPARQL query. For example: SELECT ?s WHERE {?s ?p ?o} has a
     *                  single variable, s.
     */
    public SPARQLResultTable(String... variables){
        this.variables = Arrays.asList(variables);
        this.table = new ArrayList<>();
    }
    public SPARQLResultTable(List<String> variables){
        this.variables = variables;
        this.table = new ArrayList<>();
    }

    /**
     * Adds a row to the SPARQL Results Table
     * @param record the record to be added.
     */
    public void addRecord(List<GenericValue> record){
        table.add(record);
    }

    /**
     * Adds the rows to the SPARQL Results Table
     * @param records the records to be added.
     */
    @SafeVarargs
    public final void addRecords(List<GenericValue>... records){ table.addAll(Arrays.asList(records)); }

    /**
     * @return the list of variables that was added during construction of the Table.
     */
    public List<String> getVariables() {
        return variables;
    }

    /**
     * Returns the i-th record in the table.
     * @param i the index at which the index will be retrieved.
     * @return the record at said index.
     */
    public List<GenericValue> getRecord(int i){
        return table.get(i);
    }

    /**
     * Gets the column of the table that represents the given attribute.
     * @param attribute the attribute from which the results are sourced.
     * @return the column of the table.
     */
    public List<GenericValue> getValuesOfAttribute(String attribute){
        int index = variables.indexOf(attribute);
        List<GenericValue> attributes = new ArrayList<>();

        for (List<GenericValue> record : table){
            attributes.add(record.get(index));
        }

        return attributes;
    }

    /**
     * @return the String representation of the table.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        int longestWord = 20;

        for (List<GenericValue> rec : table)
            for (GenericValue val : rec)
                if (val.toString().length() > longestWord)
                    longestWord = val.toString().length();

        for (String variable : variables) {
            int fill =  longestWord - variable.length();
            result.append(variable)
                    .append(new String(new char[fill]).replace("\0", " "))
                    .append("|");
        }
        result.append("\n");

        for (List<GenericValue> record : table){
            for (GenericValue value : record){
                int fill = longestWord - value.get().length();
                result.append(value)
                        .append(new String(new char[fill]).replace("\0", " "))
                        .append("|");
            }
            result.append("\n");
        }

        return result.toString();
    }
}
