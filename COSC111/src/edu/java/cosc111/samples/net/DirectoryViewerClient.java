package edu.java.cosc111.samples.net;

import edu.java.cosc111.samples.GlobFilter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;

/**
 *
 * @author russel
 */
public class DirectoryViewerClient {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.print("Host: ");
        doConnect(in.nextLine());
    }
    
    private static void doConnect(String host) throws IOException{
        try(Socket socket = new Socket();){
            socket.connect(new InetSocketAddress(host, 1024));
            
            Scanner in = new Scanner(System.in);
            System.out.print("Directory Path: ");
            String path = in.nextLine();
            System.out.print("Filter: ");
            String glob = in.nextLine().trim();

            if(glob.isEmpty()){
                glob="*";
            }
            
            try(BufferedReader cin = new BufferedReader(
                                            new InputStreamReader(
                                                socket.getInputStream(),"UTF-8"));
                PrintWriter cout = new PrintWriter(socket.getOutputStream(),true)){
            
                cout.println(path);
                cout.println(glob);
                
                String content;
                while(!(content=cin.readLine()).isEmpty()){
                    System.out.println(content);
                }
                
            }
            
            
            
        }
    }
    
}
