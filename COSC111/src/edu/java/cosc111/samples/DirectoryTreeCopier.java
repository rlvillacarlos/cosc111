package edu.java.cosc111.samples;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 *
 * @author russel
 */
public class DirectoryTreeCopier {
    private static class DirectoryWalkerAndCopier extends SimpleFileVisitor<Path>{
        
        Path dest;
        Path parent;
        
        DirectoryWalkerAndCopier(Path dest){
            this.dest = dest; 
            this.parent = dest; 
        }
        
        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            parent = parent.getParent();
            return super.postVisitDirectory(dir, exc);
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            return super.visitFileFailed(file, exc); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Path toCopy = parent.resolve(file.getFileName());
            System.out.println("Copying " + toCopy);
            Files.copy(file, toCopy);
            return super.visitFile(file, attrs); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            parent = parent.resolve(dir.getFileName());
            System.out.println("Creating directory: " + parent);
            Files.createDirectories(parent);
            return super.preVisitDirectory(dir, attrs); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
     public static void copyDirectoryTree(Path src, Path dest) throws IOException{
        if(Files.isDirectory(src) && Files.isDirectory(dest)){
            Files.walkFileTree(src, new DirectoryWalkerAndCopier(dest));
        }else{
            throw new IllegalArgumentException(src + " or " + dest + " is not a directory");
        }
    } 
}
