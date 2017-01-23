/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.java.cosc111.samples;

/**
 *
 * @author russel
 */
public class IntegerToBytes {
    public static void main(String[] args) {
        int a = 8190;
        byte[] b= new byte[4];
        
        System.out.println(0x000000FF & a);
        System.out.println(0x000000FF & (a >> 8));
        System.out.println(0x000000FF & (a >> 16));
        System.out.println(0x000000FF & (a >> 24));
        
        for(int i =0;i<3;i++){
        
        }
        "Hello".getBytes();
        
    }
}
