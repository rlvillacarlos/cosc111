package edu.java.cosc111.samples;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class FileArt {
    private static final int BUFF_SIZE = 1024;
    private static final ByteArrayOutputStream fileBuff = new ByteArrayOutputStream();
    
    public static void main(String[] args) {
//        int d = 0xff,e=0x00;
//        for (int j = 24; j >= 0; j -= 8) {
//            e = e | d<<j; 
//            System.out.printf("%h%n",e);
//        }
        
        Path src;     
        Scanner cin = new Scanner(System.in);
        System.out.print("Source file: ");
        src = Paths.get(cin.nextLine());
        try{
            System.out.println("Converting file...");
            toImage(src);            
            System.out.println("Done.");
        }catch (NoSuchFileException ex){
            System.out.println("\nSource file does not exists.");  
        }catch (IOException ex){
            System.out.println("\nAn error occured while converting file: " + ex.getMessage());
        }
    }
       
    public static void toImage(Path src) throws IOException{
        byte[] buff = new byte [BUFF_SIZE];        
        try(BufferedInputStream fin = new BufferedInputStream(new FileInputStream(src.toFile()),BUFF_SIZE);){            
            int sz;           
            while((sz = fin.read(buff))!=-1){
                fileBuff.write(buff, 0, sz);                                                        
            }
            List<Integer> intBuff = toIntegerArray(fileBuff.toByteArray());
            int dimension = (int)Math.floor(Math.sqrt(intBuff.size()));
            int imgSz = dimension*dimension;
            BufferedImage img = new BufferedImage(dimension, dimension, BufferedImage.TYPE_INT_RGB);
            int x = 0,y=0;
            
            for(int i = 0;i<imgSz;i++){                
                img.setRGB(x, y, intBuff.get(i));
                x = (x+1)%dimension;
                if(x==0){
                    y++;
                }
            }
            String fname = src.getFileName().toString() + ".png";
            ImageIO.write(img, "png", Files.newOutputStream(src.getParent().resolve(fname)));
        }        
    }
    
    private static List toIntegerArray(byte[] b){
        List<Integer> tmp = new ArrayList<>();
        
        for(int i =0;i<b.length;i+=4){
            int pix = 0;
            for(int j=24;j>=0;j-=8,i++){
                if(i<b.length){
                    pix = pix | ((int)b[i])<<j;
                }else{
                    break;
                }
            }
            tmp.add(pix);
        }        
        return tmp;
    }
    
}
