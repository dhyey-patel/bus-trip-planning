/**
 * @author Dhyey Patel
 *
 * This class is made to represent a graph using a adjecency matrix
 */
import java.util.*;

public class Graph {
    private GraphEdge [][] adjMatrix;
    private GraphNode [] nodes;
    private int size;

    // This is the constructor of the class and it will create a 2D array of the lenght and width n
    public Graph(int n){
        adjMatrix = new GraphEdge[n][n];
        // Create an array of all the nodes and store all the nodes in it, and also store the size
        nodes = new GraphNode[n];
        for (int i=0; i<n; i++){
            nodes[i] = new GraphNode(i);
        }
        size = n;
    }

    // This method is used to add an edge between two nodes in the graph
    // This graph will throw a Graph exception if the node does not exist or if there is already an edge between those nodes
    public void insertEdge(GraphNode u, GraphNode v, char busLine) throws GraphException{
        // Throw a GraphException if the node does not exist or if there is already an edge between those nodes
        if(u== null || v == null){
            throw new GraphException("The node does not exist so there cannot be an edge between them");
        }
        if(u.getName()>=size || v.getName()>=size){
            throw new GraphException("The node does not exist so there cannot be an edge between them");
        }
        if(areAdjacent(u,v)){
            throw new GraphException(("There is already an edge between these nodes"+ u.getName()+" and "+v.getName()));
        }
        // It will first create a temp GraphEdge and put it into the matrix
        GraphEdge temp = new GraphEdge(u, v, busLine);
        adjMatrix[u.getName()][v.getName()] = temp;
        adjMatrix[v.getName()][u.getName()] = temp;
    }

    // This method is used to get the node with the given name
    // This method will throw a GraphException if the node does not exist
    public GraphNode getNode(int name) throws GraphException {
        // Throw a GraphException if the node does not exist
        if(name>=size){
            throw new GraphException("This node does not exist");
        }
        if(nodes[name] == null){
            throw new GraphException("This node does not exist");
        }
        // Return the node associated with the name
        return nodes[name];
    }

    // This method will return all the incident edges of a given node u
    // This method will throw a GraphException if the node does not exist
    public Iterator<GraphEdge> incidentEdges (GraphNode u)throws GraphException{
        // Throw a GraphException if the node does not exist
        if(u== null){
            throw new GraphException("This node does not exist");
        }
        if(u.getName() >= size){
            throw new GraphException("This node does not exist");
        }
        // Create a list and add every edge associated to the given node to the list
        ArrayList<GraphEdge> list = new ArrayList<GraphEdge>();
        for (int i=0;i<size;i++){
            if (adjMatrix[u.getName()][i] != null ){
                list.add(adjMatrix[u.getName()][i]);
                //System.out.println("Edge between "+ u.getName()+" and "+ i);
            }
        }
        // Return the iterator of the list
        return (list.iterator());
    }

    // This method will return the edge between the two given nodes
    // This will throw a GraphException if u or v do not exist or if there is no edge between u and v
    public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException{
        // Throw a GraphException if u or v do not exist or if there is no edge between u and v
        if(u== null || v == null){
            throw new GraphException("This node does not exist");
        }
        if(u.getName() >= size || v.getName() >= size){
            throw new GraphException("This node does not exist");
        }
        if(adjMatrix[u.getName()][v.getName()] == null){
            throw new GraphException("There is no edge between these two nodes");
        }
        // Return the edge between u and v
        return adjMatrix[u.getName()][v.getName()];
    }

    // This method will determine if there is a edge between two nodes u and v
    // This method will throw a GraphException if u or v do not exist
    public boolean areAdjacent (GraphNode u, GraphNode v) throws GraphException{
        // Throw a GraphException if u or v do not exist
        if(u== null || v == null){
            throw new GraphException("This node does not exist");
        }
        if(u.getName() >= size || v.getName() >= size){
            throw new GraphException("This node does not exist");
        }
        // If there is no value stored at u and v then there is no edge between them else there is
        if (adjMatrix[u.getName()][v.getName()] == null) {
            return false;
        }
        return true;
    }
}
