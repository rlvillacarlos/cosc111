package edu.java.cosc111.samples;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;

public class DeepestDirectoryFinder {
    private static long size = 0;
    
    public static class DirectoryVisitor extends SimpleFileVisitor<Path>{
        int depth = -1;
        
        @Override
        public FileVisitResult visitFile(Path file, 
            BasicFileAttributes attrs) throws IOException {
            
            return FileVisitResult.CONTINUE;
        }
                        
        @Override
        public FileVisitResult preVisitDirectory(Path dir, 
            BasicFileAttributes attrs) throws IOException {
               
                depth++;
                return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, 
            IOException exc) throws IOException {
            
            depth--;            
            return FileVisitResult.CONTINUE;
        }       
        

        @Override
        public FileVisitResult visitFileFailed(Path file, 
                IOException exc) throws IOException {
            return FileVisitResult.TERMINATE;
        }        
    }
    
    public static void main(String[] args) {
        try(Scanner sin = new Scanner(System.in)){
            System.out.print("Enter path: ");
            String sPath = sin.nextLine();
            Path oPath = Paths.get(sPath).toRealPath();
            System.out.println("Listing directory structure of : " + oPath);
            Files.walkFileTree(oPath, new DirectoryVisitor());
            System.out.println("Size: " + size);
        }catch(InvalidPathException ex){
            System.out.println("Path contains invalid character.");
        }catch(IOException ex){
            System.out.println("An error occured while checking path: " + 
                                   ex.getMessage());
        }        
        
    }
}