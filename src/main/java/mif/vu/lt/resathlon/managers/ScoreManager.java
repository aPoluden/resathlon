package mif.vu.lt.resathlon.managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mif.vu.lt.resathlon.models.Athlete;
import mif.vu.lt.resathlon.models.Params;

public class ScoreManager {
    
    private static Map<String, Double[]> result_table;
    
    /*
     * Calculate concrete event score
     */
    public static void calculate_score(Athlete atl) {
        for(Params param : Params.values())  {
            String event_name = param.name();
            if (param.units() == "s") {
                double event_score = calculate_time_event(event_name, atl.get_points().get(event_name));
                atl.add_score(event_name, event_score);
            } else if (param.units() == "m") {
                double event_score = calculate_dist_event(event_name, atl.get_points().get(event_name));
                atl.add_score(event_name, event_score);
            }            
        }
    }
    
    public static void compare_scores(List<Athlete> atls) {
        Collections.sort(atls);
    }
    
    /*
     * Compare scores be events
     * Score is not assigned to Athletes
     * Need to perform manual score comparison by score
     */
    public static void compare_scores_by_event(List<Athlete> atls) {
        result_table = new HashMap<>();
        for (Params param: Params.values()) {
            List<Double> temp_list = new ArrayList<Double>();
            String event = param.name();
            for (Athlete atl: atls) {
                temp_list.add(atl.get_score_by_name(event));
            }
            Double[] temp_array = temp_list.toArray(new Double[temp_list.size()]);
            // Sorts scores !!!
            Arrays.sort(temp_array, Collections.reverseOrder());
            result_table.put(event, temp_array);
        }
    }
    
    /*
     * Find event place by score
     */
    public static Integer[] get_event_place(String event, Double score) {
        List<Integer> temp_list = new ArrayList<>();
        Double[] temp_array = result_table.get(event);
        for (int i = 0; i < temp_array.length; i++) {
            if (temp_array[i] == score) {
                // i + 1 Athlete cannot have 0 place 
                temp_list.add(i + 1); 
            }
        }
        return temp_list.toArray(new Integer[temp_list.size()]);
    }
    
    /*
     * Count athletes total score
     */
    public static double total_score(Athlete atl) {
        double total_score = 0.0;
        for (Double score : atl.get_scores()) {
            total_score += score;
        }
        return total_score;
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