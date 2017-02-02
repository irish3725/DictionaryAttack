package dictionaryattack;

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
        String hash = "6f047ccaa1ed3e8e05cde1c7ebc7d958";
        String pass = "181003";
        if (hash.equals(md5Hash(pass))) {
            printResult(hash, pass, .2);
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

        LinkedList<String> dictionary = getDictionary();
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

        System.out.println("Please input the path to the dictionary file "
                + "(.txt file)");

        return dictionary;
    }

    /**
     * @param dict the list of possible passwords to compare
     * @param pass the hash value to compare to
     *
     * @return True if it matches a password, false if not
     */
    public static boolean compareDictionary(String[] dict, String pass) {

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
