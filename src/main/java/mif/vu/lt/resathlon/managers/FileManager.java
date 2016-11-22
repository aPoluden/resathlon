package mif.vu.lt.resathlon.managers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class FileManager {
    
    // Hold extracted data from files
    private Set<String> data_set = null;
    
    public FileManager() {
        data_set = new HashSet<String>();
    }
    
    /*
     * Extracts lines from specified files
     * @param paths links to files
     */
    public void process_files(String[] paths) {
        for (String path : paths) {
            try (Scanner scn = new Scanner(new File(path))) {
                while (scn.hasNextLine()) {
                    data_set.add(scn.nextLine());
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } 
        }
    }
    
    /*
     * Return data_set instance
     * @return data_set
     */
    public Set<String> get_data() {
        return data_set;
    }
    
}