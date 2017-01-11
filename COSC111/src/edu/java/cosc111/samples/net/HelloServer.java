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
 * @author Russel
 */
public class HelloServer {
    private static final int nPort = 1024;
    private static final int nTimeOut = 20000;
    
    public static void main(String[] args) throws IOException {
        doServe();
    }
    
    private static void doServe() throws IOException{        
        try(ServerSocket sockServer = new ServerSocket();){
            sockServer.setSoTimeout(nTimeOut);
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
                    if(cin.readLine().equals("hello")){
                        System.out.println("Client: \"hello\"");
                        System.out.println("Sending \"hi\"");
                        cout.println("hi");                        
                    }else{
                        System.out.println("Unknown message from client");
                    }
                    System.out.println("Closing connection...");
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
