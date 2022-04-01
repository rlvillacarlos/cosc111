package edu.java.cosc111.samples;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 *
 * @author russel
 */
public class PathTest {

    private static final Scanner cin = new Scanner(System.in);
    
    public static void main(String[] args) throws IOException {
        manageFile();
    }
    
    private static void getFileInformationFromPath(Path p) throws IOException{
        
        System.out.println("\n---------------File Information------------------");
        System.out.printf("Is absolute path? %s%n",p.isAbsolute());
        System.out.printf("Absolute Path: %s%n",p.toAbsolutePath());
        System.out.printf("Filename: %s%n",p.getFileName());
        System.out.printf("Parent: %s%n",p.getParent());

        System.out.printf("Kind: %s%n",Files.isDirectory(p)?"Directory":"File");
        System.out.printf("Size (in bytes): %d%n",Files.size(p));

        System.out.printf("Existing: %b%n",Files.exists(p));
        System.out.printf("Readable: %b%n",Files.isReadable(p));
        System.out.printf("Writable: %b%n",Files.isWritable(p));
        System.out.printf("Executable: %b%n",Files.isExecutable(p));
        System.out.printf("Hidden: %b%n",Files.isHidden(p));
        System.out.println("-------------------------------------------------\n");
    }
    
    private static void manageFile() throws IOException{

        while(true){
            System.out.print("Enter file path: ");
            String path = cin.nextLine();
            Path p = Paths.get(path);
            System.out.println("What do you want to do:");
            System.out.println("   [0] Exit");
            System.out.println("   [1] Create File");
            System.out.println("   [2] Create Directory");
            System.out.println("   [3] Delete File/Directory");
            System.out.println("   [4] Get File/Directory Information");
            System.out.print("Choice: ");
            int choice;
            switch((choice = cin.nextInt())){
                case 0:return;
                case 1:
                case 2:
                    if(Files.exists(p)){
                        System.out.printf("%s already exists.%n",p.toAbsolutePath());
                    }else{
                        if(choice == 1){
                            Files.createFile(p);
                        }else{
                            Files.createDirectories(p);
                        }
                        System.out.printf("%s is created successfully.%n",p.toAbsolutePath());                        
                    }
                    break;
                case 3:            
                    if(Files.deleteIfExists(p)){
                        System.out.printf("%s is deleted successfully.%n",p.toAbsolutePath());
                    }else{
                        System.out.printf("%s does not exists.%n",p.toAbsolutePath());
                    }
                    break;
                case 4:
                    getFileInformationFromPath(p);
                default:
                    break;
            }
            cin.nextLine();
            System.out.println();
        }
    }
}
