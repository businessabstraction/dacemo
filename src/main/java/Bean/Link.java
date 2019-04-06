package Bean;

public class Link {
    private String id;
    private int group;
    private String label;
    private int level = 0;

    public Link(String id, int group){
        this.id = id;
        this.group = group;
        int idx = id.lastIndexOf("/");
//        this.label = id.lastIndexOf("/") == -1 ?
//                id.substring(id.lastIndexOf("/") + 1) :
//                id;
        this.label = id.substring(idx+1);
    }

    public Link(String id, int group,String label,int level){
        this.id = id;
        this.group = group;
        this.label = label;
        this.level = level;
    }

    public String getId(){
        return id;
    }
    public String getLabel(){
        return label;
    }
    public int getGroup() {
        return group;
    }
    public int getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return "" + id +","+group +","+label +","+level;
    }
}
