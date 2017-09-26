package edu.java.cosc111.samples;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.spi.FileSystemProvider;
import java.util.Set;

/**
 *
 * @author Russel
 */
public class FileSystemTest1 {

    public static void main(String[] args) throws IOException{
        FileSystem fsys = FileSystems.getDefault();
        FileSystemProvider provider = fsys.provider(); 
        Iterable<FileStore> fs = fsys.getFileStores();
        Iterable<Path> roots = fsys.getRootDirectories();
        Set<String> attrViews = fsys.supportedFileAttributeViews();
        
        System.out.println("-Default File System Information-\n");
        System.out.println("Provider Class: " + provider.getClass().getCanonicalName());
        System.out.println("URI Scheme: " + provider.getScheme() );
        
        System.out.println("Available File Stores");
        
        for(FileStore f:fs){
            System.out.println("\t" + f);
            System.out.println("\t\tType: " + f.type());
            System.out.println("\t\tRead-only: " + f.isReadOnly());
            System.out.println("\t\tTotal Space: " + 
                                    (f.getTotalSpace()) + " bytes");
            System.out.println("\t\tUsable Space: " + 
                                    f.getUsableSpace() + " bytes");
            System.out.println("\t\tUnallocated Space: " + 
                                    f.getUnallocatedSpace() + " bytes");                      
        }
        
        System.out.print("\nSupported Attribute Views: ");
        for(String v: attrViews){
            System.out.print(v + " ");
        }
        System.out.println("");
        System.out.print("\nRoot Directories: ");
        for(Path root: roots){
            System.out.print("[" + root + "] ");            
        }
        System.out.println("");
        
        
    }
}
