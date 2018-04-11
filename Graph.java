import java.util.HashSet;
import java.util.Set;

// Sources: https://www.sitepoint.com/implement-javas-equals-method-correctly/ for help
// implementing equals method of GraphVertex properly (I had forgotten that I could use getClass()
// to safely cast the Object parameter)
// https://coderanch.com/t/572755/certification/HashSet-adding-duplicates-hashcode-obects for
// helping me realize that I needed to override the hashCode() method for GraphVertex in order
// to properly use HashSet

/**
 * Undirected and unweighted graph implementation
 * 
 * @param <E> type of a vertex
 * 
 * @author sapan (sapan@cs.wisc.edu)
 * 
 */
public class Graph<E> implements GraphADT<E> {

    protected class GraphVertex<T> {
        final T data;
        Set<GraphVertex<T>> adjacentVertices;

        public GraphVertex(T item) {
            if (item == null) {
                throw new IllegalArgumentException();
            }
            data = item;
            adjacentVertices = new HashSet<GraphVertex<T>>();
        }


        @SuppressWarnings("rawtypes")
        @Override
        public boolean equals(Object otherVertex) {
            if (otherVertex == null) {
                return false;
            }

            if (this.getClass() != otherVertex.getClass()) {
                return false;
            }

            return this.data.equals(((GraphVertex) otherVertex).data);
        }

        @Override
        public int hashCode() {
            return data.hashCode();
        }
    }

    /**
     * Instance variables and constructors
     */

    private Set<GraphVertex<E>> vertices;

    public Graph() {
        vertices = new HashSet<GraphVertex<E>>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E addVertex(E vertex) {

        if (vertex != null) {
            GraphVertex<E> newVertex = new GraphVertex<E>(vertex);
            if (vertices.add(newVertex)) {
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
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addEdge(E vertex1, E vertex2) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeEdge(E vertex1, E vertex2) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAdjacent(E vertex1, E vertex2) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<E> getNeighbors(E vertex) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<E> getAllVertices() {
        Set<E> allData = new HashSet<E>();
        for (GraphVertex<E> v : vertices) {
            allData.add(v.data);
        }
        return allData;
    }


}
