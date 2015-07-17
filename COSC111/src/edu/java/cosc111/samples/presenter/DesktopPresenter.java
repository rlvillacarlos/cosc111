package edu.java.cosc111.samples.presenter;

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

public class DesktopPresenter {
    private Thread sender = null;
    private Thread capture = null;
    private PipedInputStream pipeIn = new PipedInputStream(1020);
    private PipedOutputStream pipeOut = new PipedOutputStream();
    private volatile boolean isSending = false;
        
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
    private class Sender implements Runnable{
        private static final int DEFAULT_PORT = 1024;
        private static final int BUFF_SIZE = 1025;
        private static final String BROADCAST_ADDR = "224.0.1.0";    
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
            int len = 0;          
            Arrays.fill(eof,(byte)0);
            while(true){            
                try(DatagramSocket socket = new DatagramSocket()){    
                    byte piece = 0;
                    while((len=pipeIn.read(buff,5,BUFF_SIZE-5))!=-1){       
                        Arrays.fill(buff, 1,5,(byte)0);
                        buff[0] = ++piece;                        
                        System.out.printf("piece[%d]: %d%n",piece,len);
                        if(len>255){
                            int i = 1;
                            while(len>=255){
                                buff[i++] = (byte)255;
                                len -=255;
                            }
                            if(len > 0){
//                                System.out.println(i + " " +len);
                                buff[i] = (byte)len;
                            }
                        }else{
                            buff[1]=(byte)len;
                        }
                        DatagramPacket dMsg = new DatagramPacket(buff, BUFF_SIZE,addr,DEFAULT_PORT);
                        socket.send(dMsg);
                    }
                    DatagramPacket dMsg = new DatagramPacket(eof, BUFF_SIZE,addr,DEFAULT_PORT);
                    socket.send(dMsg);
                    System.out.println("Done");
                } catch (IOException ex){
                    ex.printStackTrace();
                }finally{
                    try {
                        pipeIn.close();
                        pipeIn = new PipedInputStream(1020);
                        pipeOut = new PipedOutputStream(pipeIn);
                        isSending = false;
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
    
    private class ScreenCapture implements Runnable{
        private final Path pathCursor = Paths.get("resources","cursor", "white_cursor.png");
        @Override
        public void run() {
            try {
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                Rectangle screenRectangle = new Rectangle(screenSize);
                Robot robot = new Robot();        
                while(true){
                    if(!isSending){
                        try {
                            BufferedImage img = robot.createScreenCapture(screenRectangle);                            
                            Image cursor = ImageIO.read(pathCursor.toFile());
                            int x = MouseInfo.getPointerInfo().getLocation().x;
                            int y = MouseInfo.getPointerInfo().getLocation().y;                            
                            
                            Graphics2D graphics2D = img.createGraphics();
                            graphics2D.drawImage(cursor, x-6, y-6, 24, 24, null);                            
                            isSending=true;
                            ImageIO.write(img, "jpeg", pipeOut);                    
                            pipeOut.close();  
                            img=null;
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }else{
                        try{
                            System.out.println("Sleeping...");
                            Thread.sleep(10);
                        }catch(InterruptedException ex){}
                    }
                } 
            }catch (AWTException ex) {
                ex.printStackTrace();
            }
        }        
    }
}
