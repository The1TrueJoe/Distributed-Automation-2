package com.jtelaa.bwbot.bwlib;

import java.io.Serializable;
import java.util.ArrayList;

import com.jtelaa.da2.lib.mail.Mail;

public class Request implements Serializable {

    /** Constant to calculate the redemption time */
    public static volatile double CARD_TO_DAY_RATIO = (1/6);

    /** Value of the request */
    public int value;

    /** Units of the card */
    public String unit; 

    /** Array of cards */
    public Card[] card;

    /** User to use */
    public BWUser user;

    /** Mail message to send */
    public Mail mail;

    /** Redemption time */
    public double timedays;

    /**
     * 
     * @param value
     * @param name
     * @param user
     */

    public Request(int value, String name, BWUser user) {
        Card card;
        Card example = Card.getCard(name);
        ArrayList<Card> cards = new ArrayList<Card>();

        this.value = value;
        this.unit = example.getUnit();
        this.user = user;

        int current_total = 0;
        int current_total_points = 0;

        String addon = "";
        String mail_message = (
            "Request (" + System.currentTimeMillis() + ")\n" + 
            "   User: " + user.name + "\n" +
            "   Value: " + value + " in " + name + "\n" + 
            "   Cost: " + Card.calculatePointCost(example.getUnit(), value)

        );

        while (current_total <= value) {
            card = Card.getRandomCardWithType(name);
            cards.add(card);

            current_total += card.getValue();
            current_total_points += card.getCost();

            if (current_total_points >= user.entitled_points + (user.entitled_points * .1)) {
                addon = (
                    "\n\n" + current_total_points + " is over entitlement of " + user.entitled_points +
                    " new value will be " + current_total + " in " + name

                );

                this.value = current_total;

                break;

            }

        }

        mail_message += addon;

        this.card = (Card[]) cards.toArray();
        timedays = this.card.length * CARD_TO_DAY_RATIO;

        addon = (
            "   Estimated Time: " + timedays + " days\n" + 
            "   Card Count: " + this.card.length
        );

        mail_message += addon;

        user.subtractEntitledPoints(current_total_points);
        user.addRedeemedPoints(current_total_points);

        mail = new Mail(user.mail, "", mail_message);

    }

    /**
     * 
     * @param card
     * @param user
     */

    public Request(Card card, BWUser user) {
        this(card.getValue(), card.getTag(), user);
        
    }

    /**
     * 
     * @return
     */

    public Mail getMail() { return mail; }

    /**
     * 
     * @return
     */

    public Card getCard() { return card[0]; }

    /**
     * 
     * @return
     */

    public Request[] split() {
        Request[] requests = new Request[card.length];

        for (int i = 0; i < requests.length; i++) {
            requests[i] = new Request(card[i], user);

        }

        return requests;

    }

    


}