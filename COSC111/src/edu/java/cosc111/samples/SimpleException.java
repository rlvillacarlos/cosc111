/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.java.cosc111.samples;

/**
 *
 * @author Russel
 */
public class SimpleException {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        f1();
    }

    private static void f1(){
        f2();
    }
    
    private static void f2(){
        try{
            int[] data = new int[10];
            for (int i = 0; i <=data.length;i++) {
                data[i] = i;
            }
        }catch(Exception ex){
            System.out.println("Problematic index: " + ex.getMessage());
            System.out.println("Type: " + ex.getClass().getSimpleName());
        }

    }
    
}
