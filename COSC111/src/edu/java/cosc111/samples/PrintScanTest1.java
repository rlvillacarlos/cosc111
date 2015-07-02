package edu.java.cosc111.samples;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;

public class PrintScanTest1 {
    private static final Scanner cin = new Scanner(System.in);            
    private static void doViewMenu(Path p) throws IOException{
        try(Scanner fin = new Scanner(new BufferedInputStream(
                                new FileInputStream(p.toFile())),"UTF-8")){
            System.out.println("-----------------VIEW RECORDS-----------------");
            //Records must be separated
            fin.useDelimiter("\\s*,\\s*|\\h*\\v+");
            while(fin.hasNext()){                
                System.out.println("   ID: " + fin.nextInt());        
                System.out.println("   Last Name: " + fin.next());
                System.out.println("   First Name: " + fin.next());
                System.out.printf("   Height (in meters): %.2f%n", fin.nextFloat());
                System.out.println("");
            }           
        }catch (InputMismatchException ex){
            throw new IOException("Invalid file format",ex);
        }finally{
            System.out.println("----------------------------------------------");
        }
    }
    
    private static void doAddMenu(Path p)throws IOException{
        try(PrintWriter fout = new PrintWriter(                                   
                                new OutputStreamWriter(
                                    new BufferedOutputStream(
                                        new FileOutputStream(p.toFile(),true))
                                        ,"UTF-8"))){
            System.out.println("------------------ADD RECORDS-----------------");
            System.out.print("ID: ");        
            fout.print(cin.nextInt() + ",");
            //discard the remaining input
            cin.nextLine();
            System.out.print("Last Name: ");
            fout.print(cin.nextLine() + ",");
            System.out.print("First Name: ");
            fout.print(cin.nextLine() + ",");
            System.out.print("Height (in meters): ");
            fout.println(cin.nextFloat());            
        }finally{
            System.out.println("----------------------------------------------");
        }
    }
    
    public static void main(String[] args) {
        boolean bContinue = true;
        int choice = 0;
        do{
            System.out.print("-Menu-\n   [1] - View Records" +
                                "\n   [2] - Add New Record" + 
                                "\n   [3] - Exit" + 
                                "\nChoice: ");
            try{
                choice = cin.nextInt();
                Path p;
                switch(choice){
                    case 1: case 2:
                        cin.nextLine();
                        System.out.print("File containing records: ");
                        p = Paths.get(cin.nextLine());
                        if (choice == 1) 
                            doViewMenu(p);
                        else
                            doAddMenu(p);
                        break;
                    case 3:
                        bContinue = false;
                        break;
                    default:
                        throw new InputMismatchException();
                }
            }catch(InvalidPathException ex){
                System.out.println("The path you entered is not valid.");
            }catch(InputMismatchException ex){
                System.out.println("Invalid choice. Try again.");
                //discard previous input.
                cin.nextLine();
            }catch(FileNotFoundException ex){
                System.out.println("Cannot open file: " + ex.getMessage());
            }catch(IOException ex){
                System.out.println("An error occured while accessing file: " +
                                    ex.getMessage());
            }
         }while(bContinue);
    }          
}
