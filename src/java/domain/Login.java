/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.entities.User;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author kelli
 */
public class Login {

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static final int SALT_BYTE_SIZE = 32;

    
    public static void connectToDB() {
        emf = Persistence.createEntityManagerFactory("tracePU");
        em = emf.createEntityManager();
        System.out.println("--------------Connected to database-----------------");
    }

    /**
     * Create 32 bit random string to use a salt for the hash
     */
    private static String getSalt() {
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
     * Generate a password hash using SHA-256 and salt string Chances of
     * cracking this: 1 to 2^256 !!!
     */
    private static String getSecurePassword(String password, String salt) {
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

    public static boolean validateUser(String user, String password) {

        
        Login.connectToDB();
        boolean valid = false;
        /**
         * Steps: 1. Retrieve user from the database using the supplied username
         * 2. Get the details of the user including the hash and salt values. 3.
         * Create a hash value from the supplied password and compare with the
         * stored value 4. If the generated hashes is similar to the stored
         * hash, then the user is validated.
         */
        Query query = em.createNamedQuery("User.findByUserName");
        query.setParameter("userName", user);
        User u = (User) query.getSingleResult();

        String storedHash = u.getPassword();
        String storedSalt = u.getSaltVal();

        //hash the given password using the same algorithm and salt
        String generatedHash = getSecurePassword(password, storedSalt);
        if (generatedHash.equals(storedHash)) {
            valid = true;
        }

        return valid;
    }

}
