package mif.vu.lt.resathlon.models;

import java.util.ArrayList;
import java.util.List;

public class ScoreTable { 
    
    // Sorted Athletes by total score
    List<Athlete> places;
    // by competition
    
    
    public ScoreTable() {
        places = new ArrayList<Athlete>();
    }
    
    /*
     * Set sorted places list
     */
    public void set_places_list(List<Athlete> places) {
        this.places = places;
    }
    
    /*
     * Get sorted places list
     */
    public List<Athlete> get_places_list() {
        return this.places;
    }
}
