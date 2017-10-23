/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.java.cosc111.samples.net;

import edu.java.cosc111.samples.GlobFilter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

/**
 *
 * @author russel
 */
public class DirectoryViewerServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        serve();
    }
    
    private static void serve() throws IOException{
        try(ServerSocket server = new ServerSocket(1024)){
            try(Socket client = server.accept()){
                try(BufferedReader cin = new BufferedReader(
                                            new InputStreamReader(
                                                client.getInputStream(),"UTF-8"));
                PrintWriter cout = new PrintWriter(client.getOutputStream(),true)){
                    String path = cin.readLine();//.split(":")[1];
                    String glob = cin.readLine();//.split(":")[1];
                    
                    System.out.println(path);
                    System.out.println(glob);
                    
                    Path dir = Paths.get(path);
                    DirectoryStream.Filter<Path> filter = new GlobFilter<>(glob);

                    if(Files.isDirectory(dir)){
                        for(Path p:Files.newDirectoryStream(dir,filter)){
                            BasicFileAttributes attr = Files.readAttributes(p, BasicFileAttributes.class);

                            cout.printf("%s   %s%n", p,
                                (attr.isDirectory()?"Directory":"File"));    

                        }
                        cout.println();
                    }
                }
            }
        }
    }
}
