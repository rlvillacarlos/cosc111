package edu.java.cosc111.samples;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;

/**
 *
 * @author russel
 */
public class GlobFilter<T extends Path> implements DirectoryStream.Filter<T>{
    private String glob;

    public GlobFilter(String glob) {
        this.glob = "glob:"+glob;
    }
    
    @Override
    public boolean accept(T entry) throws IOException {
        FileSystem fsys = FileSystems.getDefault();
        PathMatcher matcher = fsys.getPathMatcher(glob);
        
        return matcher.matches(entry.getFileName());
    }
    
}
