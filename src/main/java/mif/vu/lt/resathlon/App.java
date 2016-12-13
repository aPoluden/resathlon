package mif.vu.lt.resathlon;

import java.util.ArrayList;
import java.util.List;

import mif.vu.lt.resathlon.factory.AthleteFactory;
import mif.vu.lt.resathlon.managers.FileManager;
import mif.vu.lt.resathlon.managers.ScoreManager;
import mif.vu.lt.resathlon.managers.XmlManager;
import mif.vu.lt.resathlon.models.athletes.Athlete;
import mif.vu.lt.resathlon.utils.Options;

/**
 * @author apoluden
 *
 */
public class App {
    
    public static void main(String[] args) throws Exception {
        
        List<Athlete> athletes = new ArrayList<Athlete>();
        AthleteFactory f = null;
        
        Options opt = new Options(); 
        opt.init_opt(args);
        
        // Call 911 :) 
        if (opt.hasFlag(Options.CMD.HELP.flag()) || args.length == 0) {
            Options.printHelp();
        }
        
        // check data input filse provided
        if (opt.hasFlag(Options.CMD.FILE.flag()) && ((opt.get_params(Options.CMD.FILE.flag()).length != 0))) {
        	
            FileManager fm = new FileManager();
            fm.process_files(opt.get_params(Options.CMD.FILE.flag()));
            
            // check sport name provided
        	if (opt.hasFlag(Options.CMD.SPORT.flag()) && ((opt.get_params(Options.CMD.SPORT.flag()).length != 0))) {
        		String sport = opt.get_params(Options.CMD.SPORT.flag())[0];
                f = new AthleteFactory(sport);
        	} else { 
        		// TODO notify to provide input sports !!!
        		System.out.println("Sport type not provided");
        	}                        
            for (String data : fm.getDataSet()) {
                Athlete athlete = f.createAthlete(data);
                ScoreManager.calculateScores(athlete);
                athletes.add(athlete);
            }
            ScoreManager.countEventPlace(athletes);
            ScoreManager.orderAthletes(athletes);
        } else {
        	// TODO notify to provide input
            System.out.println("Input file not provided");
        }
        
        // TODO File format make more generic
        if (opt.hasFlag(Options.CMD.EXTENSION.flag())) {
            // TODO check OUTPUT 
            String[] opts = opt.get_params(Options.CMD.EXTENSION.flag());
            for (String value : opts) {
                switch (value.toLowerCase()) {
                    // TODO hardcoded xml
                    case "xml":
                    	XmlManager xm = new XmlManager(); // Refactor it's horible!!! support multiple output formats
                        for (Athlete atl: athletes) {
                            xm.createElement(atl);
                        }
                        xm.write_xml();
                        break;
                    default:
                        break;
                }
            }
        } else {
            // TODO output to file default extension - xml
        }
    }
}
