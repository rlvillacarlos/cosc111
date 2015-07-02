package edu.java.cosc111.samples;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CharsetTest {

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        System.out.print("File to open: ");
        
        try{
            Path p = Paths.get(cin.nextLine());
            System.out.print("Available Character Set\n" + 
                                "  [1] US-ASCII   [2] ISO-LATIN-1\n" +
                                "  [3] UTF-8      [4] UTF-16\n" +
                                "  [5] UTF-16BE   [6] UTF-16LE\n" +
                                "Charset: ");
            int opt = cin.nextInt();
            System.out.println("Content:");
            load(p,toCharset(opt));
        }catch(InvalidPathException ex){
            System.out.println("The file path is not valid.");
        }catch (InputMismatchException |IllegalArgumentException ex){
            System.out.println("Invalid character set option");        
        }catch (FileNotFoundException ex){
            System.out.println("File does not exists.");
        }catch(IOException ex){
            System.out.println("An error occured while opening file: " +
                                ex.getMessage());
        }
        
    }
    private static Charset toCharset(int i) throws IllegalArgumentException{
        switch(i){
            case 1: return Charset.forName("US-ASCII");
            case 2: return Charset.forName("ISO-8859-1");
            case 3: return Charset.forName("UTF-8");
            case 4: return Charset.forName("UTF-16");
            case 5: return Charset.forName("UTF-16BE");
            case 6: return Charset.forName("UTF-16LE");
            default: throw new IllegalArgumentException("Invalid charset option.");
        }        
    }
    private static void load(Path p,Charset chs) throws IOException{
        try(BufferedReader fin = new BufferedReader(
                                    new InputStreamReader(
                                        new FileInputStream(p.toFile()), chs));){
            
            char[] cbuff = new char[1024];
            while(fin.read(cbuff)!=-1){
                System.out.print(new String(cbuff));
            }
            
        }catch(MalformedInputException ex){
            System.out.println("File cannot be opened using the selected charset");
        }
    }
}
