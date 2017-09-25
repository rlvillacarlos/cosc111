/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.java.cosc111.samples.oop;

/**
 *
 * @author russel
 */
public class ExceptionSample1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        function1(12, 0);
    }
    
    public static void function1(Integer int1, Integer int2){
        System.out.println(function2(int1, int2));
//        try{
//            
//        }catch(ArithmeticException ex){
//            throw new IllegalArgumentException("Argument 2 is 0");
//        }finally{
//            System.out.println("Bye");
//        }
        
    }
    
    public static int function2(Integer int1, Integer int2){
        return int1/int2;
    }
    
}
