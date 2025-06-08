package p5;

import java.io.*;
import java.util.*;
import java.util.function.Function;

// =============================================================================
// GRAPH DATA STRUCTURE IMPLEMENTATION
// =============================================================================

/**
 * Purpose: Represents a directed graph with vertices of type T and weighted edges.
 * 
 * Signature: Generic class representing directed graph structure with adjacency list.
 * 
 * Examples:
 * - new Graph<String>() -> creates empty string graph
 * - graph.addVertex("A") -> adds vertex "A" to graph
 * - graph.addEdge("A", "B", 5) -> adds weighted edge from A to B with weight 5
 * 
 * Design Strategy: Function Composition - Use adjacency list representation with HashMap.
 * 
 * Effects: Provides mutable graph operations, supports weighted edges, file I/O operations.
 */
public class Graph<T> {

    // =============================================================================
    // INNER EDGE CLASS
    // =============================================================================
    
    /**
     * Purpose: Represents a directed edge in the graph with source, destination, and weight.
     * 
     * Signature: Static generic class containing edge information.
     * 
     * Examples:
     * - new Edge<String>("A", "B", 10) -> edge from A to B with weight 10
     * - new Edge<Integer>(1, 2, 0) -> unweighted edge from 1 to 2 (weight 0)
     * 
     * Design Strategy: Simple Expression - Immutable edge representation.
     * 
     * Effects: Creates edge objects for graph construction, no side effects.
     */
    public static class Edge<T> {
        /** Source vertex of the edge */
        private final T source;
        /** Destination vertex of the edge */
        private final T destination;
        /** Weight of the edge (non-negative integer) */
        private final int weight;
        
        /**
         * Purpose: Constructs an edge with specified source, destination, and weight.
         * 
         * Signature: T, T, int -> Edge<T>
         * 
         * Examples:
         * - new Edge<String>("X", "Y", 5) -> weighted edge X->Y with weight 5
         * - new Edge<String>("A", "B", 0) -> unweighted edge A->B (default weight)
         * 
         * Design Strategy: Simple Expression - Store edge parameters as final fields.
         * 
         * Effects: Creates immutable edge object with specified properties.
         * 
         * @param source The source vertex
         * @param destination The destination vertex  
         * @param weight The non-negative weight of the edge
         */
        public Edge(T source, T destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
        
        /**
         * Purpose: Constructs an unweighted edge (weight 0) with specified source and destination.
         * 
         * Signature: T, T -> Edge<T>
         * 
         * Examples:
         * - new Edge<String>("A", "B") -> unweighted edge A->B
         * 
         * Design Strategy: Function Composition - Delegate to main constructor with weight 0.
         * 
         * Effects: Creates edge object with default weight 0.
         * 
         * @param source The source vertex
         * @param destination The destination vertex
         */
        public Edge(T source, T destination) {
            this(source, destination, 0);
        }
        
        /**
         * Purpose: Returns the source vertex of this edge.
         * 
         * Signature: void -> T
         * 
         * Examples:
         * - edge_A_to_B.getSource() -> "A"
         * 
         * Design Strategy: Simple Expression - Return source field.
         * 
         * Effects: Pure function with no side effects, returns source vertex.
         * 
         * @return The source vertex
         */
        public T getSource() {
            return source;
        }
        
        /**
         * Purpose: Returns the destination vertex of this edge.
         * 
         * Signature: void -> T
         * 
         * Examples:
         * - edge_A_to_B.getDestination() -> "B"
         * 
         * Design Strategy: Simple Expression - Return destination field.
         * 
         * Effects: Pure function with no side effects, returns destination vertex.
         * 
         * @return The destination vertex
         */
        public T getDestination() {
            return destination;
        }
        
        /**
         * Purpose: Returns the weight associated with this edge.
         * 
         * Signature: void -> int
         * 
         * Examples:
         * - weighted_edge.getWeight() -> 10
         * - unweighted_edge.getWeight() -> 0
         * 
         * Design Strategy: Simple Expression - Return weight field.
         * 
         * Effects: Pure function with no side effects, returns edge weight.
         * 
         * @return The weight of the edge (non-negative integer)
         */
        public int getWeight() {
            return weight;
        }
        
        /**
         * Purpose: Provides string representation of the edge for debugging.
         * 
         * Signature: void -> String
         * 
         * Examples:
         * - edge_A_to_B_weight_5.toString() -> "A -> B (5)"
         * 
         * Design Strategy: Simple Expression - Format edge information as string.
         * 
         * Effects: Pure function with no side effects, returns formatted string.
         * 
         * @return String representation of the edge
         */
        @Override
        public String toString() {
            return source + " -> " + destination + " (" + weight + ")";
        }
        
        /**
         * Purpose: Checks equality of two edges based on source, destination, and weight.
         * 
         * Signature: Object -> boolean
         * 
         * Examples:
         * - edge1.equals(edge2) -> true if same source, destination, weight
         * 
         * Design Strategy: Cases on Object Type - Check type, then compare fields.
         * 
         * Effects: Pure function with no side effects, returns boolean.
         * 
         * @param obj Object to compare with
         * @return true if edges are equal, false otherwise
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            
            Edge<?> edge = (Edge<?>) obj;
            return weight == edge.weight &&
                   Objects.equals(source, edge.source) &&
                   Objects.equals(destination, edge.destination);
        }
        
        /**
         * Purpose: Generates hash code for edge based on source, destination, and weight.
         * 
         * Signature: void -> int
         * 
         * Design Strategy: Function Composition - Combine hash codes of all fields.
         * 
         * Effects: Pure function with no side effects, returns hash code.
         * 
         * @return Hash code for the edge
         */
        @Override
        public int hashCode() {
            return Objects.hash(source, destination, weight);
        }
    }

    // =============================================================================
    // GRAPH DATA FIELDS
    // =============================================================================
    
    /** Adjacency list representation: vertex -> list of outgoing edges */
    private final Map<T, List<Edge<T>>> adjacencyList;
    /** Set of all vertices in the graph for efficient vertex operations */
    private final Set<T> vertices;
    
    // =============================================================================
    // CONSTRUCTORS
    // =============================================================================
    
    /**
     * Purpose: Creates a new empty graph.
     * 
     * Signature: void -> Graph<T>
     * 
     * Examples:
     * - new Graph<String>() -> empty string graph
     * - new Graph<Integer>() -> empty integer graph
     * 
     * Design Strategy: Simple Expression - Initialize empty adjacency list and vertex set.
     * 
     * Effects: Creates new empty graph with no vertices or edges.
     */
    public Graph() {
        this.adjacencyList = new HashMap<>();
        this.vertices = new HashSet<>();
    }
    
    // =============================================================================
    // BASIC GRAPH OPERATIONS
    // =============================================================================
    
    /**
     * Purpose: Adds a vertex to the graph if it doesn't already exist.
     * 
     * Signature: T -> void
     * 
     * Examples:
     * - graph.addVertex("A") -> adds vertex "A" to graph
     * - graph.addVertex("A") -> no effect if "A" already exists
     * 
     * Design Strategy: Simple Expression - Add vertex to set and initialize empty edge list.
     * 
     * Effects: Modifies graph by adding vertex, no effect if vertex already exists.
     * 
     * @param vertex The vertex to add to the graph
     */
    public void addVertex(T vertex) {
        if (!vertices.contains(vertex)) {
            vertices.add(vertex);
            adjacencyList.put(vertex, new ArrayList<>());
        }
    }
    
    /**
     * Purpose: Adds a weighted directed edge to the graph between two vertices.
     * 
     * Signature: T, T, int -> void
     * 
     * Examples:
     * - graph.addEdge("A", "B", 5) -> adds edge A->B with weight 5
     * - graph.addEdge("X", "Y", 0) -> adds unweighted edge X->Y
     * 
     * Design Strategy: Function Composition - Ensure vertices exist, then add edge.
     * 
     * Effects: Modifies graph by adding edge, automatically adds vertices if needed.
     * 
     * @param source The source vertex of the edge
     * @param destination The destination vertex of the edge
     * @param weight The weight of the edge (non-negative integer)
     */
    public void addEdge(T source, T destination, int weight) {
        addVertex(source);
        addVertex(destination);
        
        Edge<T> edge = new Edge<>(source, destination, weight);
        adjacencyList.get(source).add(edge);
    }
    
    /**
     * Purpose: Adds an unweighted directed edge (weight 0) between two vertices.
     * 
     * Signature: T, T -> void
     * 
     * Examples:
     * - graph.addEdge("A", "B") -> adds unweighted edge A->B
     * 
     * Design Strategy: Function Composition - Delegate to weighted addEdge with weight 0.
     * 
     * Effects: Modifies graph by adding unweighted edge.
     * 
     * @param source The source vertex of the edge
     * @param destination The destination vertex of the edge
     */
    public void addEdge(T source, T destination) {
        addEdge(source, destination, 0);
    }
    
    /**
     * Purpose: Returns the set of all vertices in the graph.
     * 
     * Signature: void -> Set<T>
     * 
     * Examples:
     * - graph.getVertices() -> {A, B, C} for graph with vertices A, B, C
     * 
     * Design Strategy: Simple Expression - Return copy of vertex set.
     * 
     * Effects: Pure function with no side effects, returns defensive copy.
     * 
     * @return Set of all vertices in the graph
     */
    public Set<T> getVertices() {
        return new HashSet<>(vertices);
    }
    
    /**
     * Purpose: Returns the list of outgoing edges from a specified vertex.
     * 
     * Signature: T -> List<Edge<T>>
     * 
     * Examples:
     * - graph.getEdges("A") -> [A->B(5), A->C(3)] for vertex A with two outgoing edges
     * - graph.getEdges("isolated") -> [] for vertex with no outgoing edges
     * 
     * Design Strategy: Simple Expression - Return copy of edge list for vertex.
     * 
     * Effects: Pure function with no side effects, returns defensive copy.
     * 
     * @param vertex The vertex to get edges from
     * @return List of outgoing edges from the vertex, empty list if vertex not found
     */
    public List<Edge<T>> getEdges(T vertex) {
        return adjacencyList.getOrDefault(vertex, new ArrayList<>());
    }
    
    /**
     * Purpose: Returns all edges in the graph as a single list.
     * 
     * Signature: void -> List<Edge<T>>
     * 
     * Examples:
     * - graph.getAllEdges() -> [A->B(5), A->C(3), B->C(2)] for graph with three edges
     * 
     * Design Strategy: Function Composition - Collect all edges from all vertices.
     * 
     * Effects: Pure function with no side effects, returns all edges.
     * 
     * @return List of all edges in the graph
     */
    public List<Edge<T>> getAllEdges() {
        List<Edge<T>> allEdges = new ArrayList<>();
        for (List<Edge<T>> edgeList : adjacencyList.values()) {
            allEdges.addAll(edgeList);
        }
        return allEdges;
    }
    
    /**
     * Purpose: Checks if the graph contains a specific vertex.
     * 
     * Signature: T -> boolean
     * 
     * Examples:
     * - graph.hasVertex("A") -> true if vertex A exists
     * - graph.hasVertex("Z") -> false if vertex Z doesn't exist
     * 
     * Design Strategy: Simple Expression - Check vertex set membership.
     * 
     * Effects: Pure function with no side effects, returns boolean.
     * 
     * @param vertex The vertex to check for
     * @return true if the vertex exists in the graph, false otherwise
     */
    public boolean hasVertex(T vertex) {
        return vertices.contains(vertex);
    }
    
    /**
     * Purpose: Checks if there is a direct edge between two vertices.
     * 
     * Signature: T, T -> boolean
     * 
     * Examples:
     * - graph.hasEdge("A", "B") -> true if direct edge A->B exists
     * - graph.hasEdge("B", "A") -> false if no direct edge B->A exists
     * 
     * Design Strategy: Function Composition - Get edges from source and check destinations.
     * 
     * Effects: Pure function with no side effects, returns boolean.
     * 
     * @param source The source vertex
     * @param destination The destination vertex
     * @return true if direct edge exists from source to destination
     */
    public boolean hasEdge(T source, T destination) {
        if (!hasVertex(source)) return false;
        
        for (Edge<T> edge : getEdges(source)) {
            if (edge.getDestination().equals(destination)) {
                return true;
            }
        }
        return false;
    }
    
    // =============================================================================
    // PART 2: FILE I/O AND SHORTEST PATHS
    // =============================================================================
    
    /**
     * Purpose: Reads a graph from a text file and creates a Graph<T> instance.
     * 
     * Signature: File, Function<String,T> -> Graph<T>
     * 
     * Examples:
     * - readGraph(file, s->s) -> reads string graph from file
     * - readGraph(file, Integer::parseInt) -> reads integer graph from file
     * 
     * Design Strategy: Function Composition - Parse file line by line and build graph.
     * 
     * Effects: Pure function, reads from file, may throw IOException.
     * 
     * @param file The file containing graph data
     * @param stringToT Function to convert string vertex names to type T
     * @return Graph constructed from file data
     * @throws IOException if file reading fails
     */
    public static <T> Graph<T> readGraph(File file, Function<String, T> stringToT) throws IOException {
        Graph<T> graph = new Graph<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            if (line == null) return graph;
            
            int numVertices = Integer.parseInt(line.trim());
            List<T> vertexList = new ArrayList<>();
            
            // Read vertices
            for (int i = 0; i < numVertices; i++) {
                line = reader.readLine();
                if (line != null) {
                    T vertex = stringToT.apply(line.trim());
                    graph.addVertex(vertex);
                    vertexList.add(vertex);
                }
            }
            
            // Read edges
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split(" ");
                if (parts.length >= 2) {
                    int sourceIndex = Integer.parseInt(parts[0]);
                    int destIndex = Integer.parseInt(parts[1]);
                    
                    if (sourceIndex < vertexList.size() && destIndex < vertexList.size()) {
                        T source = vertexList.get(sourceIndex);
                        T destination = vertexList.get(destIndex);
                        
                        int weight = 0;
                        if (parts.length >= 3) {
                            weight = Integer.parseInt(parts[2]);
                        }
                        
                        graph.addEdge(source, destination, weight);
                    }
                }
            }
        }
        
        return graph;
    }
    
    /**
     * Purpose: Reads a weighted graph from a text file with edge weights.
     * 
     * Signature: File, Function<String,T> -> Graph<T>
     * 
     * Examples:
     * - readWeightedGraph(file, s->s) -> reads weighted string graph
     * 
     * Design Strategy: Function Composition - Reuse readGraph for compatibility.
     * 
     * Effects: Pure function, reads from file, may throw IOException.
     * 
     * @param file The file containing weighted graph data
     * @param stringToT Function to convert string vertex names to type T
     * @return Weighted graph constructed from file data
     * @throws IOException if file reading fails
     */
    public static <T> Graph<T> readWeightedGraph(File file, Function<String, T> stringToT) throws IOException {
        return readGraph(file, stringToT);
    }
    
    /**
     * Purpose: Computes shortest paths from a source vertex using breadth-first search.
     * 
     * Signature: T -> Map<T,Integer>
     * 
     * Examples:
     * - graph.computeShortestPaths("A") -> {A=0, B=1, C=2} for BFS distances
     * 
     * Design Strategy: Function Composition - Use BFS algorithm with distance tracking.
     * 
     * Effects: Pure function with no side effects, returns distance map.
     * 
     * @param source The source vertex to compute paths from
     * @return Map from vertices to shortest path distances (hops)
     */
    public Map<T, Integer> computeShortestPaths(T source) {
        Map<T, Integer> distances = new HashMap<>();
        Queue<T> queue = new LinkedList<>();
        Set<T> visited = new HashSet<>();
        
        // Initialize distances to -1 (unreachable)
        for (T vertex : vertices) {
            distances.put(vertex, -1);
        }
        
        // Start BFS from source
        if (hasVertex(source)) {
            distances.put(source, 0);
            queue.offer(source);
            visited.add(source);
            
            while (!queue.isEmpty()) {
                T current = queue.poll();
                int currentDistance = distances.get(current);
                
                for (Edge<T> edge : getEdges(current)) {
                    T neighbor = edge.getDestination();
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        distances.put(neighbor, currentDistance + 1);
                        queue.offer(neighbor);
                    }
                }
            }
        }
        
        return distances;
    }
    
    /**
     * Purpose: Computes minimum weight paths from source vertex using Dijkstra's algorithm.
     * 
     * Signature: Graph<T>, T -> Map<T,Integer>
     * 
     * Examples:
     * - computeMinWeightPaths(graph, "A") -> {A=0, B=5, C=8} for weighted distances
     * 
     * Design Strategy: Function Composition - Use Dijkstra's algorithm with priority queue.
     * 
     * Effects: Pure function with no side effects, returns weight map.
     * 
     * Time Complexity: O((|V| + |E|) log |V|) where |V| is vertices, |E| is edges
     * 
     * @param weightedGraph The weighted graph to analyze
     * @param source The source vertex
     * @return Map from vertices to minimum path weights
     */
    public static <T> Map<T, Integer> computeMinWeightPaths(Graph<T> weightedGraph, T source) {
        Map<T, Integer> distances = new HashMap<>();
        PriorityQueue<Map.Entry<T, Integer>> pq = new PriorityQueue<>(Map.Entry.comparingByValue());
        Set<T> visited = new HashSet<>();
        
        // Initialize distances
        for (T vertex : weightedGraph.getVertices()) {
            distances.put(vertex, -1);
        }
        
        if (weightedGraph.hasVertex(source)) {
            distances.put(source, 0);
            pq.offer(new AbstractMap.SimpleEntry<>(source, 0));
            
            while (!pq.isEmpty()) {
                Map.Entry<T, Integer> current = pq.poll();
                T vertex = current.getKey();
                int dist = current.getValue();
                
                if (visited.contains(vertex)) continue;
                visited.add(vertex);
                
                for (Edge<T> edge : weightedGraph.getEdges(vertex)) {
                    T neighbor = edge.getDestination();
                    int newDist = dist + edge.getWeight();
                    
                    if (!visited.contains(neighbor) && 
                        (distances.get(neighbor) == -1 || newDist < distances.get(neighbor))) {
                        distances.put(neighbor, newDist);
                        pq.offer(new AbstractMap.SimpleEntry<>(neighbor, newDist));
                    }
                }
            }
        }
        
        return distances;
    }
}
