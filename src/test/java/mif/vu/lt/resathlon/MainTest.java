package mif.vu.lt.resathlon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import mif.vu.lt.resathlon.managers.ScoreManager;
import mif.vu.lt.resathlon.models.Event;
import mif.vu.lt.resathlon.models.athletes.Athlete;
import mif.vu.lt.resathlon.models.athletes.Decathlete;
import mif.vu.lt.resathlon.utils.Options;

import org.junit.Test;

public class MainTest {
	
	private String[] dataset = {"Siim Susi", "12.61", "5.00", "9.22", "1.50","60.39", "16.43", "21.60", "2.60", "35.81", "5.25.72"};
	
	// Options test
    @Test
    public void opt_test() {
        String PATH = "data/Decathlon_input.txt";
        // Good arguments
        String[] a0 = {"--f", PATH, "--e", "xml"};
        String[] a1 = {"--f", PATH};
        // Falsee arguments
        String[] b0 = {}; // No arguments specified
        String[] b1 = {"--", "--f", PATH};
        String[] b2 = {"--f"}; // ?
        String[] b3 = {"param"}; 
        String[] b4 = {"--e", "--f", PATH}; // This may fail !
//        String[] b5 = {"--e", "xml"}; // No path specified, not checked
        String[] b6 = {"param", "--e"};
        String[] b7 = {"--f", PATH, "--f"};
        String[] b8 = {"--f", "--e"};
        String[] b9 = {"--f", "xml"};
        
        /*
         * Good argument tests
         */
        Options opt = new Options();
        opt.init_opt(a0);
        assertTrue(opt.hasFlag("--f"));
        assertTrue(opt.hasFlag("--e"));
        assertEquals(PATH, opt.get_params("--f")[0]);
        assertEquals("xml", opt.get_params("--e")[0]);
        
        opt = new Options();
        opt.init_opt(a1);
        assertTrue(opt.hasFlag("--f"));
        assertEquals(PATH, opt.get_params("--f")[0]);
        
        /*
         * Uncorrect argument tests
         */
        opt = new Options();
        opt.init_opt(b0);
        assertFalse(opt.hasFlag("--f"));
        
        opt = new Options();
        opt.init_opt(b1);
        assertTrue(opt.hasFlag("--f"));
        assertEquals(PATH, opt.get_params("--f")[0]);
        assertFalse(opt.hasFlag("--"));
        
        opt = new Options();
        opt.init_opt(b2);
        assertTrue(opt.hasFlag("--f"));
        assertNotNull(opt.get_params("--f"));
        
        opt = new Options();
        opt.init_opt(b3);
        assertNull(opt.get_params("--f"));
        
        opt = new Options();
        opt.init_opt(b4);
        assertTrue(opt.hasFlag("--e"));
        assertTrue(opt.hasFlag("--f"));
        assertEquals(PATH, opt.get_params("--f")[0]);
        
        opt = new Options();
        opt.init_opt(b6);
        assertTrue(opt.hasFlag("--e"));
        assertFalse(opt.hasFlag("param"));
        
        opt = new Options();
        opt.init_opt(b7);
        assertTrue(opt.hasFlag("--f"));
        assertEquals(PATH, opt.get_params("--f")[0]);
        
        opt = new Options();
        opt.init_opt(b8);
        assertTrue(opt.hasFlag("--e"));
        assertTrue(opt.hasFlag("--f"));
        
        opt = new Options();
        opt.init_opt(b9);
        assertTrue(opt.hasFlag("--f"));
        assertEquals(0, opt.get_params("--f").length);
    }
    
	@Test
	public void model_test() {
		Athlete athlete = new Decathlete(dataset);
		assertEquals(10, athlete.getEvents().size());
		assertEquals("Siim Susi", athlete.getName());
	}
	
	// ScoreManager test
	@Test
	public void scoremngr_test() {
		Integer firstPlace = 1;
		Athlete athlete1 = new Decathlete(dataset);
		Athlete athlete2 = new Decathlete(dataset);
		
		List<Athlete> athletes = new ArrayList<Athlete>();
		athletes.add(athlete1);
		athletes.add(athlete2);
		
		assertEquals(0.0, athlete1.getTotalScore(), 0);
		for (Event event: athlete1.getEvents()) {
			// index is used in case of similar competition results
			assertNull(event.getPlaces());
		}
		
		ScoreManager.compareEventPlace(athletes);
		
		for (Event event: athlete1.getEvents()) {
			// index is used in case of similar competition results
			assertEquals(2, event.getPlaces().length);
			assertEquals(firstPlace, event.getPlaces()[0]);
		}
		
		ScoreManager.calculateAthleteScores(athlete1);
		ScoreManager.calculateAthleteScores(athlete2);
		ScoreManager.compareEventPlace(athletes);
		
		for (Event event: athlete1.getEvents()) {
			// index is used in case of similar competition results
			assertEquals(2, event.getPlaces().length);
		}
	}
}