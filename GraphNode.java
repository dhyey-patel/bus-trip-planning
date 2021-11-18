/**
 * @author Dhyey Patel
 *
 * This class is made to represent a node in the class graph
 */
public class GraphNode {
    private int name;
    Boolean mark;

    // This is the constructor for the class, it will create a node with the give name and mark it false
    public GraphNode(int name){
        this.name = name;
        mark =false;
    }

    // This is a public method to mark or unmark the node
    public void setMark(Boolean mark) {
        this.mark = mark;
    }

    // This method is used to see weather the node is marked or not
    public boolean getMark(){
        return mark;
    }

    int getName(){
        return name;
    }
}
