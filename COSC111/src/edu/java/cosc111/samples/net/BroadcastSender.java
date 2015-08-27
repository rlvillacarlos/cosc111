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
            System.out.println(socket.getLocalPort());
            byte[] data = "Hello".getBytes();
            socket.send(new DatagramPacket(data, data.length, InetAddress.getByName("255.255.255.255"), 1024));
        }
    }
    
}
