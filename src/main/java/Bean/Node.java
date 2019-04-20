package Bean;

import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The node entity class
 */
public class Node {
    private String id;
    private int group;
    private String label;
    private int level = 0;
    public Node object;
    public Link predicate;

    /**
     * The constructor of node object, only set the unique id and group
     * @param id the unique id about the node
     * @param group the group about node should belongs to
     */
    public Node(String id, int group){
        this.id = id;
        this.group = group;
        int idx = id.lastIndexOf("/");
        if (id.matches("\".*\".*")){
            Pattern p = Pattern.compile("\"(.*?)\"");
            Matcher m = p.matcher(id);
            while (m.find()){
                this.label = m.group(1);
            }
            int idx1 = id.indexOf("<");
            int idx2 = id.indexOf(">");
            id = id.substring(idx1+1, idx2);
            this.id = id;
        } else {
            this.label = id.substring(idx + 1);
        }
    }

    /**
     * The overwrite node constructor which required more information about nodes
     * @param id The unique id of node
     * @param group The group which the node should belongs to
     * @param label The label of the node, which is display the name information about node
     * @param level The level which the node should belongs to
     */
    public Node(String id, int group, String label, int level){
        this.id = id;
        this.group = group;
        this.label = label;
        this.level = level;
    }

    /**
     * Add the object of the node as a variable
     * @param obj the object of the node
     */
    public void addObj (Node obj) {
        this.object= obj;
    }

    /**
     * Add the predicate of the node as a variable
     * @param pred the predicate of the node
     */
    public void addPred (Link pred) {
        this.predicate = pred;
    }

    /**
     * Get the id of the node
     * @return The unique id of node
     */
    public String getId(){
        return id;
    }

    /**
     * Get the label of the node
     * @return The unique label of node
     */
    public String getLabel(){
        return label;
    }
    /**
     * Get the group of the node
     * @return The unique group of node
     */
    public int getGroup() {
        return group;
    }
    /**
     * Get the level of the node
     * @return The unique level of node
     */
    public int getLevel() {
        return level;
    }

    /**
     * Get the structure of node
     * @return a map of node, only for JSON format conversion
     */
    public LinkedHashMap getMap() {
        LinkedHashMap m = new LinkedHashMap();
        m.put("id", id);
        m.put("group", group);
        m.put("label", label);
        m.put("level", level);
        return m;
    }

    @Override
    /**
     * The toString function to convert node information to string type
     */
    public String toString() {
        return "id: "+ id + ", group: " + group + ", label: " + label + ", level: " + level;
    }
}
