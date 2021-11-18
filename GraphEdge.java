/**
 * @author Dhyey Patel
 *
 * This class is made to represent a graph edge with a given busline
 */

public class GraphEdge {
    private GraphNode first, second;
    private char busLine;

    // This is the constructor of the class that takes in the two endpoints and the busline associated with the edge
    public GraphEdge(GraphNode u, GraphNode v, char busLine){
        first = u;
        second = v;
        this.busLine = busLine;
    }

    // These are all the getter methods for the endpoints and the busline
    public GraphNode firstEndpoint(){
        return first;
    }

    public GraphNode secondEndpoint(){
        return  second;
    }

    public char getBusLine(){
        return busLine;
    }
}
