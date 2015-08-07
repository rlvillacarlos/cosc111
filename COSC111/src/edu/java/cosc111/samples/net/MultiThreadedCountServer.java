package edu.java.cosc111.samples.net;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MultiThreadedCountServer {
    private static final int DEFAULT_PORT = 1024;
    private static int ctr;
    private static int max;
    
    public static void main(String[] args) throws IOException {        
        System.out.print("Enter max count: ");
        Scanner cin = new Scanner(System.in);
        int max_ = cin.nextInt();
        if(max_>0){
            max =max_;
            serve();
        }else{
            System.out.println("Maximum counter value is invalid. Closing server...");
        }
    }
    
    private static void serve(){
        System.out.println("Starting count server...");        
        try (ServerSocket sockServer = new ServerSocket()) {
            sockServer.bind(new InetSocketAddress(DEFAULT_PORT));
            System.out.println("Host address: " + 
                    InetAddress.getLocalHost().getHostAddress());
            System.out.println("Port: " + sockServer.getLocalPort());
            
            while(ctr<max){
                try {
                    Socket sockClient = sockServer.accept();
                    System.out.println("Client " +
                            sockClient.getInetAddress().getHostName() +
                            " connected...");
                    //Create and start a new client thread
                    new Thread(new Client(sockClient)).start();
                }catch(IOException ex){
                    System.out.println("Connecting with client failed: " + 
                                        ex.getMessage());
                }
            }
            System.out.println("Counter has reached the maximum count.\nClosing server...");
        }catch(IOException ex){
            System.out.println("Server error: " + ex.getMessage());
            System.out.println("Closing server...");
        }
    }
    
    //Class for Client Thread
    private static class Client implements Runnable{
        private Socket sockClient;
        
        Client(Socket sockClient_){
            if(sockClient_ != null && !sockClient_.isClosed()){
                sockClient = sockClient_;                    
            }else{
                throw new IllegalArgumentException("Invalid Socket");
            }
        }
        
        @Override
        public void run(){                        
            try(DataInputStream in = new DataInputStream(
                    new BufferedInputStream(sockClient.getInputStream()));
                DataOutputStream out = new DataOutputStream(
                    new BufferedOutputStream(sockClient.getOutputStream()));){
                if(in.readBoolean()){
                    System.out.println("Client " +
                            sockClient.getInetAddress().getHostName() +
                            " incremented the counter.");
                    ctr++;
                    System.out.println("Counter: " + ctr);
                }else{
                    System.out.println("Client " +
                            sockClient.getInetAddress().getHostName() +
                            " did not increment the counter...");
                }
                out.writeInt(ctr);
                out.flush();
                sockClient.close();
            }catch(IOException ex){
                System.out.println("Communicating with client failed: " 
                                    + ex.getMessage());
            }
        }        
    }    
}