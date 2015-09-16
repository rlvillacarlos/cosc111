package edu.java.cosc111.samples.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 *
 * @author Russel
 */
public class BroadcastSender {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SocketException, IOException {
        try(DatagramSocket socket = new DatagramSocket()){
            socket.setSoTimeout(1000);
            socket.setBroadcast(true);    
            System.out.println("-Broadcaster's Information-");
            System.out.println("  Address:" + InetAddress.getLocalHost());            
            System.out.println("  Port:" + socket.getLocalPort());            
            byte[] bMsg = "Hi".getBytes();
            socket.send(new DatagramPacket(bMsg, bMsg.length, InetAddress.getByName("255.255.255.255"), 1025));
            DatagramPacket datagram = new DatagramPacket(new byte[1024], 1024);
            socket.receive(datagram);
            System.out.println("-Listener's Information-");
            //Print listener's address from the received datagram
            System.out.println("  Host: " + datagram.getAddress());    
            //Print listener's port from the received datagram
            System.out.println("  Port: " + datagram.getPort());                
            //Print listener's message
            System.out.println("  Message: " +  new String(datagram.getData()).trim());
        }
    }
    
}
