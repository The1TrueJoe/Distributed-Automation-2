package com.jtelaa.bwbot.bwlib;

import java.io.Serializable;
import java.util.ArrayList;

import com.jtelaa.da2.lib.mail.Mail;

public class Request implements Serializable {

    public static volatile double CARD_TO_DAY_RATIO = (1/6);

    /** */
    private int value;

    /** */
    private String unit; 

    /** */
    private Card[] card;

    /** */
    private BWUser user;

    /** */
    private Mail mail;

    /** */
    private double timedays;


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
            "   User: " + user.getName() + "\n" +
            "   Value: " + value + " in " + name + "\n" + 
            "   Cost: " + Card.calculatePointCost(example.getUnit(), value)

        );

        while (current_total <= value) {
            card = Card.getRandomCardWithType(name);
            cards.add(card);

            current_total += card.getValue();
            current_total_points += card.getCost();

            if (current_total_points >= user.getEntitledPoints() + (user.getEntitledPoints() * .1)) {
                addon = (
                    "\n\n" + current_total_points + " is over entitlement of " + user.getEntitledPoints() +
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

        mail = new Mail(user.getAddress(), "", mail_message);

    }


    public Request(Card card) {
        this.card = new Card[] { card } ;

        this.value = card.getValue();
        this.unit = card.getUnit();
        
    }

    public Mail getMail() { return mail; }

    public Card getCard() { return card[0]; }


    public Request[] split() {
        Request[] requests = new Request[card.length];

        for (int i = 0; i < requests.length; i++) {
            requests[i] = new Request(card[i]);

        }

        return requests;

    }

    


}