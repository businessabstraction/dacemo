package Bean;

/**
 * The node entity class
 */
public class Node {
    private String id;
    private int group;
    private String label;
    private int level = 0;

    /**
     * The constructor of node object, only set the unique id and group
     * @param id the unique id about the node
     * @param group the group about node should belongs to
     */
    public Node(String id, int group){
        this.id = id;
        this.group = group;
        int idx = id.lastIndexOf("/");
//        this.label = id.lastIndexOf("/") == -1 ?
//                id.substring(id.lastIndexOf("/") + 1) :
//                id; // TODO: 23/03/2019 Basic Labelling function, update.
        this.label = id.substring(idx+1);
    }

    /**
     * The overwrite node constructor which required more information about nodes
     * @param id The unique id of node
     * @param group The group which the node should belongs to
     * @param label The label of the node, which is display the name information about node
     * @param level The level which the node should belongs to
     */
    public Node(String id, int group,String label,int level){
        this.id = id;
        this.group = group;
        this.label = label;
        this.level = level;
    }

    @Override
    /**
     * The toString function to convert node information to string type
     */
    public String toString() {
        return "" + id +","+group +","+label +","+level;
    }
}
