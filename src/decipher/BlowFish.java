/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decipher;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author fabio
 */
public class BlowFish {
    
        public static String bytesToString(byte[] b) {
        byte[] b2 = new byte[b.length + 1];
        b2[0] = 1;
        System.arraycopy(b, 0, b2, 1, b.length);
        return new BigInteger(b2).toString(36);
    }
    
    
    public static  String encrypt(String str, Key KS) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
               // create a key generator based upon the Blowfish cipher
        KeyGenerator keygenerator = KeyGenerator.getInstance("Blowfish");

        // create a key

        // create a cipher based upon Blowfish
        Cipher cipher = Cipher.getInstance("Blowfish");

        // initialise cipher to with secret key
        cipher.init(Cipher.ENCRYPT_MODE, KS);

        // get the text to encrypt
        String inputText = "MyTextToEncrypt";

        // encrypt message
        byte[] encrypted = cipher.doFinal(inputText.getBytes());
        String encryptedData =new String(encrypted);
        return  encryptedData;
    }
    
    
    
    public static String decrypt (String str, Key key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        Cipher c = Cipher.getInstance("Blowfish");
        System.out.println(c.getBlockSize());
        c.init(Cipher.DECRYPT_MODE, key);
        
        byte[] decValue = c.doFinal(str.getBytes());
        return  new String (decValue);
    }
    
    
    public static void main(String[] args) throws NoSuchAlgorithmException {
            try {
                String s = "Este es el mensa";
                
                String Key = "KEY";
                byte[] KeyData = Key.getBytes();
                SecretKeySpec KS = new SecretKeySpec(KeyData, "Blowfish");
                String encrypted = encrypt(s,KS);
                System.out.println("enc: "+ encrypted);
                String dec = decrypt(s,KS);
                System.out.println("mensaje: "+dec);
            } catch (NoSuchPaddingException ex) {
                Logger.getLogger(BlowFish.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeyException ex) {
                Logger.getLogger(BlowFish.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalBlockSizeException ex) {
                Logger.getLogger(BlowFish.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BadPaddingException ex) {
                Logger.getLogger(BlowFish.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    
    
}
