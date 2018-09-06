/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decipher;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author fabio
 */
public class BlowFish {

    public static String encrypt(String info, String pass) throws Exception {
        byte[] keyData = (pass).getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyData, "Blowfish");
        Cipher cipher = Cipher.getInstance("Blowfish");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] hasil = cipher.doFinal(info.getBytes());
        return new BASE64Encoder().encode(hasil);
    }
     
    public static String decrypt(String info, String pass) throws Exception {
        byte[] keyData = pass.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyData, "Blowfish");
        Cipher cipher = Cipher.getInstance("Blowfish");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] hasil = cipher.doFinal(new BASE64Decoder().decodeBuffer(info));
        return new String(hasil);
    }
 
    public static void main(String[] args) throws  Exception {
        
        File f = new File("100P.txt");
        Scanner scan = new Scanner(f);
        String res = "";
        while(scan.hasNext()){
            res += scan.nextLine();
        }
        
        
        
        String key = "Fazd";
        // Check if the user wants to encrypt or decrypt
        String enc = encrypt(res,key);
        File f2 = new File("cifred100PBlow.txt");
        BufferedWriter bf = new BufferedWriter(new FileWriter(f2));
        bf.write(enc);
        bf.close();
        
        
        System.out.println("enc:"+enc);
        System.out.println(decrypt(enc, key));
        

    }
}
