package edu.java.cosc111.samples;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.nio.file.Path;
import java.nio.file.*;
import java.net.URI;
import java.nio.file.attribute.BasicFileAttributes;
import javax.swing.JFileChooser;

/**
 *
 * @author Russel
 */
public class ZipCreate {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        Map<String, String> env = new HashMap<>(); 
        env.put("create", "true");
        JFileChooser jChooser = new JFileChooser("D:\\");        
        jChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        
        if(jChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
            File f = jChooser.getSelectedFile();
            if(f.getParent()!=null){
                Path sPath = Paths.get(f.getParent(),f.getName().concat(".zip"));
                System.out.println("Zip File: " + sPath);
                try (FileSystem zipfs = FileSystems.newFileSystem(sPath, env,null)) {
                    Path filePath = f.toPath();
                    if(Files.isDirectory(filePath)){
                        Files.walkFileTree(filePath, new DirectoryVisitor(zipfs));
                    }else if(Files.isRegularFile(filePath)){                    
                        Path fPath = zipfs.getPath(f.getName());
                        System.out.println("Adding " + fPath);
                        Files.copy(filePath,fPath);
                    }
                }                            
            }else{
                System.out.println("Invalid file/directory.");
            }
        }
 
    }
    public static class DirectoryVisitor extends SimpleFileVisitor<Path>{
//        private long depth = 0L;
        private int startIndex = -1;
        private final String sep = FileSystems.getDefault().getSeparator();
        private final FileSystem zipfs;
        
        DirectoryVisitor(FileSystem zipfs_){
            if(zipfs_==null){
                throw new NullPointerException();
            }
            zipfs = zipfs_;
        }
        
        @Override
        public FileVisitResult visitFile(Path file, 
            BasicFileAttributes attrs) throws IOException {
            if(Files.isReadable(file)){
                Path zipPath = zipfs.getPath(zipfs.getSeparator(),file.subpath(startIndex, file.getNameCount()).toString());
                System.out.println("Adding " + zipPath);
                Files.copy(file,zipPath,StandardCopyOption.REPLACE_EXISTING);
            }
            return FileVisitResult.CONTINUE;
        }
                        
        @Override
        public FileVisitResult preVisitDirectory(Path dir, 
            BasicFileAttributes attrs) throws IOException {
                if(startIndex==-1){
                    startIndex = dir.getNameCount()-1;
                }
                Path toCreate = zipfs.getPath(zipfs.getSeparator(),dir.subpath(startIndex, dir.getNameCount()).toString());
                if(Files.notExists(toCreate)){
                    zipfs.provider().createDirectory(toCreate);
                }                    
                //depth++;
                return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, 
            IOException exc) throws IOException {
            //depth--;            
            return FileVisitResult.CONTINUE;
        }       
        

        @Override
        public FileVisitResult visitFileFailed(Path file, 
                IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }        
    }    
}
