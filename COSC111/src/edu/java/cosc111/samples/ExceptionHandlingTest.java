package edu.java.cosc111.samples;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ExceptionHandlingTest {
    private static final Scanner sin = new Scanner(System.in);
    private static final PrintStream sout = System.out;
    private static int[] arr;
    
    private static void open(Path p) throws FileNotFoundException, IOException{        
        try(Scanner fin = new Scanner(Files.newBufferedReader(p, Charset.defaultCharset()))) {
            arr = new int[Integer.parseInt(fin.nextLine())];                
            try{
                int i =0;
                while(fin.hasNextLine()){
                    arr[i++] = Integer.parseInt(fin.nextLine());
                }
                if(i<arr.length)
                    throw new NoSuchElementException("Element " + i + " does not exists.");      
            }catch(NumberFormatException ex1){
                throw new IOException("Non-integer content found.",ex1);
            }catch (NoSuchElementException|IndexOutOfBoundsException ex2){
                throw new IOException("Header information does not match contents. ",ex2);
            }
        }catch (NumberFormatException | NegativeArraySizeException | NoSuchElementException ex){
            throw new IOException("Illegal header format.", ex);
        }
    }  
    
    private static void print(){
        for(int i:arr)
            sout.print(i + " ");
        sout.println();
    }
    
    public static void main(String[] args) {        
        sout.print("Enter file to open: ");
        Path pFile = null;
        String fname = null;
        
        try {
            fname = sin.nextLine();        
            pFile = Paths.get(fname);
            open(pFile);
            sout.println("File contains " + arr.length + " integers: ");
            print();        
        }catch (InvalidPathException ex1){
            sout.println("Path \"" + fname + "\" contains invalid character(s).");
        } catch (FileNotFoundException ex2) {
            sout.println("The file \"" + pFile + "\" does not exists.");
        } catch (IOException ex3) {
            sout.println("Error while reading file: " + ex3.getMessage());
        }
    }   
}
