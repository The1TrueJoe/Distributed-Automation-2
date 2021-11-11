package com.jtelaa.bwbot.bw.sys;

import java.util.GregorianCalendar;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import com.jtelaa.bwbot.bw.Main;
import com.jtelaa.bwbot.bwlib.Account;
import com.jtelaa.bwbot.bwlib.BWMessages;
import com.jtelaa.bwbot.bwlib.BWPorts;
import com.jtelaa.bwbot.bwlib.Query;
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
    public static void setup(boolean first_time) {
        bw_mgr_ip = Main.config.getProperty("bw_mgr_ip");

        if (first_time) { requestAccount(); }
        loadAccount();

    }

    /**
     * Announces the account info
     */

    public static void announceAccount() {
        // Setup client
        ClientUDP pt_announce = new ClientUDP(bw_mgr_ip, BWPorts.ACCOUNT_ANNOUNCE);
        pt_announce.startClient();
        
        // Recalculate points
        me.newPoints(getPointCount());

        // Send account
        Log.sendMessage("Announcing Account Info.....");
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
            final HtmlPage page = webClient.getPage(Query.BING_URL + "rewards");
            
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
     * Loads account info from the properties file
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
     * Request account using SQL
     */

    // TODO Create SQL Request
    public static void requestAccount() {

    }

    /**
     * Send a request message for an account
     * 
     * @deprecated replaced with SQL
     */

    @Deprecated
    public static void requestAccountUDP() {

        // Server to send account data
        ServerUDP acct_response = new ServerUDP(BWPorts.ACCOUNT_ANNOUNCE);
        acct_response.startServer();

        // Client to accept requests
        ClientUDP acct_request = new ClientUDP(bw_mgr_ip, BWPorts.INFO_REQUEST);
        acct_request.startClient();

        // Send an account request
        acct_request.sendMessage(BWMessages.ACCOUNT_REQUEST_MESSAGE.getMessage());

        String response;
        me = new Account();

        do {
            // Get and check response
            response = acct_response.getMessage();
            if (response.contains(BWMessages.ACCOUNT_REPONSE_MESSAGE.getMessage())) {
                // Get the account
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

        } while (!response.contains(BWMessages.ACCOUNT_REPONSE_MESSAGE.getMessage()));
    }
    
}
