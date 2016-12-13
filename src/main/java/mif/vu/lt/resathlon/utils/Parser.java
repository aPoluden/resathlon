package mif.vu.lt.resathlon.utils;

import mif.vu.lt.resathlon.models.athletes.Athlete;

public abstract class Parser {
    
    public abstract Athlete parse(String str) throws Exception;
    
    /*
     * Parses results and returns athlete instance
     */
//    public Athlete parse(String str) throws Exception {
//        // remove white space at the end of the line
//        String record = str.replaceFirst("(\\s+)($)", "");
//        String regex = "(.+)(\\s)(.+);"
//                + "(\\d+).(\\d+);(\\d+).(\\d+);"
//                + "(\\d+).(\\d+);(\\d+).(\\d+);"
//                + "(\\d+).(\\d+);(\\d+).(\\d+);"
//                + "(\\d+).(\\d+);(\\d+).(\\d+);"
//                + "(\\d+).(\\d+);(\\d+).(\\d+).(\\d+)";
//        
//        if (record.matches(regex)) {
//            String[] res = str.split(";");
//            // TODO split name surname            
//            return af.create_athlete(res);
//        } else {
//            throw new Exception("List structure not correct");
//        }
//    }
}