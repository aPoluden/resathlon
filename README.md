# resathlon
Command line tool calculate sports event scores (currently Decathlon sport implemented). Calculated results outputed to file (currently to xml).

### How to run
```sh
$ mvn package
$ java -cp target/jarname.jar mif.vu.lt.resathlon.App --f path/to/input/file --e xml --s dc
```
 Result file will be my_raw.xml
 
**Available cmd options:**
* --h - prints help
* --e - output file extension, available: xml
* --s - sport type, available: dc - for decathlon
* --f - input file path

**Decathlon input data:**
Decathlon input file locates in data/Decathlon_input.txt
### How to implement more sports: 
1. In ``` Option.class``` fill in SPORTS array
2. Create Athlete subclass and extend ```Athlete.class```, implement abstract methods
4. Create custom sub Parser and extend ```Parser.class```, implement abstract methods
5. Implement score calculations for custom sports in ```ScoreManager.class```
6. In ```AthleteFactory.class``` implement custom sport type check