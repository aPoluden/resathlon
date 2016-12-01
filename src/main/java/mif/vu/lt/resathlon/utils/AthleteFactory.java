package mif.vu.lt.resathlon.utils;

import mif.vu.lt.resathlon.models.Athlete;

public class AthleteFactory implements Factory {
    /*
     * Creates and retuns new athlete instance
     * By default all athletes won't have gender
     */
    public Athlete create_athlete(String[] data) {
        return new Athlete(data);
    }
}
