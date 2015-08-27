package edu.java.cosc111.samples;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.file.Path;
import static java.nio.file.StandardWatchEventKinds.*;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import javax.swing.JFileChooser;

/**
 *
 * @author Russel
 */
public class DirectoryWatcher {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFileChooser jChooser = new JFileChooser("D:\\");        
        jChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if(jChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
            File f = jChooser.getSelectedFile();
            System.out.println(f);
            Path dir = f.toPath();
            System.out.println("Watching " + f.getAbsolutePath());
            try {
                WatchService watcher = dir.getFileSystem().newWatchService();
                dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

                while (true) {
                    WatchKey key;
                    try {
                        key = watcher.take();
                    } catch (InterruptedException ex) {
                        break;
                    }
                    
                    for (WatchEvent<?> event : key.pollEvents()) {
                        WatchEvent.Kind<?> kind = event.kind();

                        if (kind == OVERFLOW) {
                            continue;
                        }
                        WatchEvent<Path> ev = (WatchEvent<Path>) event;
                        Path target = dir.resolve(ev.context());                       
                        System.out.println("Event:   " + kind.name());
                        System.out.println("Target:  " + target);


                    }
                    boolean valid = key.reset();
                    if (!valid) {
                        break;
                    }
                }
            } catch (UnsupportedOperationException | IOException ex) {
                ex.printStackTrace();
            }
        }
    } 
}
