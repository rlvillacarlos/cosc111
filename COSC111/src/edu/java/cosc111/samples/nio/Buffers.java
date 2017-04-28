package edu.java.cosc111.samples.nio;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.IntBuffer;

/**
 *
 * @author russel
 */
public class Buffers {
    public static void main(String[] args) throws UnsupportedEncodingException {
        byte[] b = {(byte)0xFF,(byte)0xFE,(byte)0xFD,
                    (byte)0xFC,(byte)0xFB
                    };
        
        ByteBuffer buff = ByteBuffer.wrap(b);
        System.out.println("-Byte Buffer-");
        System.out.printf("Is Direct: %b%n",buff.isDirect());
        System.out.printf("Limit: %d%n",buff.limit());
        System.out.printf("Capacity: %d%n",buff.capacity());
        
        while(buff.hasRemaining()){
            System.out.printf("Position(%d): %d %n",
                                buff.position(),
                                buff.get() & 0x00FF );
        }
        
        System.out.println("\n-CharacterBuffer-");
        char[] c = {'a','b','c','d','e'};
        buff = ByteBuffer.wrap("Hello".getBytes("UTF-16"));
        CharBuffer cBuff = buff.asCharBuffer();//CharBuffer.wrap(c);
        System.out.printf("Limit: %d%n",cBuff.limit());
        System.out.printf("Capacity: %d%n",cBuff.capacity());
        
        while(cBuff.hasRemaining()){
            System.out.printf("Position(%d): %c %n",
                                cBuff.position(),
                                cBuff.get());
        }
        
        System.out.println("\n-IntegerBuffer-");
        IntBuffer iBuff = IntBuffer.allocate(10);
        System.out.printf("Limit: %d%n",iBuff.limit());
        System.out.printf("Capacity: %d%n",iBuff.capacity());
        
        for(int i=1;i<=5;i++){
            iBuff.put(i);
        }
        
        System.out.println("\nRead buffer without flipping");
        while(iBuff.hasRemaining()){
            System.out.printf("Position(%d): %d %n",
                    iBuff.position(),
                    iBuff.get());
        }
        
        iBuff.clear();
        for(int i=1;i<=5;i++){
            iBuff.put(i);
        }
        
        
        System.out.println("\nRead buffer after rewind");
        iBuff.rewind();
        
        while(iBuff.hasRemaining()){
            System.out.printf("Position(%d): %d %n",iBuff.position(),iBuff.get());
//            System.out.println();
        }
        
        iBuff.clear();
        for(int i=1;i<=5;i++){
            iBuff.put(i);
        }
        
        
        System.out.println("\nRead buffer after flip");
        iBuff.flip();
        
        while(iBuff.hasRemaining()){
            System.out.printf("Position(%d): %d %n",iBuff.position(),iBuff.get());
        }
        
        System.out.println("\nRead buffer after setting limit and rewind ");
        iBuff.limit(3);
        iBuff.rewind();
        while(iBuff.hasRemaining()){
            System.out.printf("Position(%d): %d %n",iBuff.position(),iBuff.get());
        }
        
        System.out.println("");
        iBuff.position(1);
        iBuff.mark();
        iBuff.position(2);
        iBuff.reset();
        while(iBuff.hasRemaining()){
            System.out.printf("Position(%d): %d %n",iBuff.position(),iBuff.get());
        }
        
    }
}
