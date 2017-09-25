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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import javax.swing.JFileChooser;

public class SampleIO {
    public static void main(String[] args) throws IOException{
        JFileChooser jFileChoose = new JFileChooser("E:\\");
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
            
            try(PrintWriter out= new PrintWriter(
                                    new BufferedWriter(
                                        new OutputStreamWriter(
                                            new BufferedOutputStream(
                                                new FileOutputStream(f)),"UTF-8")))){
                
                String toWrite = "The quick brown fox jumps over the lazy dog.";
                String[] toWriteArr = toWrite.split(" ");
                
                for(int i =1; i<=100;i++){
                    System.out.printf("%1$d, %1$d%n",i ,i);
//                    out.printf("(%2$d, %1$d), ",i,i+1);
                }
                
                System.out.println("Writing to '" + f.getAbsolutePath() + "'");   
                
                System.out.println("\nDone writing to '" + f.getAbsolutePath() + "'");
            }
            
            try(BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(f),"UTF-8"))){
//                char[] toRead = new char[10];
                String toRead;
                int len;
                
                while((toRead=in.readLine())!=null){
                    System.out.println(toRead);

                }
               System.out.println("\nDone reading from '" + f.getAbsolutePath() + "'");
            }
    }
    
    private static void byteStreamTest(File f) throws IOException{
        
        try(OutputStream out = new BufferedOutputStream(     new FileOutputStream(f));){
            System.out.println("Writing to '" + f.getAbsolutePath() + "'");

            byte[] data = new byte[256];
            for (int i = 0; i < data.length; i++) {
                data[i] = (byte) i;
            }
            out.write(data);
            out.flush();
        }        
        
//        out.close();
        
        System.out.println("\nDone writing to '" + f.getAbsolutePath() + "'");
        System.out.println("Reading from '" + f.getAbsolutePath() + "'");
        
        try(InputStream in = new BufferedInputStream(
                                new FileInputStream(f));){
            byte[] b = new byte[1024];
            int len;
            //  1111111110000000 
            //& 0000000011111111
            //------------------
            //  0000000010000000 
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
        try(DataOutputStream fout = new DataOutputStream(
                                    new BufferedOutputStream(
                                        new FileOutputStream(f)));){
            System.out.println("Writing to '" + f.getAbsolutePath() + "'");        
            fout.writeUTF("Hello");
            fout.writeChar('!');
            fout.writeDouble(1.0D);
            fout.writeInt(100);
            fout.writeLong(2L);
            
//        fout.close();
        }

        System.out.println("\nDone writing to '" + f.getAbsolutePath() + "'");
        System.out.println("Reading from '" + f.getAbsolutePath() + "'");
        
        try(DataInputStream fin = new DataInputStream(
                                    new BufferedInputStream(
                                        new FileInputStream(f)));){
            System.out.println(fin.readUTF());
            System.out.println(fin.readChar());
            System.out.println(fin.readDouble());
            System.out.println(fin.readInt());
            System.out.println(fin.readLong());
        }
//        fin.close();
        System.out.println("\nDone reading from '" + f.getAbsolutePath() + "'");
    }
}