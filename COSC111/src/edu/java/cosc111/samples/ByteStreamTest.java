package edu.java.cosc111.samples;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

/**
 *
 * @author Acer
 */
public class ByteStreamTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.print("File: ");
        Path file = Paths.get(in.nextLine());
        
        writeBytesToFile(file);
        readBytesFromFile(file);
        
    }
    
    public static void writeBytesToFile(Path p) throws IOException{
        try(OutputStream out = Files.newOutputStream(p, StandardOpenOption.CREATE);){
            byte contents[] = {0,1,2,3,4,5,6,7,8,9};
            out.write(contents);
        }
    }
    
    public static void readBytesFromFile(Path p) throws IOException{
        try(InputStream out = Files.newInputStream(p, StandardOpenOption.CREATE);){
            byte contents[] = new byte[10];
            int len;
            while((len = out.read(contents)) != -1){
                for(int i = 0; i < len; i++){
                    System.out.println(contents[i]);
                }
            }
        }
    }
}
