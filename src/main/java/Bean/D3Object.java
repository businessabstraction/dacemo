package Bean;

import java.util.ArrayList;

public class D3Object {
    private ArrayList<Node> nodes;

    public D3Object(ArrayList<Node> nodes){
        this.nodes = nodes;
    }

    public D3Object(){

    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }
    public void setNodes(Node node){
        nodes.add(node);
    }

    @Override
    public String toString() {

        StringBuilder stream = new StringBuilder();
        for(Node n : getNodes()){
            stream.append(n.toString());
            stream.append(",");
        }

        return stream.toString();
    }
}
