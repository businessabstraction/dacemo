package Bean;
public class Graph {
    private String Name;
    private int NodeId;
    public Graph(String name, int nodeId){

    }
    public int getNodeId() {
        return NodeId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public void setNodeId(int nodeId) {
        NodeId = nodeId;
    }
}