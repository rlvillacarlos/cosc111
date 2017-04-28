package edu.java.cosc111.samples.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 *
 * @author russel
 */
public class FileChannels {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Path p = Paths.get("E:", "samples","sample1","sample1.txt");
        Path p2 = Paths.get("E:", "sample2.txt");
        Files.deleteIfExists(p2);
        Files.createFile(p2);
        try(FileChannel fchIn = FileChannel.open(p);
            FileChannel fchOut = FileChannel.open(p2,StandardOpenOption.WRITE)){
            
            MappedByteBuffer mbb = fchIn.map(FileChannel.MapMode.READ_ONLY, 
                                        0, fchIn.size());
            
//            ByteBuffer buff = ByteBuffer.allocateDirect(1024*10);
//            Charset utf8 = Charset.forName("UTF-8");
////            fchIn.position(4);
////            fchOut.transferFrom(fchIn, 0, fchIn.size());
//            fchIn.transferTo(0, fchIn.size(), fchOut);
//            while(fchIn.read(buff)!=-1){
//                buff.flip();
//                
////                CharBuffer cBuff = utf8.decode(buff);
////                
////                while(cBuff.hasRemaining()){
////                    System.out.print(cBuff.get());
////                }
//                fchOut.write(buff);
//                
//                buff.clear();
//            }
            System.out.println();
        }
    }
    
}
