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

    /**
     * The constructor of the d3object
     * @param subject a Node object which represents the subject of triple store
     * @param predicates a list of links which represent the predicates
     * @param objects a list of nodes which represent the objects
     */
    public D3Object(Node subject, ArrayList<Link> predicates, ArrayList<Node> objects){
        this.subject = subject;
        this.predicates = predicates;
        this.objects = objects;
    }

    public D3Object(){

    }

    /**
     * The get function for the data object to get the subject node of the clicked node
     * @return the subject node
     */
    public Node getSubject() {
        return subject;
    }
    /**
     * The get function for the data object to get the predicate links of the clicked node
     * @return the predicate links
     */
    public ArrayList<Link> getPredicates() {
        return predicates;
    }
    /**
     * The get function for the data object to get the object node of the clicked node
     * @return the object node
     */
    public ArrayList<Node> getObjects() {
        return objects;
    }
    /**
     * The set function for the data object to add subject for the clicked node
     * @param node set the value of subject node
     */
    public void setSubject(Node node){
        subject = node;
    }

    /**
     * The set function for the data object to add one predicate link to the predicate list
     * @param link This link will be added to the predicate list
     */
    public void setPredicates(Link link){
        predicates.add(link);
    }
    /**
     * The set function for the data object to add one object node to the object list
     * @param node This link will be added to the object list
     */
    public void setObjects(Node node){
        objects.add(node);
    }

    @Override
    /**
     * The toString function is to convert all d3object information to the string format
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
