package mif.vu.lt.resathlon.factory;

import mif.vu.lt.resathlon.models.athletes.Athlete;
import mif.vu.lt.resathlon.utils.DecathleteParser;
import mif.vu.lt.resathlon.utils.Options;
import mif.vu.lt.resathlon.utils.Parser;

public class AthleteFactory {
	
	Parser parser;
	
	public AthleteFactory(String type) {
		// Implement other SPORT types
    	if (type.equals(Options.SPORT.DECATHLETE.sport())) {
    		parser = new DecathleteParser();
    	}
    }

	/*
     * Creates and retuns new athlete instance
     * By default all athletes won't have gender
     */
    public Athlete createAthlete(String data) throws Exception {
    	// Runner, Jumper, Swimmer or even Chessplayer ?
        return parser.parse(data);
    }
}
