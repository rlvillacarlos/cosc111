/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.java.cosc111.samples;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author RLVillacarlos
 */
public class RandomIdGeneratorTest {
    private static final int MAX_DIGITS = RandomIntegerIDGenerator.MAX_DIGITS;
    
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);

        System.out.printf("Number of digits (min = 1,max = %d): ",MAX_DIGITS);
        int numDigits = in.nextInt();
        in.nextLine();

        if(numDigits<1 || numDigits>MAX_DIGITS){
            System.out.println("Invalid number of digits.");
            return;
        }
        
        int maxId = (numDigits<MAX_DIGITS?((int)Math.pow(10, numDigits)) - 1:Integer.MAX_VALUE);        
        
        System.out.printf("Start ID number (min = 1; max = %d): ",maxId);
        int idStart = in.nextInt();
        in.nextLine();
        
        if(idStart < 1 || idStart > maxId){
            System.out.println("Invalid start ID value.");
            return;            
        }
        
        int maxNumId = maxId - idStart + 1; 
        
        System.out.printf("Number of ID to generate (min = 1, max = %d): ",maxNumId);
        int numIds = in.nextInt();
        in.nextLine();

        if(numIds < 0 || numIds > maxNumId){
            System.out.println("Invalid number of ID to generate.");
            return;            
        }
        
        System.out.print("Path to store IDs: ");
        Path p = Paths.get(in.nextLine());
        
        /*
            Create all the necessary directories in the path if they do not exists.
            We use getParent() since the last element of the path is considered
            as the filename.
        */
        Files.createDirectories(p.getParent());
        
        try(PrintWriter out = new PrintWriter(Files.newBufferedWriter(
                                    p, StandardCharsets.UTF_8,
                                        StandardOpenOption.CREATE,
                                        StandardOpenOption.TRUNCATE_EXISTING))){
            
            //Write the number of id as the first line of the file.
            out.println(numIds);
            
            //Generate unique random ids
            List<Integer> ids = RandomIntegerIDGenerator.generate(numDigits, idStart, numIds);
            
            /*
              0 padded integer format. Each integer has width(number of digits) 
              equal to the number of digits provided earlier
            */
            
            String format = "%1$0"+numDigits+"d%n";
            for(Integer id:ids){
                
                /*
                    Write the random id in the file with the given format.
                */
                out.printf(format, id);
            }
        }
        System.out.println("Done.");
    }
}
