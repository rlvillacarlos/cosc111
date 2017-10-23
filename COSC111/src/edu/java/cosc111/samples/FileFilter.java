
package edu.java.cosc111.samples;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 *
 * @author russel
 */
public class FileFilter<T extends Path> implements DirectoryStream.Filter<T>{
    private boolean includeHidden;

    public FileFilter(boolean includeHidden) {
        this.includeHidden = includeHidden;
    }
    
    @Override
    public boolean accept(T entry) throws IOException {
        BasicFileAttributes attr = Files.readAttributes(entry, BasicFileAttributes.class);
        
        return Files.isRegularFile(entry) && 
                ((Files.isHidden(entry)&&includeHidden)||!Files.isHidden(entry));
    }
    
}
