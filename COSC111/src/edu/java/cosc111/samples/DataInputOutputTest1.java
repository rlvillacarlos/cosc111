package edu.java.cosc111.samples;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DataInputOutputTest1 {
    private static final Scanner cin = new Scanner(System.in);     
            
    private static void doViewMenu(Path p) throws IOException{
        try(DataInputStream fin = new DataInputStream(
                                        new BufferedInputStream(
                                            new FileInputStream(p.toFile())))){
            System.out.println("-----------------VIEW RECORDS-----------------");
            while(true){
                System.out.println("   ID: " + fin.readInt());        
                System.out.println("   Last Name: " + fin.readUTF());
                System.out.println("   First Name: " + fin.readUTF());
                System.out.println("   Height (in meters): " + fin.readFloat());
                System.out.println("");
            }
            
        }catch(EOFException ex){
            System.out.println("----------------------------------------------");
        } 
    }
    
    private static void doAddMenu(Path p)throws IOException{
        try(DataOutputStream fout = new DataOutputStream(
                                        new BufferedOutputStream(
                                            new FileOutputStream(p.toFile(),true)
                                        ))){
            System.out.println("------------------ADD RECORDS-----------------");
            System.out.print("ID: ");        
            fout.writeInt(cin.nextInt());
            //discard the remaining input
            cin.nextLine();
            System.out.print("Last Name: ");
            fout.writeUTF(cin.nextLine());
            System.out.print("First Name: ");
            fout.writeUTF(cin.nextLine());
            System.out.print("Height (in meters): ");
            fout.writeFloat(cin.nextFloat());
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
