package mif.vu.lt.resathlon.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mif.vu.lt.resathlon.models.Event;
import mif.vu.lt.resathlon.models.Params;
import mif.vu.lt.resathlon.models.athletes.Athlete;

public class ScoreManager {
    
    private static Map<String, List<Double>> sortedEventScores  = new HashMap<String, List<Double>>();
    
    /*
     * Calculate Athlete event score and total score
     */
    public static void calculateScores(Athlete atl) {
    	double total_score = 0.0;
    	for (Event event: atl.getEvents()) {
    		String event_name = event.getName();
    		Params param = Params.valueOf(event_name);
            if (param.units() == "s") {
            	double score = calculate_time_event(event.getName(), event.getPoints());
            	total_score += score;
                event.setScore(score);
            } else if (param.units() == "m") {
            	double score = calculate_dist_event(event.getName(), event.getPoints());
            	total_score += score;
                event.setScore(score);
            }
        }
    	atl.setTotalScore(total_score);
    }
    
    /*
     * Order athletes in list by Total score
     */
    public static void orderAthletes(List<Athlete> atls) {
        Collections.sort(atls);
    }
    
    /*
     * Calculate Athlete event places
     */
    public static void countEventPlace(List<Athlete> atls) {
    	for (Athlete athlete: atls) {
    		for (Event event: athlete.getEvents()) {
    			String eventName = event.getName();
    			if (!sortedEventScores.containsKey(eventName)) { 
    				sortedEventScores
    					.put(eventName, new ArrayList<Double>());
    				sortedEventScores.get(eventName).add(event.getScore());
    			} else { 
    				sortedEventScores.get(eventName).add(event.getScore());
    			}
    			// Sorts on every event score addition. May interupt performance
    			Collections.sort(sortedEventScores.get(eventName), Collections.reverseOrder());
    		}
    	}
    	// Calculate event places
    	for (Athlete athlete: atls) { 
    		for (Event event: athlete.getEvents()) {
    			String eventName = event.getName();
    			Double[] scores = sortedEventScores.get(eventName).toArray(new Double[0]);
    	        List<Integer> temp_list = new ArrayList<>();
    	        for (int i = 0; i < scores.length; i++) {
    	            if (scores[i] == event.getScore()) {
    	                // i + 1 Athlete cannot have 0 place 
    	                temp_list.add(i + 1); 
    	            }
    	        }
    	        // Set athlete event place
    	        event.setPlaces(temp_list.toArray(new Integer[temp_list.size()]));
    		}
    	}
    }

    /*
     * Calculate time events
     */
    private static double calculate_time_event(String event_name, double rslt) {
        double a = Params.valueOf(event_name).a();
        double b = Params.valueOf(event_name).b();
        double c = Params.valueOf(event_name).c();
        return Math.abs(a * (Math.pow((b - rslt), c)));
    }
    
    /*
     * Calculate distance event
     */
    private static double calculate_dist_event(String event_name, double rslt) {
        double a = Params.valueOf(event_name).a();
        double b = Params.valueOf(event_name).b();
        double c = Params.valueOf(event_name).c();
        // Addition Math.abs to prevent NaN output
        return Math.abs(a * (Math.pow((Math.abs(rslt - b)), c)));
    }
    
}