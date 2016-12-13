package mif.vu.lt.resathlon.models.athletes;

import java.util.HashSet;
import java.util.Set;

import mif.vu.lt.resathlon.models.Event;
import mif.vu.lt.resathlon.models.Params;

public class Decathlete extends Athlete {
    
    /*
     * Constructor
     */
    public Decathlete(String[] dataset) {    	
    	super(dataset);
    	NAME_SURNAME = dataset[0];
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
   
    @Override
    public Set<Event> createEvents(String[] dataset) {
        String[] only_rslt = new String[10];
        Set<Event> event_set = new HashSet<Event>();
        // Removing name_surname from dataset
        System.arraycopy(dataset, 1, only_rslt, 0, dataset.length - 1);
    	Params[] params = Params.values();
        for (int i = 0; i < params.length; i++) {
            if (i == (params.length - 1)) {
                // Last rslt array element pattern 00.00.00 need to converted into 00.00000
                event_set.add(new Event(params[i].name(), convert_1500_rslt(only_rslt[i])));
                break;
            }
            event_set.add(new Event(params[i].name(), Double.parseDouble(only_rslt[i])));
        }
    	return event_set;
    }
}
