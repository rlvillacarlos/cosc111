
package edu.java.cosc111.samples;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author russel
 */
public class CreationDateFilter<T extends Path> implements DirectoryStream.Filter<T>{
    private Instant creationTime;

    public CreationDateFilter(Date creationTime) {
        this.creationTime = creationTime.toInstant();
    }
            
    @Override
    public boolean accept(T entry) throws IOException {
        BasicFileAttributes attr = Files.readAttributes(entry, BasicFileAttributes.class);
        Instant fileCreateTime = attr.creationTime().toInstant();
        return creationTime.compareTo(fileCreateTime)<=0;
    }
    
}
