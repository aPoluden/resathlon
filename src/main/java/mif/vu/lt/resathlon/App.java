package mif.vu.lt.resathlon;

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
        XmlManager xm = new XmlManager();
        Options opt = new Options(args);
        // TODO support more sport events
        // Check if Options has file flag
        if (opt.hasFlag("--f")) {
            FileManager fm = new FileManager();
            fm.process_files(opt.get_options("--f"));
            Parser parser = new Parser(new AthleteFactory());
            for (String data : fm.get_data()) {
                Athlete athlete = parser.parse(data);
                ScoreManager.calculate_score(athlete);
                athlete.set_total_score(ScoreManager.total_score(athlete));
                athletes.add(athlete);   
            }
            ScoreManager.compare_scores_by_event(athletes);
            ScoreManager.compare_scores(athletes);
        }
        
        // TODO File format make more generic
        if (opt.hasFlag("--e")) {
            // TODO check OUTPUT 
            String[] opts = opt.get_options("--e");
            for (String value : opts) {
                switch (value.toLowerCase()) {
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
        }
    }
}
