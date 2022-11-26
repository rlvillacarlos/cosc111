package edu.java.cosc111.samples.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author Russel
 */
public class HelloClient {
    private static final int nPort = 1024;
    private static final int nTimeOut = 20000;
    
    public static void main(String[] args) throws IOException {
        System.out.print("Enter host address/name: ");
        Scanner cin = new Scanner(System.in);        
        String sHostaddr = cin.nextLine();
        doConnect(sHostaddr);
    }
    
    private static void doConnect(String host) throws IOException{
        try(Socket sock = new Socket()){            
            sock.setSoTimeout(nTimeOut);
            System.out.println("Connecting to server...");
            sock.connect(new InetSocketAddress(host,nPort)); //block           
            System.out.println("Connected...");
            try(BufferedReader cin = new BufferedReader(
                                        new InputStreamReader(
                                            sock.getInputStream(),"UTF-8"));
                PrintWriter cout = new PrintWriter(sock.getOutputStream(),true)){
                
                System.out.println("Sending \"hello\"");                
                cout.println("hello ");  
                String msg = cin.readLine();
                if(msg!=null && msg.equals("hi")){
                    System.out.println("Server: \"hi\"");
                    System.out.println("Closing connection...");
                }
            }
        }catch(SocketException | UnknownHostException | SocketTimeoutException e){
//            System.out.println("Error:  " + e.getMessage());
            e.printStackTrace();
        }
    }
}
