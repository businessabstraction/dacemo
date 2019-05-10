package model;

import database.format.SPARQLResultTable;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;

/**
 * Data to JSON class. It is implemented for converting SPARQL result into JSON format.
 */
public class Data2Json {
    private SPARQLResultTable description;

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
     * @throws JSONException if data does not conform to JSON standards.
     */
    public JSONObject getJsonData() throws JSONException {

        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < description.size(); i++) {
            Node subj = new Node(description.getValuesOfAttribute("subject").get(i).get(), 1);
            Link pred = new Link(description.getValuesOfAttribute("predicate").get(i).get(), 1);
            Node obj = new Node(description.getValuesOfAttribute("object").get(i).get(), 1);
            subj.addObj(obj);
            subj.addPred(pred);
            LinkedHashMap<String, LinkedHashMap<String, Object>> m = new LinkedHashMap<>();
            m.put("subject", subj.getMap());
            m.put("predicate", pred.getMap());
            m.put("object", obj.getMap());
            jsonObject.put("index"+ i, m);
        }
        return jsonObject;
    }

    /**
     * Initializing the graph. Detailed description is in JSONFormat.md
     * @return JSON Object of the top level concept node.
     */
    public JSONObject initializeGraph() throws JSONException {
        JSONObject jsonObject = new JSONObject();

        for (int i = 0; i < description.size(); i++) {
            Node node = new Node(description.getValuesOfAttribute("s").get(i).get(), 1);
            LinkedHashMap<String, LinkedHashMap<String, Object>> m = new LinkedHashMap<>();
            m.put("s", node.getMap());
            jsonObject.put("index"+ i, m);
        }

        return jsonObject;
    }
}
