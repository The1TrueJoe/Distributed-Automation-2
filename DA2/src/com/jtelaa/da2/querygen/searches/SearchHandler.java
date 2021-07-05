package com.jtelaa.da2.querygen.searches;

import java.util.Random;
import java.util.ArrayList;

import com.jtelaa.da2.bot.plugin.bw.util.SearchSystem;
import com.jtelaa.da2.lib.files.Files;
import com.jtelaa.da2.querygen.util.Query;

// TODO comment

public class SearchHandler {

    public static void main(String[] args) {
        while (true) {
            System.out.println(SearchSystem.BING_URL + getRandomSearch());
        }
    }


    public synchronized static Query getRandomSearch() { 
        Random rand = new Random();
        Query[] searches = loadSearches(100);
        return searches[rand.nextInt(searches.length-1)]; 
    }
    
    public synchronized static Query[] loadSearches(int count) {
        Query[] searches = new Query[count];
        ArrayList<String> search_list = pickList();
        Random rand = new Random();

        for (int i = 0; i < searches.length; i++) {
            searches[i] = new Query(search_list.get(rand.nextInt(search_list.size()-1)));
        
        }

        return searches;
    }

    private synchronized static ArrayList<String> pickList() {
        Random rand = new Random();

        final String PATH = "com/jtelaa/da2/querygen/searches/searchdata/";
        String name;

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

        return Files.listLinesInternalFile(PATH + name);
    }
    
}
