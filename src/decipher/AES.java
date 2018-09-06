/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decipher;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Key;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author fabio
 */
public class AES {
    private static final String ALGO = "AES";
    

    /**
     * Encrypt a string with AES algorithm.
     *
     * @param data is a string
     * @return the encrypted string
     */
    public static String encrypt(String data, Key key ) throws Exception {
        Cipher c = Cipher.getInstance(ALGO);
        keyToFile(key, "llave.pub");
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encVal);
    }

    /**
     * Decrypt a string with AES algorithm.
     *
     * @param encryptedData is a string
     * @return the decrypted string
     */
    public static String decrypt(String encryptedData, Key key) throws Exception {
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = Base64.getDecoder().decode(encryptedData);
        byte[] decValue = c.doFinal(decordedValue);
        return new String(decValue);
    }
    
    
    /**
     * Generate a new encryption key.
     */
    public static Key generate(String s) throws Exception {
        byte [] b = new byte [s.length()];
        for(int i = 0; i < s.length(); i++){
            b[i] = (byte) s.charAt(i);
            
        }
        Key k = new SecretKeySpec(b, ALGO);
        
        return k;
    }
    
    
    public static Key fileToKey (File f) throws FileNotFoundException{
        Scanner scan = new Scanner(f);
        byte vec [] = new byte [16];
        int i = 0;
        while(scan.hasNext()){
            String linea = scan.nextLine();
            byte b = (byte) linea.charAt(0);
            vec[i] = b;
            i++;
        }
        return new SecretKeySpec(vec, ALGO);
    }
    
    public static void keyToFile(Key k, String path) throws IOException{
        File f = new File(path);
        BufferedWriter bf = new BufferedWriter(new FileWriter(f));
        byte b []  = k.getEncoded();
        for(int i = 0; i < b.length; i++){
            bf.write(b[i]);
            bf.newLine();
        }
        bf.close();
    }


    
    public static void main(String[] args) throws Exception {
        Key k  = generate("ABCDEFGIJKLOJKHG");
        File f = new File("100P.txt");
        Scanner scan = new Scanner(f);
        String res = "";
        while(scan.hasNext()){
            res+=scan.nextLine();
        }
        
        String enc = encrypt(res, k);
        File f2 = new File("100PAES.txt");
        BufferedWriter bf = new BufferedWriter(new FileWriter(f2));
        bf.write(enc);
        bf.close();
        File llave = new File("llave.pub");
        Key k2 = fileToKey(llave);
        System.out.println(decrypt(enc, k2));
        
    }
}
