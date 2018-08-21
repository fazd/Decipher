/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decipher;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
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
    

    public static void main(String[] args) throws Exception {

        //Definimos un texto a cifrar
        String str = "Diseñar un procesador de descifrador de textos que permita escoger entre varios métodos de encriptación para des-encriptar un texto ya sea introducido en la consola o agregado desde un archivo.";

        System.out.println("\nTexto a cifrar:");
        System.out.println(str);

        //Instanciamos la clase
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
        System.out.println(unsecure);
    }

}
