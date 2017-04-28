package edu.java.cosc111.samples;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 *
 * @author russel
 */
public class DirectoryCopier {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.print("Source directory: ");
        Scanner in = new Scanner(System.in);
        Path src = Paths.get(in.nextLine());
        System.out.print("Destination directory: ");
        Path dest = Paths.get(in.nextLine());
        try{
            DirectoryTreeCopier.copyDirectoryTree(src, dest);
        }catch(Exception ex){
            System.out.println("An error occured.");
        }
        
    }
    
}
