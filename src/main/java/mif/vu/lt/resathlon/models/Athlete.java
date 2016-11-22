package mif.vu.lt.resathlon.models;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Athlete implements Comparable<Athlete> {
    
    private String NAME_SURNAME;
    private Map<String, Double> points;
    private Map<String, Double> scores;
    private double total_score;

    /*
     * Constructor
     */
    public Athlete(String[] rslt) {
        points = new HashMap<String, Double>();
        scores = new HashMap<String, Double>();
        this.NAME_SURNAME = rslt[0];
        String[] only_rlst = new String[10];
        // Remove name_surname from results
        System.arraycopy(rslt, 1, only_rlst, 0, rslt.length - 1);
        group_points(only_rlst);
    }
    
    /*
     * Get scores by name
     */
    public Double get_score_by_name(String name) {
        return scores.get(name);
    }
    
    /*
     * Get point by name
     */
    public Double get_points_by_name(String name) {
        return points.get(name);
    }
    
    /*
     * Set athletes total score
     */
    public void set_total_score(double t_scr) {
        this.total_score = t_scr;
    }
    
    /*
     * Get athletes total score
     */
    public double get_total_score() {
        return this.total_score;
    }
    
    /*
     * returns current instance results
     */
    public Map<String, Double> get_points() {
        return points;
    }
    
    /*
     * Add score to score list
     */
    public void add_score(String key, Double value) {
        scores.put(key, value);
    }
    
    /*
     * Get score list
     */
    public Collection<Double> get_scores() { 
        return scores.values();
    }
    
    public Set<String> get_events() {
        return points.keySet();
    }
    
    /*
     * Get athelete name_surname
     */
    public String get_name_surname() {
        return this.NAME_SURNAME;
    }
    
    /*
     * Groups Athlete results into key_value obj
     */
    private void group_points(String[] rslt) {
        Params[] params = Params.values();
        for (int i = 0; i < params.length; i++) {
            if (i == (params.length - 1)) {
                // Last rslt array element pattern 00.00.00 need to converted into 00.00000
                points.put(params[i].name(), convert_1500_rslt(rslt[i]));
                break;
            }
            points.put(params[i].name(), Double.parseDouble(rslt[i]));
        }
    }
    
    /*
     * Converts 1500 m. results from 00.00.00 to 00.00000 
     * This lets interpret result as double value
     */
    private Double convert_1500_rslt(String rslt) {
        double ms_in_s = 0.001;
        String[] temp = rslt.split("\\.");
        String min = temp[0] + ".";
        String sec_ms = String.valueOf((int) (Double.parseDouble(temp[1] + "." + temp[2]) / ms_in_s)); 
        return Double.valueOf(min + sec_ms);
    }
    
    /*
     * (non-Javadoc)
     * Compares Athletes objects via total_score
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     * 
     */
    @Override
    public int compareTo(Athlete atl) {
        if (this.total_score > atl.total_score) {
            // Compared athlete score less then comparable athlete
            return -1;
        } else if (this.total_score < atl.total_score) {
            // Compared athlete score greater than comparable athlete
            return 1;
        }
        // Scores are equals
        return 0;
    }
}
