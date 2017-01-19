package edu.java.cosc111.samples;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import javax.swing.JFileChooser;

public class SampleIO {
    public static void main(String[] args) 
                throws IOException{
        JFileChooser jFileChoose = new JFileChooser("D:\\Samples");
        int opt =choose();
        
        if(opt >= 1 && opt <= 3){
            if(jFileChoose.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){
                File f = jFileChoose.getSelectedFile();
                switch(opt){
                    case 1:
                        byteStreamTest(f);
                        break;
                    case 2:
                        charStreamTest(f);
                        break;
                    case 3: 
                        dataStreamTest(f);
                        break;
                }
            }else{
                System.out.println("No file selected.");
            }
        }else{
            System.out.println("Invalid option.");
        }
    } 
    
    private static int choose(){
        System.out.print("What do you want to do?\n\t[1] Read/Write Bytes\n" + 
                            "\t[2] Read/Write Characters\n\t[3] Read/Write Data\nOption: " );
        Scanner in = new Scanner(System.in);
        int opt = in.nextInt();        
        in.close();
        return ((opt >= 1 && opt <= 3) ? opt : -1);
    }
    
    private static void charStreamTest(File f) throws IOException{
            PrintWriter fout = new PrintWriter(
                                new BufferedWriter(
                                    new OutputStreamWriter(                                       
                                          new FileOutputStream(f),StandardCharsets.UTF_8)));
            
            String toWrite = "The quick brown fox jumps over the lazy dog.";
            System.out.println("Writing to '" + f.getAbsolutePath() + "'");            
//            for(String s:toWrite.split("\\s")){
//                fout.print(s);
//            }
            for (int i = 0; i <= 255; i++) {
               fout.println(Integer.toString(i));
            }
            fout.close();
            System.out.println("\nDone writing to '" + f.getAbsolutePath() + "'");
            
            BufferedReader fin =new BufferedReader(
                                    new InputStreamReader(
                                        new BufferedInputStream(
                                            new FileInputStream(f)), "utf-8"));
            String toRead;
            System.out.println("Reading from '" + f.getAbsolutePath() + "'");
            while((toRead=fin.readLine())!=null){
                System.out.println(toRead);
            }
            fin.close();        
            System.out.println("\nDone reading from '" + f.getAbsolutePath() + "'");
            
    }
    
    private static void byteStreamTest(File f) throws IOException{
        
        try(OutputStream out = new BufferedOutputStream(
                            new FileOutputStream(f));){
            System.out.println("Writing to '" + f.getAbsolutePath() + "'");

            byte[] data = new byte[255];
            for (int i = 0; i < 255; i++) {
                data[i] = (byte) i;
            }
            out.write(data);
            out.flush();

        }        
        
//        out.close();
        
        System.out.println("\nDone writing to '" + f.getAbsolutePath() + "'");
        System.out.println("Reading from '" + f.getAbsolutePath() + "'");
        
        try(InputStream in = new BufferedInputStream(
                            new FileInputStream(f),1024);){
            byte[] b = new byte[1024];
            int len;

            while((len=in.read(b))!=-1){
                for(int i=0;i<len;i++){
                    System.out.print((b[i] & 0xff) + " ");
                }            
            }            
        }
        
//        in.close();
        System.out.println("\nDone reading from '" + f.getAbsolutePath() + "'");
    }
    private static void dataStreamTest(File f) throws IOException{
        DataOutputStream fout = new DataOutputStream(
                                    new BufferedOutputStream(
                                        new FileOutputStream(f)));
        System.out.println("Writing to '" + f.getAbsolutePath() + "'");        
        fout.writeUTF("Hello");
        fout.writeChar('!');
        fout.writeDouble(1.0D);
        fout.writeInt(100);
        fout.writeLong(1L);
        fout.close();

        System.out.println("\nDone writing to '" + f.getAbsolutePath() + "'");
        System.out.println("Reading from '" + f.getAbsolutePath() + "'");
        
        DataInputStream fin = new DataInputStream(
                                    new BufferedInputStream(
                                        new FileInputStream(f)));
        System.out.println(fin.readUTF());
        System.out.println(fin.readChar());
        System.out.println(fin.readDouble());
        System.out.println(fin.readInt());
        System.out.println(fin.readLong());
        fin.close();
        System.out.println("\nDone reading from '" + f.getAbsolutePath() + "'");
    }
}