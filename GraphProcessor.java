import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

///////////////////////////////// FILE HEADER //////////////////////////////////
//
// Assignment name: DictionaryGraph
//
// Files submitted: Graph.java, GraphTest.java, WordProcessor.java,
// GraphProcessor.java, GraphProcessorTest.java
//
// Course: CS 400 Spring 2018
//
// Authors: Bryan Jin, Joon Jang, Aanjanaye Kajaria
//
// Emails: bjin23@wisc.edu, jjang48@wisc.edu, kajaria@wisc.edu
//
// Lecturer's Name: Deb Deppeler
//
// Outside sources: http://www.programming-algorithms.net/article/45708/Floyd-Warshall-algorithm
// (Implementation of Floyd-Worshall algorithm with predecessor matrix)
//
// Known bugs: NONE
//
// Due date: 4/16/2018
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

/**
 * This class adds additional functionality to the graph as a whole.
 * 
 * Contains an instance variable, {@link #graph}, which stores information for all the vertices and
 * edges.
 * 
 * @see #populateGraph(String) - loads a dictionary of words as vertices in the graph. - finds
 *      possible edges between all pairs of vertices and adds these edges in the graph. - returns
 *      number of vertices added as Integer. - every call to this method will add to the existing
 *      graph. - this method needs to be invoked first for other methods on shortest path
 *      computation to work.
 * @see #shortestPathPrecomputation() - applies a shortest path algorithm to precompute data
 *      structures (that store shortest path data) - the shortest path data structures are used
 *      later to to quickly find the shortest path and distance between two vertices. - this method
 *      is called after any call to populateGraph. - It is not called again unless new graph
 *      information is added via populateGraph().
 * @see #getShortestPath(String, String) - returns a list of vertices that constitute the shortest
 *      path between two given vertices, computed using the precomputed data structures computed as
 *      part of {@link #shortestPathPrecomputation()}. - {@link #shortestPathPrecomputation()} must
 *      have been invoked once before invoking this method.
 * @see #getShortestDistance(String, String) - returns distance (number of edges) as an Integer for
 *      the shortest path between two given vertices - this is computed using the precomputed data
 *      structures computed as part of {@link #shortestPathPrecomputation()}. -
 *      {@link #shortestPathPrecomputation()} must have been invoked once before invoking this
 *      method.
 * 
 * @author sapan (sapan@cs.wisc.edu)
 * 
 */
public class GraphProcessor {

    String currFileName;
    List<String> vertexData;
    Integer[][] dist;
    int[][] next;
    ArrayList<String> outputPath;
    boolean marker;

    /**
     * Graph which stores the dictionary words and their associated connections
     */
    private Graph<String> graph;

    /**
     * Constructor for this class. Initializes instances variables to set the starting state of the
     * object
     */
    public GraphProcessor() {
        this.graph = new Graph<>();
    }

    /**
     * Builds a graph from the words in a file. Populate an internal graph, by adding words from the
     * dictionary as vertices and finding and adding the corresponding connections (edges) between
     * existing words.
     * 
     * Reads a word from the file and adds it as a vertex to a graph. Repeat for all words.
     * 
     * For all possible pairs of vertices, finds if the pair of vertices is adjacent
     * {@link WordProcessor#isAdjacent(String, String)} If a pair is adjacent, adds an undirected
     * and unweighted edge between the pair of vertices in the graph.
     * 
     * @param filepath file path to the dictionary
     * @return Integer the number of vertices (words) added
     */
    public Integer populateGraph(String filepath) {
        // takes in the particular data file name
        currFileName = filepath;
        Integer count = 0;
        // load the data into an appropriately instantiated ArrayList of keyvals
        try {
            count = loadData(currFileName);
        } catch (IOException e) {
            // output error message stating problem details
            System.out.println("IOException thrown from loading data.");
            // Note on canvas said to return -1 if Exceptions are encountered
            return -1;
        }

        return count;
    }

    /**
     * Gets the list of words that create the shortest path between word1 and word2
     * 
     * Example: Given a dictionary, cat rat hat neat wheat kit shortest path between cat and wheat
     * is the following list of words: [cat, hat, heat, wheat]
     * 
     * @param word1 first word
     * @param word2 second word
     * @return List<String> list of the words
     */
    public List<String> getShortestPath(String word1, String word2) {

        // convert query Strings into uppercase as graph has only uppercase
        String w1 = word1.toUpperCase();
        String w2 = word2.toUpperCase();

        outputPath = new ArrayList<String>();

        // sanity check to see if both words are part of graph
        if (!(vertexData.contains(w1) && vertexData.contains(w2))) {
            return outputPath;
        }

        getShortestPathHelper(w1, w2);

        if (!marker) {
            outputPath = new ArrayList<String>();
            return outputPath;
        }

        return outputPath;

    }

    private void getShortestPathHelper(String word1, String word2) {

        String w1 = word1;
        String w2 = word2;

        // check if we are comparing identical words (base case of recursion)
        if (w1.equals(w2)) {
            outputPath.add(w1);
            marker = true;

        }
        // next matrix shows 0, ie there is no connection (no more valid predecessors)
        else if (next[vertexData.indexOf(w1)][vertexData.indexOf(w2)] == -1) {
            marker = false;
        }

        else {
            getShortestPath(w1,
                            vertexData.get(next[vertexData.indexOf(w1)][vertexData.indexOf(w2)]));
            outputPath.add(w2);
        }
    }

    /**
     * Gets the distance of the shortest path between word1 and word2
     * 
     * Example: Given a dictionary, cat rat hat neat wheat kit distance of the shortest path between
     * cat and wheat, [cat, hat, heat, wheat] = 3 (the number of edges in the shortest path)
     * 
     * @param word1 first word
     * @param word2 second word
     * @return Integer distance
     */
    public Integer getShortestDistance(String word1, String word2) {

        String w1 = word1.toUpperCase();
        String w2 = word2.toUpperCase();
        int w1Index;
        int w2Index;

        if (vertexData.contains(w1) && vertexData.contains(w2)) {
            if (w1.equals(w2)) {
                return -1;
            }
            w1Index = vertexData.indexOf(w1);
            w2Index = vertexData.indexOf(w2);

            return dist[w1Index][w2Index];

        }

        return -1;
    }

    /**
     * Computes shortest paths and distances between all possible pairs of vertices. This method is
     * called after every set of updates in the graph to recompute the path information. Any
     * shortest path algorithm can be used (Djikstra's or Floyd-Warshall recommended).
     * 
     * Again, the assumption is that this method will be called after a any material change to the
     * graph
     */
    public void shortestPathPrecomputation() {

        int i = 0;
        int j = 0;

        vertexData = new ArrayList<String>();

        for (String vertex : graph.getAllVertices()) {
            vertexData.add(vertex);
        }

        // set size for our distance matrix
        dist = new Integer[vertexData.size()][vertexData.size()];
        next = new int[vertexData.size()][vertexData.size()];

        // initialize minimum distances to MAX_VALUE (pseudo-infinity)
        for (int s = 0; s < vertexData.size(); s++) {
            for (int t = 0; t < vertexData.size(); t++) {
                dist[s][t] = Integer.MAX_VALUE;
                next[s][t] = -1;
            }
        }

        // iterate through all possible vertex1, vertex2 possibilities
        for (String vertex1 : vertexData) {
            i = vertexData.indexOf(vertex1);
            for (String vertex2 : vertexData) {
                j = vertexData.indexOf(vertex2);
                // if vertex1 and vertex2 have an edge between them, mark to dist
                if (graph.isAdjacent(vertex1, vertex2)) {
                    dist[i][j] = 1;
                    next[i][j] = i;
                }
            }
        }

        // fill out rest of dist matrix by Floyd-Warshall method
        for (int a = 0; a < vertexData.size(); a++) {
            for (int b = 0; b < vertexData.size(); b++) {
                for (int c = 0; c < vertexData.size(); c++) {
                    if (dist[b][a] == Integer.MAX_VALUE || dist[a][c] == Integer.MAX_VALUE) {
                        continue;
                    }
                    if (dist[b][c] > dist[b][a] + dist[a][c]) {
                        dist[b][c] = dist[b][a] + dist[a][c];
                        next[b][c] = next[a][c];
                    }
                }
            }
        }

    }

    /*
     * This method is a helper method to take the contents from the given file parse individual
     * vertices and add them to our given Graph.
     * 
     * @return returns number of vertices added to graph
     */
    private Integer loadData(String filename) throws IOException {

        Integer counter = 0;

        // Opens the given test file and stores the objects each line as a string
        Stream<String> stream = WordProcessor.getWordStream(filename);

        // add each object from Stream (treating them as individual Strings) to an
        // ArrayList
        vertexData = stream.collect(Collectors.toList());

        // adding words taken from given file into graph
        for (String word : vertexData) {
            // for each unique (and non-null) vertex added, iterate counter
            if (graph.addVertex(word) != null) {
                counter++;
            }
        }

        // check for possible edges between all words in graph
        for (String vertex1 : graph.getAllVertices()) {
            for (String vertex2 : graph.getAllVertices()) {
                if (!vertex1.equals(vertex2)) {
                    if (WordProcessor.isAdjacent(vertex1, vertex2)) {
                        graph.addEdge(vertex1, vertex2);
                    }
                }
            }
        }

        // generate edge adjacency matrix between each individual word
        shortestPathPrecomputation();

        // return the number of vertices added
        return counter;
    }
}

