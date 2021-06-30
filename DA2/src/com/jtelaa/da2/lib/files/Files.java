package com.jtelaa.da2.lib.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.jtelaa.da2.lib.log.Log;

/**
 * Utilities class for handling files
 * 
 * @since 2
 * @author Joseph
 */

public class Files {

    /**
     * Reads a file within the classpath
     * 
     * @param path Path of the file
     * @return File as a String
     */

    public synchronized static String readInternalFile(String path) {
        ArrayList<String> list = listLinesInternalFile(path);
        String out = "";

        for (String item : list) {
            out += item + "\n";

        }

        return out;

    }

    /**
     * Reads a file within the classpath
     * 
     * @param path Path of the file
     * @return File as a list of the lines
     */

    public synchronized static ArrayList<String> listLinesInternalFile(String path) {
        ArrayList<String> list = new ArrayList<String>();
        
        try {
            @SuppressWarnings ({"all"})
            InputStream inputStream = ClassLoader.getSystemClassLoader().getSystemResourceAsStream(path);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");

            BufferedReader in = new BufferedReader(inputStreamReader);

            for (String line; (line = in.readLine()) != null;) {
                list.add(line);

            }

        } catch (Exception e) {
            Log.sendMessage(e.getMessage());
            
        }

        return list;
    }

    /**
     * Reads a file
     * 
     * @param path Path of the file
     * @return File as a String
     */

    public synchronized static String readFile(String path) {
        ArrayList<String> list = listLinesFile(path);
        String out = "";

        for (String item : list) {
            out += item + "\n";

        }

        return out;

    }

    /**
     * Reads a file
     * 
     * @param path Path of the file
     * @return File as a list of the lines
     */

    public synchronized static ArrayList<String> listLinesFile(String path) {
        ArrayList<String> list = new ArrayList<String>();
        
        try {
            @SuppressWarnings ({"all"})
            File file = new File(path);
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

            BufferedReader in = new BufferedReader(inputStreamReader);

            for (String line; (line = in.readLine()) != null;) {
                list.add(line);

            }

            in.close();

        } catch (Exception e) {
            Log.sendMessage(e.getMessage());
            
        }

        return list;
    }

    /**
     * Writes a serializable object to the file
     * 
     * @param object Object to write
     * @param path Path of the file
     */

    public synchronized static void writeObjectToFile(Object[] object, String path) {
        try {
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            for (Object ob : object) {
                objectOut.writeObject(ob);
            }
            
            objectOut.close();

        } catch (Exception e) {
            Log.sendMessage(e.getMessage());

        }
    }

    /**
     * Writes a serializable object to the file
     * 
     * @param object Object to write
     * @param path Path of the file
     */

    public synchronized static void writeObjectToFile(Object object, String path) {
        writeObjectToFile(new Object[] {object}, path);
    }

    /**
     * Reads a serializable object from a file
     * 
     * @param path Path of the file
     * @return Object
     */

    public synchronized static Object readObjectFromFile(String path, int line_index) {
        return readObjectFromFile(path).get(line_index);
    }

    /**
     * Reads a serializable object from a file
     * 
     * @param path Path of the file
     * @return Object
     */

    public synchronized static ArrayList<Object> readObjectFromFile(String path) {
        ArrayList<Object> objects = new ArrayList<Object>();
        
        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            for (Object line; (line = objectIn.readObject()) != null;) {
                objects.add(line);

            }
            
            objectIn.close();

            return objects;

        } catch (Exception e) {
            Log.sendMessage(e.getMessage());

        }

        return objects;

    }

}