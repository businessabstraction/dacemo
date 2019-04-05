package Bean;

import java.util.ArrayList;

/**
 * The is the D3 graph data objects class, which is a entity class.
 * It have bean used for create the data object for data transfer to the front end
 */
public class D3Object {
    private ArrayList<Node> nodes;
    // the constructor of d3 data object
    public D3Object(ArrayList<Node> nodes){
        this.nodes = nodes;
    }

    public D3Object(){

    }

    /**
     * The get function for the data object to get all nodes of the graph
     * @return it will return a ArrayList which contains all nodes
     */
    public ArrayList<Node> getNodes() {
        return nodes;
    }

    /**
     * The set function for the data object to add nodes for current graph
     * @param node The node object will add to the node list which contains all
     *             nodes of the graph
     */
    public void setNodes(Node node){
        nodes.add(node);
    }

    @Override
    /**
     * The toString function is to convert all nodes information to the string format
     */
    public String toString() {

        StringBuilder stream = new StringBuilder();
        for(Node n : getNodes()){
            stream.append(n.toString());
            stream.append(",");
        }

        return stream.toString();
    }
}
