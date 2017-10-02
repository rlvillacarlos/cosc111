
package edu.java.cosc111.samples;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author russel
 */
public class DirectoryFilter<T extends Path> implements DirectoryStream.Filter<T>{

    @Override
    public boolean accept(T entry) throws IOException {
        return Files.isDirectory(entry);
    }
    
}
