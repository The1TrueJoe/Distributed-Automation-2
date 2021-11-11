package com.jtelaa.bwbot.querygen.searches;

import java.util.Random;
import java.util.ArrayList;

import com.jtelaa.bwbot.bwlib.Query;
import com.jtelaa.da2.lib.files.FileUtil;
import com.jtelaa.da2.lib.log.Log;

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

    /** Path of lists */
    // private static final String PATH =
    // "com/jtelaa/bwbot/querygen/searches/searchdata/";
    // TODO Make internal again

    /** Path of lists */
    private static final String PATH = "/QueryGen/sys/rsc/searches/";

    /** Logging prefix */
    private static String log_prefix = "Search Handler: ";

    /**
     * Test that prints out searches
     * 
     * @param args Arguments
     */
    public static void main(String[] args) {
        while (true) {
            //System.out.println(Query.BING_URL + getRandomSearch());
            System.out.println(mangle("testme"));

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
        return searches[rand.nextInt(searches.length - 1)];

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
        ArrayList<String> search_list = pickList(); // all lines of one file

        // Populate lists
        for (int i = 0; i < searches.length; i++) {
            // Get query
            String query_string = search_list.get(rand.nextInt(search_list.size() - 1)).toLowerCase();
            
            // Mangle
            if (rand.nextInt(100) == 1) { query_string = mangle(query_string); }
            
            // Add search
            searches[i] = new Query(query_string);

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
        // Get list
        Random rand = new Random();
        String name = pickList(rand.nextInt(11));

        // Read file
        // ArrayList<String> lines = FileUtil.listLinesInternalFile(name);
        ArrayList<String> lines = FileUtil.listLinesFile(name);

        // Show file is empty
        if (lines.size() < 1) {

            int i = -1;

            do {
                Log.sendMessage(log_prefix + "File " + name + " is empty");

                i++;
                // lines = FileUtil.listLinesInternalFile(pickList(i));
                lines = FileUtil.listLinesFile(pickList(i));

                if (i > 20) {
                    Log.sendMessage(log_prefix + " All files empty (Will use list of default)");
                    lines = ManualSearches.searches();

                }

            } while (lines.size() < 1);

        }

        // Return list + path
        return lines;

    }

    public synchronized static String pickList(int num) {
        String name;

        // Find random list
        switch (num) {
        case 1:
            name = ("Searches2009.txt");
            break;
        case 2:
            name = ("Searches2011.txt");
            break;
        case 3:
            name = ("Searches2012.txt");
            break;
        case 4:
            name = ("Searches2013.txt");
            break;
        case 5:
            name = ("Searches2014.txt");
            break;
        case 6:
            name = ("Searches2015.txt");
            break;
        case 7:
            name = ("Searches2016.txt");
            break;
        case 8:
            name = ("Searches2017.txt");
            break;
        case 9:
            name = ("Searches2018.txt");
            break;
        case 10:
            name = ("Searches2019.txt");
            break;
        default:
            name = ("Searches2020.txt");
            break;

        }

        return PATH + name;

    }

    /**
     * Mangle the search query
     * 
     * @param query query to mangle
     * 
     * @return mangled query
     */

    public static String mangle(String query) {
        Random random = new Random(42);
        int num = random.nextInt(4);

        switch (num) {
            case 0:
                query = query.substring(1);
                break;
            case 1:
                query = query.substring(0, query.length() - 2);
                break;
            case 3:
                query = query.toUpperCase();
                break;
            case 4:
                String word = "";

                for (int i = 0; i < query.length(); i++) {
                    if (i % 2 == 0) {
                        word = word + query.substring(i, i + 1).toUpperCase();
                    } else {
                        word = word + query.substring(i, i + 1).toLowerCase();
                    }
                }

                query = word;
                break;

        }

        return query;

    }

}
