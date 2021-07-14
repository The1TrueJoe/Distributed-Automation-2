package com.jtelaa.da2.lib.mail;

import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;

import com.jtelaa.da2.lib.log.Log;
import com.jtelaa.da2.lib.misc.MiscUtil;

/**
 * Program to send email notifications
 * 
 * <p> Example use case is gift-card code distribution
 * 
 * @since 2
 * @author Joseph
 * 
 * @see com.jtelaa.da2.lib.mail.Mail
 */

 //TODO Comment

public class MailSender extends Thread {

    private String host;

    private String user;
    private String password;

    private boolean isConfigured;

    private Properties properties;
    private Session session;

    public static volatile Queue<Mail> mail_queue;

    /**
     * Configures SMTP
     * 
     * @param user User
     * @param password Password
     * @param host SMTP Host
     */

    public synchronized void configure(String user, String password, String host) {
        this.user = user;
        this.password = password;
        this.host = host;

        isConfigured = true;
    }


    public void run() {
        properties = new Properties();
        properties.put("mail.smtp.host", host);  
        properties.put("mail.smtp.auth", "true"); 

        mail_queue = new LinkedList<>();

        session = Session.getDefaultInstance(properties,
            new Authenticator(){
                @SuppressWarnings("all")
                protected PasswordAuthentication getPasswordAuthenication() {
                    return new PasswordAuthentication(user, password);
                }
            }
        );

        if (!isConfigured) {
            do {
                MiscUtil.waitasec();
                Log.sendMessage("Mail waiting for config!");
            } while (!isConfigured);
        }

        while (run) {
            sendMessage();
        }
    }

    /** Boolean to control the receiver */
    private boolean run = true;

    /** Stops the command receiver */
    public synchronized void stopReceiver() { run = false; }

    /** Checks if the receier is ready */
    public synchronized static boolean receiverReady() { return run; }
    // TODO Implement

    /**
     * Sends messages
     */

    private synchronized void sendMessage() {
        if (mail_queue.size() == 0) { return; }
        Mail mail = mail_queue.poll();
        Log.sendMessage("Attempting to send mail: " + mail.summary());

        if (MiscUtil.notBlank(mail.getMessage())) {
            try {
                Transport.send(mail.get(session));
                Log.sendMessage("Mail sent sucessfully: " + mail.summary());

            } catch (MessagingException e) {
                Log.sendMessage("Failed " + e.getMessage());

            }
        }
    }
    
}
