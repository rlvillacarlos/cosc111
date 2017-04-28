package edu.java.cosc111.samples.games;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author Russel
 */
public class Memory extends javax.swing.JFrame {   
    private Level[] levels;
    private TileIcons[] icons;
    private final int cellSize = 64;
    private final int iconSize = 48;
    private final Path pResDir = Paths.get("./resources/");

    private Level curLevel;
    private TileIcons curIcon;
    private int curIconIndex =0;
    private int curLevelIndex =0;
    private int curCount;
    private long curTime;
    private int[] pieceValues;
    private boolean[] isPaired;
    private JLabel[] jPieces;
    private Timer timer;
    
    private int curIndex = -1;
    private int curValue = -1;
    private JLabel currentLabel = null;
    
    /**
     * Creates new form Memory
     * @throws java.io.IOException
     */
    public Memory() throws IOException {
        initComponents();
        loadLevels();
        loadIcons();
        //createPuzzle(0,curIconIndex);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jdlgServerConfig = new javax.swing.JDialog();
        jPnlServerConfig = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        filler11 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));
        jpnlOptions = new javax.swing.JPanel();
        jpnlLevel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jcmbLevels = new javax.swing.JComboBox<>();
        jpnIcon = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jcmbIcons = new javax.swing.JComboBox<>();
        filler13 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        filler14 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        filler7 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jpnlControl = new javax.swing.JPanel();
        jbtnCancelCreateServer = new javax.swing.JButton();
        filler15 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        jbtnCreateServer = new javax.swing.JButton();
        filler10 = new javax.swing.Box.Filler(new java.awt.Dimension(9, 0), new java.awt.Dimension(9, 0), new java.awt.Dimension(9, 32767));
        filler9 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));
        jPnlPieces = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 100), new java.awt.Dimension(0, 100), new java.awt.Dimension(32767, 100));
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(100, 0), new java.awt.Dimension(100, 0), new java.awt.Dimension(50, 32767));
        panel1 = new java.awt.Panel();
        jLabel1 = new javax.swing.JLabel();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(100, 0), new java.awt.Dimension(100, 0), new java.awt.Dimension(50, 32767));
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 100), new java.awt.Dimension(0, 100), new java.awt.Dimension(32767, 100));
        jPnlTimer = new javax.swing.JPanel();
        lblTime = new javax.swing.JLabel();
        jGameMenuBar = new javax.swing.JMenuBar();
        jMenuGame = new javax.swing.JMenu();
        jMenuNewGame = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuIcons = new javax.swing.JMenu();

        jdlgServerConfig.setTitle("Create Server");
        jdlgServerConfig.setBackground(new java.awt.Color(255, 255, 255));
        jdlgServerConfig.setModal(true);
        jdlgServerConfig.setResizable(false);

        jPnlServerConfig.setLayout(new javax.swing.BoxLayout(jPnlServerConfig, javax.swing.BoxLayout.Y_AXIS));

        jPanel2.setMaximumSize(new java.awt.Dimension(2147483647, 100));
        jPanel2.setPreferredSize(new java.awt.Dimension(200, 50));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        jPanel5.setLayout(new java.awt.BorderLayout());
        jPanel5.add(filler11, java.awt.BorderLayout.NORTH);

        jpnlOptions.setLayout(new javax.swing.BoxLayout(jpnlOptions, javax.swing.BoxLayout.Y_AXIS));

        jpnlLevel.setLayout(new java.awt.BorderLayout());

        jLabel3.setText("Level:");
        jLabel3.setMaximumSize(new java.awt.Dimension(40, 14));
        jLabel3.setMinimumSize(new java.awt.Dimension(40, 14));
        jLabel3.setPreferredSize(new java.awt.Dimension(40, 14));
        jpnlLevel.add(jLabel3, java.awt.BorderLayout.WEST);

        jcmbLevels.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jcmbLevels.setPreferredSize(new java.awt.Dimension(200, 20));
        jpnlLevel.add(jcmbLevels, java.awt.BorderLayout.CENTER);

        jpnlOptions.add(jpnlLevel);

        jpnIcon.setLayout(new java.awt.BorderLayout());

        jLabel4.setText("Icon:");
        jLabel4.setMaximumSize(new java.awt.Dimension(40, 14));
        jLabel4.setMinimumSize(new java.awt.Dimension(40, 14));
        jLabel4.setPreferredSize(new java.awt.Dimension(40, 14));
        jpnIcon.add(jLabel4, java.awt.BorderLayout.WEST);

        jcmbIcons.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jcmbIcons.setPreferredSize(new java.awt.Dimension(200, 20));
        jpnIcon.add(jcmbIcons, java.awt.BorderLayout.CENTER);

        jpnlOptions.add(jpnIcon);

        jPanel5.add(jpnlOptions, java.awt.BorderLayout.CENTER);
        jPanel5.add(filler13, java.awt.BorderLayout.WEST);
        jPanel5.add(filler14, java.awt.BorderLayout.EAST);

        jPanel2.add(jPanel5);

        jPnlServerConfig.add(jPanel2);
        jPnlServerConfig.add(filler7);

        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.Y_AXIS));

        jPanel4.setLayout(new java.awt.BorderLayout());

        jpnlControl.setMinimumSize(new java.awt.Dimension(161, 10));
        jpnlControl.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 0, 0));

        jbtnCancelCreateServer.setText("Cancel");
        jbtnCancelCreateServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCancelCreateServerActionPerformed(evt);
            }
        });
        jpnlControl.add(jbtnCancelCreateServer);
        jpnlControl.add(filler15);

        jbtnCreateServer.setText("Create");
        jbtnCreateServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCreateServerActionPerformed(evt);
            }
        });
        jpnlControl.add(jbtnCreateServer);
        jpnlControl.add(filler10);

        jPanel4.add(jpnlControl, java.awt.BorderLayout.SOUTH);

        jPanel3.add(jPanel4);
        jPanel3.add(filler9);

        jPnlServerConfig.add(jPanel3);

        jdlgServerConfig.getContentPane().add(jPnlServerConfig, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Memory");
        setResizable(false);

        jPnlPieces.setBackground(new java.awt.Color(255, 255, 255));
        jPnlPieces.setEnabled(false);
        jPnlPieces.setLayout(new java.awt.BorderLayout());
        //gameUILayout = new java.awt.GridLayout();
        //jPnlPieces.setLayout(gameUILayout);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());
        jPanel1.add(filler2, java.awt.BorderLayout.PAGE_START);
        jPanel1.add(filler4, java.awt.BorderLayout.LINE_START);

        panel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Memory");
        panel1.add(jLabel1, java.awt.BorderLayout.CENTER);

        jPanel1.add(panel1, java.awt.BorderLayout.CENTER);
        jPanel1.add(filler5, java.awt.BorderLayout.LINE_END);
        jPanel1.add(filler1, java.awt.BorderLayout.PAGE_END);

        jPnlPieces.add(jPanel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPnlPieces, java.awt.BorderLayout.CENTER);

        jPnlTimer.setBackground(new java.awt.Color(255, 255, 255));

        lblTime.setText("00:00");
        jPnlTimer.add(lblTime);

        jPnlTimer.setVisible(false);

        getContentPane().add(jPnlTimer, java.awt.BorderLayout.NORTH);

        jMenuGame.setText("Game");

        jMenuNewGame.setText("Solo Mode");
        jMenuGame.add(jMenuNewGame);

        jMenu3.setText("2 Player Mode");

        jMenuItem2.setText("Server");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem2);

        jMenuItem3.setText("Client");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem3);

        jMenuGame.add(jMenu3);

        jGameMenuBar.add(jMenuGame);

        jMenu1.setText("Setting");

        jMenuIcons.setText("Icons");
        jMenu1.add(jMenuIcons);

        jGameMenuBar.add(jMenu1);

        setJMenuBar(jGameMenuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
//        startServer();
        jcmbLevels.setSelectedIndex(0);
        jcmbIcons.setSelectedIndex(0);
        jdlgServerConfig.setSize(300, 150);
        jdlgServerConfig.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        startClient();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jbtnCreateServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCreateServerActionPerformed
        jdlgServerConfig.setVisible(false);
        try {
            System.out.println("Memory - (Server:" + InetAddress.getLocalHost()+")");
            setTitle("Waiting for connection...");
            createPuzzle(jcmbLevels.getSelectedIndex(), jcmbIcons.getSelectedIndex(),true);
            startServer();
            setTitle("Memory");
        } catch (UnknownHostException ex) {
            String msg = String.format("An error occured while creating server - %s", ex.getMessage());
            JOptionPane.showMessageDialog(this, msg,this.getTitle(),JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jbtnCreateServerActionPerformed

    private void jbtnCancelCreateServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCancelCreateServerActionPerformed
       jdlgServerConfig.setVisible(false);
    }//GEN-LAST:event_jbtnCancelCreateServerActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Memory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Memory().setVisible(true);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    
    private void loadIcons() throws IOException{       
        TileIcons[] icons = { new TileIcons("Smileys",ImageIO.read(pResDir.resolve("images/pieces1").toFile()),120, 121),
                              new TileIcons("Flags",ImageIO.read(pResDir.resolve("images/pieces2").toFile()),64,64),
                              new TileIcons("Gems",ImageIO.read(pResDir.resolve("images/pieces3").toFile()),64,64)
                            };        
        this.icons = icons;
        jcmbIcons.removeAllItems();
        for(int i = 0;i<this.icons.length;i++){
            JMenuItem mnuItem = new JMenuItem();
            jcmbIcons.addItem(icons[i].label);
            mnuItem.setText(icons[i].label);
            final int iconIndex = i;
            mnuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    createPuzzle(curLevelIndex, iconIndex,true);
                    startGame();
                }
            });
            jMenuIcons.add(mnuItem);
        }    
    }
    
    private void loadLevels(){
        Level[] levels = {new Level(4, 4,60),new Level(6, 4,120),
                          new Level(6, 6,180),new Level(8, 6,300)};        
        this.levels = levels;
        int i = 0;
        jcmbLevels.removeAllItems();
        for(Level l:this.levels){
            JMenuItem mnuItem = new JMenuItem();
            String caption =  String.format("%d x %d",l.rows , l.cols);
            jcmbLevels.addItem(caption);
            mnuItem.setText(caption);
            final int lvl = i;
            mnuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    createPuzzle(lvl, curIconIndex,true);
                    createRandomTiles();
                    startGame();
                }
            });
            i++;
            jMenuNewGame.add(mnuItem);
        }
        
    }
    private void startClient(){
        String host = JOptionPane.showInputDialog(this,"Server: ",this.getTitle(),JOptionPane.INFORMATION_MESSAGE);
        
        try(Socket player1 = new Socket();){
            player1.connect(new InetSocketAddress(host,1024));
            try(DataInputStream in = new DataInputStream(
                                    player1.getInputStream());){
                int level = in.readInt();
                int icon = in.readInt();
                System.out.println(level+ " " + icon );
                createPuzzle(level, icon,false);
                for(int i=0;i<pieceValues.length;i++){
                    pieceValues[i] = in.readInt();
                }
            }
            startGame();
        } catch (IOException ex) {
            String msg = String.format("An error occured while connecting to server - %s", ex.getMessage());            
            JOptionPane.showMessageDialog(this, msg,this.getTitle(),JOptionPane.ERROR_MESSAGE);
        }
        
    }
    private void startServer(){
        try(ServerSocket server = new ServerSocket()){
            server.bind(new InetSocketAddress(1024));
            Socket player2 = server.accept();
            
            ByteArrayOutputStream bOut = new ByteArrayOutputStream();
            DataOutputStream dOut = new DataOutputStream(bOut);
            dOut.writeInt(curLevelIndex);
            dOut.writeInt(curIconIndex);
            for(int piece:pieceValues){
                dOut.writeInt(piece);
            }
            
            byte[] data = bOut.toByteArray();
            
            try(OutputStream out = player2.getOutputStream();){    
                out.write(data);
            }
            startGame();
        } catch (IOException ex) {
            Logger.getLogger(Memory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    
    private void initPuzzle(){
        //Set the current level
        curLevel = levels[curLevelIndex];
        //Set the current image set
        curIcon = icons[curIconIndex];
        //Set the current count
        curCount = curLevel.pieceCount;
        //Set the current time
        curTime = curLevel.time;
        //Create new pieces
        pieceValues = new int[curCount];
        
        isPaired = new boolean[curCount];
    }
    
    private void createPuzzle(int levelIndex,int iconIndex, boolean createRandomTiles){    
        if(levelIndex<0 || levelIndex >= levels.length || iconIndex <0 || iconIndex > icons.length)
            return;
        
        curLevelIndex = levelIndex;
        curIconIndex = iconIndex;
        
        initPuzzle();     
        loadTimer();
        
        if(createRandomTiles){
            createRandomTiles();
        }
        
    }
    
    private void createRandomTiles(){
        Random rnd = new Random(System.currentTimeMillis());
        
        //Fill array with random pairs
        for(int i = 0;i<curCount;i+=2){
            int e = rnd.nextInt(curIcon.iconCount);
            pieceValues[i] = e;
            pieceValues[curCount-1-i] = e;            
        }   
        
        //Randomly permute the array of pairs
        rnd = new Random(System.currentTimeMillis());
        for(int i = pieceValues.length-1;i>0;i--){
            int j =rnd.nextInt(i+1);
            int tmp = pieceValues[j];            
            pieceValues[j] = pieceValues[i];
            pieceValues[i] = tmp;
        }
    }
    
    private void startGame(){
        //Set-up the GUI
        
        //Remove all previously added component
        jPnlPieces.removeAll();
        //Set the new layout        
        jPnlPieces.setLayout(new java.awt.GridLayout(curLevel.rows, curLevel.cols));
        
        jPieces = new JLabel[curCount];
        
        for(int i =0;i<pieceValues.length;i++){
            //Compute the x and y position of the subimage
            int y = (pieceValues[i]/curIcon.cols);// * imageHeight;
            int x = (pieceValues[i]%curIcon.cols);// * imageWidth;

            //Get the subimage
            Image icon = curIcon.getIconAt(x, y).getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);            
            //Create JLabel with subimage as icon
            jPieces[i] = new JLabel(new ImageIcon(icon));
            //Hide the label
            jPieces[i].setVisible(false);
            //Create a panel for the label
            JPanel pnl = new JPanel(new BorderLayout());
            //Set the border of the panel
            pnl.setBorder(new EtchedBorder());
            //Set-up the mouse listener for handling mouse events
            pnl.addMouseListener(new CellMouseAdapter(i,jPieces[i], pieceValues[i]));
            //Change teh background color of the panel
            pnl.setBackground(Color.WHITE);
            //Add the JLabel at the center of panel
            pnl.add(jPieces[i],java.awt.BorderLayout.CENTER);
            //Add the panel to the main panel
            jPnlPieces.add(pnl);
        }
        pack();
        jPnlPieces.doLayout();
    
        //Set the new size of the frame
        setSize(cellSize *curLevel.cols , cellSize*curLevel.rows + jPnlTimer.getHeight());

        startTimer();
    }
    
    private void loadTimer(){
        if(timer !=null){
            timer.stop();
        }
        jPnlTimer.setVisible(true);        
        lblTime.setText(String.format("%02d:%02d",curTime/60,curTime%60));        
    }
    
    private void startTimer(){
        timer = new Timer();
        new Thread(timer).start();
    }
    
    private void revealPieces(){
        for(int i=0;i<jPieces.length;i++){            
            if(!isPaired[i]){
                isPaired[i]=true;
                jPieces[i].setEnabled(false);
                jPieces[i].setVisible(true);                
            }
        }
    }
    
    private class Timer implements Runnable{
        private volatile boolean bStop = false;
        
        public void stop(){
            bStop = true;
        }
        
        @Override
        public void run() {
            while(curTime>0  && !bStop){
                curTime--;
                lblTime.setText(String.format("%02d:%02d",curTime/60,curTime%60));        
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {}
            }
            if(!bStop){
                JOptionPane.showMessageDialog(null, "Time is up! Try again next time.");
                revealPieces();                
            }
        }        
    }
    
    private class CellMouseAdapter extends MouseAdapter{
        final int pieceValue;
        final int pieceIndex;
        final JLabel pieceLabel;
        
        boolean isClicked = false;
        
        public CellMouseAdapter(int index,JLabel label, int value) {
            pieceLabel = label;
            pieceValue = value;
            pieceIndex = index;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            pieceLabel.setVisible(true);            
            isClicked = true;
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
            if(!isClicked || isPaired[pieceIndex]){
                return;
            }
            if(curValue==-1){
                curValue = pieceValue;
                curIndex = pieceIndex;
                currentLabel = pieceLabel;
            }else {
                if(curValue != pieceValue){
                    currentLabel.setVisible(false);
                    pieceLabel.setVisible(false);
                }else{
                    isPaired[curIndex]=true;
                    isPaired[pieceIndex]=true;
                    curCount-=2;
                    if(curCount==0){
                        timer.stop();
                        JOptionPane.showMessageDialog(null, "Congratulations!");
                    }
                }
                curIndex = -1;
                curValue = -1;
                currentLabel = null;
            }
            isClicked = false;
        }
    }
    
    private class Level{
        private final int rows;
        private final int cols;
        private final int pieceCount;
        private final long time;
        
        Level(int rows_,int cols_,long time_){
            rows = rows_;
            cols = cols_;
            time = time_;
            pieceCount = rows * cols;
        }
    }
    private class TileIcons{        
        private final String label;
        private final int iconHeight;
        private final int iconWidth;
        private final BufferedImage image;
        private final int rows;
        private final int cols;
        private final int iconCount;
        
        public TileIcons(String label, BufferedImage image,int iconWidth, int iconHeight) {
            if(image == null || image.getWidth() <= 0 || image.getHeight()<=0 || iconWidth<=0 || iconHeight<=0)
                throw new IllegalArgumentException();
            this.label = label;
            this.iconHeight = iconHeight;
            this.iconWidth = iconWidth;
            this.image = image;
            this.rows = image.getHeight()/iconHeight;
            this.cols = image.getWidth()/iconWidth;
            this.iconCount = rows * cols;
        }
        
        public Image getIconAt(int x,int y){
            if(y<0 || y>=rows ||x<0 || x>=cols)
                throw new IllegalArgumentException("Invalid row and col value");
            return image.getSubimage(x * iconWidth, y * iconHeight, iconWidth, iconHeight);
        }
        
    }
//    private java.awt.GridLayout gameUILayout;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler10;
    private javax.swing.Box.Filler filler11;
    private javax.swing.Box.Filler filler13;
    private javax.swing.Box.Filler filler14;
    private javax.swing.Box.Filler filler15;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler7;
    private javax.swing.Box.Filler filler9;
    private javax.swing.JMenuBar jGameMenuBar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenuGame;
    private javax.swing.JMenu jMenuIcons;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenu jMenuNewGame;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPnlPieces;
    private javax.swing.JPanel jPnlServerConfig;
    private javax.swing.JPanel jPnlTimer;
    private javax.swing.JButton jbtnCancelCreateServer;
    private javax.swing.JButton jbtnCreateServer;
    private javax.swing.JComboBox<String> jcmbIcons;
    private javax.swing.JComboBox<String> jcmbLevels;
    private javax.swing.JDialog jdlgServerConfig;
    private javax.swing.JPanel jpnIcon;
    private javax.swing.JPanel jpnlControl;
    private javax.swing.JPanel jpnlLevel;
    private javax.swing.JPanel jpnlOptions;
    private javax.swing.JLabel lblTime;
    private java.awt.Panel panel1;
    // End of variables declaration//GEN-END:variables
}
