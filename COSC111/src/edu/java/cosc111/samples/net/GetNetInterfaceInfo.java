package edu.java.cosc111.samples.net;

import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 *
 * @author Russel
 */
public class GetNetInterfaceInfo {
    public static void main(String[] args) throws Exception{
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();

        for (NetworkInterface netIntf: Collections.list(nets)) {
            if(netIntf.isUp()){
                displayInterfaceInformation(netIntf);
                System.out.printf("\n");
            }
//            displaySubInterfaces(netIf);
        }                    
    }
    static void displayInterfaceInformation(NetworkInterface netIntf) throws SocketException {
        System.out.printf("Display name: %s\n", netIntf.getDisplayName());
        System.out.printf("Name: %s\n", netIntf.getName());
        System.out.printf("Supports multicast: %s\n",netIntf.supportsMulticast());
        System.out.printf("Is Up: %s\n",netIntf.isUp());
        System.out.printf("Is Virtual: %s\n",netIntf.isVirtual());
        System.out.printf("Is Loopback: %s\n",netIntf.isLoopback());
        System.out.printf("MTU: %d\n",netIntf.getMTU());
        List<InterfaceAddress> intfAddresses = netIntf.getInterfaceAddresses();        
        if(!intfAddresses.isEmpty()){
            System.out.println("Interface Addresses:");
            int i = 0;
            for (InterfaceAddress intfAddress : intfAddresses) {
                System.out.printf("  [%d]InetAddress: %s\n", i, intfAddress.getAddress());            
                System.out.printf("     Broadcast InetAddress: %s\n", intfAddress.getBroadcast());            
                i++;
            }        
            System.out.printf("\n");
        }
     }       
}
