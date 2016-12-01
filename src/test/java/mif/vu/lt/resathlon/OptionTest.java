package mif.vu.lt.resathlon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import mif.vu.lt.resathlon.utils.Options;

import org.junit.Test;

public class OptionTest {

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
    
    
}