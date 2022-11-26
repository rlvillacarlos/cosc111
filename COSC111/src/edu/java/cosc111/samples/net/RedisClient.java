package edu.java.cosc111.samples.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author Russel
 */
public class RedisClient {
    private static final int nPort = 6379;
    
    public static void main(String[] args) throws IOException {
        System.out.print("Enter host address/name: ");
        Scanner cin = new Scanner(System.in);        
        String sHostaddr = cin.nextLine();
        doConnect(sHostaddr);
    }
    
    private static void doConnect(String host) throws IOException{
        try(Socket sock = new Socket()){            
            sock.setKeepAlive(true);
            sock.connect(new InetSocketAddress(host,nPort)); //block           
            System.out.println("Connected to " + sock.getInetAddress());
            try(BufferedReader in = new BufferedReader(
                                        new InputStreamReader(
                                            sock.getInputStream(),"UTF-8"));
                PrintWriter out = new PrintWriter(sock.getOutputStream(),true)){
                Scanner cin = new Scanner(System.in);
      
                while(true){
                    System.out.print(">>");
                    String redisCmd = cin.nextLine().trim();
                    String redisMessage = buildRedisMessage(redisCmd);
                    out.println(redisMessage);
                    String resp = readRedisResponse(in);
                    System.out.println(resp);
                    if(redisCmd.equalsIgnoreCase("quit") && 
                            resp.equalsIgnoreCase("ok")){
                        break;
                    }
                }
                
            }
        }catch(SocketException | UnknownHostException | SocketTimeoutException e){
//            System.out.println("Error:  " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static String buildRedisMessage(String cmd){
        
        String parts[] = cmd.split(" ");
        
        StringBuilder message = new StringBuilder("*");
        
        message.append(parts.length)
                .append("\r\n");
        
        for(String part: parts){
            message.append("$")
                    .append(part.length())
                    .append("\r\n")
                    .append(part)
                    .append("\r\n");
        }
        
        return message.toString();
    }
    
    public static String readRedisResponse(BufferedReader in) throws IOException{
        String line = in.readLine();
        
        switch(line.charAt(0)){
            case '*':
                return readRedisArray(Integer.parseInt(line.substring(1)), in);
            case '-':
                return readRedisError(line);
            case '+':
                return readRedisSimpleString(line);
            case ':':
                return readRedisInteger(line);
            case '$':
                return readRedisBulkString(Integer.parseInt(line.substring(1)),in);
        }
        return "";
    }
    
    public static String readRedisArray(int len, BufferedReader in) throws IOException {
        StringBuilder resp = new StringBuilder();
                
        for (int i = 1; i <= len; i++) {
            resp.append("[" + i + "]")
                 .append(readRedisResponse(in))
                  .append("\n");
        }
        return resp.toString().trim();
    }

    public static String readRedisBulkString(int len, BufferedReader in) throws IOException{
        StringBuilder resp = new StringBuilder("\"");
        for(int i = 1; i <= len; i++){
            resp.append((char)in.read());
        }
        resp.append("\"");
        in.readLine();
        
        return resp.toString();
    }

    public static String readRedisSimpleString(String str){
        return str.substring(1);
    }

    public static String readRedisError(String str){
        return "Error: " + str.substring(1);
    }
    
    public static String readRedisInteger(String str){
        return "(int)" + Integer.parseInt(str.substring(1));
    }
    

}
