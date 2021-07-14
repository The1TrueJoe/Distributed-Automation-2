package com.jtelaa.da2.bw_manager.redemptions;

import java.util.ArrayList;
import java.util.Random;

/**
 * ENUM That stores card IDs
 * 
 * @since 1
 * @author Joseph
 */

 // TODO Comment

public enum CardType {

    /* Supports Mass-Redepmtions */

    AMAZON5("000800000000", 5),
    AMAZON10("000800000064", 10),
    AMAZON(new CardType[] { AMAZON5, AMAZON10 }),

    WALMART5("000800000020", 5),
    WALMART10("000800000100", 10),
    WALMART25("000800000102", 25),
    WALMART(new CardType[] { WALMART5, WALMART10, WALMART25 }),

    TARGET5("000800000018", 5),
    TARGET10("000800000096", 10),
    TARGET25("000800000098", 25),
    TARGET(new CardType[] { TARGET5, TARGET10, TARGET25 }),

    /* Single Redemption */

    ROBLOX1("000400000343", "ROBUX", 100),
    ROBLOX2("000400000344", "ROBUX", 200),
    ROBLOX8("000400000346", "ROBUX", 800),
    ROBLOX10("000400000347", "ROBUX", 1000),
    ROBLOX(new CardType[] { ROBLOX1, ROBLOX2, ROBLOX8, ROBLOX10 }),

    DOMINOES5("000800000013", 5),
    DOMINOES10("000800000082", 10),
    DOMINOES25("000800000084", 25),
    DOMINOES(new CardType[] { DOMINOES5, DOMINOES10, DOMINOES25 }),

    DUNKIN5("000800000057", 5),
    DUNKIN10("000800000059", 10),
    DUNKIN25("000800000068", 25),
    DUNKIN(new CardType[] { DUNKIN5, DUNKIN10, DUNKIN25 }),
    
    TACOBELL5("000800000110", 5),
    TACOBELL10("000800000111", 10),
    TACOBELL25("000800000112", 25),
    TACOBELL(new CardType[] { TACOBELL5, TACOBELL10, TACOBELL25 }),

    STARBUCKS5("000800000001", 5),
    STARBUCKS10("000800000002", 10),
    STARBUCKS25("000800000070", 25),
    STARBUCKS(new CardType[] { STARBUCKS5, STARBUCKS10, STARBUCKS25 }),

    BURGERKING5("000800000010", 5),
    BURGERKING10("000800000074", 10),
    BURGERKING25("000800000076", 25),
    BURGERKING(new CardType[] { BURGERKING5, BURGERKING10, BURGERKING25 }),

    UBEREATS10("000800000117", "EAT", 10),
    UBEREATS25("000800000118", "EAT", 25),
    UBEREATS(new CardType[] { UBEREATS10, UBEREATS25 }),

    DOORDASH15("000800000115", "EAT", 15),
    DOORDASH25("000800000116", "EAT", 25),
    DOORDASH(new CardType[] { DOORDASH15, DOORDASH25 }),

    GRUBHUB10("000800000113", "EAT", 10),
    GRUBHUB25("000800000114", "EAT", 25),
    GRUBHUB(new CardType[] { GRUBHUB10, GRUBHUB25 }),
    
    REI5("000800000016", 5),
    REI10("000800000086", 10),
    REI25("000800000088", 25),
    REI(new CardType[] { REI5, REI10, REI25 }),

    HULU25("000800000046", "HULU", 25),
    HULU(CardType.HULU25),

    FIRST1("000500000016", "DONO", 1),
    FIRST3("000500000018", "DONO", 3),
    FIRST5("000500000019", "DONO", 5),
    FIRST(new CardType[] { FIRST1, FIRST3, FIRST5 }),

    CODEORG1("000500000017", "DONO", 1),
    CODEORG3("000500000020", "DONO", 3),
    CODEORG5("000500000021", "DONO", 5),
    CODEORG(new CardType[] { CODEORG1, CODEORG3, CODEORG3 }),

    WIKIPEDIA1("000500001187", "DONO", 1),
    WIKIPEDIA3("000500001188", "DONO", 3),
    WIKIPEDIA5("000500001189", "DONO", 5),
    WIKIPEDIA(new CardType[] { WIKIPEDIA1, WIKIPEDIA3, WIKIPEDIA5 }),

    WWF1("000500000165", "DONO", 1),
    WWF3("000500000166", "DONO", 3),
    WWF5("000500000167", "DONO", 5),
    WWF(new CardType[] { WWF1, WWF3, WWF5 }),

    UNICEF1("000500000310", "DONO", 1),
    UNICEF3("000500000311", "DONO", 3),
    UNICEF5("000500000312", "DONO", 5),
    UNICEF(new CardType[] { UNICEF1, UNICEF3, UNICEF5 }),

    GAMERSOUT1("000500000310", "DONO", 1),
    GAMERSOUT3("000500000311", "DONO", 3),
    GAMERSOUT5("000500000312", "DONO", 5),
    GAMERSOUT(new CardType[] { GAMERSOUT1, GAMERSOUT3, GAMERSOUT5 }),

    REDCROSS1("000500001285", "DONO", 1),
    REDCROSS3("000500001286", "DONO", 3),
    REDCROSS5("000500001287", "DONO", 5),
    REDCROSS(new CardType[] { REDCROSS1, REDCROSS3, REDCROSS5 }),
    
    ;

    /** */
    public static final String REWARDS_ADDRESS = "https://account.microsoft.com/rewards/";
    /** */
    public static final String REWARDS_CARD_ADDRESS = REWARDS_ADDRESS + "redeem/";
    /** */
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

    CardType(CardType[] cards) {
        CardType card = randomCardType(cards);

        url = card.getTag().toCharArray();
        unit = card.getUnit().toCharArray();
        value = card.getValue();
        point_cost = card.getCost();

    }

    /**
     * 
     * @param card
     */

    CardType(CardType card) {
        url = card.getTag().toCharArray();
        unit = card.getUnit().toCharArray();
        value = card.getValue();
        point_cost = card.getCost();

    }

    /**
     * 
     * @param url
     */

    CardType(String url) {
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

    CardType(String url, int value) {
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

    CardType(String url, String unit) {
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

    CardType(String url, String unit, int value) {
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

    private int calculatePointCost(String unit, int value) {
        switch (unit) {
            case "USD" -> value *= 1050;
            case "EAT" -> value *= 1300;
            case "ROBUX" -> value *= 15;
            case "HULU" -> value *= 1120;
            case "DONO" -> value *= 1000;
        }

        return value;

    }
    
    /**
     * Picks random card
     * 
     * @param cards CardTypes to pick from
     * 
     * @return random card
     */

    private CardType randomCardType(CardType[] cards) {
        Random rand = new Random();

        return cards[rand.nextInt(cards.length-1)];
    }

    /**
     * Returns a specific card
     * 
     * @param name Name of the card
     * 
     * @return The CardType
     */

    public static CardType getCardType(String name) {
        // Look for a card with the name given
        for (CardType e : values()) {
            if (e.toString().equalsIgnoreCase(name)) {
                return e;
            }
        }

        // Looks for the first card that is similar
        for (CardType e : values()) {
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

    public static CardType[] getRandomCardTypeWithType(String type) {
        return randomCardType(getCardTypesByType(type))

    }

    /**
     * Gives all available cards with a certain unit
     * 
     * @param type Desired type (Eg. Amazon)
     * 
     * @return array of cards
     */

    public static CardType[] getCardTypesByType(String type) {
        ArrayList<CardType> cards = new ArrayList<CardType>();

        // Iterate through all cards
        for (CardType e : values()) {
            if (type.toLowerCase().contains(e.toString().toLowerCase())) {
                cards.add(e);

            }
        }

        return (CardType[]) cards.toArray();
    }

    /**
     * Gives all available cards with a certain unit
     * 
     * @param price Desired price
     * 
     * @return array of cards
     */

    public static CardType[] getCardTypesByUnit(String unit) {
        ArrayList<CardType> cards = new ArrayList<CardType>();

        // Iterate through all cards
        for (CardType e : values()) {
            if (e.getUnit().equalsIgnoreCase(unit)) {
                cards.add(e);

            }
        }

        return (CardType[]) cards.toArray();
    }

    /**
     * Gives all available cards at a certain value
     * 
     * @param price Desired price
     * 
     * @return array of cards
     */

    public static CardType[] getCardTypesByPrice(int price) {
        ArrayList<CardType> cards = new ArrayList<CardType>();

        // Iterate through all cards
        for (CardType e : values()) {
            if (e.getValue() == price) {
                cards.add(e);

            }
        }

        return (CardType[]) cards.toArray();
    }

    /**
     * Gives all available cards at a certain value
     * 
     * @param point_cost Desired point cost
     * 
     * @return array of cards
     */

    public static CardType[] getCardTypesByPointCost(int point_cost) {
        ArrayList<CardType> cards = new ArrayList<CardType>();

        // Iterate through all cards
        for (CardType e : values()) {
            if (e.getCost() == point_cost) {
                cards.add(e);

            }
        }

        return (CardType[]) cards.toArray();
    }

    /**
     * Gives all available cards under a certain value
     * 
     * @param price Desired price max
     * 
     * @return array of cards
     */

    public static CardType[] getCardTypesUnderPrice(int price) {
        ArrayList<CardType> cards = new ArrayList<CardType>();

        // Iterate through all cards
        for (CardType e : values()) {
            if (e.getValue() <= price) {
                cards.add(e);

            }
        }

        return (CardType[]) cards.toArray();
    }

    /**
     * Gives all available cards under a certain value
     * 
     * @param point_cost Desired point cost max
     * 
     * @return array of cards
     */

    public static CardType[] getCardTypesUnderPointCost(int point_cost) {
        ArrayList<CardType> cards = new ArrayList<CardType>();

        // Iterate through all cards
        for (CardType e : values()) {
            if (e.getCost() <= point_cost) {
                cards.add(e);

            }
        }

        return (CardType[]) cards.toArray();
    }

    /** */
    public CardType[] getAll() { return values(); }

    /** */
    public String getTag() { return new String(url); }
    /** */
    public String getUnit() { return new String(unit); }
    /** */
    public int getValue() { return value; }
    /** */
    public int getCost() { return point_cost; }
    
}
