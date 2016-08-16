package edu.java.cosc111.samples.net;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class UDPClient1 {
    private static final int BUFF_SIZE = 1;
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String sHost = "localhost";
        int nPort = 1024;
        
        System.out.print("Connect to local server? (y/n): ");
        String response = in.nextLine().substring(0, 1);
        if(response.equalsIgnoreCase("n")){
            System.out.print("Host address/name: ");
            sHost = in.nextLine();
            System.out.print("Host port: ");
            nPort = in.nextInt();
            in.nextLine();        
        }else if (response.equalsIgnoreCase("y")){
            System.out.println("Host: " + sHost + "\nPort: " + nPort);
        }else{
            System.out.println("Invalid response. Exiting...");  
            return;
        }           
        System.out.print("Message: ");
        String sMsg = in.nextLine();
        send(sHost,nPort,sMsg);
    }
    
    private static void send(String host, int port, String msg){
        try {
            byte[] tmp = new byte[BUFF_SIZE];            
            DatagramSocket socket = new DatagramSocket();    
            socket.setSoTimeout(1000);
            ByteArrayInputStream bArrMsg = new ByteArrayInputStream(msg.getBytes());
            int len;            
            InetAddress oHost; 
            
            try {
                oHost = InetAddress.getByName(host);
            } catch (UnknownHostException ex) {
                oHost = InetAddress.getLoopbackAddress();
            }
            
            try {
                while((len=bArrMsg.read(tmp))!=-1){
                    DatagramPacket dMsg = new DatagramPacket(tmp,len,oHost,port);
                    socket.send(dMsg);
                }                
            } catch (IOException ex) {
                System.out.println("Sending Message Failed: " + ex);
            }
            try {                
                DatagramPacket dMsg = new DatagramPacket(new byte[BUFF_SIZE],BUFF_SIZE);
                socket.receive(dMsg);
                System.out.println("Host: " + new String(dMsg.getData()));
             } catch (IOException ex) {
                    System.out.println("Receiving Response Failed: " + ex.getMessage());
            }
        } catch (SocketException ex) {
            System.out.println("Creating socket failed: " + ex);
        }
    }    
}
