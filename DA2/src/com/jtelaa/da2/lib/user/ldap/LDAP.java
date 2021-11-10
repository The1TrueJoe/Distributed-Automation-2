package com.jtelaa.da2.lib.user.ldap;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import com.jtelaa.da2.lib.config.ConfigHandler;
import com.jtelaa.da2.lib.log.Log;

/**
 * User authentication against LDAP
 * 
 * @deprecated no longer going to be used in system
 */

 // TODO comment

@Deprecated
public class LDAP {

    private static DirContext directory;

    private static Hashtable<String, String> environment;
    private static String ldap_url;

    /** Adds the url of the LDAP Server */
    public static void setup(String url) { ldap_url = url; }
    
    /** Adds the url of the LDAP Server */
    public static void setup(ConfigHandler config) { ldap_url = config.getProperty("ldap_url"); }

    /**
     * Authenticates against and LDAP server with a temporary connection
     * 
     * @param user User
     * @param ou OU
     * 
     * @return Success
     */

    public static boolean authenticate(String user, String ou) {
        Log.sendMessage("Setting up LDAP directory at " + ldap_url);
        environment = new Hashtable<>();

        environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        environment.put(Context.PROVIDER_URL, ldap_url);
        environment.put(Context.SECURITY_AUTHENTICATION, "simple");
        environment.put(Context.SECURITY_PRINCIPAL, "uid="+user+",ou="+ou);
        environment.put(Context.SECURITY_CREDENTIALS, "secret");

        try {
            directory = new InitialDirContext(environment);
            Log.sendMessage("Sucessful LDAP Authetication");
            close();
            return true;

        } catch (AuthenticationException e) {
            Log.sendMessage(user  + " is invalid");
            close();
            return false;
        
        } catch (Exception e) {
            Log.sendMessage("Failed" + e.getMessage());
            close();
            return false;

        }

    }

    /**
     * Close the directory connection
     * 
     * @return Success of the action
     */

    public static boolean close() {
        try {
            directory.close();

            return true;

        } catch (Exception e) {
            Log.sendMessage("Failed to close" + e.getMessage());
            return false;

        }
    }
    
}
