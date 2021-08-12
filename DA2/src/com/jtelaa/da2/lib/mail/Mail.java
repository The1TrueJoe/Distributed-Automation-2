package com.jtelaa.da2.lib.mail;

import java.io.Serializable;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Mail object for organization purposes
 * 
 * @since 2
 * @author Joseph
 */

public class Mail implements Serializable {

    private String[] email_to;
    private String email_from;

    private String message;
    private String subject;

    /**
     * Constructor
     * 
     * @param email_to Email recipient
     * @param email_from Email sender
     * @param subject Email subject
     * @param message Email message
     */

    public Mail(String email_to, String email_from, String subject, String message) {
        this.email_to = new String[] { email_to };
        this.email_from = email_from;
        this.subject = subject;
        this.message = message;

    }

    /**
     * Constructor
     * 
     * @param email_to Email recipients
     * @param email_from Email sender
     * @param subject Email subject
     * @param message Email message
     */

    public Mail(String email_to[], String email_from, String subject, String message) {
        this.email_to = email_to;
        this.email_from = email_from;
        this.subject = subject;
        this.message = message;

    }

    /**
     * Constructor
     * 
     * @param email_to Email recipient
     * @param subject Email subject
     * @param message Email message
     */

    public Mail(String email_to, String subject, String message) {
        this.email_to = new String[] { email_to };
        this.subject = subject;
        this.message = message;

    }

    /**
     * Constructor
     * 
     * @param email_to Email recipients
     * @param subject Email subject
     * @param message Email message
     */

    public Mail(String email_to[], String subject, String message) {
        this.email_to = email_to;
        this.subject = subject;
        this.message = message;

    }

    /**
     * 
     * @param email_from
     */

    public void setSender(String email_from) { this.email_from = email_from; }


    /** @return Sender email */
    public String getSender() { return email_from; }

    /** @return Destination email */
    public String[] getRecipients() { return email_to; }

    /** @return Destination email (If only one recipient) */
    public String getRecipient() { return email_to[0]; }

    /** @return Subject */
    public String getSubject() { return subject; }

    /** @return Message */
    public String getMessage() { return message; }

    /**
     * Formats the message so it can be sent
     * 
     * @param session Mail Session
     * 
     * @return Email
     * 
     * @throws MessagingException
     */

    public MimeMessage get(Session session) throws MessagingException {
        MimeMessage mimeMessage = new MimeMessage(session);

        mimeMessage.setFrom(new InternetAddress(email_from));

        for (String recepient : email_to) {
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
        }

        mimeMessage.setSubject(subject);
        mimeMessage.setText(message);
        
        return mimeMessage;

    }

    /**
     * Prints out the basic information in the message
     * 
     * @return String of summary
     */

    public String summary() {
        String receps = "";
        for (String to : email_to) { receps += to + " "; }

        return "[To: " + receps + ", From: " + email_from + ", Subject: " + subject +"]";
    }

    public String toString() { 
        String receps = "";
        for (String to : email_to) { receps += to + " "; }

        return 
            "To: " + receps + ", From: " + email_from + "\n" +
            "Subject: " + subject + "\n" +
            "Message:\n" + 
            message
        ; 
    }
    
}
