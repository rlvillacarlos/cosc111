package edu.java.cosc111.samples.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class CountClient {    
    private static final int DEFAULT_PORT = 1024;
    private static final String LOCALHOST = "localhost";
    
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        System.out.print("Use default? (y/n): ");
        String response = cin.nextLine().substring(0, 1);
        if(response.equalsIgnoreCase("y")){
            System.out.println("Connecting to " + LOCALHOST + ":" 
                                + DEFAULT_PORT + "...");
            connect(LOCALHOST, DEFAULT_PORT);
        }else if(response.equalsIgnoreCase("n")){
            System.out.print("Enter host name/address: ");
            String host = cin.nextLine();
            System.out.print("Enter port number: ");
            int port = cin.nextInt();
            connect(host, port);
        }else{
            System.out.println("Invalid response. Exiting...");  
        }        
    }
    private static void connect(String host, int port){
        try(Socket sockClient = new Socket()) {            
            sockClient.setSoTimeout(1000);
            sockClient.connect(new InetSocketAddress(host, port));
            System.out.print("Increment the counter? (y/n): ");
            String resp = new Scanner(System.in).next();
            try(DataInputStream in = new DataInputStream(
                    new BufferedInputStream(sockClient.getInputStream()));
                DataOutputStream out = new DataOutputStream(
                    new BufferedOutputStream(sockClient.getOutputStream()));){
                
                out.writeBoolean(resp.substring(0, 1).equalsIgnoreCase("y"));
                out.flush();
                
                int count = in.readInt();
                System.out.println("Counter: " + count);
            }catch(IOException ex){
                System.out.println("Communication error: " + ex.getMessage());
            }
        }catch(IOException ex){
            System.out.println("Connection error: " + ex.getMessage());
        }
    }
}
