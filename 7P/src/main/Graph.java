import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;
import java.util.TreeMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.*;

import set.DisjointSet;

/** 	CS515 7P
 File: Graph.java
 Name: Qifei Wang
 Section: 2
 Date: 11/18/2024
 Collaboration Declaration: None
 Lab Partner: None
 Collaboration: None
 */
public class Graph<T extends Comparable<T>> {
    private Map<T, List<Edge>> adjacencyList = new HashMap<>();
    private Set<T> vertices = new LinkedHashSet<>();
    private List<Edge> edges = new ArrayList<>();



    class Edge implements Comparable<Edge> {
        public final T first;
        public final T second;
        public final int weight;

        public Edge( T v1, T v2, int w) {
            first = v1;
            second = v2;
            weight = w;
        }

        @Override
        public int compareTo(Edge rhs) {
            // TODO: Implement
            return Integer.compare(this.weight, rhs.weight);
        }

        @Override
        public boolean equals(Object any) {
            // TODO Implement
            if (this == any) return true;
            if (any == null || getClass() != any.getClass()) return false;
            Edge edge = (Edge) any;
            return weight == edge.weight && Objects.equals(first, edge.first) && Objects.equals(second, edge.second);
        }

    }

    // TODO: Add data Graph data members


    public T getRoot() {
        // TODO: Implement
        return vertices.isEmpty() ? null : vertices.iterator().next();
    }

    public Collection<T> getVertices() {
        // TODO: Implement
        return vertices;
    }

    public Collection<Edge> getEdges() {
        // TODO: Implement
        return edges;
    }

    Iterator<T> iterator() {
        // TODO: Implement
        return vertices.iterator();
    }

    // does nothing if vertex already in graph
    void addVertex( T v) {
        // TODO: Implelemt
        if (!vertices.contains(v)) {
            vertices.add(v);
            adjacencyList.putIfAbsent(v, new ArrayList<>());
        }

    }

    boolean containsVertex(T v) {
        // TODO: Implement
        return vertices.contains(v);
    }

    void addEdge( T v1, T v2, int weight) {
        // TODO: Implement
        Edge forwardEdge = new Edge(v1, v2, weight);
        edges.add(forwardEdge);
        adjacencyList.get(v1).add(forwardEdge);

        Edge backwardEdge = new Edge(v2, v1, weight);
        adjacencyList.get(v2).add(backwardEdge);
    }

    // creates a minimum spanning tree from the graph
    // use graph = graph.generateMST(); for easy conversion
    Graph<T> generateMST( ) {
	    // TODO: Implement
        Graph<T> mst = new Graph<>();
        DisjointSet<T> disjointSet = new DisjointSet<>();

        for (T vertex : vertices) {
            disjointSet.createSet(vertex);
            mst.addVertex(vertex);
        }

        Collections.sort(edges);
        for (Edge edge : edges) {
            T root1 = disjointSet.findSet(edge.first);
            T root2 = disjointSet.findSet(edge.second);
            if (!root1.equals(root2)) {
                mst.addEdge(edge.first, edge.second, edge.weight);
                disjointSet.unionSets(root1, root2);
            }
        }
        return mst;
    }

    public void setRoot(T newRoot) {
        if (vertices.contains(newRoot)) {
            List<T> vertexList = new ArrayList<>(vertices);
            vertexList.remove(newRoot);
            vertexList.add(0, newRoot); // move newRoot to front
            vertices = new LinkedHashSet<>(vertexList); // updated to the new order
        }
    }

    // returned lists, including start and target, the vertices along path
    // breadth-first traversal; best if called on MST
    List<T> findPath( T start, T target) {
        // TODO: Implement
        if (!vertices.contains(start) || !vertices.contains(target)) {
            return new ArrayList<>();
        }

        Map<T, T> parentMap = new HashMap<>();
        Set<T> visited = new HashSet<>();
        Queue<T> queue = new LinkedList<>();
        queue.add(start);
        parentMap.put(start, null);
        visited.add(start);

        while (!queue.isEmpty()) {
            T current = queue.poll();
            if (current.equals(target)) break;

            for (Edge edge : adjacencyList.getOrDefault(current, new ArrayList<>())) {
                T neighbor = edge.second;
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        List<T> path = new ArrayList<>();
        for (T at = target; at != null; at = parentMap.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);

        return path.get(0).equals(start) ? path : new ArrayList<>();
    }

}
