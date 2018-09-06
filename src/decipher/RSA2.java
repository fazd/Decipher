/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decipher;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author fabio
 */
public class RSA2 {

    public static String encrypt(String str) throws BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, IOException, InvalidKeySpecException, UnsupportedEncodingException, NoSuchProviderException {
        RSA rsa = new RSA();

        //Generamos un par de claves
        //Admite claves de 512, 1024, 2048 y 4096 bits
        rsa.genKeyPair(4096);

        String file_private = "rsa.pri";
        String file_public = "rsa.pub";

        //Las guardamos asi podemos usarlas despues
        //a lo largo del tiempo
        rsa.saveToDiskPrivateKey("rsa.pri");
        rsa.saveToDiskPublicKey("rsa.pub");

        //Ciframos y e imprimimos, el texto cifrado
        //es devuelto en la variable secure
        String secure = rsa.Encrypt(str);

        System.out.println("\nCifrado:");
        System.out.println(secure);
        return secure;
    }
    
    public static String decipher(String str, String pathPublic, String pathPrivate) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        RSA rsa = new RSA();

        //A diferencia de la anterior aca no creamos
        //un nuevo par de claves, sino que cargamos
        //el juego de claves que habiamos guadado
        rsa.openFromDiskPrivateKey(pathPrivate);
        rsa.openFromDiskPublicKey(pathPublic);

        //Le pasamos el texto cifrado (secure) y nos 
        //es devuelto el texto ya descifrado (unsecure) 
        String unsecure = rsa.Decrypt(str);

        //Imprimimos
        System.out.println("\nDescifrado:");
        System.out.println(unsecure);
        return unsecure;
    } 
    
     public static String decipher(File f, String pathPublic, String pathPrivate) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        RSA rsa = new RSA();

        rsa.openFromDiskPrivateKey(pathPrivate);
        rsa.openFromDiskPublicKey(pathPublic);

        
        Scanner scan = new Scanner(f);
        String res = "";
        while(scan.hasNext()){
            String linea = scan.nextLine();
            if(linea.length() == 0){
                res+="\n";
                continue;
            }
            linea = rsa.Decrypt(linea);
            res+= linea;
        }
         System.out.println("res__");
         System.out.println(res);
        return res;
    } 
    
    

    public static void main(String[] args) throws Exception {

        
        File f = new File("100P.txt");
        Scanner scan = new Scanner(f);
        ArrayList<String> parr = new ArrayList<>();
        while(scan.hasNext()){
            String linea = scan.nextLine();
            byte [] h = linea.getBytes();
            //System.out.println("Linea:"+linea.getBytes().length);
            if(h.length>500){
                int tam = h.length;
                int cont = 0;
                while(tam>0){
                    int lim =0;
                    if(tam > 500){
                        lim = 500;
                    }
                    else{
                        lim = tam;
                    }
                    byte  h1 [] = new byte [lim];
                    for(int i = cont; i < cont + lim; i++) h1[i-cont] = h[i];
                    String s1 = new String(h1);;
                    parr.add(s1);
                    cont = cont + lim;
                    tam-= cont;
                
                }
                
                parr.add("\n");
            }
            else{
                parr.add(linea);
            }
        }
        
        //Instanciamos la clase
        RSA rsa = new RSA();

        //Generamos un par de claves
        //Admite claves de 512, 1024, 2048 y 4096 bits
        System.out.println("Pasa");
        rsa.genKeyPair(4096);

        String file_private = "rsa.pri";
        String file_public = "rsa.pub";

        //Las guardamos asi podemos usarlas despues
        //a lo largo del tiempo
        rsa.saveToDiskPrivateKey("rsa.pri");
        rsa.saveToDiskPublicKey("rsa.pub");

        //Ciframos y e imprimimos, el texto cifrado
        //es devuelto en la variable secure
        System.out.println("Empieza");
        ArrayList<String> encrypted = new  ArrayList<>();
        for(String s : parr){
            if(s.equals("\n")){
               encrypted.add("\n");
            }
            else{
                //System.out.println("s:"+s.getBytes().length);
                String secure = rsa.Encrypt(s);
                encrypted.add(secure);
            }
            
        }
        System.out.println("Comienza descifrado");
        File f2 = new File("100PRSA.txt");
        BufferedWriter bf = new BufferedWriter(new FileWriter(f2));
        for(String s : encrypted){
            bf.write(s);
            bf.newLine();
        }
        bf.close();
        
        decipher(f2,file_public,file_private);
        
        /*
        //A modo de ejemplo creamos otra clase rsa
        RSA rsa2 = new RSA();

        //A diferencia de la anterior aca no creamos
        //un nuevo par de claves, sino que cargamos
        //el juego de claves que habiamos guadado
        rsa2.openFromDiskPrivateKey("rsa.pri");
        rsa2.openFromDiskPublicKey("rsa.pub");

        //Le pasamos el texto cifrado (secure) y nos 
        //es devuelto el texto ya descifrado (unsecure) 
        String unsecure = rsa2.Decrypt(secure);

        //Imprimimos
        System.out.println("\nDescifrado:");
        System.out.println(unsecure);*/
    }

}
