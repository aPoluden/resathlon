package mif.vu.lt.resathlon;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import mif.vu.lt.resathlon.managers.FileManager;
import mif.vu.lt.resathlon.managers.ScoreManager;
import mif.vu.lt.resathlon.managers.XmlManager;
import mif.vu.lt.resathlon.models.Athlete;
import mif.vu.lt.resathlon.utils.AthleteFactory;
import mif.vu.lt.resathlon.utils.Options;
import mif.vu.lt.resathlon.utils.Parser;

/**
 * @author apoluden
 *
 */
public class App {
    
    public static void main(String[] args) throws Exception {
        
        List<Athlete> athletes = new ArrayList<Athlete>();
        
        Options opt = new Options(); 
        opt.init_opt(args);
        
        // Call help 911
        if (opt.hasFlag(Options.CMD.HELP.flag()) || args.length == 0) {
            Options.printHelp();
        }
        
        XmlManager xm = new XmlManager(); // Refactor it's horible!!!
        // TODO support more sport events
        // Check if Options has file flag
        if (opt.hasFlag(Options.CMD.FILE.flag())) {
            FileManager fm = new FileManager();
            fm.process_files(opt.get_params(Options.CMD.FILE.flag()));
            Parser parser = new Parser(new AthleteFactory());
            for (String data : fm.get_data()) {
                Athlete athlete = parser.parse(data);
                ScoreManager.calculate_score(athlete);
                athlete.set_total_score(ScoreManager.total_score(athlete));
                athletes.add(athlete);   
            }
            ScoreManager.compare_scores_by_event(athletes);
            ScoreManager.compare_scores(athletes);
        } else { 
            throw new FileNotFoundException("Path not provided");
        }
        
        // TODO File format make more generic
        if (opt.hasFlag(Options.CMD.EXTENSION.flag())) {
            // TODO check OUTPUT 
            String[] opts = opt.get_params(Options.CMD.EXTENSION.flag());
            for (String value : opts) {
                switch (value.toLowerCase()) {
                    // TODO hardcoded xml
                    case "xml":
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
