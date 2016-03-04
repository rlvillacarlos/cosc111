package edu.java.cosc111.samples;

import java.io.IOException;
import java.util.*;
import java.nio.file.Path;
import java.nio.file.*;
import java.net.URI;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.spi.FileSystemProvider;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Russel
 */
public class ZipView {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        Map<String, String> env = new HashMap<>(); 
        JFileChooser jZipFileOpen = new JFileChooser("D:\\");
        jZipFileOpen.setFileFilter(new FileNameExtensionFilter("Zip Files (\"*.zip, *.jar, *.syp\")", 
                                        "zip","jar","syp"));
        if(jZipFileOpen.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
            URI p = new URI("jar:" + jZipFileOpen.getSelectedFile().toURI().toString());
            try (FileSystem zipfs = FileSystems.newFileSystem(p, env)){
                FileSystemProvider provider = zipfs.provider();        
                Path dir = zipfs.getPath(zipfs.getSeparator());
                try (DirectoryStream<Path> stream = provider.newDirectoryStream(dir,null)){ 
                    System.out.printf("%-20s\t%-20s\t%-20s\t%-20s%n%n",
                                        "Filename","Create",
                                        "Last Accessed","Last Modified");
                    for (Path file : stream) {
                        BasicFileAttributes bAttr = provider.getFileAttributeView(
                                                        file,BasicFileAttributeView.class)
                                                            .readAttributes();
                        
                        String fname = file.getFileName().toString();
                        if(fname.length()>24){
                            fname = fname.substring(0, 19) + "...";
                        }                        
                        System.out.printf("%-24s%s\t%s\t%s%n",fname,
                                            formatFileTime(bAttr.creationTime()),
                                            formatFileTime(bAttr.lastAccessTime()),
                                            formatFileTime(bAttr.lastModifiedTime()));
                    }                    
                } catch (IOException | DirectoryIteratorException ex) {
                    ex.printStackTrace();
                }
            }            
        } 
    }
    
    private static String formatFileTime(FileTime ft){
        if(ft==null){
            return "";
        }
        
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(ft.toMillis());
        return String.format("%1$tm/%1$td/%1$tY %1$tr",cal);            
    }
}
