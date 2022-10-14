package edu.java.cosc111.samples;

import java.io.File;
import java.util.*;
import java.nio.file.Path;
import java.nio.file.*;
import java.net.URI;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Russel
 */
public class ZipFS {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        Map<String, String> env = new HashMap<>(); 
        env.put("create", "true");
        JFileChooser jZipFileCreate = new JFileChooser("D:\\Samples");
        JFileChooser jFileChoose = new JFileChooser("D:\\Samples");
        jZipFileCreate.setFileFilter(new FileNameExtensionFilter("Zip Files (\"*.zip, *.jar\")", 
                                        "zip","jar"));
        if(jZipFileCreate.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){
            URI p = new URI("jar:" + jZipFileCreate.getSelectedFile().toURI().toString());
            System.out.println(p);
            try (FileSystem zipfs = FileSystems.newFileSystem(p, env)) {
                while(true){                    
                    jFileChoose.setSelectedFile(new File(""));                    
                    if(jFileChoose.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
                        Path toAdd = jFileChoose.getSelectedFile().toPath();
                        Path pathInZipfile = zipfs.getPath(toAdd.getFileName().toString());          
                        // copy a file into the zip file
                        Files.copy(toAdd,pathInZipfile, 
                                StandardCopyOption.REPLACE_EXISTING );                     
                    }else{                        
                        break;
                    }
                }                
            }            
        }
 
    }
    
}
