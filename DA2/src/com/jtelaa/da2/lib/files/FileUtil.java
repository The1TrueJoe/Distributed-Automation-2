package com.jtelaa.da2.lib.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import com.jtelaa.da2.lib.log.Log;

/**
 * Utilities class for handling files
 * 
 * @since 2
 * @author Joseph
 */

 // TODO comment

public class FileUtil {

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

            //JarFile jarFile = new JarFile("QueryGen.jar");
            //JarEntry entry = jarFile.getJarEntry("TextFile/" + path);
            //InputStreamReader inputStreamReader = new InputStreamReader(jarFile.getInputStream(entry));

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
        return readFile(new File(path));

    }

    /**
     * Reads a file
     * 
     * @param path Path of the file
     * @return File as a String
     */

    public synchronized static String readFile(File path) {
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
        return listLinesFile(new File(path));
        
    }

    /**
     * Reads a file
     * 
     * @param path Path of the file
     * @return File as a list of the lines
     */

    public synchronized static ArrayList<String> listLinesFile(File file) {
        ArrayList<String> list = new ArrayList<String>();
        
        try {
            @SuppressWarnings ({"all"})
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
        writeObjectToFile(object, new File(path)); 
        
    }

    /**
     * Writes a serializable object to the file
     * 
     * @param object Object to write
     * @param file file to write to
     */

    public synchronized static void writeObjectToFile(Object[] object, File file) {
        try {
            FileOutputStream fileOut = new FileOutputStream(file);
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
     * 
     * @return Object
     */

    public synchronized static ArrayList<Object> readObjectFromFile(String path) {
        return readObjectFromFile(new File(path));

    }

    /**
     * Reads a serializable object from a file
     * 
     * @param file File
     * 
     * @return Object
     */

    public synchronized static ArrayList<Object> readObjectFromFile(File file) {
        ArrayList<Object> objects = new ArrayList<Object>();
        
        try {
            FileInputStream fileIn = new FileInputStream(file);
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

    /**
     * Duplicates an internal file for editing outside of the jar
     * <p> Used to store default files within the jar and edit them outside
     * 
     * @param classLoader Class loader for the internal files
     * 
     * @param internal_file Internal file path
     * @param external_file External file path
     * 
     * @return New external file
     */

    public synchronized static File duplicateInternalFile(ClassLoader classLoader, String internal_file, String external_file) {
        try {

            File new_file = new File(external_file);
            Path new_file_path = new_file.toPath();
        
            Files.copy(classLoader.getResourceAsStream(internal_file), new_file_path, StandardCopyOption.COPY_ATTRIBUTES);
        
            return new_file_path.toFile();

        } catch (Exception e) {
            Log.sendMessage(e);
            return null;

        }
        
    }

}