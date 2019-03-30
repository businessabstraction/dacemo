package Bean;

public class Node {
    private String id;
    private int group;
    private String label;
    private int level = 0;

    public Node(String id, int group){
        this.id = id;
        this.group = group;
        int idx = id.lastIndexOf("/");
//        this.label = id.lastIndexOf("/") == -1 ?
//                id.substring(id.lastIndexOf("/") + 1) :
//                id; // TODO: 23/03/2019 Basic Labelling function, update.
        this.label = id.substring(idx+1);
    }

    public Node(String id, int group,String label,int level){
        this.id = id;
        this.group = group;
        this.label = label;
        this.level = level;
    }

    @Override
    public String toString() {
        return "" + id +","+group +","+label +","+level;
    }
}
