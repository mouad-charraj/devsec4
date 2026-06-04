package com.example.securestorage.database;

import android.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordHasher {

    private static final int ITERATIONS_mouad = 10000;
    private static final int KEY_LENGTH_mouad = 256;
    private static final int SALT_LENGTH_mouad = 16;

    public static String hashPassword(String password_mouad) {
        java.security.SecureRandom random_mouad = new java.security.SecureRandom();
        byte[] salt_mouad = new byte[SALT_LENGTH_mouad];
        random_mouad.nextBytes(salt_mouad);

        byte[] hash_mouad = pbkdf2_mouad(password_mouad.toCharArray(), salt_mouad, ITERATIONS_mouad, KEY_LENGTH_mouad);
        
        String saltBase64_mouad = Base64.encodeToString(salt_mouad, Base64.NO_WRAP);
        String hashBase64_mouad = Base64.encodeToString(hash_mouad, Base64.NO_WRAP);

        return saltBase64_mouad + ":" + hashBase64_mouad;
    }

    public static boolean verifyPassword(String password_mouad, String storedHash_mouad) {
        if (storedHash_mouad == null || !storedHash_mouad.contains(":")) return false;

        String[] parts_mouad = storedHash_mouad.split(":");
        if (parts_mouad.length != 2) return false;

        try {
            byte[] salt_mouad = Base64.decode(parts_mouad[0], Base64.NO_WRAP);
            byte[] hash_mouad = Base64.decode(parts_mouad[1], Base64.NO_WRAP);
            byte[] testHash_mouad = pbkdf2_mouad(password_mouad.toCharArray(), salt_mouad, ITERATIONS_mouad, KEY_LENGTH_mouad);

            int diff_mouad = hash_mouad.length ^ testHash_mouad.length;
            for (int i_mouad = 0; i_mouad < hash_mouad.length && i_mouad < testHash_mouad.length; i_mouad++) {
                diff_mouad |= hash_mouad[i_mouad] ^ testHash_mouad[i_mouad];
            }
            return diff_mouad == 0;
        } catch (Exception e_mouad) {
            return false;
        }
    }

    private static byte[] pbkdf2_mouad(char[] password_mouad, byte[] salt_mouad, int iterations_mouad, int keyLength_mouad) {
        PBEKeySpec spec_mouad = new PBEKeySpec(password_mouad, salt_mouad, iterations_mouad, keyLength_mouad);
        try {
            SecretKeyFactory skf_mouad = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            return skf_mouad.generateSecret(spec_mouad).getEncoded();
        } catch (Exception e_mouad) {
            return new byte[0];
        } finally {
            spec_mouad.clearPassword();
        }
    }
}
