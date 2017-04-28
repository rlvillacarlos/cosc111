package edu.java.cosc111.samples;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 *
 * @author russel
 */
public class DirectoryDeleter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.print("Path to delete: ");
        Scanner in = new Scanner(System.in);
        Path p = Paths.get(in.nextLine());
        try{
            DirectoryTreeDeleter.deleteDirectoryTree(p);
        }catch(Exception ex){
            System.out.println("An error occured.");
        }
        
    }
    
}
