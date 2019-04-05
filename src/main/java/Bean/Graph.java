package Bean;

/**
 * The graph class is a entity class for the data transfer on the backend and frontend
 * which have been used for the iteration 1, I will be used for the next iteration
 */
public class Graph {
    private String Name;
    private int NodeId;
    public Graph(String name, int nodeId){

    }

    /**
     * The get function to get the node unique id
     * @return the unique integer id about the node
     */
    public int getNodeId() {
        return NodeId;
    }

    /**
     *
     * @return returen the name of the graph
     */
    public String getName() {
        return Name;
    }

    /**
     * Set the neme for the graph
     * @param name the name of the graph
     */
    public void setName(String name) {
        this.Name = name;
    }

    /**
     * Set the unique id for the node
     * @param nodeId the unique node id
     */
    public void setNodeId(int nodeId) {
        NodeId = nodeId;
    }
}