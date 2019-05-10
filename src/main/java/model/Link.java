package model;

import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The link entity class
 */
public class Link {
    private String id;
    private int group;
    private String label;
    private int level = 0;

    /**
     * The constructor of link object, only set the unique id and group
     * @param id the unique id about the link
     * @param group the group about link should belongs to
     */
    Link(String id, int group){
        this.id = id;
        this.group = group;

        int lastSeperatorIndex = id.lastIndexOf("/") > id.lastIndexOf("#") ?
                id.lastIndexOf("/") :
                id.lastIndexOf("#");

        if (lastSeperatorIndex == -1){
            this.label = id;
        } else if (id.matches("\".*\".*")){
            this.label = id.split("\".*?\"")[0];
        } else {
            this.label = id.substring(lastSeperatorIndex + 1);
        }
    }
    /**
     * The overwrite node constructor which required more information about links
     * @param id The unique id of link
     * @param group The group which the link should belongs to
     * @param label The label of the link, which is display the name information about node
     * @param level The level which the link should belongs to
     */
    public Link(String id, int group,String label,int level){
        this.id = id;
        this.group = group;
        this.label = label;
        this.level = level;
    }
    /**
     * Get the id of the link
     * @return The unique id of link
     */
    public String getId(){
        return id;
    }
    /**
     * Get the label of the link
     * @return The unique label of link
     */
    public String getLabel(){
        return label;
    }
    /**
     * Get the group of the link
     * @return The unique group of link
     */
    public int getGroup() {
        return group;
    }
    /**
     * Get the level of the link
     * @return The unique level of link
     */
    public int getLevel() {
        return level;
    }
    /**
     * Get the structure of link
     * @return a map of link, only for JSON format conversion
     */
    LinkedHashMap<String, Object> getMap() {
        LinkedHashMap<String, Object> m = new LinkedHashMap<>();
        m.put("id", id);
        m.put("group", group);
        m.put("label", label);
        m.put("level", level);
        return m;
    }
    @Override
    /*  
     * The toString function to convert link information to string type
     */
    public String toString() {
        return "id: "+ id + ", group: " + group + ", label: " + label + ", level: " + level;
    }
}
