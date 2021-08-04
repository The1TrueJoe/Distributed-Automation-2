package com.jtelaa.bwbot.bw.sys;

import java.util.GregorianCalendar;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import com.jtelaa.bwbot.bw.Main;
import com.jtelaa.bwbot.bw.util.BWControls;
import com.jtelaa.bwbot.bwlib.Account;
import com.jtelaa.bwbot.bwlib.BWMessages;
import com.jtelaa.bwbot.bwlib.BWPorts;
import com.jtelaa.bwbot.bwlib.Card;
import com.jtelaa.da2.lib.log.Log;
import com.jtelaa.da2.lib.misc.MiscUtil;
import com.jtelaa.da2.lib.net.client.ClientUDP;
import com.jtelaa.da2.lib.net.server.ServerUDP;

/**
 * Allows for the auto-redemption of points
 * 
 * @since 2
 */

 // TODO comment

public class AcctInfo {

    /** */
    public static final String OUTLOOK_EMAIL_URL = "https://outlook.live.com/mail/0/inbox";

    /** */
    public static String bw_mgr_ip;

    /** */
    public static Account me;

    /** */
    public static void setup() {
        bw_mgr_ip = Main.config.getProperty("bw_mgr_ip");

    }

    /**
     * Announces the account info
     */

    public static void announceAccount() {
        // Setup client
        ClientUDP pt_announce = new ClientUDP(bw_mgr_ip, BWPorts.ACCOUNT_ANNOUNCE.getPort());
        pt_announce.startClient();
        
        // Recalculate points
        me.newPoints(getPointCount());

        // Send account
        pt_announce.sendObject(me);

        // Close
        pt_announce.closeClient();

    }


    /**
     * Looks throught page for the tag
     * 
     * <pre>
     * {@code <span id="id_rc" class="serp">25</span> }
     * </pre>
     * 
     * @return point count
     */

    public static int getPointCount() {
        try {
            final WebClient webClient = new WebClient(BrowserVersion.CHROME);
            final HtmlPage page = webClient.getPage(SearchSystem.BING_URL + "rewards");
            
            final List<DomElement> spans = page.getElementsByTagName("span");
            for (DomElement element : spans) {
                if (element.getAttribute("class").equals("serp")) {
                    webClient.close();
                    return Integer.parseInt(element.getNodeValue());

                }
            }

            webClient.close();

        } catch (Exception e) {
            Log.sendMessage(e.getMessage());
            
        }
        
        return 0;

    }

    /**
     * Sets up the account on the local machine
     * 
     * <p> (Account info is already specified)
     */

    public static void setupAccount() {

        // TODO add account setup

    }

    /**
     * 
     */

    public static void loadAccount() {
        me = new Account();

        me.setFirstName(Main.config.getProperty("first_name", "Jane"));
        me.setLastName(Main.config.getProperty("last_name", "Doe"));
        me.setUsername(Main.config.getProperty("user", "JDoe20"));
        me.setPassword(Main.config.getProperty("password", "Passw0rd!"));

        GregorianCalendar dob = new GregorianCalendar();
        dob.setTimeInMillis(Long.parseLong(Main.config.getProperty("dob", "0")));
        me.setBirthDay(dob);

    }

    /**
     * 
     */

    public static void requestAccount() {

        // Server to send account data
        ServerUDP acct_response = new ServerUDP(BWPorts.ACCOUNT_ANNOUNCE.getPort());
        acct_response.startServer();

        // Client to accept requests
        ClientUDP acct_request = new ClientUDP(bw_mgr_ip, BWPorts.INFO_REQUEST.getPort());
        acct_request.startClient();

        // Send an account request
        acct_request.sendMessage(BWMessages.ACCOUNT_REQUEST_MESSAGE.getMessage());

        String response;
        me = new Account();

        do {
            // Get and check response
            response = acct_response.getMessage();
            if (response.contains(BWMessages.ACCOUNT_REPONSE_MESSAGE.getMessage())) {
                me = (Account) acct_response.getObject();
                
                // Store account info
                Main.config.setProperty("user_name", me.getUsername());
                Main.config.setProperty("password", me.getPassword());
                Main.config.setProperty("first_name", me.getPassword());
                Main.config.setProperty("last_name", me.getPassword());
                Main.config.setProperty("birthday", me.getBirthDay().getTimeInMillis() + "");
                
                return;
            }

            MiscUtil.waitasec();

        } while (response.contains(BWMessages.ACCOUNT_REPONSE_MESSAGE.getMessage()));
    }

    /**
     * Looks throught page for the gift card and redeems it
     * 
     * <pre>
     * {@code 
     *  <button id="redeem-checkout-review-confirm" class="btn-primary card-button-height padding-left-24 padding-right-24">
     *  <div class="text-body margin-top-5 spacer-32-bottom x-hidden-focus"> Order ID: 7b584876-0491-4aac-ac33-4ad4ada1e537 </div>
     * }
     * </pre>
     * 
     * @return orderid
     */

    public String redeemGiftCard(Card card) {
        String url = card.getTag();

        // TODO add redemption system

        BWControls.openChrome(url);

        return "";

    }

    /** */

    public String getGiftCardCode() {
        return "";
    }
    
}
