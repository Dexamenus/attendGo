package util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionUtils {
    private static final String ALGORITHM = "AES";
    
    // PERBAIKAN: Beri fallback default key jika System.getProperty bernilai null
    private static final String KEY = System.getProperty("KEY") != null ? System.getProperty("KEY") : "attendGo2026Key1";
    private static final byte[] SECRET_KEY = KEY.getBytes(); 
    
    public static String encrypt(String value) {
        try {
            SecretKeySpec spec = new SecretKeySpec(SECRET_KEY, ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, spec);

            byte[] encryptedBytes = cipher.doFinal(value.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            System.err.println("Error saat enkripsi: " + e.getMessage());
            return null;
        }
    }

    public static String decrypt(String encryptedValue) {
        try {
            SecretKeySpec spec = new SecretKeySpec(SECRET_KEY, ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, spec);

            byte[] decodedBytes = Base64.getDecoder().decode(encryptedValue);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            System.err.println("Error saat dekripsi: " + e.getMessage());
            return null;
        }
    }
}