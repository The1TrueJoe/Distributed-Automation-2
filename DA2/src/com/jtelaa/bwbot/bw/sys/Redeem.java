package com.jtelaa.bwbot.bw.sys;

import com.jtelaa.bwbot.bw.util.BWControls;
import com.jtelaa.bwbot.bwlib.Card;

public class Redeem {


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

    public static void setup() {
    }

    public static void redeemCard(Card card) {
    }
    
}
