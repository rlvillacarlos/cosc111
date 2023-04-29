package edu.java.cosc111.samples;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

/**
 *
 * @author Acer
 */
public class CharacterStreamTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.print("File: ");
        Path file = Paths.get(in.nextLine());
        
        writeCharactersToFile(file);
        readCharactersFromFile(file);
        
    }
    
    public static void writeCharactersToFile(Path p) throws IOException{
        try(Writer out = Files.newBufferedWriter(p, StandardOpenOption.CREATE);){
            char contents[] = {'0','1','2','3','4','5','6','7','8','9'};
            out.write(contents);
        }
    }
    
    public static void readCharactersFromFile(Path p) throws IOException{
        try(Reader out = Files.newBufferedReader(p);){
            char contents[] = new char[10];
            int len;
            while((len = out.read(contents)) != -1){
                for(int i = 0; i < len; i++){
                    System.out.println(contents[i]);
                }
            }
        }
    }
}
