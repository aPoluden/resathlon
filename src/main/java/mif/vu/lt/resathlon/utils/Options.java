package mif.vu.lt.resathlon.utils;

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
    
    private Map<String, HashSet<String>> options = new HashMap<String, HashSet<String>>();
    private String CMD[] = null; // command - line params
    
    public Options(String[] args) {
        CMD = args;
        init_opt();
    }
    
    /*
     * Splits command-line params into flags and params
     */
    private void init_opt() {
        String flag = null;
        for (String param: CMD) {
            if (param.matches("--([a-z])")) {
                flag = param;
                if (!options.containsKey(param)) {
                    options.put(param, new HashSet<String>());
                }
            } else if (flag != null) {
                options.get(flag).add(param);
            }
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
    public String[] get_options(String flag) {
        return options.get(flag).toArray(new String[options.get(flag).size()]);
    }
}
