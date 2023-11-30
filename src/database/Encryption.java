package src.database;
import src.database.User;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Encryption {

    private static final int SALT_LENGTH = 16; // Adjust the salt length as needed

    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return bytesToHex(salt);
    }

    // Redundant method, can be deleted

    // public static String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
    //     String saltedPassword = password + salt;

    //     MessageDigest digest = MessageDigest.getInstance("SHA-256");
    //     byte[] hashedBytes = digest.digest(saltedPassword.getBytes());

    //     return bytesToHex(hashedBytes);
    // }

    // Can be used to hash any string, not just passwords
    public static String generateHash(String inputString, String salt) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String saltedInput = inputString + salt;
        byte[] hashedBytes = digest.digest(saltedInput.getBytes());
        return bytesToHex(hashedBytes);

    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexStringBuilder = new StringBuilder();
        for (byte aByte : bytes) {
            hexStringBuilder.append(String.format("%02x", aByte));
        }
        return hexStringBuilder.toString();
    }

}