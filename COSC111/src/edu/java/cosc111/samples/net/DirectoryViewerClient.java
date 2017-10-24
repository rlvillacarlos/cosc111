package edu.java.cosc111.samples.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
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
        String host = in.nextLine();
        
        System.out.print("Path:");
        String path = in.nextLine();
        
        System.out.print("Filter:");
        String filter = in.nextLine().trim();
        
        if(filter.isEmpty()){
            filter="*";
        }
        
        process(host,path,filter);
    }
    
    private static void process(String host, String path, String filter) throws IOException{
        try(Socket socket = new Socket()){
            socket.connect(new InetSocketAddress(host,1024));
            
            try(BufferedReader in = new BufferedReader(
                                        new InputStreamReader(
                                            socket.getInputStream(),"UTF-8"));
                PrintWriter out = new PrintWriter(
                                    new OutputStreamWriter(
                                        socket.getOutputStream(),"UTF-8"))){
                
                
                out.println(path);
                out.println(filter);
                out.flush();
                
                String content;
                while(!(content = in.readLine()).isEmpty()){
                    System.out.println(content);
                }
                
            }
            
        }
    }
    
}
