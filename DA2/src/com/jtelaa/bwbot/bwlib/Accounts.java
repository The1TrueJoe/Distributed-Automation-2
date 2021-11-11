package com.jtelaa.bwbot.bwlib;

import java.net.http.HttpClient;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

import com.jtelaa.da2.lib.config.PropertiesUtils;
import com.jtelaa.da2.lib.log.Log;

import org.apache.commons.validator.routines.EmailValidator;

import org.json.JSONArray;
import org.json.JSONObject;

public class Accounts {

    /**
     * Validate email using choice method
     * 
     * <p> Methods: Simple (format), RapidAPI (existance, requires payment)
     * 
     * @param email Email
     * @param method Method
     * 
     * @return validity
     */

    public static synchronized boolean validateEmail(String email, String method) {
        switch (method) {
            case "simple": return validateEmailSimple(email);
            case "rapidapi": return validateEmailRapidAPI(email);
            default: return validateEmailSimple(email);

        }
    }

    /**
     * Validate username using choice method
     * 
     * <p> Methods: Simple (format), RapidAPI (existance, requires payment)
     * 
     * @param username Username 
     * @param method Method
     * 
     * @return validity
     */

    public static synchronized boolean validateUser(String username, String method) { 
        return validate(username, method);

    }

    /**
     * Validate username using choice method
     * 
     * <p> Methods: Simple (format), RapidAPI (existance, requires payment)
     * 
     * @param username Username 
     * @param method Method
     * 
     * @return validity
     */

    public static synchronized boolean validate(String username, String method) {
        switch (method) {
            case "simple": return validateUsernameSimple(username);
            case "rapidapi": return validateUsernameRapidAPI(username);
            default: return validateUsernameSimple(username);

        }
    }

    /**
     * Validates a username
     * <p> Uses apache validator (only formatting)
     * 
     * @param username Username to check
     * 
     * @return validity
     */

    public static synchronized boolean validateUsernameSimple(String username) {
        return validateEmailSimple(username + Account.EMAIL_DOMAIN);
        
    }

    /**
     * Validates an email
     * <p> Uses apache validator (only formatting)
     * 
     * @param email Email to check
     * 
     * @return validity
     */

    public static synchronized boolean validateEmailSimple(String email) {
        // Log
        Log.sendMessage("Validating email " + email);

        // Validate email
        EmailValidator validator = EmailValidator.getInstance();

        // Check validity
        if (validator.isValid(email)) {
            Log.sendMessage(email + " format is valid");
            return true;

        } else {
            Log.sendMessage(email + " format is invalid");
            return false;

        }

    }

    /**
     * Validate username using rapidapi's email-checker
     * 
     * @param username username to check
     * 
     * @return validity
     */

    public static synchronized boolean validateUsernameRapidAPI(String username) {
        return validateEmailRapidAPI(username + Account.EMAIL_DOMAIN);

    }

    /**
     * Validate email using rapidapi's email-checker
     * 
     * @param email Email to check
     * 
     * @return validity
     */

    public static synchronized boolean validateEmailRapidAPI(String email) {
        // Check if its correctly formatted first
        if (!validateEmailSimple(email)) { return false; }
        Log.sendMessage("checking agaist rapidapi email-checker");

        // Config
        Properties config = PropertiesUtils.importInternalConfig("com/jtelaa/bwbot/bw_manager/apis.properties");

        // Reformat Email
        email = email.replace("@", "%40");

        // Create HTTP Request
        HttpRequest request = HttpRequest.newBuilder()
		.uri(URI.create("https://email-checker.p.rapidapi.com/verify/v1?email=" + email))
        .header("x-rapidapi-key", config.getProperty("email-checker"))
		.header("x-rapidapi-host", "email-checker.p.rapidapi.com")
		.method("GET", HttpRequest.BodyPublishers.noBody())
		.build();
        
        try {
            // Check against api
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            
            // Parse JSON
            JSONArray json = new JSONArray(response.body());
            Log.sendMessage("response: " + json.toString());
            
            // Setup local var
            String status = "n";

            // Look through response for the status
            for (int i = 0; i < json.length(); i++) {
                JSONObject json_obj = json.getJSONObject(i);
                status = json_obj.getString("status");

            }

            // Send status to log
            Log.sendLogMessage(email + " using rapidapi is " + status);

            // If valid
            if (status.equals("valid")) { 
                return true;

            } else {
                return false;

            }
            

        } catch (Exception e) {
            Log.sendMessage(e);
        } 

        // Default
        return false;

    }
    
}
