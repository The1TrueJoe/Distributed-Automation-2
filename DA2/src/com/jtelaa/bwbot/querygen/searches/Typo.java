package com.jtelaa.bwbot.querygen.searches;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Random;

/**
 * Generates a variety of typos
 * Thanks so much https://github.com/KeywordDomains/TypoGenerator
 * 
 * @param String query
 * @return Query string with a typo
 */
public class Typo {


    /**
     * Generates a wrong key typo by matching one random letter in the query string to a letter adjacent to it on the QWERTY keyboard
     * @param query
     * @return query string with one char wrong
     */
    public static String wrongKey(String query) {
        String typo;
        Random r = new Random();
        // Dictionary of close keys
        Dictionary<String, String> keyboard = new Hashtable<String, String>() {};
        // list of close keys, use \t and # to use .split() without regex matching
        String kbd = "1\t2,q#2\t1,q,w,3#3\t2,w,e,4#4\t3,e,r,5#5\t4,r,t,6#6\t5,t,y,7#7\t6,y,u,8#8\t7,u,i,9#9\t8,i,o,0#0\t9,o,p,-#-\t0,p#q\t1,2,w,a#w\tq,a,s,e,3,2#e\tw,s,d,r,4,3#r\te,d,f,t,5,4#t\tr,f,g,y,6,5#y\tt,g,h,u,7,6#u\ty,h,j,i,8,7#i\tu,j,k,o,9,8#o\ti,k,l,p,0,9#p\to,l,-,0#a\tz,s,w,q#s\ta,z,x,d,e,w#d\ts,x,c,f,r,e#f\td,c,v,g,t,r#g\tf,v,b,h,y,t#h\tg,b,n,j,u,y#j\th,n,m,k,i,u#k\tj,m,l,o,i#l\tk,p,o#z\tx,s,a#x\tz,c,d,s#c\tx,v,f,d#v\tc,b,g,f#b\tv,n,h,g#n\tb,m,j,h#m\tn,k,j";
        for (String tuple : kbd.split("#")) {
            String[] pairs = tuple.split("\t");
            keyboard.put(pairs[0], pairs[1]);            
        }

        int index = r.nextInt(query.length());

        String c = Character.toString(query.charAt(index));

        String[] typos = keyboard.get(c).split(",");

        String c2 = typos[r.nextInt(typos.length)];

        typo = query.substring(0, index) + c2 + query.substring(index + 1);

        return typo;
        
    }

    /**
     * Generates a missed character typo - removes one character from a string
     * @param query
     * @return query string, with one character missing
     */
    public static String missedChar(String query) {
        Random r = new Random();
        String typo;
        
        int index = r.nextInt(query.length());

        typo = query.substring(0, index) + query.substring(index + 1);

        return typo;
    }

    /**
     * Generates a transposed character typo by swapping two characters
     * @param query
     * @return query string, with two letters swapped
     */
    public static String transposedChar(String query) {
        String typo;
        Random r = new Random();

        int index = r.nextInt(query.length());

        typo = query.substring(0, index) + query.substring(index + 1, index + 2) + query.substring(index, index + 1) + query.substring(index + 2);

        return typo;
    }

    /**
     * Generates a double character typo
     * @param query
     * @return query string, with one character duplicated
     */
    public static String doubleChar(String query) {
        Random r = new Random();
        String typo;

        int index = r.nextInt(query.length());

        typo = query.substring(0, index) + query.substring(index - 1);

        return typo;
    }

    /**
     * Mimics a bit flipping typo, chooses one character and replaces it with one of the possibilites of a bit flip
     * Checks regex to make sure the character is in the alphabet or set of known numbers
     * @param query
     * @return query string, with one character replaced with a bit flipped version of itself
     */
    public static String bitFlip(String query) {
        Random r = new Random();
        String typo;
        String allowed_regex = "[a-zA-Z0-9_\\-\\.]";
        int[] masks = {128,64,32,16,8,4,2,1};


        int index = r.nextInt(query.length());
        String new_letter;
        
        do {
            new_letter = Character.toString(
                (Character.toChars
                (Integer.parseInt
                (Integer.toHexString
                (query.charAt(index))) ^ masks
                            [r.nextInt(masks.length)]
                            ))
                            [0]
                            ).toLowerCase();
        } while (!new_letter.matches(allowed_regex));
        

        typo = query.substring(0, index) + String.valueOf(new_letter) + query.substring(index + 1);

        return typo;
    }
    
}
