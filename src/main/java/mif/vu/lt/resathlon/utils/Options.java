package mif.vu.lt.resathlon.utils;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * 
 * @author artiom
 *
 * Options class is used for structure command-line params
 */
public class Options {
    
	// cmd options
    public enum CMD {
        FILE("--f", "Result file paths. Use space for path argument split"),
        HELP("--h", "Print help"),
        EXTENSION("--e", "Preferable output file extension. Available [XML]"),
        SPORT("--s", "dc-decathlon");
        
        private final String flag;
        private final String description;
        
        CMD(String flag, String description) {
            this.flag = flag;
            this.description = description;
        }
        
        public String flag() { return this.flag; }
        public String description() { return this.description; }
        public boolean contains_flag(String str) { return false;}
    }
    
    private Map<String, HashSet<String>> options = new HashMap<String, HashSet<String>>();
    private String FLAG_REGEX = "--([a-z])";
    // Fill in custom extension abbreviation
    private String[] EXTENSIONS = {"xml"};
    // Fill in custom sport abbreviation
    private String[] SPORTS = {"dc"};
    
    // sport options
    public enum SPORT {
    	DECATHLETE("dc");
    	
    	private final String sport;
    	
    	SPORT(String sport) {
    		this.sport = sport;
    	}
    	
    	public String sport() {return this.sport;}
    	
    }
    // Available extensions
    public enum EXTENSION {
    	XML("xml");
    	
    	private final String extensionName;
    	
    	EXTENSION(String extensionName) { 
    		this.extensionName = extensionName;
    	}
    	
    	public String extensionName() {return this.extensionName; }
    }
    /*
     * Splits command-line arguments into key value set
     */
    public void init_opt(String[] args) {
        String flag = null;
        for (String param: args) {
            if (param.matches(FLAG_REGEX) && validateFlag(param)) {
                flag = param;
                if (!options.containsKey(flag)) {
                    options.put(flag, new HashSet<String>());
                }
            } else if (flag != null && validateParam(flag, param)) {
                options.get(flag).add(param);
                flag = null;
            }
        } // for
    }
    
    /*
     * Validate if flag exists
     */
    private boolean validateFlag(String flag) {
        for (CMD value : CMD.values()) { 
            if (value.flag.equals(flag)) return true;
        }
        return false;
    }
    
    /*
     * validate if param exists
     */
    private boolean validateParam(String flag, String param) {
        if (CMD.EXTENSION.flag().equals(flag)) {
            return Arrays.asList(EXTENSIONS).contains(param);
        } else if (CMD.FILE.flag().equals(flag)) {
            File file = new File(param);
            return file.exists();
        } else if (CMD.SPORT.flag().equals(flag)) {
            return Arrays.asList(SPORTS).contains(param);
        } else if ((CMD.HELP.flag().equals(flag))) {
            return true;
        }
        return false;
    }
    
    /*
     * Print to console available CMD options
     */
    public static void printHelp() {
        for (CMD value : CMD.values()) {
            System.out.println(String.format("%s: %s", value.flag(), value.description() ));
        }
    }
    
    /*
     * Checks if options contains certain flag
     */
    public boolean hasFlag(String flg) {
        if (options.containsKey(flg)) return true;
        return false;
    }
    
    /*
     * Get params by flag
     */
    public String[] get_params(String flag) {
        try {
            return options.get(flag).toArray(new String[options.get(flag).size()]);
        } catch(NullPointerException e){
            return null;
        }
        
    }
}
