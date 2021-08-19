package com.jtelaa.bwbot.querygen.searches;

import java.util.ArrayList;

/**
 * List of manual searches
 * 
 * Can still be used but has less randomness (May be deprecated in future)
 * 
 * @since 1
 * @author Joseph
 */

public class ManualSearches {

    /**
     * Manual searches
     * 
     * @return searches as an arraylist
     */

    public synchronized static ArrayList<String> searches() {
        ArrayList<String> searches = new ArrayList<String>();

        for (String search : searches) {
            searches.add(search);

        }

        return searches;
        
    }

    /**
     * Manual searches
     */

    public volatile static String[] searches = {
        "youtube", "facebook", "twitter", "instagram",
        "snapchat", "wikipedia", "tumblr", "amazon", 
        "gmail", "outlook", "hotmail", "aol", 
        "temp email", "google", "bing", "duckduckgo",
        "yahoo", "yahoo email", "weather", "netflix",
        "hulu", "disney", "hbo max", "adult swim",
        "abc", "nbc", "cbs", "paramount", "msn",
        "tbs", "espn", "crunchyroll", "walmart",
        "ebay", "offerup", "wikipedia", "lowes",
        "home depot", "tiktok", "menards", "ace+hardware",
        "usps", "ups", "fedex", "package+tracking",
        "craigslist", "fox", "cnn", "google+docs",
        "google+drive", "google+translate", "google+slides",
        "google+sheets", "news", "this+day+in+history",
        "google+classroom", "minecraft", "fortnite", "tetris",
        "roblox", "steam", "terraria", "calculator", "periodic+table", 
        "webmd", "paypal", "speedtest", "whatismyip", "target",
        "aero", "f21", "rue21", "pink", "hollister", "gap",
        "old+navy", "zillow", "best buy", "bank+of+america",
        "td+bank", "wells+fargo", "robinhood", "maps", "discord",
        "twitch", "trump", "obama", "biden", "obama+last+name",
        "memes", "dank+memes", "pewdiepie", "markiplier",
        "jacksepticeye", "belle+delphine", "f1nnster",
        "sykunno", "corpse+husband", "josh", "costco",
        "indeed", "linkedin", "23andme", "cvs", "ancestry",
        "pintrest", "coronavirus", "masks", "etsy",
        "stonks", "stocks", "dow", "nasdaq", "chase",
        "capital+one", "batman", "dominoes", "spotify",
        "wayfair", "you", "me", "walgreens", "bitcoin",
        "dogecoin", "google+scholar", "trivago", "vrbo",
        "vsco", "macys", "kohls", "belk", "nike",
        "under armour", "turbotax", "xfinity", "timer",
        "pandora", "zoom", "verizon", "spectrum"


    };

}
