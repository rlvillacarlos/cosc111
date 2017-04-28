package edu.java.cosc111.samples.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author russel
 */
public class WhoIsOnline {
    private static final String DEFAULT_MULTICAST_ADDR = "224.0.2.0";
    private static final int DEFAULT_MULTICAST_PORT = 1024;
    private static final int MAX_USN_LEN = 255;
    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-16");
    
    private static InetAddress multicastAddress;
    private static String username = null;
    private static DatagramSocket sender;
    
    public static void main(String[] args) throws UnknownHostException, SocketException, IOException {
        Scanner cin = new Scanner(System.in);
        
        System.out.print("Username: ");
        username = cin.nextLine();
        multicastAddress = InetAddress.getByName(DEFAULT_MULTICAST_ADDR);
        
        new Thread(new UserLogger()).start();
        sender = new DatagramSocket();
        byte[] b = username.getBytes(DEFAULT_CHARSET);
        DatagramPacket msg = new DatagramPacket(b, 0, b.length, 
                                multicastAddress,DEFAULT_MULTICAST_PORT);
        sender.send(msg);
        
        b = new byte[MAX_USN_LEN*2];
        msg = new DatagramPacket(b, b.length);
        sender.receive(msg);
        String name = new String(msg.getData(),DEFAULT_CHARSET).trim();
        System.out.printf(">> %s @ %s%n",name,msg.getAddress());
    }
    
    private static class UserLogger implements Runnable{
        
        @Override
        public void run() {
            MulticastSocket receiver = null;
            try{
                receiver = new MulticastSocket(1024);
//                System.out.println(receiver.getReuseAddress());
                receiver.joinGroup(multicastAddress);
                while(true){
                    byte[] b = new byte[MAX_USN_LEN*2];
                    DatagramPacket msg = new DatagramPacket(b, b.length);
                    receiver.receive(msg);
                    
                    if(!msg.getAddress().equals(InetAddress.getLocalHost())){
                        String name = new String(msg.getData(),DEFAULT_CHARSET).trim();
                        System.out.printf(">> %s @ %s%n",name,msg.getAddress());
                        msg.setData(username.getBytes(DEFAULT_CHARSET));
                        receiver.send(msg);
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }finally{
                if(receiver!=null){
                    try {
                        receiver.leaveGroup(multicastAddress);
                    } catch (IOException ex) {
                    }finally{
                        receiver.close();
                    }
                }
            }
        }
        
    }
    
//    private static class UserIdentifier implements Runnable{
//        
//        @Override
//        public void run() {
//            
//        }
//        
//    }
}
