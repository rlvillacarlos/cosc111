package edu.java.cosc111.samples;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author russel
 */
public class FileTest {

    private static final Scanner cin = new Scanner(System.in);
    
    public static void main(String[] args) throws IOException {
        System.out.print("Enter file path: ");
        String path = cin.nextLine();
        File f = new File(path); 
        getFileInformationFromPath(f);
        manageFile(f);
    }
    
    private static void getFileInformationFromPath(File f){
        
        System.out.println("\n---------------File Information------------------");
        System.out.printf("Absolute Path: %s%n",f.getAbsolutePath());
        System.out.printf("Filename: %s%n",f.getName());
        System.out.printf("Parent: %s%n",f.getParent());

        System.out.printf("Kind: %s%n",f.isDirectory()?"Directory":"File");
        System.out.printf("Size (in bytes): %d%n",f.length());

        System.out.printf("Existing: %b%n",f.exists());
        System.out.printf("Readable: %b%n",f.canRead());
        System.out.printf("Writable: %b%n",f.canWrite());
        System.out.printf("Executable: %b%n",f.canExecute());
        System.out.println("-------------------------------------------------\n");
    }
    
    private static void manageFile(File f) throws IOException{
        while(true){
            System.out.println("What do you want to do:");
            System.out.println("   [0] Exit");
            System.out.println("   [1] Create File");
            System.out.println("   [2] Create Directory");
            System.out.println("   [3] Delete File/Directory");
            System.out.print("Choice: ");
            int choice;
            switch((choice = cin.nextInt())){
                case 0:System.out.println("Bye!!!");return;
                case 1:
                case 2:
                    if(f.exists()){
                        System.out.printf("%s already exists.%n",f.getAbsolutePath());
                    }else{
                        if(choice == 1){
                            f.createNewFile();
                        }else{
                            f.mkdirs();
                        }
                        System.out.printf("%s is created successfully.%n",f.getAbsolutePath());                        
                    }
                    break;
                case 3:
                    if(!f.exists()){
                        System.out.printf("%s does not exists.%n",f.getAbsolutePath());
                    }else{
                        f.delete();
                        System.out.printf("%s is deleted successfully.%n",f.getAbsolutePath());
                    }
                    break;
                default:
                    break;
            }
            System.out.println();
        }
    }
}
