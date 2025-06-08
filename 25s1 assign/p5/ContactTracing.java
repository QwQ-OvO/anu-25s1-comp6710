package p5;

import java.util.*;

/**
 * Purpose: Provides contact tracing algorithms for infection spread analysis.
 * 
 * Signature: Utility class with static methods for contact tracing operations.
 * 
 * Examples:
 * - ContactTracing.determineInfectedPeopleNames(graph, "Patient0") -> ["Patient0", "Contact1", "Contact2"]
 * - ContactTracing.minBitesForFullInfection(graph) -> 3 (minimum mosquito bites needed)
 * 
 * Design Strategy: Function Composition - Use graph traversal algorithms for infection modeling.
 * 
 * Effects: Pure functions with no side effects, analyze infection spread patterns.
 */
public class ContactTracing {
    
    /**
     * Purpose: Given a graph representing interactions between people and the name of a person 
     * that has been infected by a mosquito bite, generate a list with the names of all people 
     * that have been infected.
     * 
     * Signature: Graph<String>, String -> List<String>
     * 
     * Examples:
     * - determineInfectedPeopleNames(socialGraph, "Alice") -> ["Alice", "Bob", "Carol"] 
     *   if Alice can spread infection to Bob and Carol through interactions
     * - determineInfectedPeopleNames(isolatedGraph, "Dave") -> ["Dave"] 
     *   if Dave has no interactions with others
     * 
     * Design Strategy: Function Composition - Use depth-first search recursion to find all reachable people.
     * 
     * Effects: Pure function with no side effects, returns list of infected people names.
     * 
     * Time Complexity: O(|V| + |E|) where |V| is number of vertices (people), |E| is number of edges (interactions)
     * 
     * @param interactions Graph representing interactions between people
     * @param name Name of the person initially infected by mosquito bite
     * @return List of names of all people that have been infected
     */
    public static List<String> determineInfectedPeopleNames(Graph<String> interactions, String name) {
        List<String> infected = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        
        // Use recursive helper to perform DFS
        determineInfectedHelper(interactions, name, visited, infected);
        
        return infected;
    }
    
    /**
     * Purpose: Recursive helper method for depth-first search to find all infected people.
     * 
     * Signature: Graph<String>, String, Set<String>, List<String> -> void
     * 
     * Examples:
     * - determineInfectedHelper(graph, "Alice", visited, infected) -> adds all reachable people to infected list
     * 
     * Design Strategy: Structural Recursion - Process current person, then recursively process all neighbors.
     * 
     * Effects: Modifies visited set and infected list as side effects during recursion.
     * 
     * @param interactions Graph representing interactions between people
     * @param currentPerson Current person being processed in the DFS
     * @param visited Set of already visited people to avoid cycles
     * @param infected List being built of all infected people
     */
    private static void determineInfectedHelper(Graph<String> interactions, String currentPerson, 
                                              Set<String> visited, List<String> infected) {
        // Base case: if already visited, stop recursion
        if (visited.contains(currentPerson)) {
            return;
        }
        
        // Process current person
        visited.add(currentPerson);
        infected.add(currentPerson);
        
        // Recursive case: process all neighbors (people this person can infect)
        for (Graph.Edge<String> edge : interactions.getEdges(currentPerson)) {
            String neighbor = edge.getDestination();
            determineInfectedHelper(interactions, neighbor, visited, infected);
        }
    }
    
    /**
     * Purpose: Given a graph representing interactions between people, returns the minimum 
     * number of mosquito bites required to infect all people in the graph.
     * 
     * Signature: Graph<String> -> int
     * 
     * Examples:
     * - minBitesForFullInfection(connectedGraph) -> 1 if all people are in one connected component
     * - minBitesForFullInfection(threeComponentGraph) -> 3 if graph has three disconnected components
     * - minBitesForFullInfection(emptyGraph) -> 0 if no people in graph
     * 
     * Design Strategy: Function Composition - Find number of connected components using recursive DFS.
     * 
     * Effects: Pure function with no side effects, returns minimum number of mosquito bites.
     * 
     * Time Complexity: O(|V| + |E|) where |V| is number of vertices (people), |E| is number of edges (interactions)
     * 
     * @param interactions Graph representing interactions between people
     * @return Minimum number of mosquito bites required to infect all people
     */
    public static int minBitesForFullInfection(Graph<String> interactions) {
        Set<String> allVisited = new HashSet<>();
        int componentCount = 0;
        
        // Process each connected component
        for (String person : interactions.getVertices()) {
            if (!allVisited.contains(person)) {
                // Found a new connected component - need one mosquito bite for this component
                componentCount++;
                
                // Use recursive DFS to mark all people in this component as visited
                visitComponentRecursively(interactions, person, allVisited);
            }
        }
        
        return componentCount;
    }
    
    /**
     * Purpose: Recursively visits all people in a connected component using depth-first search.
     * 
     * Signature: Graph<String>, String, Set<String> -> void
     * 
     * Examples:
     * - visitComponentRecursively(graph, "Alice", visited) -> marks Alice and all connected people as visited
     * 
     * Design Strategy: Structural Recursion - Process current person, then recursively process all unvisited neighbors.
     * 
     * Effects: Modifies visited set as side effect to mark all people in component as visited.
     * 
     * @param interactions Graph representing interactions between people
     * @param currentPerson Current person being processed in the DFS
     * @param visited Set of already visited people across all components
     */
    private static void visitComponentRecursively(Graph<String> interactions, String currentPerson, Set<String> visited) {
        // Base case: if already visited, stop recursion
        if (visited.contains(currentPerson)) {
            return;
        }
        
        // Process current person
        visited.add(currentPerson);
        
        // Recursive case: visit all unvisited neighbors
        for (Graph.Edge<String> edge : interactions.getEdges(currentPerson)) {
            String neighbor = edge.getDestination();
            visitComponentRecursively(interactions, neighbor, visited);
        }
        
        // Also need to check incoming edges (since graph is directed but infection spreads both ways)
        for (String otherPerson : interactions.getVertices()) {
            if (!visited.contains(otherPerson) && interactions.hasEdge(otherPerson, currentPerson)) {
                visitComponentRecursively(interactions, otherPerson, visited);
            }
        }
    }
} 