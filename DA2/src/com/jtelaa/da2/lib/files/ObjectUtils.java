package com.jtelaa.da2.lib.files;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;

// TODO comment

public class ObjectUtils {

    /**
     * Deserializes an object encoded as a string
     * 
     * @param string Serialized object
     * 
     * @return Object
     * 
     * @throws IOException
     * @throws ClassNotFoundException
     */

    public static Object objfromString(String string) throws IOException, ClassNotFoundException {
        byte[] data = Base64.getDecoder().decode(string);

        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(data));
        Object obj  = objectInputStream.readObject();
        
        objectInputStream.close();
        
        return obj;
    }

    /**
     * Serializes an object into a string
     * 
     * @param obj Object to serialize
     * 
     * @return Object as a string
     * 
     * @throws IOException
     */
    
    public static String objtoString(Serializable obj) throws IOException {
        ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayInputStream);

        objectOutputStream.writeObject(obj);

        objectOutputStream.close();

        return Base64.getEncoder().encodeToString(byteArrayInputStream.toByteArray()); 
    }
    
}
