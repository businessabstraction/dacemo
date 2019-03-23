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

        String stream = "";
        for(Node n : getNodes()){
            stream +=n.toString();
            stream +=",";
        }

        return stream;
    }
}
