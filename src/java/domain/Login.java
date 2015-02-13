/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 *
 * @author kelli
 */
public class Login {

    private static final int SALT_BYTE_SIZE = 32;

    /**
     Create 32 bit random string to use a salt for the hash
     */
    public static String getSalt() {
        byte[] bytes = new byte[SALT_BYTE_SIZE]; //create an array for salt
        try {
            //provide a cryptographically secure random number generator 
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG"); //use SHA-1 message digest algorithm
            random.nextBytes(bytes); //get random salt
        } catch (NoSuchAlgorithmException exception) {
            System.out.println("No such Argument Exception");
        }
        System.out.println("Salt: " + bytes.toString());
        return bytes.toString();
    }

    /**
     Generate a password hash using SHA-256 and salt string
     *  Chances of cracking this: 1 to 2^256 !!!
     */
    public static String getSecurePassword(String password, String salt) {
        String generatedPassword = null;
        try {
            //create message digest  instance for MD5
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            //Add salt bytes to digest
            md.update(salt.getBytes());
            //get the bytes of the hash after digest generates it.
            byte[] bytes = md.digest(password.getBytes());

            //convert the bytes in decimal format to hex.
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException nre) {
            System.out.println("NoSuchArument: " + nre.getLocalizedMessage());
        }
        return generatedPassword;
    }

    /**
     * Steps 1. Retrieve password and salt from source e.g. database 2. Prepend
     * salt to the given password and hash it using the same hash function 3.
     * Compare the hash of the given function with the hash from the database.
     * If a match is found the password is correct else the password is
     * incorrect
     *
     * @param storedPassword
     * @param userPassword
     * @return
     */
    public static boolean validatePassword(String userPassword) {

        boolean validity = false;
        //stored slat and password
        String salt = "[B@13e55db";
        String storedPassHash = "e2134ee650a9620ba56d6131dae62b29f18a069d63db8549b2eb35e891ea4a43";

        //hash the given password using the same algorithm and salt
        String passToCheck = getSecurePassword(userPassword, salt);
        System.out.println("\n================ Validation ===========================\n");
        System.out.println("PassToCheck:  " + passToCheck);
        System.out.println("StoredPassHash:  " + storedPassHash);
        if (passToCheck.equals(storedPassHash)) {
            System.out.println("The Password is OK");
            validity = true;
        } else {
            System.out.println("The Password is False");

        }

        return validity;
    }
}
