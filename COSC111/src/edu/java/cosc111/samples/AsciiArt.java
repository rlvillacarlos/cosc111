package edu.java.cosc111.samples;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Russel
 */
public class AsciiArt {
    private static final int BASE=33;
    private static final int GROUP_SIZE = 8;
    private static final int MAX_HEIGHT = 1000;
    private static final int MAX_WIDTH  = 1000;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFileChooser jFileChoose = new JFileChooser("D:\\Server Files\\COSC111\\misc\\images");
        
        if(jFileChoose.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
            try {
                File f = jFileChoose.getSelectedFile();
                BufferedImage img = ImageIO.read(f);
                if(img==null){
                    throw new IOException("Failed to read image file.");
                }
                jFileChoose.setSelectedFile(new File(f.getAbsolutePath()+ ".txt"));
                if(jFileChoose.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){
                    try{
                        saveAsAsciiArt(img, jFileChoose.getSelectedFile());
                        JOptionPane.showMessageDialog(null, "ASCII Art Created!",
                                    "ASCII Art",JOptionPane.INFORMATION_MESSAGE);                        
                    }catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(),
                                        "ASCII Art",JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    System.out.println("File creation cancelled.");
                }                
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(),
                                "Error reading image",JOptionPane.ERROR_MESSAGE);
            }
        }else{
            System.out.println("No file selected.");
        }
    }
        
    
    private static void saveAsAsciiArt(BufferedImage img,File f) throws IOException{
        int h = img.getHeight(), w = img.getWidth();
        System.out.println("height: " + h + ", width: " + w);
        if(h>MAX_HEIGHT || w >MAX_WIDTH){
            if(JOptionPane.showConfirmDialog(
                            null,"Image too big. Do you want to donwsample image first?",
                            "ASCII Art", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                img = downSample(img);
                ImageIO.write(img, "jpg", new File(f.getAbsolutePath()+".jpg"));
                h = img.getHeight();
                w = img.getWidth();                
                System.out.println("downsampled height: " + h + ", downsampled width: " + w);
            }
        }
        Graphics2D g =  (Graphics2D) img.getGraphics();
        Font fnt = new Font(Font.MONOSPACED,Font.PLAIN,10);          
        g.setFont(fnt);
        FontRenderContext ctxt = g.getFontRenderContext();        
        List<TextLayout> layouts = new ArrayList<>();
        
        try(OutputStream out = new BufferedOutputStream(new FileOutputStream(f))){
            int layoutH =0;
            int layoutW =0;
            for(int y = 0;y < h;y++){
                CharArrayWriter ch = new CharArrayWriter();
                for(int x = 0; x < w ;x++){
                    Color rgb= new Color(img.getRGB(x, y));
                    int gray = (rgb.getBlue() + rgb.getRed() + rgb.getGreen())/3;    
                    gray = (gray / GROUP_SIZE) + BASE;
                    ch.write(gray);
                    out.write(gray);                    
                }                
                TextLayout layout = new TextLayout(new String(ch.toCharArray()), fnt, ctxt);
                layouts.add(layout);
                Rectangle2D bounds =  layout.getBounds();
                if(bounds.getWidth()>layoutW){
                    layoutW = (int)bounds.getWidth();
                }
                layoutH += (int)bounds.getHeight();
                out.write('\r');
                out.write('\n');
            }
            BufferedImage imgNew = new BufferedImage(layoutW,layoutH,BufferedImage.TYPE_INT_RGB);            
            g =  (Graphics2D) imgNew.getGraphics();            
            g.setFont(fnt);
            g.setBackground(Color.white);
            g.clearRect(0, 0, layoutW, layoutH);
            g.setColor(Color.RED);
            int yPos=0;
            for(TextLayout t:layouts){
                t.draw(g, 0, yPos);
                yPos += t.getBounds().getHeight();
            }
            ImageIO.write(imgNew, "png",new File(f.getAbsolutePath()+".png"));
        }        
    }
    
    private static int average(int... args) {
        if (args != null && args.length > 0) {
            int rsum = 0, gsum = 0, bsum = 0;
            for (int i : args) {
                Color c = new Color(i);
                rsum += c.getRed();
                bsum += c.getBlue();
                gsum += c.getGreen();
            }
            return new Color(rsum / args.length,
                    gsum / args.length,
                    bsum / args.length).getRGB();
        }
        return 0;
    }

    private static BufferedImage downSample(BufferedImage img) {
        int nImgHeight = img.getHeight(),nImgWidth = img.getWidth();
        double hPercent = 1.0D;
        double wPercent = 1.0D;
        double perc;
        
        
        if(nImgHeight<=MAX_HEIGHT && nImgWidth <=MAX_WIDTH){
            return img;
        }        
        if(nImgHeight>MAX_HEIGHT){
            hPercent = MAX_HEIGHT/(double)img.getHeight();            
        }
        if(nImgWidth>MAX_WIDTH){
            wPercent = MAX_WIDTH/(double)img.getWidth();            
        }
        
        if(wPercent < hPercent){
            perc = wPercent;
        }else{
            perc = hPercent;
        }
        
        BufferedImage tmp = new BufferedImage(
                                (int)Math.ceil(nImgWidth * perc),
                                (int)Math.ceil(nImgHeight * perc), 
                                BufferedImage.TYPE_INT_RGB);

        int[] square = new int[4];
        for (int y = 0; y < nImgHeight; y ++) {
            for (int x = 0; x < nImgWidth; x ++) {
                square[0] = img.getRGB(x, y);
                square[1] = x + 1 < nImgWidth
                        ? img.getRGB(x + 1, y)
                        : square[0];
                square[2] = y + 1 < nImgHeight
                        ? img.getRGB(x, y + 1)
                        : square[0];
                square[3] = (y + 1 < nImgHeight && x + 1 < nImgWidth)
                        ? img.getRGB(x + 1, y + 1)
                        : square[0];
                tmp.setRGB((int)(x * perc), (int)(y * perc), average(square));
            }
        }
        return tmp;
    }   
}
