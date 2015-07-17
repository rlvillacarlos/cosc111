package edu.java.cosc111.samples;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Calendar;
import java.util.Scanner;
import java.util.Set;

public class NIO2Test {
    private static final FileSystem fsys  = FileSystems.getDefault();
    private static final String     delim = fsys.getSeparator();
    private static final Path       pRoot = Paths.get(delim).toAbsolutePath().getRoot();
    private static final Path       pWorkDir = Paths.get("").toAbsolutePath();
    private static final String     sRoot = pRoot.toString();
    
    private static void init() throws IOException{
        Path src = Paths.get(delim + "samples");
        
        if(Files.exists(src)){
            DirectoryTreeDeleter.deleteDirectoryTree(src);
        }
        
        Path[] contents = {src.resolve(Paths.get("sample1","sample1.txt")),
                           src.resolve(Paths.get("sample2","sample2.txt"))
                          };
        
        for(Path p:contents){
            Files.createDirectories(p.getParent());   
            Files.createFile(p);
        }                   
    }
    
    public static void main(String[] args) throws IOException{   
        init();
        System.out.println("File System: " + fsys.getClass().getSimpleName());
        System.out.println("Separator: " + delim);
        System.out.println("Current root: " + sRoot);
        System.out.println("Working Directory: " + pWorkDir);
        System.out.println();
        
        //showFileStoreInfo();
        //showRootDirectories();
        //showSupportedAttributeViews();
        //testPath();
        testFiles();
    }
    
    private static String toDateFormat(FileTime value){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(value.toMillis());
        return String.format("%1$tm/%1$td/%1$tY %1$tr",cal);        
    }
    
    private static void testFiles() throws IOException{
        Path[] oPath = {Paths.get(sRoot + "samples","sample1","sample1.txt"),
                        Paths.get(sRoot + "samples","sample2","sample2.txt"),
                        Paths.get(sRoot + "samples","sample3","text","sample3.txt"),
                        Paths.get(sRoot + "samples","sample1")
                        };
        for(Path p: oPath){
            //Check if existing...
            System.out.println("File: " + p.toAbsolutePath());          
            if(Files.exists(p) && !Files.notExists(p)){
                BasicFileAttributes attr = Files.readAttributes(p,                                                
                                                BasicFileAttributes.class);
                System.out.println("Attributes: ");
                System.out.println("   Size:" +  attr.size());
                System.out.println("   Create:" +  toDateFormat(attr.creationTime()));
                System.out.println("   Last Modified:" +  toDateFormat(attr.lastModifiedTime()));
                System.out.println("   Last Accessed:" +  toDateFormat(attr.lastAccessTime()));
                System.out.println("   Directory:" +  attr.isDirectory());
                System.out.println("   Regular File:" +  attr.isRegularFile());
                System.out.println("   Other Type:" +  attr.isOther());     
            }else if(!Files.exists(p) && Files.notExists(p)){
                System.out.println("   File does not exists.");
            }else{
                System.out.println("   Cannot check if the file exists.");
            }
            System.out.println();
        }
        
        System.out.println("Creating path: \"" + oPath[2].getParent() + "\"");
        Files.createDirectories(oPath[2].getParent());
        System.out.println("Creating file: \"" + oPath[2] + "\"");
        Files.createFile(oPath[2]);
        Path p = oPath[1].resolveSibling("sample1(1).txt");
        System.out.println();
        System.out.println("Copying \"" + oPath[0] + "\" as \"" + p + "\"");
        System.out.println();
        Files.copy(oPath[0],p,
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.COPY_ATTRIBUTES);
        
        System.out.println("Writing contents to " + oPath[1] +".");
        try(PrintWriter fout = new PrintWriter(Files.newBufferedWriter(oPath[1],
                                    StandardOpenOption.APPEND))){
            for(int i =1;i<=10;i++){
                System.out.print(i + " ");
                fout.print(i + " ");
            }
            System.out.println();
        }
        
        System.out.println("Reading contents of " + oPath[1] +".");
        try(Scanner fin = new Scanner(Files.newBufferedReader(oPath[1],
                                        Charset.forName("UTF-8")))){
            while(fin.hasNext()){
                System.out.print(fin.nextLine());
            }
        }
        System.out.println();
    }
    
    private static void testPath(){
        Path[] oPath = new Path[10];
        oPath[0] = Paths.get(sRoot,"samples","sample1","sample1.txt");
        oPath[1] = Paths.get(sRoot + "\\samples\\sample2\\sample2.txt");
        
        //Path relative to the working directory
        oPath[2] = Paths.get("samples" , "sample","sample.txt");
        //Convert relative path to absolute path
        oPath[3] = oPath[2].toAbsolutePath();
        
        //Path relative to the current root directory     
        oPath[4] = Paths.get(delim + "samples", "sample1","sample1.txt");
        oPath[5] = oPath[4].toAbsolutePath();        
        
        //The . in a path refers to the current directory
        oPath[6] = Paths.get(sRoot + "\\samples\\.");
        oPath[7] = oPath[6].normalize();
        
        //The .. in a path refers to the parent of the current directory
        oPath[8] = Paths.get(sRoot + "\\samples\\..");
        oPath[9] = oPath[8].normalize();
        int i = 0;
        for(Path p:oPath){
            System.out.printf("Path [%d]: %s%n", (i++), p);
            
            System.out.println("   Name Count: " + p.getNameCount());
            System.out.println("   File Name: " + p.getFileName());
            System.out.println("   Is absolute: " + p.isAbsolute());
            System.out.println("   Root: " + p.getRoot());
            System.out.println("   parent: " + p.getParent());
        }
        System.out.println();
        
        Path parent = oPath[0].getParent();
        System.out.println("Path: " + oPath[0]);
        System.out.println("Parent: " + parent);
        for(int j = 2;j<=3;j++){
            Path p = Paths.get("sample" + j + ".txt");
            Path p2 = oPath[0].resolveSibling(p);
            Path p1 = parent.resolve(p);
            System.out.println("   New path: " + p);
            //Combine 
            System.out.println("   Path after resolving with parent: " + p1);            
            System.out.println("   Path after resolving with sibling: " + p1);
            System.out.println();
        }        
        System.out.println();
        
        //Creating a path between two paths
        System.out.println("Path from \"" + oPath[0] + "\" to \"" + oPath[1] +
                            "\": " + oPath[0].relativize(oPath[1]));
        System.out.println();

        //Test if two paths are the same
        System.out.println("Comparing " + oPath[0] + " and " + oPath[4]);
        System.out.println("   Same path (non-absolute): " + 
                                oPath[0].equals(oPath[4]));
        System.out.println("   Same path (absolute): " + 
                                oPath[0].equals(oPath[4].toAbsolutePath()));
        System.out.println();
    }    
    
    private static void showFileStoreInfo() throws IOException{
        Iterable<FileStore> fs = fsys.getFileStores();
        System.out.println("Available File Stores");        
        for(FileStore f:fs){            
            System.out.println("\t" + f);
            System.out.println("\t\tType: " + f.type());
            System.out.println("\t\tRead-only: " + f.isReadOnly());
            System.out.println("\t\tTotal Space: " + 
                                    (f.getTotalSpace()) + " bytes");
            System.out.println("\t\tUsable Space: " + 
                                    f.getUsableSpace() + " bytes");
            System.out.println("\t\tUnallocated Space: " + 
                                    f.getUnallocatedSpace() + " bytes");                      
        }
        System.out.println();        
    }
    
    private static void showRootDirectories(){
        Iterable<Path> roots = fsys.getRootDirectories();        
        System.out.print("\nRoot Directories: ");
        for(Path root: roots){
            System.out.print("[" + root + "] ");           
        }
        System.out.println();        
    }

    private static void showSupportedAttributeViews(){
        Set<String> attrViews = fsys.supportedFileAttributeViews();   
        
        System.out.print("\nSupported Attribute Views: ");
        for(String v: attrViews){
            System.out.print(v + " ");
        }
        System.out.println("\n");        
    }
}