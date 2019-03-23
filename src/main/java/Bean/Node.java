package Bean;

public class Node {
    private String id;
    private int group;

    public Node(String id, int group){
        this.id = id;
        this.group = group;
    }


    @Override
    public String toString() {
        return "" + id +","+group;
    }
}
