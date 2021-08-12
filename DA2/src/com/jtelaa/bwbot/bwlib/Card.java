package com.jtelaa.bwbot.bwlib;

import java.util.ArrayList;
import java.util.Random;

/**
 * ENUM That stores card IDs
 * 
 * @since 1
 * @author Joseph
 */

public enum Card {

    /* Supports Mass-Redepmtions */

    AMAZON5("000800000000", 5),
    AMAZON10("000800000064", 10),
    AMAZON(new Card[] { AMAZON5, AMAZON10 }),

    WALMART5("000800000020", 5),
    WALMART10("000800000100", 10),
    WALMART25("000800000102", 25),
    WALMART(new Card[] { WALMART5, WALMART10, WALMART25 }),

    TARGET5("000800000018", 5),
    TARGET10("000800000096", 10),
    TARGET25("000800000098", 25),
    TARGET(new Card[] { TARGET5, TARGET10, TARGET25 }),

    /* Single Redemption */

    ROBLOX1("000400000343", "ROBUX", 100),
    ROBLOX2("000400000344", "ROBUX", 200),
    ROBLOX8("000400000346", "ROBUX", 800),
    ROBLOX10("000400000347", "ROBUX", 1000),
    ROBLOX(new Card[] { ROBLOX1, ROBLOX2, ROBLOX8, ROBLOX10 }),

    DOMINOES5("000800000013", 5),
    DOMINOES10("000800000082", 10),
    DOMINOES25("000800000084", 25),
    DOMINOES(new Card[] { DOMINOES5, DOMINOES10, DOMINOES25 }),

    DUNKIN5("000800000057", 5),
    DUNKIN10("000800000059", 10),
    DUNKIN25("000800000068", 25),
    DUNKIN(new Card[] { DUNKIN5, DUNKIN10, DUNKIN25 }),
    
    TACOBELL5("000800000110", 5),
    TACOBELL10("000800000111", 10),
    TACOBELL25("000800000112", 25),
    TACOBELL(new Card[] { TACOBELL5, TACOBELL10, TACOBELL25 }),

    STARBUCKS5("000800000001", 5),
    STARBUCKS10("000800000002", 10),
    STARBUCKS25("000800000070", 25),
    STARBUCKS(new Card[] { STARBUCKS5, STARBUCKS10, STARBUCKS25 }),

    BURGERKING5("000800000010", 5),
    BURGERKING10("000800000074", 10),
    BURGERKING25("000800000076", 25),
    BURGERKING(new Card[] { BURGERKING5, BURGERKING10, BURGERKING25 }),

    UBEREATS10("000800000117", "EAT", 10),
    UBEREATS25("000800000118", "EAT", 25),
    UBEREATS(new Card[] { UBEREATS10, UBEREATS25 }),

    DOORDASH15("000800000115", "EAT", 15),
    DOORDASH25("000800000116", "EAT", 25),
    DOORDASH(new Card[] { DOORDASH15, DOORDASH25 }),

    GRUBHUB10("000800000113", "EAT", 10),
    GRUBHUB25("000800000114", "EAT", 25),
    GRUBHUB(new Card[] { GRUBHUB10, GRUBHUB25 }),
    
    REI5("000800000016", 5),
    REI10("000800000086", 10),
    REI25("000800000088", 25),
    REI(new Card[] { REI5, REI10, REI25 }),

    HULU25("000800000046", "HULU", 25),
    HULU(Card.HULU25),

    FIRST1("000500000016", "DONO", 1),
    FIRST3("000500000018", "DONO", 3),
    FIRST5("000500000019", "DONO", 5),
    FIRST(new Card[] { FIRST1, FIRST3, FIRST5 }),

    CODEORG1("000500000017", "DONO", 1),
    CODEORG3("000500000020", "DONO", 3),
    CODEORG5("000500000021", "DONO", 5),
    CODEORG(new Card[] { CODEORG1, CODEORG3, CODEORG3 }),

    WIKIPEDIA1("000500001187", "DONO", 1),
    WIKIPEDIA3("000500001188", "DONO", 3),
    WIKIPEDIA5("000500001189", "DONO", 5),
    WIKIPEDIA(new Card[] { WIKIPEDIA1, WIKIPEDIA3, WIKIPEDIA5 }),

    WWF1("000500000165", "DONO", 1),
    WWF3("000500000166", "DONO", 3),
    WWF5("000500000167", "DONO", 5),
    WWF(new Card[] { WWF1, WWF3, WWF5 }),

    UNICEF1("000500000310", "DONO", 1),
    UNICEF3("000500000311", "DONO", 3),
    UNICEF5("000500000312", "DONO", 5),
    UNICEF(new Card[] { UNICEF1, UNICEF3, UNICEF5 }),

    GAMERSOUT1("000500000310", "DONO", 1),
    GAMERSOUT3("000500000311", "DONO", 3),
    GAMERSOUT5("000500000312", "DONO", 5),
    GAMERSOUT(new Card[] { GAMERSOUT1, GAMERSOUT3, GAMERSOUT5 }),

    REDCROSS1("000500001285", "DONO", 1),
    REDCROSS3("000500001286", "DONO", 3),
    REDCROSS5("000500001287", "DONO", 5),
    REDCROSS(new Card[] { REDCROSS1, REDCROSS3, REDCROSS5 }),
    
    ;

    /** Rewards redepmtion address base */
    public static final String REWARDS_ADDRESS = "https://rewards.microsoft.com/";
    /** Rewards card redemption address (Add product ID at the end to visit) */
    public static final String REWARDS_CARD_ADDRESS = REWARDS_ADDRESS + "redeem/";
    /** Redeption checkout address (Add product id at the end to open redmeption page) */
    public static final String REDEMPTION_CHECKOUT_ADDRESS = REWARDS_CARD_ADDRESS + "checkout?productId=";

    /** */
    private final char[] url;
    /** */
    private final char[] unit;
    /** */
    private final int value;
    /** */
    private final int point_cost;

    /**
     * 
     * @param cards
     */

    Card(Card[] cards) {
        Card card = randomCard(cards);

        url = card.getTag().toCharArray();
        unit = card.getUnit().toCharArray();
        value = card.getValue();
        point_cost = card.getCost();

    }

    /**
     * 
     * @param card
     */

    Card(Card card) {
        url = card.getTag().toCharArray();
        unit = card.getUnit().toCharArray();
        value = card.getValue();
        point_cost = card.getCost();

    }

    /**
     * 
     * @param url
     */

    Card(String url) {
        this.url = url.toCharArray();
        unit = "USD".toCharArray();
        value = 5;
        point_cost = calculatePointCost(new String(unit), value);

    }

    /**
     * 
     * @param url
     * @param value
     */

    Card(String url, int value) {
        this.url = url.toCharArray();
        unit = "USD".toCharArray();
        this.value = value;
        point_cost = calculatePointCost(new String(unit), value);

    }

    /**
     * 
     * @param url
     * @param unit
     */

    Card(String url, String unit) {
        this.url = url.toCharArray();
        this.unit = unit.toCharArray();
        value = 5;
        point_cost = calculatePointCost(new String(unit), value);

    }

    /**
     * 
     * @param url
     * @param unit
     * @param value
     */

    Card(String url, String unit, int value) {
        this.url = url.toCharArray();
        this.unit = unit.toCharArray();
        this.value = value;
        point_cost = calculatePointCost(new String(unit), value);

    }

    /**
     * Calculates the point cost given the value
     * 
     * @param unit Unit of the card (Ex. USD or ROBUX)
     * @param value Value of the card in the unit
     * 
     * @return Overall point value
     */

    public static int calculatePointCost(String unit, int value) {
        switch (unit) {
            case "USD": value *= 1050; break;
            case "EAT": value *= 1300; break;
            case "ROBUX": value *= 15; break;
            case "HULU": value *= 1120; break;
            case "DONO": value *= 1000; break;
        }

        return value;

    }
    
    /**
     * Picks random card
     * 
     * @param cards Cards to pick from
     * 
     * @return random card
     */

    public static Card randomCard(Card[] cards) {
        Random rand = new Random();

        return cards[rand.nextInt(cards.length-1)];
    }

    /**
     * Returns a specific card
     * 
     * @param name Name of the card
     * 
     * @return The Card
     */

    public static Card getCard(String name) {
        // Look for a card with the name given
        for (Card e : values()) {
            if (e.toString().equalsIgnoreCase(name)) {
                return e;
            }
        }

        // Looks for the first card that is similar
        for (Card e : values()) {
            if (e.toString().toLowerCase().contains(name.toLowerCase())) {
                return e;
            }
        }

        // Returns the standard amazon card if neither above apply
        return AMAZON;
    }

    /**
     * 
     */

    public static Card getRandomCardWithType(String type) {
        return randomCard(getCardsByType(type));

    }

    /**
     * Gives all available cards with a certain unit
     * 
     * @param type Desired type (Eg. Amazon)
     * 
     * @return array of cards
     */

    public static Card[] getCardsByType(String type) {
        ArrayList<Card> cards = new ArrayList<Card>();

        // Iterate through all cards
        for (Card e : values()) {
            if (type.toLowerCase().contains(e.toString().toLowerCase())) {
                cards.add(e);

            }
        }

        return (Card[]) cards.toArray();
    }

    /**
     * Gives all available cards with a certain unit
     * 
     * @param price Desired price
     * 
     * @return array of cards
     */

    public static Card[] getCardsByUnit(String unit) {
        ArrayList<Card> cards = new ArrayList<Card>();

        // Iterate through all cards
        for (Card e : values()) {
            if (e.getUnit().equalsIgnoreCase(unit)) {
                cards.add(e);

            }
        }

        return (Card[]) cards.toArray();
    }

    /**
     * Gives all available cards at a certain value
     * 
     * @param price Desired price
     * 
     * @return array of cards
     */

    public static Card[] getCardsByPrice(int price) {
        ArrayList<Card> cards = new ArrayList<Card>();

        // Iterate through all cards
        for (Card e : values()) {
            if (e.getValue() == price) {
                cards.add(e);

            }
        }

        return (Card[]) cards.toArray();
    }

    /**
     * Gives all available cards at a certain value
     * 
     * @param point_cost Desired point cost
     * 
     * @return array of cards
     */

    public static Card[] getCardsByPointCost(int point_cost) {
        ArrayList<Card> cards = new ArrayList<Card>();

        // Iterate through all cards
        for (Card e : values()) {
            if (e.getCost() == point_cost) {
                cards.add(e);

            }
        }

        return (Card[]) cards.toArray();
    }

    /**
     * Gives all available cards under a certain value
     * 
     * @param price Desired price max
     * 
     * @return array of cards
     */

    public static Card[] getCardsUnderPrice(int price) {
        ArrayList<Card> cards = new ArrayList<Card>();

        // Iterate through all cards
        for (Card e : values()) {
            if (e.getValue() <= price) {
                cards.add(e);

            }
        }

        return (Card[]) cards.toArray();
    }

    /**
     * Gives all available cards under a certain value
     * 
     * @param point_cost Desired point cost max
     * 
     * @return array of cards
     */

    public static Card[] getCardsUnderPointCost(int point_cost) {
        ArrayList<Card> cards = new ArrayList<Card>();

        // Iterate through all cards
        for (Card e : values()) {
            if (e.getCost() <= point_cost) {
                cards.add(e);

            }
        }

        return (Card[]) cards.toArray();
    }

    /** */
    public Card[] getAll() { return values(); }

    /** */
    public String getTag() { return new String(url); }
    /** */
    public String getUnit() { return new String(unit); }
    /** */
    public int getValue() { return value; }
    /** */
    public int getCost() { return point_cost; }
    
}
