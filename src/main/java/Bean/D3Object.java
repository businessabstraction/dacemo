package Bean;

import java.util.ArrayList;

/**
 * The is the D3 graph data objects class, which is a entity class.
 * It have bean used for create the data object for data transfer to the front end
 */
public class D3Object {
    private Node subject;
    private ArrayList<Link> predicates;
    private ArrayList<Node> objects;

    // the constructor of d3 data object
    public D3Object(Node subject, ArrayList<Link> predicates, ArrayList<Node> objects){
        this.subject = subject;
        this.predicates = predicates;
        this.objects = objects;
    }

    public D3Object(){

    }

    /**
     * The get function for the data object to get all subject of the graph
     * @return it will return a ArrayList which contains all subject
     */
    public Node getSubject() {
        return subject;
    }
    public ArrayList<Link> getPredicates() {
        return predicates;
    }
    public ArrayList<Node> getObjects() {
        return objects;
    }
    /**
     * The set function for the data object to add subject for current graph
     * @param node The node object will add to the node list which contains all
     *             subject of the graph
     */
    public void setSubject(Node node){
        subject = node;
    }
    public void setPredicates(Link link){
        predicates.add(link);
    }
    public void setObjects(Node nodes){
        objects.add(nodes);
    }

    @Override
    /**
     * The toString function is to convert all subject information to the string format
     */
    public String toString() {

        StringBuilder stream = new StringBuilder();
        stream.append(getSubject());
        stream.append(", ");

        for(Link l : getPredicates()){
            stream.append(l.toString());
            stream.append(", ");
        }
        for(Node n : getObjects()){
            stream.append(n.toString());
            stream.append(", ");
        }

        return stream.toString();
    }
}
