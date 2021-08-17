package com.jtelaa.bwbot.querygen.searches;

import java.util.Random;
import java.util.ArrayList;

import com.jtelaa.bwbot.bwlib.Query;
import com.jtelaa.da2.lib.files.FileUtil;

/**
 * Random search query generator
 * 
 * @since 2
 * @author Joseph
 * 
 * @see com.jtelaa.bwbot.bwlib.Query
 * @see com.jtelaa.bwbot.querygen.processes.QueryGenerator
 */

public class SearchHandler {

    /**
     * Test that prints out searches
     * 
     * @param args Arguments
     */ 
    public static void main(String[] args) {
        while (true) {
            System.out.println(Query.BING_URL + getRandomSearch());

        }
    }

    /**
     * Generates random searches
     * 
     * @return new Query
     */

    public synchronized static Query getRandomSearch() { 
        Random rand = new Random();

        // Generate an array and pick at a random index
        Query[] searches = getRandomSearches(100);
        return searches[rand.nextInt(searches.length-1)]; 

    }

    /**
     * Generates an array of random searches
     * 
     * @param count Size of the array
     * 
     * @return Array of random searches
     */
    
    public synchronized static Query[] getRandomSearches(int count) {
        Random rand = new Random();

        // Setup lists
        Query[] searches = new Query[count];
        ArrayList<String> search_list = pickList();

        // Populate lists
        for (int i = 0; i < searches.length; i++) {
            searches[i] = new Query(search_list.get(rand.nextInt(search_list.size()-1)));
        
        }

        // Return
        return searches;

    }

    /**
     * Pick a list of popular searches
     * 
     * @return List of popular searches
     */

    private synchronized static ArrayList<String> pickList() {
        Random rand = new Random();

        // Path of lists
        final String PATH = "com/jtelaa/da2/querygen/searches/searchdata/";
        String name;

        // Find random list
        switch (rand.nextInt(15)) {
            case 1:
                name = ("Searches2009.txt");
            case 2:
                name = ("Searches2011.txt");
            case 3:
                name = ("Searches2012.txt");
            case 4:
                name = ("Searches2013.txt");
            case 5:
                name = ("Searches2014.txt");
            case 6:
                name = ("Searches2015.txt");
            case 7:
                name = ("Searches2016.txt");
            case 8:
                name = ("Searches2017.txt");
            case 9:
                name = ("Searches2018.txt");
            case 10:
                name = ("Searches2019.txt");
            default:
                name = ("Searches2020.txt");

        }

        // Return list + path
        return FileUtil.listLinesInternalFile(PATH + name);

    }
    
}
