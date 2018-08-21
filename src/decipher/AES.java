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
        String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam scelerisque turpis quis neque elementum, ut finibus urna malesuada. Duis non varius libero. Donec a sem non justo scelerisque tempus et id lectus. Maecenas viverra tortor nec mi commodo aliquet. Nulla nec turpis quis velit tristique consectetur. Phasellus pellentesque vestibulum iaculis. Donec sit amet nibh eu urna tristique hendrerit sit amet sit amet augue. Nunc eu velit in risus aliquam accumsan.\n" +
"\n" +
"Integer ullamcorper varius commodo. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vivamus scelerisque nunc id neque bibendum facilisis. Nullam id facilisis risus. Fusce dui dui, euismod at lacus non, molestie cursus tellus. Curabitur felis ligula, lobortis eu purus eu, lobortis commodo lectus. Aenean eu libero viverra, feugiat elit vitae, elementum nisl.\n" +
"\n" +
"Fusce ac dolor metus. Nunc convallis suscipit enim, nec fringilla dolor vehicula imperdiet. Vivamus aliquam venenatis ipsum, vitae tempus nisl suscipit sed. Fusce maximus vulputate rutrum. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras vitae erat mi. Etiam sit amet purus id leo condimentum fringilla. Mauris in sem at tellus posuere gravida et in risus. Quisque pretium erat arcu, egestas sagittis diam placerat sit amet. Proin condimentum neque lectus, et molestie lacus lobortis at.\n" +
"\n" +
"Quisque tincidunt nibh nec dolor imperdiet, vel suscipit tortor viverra. Vivamus non ligula accumsan, ultrices nisl quis, fringilla mauris. Curabitur lobortis finibus pulvinar. Quisque neque ante, feugiat ut risus id, eleifend auctor risus. Cras sit amet luctus elit. Sed efficitur, nisi at dignissim venenatis, metus nunc bibendum sem, convallis consectetur est odio ac lectus. Nulla tempus nisl in velit lacinia dictum. Sed suscipit, sapien non tristique tempor, ipsum elit feugiat mauris, eget rutrum turpis velit eget dui. Cras posuere et ex sed consectetur. Quisque rutrum tellus non porta ultricies. Cras in sagittis urna, eu imperdiet lectus. Fusce viverra ex lorem, id tempus velit consequat vitae.\n" +
"\n" +
"Duis dapibus ipsum sit amet leo placerat tristique. Integer pharetra eleifend sapien at viverra. Ut iaculis leo nec arcu ultrices, et bibendum eros mattis. Donec fringilla, ligula vitae scelerisque mattis, sapien dui dignissim nisl, nec cursus urna est ut tortor. Pellentesque iaculis magna non lobortis vehicula. Nam in sollicitudin justo. Proin cursus faucibus feugiat. Aliquam tincidunt fringilla quam ultrices aliquam. Donec at iaculis nisi. Donec placerat facilisis interdum. Vestibulum semper nisi eu lorem vulputate commodo. Pellentesque vel volutpat dui, quis fermentum felis. Pellentesque vitae convallis purus.";
        String enc = encrypt(text, k);
        System.out.println("Enc: "+enc);
        File f = new File("llave.pub");
        Key k2 = fileToKey(f);
        System.out.println(decrypt(enc, k2));
        
    }
}
