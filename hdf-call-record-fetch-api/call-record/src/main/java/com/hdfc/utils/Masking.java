/* Decompiler 38ms, total 348ms, lines 56 */
package com.hdfc.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Masking {
   private static SecretKeySpec secretKey;
   private static byte[] key;

   public static void setKey(String myKey) {
      MessageDigest sha = null;

      try {
         key = myKey.getBytes("UTF-8");
         sha = MessageDigest.getInstance("SHA-1");
         key = sha.digest(key);
         key = Arrays.copyOf(key, 16);
         secretKey = new SecretKeySpec(key, "AES");
      } catch (NoSuchAlgorithmException var3) {
         var3.printStackTrace();
      } catch (UnsupportedEncodingException var4) {
         var4.printStackTrace();
      }

   }

   public static String encrypt(String strToEncrypt, String secret) {
      try {
         setKey(secret);
         Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
         cipher.init(1, secretKey);
         return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
      } catch (Exception var3) {
         System.out.println("Error while encrypting: " + var3.toString());
         return null;
      }
   }

   public static String decrypt(String strToDecrypt, String secret) {
      try {
         setKey(secret);
         Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
         cipher.init(2, secretKey);
         return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
      } catch (Exception var3) {
         System.out.println("Error while decrypting: " + var3.toString());
         return null;
      }
   }
}
