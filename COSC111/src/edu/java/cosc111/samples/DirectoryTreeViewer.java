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

public class DirectoryTreeViewer {
    public static class DirectoryVisitor extends SimpleFileVisitor<Path>{
        int depth = -1;
        String sep = FileSystems.getDefault().getSeparator();
        private void printWithLinks(Path p, BasicFileAttributes attrs, boolean isDir){            
            for(int i = 1;i<=depth;i++){
                System.out.print("  ");
            }
            System.out.println(" |-[" + p.getFileName() + 
                    (isDir ? sep : attrs.size() ) + "]" );
        }

        @Override
        public FileVisitResult visitFile(Path file, 
            BasicFileAttributes attrs) throws IOException {
            if(Files.isReadable(file)){
                printWithLinks(file,attrs,false);                
            }
            return FileVisitResult.CONTINUE;
        }
                        
        @Override
        public FileVisitResult preVisitDirectory(Path dir, 
            BasicFileAttributes attrs) throws IOException {
                if(depth>=0){
                    printWithLinks(dir,attrs,true);                
                }else{
                    System.out.println("*-[" + dir + sep + "]");
                }
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
            printWithLinks(file, null, Files.isDirectory(file));
            return FileVisitResult.CONTINUE;
        }        
    }
    
    public static void main(String[] args) {
        try(Scanner sin = new Scanner(System.in)){
            System.out.print("Enter path: ");
            String sPath = sin.nextLine();
            Path oPath = Paths.get(sPath).toRealPath();
            System.out.println("Listing directory structure of : " + oPath);
            Files.walkFileTree(oPath, new DirectoryVisitor());
        }catch(InvalidPathException ex){
            System.out.println("Path contains invalid character.");
        }catch(IOException ex){
            System.out.println("An error occured while checking path: " + 
                                   ex.getMessage());
        }        
        
    }
}