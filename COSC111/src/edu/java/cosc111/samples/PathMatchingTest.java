package edu.java.cosc111.samples;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 *
 * @author russel
 */
public class PathMatchingTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        System.out.print("Path:");
        Path p = Paths.get(cin.nextLine());
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:E:\\\\**\\\\*.exe");
        System.out.printf("Matched: %b%n",matcher.matches(p));
    }
    
}
