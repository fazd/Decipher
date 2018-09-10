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
import java.math.BigInteger;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author fabio
 */
public class Prueba {
    
    private static BigInteger ZERO = new BigInteger("0");
    private static BigInteger ONE = new BigInteger("1");
    private static BigInteger TWO = new BigInteger("2");
    private static BigInteger clavePublica [] = new BigInteger[2];
    private static BigInteger clavePrivada [] = new BigInteger[2];
    
    
    public static BigInteger getPhi(BigInteger p, BigInteger q){
        return (p.subtract(ONE)).multiply((q.subtract(ONE)));
    }
    
    public static BigInteger gcd(BigInteger a , BigInteger b){
        if(b.compareTo(ZERO)==0){
            return a;
        }
        else{
            return gcd(b,a.mod(b));
        }
    }
    
    
    public static BigInteger getK(BigInteger phi){
        BigInteger k = new BigInteger("7");
        while(gcd(k,phi).compareTo(ONE)!=0){
            k= k.add(TWO);
        }
        return k;
    }
    
    public static BigInteger getJ (BigInteger k,BigInteger z){
        BigInteger j = TWO;
        while((j.multiply(k)).mod(z).compareTo(ONE)!=0){
            j = j.add(ONE);
        }
        return j;
    }
    
    public static void genKey(BigInteger p, BigInteger q){
        try {
            BigInteger phi,k,j,n;
            n = p.multiply(q);
            phi = getPhi(p,q);
            k = getK(phi);
            j = getJ(k,phi);
            clavePublica[0] = n;
            clavePublica[1] = k;
            clavePrivada[0]= n;
            clavePrivada[1]= j;
            saveKeys();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,"El archivo no pudo ser guardado",
                    "Error",JOptionPane.ERROR_MESSAGE); 
        }
    }
    
    public static void saveKeys() throws IOException{
        File f = new File("ClavePub.pub");
        BufferedWriter bf = new BufferedWriter(new FileWriter(f));
        bf.write(clavePublica[0].toString());
        bf.newLine();
        bf.write(clavePublica[1].toString());
        bf.close();
        System.out.println("La clave publica se guardó en "+ f.getAbsolutePath());
        f = new File("ClavePri.pri");
        bf = new BufferedWriter(new FileWriter(f));
        bf.write(clavePrivada[0].toString());
        bf.newLine();
        bf.write(clavePrivada[1].toString());
        bf.close();
        System.out.println("La clave privada se guardó en "+ f.getAbsolutePath());
        
    }
    
    public static void uploadPrivateKey(File f){
        try {
            Scanner scan = new Scanner(f);
            String linea = scan.nextLine();
            clavePrivada[0] = new BigInteger(linea);
            linea = scan.nextLine();
            clavePrivada[1] = new BigInteger(linea);
            
            
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "El Archivo no permite lectura",
                    "Error",JOptionPane.ERROR_MESSAGE);
        }
        
    }
    public static void uploadPublicKey(File f){
        try {
            Scanner scan = new Scanner(f);
            String linea = scan.nextLine();
            clavePublica[0] = new BigInteger(linea);
            linea = scan.nextLine();
            clavePublica[1] = new BigInteger(linea);
            
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "El Archivo no permite lectura",
                    "Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    public static void saveFile(String mess,String path) throws IOException{
        File f = new File(path);
        BufferedWriter bf = new BufferedWriter(new FileWriter(f));
        String m [] = mess.split("\n");
        for(String s: m){
            bf.write(s);
            bf.newLine();
        }
        bf.close();
        
    }
    
    public static String encrypt(File f, File pub) throws FileNotFoundException, IOException{
        Scanner scan = new Scanner(f);
        String res = "";
        while(scan.hasNext()){
            String linea = scan.nextLine();
            //System.out.println("Linea:"+linea);
            if(!linea.equals("")){
                String en = encrypt(linea, pub);
                res += en + "\n";
            }
            else{
                res +="\n";
            }
        }
        saveFile(res,"enc.txt");
        return res;
    }
    
    public static String encrypt(String mensaje, File pub) throws FileNotFoundException{
        BigInteger n,k;
        uploadPublicKey(pub);
        n = clavePublica[0];
        k = clavePublica[1];
        //System.out.println("N="+n);
        //System.out.println("K="+k);
        
        int i;
        byte[] temp = new byte[1];
        byte[] digitos = mensaje.getBytes();
        BigInteger[] bigdigitos = new BigInteger[digitos.length];
        
        for(i=0; i<bigdigitos.length;i++){
            temp[0] = digitos[i];
            bigdigitos[i] = new BigInteger(temp);
        }
        
        BigInteger[] encriptado = new BigInteger[bigdigitos.length];
        
        for(i=0; i<bigdigitos.length; i++)
            encriptado[i] = bigdigitos[i].modPow(k,n);
        
        
        String en ="";
        for (int j = 0; j < encriptado.length-1; j++) {
            en = en + encriptado[j]+"#";
        }
        en = en + encriptado[encriptado.length-1];
        
        return en;
    }
    
    
    public static String decrypt (File f, File pri) throws FileNotFoundException{
        Scanner scan = new Scanner(f);
        String res = "";
        while(scan.hasNext()){
            String linea = scan.nextLine();
            //System.out.println("Lineadc:"+linea);
            String en = "";
            if(!linea.equals("")){
                en = decrypt(linea, pri);
            
            }
            res += en + "\n";
            
        }
        return res;
    }
    
    public static  String decrypt(String message, File pri) {
        uploadPrivateKey(pri);
        BigInteger n,j;
        n = clavePrivada[0];
        j = clavePrivada[1];
        
        String l [] =  message.split("#");
        BigInteger encrypt [] = new BigInteger[l.length];
        for (int i = 0; i < l.length; i++) {
            encrypt[i] = new BigInteger(l[i]);
        }
        
        
        BigInteger[] desencriptado = new BigInteger[encrypt.length];
        
        for(int i=0; i<desencriptado.length; i++){
            desencriptado[i] = encrypt[i].modPow(j,n);
            //System.out.println(desencriptado[i]);
        }
        char[] charArray = new char[desencriptado.length];
        
        for(int i=0; i<charArray.length; i++){
            charArray[i] = (char) (desencriptado[i].intValue());
        }
        
        return(new String(charArray));
    }
    
     
     
     public static void main(String[] args) throws FileNotFoundException, IOException {
        genKey(new BigInteger("907"), new BigInteger("911"));
        
        File f = new File("100P.txt");
        
        File cpub = new File("ClavePub.pub");
        File cpri = new File("ClavePri.pri");
        
        String encriptado = encrypt(f,cpub);
        //System.out.println("enc:"+encriptado);
        File f2 = new File("enc.txt");
        String nuevo = decrypt(f2,cpri);
        System.out.println("nuevo: "+nuevo);
    }
    
    
}
