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
public class Vignere {
    
    public  static String cipher(String str, String key){
        str = str.toLowerCase();
        key = key.toLowerCase();
        String vec [] = str.split(" ");
        int c = 0, kl = key.length();
        String res="";
        
        for(String s: vec){
            int n = s.length();
            for(int i = 0; i < n; i++){
                int car = (s.charAt(i) - 97 + key.charAt(c) - 97)%26;
                c = (c+1)%kl;
                res += (char)(car+97);
            }
            res+=" ";
        }
        System.out.println(res);
        return res;
    }
    
    public static String decipher(String str, String key){
        str = str.toLowerCase();
        key = key.toLowerCase();
        String vec [] = str.split(" ");
        int c = 0, kl = key.length();
        String res="";
        
        for(String s: vec){
            int n = s.length();
            for(int i = 0; i < n; i++){
                System.out.println("s: "+s.charAt(i)+" k: "+key.charAt(c));
                
                int car = s.charAt(i) - key.charAt(c);
                if(car <0){
                    car +=26;
                }
                car = car%26;
                c = (c+1)%kl;
                res += (char)(car+97);
            }
            res+=" ";
        }
        System.out.println(res);
        return res;
    }
    
    
    
    public static void main(String[] args) {
        String k = cipher("MICHIGAN TECHNOLOGICAL UNIVERSITY", "HOUGHTON");
        String k2 = decipher("twwnpzoa aswnuhzbnwwgs nbvcslypmm", "HOUGHTON");
    }
    
}
