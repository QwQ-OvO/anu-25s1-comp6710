package p5;

import java.util.*;

/**
 * Purpose: Provides ambulance dispatch algorithms for emergency response optimization.
 * 
 * Signature: Utility class with static methods for ambulance dispatch operations.
 * 
 * Examples:
 * - AmbulanceDispatch.identifyAmbulances(cityGraph, "Hospital", 2, ambulanceMap) -> [101, 205]
 *   returns IDs of 2 closest ambulances to Hospital location
 * 
 * Design Strategy: Function Composition - Use shortest path algorithms for optimal ambulance selection.
 * 
 * Effects: Pure functions with no side effects, analyze optimal ambulance deployment.
 */
public class AmbulanceDispatch {
    
    /**
     * Purpose: Given the name of the vertex where an accident has occurred, the number of free 
     * ambulances required to handle the accident, and the current locations of free ambulances, 
     * return a list of the ambulance identifiers that can arrive in the shortest possible time 
     * to the location of the accident.
     * 
     * Signature: Graph<String>, String, int, Map<Integer,String> -> List<Integer>
     * 
     * Examples:
     * - identifyAmbulances(cityGraph, "MainSt", 2, {101:"HospitalA", 202:"ParkSt", 303:"MainSt"}) 
     *   -> [303, 101] if ambulance 303 is already at MainSt and 101 is next closest
     * - identifyAmbulances(cityGraph, "Downtown", 1, {404:"Uptown"}) 
     *   -> [404] if only one ambulance available and it can reach Downtown
     * - identifyAmbulances(cityGraph, "IslandLocation", 3, {501:"MainlandA", 502:"MainlandB"}) 
     *   -> [501, 502] if only 2 ambulances can reach the island (less than 3 requested)
     * 
     * Design Strategy: Function Composition - Compute shortest paths from accident location, 
     * then sort ambulances by travel time and select the required number.
     * 
     * Effects: Pure function with no side effects, returns list of optimal ambulance IDs.
     * 
     * Time Complexity: O((|V| + |E|) log |V| + |A| log |A|) where |V| is vertices, |E| is edges, |A| is ambulances
     * 
     * @param locations Graph representing city locations and travel times between them
     * @param accidentLocation Name of the vertex where the accident occurred
     * @param numberAmbulancesRequired Number of ambulances needed for the accident
     * @param ambulanceLocations Map from ambulance ID to current location name
     * @return List of ambulance IDs that can arrive fastest, up to the number required
     */
    public static List<Integer> identifyAmbulances(Graph<String> locations, String accidentLocation,
                                                  int numberAmbulancesRequired, Map<Integer, String> ambulanceLocations) {
        
        // Create list of ambulances with their travel times to accident location
        List<AmbulanceInfo> ambulanceInfos = new ArrayList<>();
        
        for (Map.Entry<Integer, String> entry : ambulanceLocations.entrySet()) {
            Integer ambulanceId = entry.getKey();
            String ambulanceLocation = entry.getValue();
            
            // Compute travel time from ambulance location to accident location
            Map<String, Integer> distancesFromAmbulance = Graph.computeMinWeightPaths(locations, ambulanceLocation);
            Integer travelTime = distancesFromAmbulance.get(accidentLocation);
            
            if (travelTime != null && travelTime >= 0) {
                // Ambulance can reach the accident location
                ambulanceInfos.add(new AmbulanceInfo(ambulanceId, travelTime));
            }
        }
        
        // Sort ambulances by travel time (shortest first)
        ambulanceInfos.sort(Comparator.comparingInt(info -> info.travelTime));
        
        // Select the required number of ambulances (or all available if fewer than required)
        List<Integer> selectedAmbulances = new ArrayList<>();
        int numToSelect = Math.min(numberAmbulancesRequired, ambulanceInfos.size());
        
        for (int i = 0; i < numToSelect; i++) {
            selectedAmbulances.add(ambulanceInfos.get(i).ambulanceId);
        }
        
        return selectedAmbulances;
    }
    
    /**
     * Purpose: Helper class to store ambulance information for sorting by travel time.
     * 
     * Signature: Private static class containing ambulance ID and travel time.
     * 
     * Examples:
     * - new AmbulanceInfo(101, 15) -> ambulance 101 with 15 time units travel time
     * 
     * Design Strategy: Simple Expression - Immutable data holder for ambulance information.
     * 
     * Effects: Creates ambulance info objects for sorting, no side effects.
     */
    private static class AmbulanceInfo {
        /** Unique identifier of the ambulance */
        final Integer ambulanceId;
        /** Travel time to reach accident location */
        final Integer travelTime;
        
        /**
         * Purpose: Constructs ambulance information with ID and travel time.
         * 
         * Signature: Integer, Integer -> AmbulanceInfo
         * 
         * Examples:
         * - new AmbulanceInfo(205, 8) -> ambulance 205 with 8 time units travel time
         * 
         * Design Strategy: Simple Expression - Store parameters as final fields.
         * 
         * Effects: Creates immutable ambulance info object.
         * 
         * @param ambulanceId The unique identifier of the ambulance
         * @param travelTime The travel time to reach the accident location
         */
        AmbulanceInfo(Integer ambulanceId, Integer travelTime) {
            this.ambulanceId = ambulanceId;
            this.travelTime = travelTime;
        }
        
        /**
         * Purpose: Provides string representation for debugging purposes.
         * 
         * Signature: void -> String
         * 
         * Examples:
         * - ambulanceInfo.toString() -> "Ambulance 101: 15 time units"
         * 
         * Design Strategy: Simple Expression - Format ambulance information as string.
         * 
         * Effects: Pure function with no side effects, returns formatted string.
         * 
         * @return String representation of ambulance information
         */
        @Override
        public String toString() {
            return "Ambulance " + ambulanceId + ": " + travelTime + " time units";
        }
    }
} 