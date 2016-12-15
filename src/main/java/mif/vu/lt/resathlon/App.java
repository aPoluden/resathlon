package mif.vu.lt.resathlon;

import java.io.IOException;
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
        
        try {
            Options opt = new Options(); 
            opt.init_opt(args);
            
            // Call 911 :) 
            if (opt.hasFlag(Options.CMD.HELP.flag()) || args.length == 0) {
                Options.printHelp();
                return;
            }
            
            // check data input filse provided
            if (opt.hasFlag(Options.CMD.FILE.flag()) && ((opt.get_params(Options.CMD.FILE.flag()).length != 0))) {
            	
                FileManager fm = new FileManager();
                fm.process_files(opt.get_params(Options.CMD.FILE.flag()));
                
                // check sport name provided
            	if (opt.hasFlag(Options.CMD.SPORT.flag()) && ((opt.get_params(Options.CMD.SPORT.flag()).length != 0))) {
            		// index 0 always should point to first array element
            		// TODO remove SPORT indexes
            		String sport = opt.get_params(Options.CMD.SPORT.flag())[0];
                    f = new AthleteFactory(sport);
            	} else {
            		throw new IOException("Sports attribute required");
            	}                        
                for (String data : fm.getDataSet()) {
                    Athlete athlete = f.createAthlete(data);
                    ScoreManager.calculateAthleteScores(athlete);
                    athletes.add(athlete);
                }
                ScoreManager.compareEventPlace(athletes);
                ScoreManager.orderAthletes(athletes);
            } else {
                throw new IOException("Input file required");
            }
            
            // TODO output file extension make more generic
            if (opt.hasFlag(Options.CMD.EXTENSION.flag()) && ((opt.get_params(Options.CMD.EXTENSION.flag()).length != 0))) {
                String extension = opt.get_params(Options.CMD.EXTENSION.flag())[0];
                if (extension.equals(Options.EXTENSION.XML.extensionName())) { 
                	XmlManager xm = new XmlManager(); // Refactor it's horible!!! support multiple output formats
                    for (Athlete atl: athletes) {
                        xm.createElement(atl);
                    }
                    xm.write_xml();
                }
            } else {
        		throw new IOException("Output file extension required");
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }
    }
}
