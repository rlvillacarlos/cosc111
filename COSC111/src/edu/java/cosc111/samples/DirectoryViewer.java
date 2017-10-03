package edu.java.cosc111.samples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Calendar;
import java.util.Scanner;

/**
 *
 * @author russel
 */
public class DirectoryViewer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Scanner cin = new Scanner(System.in);
        System.out.print("Directory Path:");
        Path dir = Paths.get(cin.nextLine());
       
        if(Files.isDirectory(dir)){
            for(Path p:Files.newDirectoryStream(dir)){
                BasicFileAttributes attr = Files.readAttributes(p, BasicFileAttributes.class);
                System.out.printf("%s   %s%n", p,attr.creationTime().toString());
            }
        }else{
            System.out.println("Not a directory.");
        }
        
    }
    
}
