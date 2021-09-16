package com.jtelaa.da2.lib.mail;

import java.util.Locale;

import com.google.i18n.phonenumbers.PhoneNumberToCarrierMapper;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

/**
 * ENUM containing the gateway address for SMS
 * 
 * Does not include "@"
 */

 // TODO Comment

public enum MessageGateways {
    ATT("txt.att.net", "mms.att.net"),
    BOOST("sms.myboostmobile.com", "myboostmobile.com"),
    CSPIRE("cspire1.com"),
    CONSUMER("mailmymobile.net"),
    CRICKET("sms.cricketwireless.net", "mms.cricketwireless.net"),
    GOOGLE("msg.fi.google.com", true),
    METROPCS("mymetropcs.com", true),
    MINT("mailmymobile.net"),
    PAGEPLUS("vtext.com", "mypixmessages.com"),
    REPUBLIC("text.republicwireless.com"),
    SIMPLE("smtext.com"),
    SPRINT("messaging.sprintpcs.com", "pm.sprint.com"),
    TMOBILE("tmomail.net", true),
    TING("message.ting.com"),
    TRACFONE("mmst5.tracfone.com", true),
    USCELL("email.uscc.net", "mms.uscc.net"),
    VERIZON("vtext.com", "vzwpix.com"),
    VIRGIN("vmobl.com", "vmpix.com"),
    XFINITY("vtext.com", "mypixmessages.com")
    ;

    /** */
    private char[] sms_address;
    /** */
    private char[] mms_address;

    /** */
    private boolean has_sms_address;
    /** */
    private boolean has_mms_address;

    /**
     * 
     */

    MessageGateways(String sms_address, String mms_address) {
        this.sms_address = sms_address.toCharArray();
        has_sms_address = true;

        this.mms_address = mms_address.toCharArray();
        has_mms_address = true;

    }

    /**
     * 
     */

    MessageGateways(String sms_address) {
        this.sms_address = sms_address.toCharArray();
        has_sms_address = true;
        has_mms_address = false;

    }

    /**
     * 
     */

    MessageGateways(String address, boolean supports_both) {
        this.sms_address = address.toCharArray();
        has_sms_address = true;

        if (supports_both) { mms_address = sms_address; }
        has_mms_address = supports_both;

    }

    /** Return the carrier's Simple Message Service address */
    public String getSMSAddress() { return new String(sms_address); }
    /** Return the carrier's Multimedia Message Service address */
    public String getMMSAddress() { return new String(mms_address); }

    /** */
    public boolean hasSMSAddress() { return has_sms_address; }
    /** */
    public boolean hasMMSAddress() { return has_mms_address; }

    /**
     * Returns a specific carrier
     * 
     * @param carrier Name of the carrier
     * 
     * @return The carrier
     */

    public static MessageGateways findCarrier(String carrier) {
        // Look for a carrier with the name given
        for (MessageGateways e : values()) {
            if (e.toString().equalsIgnoreCase(carrier)) {
                return e;
            }
        }

        // Looks for the first carrier that is similar
        for (MessageGateways e : values()) {
            if (carrier.toLowerCase().contains(e.toString().toLowerCase())) {
                return e;
            }
        }

        // Returns the standard VERIZON carrier if neither above apply
        return VERIZON;
    }

    /**
     * 
     */

    public static MessageGateways getCarrier(PhoneNumber number) {
        PhoneNumberToCarrierMapper mapper = PhoneNumberToCarrierMapper.getInstance();
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

        if (phoneNumberUtil.isPossibleNumber(number)) {
            MessageGateways carrier = MessageGateways.findCarrier(mapper.getNameForNumber(number, Locale.US));
            
            if (carrier.getMMSAddress() != null && carrier.getMMSAddress() != " " && carrier.getMMSAddress() != "") {
                return carrier;

            } 
        } 
        
        return VERIZON;
        
    }

    /**
     * 
     */

    public static MessageGateways getCarrier(String number) {
        PhoneNumber phonenumber = new PhoneNumber();
        phonenumber.setCountryCode(1).setNationalNumber(Long.parseLong(number));
        
        return getCarrier(phonenumber);
        
    }

}