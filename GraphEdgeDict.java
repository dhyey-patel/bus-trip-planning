/**
 * @author Dhyey Patel
 *
 * This class is made to create a dictionary where the key is a GraphEdge and the data is a char which indicates if the edge is labelled or not
 * For reference u = unlabelled, l = labelled
 */

public class GraphEdgeDict {
    private GraphEdge[] edges;
    private char[] labels;
    private int size, numEdges=0;

    // This is the constructor of the class that will create two arrays of size 100
    public GraphEdgeDict(){
        edges = new GraphEdge[100];
        labels = new char[100];
        size = 100;
    }

    // This method will be used to add to the dictionary
    public void put(GraphEdge edge, char label){
        // If the dictionary is full then we need to call the helper function to expand it
        if(numEdges>=size){
            expand();
        }
        // It will add the edge and the given label to both of the arrays and increase numEdges by 1
        edges[numEdges] = edge;
        labels[numEdges] = label;
        numEdges++;
    }

    // This method is used to check if the edge is already labelled
    public boolean isLabelled(GraphEdge edge){
        int index=-1;
        // Go through the array to find the matching edge
        for (int i=0; i<numEdges; i++){
            if(edges[i].firstEndpoint().getName() == edge.firstEndpoint().getName() && edges[i].secondEndpoint().getName() == edge.secondEndpoint().getName()){
                index = i;
            }
        }
        // If the matching edge is 'u' then return false, otherwise return true
        return labels[index] != 'u';
    }

    // This method is used to set the label of a edge that is in the dictionary
    public void setLabel(GraphEdge edge, char label){
        // Find the matching edges and label them with the given label
        for (int i=0; i<numEdges; i++){
            if(edges[i].firstEndpoint().getName() == edge.firstEndpoint().getName() && edges[i].secondEndpoint().getName() == edge.secondEndpoint().getName()){
                labels[i] = label;
            }
            if(edges[i].secondEndpoint().getName() == edge.firstEndpoint().getName() && edges[i].firstEndpoint().getName() == edge.secondEndpoint().getName()){
                labels[i] = label;
            }
        }
    }

    // This is a private helper function that is used to expand the size of the dictionary
    private void expand(){
        // Create new arrays with double the size
        size = size*2;
        GraphEdge[] largeEdges = new GraphEdge[size];
        char[] largeLabels = new char[size];
        // Transfer everything over
        for (int i=0; i<edges.length; i++ ){
            largeEdges[i] = edges[i];
            largeLabels[i] = labels[i];
        }
        edges = largeEdges;
        labels = largeLabels;
    }
}

