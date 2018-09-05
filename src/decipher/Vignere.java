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
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author fabio
 */
public class Vignere {
    
    public  static String cipher(String str, String key){
        //str = str.toLowerCase();
        //key = key.toLowerCase();
        String vec [] = str.split(" ");
        int c = 0, kl = key.length();
        String res="";
        
        for(String s: vec){
            int n = s.length();
            for(int i = 0; i < n; i++){
                int car = 48+ ((s.charAt(i) + key.charAt(c) )%74);
                
                //System.out.println("car:"+car);
                c = (c+1)%kl;
                //System.out.println("car1:"+car);
                res += (char)(car);
                
            }
            res+=" ";
        }
        System.out.println(res);
        return res;
    }
    
    public static String decipher(String str, String key){
        //str = str.toLowerCase();
        //key = key.toLowerCase();
        String vec [] = str.split(" ");
        int c = 0, kl = key.length();
        String res="";
        
        for(String s: vec){
            int n = s.length();
            for(int i = 0; i < n; i++){
               // System.out.println("s: "+s.charAt(i)+" k: "+key.charAt(c));
                
               
                int car = s.charAt(i) -48- key.charAt(c);
                
                if(car<0){
                    car+=74;
                }
                c = (c+1)%kl;
                res+=(char)car;
                //System.out.println("carD:"+car);
                
            }
            res+=" ";
        }
        System.out.println(res);
        return res;
    }
    
    
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String k = cipher("Hola mundo! esto tiene muchos, signos.", "Fazd.");
        String k2 = decipher(k,"Fazd.");
        /*File f = new File("100P.txt");
        Scanner scan = new Scanner(f);
        ArrayList<String> c = new ArrayList<>();
        while(scan.hasNext()){
            String line = scan.nextLine();
            c.add(cipher(line,"Fazd"));
        }
        
        File f2 = new File("cifred100PVigenere.txt");
        BufferedWriter bf = new BufferedWriter(new FileWriter(f2));
        for(String s : c){
            bf.write(s);
            bf.newLine();
        }
        bf.close();  */ 
    }
    
}
