package com.jtelaa.da2.redemption_manager.util;

public enum Card {

    AMAZON("000800000000"),
    WALMART("000800000020"),
    TARGET("000800000018"),

    ROBLOX("000400000343"),

    DOMINOES("000800000013"),
    DUNKIN("000800000057"),
    TACOBELL("000800000110"),
    STARBUCKS("000800000001"),
    BURGERKING("000800000010"),

    UBEREATS("000800000117"),
    DOORDASH("000800000115"),
    GRUBHUB("000800000113"),
    
    REI("000800000016"),

    HULU("000800000046"),

    FIRST("000500000016"),
    CODEORG("000500000017"),
    WIKIPEDIA("000500001187"),
    WWF("000500000165"),
    UNICEF("000500000310"),
    GAMERSOUT("000500001170"),
    REDCROSS("000500001285");
    

    private final char[] url;

    Card(String url) {
        this.url = url.toCharArray();
    }

    public String getTag() { return new String(url); }
    
}
