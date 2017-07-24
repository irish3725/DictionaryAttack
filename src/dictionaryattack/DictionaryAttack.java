package dictionaryattack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Takes in one or more .txt files for cracking hashed passwords. Each .txt file
 * should have one password per line. .txt files can be read in as arguements
 * or if no arguements are given, it will prompt for one .txt file.
 * 
 * @author alex
 */
public class DictionaryAttack {

    /**
     * Reads in .txt file as a dictionary from arguements. Will take any number
     * of .txt files. If none are given, it will prompt for one .txt file.
     *
     * @param args the command line arguments
     * @throws java.io.UnsupportedEncodingException
     * @throws java.security.NoSuchAlgorithmException
     */
    public static void main(String[] args) throws UnsupportedEncodingException,
            NoSuchAlgorithmException {
        System.out.println("Starting attack.");
        LinkedList<String> passwords = new LinkedList<>();
        passwords.add("6f047ccaa1ed3e8e05cde1c7ebc7d958");
        passwords.add("275a5602cd91a468a0e10c226a03a39c");
        passwords.add("b4ba93170358df216e8648734ac2d539");
        passwords.add("dc1c6ca00763a1821c5af993e0b6f60a");
        passwords.add("8cd9f1b962128bd3d3ede2f5f101f4fc");
        passwords.add("554532464e066aba23aee72b95f18ba2");
        if (args.length == 0) {
            String path;
            System.out.println("Please input the path to dictionary file ");
            Scanner in = new Scanner(System.in);
            path = in.nextLine();
            if (path.startsWith("~")) {
                path = path.substring(1);
            }
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            getDictionary(passwords, path);
        } else {
            for (String path : args) {
                System.out.println("\n");
                if (path.startsWith("~")) {
                    path = path.substring(1);
                }
                if (!path.startsWith("/")) {
                    path = "/" + path;
                }
                getDictionary(passwords, path);
            }
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

        return hash;
    }

    /**
     * Compares hashed values of passwords in .txt file to the hashed values
     * hardcoded into the program. If one is found, the info about it is printed
     * and it is removed from the list.
     *
     * @param hash list of hashed passwords
     * @param path is string representing path to file.
     * @throws java.io.UnsupportedEncodingException
     * @throws java.security.NoSuchAlgorithmException
     */
    public static void getDictionary(LinkedList<String> hash, String path) throws
            UnsupportedEncodingException, NoSuchAlgorithmException {
        System.out.print("Loading dictionary ");
        long startTime = System.currentTimeMillis();
        FileReader fr = null;
        BufferedReader br = null;
        String home = System.getProperty("user.home");
        File absolute = new File(home + path);
        System.out.println(absolute.getAbsolutePath());

        try {
            fr = new FileReader(absolute);
            br = new BufferedReader(fr);
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                String remove = "";
                for (String s : hash) {
                    if (s.equals(md5Hash(currentLine))) {
                        printResult(s, currentLine, (double) (System.currentTimeMillis() - startTime) / 1000);
                        remove = s;
                    }
                }
                hash.remove(remove);
            }
        } catch (IOException e) {
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException ex) {
            }
        }
    }

    /**
     * Prints all info about a password that was found.
     *
     * @param hash hash value to be printed
     * @param pass password to be printed
     * @param time amount of time to be printed
     */
    public static void printResult(String hash, String pass, double time) {
        System.out.println("\nThe password for the hash value "
                + hash + " is \n" + pass + ", and it takes the program " + time
                + " seconds to recover this password.");
    }

}
