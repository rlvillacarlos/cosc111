package edu.java.cosc111.samples;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 *
 * @author Russel
 */
public final class DirectoryTreeDeleter {
    private static final DirectoryTreeDeleter instance = new DirectoryTreeDeleter();
    
    private DirectoryTreeDeleter(){}//cannot instantiate
    
    private static class FileTreeVisitorAndDeleter extends SimpleFileVisitor<Path>{                
        
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Files.delete(file);
            return super.visitFile(file, attrs);
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            Files.delete(dir);
            return super.postVisitDirectory(dir, exc);
        }                     
    }
    public static void deleteDirectoryTree(String sDir) throws IOException{
        deleteDirectoryTree(Paths.get(sDir).toAbsolutePath());
    }
    
    public static void deleteDirectoryTree(Path dir) throws IOException{
        if(Files.isDirectory(dir)){
            Files.walkFileTree(dir, new FileTreeVisitorAndDeleter());
        }else{
            throw new IllegalArgumentException(dir + " is not a directory");
        }
    } 
    
}
