package com.jtelaa.da2.bot.plugin.bw.sys;

import java.util.GregorianCalendar;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.jtelaa.da2.bot.plugin.bw.BingRewards;
import com.jtelaa.da2.bot.plugin.bw.util.BWMessages;
import com.jtelaa.da2.bot.plugin.bw.util.BWPorts;
import com.jtelaa.da2.bw_manager.accounts.Account;
import com.jtelaa.da2.bw_manager.util.Card;
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

    public static final String OUTLOOK_EMAIL_ADDRESS = "https://outlook.live.com/mail/0/inbox";

    public static String pt_mgr_ip;

    public static Account me;

    public static void setup() {
        pt_mgr_ip = BingRewards.config.getProperty("pt_mgr_ip");

    }

    /**
     * Announces the account info
     */

    public static void announceAccount() {
        ClientUDP pt_announce = new ClientUDP(pt_mgr_ip, BWPorts.INFO_ANNOUNCE.getPort());
        pt_announce.startClient();
        
        me.newPoints(getPointCount());
        pt_announce.sendObject(me);
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

        me.setFirstName(BingRewards.config.getProperty("first_name", "Jane"));
        me.setLastName(BingRewards.config.getProperty("last_name", "Doe"));
        me.setUsername(BingRewards.config.getProperty("user", "JDoe20"));
        me.setPassword(BingRewards.config.getProperty("password", "Passw0rd!"));

        GregorianCalendar dob = new GregorianCalendar();
        dob.setTimeInMillis(Long.parseLong(BingRewards.config.getProperty("dob", "0")));
        me.setBirthDay(dob);

    }

    /**
     * 
     */

    public static void requestAccount() {

        ServerUDP acct_response = new ServerUDP(BWPorts.INFO_RECEIVE.getPort());
        acct_response.startServer();

        ClientUDP acct_request = new ClientUDP(pt_mgr_ip, BWPorts.INFO_REQUEST.getPort());
        acct_request.startClient();

        acct_request.sendMessage(BWMessages.ACCOUNT_REQUEST_MESSAGE.getMessage());

        String response;
        me = new Account();

        do {
            response = acct_response.getMessage();
            if (response.contains(BWMessages.ACCOUNT_REPONSE_MESSAGE.getMessage())) {
                me = (Account) acct_response.getObject();
                
                BingRewards.config.setProperty("user", me.getUsername());
                BingRewards.config.setProperty("password", me.getPassword());
                BingRewards.config.setProperty("first_name", me.getPassword());
                BingRewards.config.setProperty("last_name", me.getPassword());
                BingRewards.config.setProperty("dob", me.getBirthDay().getTimeInMillis() + "");
                
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

        SearchSystem.openChrome(url);

        return "";

    }

    public String getGiftCardCode() {
        return "";
    }
    
}
