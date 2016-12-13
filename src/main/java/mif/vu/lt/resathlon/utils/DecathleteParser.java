package mif.vu.lt.resathlon.utils;

import mif.vu.lt.resathlon.models.athletes.Athlete;
import mif.vu.lt.resathlon.models.athletes.Decathlete;


public class DecathleteParser extends Parser {
	
	public Athlete parse(String data) throws Exception  {
      // remove white space at the end of the line
      String record = data.replaceFirst("(\\s+)($)", "");
      String regex = "(.+)(\\s)(.+);"
              + "(\\d+).(\\d+);(\\d+).(\\d+);"
              + "(\\d+).(\\d+);(\\d+).(\\d+);"
              + "(\\d+).(\\d+);(\\d+).(\\d+);"
              + "(\\d+).(\\d+);(\\d+).(\\d+);"
              + "(\\d+).(\\d+);(\\d+).(\\d+).(\\d+)";
      
      if (record.matches(regex)) {
          String[] res = data.split(";");
          // TODO split name surname
          return new Decathlete(res);
      } else {
          throw new Exception("List structure not correct");
      }
	}
	
}
