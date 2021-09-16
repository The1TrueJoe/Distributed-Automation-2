package com.jtelaa.da2.lib.user.local;

import java.util.ArrayList;

import com.jtelaa.da2.lib.files.FileUtil;
import com.jtelaa.da2.lib.files.ObjectUtils;
import com.jtelaa.da2.lib.log.Log;

/**
 * This manages the user accounts in the system
 * 
 * @since 2
 * @author Joseph
 */

 // TODO comment

public class UserHandler {

    public ArrayList<User> users;

    public UserHandler() {
        users = new ArrayList<User>();

    }

    /**
     * Gets a user at an index
     * 
     * @param index Index of user
     * 
     * @return User
     */

    public User getUser(int index) {
        return users.get(index);

    }

    /**
     * Adds user to pool
     * 
     * @param user User to add
     */

    public void addUser(User user) {
        users.add(user);

    }

    /**
     * Serializes and exports the user objects
     * 
     * @param file_path File path
     */

    public void exportusr(String file_path) {
        FileUtil.writeObjectToFile(users.toArray(), file_path);

    }

    /**
     * Imports users from a file
     * 
     * @param path File path
     */

    public void importusr(String path) {
        importusr(FileUtil.listLinesFile(path));

    }

    /**
     * Imports users from an internal file
     * 
     * @param path File path
     */

    public void importInternalusr(String path) {
        importusr(FileUtil.listLinesInternalFile(path));

    }

    /**
     * Imports users from an arraylist of file lines
     * 
     * @param file_out List of lines
     */

    public void importusr(ArrayList<String> file_out) {
        try {
            for (String usr : file_out) {
                users.add((User) ObjectUtils.objfromString(usr));
        
            }

        } catch (Exception e) {
            Log.sendMessage(e.getMessage());

        }
    }

    /**
     * Exports root user into file
     */
    @SuppressWarnings("all")
    public static void main(String[] args) {
        UserHandler userHandler = new UserHandler();
        userHandler.addUser(new User("root", "password", true));
        userHandler.exportusr("src/com/jtelaa/da2/lib/user/local/RootUser.txt");

    }
    
}
