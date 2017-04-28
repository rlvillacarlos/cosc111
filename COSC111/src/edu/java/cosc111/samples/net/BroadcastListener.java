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
public class BroadcastListener {

    /**
     * @param args the command line arguments
     * @throws java.net.SocketException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws SocketException, IOException {
        try(DatagramSocket socket = new DatagramSocket(1025)){
            DatagramPacket datagram = new DatagramPacket(new byte[1024], 1024);
            socket.setSoTimeout(10000);
            socket.setBroadcast(true);
            System.out.println("-Listener's Information-");            
            System.out.println("  Address:" + InetAddress.getLocalHost());            
            System.out.println("  Port:" + socket.getLocalPort());            
            socket.receive(datagram);
            byte[] msg = "Hello".getBytes();
            System.out.println("-Broadcaster's Information-");            
            //Print sender's address from the received datagram
            System.out.println("  Host: " + datagram.getAddress());    
            //Print sender's port from the received datagram
            System.out.println("  Port: " + datagram.getPort());                
            //Print sender's message
            System.out.println("  Message: " +  new String(datagram.getData()).trim());
            //Send a reply to the sender directly (not broadcast)
            socket.send(new DatagramPacket(msg,msg.length,datagram.getAddress(),datagram.getPort()));
        }
    }    
}
