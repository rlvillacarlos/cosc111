/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.java.cosc111.samples.net;

/**
 *
 * @author Russel
 */
public class TestBytes {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        byte[] data= new byte[4];
        int x = Integer.MAX_VALUE;
        System.out.println(x);
//        int step = 0;
        do{
            System.out.println(x & 0x000000FF);
            x = x >> 8;
        }while(x>0);
        data[0] = (byte)(x & 0xFF);
        data[1] = (byte)((x>>8) & 0xFF);
        data[2] = (byte)((x>>16) & 0xFF);
        data[3] = (byte)((x>>24) & 0xFF);
        
        
        
        
        System.out.println(x & 0x000000FF);
        System.out.println((x>>8) & 0x000000FF);
        System.out.println((x>>16) & 0x000000FF);
        System.out.println((x>>24) & 0x000000FF);
        
        System.out.println(
                data[0] & 0xFF + 
                ((data[1]<<8) & 0xFF00)  + 
                ((data[2]<<16) & 0xFF0000)+
                ((data[3]<<24) &0xFF000000));
    }
    
}
