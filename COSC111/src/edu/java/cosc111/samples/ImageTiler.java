package edu.java.cosc111.samples;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.imageio.ImageIO;

/**
 *
 * @author Russel
 */
public class ImageTiler {
    private static int maxHeight =0;
    private static int maxWidth =0;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.print("Directory: ");        
        String dirPath = in.nextLine();
        System.out.print("Rows: ");
        int rows = in.nextInt();
        System.out.print("Cols: ");
        int cols = in.nextInt();
        
        if(cols <=0 || rows <= 0){
            System.err.println("Illegal rows/colums value.");
            return;
        }        
        List<BufferedImage> images = getImages(Paths.get(dirPath));
        
        if(images.isEmpty()){
            System.err.println("Directory does not contain images.");
            return;
        }
        System.out.println(images.size());
        BufferedImage tiledImage = createTiledImage(cols * maxWidth, rows * maxHeight, images);
        ImageIO.write(tiledImage,"png" ,Paths.get(dirPath,"tiled.png").toFile());
    }
    
    private static BufferedImage createTiledImage(int width,int height,List<BufferedImage> images){
        BufferedImage tiledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) tiledImage.getGraphics();
        
        int x = 0, y = 0;
        
        g.setBackground(Color.WHITE);
        for(BufferedImage img:images){
            g.drawImage(img,x,y, null);
            x += maxWidth;
            if(x == width){
                x = 0;
                y += maxHeight;
            } 
        }
        
        
        return tiledImage;
    }
    
    private static List<BufferedImage> getImages(Path dir) throws IOException{
        String glob = "*.{jpg,jpeg,bmp,gif,png}";
        List<BufferedImage> images = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir,glob)){
            for (Path imgPath : stream) {
                BufferedImage img = ImageIO.read(imgPath.toFile());
                if(img.getWidth()>maxWidth){
                    maxWidth = img.getWidth();
                }
                if(img.getHeight()>maxHeight){
                    maxHeight = img.getHeight();
                }
                images.add(img);
            }
        }
        return images;
    }
}
