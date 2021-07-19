package com.jtelaa.bwbot.bw_manager.redemptions;

/**
 * 
 */

public class Card {

    private CardType card_type;

    private String code;

    /** */
    public String getTag() { return card_type.getTag(); }
    /** */
    public String getUnit() { return card_type.getUnit(); }
    /** */
    public int getValue() { return card_type.getValue(); }
    /** */
    public int getCost() { return card_type.getCost(); }

}