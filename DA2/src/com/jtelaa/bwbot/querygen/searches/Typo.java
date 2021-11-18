package com.jtelaa.bwbot.querygen.searches;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Random;

import com.google.i18n.phonenumbers.internal.RegexBasedMatcher;

import org.apache.xerces.impl.xpath.regex.Match;

public class Typo {

    public static String getTypo(String query) {
        Random r = new Random();
        String typo;
        int mistake = r.nextInt(6);

        switch (mistake) {
            case 0:
                typo = wrongKey(query);
                break;
            case 1:
                typo = missedChar(query);
                break;
            case 2:
                typo = transposedChar(query);
                break;
            case 3:
                typo = doubleChar(query);
                break;
            case 4:
                typo = bitFlip(query);
                break;
            case 5:
                typo = homophone(query);
                break;
        }

        return typo;
    }

    private static String wrongKey(String query) {
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

    private static String missedChar(String query) {
        Random r = new Random();
        String typo;
        
        int index = r.nextInt(query.length());

        typo = query.substring(0, index) + query.substring(index + 1);

        return typo;
    }

    private static String transposedChar(String query) {
        String typo;
        Random r = new Random();

        int index = r.nextInt(query.length());

        typo = query.substring(0, index) + query.substring(index + 1, index + 2) + query.substring(index, index + 1) + query.substring(index + 2);

        return typo;
    }

    private static String doubleChar(String query) {
        Random r = new Random();
        String typo;

        int index = r.nextInt(query.length());

        typo = query.substring(0, index) + query.substring(index - 1);

        return typo;
    }

    private static String bitFlip(String query) {
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
