package DAO;

import Bean.Link;
import Bean.Node;
import com.complexible.stardog.protocols.http.JSON;
import database.format.GenericValue;
import database.format.SPARQLResultTable;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.LinkedHashMap;

/**
 * Data to JSON class. It is implemented for converting SPARQL result into JSON format.
 */
public class Data2Json {
    SPARQLResultTable description;

    /**
     * The constructor, set the result from SPARQL
     * @param description the result from SPARQL
     */
    public Data2Json(SPARQLResultTable description) {
        this.description = description;
    }

    /**
     * Convert SPARQL result into JSON format. Detailed description is in JSONFormat.md
     * @return a JSON Object which contains all the descriptions from SPARQL result
     * @throws JSONException
     */
    public JSONObject getJsonData() throws JSONException {

        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < description.getVariables().size(); i++) {
            Node subj = new Node(description.getValuesOfAttribute("subject").get(i).get(), 1);
            Link pred = new Link(description.getValuesOfAttribute("predicate").get(i).get(), 1);
            Node obj = new Node(description.getValuesOfAttribute("object").get(i).get(), 1);
            subj.addObj(obj);
            subj.addPred(pred);
            LinkedHashMap m = new LinkedHashMap();
            m.put("subject", subj.getMap());
            m.put("predicate", pred.getMap());
            m.put("object", obj.getMap());
            jsonObject.put("index"+String.valueOf(i), m);
        }
        return jsonObject;
    }

    /**
     * Initializing the graph, haven't been implemented yet.
     * @return JSON Object of the top level concept node.
     */
    public JSONObject initializeGraph() {


        return null;
    }
}
