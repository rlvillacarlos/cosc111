package edu.java.cosc111.samples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author rlvillacarlos
 */
public class SimpleTextFileReadAndWrite {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        
        System.out.print("Enter path: ");
        Path p = Paths.get(in.nextLine());
        List<String> data = Arrays.asList("1,2,3,4,5,6,7,8,9,10".split(",")) ;
        if(Files.exists(p)){
            System.out.printf("The file %s already exists%n", p.toAbsolutePath());
            System.out.print("Do you want to append? (y/n)");
            
            if(in.nextLine().substring(0, 1).equalsIgnoreCase("y")){
                System.out.println("--Writing Data--");
                Files.write(p, data,StandardOpenOption.APPEND);    
            }
        }else{
            System.out.println("--Writing Data--");
            Files.write(p, data);        
        }
        System.out.println("--Reading Data--");        
        List<String> contents = Files.readAllLines(p);
        for(String c:contents){
            System.out.println(c);
        }
    }
    
}
