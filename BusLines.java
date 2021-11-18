/**
 * @author Dhyey Patel
 *
 * This is a class made to implement the Graph class created it will also be creating the alogrithm that finds the path with the required bus changes
 */
import java.io.*;
import java.util.*;

public class BusLines {
    private int maxBusChanges, start, destination, busChanges;
    private Graph map;
    private GraphEdgeDict edges = new GraphEdgeDict();
    private GraphNode[] nodes;
    MyStack<GraphNode> pathStack = new MyStack<GraphNode>();
    MyStack<GraphNode> finalPathStack = new MyStack<GraphNode>();
    MyStack<GraphNode> stackPath = new MyStack<GraphNode>();
    private boolean checkFinalDestination = false;

    // This is the constructor for the class and it will create the map from the given inputfile and create a graph for it
    // This method will throw a MapException in the event that there is an error reading from the file, or if the file's format is incorrect
    public BusLines(String inputFile) throws MapException {
        int width, height;
        String line1, line2;
        String[] words;
        int counter = 0;
        try {
            BufferedReader in = new BufferedReader(new FileReader(inputFile));
            line1 = in.readLine();
            words = line1.split(" ");
            // The first line will have all the relevant information like width, height and max bus changes
            width = Integer.parseInt(words[1]);
            height = Integer.parseInt(words[2]);
            maxBusChanges = Integer.parseInt(words[3]);
            // Create a graph that will have width * height nodes in it
            map = new Graph(width * height);
            // Create an array of size width*height and create a node for each position
            nodes = new GraphNode[width * height];
            for (int i = 0; i < width * height; i++) {
                nodes[i] = new GraphNode(i);
            }
            // Read two lines at a time until the end of the file is reached
            line1 = in.readLine();
            line2 = in.readLine();
            for (int reader = 0; reader < height; reader++) {
                // We will go through each char in each line
                for (int i = 0; i < line1.length(); i++) {
                    // Each pair of lines is handled differently so if I is even handle line2, otherwise handle line1
                    if (i % 2 == 0) {
                        // If the char is S or D then store them appropriately as they will be needed to create the path
                        if (line1.charAt(i) == 'S') {
                            start = counter;
                        }
                        if (line1.charAt(i) == 'D') {
                            destination = counter;
                        }
                        // As long as line2 is not empty
                        if (line2 != null) {
                            // If the char at index i is not a space then add the edge to the map as well as the dictionary
                            if (line2.charAt(i) != ' ') {
                                map.insertEdge(nodes[counter], nodes[counter + width], line2.charAt(i));
                                edges.put(new GraphEdge(nodes[counter], nodes[counter + width], line2.charAt(i)), 'u');
                            }
                        }
                    }
                    else {
                        // If the char at index i is not a space then add the edge to the map as well as the dictionary
                        if (line1.charAt(i) != ' ') {
                            map.insertEdge(nodes[counter], nodes[counter + 1], line1.charAt(i));
                            edges.put(new GraphEdge(nodes[counter], nodes[counter + 1], line1.charAt(i)), 'u');
                        }
                        counter++;
                    }
                }
                counter++;
                // Read in the next two lines
                line1 = in.readLine();
                line2 = in.readLine();
            }
        }
        // This will catch any exceptions thrown as throw a Map exception with the message
        catch (Exception e) {
            throw new MapException("There was a problem reading form the file");
        }
    }

    // This method will return the graph of the inputfile as long as it is not empty
    public Graph getGraph() throws MapException {
        if (map == null) {
            throw new MapException("The map does not exist");
        }
        return map;
    }

    // This method will return an iterator will the path from start to destination
    public Iterator<GraphNode> trip() {
        busChanges = 0;
        // This will compute the path using a depth for search algorithm
        getPath(nodes[start], nodes[destination], ' ');
        // If there is no solution then return null
        if(!checkFinalDestination){
            return null;
        }
        // If there is a solution then it will be in the finalPathStack, where the destination will be at the top
        ArrayList<GraphNode> list = new ArrayList<GraphNode>();
        // Add everything from finalPathStack to a list in reverse order
        while (!finalPathStack.isEmpty()){
            list.add(finalPathStack.pop());
        }
        // Return the iterator of the list
        return(list.iterator());
    }

    // This is a private helper function that is used to compute the path using the depth for search algorithm
    private void getPath(GraphNode s, GraphNode d, char currBusLine) {
        // If the final destination is not previously been reached
        if(!checkFinalDestination){
            boolean checkBusChange=false;
            // First we will mark the start node and we will push it into the stack
            s.setMark(true);
            pathStack.push(s);
            // If s is equal to d then we have just reached our final destination
            if (s.getName() == d.getName()) {
                // Transfer everything from pathStack to finalPathStack  (reverses the order)
                while(!pathStack.isEmpty()){
                    finalPathStack.push(pathStack.pop());
                }
                // set checkFinalDestination to true
                checkFinalDestination = true;
            }
            try {
                Iterator<GraphEdge> allEdges = map.incidentEdges(s);
                GraphEdge tempEdge = null;
                // For all of the edges incident to s
                while (allEdges.hasNext()) {
                    tempEdge = allEdges.next();
                    // If the edge is not labelled
                    if(!edges.isLabelled(tempEdge)){
                        // If this is the first line then set currBusLine to the first edge's line
                        if(currBusLine == ' '){
                            currBusLine = tempEdge.getBusLine();
                        }
                        // If the max number if bus changes has been met
                        if(busChanges == maxBusChanges){
                            // As long as there are no more bus changes
                            if(currBusLine == tempEdge.getBusLine()){
                                // Mark the edge as labelled
                                edges.setLabel(tempEdge, 'l');
                                // Recursively call getPath with the next node
                                // If the firstEndPoint is equal to s then you need to recursively call on the secondEndpoint else call on the firstEndpoint
                                if(tempEdge.firstEndpoint().getName() == s.getName()){
                                    getPath(tempEdge.secondEndpoint(), d, currBusLine);
                                }
                                else{
                                    getPath(tempEdge.firstEndpoint(), d, currBusLine);
                                }
                            }
                        }
                        // In the case that the max number of bus changes has not been reached
                        else if (busChanges < maxBusChanges){
                            checkBusChange = false;
                            // If the line is a seperate line then change the current line, and add 1 to busChanges
                            if(currBusLine != tempEdge.getBusLine()){
                                checkBusChange = true;
                                busChanges++;
                                currBusLine = tempEdge.getBusLine();
                            }
                            // Mark the edge as labelled
                            edges.setLabel(tempEdge, 'l');
                            // Recursively call getPath with the next node
                            // If the firstEndPoint is equal to s then you need to recursively call on the secondEndpoint else call on the firstEndpoint
                            if(tempEdge.firstEndpoint().getName() == s.getName()){
                                getPath(tempEdge.secondEndpoint(), d, currBusLine);
                            }
                            else{
                                getPath(tempEdge.firstEndpoint(), d, currBusLine);
                            }
                        }
                        // If the solution is not found on this edge then backtrack by unlabelling the edge
                        edges.setLabel(tempEdge,'u');
                        // If the unlabelled edge created a busChange then subtract 1 from busChanges as we undid the bus change
                        if(checkBusChange){
                            busChanges--;
                        }
                    }
                }
                // If the path is not found from s then pop s from the stack and unmark it so it can be revisited
                if(!pathStack.isEmpty()){
                    pathStack.pop().setMark(false);
                }

            }
            //If the GraphException is thrown then print the error message
            catch (GraphException e) {
                System.out.println(e.getMessage());
            }
        }

    }

}
