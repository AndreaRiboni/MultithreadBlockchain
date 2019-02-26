/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiblockchain;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author andrerib
 */
public class Crypto {
    
    /**
     * Crittografia SHA256
     * @param input Stringa da crittografare
     * @return stringa crittografata con l'algoritmo SHA256
     */
    public static String SHA256(String input){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder HexString = new StringBuilder();
            for(int i = 0; i < hash.length; i++){
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) HexString.append('0');
                HexString.append(hex);
            }
            return HexString.toString();
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e){
            System.out.println("Error");
        }
        return null;
    }
    
    public static String getRandomMessage(){
        String[] nomi = {
           "Andrea",
           "Simone",
           "Stefano",
           "Simone",
           "Marta",
           "Rosa",
           "Marco",
           "Sara"
        };
        return nomi[(int)(Math.random()*nomi.length)]+" paga "+nomi[(int)(Math.random()*nomi.length)]+" "+(int)((Math.random()*100)+1)+" euro.";
    }
}
