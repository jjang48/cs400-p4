import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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
// Outside sources: NONE
//
// Known bugs: NONE
//
// Due date: 4/16/2018
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////


/**
 * Undirected and unweighted graph implementation
 * 
 * @param <E> type of a vertex
 * 
 * @author sapan (sapan@cs.wisc.edu), Bryan Jin (bjin23@wisc.edu), Joon Jang (jjang48@wisc.edu),
 *         Aanjanaye Kajaria (kajaria@wisc.edu)
 * 
 */
public class Graph<E> implements GraphADT<E> {

    /**
     * Instance variables and constructors
     */

    // this Map associates each vertex with a set that acts as the vertex's adjacency list
    // the use of a set here means we do not allow duplicate edges
    // note that we will never allow a key or value in this Map to be null
    private Map<E, HashSet<E>> vertices;

    public Graph() {
        vertices = new HashMap<E, HashSet<E>>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E addVertex(E vertex) {
        if (vertex != null) {
            if (vertices.putIfAbsent(vertex, new HashSet<E>()) == null) {
                return vertex;
            }
        }
        return null;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E removeVertex(E vertex) {
        if (vertex != null) {
            if (vertices.remove(vertex) != null) {
                // remove vertex from all adjacency lists
                for (Set<E> adjList : vertices.values()) {
                    for (E v : adjList) {
                        if (vertex.equals(v)) {
                            adjList.remove(v);
                            // adjList is a Set so removing vertex once is enough
                            break;
                        }
                    }
                }
                return vertex;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addEdge(E vertex1, E vertex2) {
        if (vertices.containsKey(vertex1) && vertices.containsKey(vertex2)) {
            // note we know here that vertex1 and vertex2 are both not null
            if (!vertex1.equals(vertex2)) {
                // checks to see if edge already exists
                // assumes that an edge from vertex1 to vertex2 exists iff
                // an edge from vertex2 to vertex 1 exists
                if (vertices.get(vertex1).add(vertex2)) {
                    vertices.get(vertex2).add(vertex1);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeEdge(E vertex1, E vertex2) {
        if (vertices.containsKey(vertex1) && vertices.containsKey(vertex2)) {
            // note we know here that vertex1 and vertex2 are both not null
            if (!vertex1.equals(vertex2)) {
                // checks to see if edge actually exists
                // assumes that an edge from vertex1 to vertex2 exists iff
                // an edge from vertex2 to vertex 1 exists
                if (vertices.get(vertex1).remove(vertex2)) {
                    vertices.get(vertex2).remove(vertex1);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAdjacent(E vertex1, E vertex2) {
        if (vertices.containsKey(vertex1) && vertices.containsKey(vertex2)) {
            // note we know here that vertex1 and vertex2 are both not null
            if (!vertex1.equals(vertex2)) {
                if (vertices.get(vertex1).contains(vertex2)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<E> getNeighbors(E vertex) {
        if (vertex == null || !vertices.containsKey(vertex)) {
            throw new IllegalArgumentException();
        }

        return vertices.get(vertex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<E> getAllVertices() {
        return vertices.keySet();
    }


}
