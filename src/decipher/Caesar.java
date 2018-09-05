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
public class Caesar {
    
    public static String Cipher(String str, int l){
        //str = str.toLowerCase();
        String [] par = str.split(" ");
        String cip = "";
        for(String s : par){
            int n = s.length();
            for(int i = 0; i < n; i++){
                cip+= (char)(s.charAt(i)+l);
            }
            cip+=" ";
        }
        return cip;
    }
    
    
    public static String decipher (String str, int l){
        String res = "";
        String [] par = str.split(" ");
        for (String s : par ){
            int n = s.length();
            for(int i = 0; i < n; i++){
                int num = s.charAt(i)-l;
                
                res+= (char)(num);
            }
            res+=" ";
        }
        return res;
    }
    
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        File f = new File("100P.txt");
        Scanner scan = new Scanner(f);
        ArrayList<String> c = new ArrayList<>();
        while(scan.hasNext()){
            String line = scan.nextLine();
            c.add(Cipher(line,10));
        }
        
        File f2 = new File("cifred10PCaesar.txt");
        BufferedWriter bf = new BufferedWriter(new FileWriter(f2));
        for(String s : c){
            bf.write(s);
            bf.newLine();
        }
        bf.close();       
    }
    
    
}
