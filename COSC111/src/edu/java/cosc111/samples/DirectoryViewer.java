package edu.java.cosc111.samples;

import java.io.IOException;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        Filter<Path> filter = new FileFilter<>();
        if(Files.isDirectory(dir)){
            for(Path p:Files.newDirectoryStream(dir,filter)){
                System.out.println(p);
            }
        }else{
            System.out.println("Not a directory.");
        }
        
    }
    
}
