package edu.java.cosc111.samples;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileCopier {
    private static final int BUFF_SIZE = 1024;
    public static void main(String[] args) {
        Path src, dest;        
        Scanner cin = new Scanner(System.in);
        System.out.print("Source file: ");
        src = Paths.get(cin.nextLine());
        System.out.print("Target file: ");
        dest = Paths.get(cin.nextLine());
        try{
            System.out.println("Copying file...");
            copy(src, dest);
            System.out.println("Done.");
        }catch (NoSuchFileException ex){
            System.out.println("\nCannot copy file: Source file does not exists");  
        }catch (IOException ex){
            System.out.println("\nAn error occured while copying file: " + 
                                    ex.getMessage());
        }
    }
       
    public static void copy(Path src,Path dest) 
        throws IOException{
        byte[] buff = new byte [BUFF_SIZE];        
        try(BufferedInputStream fin = new BufferedInputStream(
                                            new FileInputStream(src.toFile()));
            BufferedOutputStream fout = new BufferedOutputStream(
                                            new FileOutputStream(dest.toFile()));){            
            int sz;
            while((sz = fin.read(buff))!=-1){
                fout.write(buff, 0, sz);                                                        
            }                    
        }        
    }
    
}
