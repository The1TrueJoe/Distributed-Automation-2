package com.jtelaa.da2.bot.plugin.bw.util;

import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.jtelaa.da2.bot.main.Main;
import com.jtelaa.da2.bot.plugin.bw.BingRewards;
import com.jtelaa.da2.lib.log.Log;
import com.jtelaa.da2.lib.net.client.ClientUDP;
import com.jtelaa.da2.redemption_manager.util.Account;
import com.jtelaa.da2.redemption_manager.util.Card;

/**
 * Allows for the auto-redemption of points
 * 
 * @since 2
 */

public class AcctInfo {

    public static final String REWARDS_ADDRESS = "https://account.microsoft.com/rewards/";
    public static final String REWARDS_CARD_ADDRESS = REWARDS_ADDRESS + "redeem/";
    public static final String REDEMPTION_CHECKOUT_ADDRESS = REWARDS_CARD_ADDRESS + "checkout?productId=";

    public static final String OUTLOOK_EMAIL_ADDRESS = "https://outlook.live.com/mail/0/inbox";

    private static ClientUDP ptmgr;

    public static Account me;

    public static void setup() {
        ptmgr = new ClientUDP(Main.me.getPointMgrIP(), BWPorts.INFO_ANNOUNCE.getPort());
        ptmgr.startClient();

        me = new Account(BingRewards.config.getProperty("email"), BingRewards.config.getProperty("password"));

    }

    public static void announceAccount() {
        me.newPoints(getPointCount());
        ptmgr.sendObject(me);
        
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

    public static void setupAccount() {

        // TODO add account setup

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
    
}
