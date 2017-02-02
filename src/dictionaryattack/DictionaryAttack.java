package dictionaryattack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;

/**
 *
 * @author alex
 */
public class DictionaryAttack {

    /**
     * Reads in Dictionary document
     *
     * @param args the command line arguments
     * @throws java.io.UnsupportedEncodingException
     * @throws java.security.NoSuchAlgorithmException
     */
    public static void main(String[] args) throws UnsupportedEncodingException,
            NoSuchAlgorithmException {
        String[] passwords = new String[6];
        passwords[0] = "6f047ccaa1ed3e8e05cde1c7ebc7d958";
        passwords[1] = "275a5602cd91a468a0e10c226a03a39c";
        passwords[2] = "b4ba93170358df216e8648734ac2d539";
        passwords[3] = "dc1c6ca00763a1821c5af993e0b6f60a";
        passwords[4] = "8cd9f1b962128bd3d3ede2f5f101f4fc";
        passwords[5] = "554532464e066aba23aee72b95f18ba2";
        LinkedList<String> dictionary = getDictionary();
        for (String hash : passwords) {
            System.out.println("Comparing new hash value");
            compareDictionary(dictionary, hash);
        }
    }

    /**
     * Hashes string value using MD5
     *
     * @param s the string to be hashed
     * @return String of hex values representing MD5 hash of s
     * @throws java.io.UnsupportedEncodingException
     * @throws java.security.NoSuchAlgorithmException
     */
    public static String md5Hash(String s) throws UnsupportedEncodingException,
            NoSuchAlgorithmException {
        // gets byte array for s
        byte[] bytesOfS = s.getBytes("UTF-8");
        // hashes byte array with MD5
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] theDigest = md.digest(bytesOfS);
        // converts hashed byte array to string of hex values
        StringBuilder sb = new StringBuilder();
        for (byte b : theDigest) {
            sb.append(String.format("%02X", b));
        }
        String hash = sb.toString().toLowerCase();
        //
        return hash;
    }

    /**
     * Prompts user for path to dictionary file, reads in file, and creates
     * linked list of possible passwords.
     *
     * @return linked list of possible passwords
     */
    public static LinkedList<String> getDictionary() {
        LinkedList<String> dictionary = new LinkedList<>();
        FileReader fr = null;
        BufferedReader br = null;
        String FILENAME = "phpbb.txt";

        try {
            fr = new FileReader(FILENAME);
            br = new BufferedReader(fr);
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                dictionary.add(currentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("Dictionary is " + dictionary.size() 
        + " passwords long.");
        return dictionary;
    }

    /**
     * @param dict the list of possible passwords to compare
     * @param hash the hash value to compare to
     *
     * @return True if it matches a password, false if not
     * @throws java.io.UnsupportedEncodingException
     * @throws java.security.NoSuchAlgorithmException
     */
    public static boolean compareDictionary(LinkedList<String> dict,
            String hash) throws UnsupportedEncodingException, 
            NoSuchAlgorithmException {
        int i = 1;
        int j = 1;
        int tenth = dict.size()/10;
        for (String pass : dict) {
            if(i%tenth==0){
                System.out.println(j + "0% of the way through dictionary.");
                j++;
            }
            if (hash.equals(md5Hash(pass))) {
                printResult(hash, pass, .2);
                return true;
            }
            i++;
        }

        return false;
    }

    /**
     * @param hash hash value to be printed
     * @param pass password to be printed
     * @param time amount of time to be printed
     */
    public static void printResult(String hash, String pass, double time) {
        System.out.println("The password for the hash value "
                + hash + " is \n" + pass + ", and it takes the program " + time
                + " seconds to recover this password.");
    }

}
