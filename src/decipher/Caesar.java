/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decipher;

/**
 *
 * @author fabio
 */
public class Caesar {
    
    public static String Cipher(String str, int l){
        str = str.toLowerCase();
        String [] par = str.split(" ");
        String cip = "";
        for(String s : par){
            int n = s.length();
            for(int i = 0; i < n; i++){
                int num = s.charAt(i)+l;
                cip+= (char)(((s.charAt(i)+l - 97)%26)+97);
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
                if(num < 97){
                    num = 122;
                }
                res+= (char)(((num - 97)%26)+97);
            }
            res+=" ";
        }
        return res;
    }
    
    
    public static void main(String[] args) {
        System.out.println(Cipher("hola mundo", 1));
        System.out.println(decipher("ipmb nvoep", 1));
        
    }
    
    
}
