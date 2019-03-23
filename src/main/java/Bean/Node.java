package Bean;

public class Node {
    private String id;
    private int group;
    private String label = "default";
    private int level = 0;

    public Node(String id, int group){
        this.id = id;
        this.group = group;
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
