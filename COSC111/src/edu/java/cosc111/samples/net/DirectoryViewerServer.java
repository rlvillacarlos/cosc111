package edu.java.cosc111.samples.net;

import edu.java.cosc111.samples.GlobFilter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
                
                try(BufferedReader in = new BufferedReader(
                                        new InputStreamReader(
                                            client.getInputStream(),"UTF-8"));
                    PrintWriter out = new PrintWriter(
                                        new OutputStreamWriter(
                                            client.getOutputStream(),"UTF-8"))){
                
                    String path = in.readLine();
                    String glob = in.readLine();
                    Path dir = Paths.get(path);
                    
                    DirectoryStream.Filter<Path> filter = new GlobFilter<>(glob);
        
                    if(Files.isDirectory(dir)){
                        for(Path p:Files.newDirectoryStream(dir,filter)){
                            BasicFileAttributes attr = Files.readAttributes(p, BasicFileAttributes.class);

                            out.printf("%s   %s%n", p,
                                        (attr.isDirectory()?"Directory":"File").toString());    

                        }
                        out.println();
                        out.flush();
                    }else{
                        System.out.println("Not a directory.");
                    }
                }
            }
        }
    }
    
}
