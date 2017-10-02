/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.java.cosc111.samples;

import static java.nio.file.DirectoryStream.Filter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author russel
 */
public class SizeFilter<T extends Path> implements Filter<T> {
    public static enum SizeMatchType{
        EXACT,ATLEAST,ATMOST
    }
    
    private final long sizeLimit;
    private final SizeMatchType matchType;

    public SizeFilter(long sizeLimit,SizeMatchType matchType) {
        this.sizeLimit = sizeLimit;
        this.matchType = matchType;
    }
    
    @Override
    public boolean accept(T entry) throws IOException {
        long entrySize = Files.size(entry); 
        switch(matchType){
            case EXACT:
                return  entrySize == sizeLimit;
            case ATLEAST:
                return entrySize >= sizeLimit;
            default:
                return entrySize <= sizeLimit;
        }
    }
    
}
