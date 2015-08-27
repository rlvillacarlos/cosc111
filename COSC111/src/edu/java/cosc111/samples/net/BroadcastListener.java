package edu.java.cosc111.samples.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
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
        try(DatagramSocket socket = new DatagramSocket(1024)){
            DatagramPacket data = new DatagramPacket(new byte[1024], 1024);
            socket.setSoTimeout(10000);
            socket.setBroadcast(true);
            System.out.println(socket.getLocalPort());
            socket.receive(data);
            System.out.println(new String(data.getData(),"UTF-8").trim());       
        }
    }    
}
