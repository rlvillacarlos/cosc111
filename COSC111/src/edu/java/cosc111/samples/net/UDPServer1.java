package edu.java.cosc111.samples.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class UDPServer1 {
    private static final int DEFAULT_PORT = 1024;
    private static final int BUFF_SIZE = 1024;
    private static final int MAX_IDLE = 120;
    
    private static String sCurrentSender = "";
    private static StringBuilder sMsgBuffer = new StringBuilder();
    private static int nCurIdleCount = 0;
    
    public static void main(String[] args) throws UnknownHostException {
        listen();
    }
    
    public static void listen() throws UnknownHostException{
        try(DatagramSocket socket = new DatagramSocket(DEFAULT_PORT)){
            System.out.println("Waiting for clients to send message...");
            socket.setSoTimeout(500);            
            System.out.println(InetAddress.getLocalHost().getHostAddress());
            while(nCurIdleCount<MAX_IDLE){
                byte[] msg = new byte[BUFF_SIZE];
                DatagramPacket dMsg = new DatagramPacket(msg, BUFF_SIZE);
                try {
                    socket.receive(dMsg);
                    nCurIdleCount = 0;
                    String sender = dMsg.getAddress().getHostName()+ "(" +dMsg.getPort() + ")";
                    if(sCurrentSender.equals(sender)){
                        sMsgBuffer.append(new String(dMsg.getData()));                                
                    }else {
                        if(sMsgBuffer.length()>0) {
                            System.out.print("\n" + sCurrentSender + ": " + sMsgBuffer.toString().trim());
                        }
                        sCurrentSender = sender;
                        sMsgBuffer = new StringBuilder(new String(dMsg.getData()));
                    }
                    dMsg.setData("Message received.".getBytes());
                    socket.send(dMsg);
                } catch (SocketTimeoutException ex){
                    if(sMsgBuffer.length()>0) {
                        System.out.print("\n" + sCurrentSender + ": " + 
                                            sMsgBuffer.toString().trim());
                        sMsgBuffer = new StringBuilder();                        
                    }else{
                        nCurIdleCount++;
                    }                    
                    sCurrentSender = "";
                } catch (IOException ex) {
                    System.out.println("Recieving Message Failed: " + 
                                            ex.getMessage());
                }    
            }
            System.out.println("\nMaximum idle time reached. Closing server...");
        }catch(SocketException ex){
            System.out.println("Server error: " + ex.getMessage());
        }
    }
}
