package edu.java.cosc111.samples;

import static java.nio.file.DirectoryStream.Filter;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 *
 * @author russel
 */
public class DirectoryLister {

    private static String filterTypes[] = {"Glob", "Size"};
    private static Scanner cin = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        System.out.print("Enter directory path: ");
        Path dirPath = Paths.get(cin.nextLine());
        
        if(Files.isDirectory(dirPath)){
            int filterTypeId = getFilterType();
            Filter<Path> filter = null;
            
            switch(filterTypeId){
                case 0: break;
                case 1:
                    filter = getGlobFilterFromUser();
                    break;
                case 2:
                    filter = getSizeFilterFromUser();
                    break;
                default:
                    return;
            }
            listDirectoryEntries(dirPath, filter);
        }else{
            System.out.println("Path is not a directory");
        }
        
        
    }
    private static int getFilterType(){
        System.out.println("Filter: ");
        System.out.println("   [0] None");
        for(int i = 0;i<filterTypes.length;i++){
            System.out.printf("   [%d] %s%n",i+1,filterTypes[i]);
        }
        System.out.print("Choice: ");
        int choice = cin.nextInt();
        cin.nextLine();
        
        if(0>choice || filterTypes.length < choice )
            return -1;
        
        return choice;
    }
    
    private static void listDirectoryEntries(Path dirPath, Filter<Path> filter) throws IOException{
        System.out.println("Directory Contents:");
        if(filter == null){
            for(Path p:Files.newDirectoryStream(dirPath)){
                System.out.printf("   %s%n",p);
            }
        }else{
            for(Path p:Files.newDirectoryStream(dirPath,filter)){
                System.out.printf("   %s%n",p);
            }
        }
    }
    
    private static Filter<Path> getSizeFilterFromUser() throws IOException{
        System.out.print("Size: ");
        long size = cin.nextLong();
        System.out.println("Match Type:");
        System.out.println("   [0] Exact\n   [1] At Least\n   [2] At Most");
        System.out.print("Choice: ");
        int matchType = cin.nextInt();
        switch(matchType){
            case 0: return new SizeFilter<Path>(size, SizeFilter.SizeMatchType.EXACT);
            case 1: return new SizeFilter<Path>(size, SizeFilter.SizeMatchType.ATLEAST);
            case 2: return new SizeFilter<Path>(size, SizeFilter.SizeMatchType.ATMOST);
        }
        return null;
    }
    
    
    private static Filter<Path> getGlobFilterFromUser() throws IOException{
        System.out.print("Glob Pattern: ");
        String globStr = cin.nextLine().trim();
//        String pString = p.toString().replace("\\", "\\\\");                
        StringBuilder glob = new StringBuilder("glob:");
        if(globStr.isEmpty()){
            glob.append("*");
        }else{
            glob.append(globStr);
        }
        return new Filter<Path>() {
            @Override
            public boolean accept(Path entry) throws IOException {
                return FileSystems.getDefault().getPathMatcher(glob.toString()).matches(entry.getFileName());
            }
        };
    }
    
}
