package edu.java.cosc111.samples.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 *
 * @author russel
 */
public class HTTPServer {

   private static final int nPort = 80;
    private static final int nTimeOut = 20000;
    
    public static void main(String[] args) throws IOException {
        doServe();
    }
    
    private static void doServe() throws IOException{        
        try(ServerSocket sockServer = new ServerSocket();){
//            sockServer.setSoTimeout(nTimeOut);
            sockServer.bind(new InetSocketAddress(nPort));
            InetAddress addr = InetAddress.getLocalHost();
            System.out.println("Server is running @ " + addr);
            System.out.println("Connection timeout: " + 
                                sockServer.getSoTimeout() + " ms.");
            while(true){
                System.out.println("Waiting for client...");
                Socket sockClient = sockServer.accept();
                System.out.println("Client @ " + sockClient.getRemoteSocketAddress()
                                    + " is connected.");
                try(BufferedReader cin = new BufferedReader(
                                            new InputStreamReader(
                                                sockClient.getInputStream(),"UTF-8"));
                    PrintWriter cout = new PrintWriter(sockClient.getOutputStream(),true)){
                    String msg;
                    while((msg = cin.readLine())!=null){
                        if(msg.length()==0){
                            break;
                        }
                        System.out.println(msg);
                    }
                    String message = "Hello Welcome";
                    cout.println("HTTP/1.1 200 OK");
                    cout.printf("Content-Length: %d%n", message.length()+2);
                    cout.println("Content-Type: text/plain");
                    cout.println("Server: Simple HTTP Server");
                    cout.println();
                    cout.println(message);
                    cout.println();
                    sockClient.close();                        
                }
            }
        }catch(SocketTimeoutException e){
            System.out.println("Server has been idle for " + nTimeOut +
                    " ms.\nClosing server...");
        }catch (BindException e){
            System.out.println("Cannot start server (" + e.getMessage() + ")");
        }
        
    }
    
}
