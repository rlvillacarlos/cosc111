/**
 *
 * @author Russel
 */

package edu.java.cosc111.samples.net;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;

public class DesktopPresenter {
    private static final int MAX_FPS = 15;
    private static int curFrameCount =0;
    private static Thread sender = null;
    private static Thread capture = null;
    private static PipedInputStream pipeIn = new PipedInputStream(Sender.BUFF_SIZE);
    private static PipedOutputStream pipeOut = new PipedOutputStream();
    private static volatile boolean isSending = false;
        
    public static void main(String[] args) throws UnknownHostException {
        System.out.println("Host name: " + InetAddress.getLocalHost().getHostName());
        System.out.println("Host address: " + InetAddress.getLocalHost().getHostAddress());
        new DesktopPresenter().startCapture();
    }
    
    public void startCapture() {
        try {
            pipeIn.connect(pipeOut);
            sender = new Thread(new Sender());
            capture = new Thread(new ScreenCapture());
            capture.start();
            sender.start();            
        } catch (IOException ex) {
            ex.printStackTrace();
        }                
    }
    
    private static byte[] toByteArray(int n){
         byte b[] = {
                     (byte)((n>>24) & 0xff),
                     (byte)((n>>16) & 0xff),
                     (byte)((n>>8) & 0xff),
                     (byte)(n & 0xff)
                   };
         return b;
    }   
    
    private static class Sender implements Runnable{
        private static final int HEADER_SIZE = 12;
        private static final int FRAME_START = 0;
        private static final int INDEX_START = 4;
        private static final int SIZE_START = 8;        
        private static final int DATA_SIZE = 32768;
        private static final int DEFAULT_PORT = 1024;
        private static final int BUFF_SIZE = HEADER_SIZE + DATA_SIZE;
        private static final String BROADCAST_ADDR ="224.0.1.0";    //"255.255.255.255";
        private InetAddress addr;
        
        @Override
        public void run() {
            try {
                addr = InetAddress.getByName(BROADCAST_ADDR);
            } catch (UnknownHostException ex) {
                ex.printStackTrace();
            }
            sendScreenShots();
        }

        private void sendScreenShots(){               
            byte[] eof = new byte[BUFF_SIZE];
            byte[] buff = new byte[BUFF_SIZE];
            int len;          
            int frameCount = 0;
            while(true){
                try(DatagramSocket socket = new DatagramSocket()){    
//                    socket.setBroadcast(true);
                    byte[] frame = toByteArray(frameCount);           
                    int piece = 0;
                    while((len=pipeIn.read(buff,HEADER_SIZE,BUFF_SIZE-HEADER_SIZE))!=-1){       
                        Arrays.fill(buff, 1,5,(byte)0);                        
                        byte[] index = toByteArray(++piece);           
                        byte[] size = toByteArray(len);
                        System.arraycopy(frame,0,buff, FRAME_START, 4);
                        System.arraycopy(index,0,buff, INDEX_START, 4);
                        System.arraycopy(size,0,buff, SIZE_START, 4);
                        DatagramPacket dMsg = new DatagramPacket(buff, BUFF_SIZE,addr,DEFAULT_PORT);
                        socket.send(dMsg);
//                        System.out.println(frameCount + ":" + piece);
//                        Thread.sleep(100);
                    }
                    Arrays.fill(eof,0,HEADER_SIZE,(byte)0);
                    byte[] count = toByteArray(piece);           
                    System.arraycopy(frame,0,eof, FRAME_START, 4);
                    System.arraycopy(count,0,eof, SIZE_START, 4);
                    DatagramPacket dMsg = new DatagramPacket(eof, BUFF_SIZE,addr,DEFAULT_PORT);
                    socket.send(dMsg);
                } catch (Exception ex){
                    ex.printStackTrace();
                }finally{
                    try {
                        pipeIn.close();
                        pipeIn = new PipedInputStream(Sender.BUFF_SIZE);
                        pipeOut = new PipedOutputStream(pipeIn);
                        isSending = false;
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }                    
                }
//                System.out.println(frameCount);
                frameCount = (frameCount + 1) % Integer.MAX_VALUE;
            }
        }
    }
    
    private static class ScreenCapture implements Runnable{
        private final Path pathCursor = Paths.get("resources","cursor", "white_cursor.png");
        
        @Override
        public void run() {
            try {
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                Rectangle screenRectangle = new Rectangle(screenSize);
                Robot robot = new Robot();
                while(true){
                    if(!isSending && curFrameCount < MAX_FPS){
                        try {
                            BufferedImage img = robot.createScreenCapture(screenRectangle);                            
                            Image cursor = ImageIO.read(pathCursor.toFile());
                            int x = MouseInfo.getPointerInfo().getLocation().x;
                            int y = MouseInfo.getPointerInfo().getLocation().y;                            
                            
                            Graphics2D graphics2D = img.createGraphics();
                            graphics2D.drawImage(cursor, x-6, y-6, 24, 24, null);                            
                            isSending=true;
                            ImageIO.write(img, "png", pipeOut);                    
                            pipeOut.close();  
                            curFrameCount ++;
                        } catch (IOException ex) {
                            System.out.println(pathCursor.toAbsolutePath());
                            ex.printStackTrace();
                        }
                    }else{
                        try{
//                            System.out.println("Sleeping...");
                            Thread.sleep(1000);
                            curFrameCount = 0;
                        }catch(InterruptedException ex){}
                    }
                } 
            }catch (AWTException ex) {
                ex.printStackTrace();
            }
        }        
    }
}
