package edu.java.cosc111.samples;

import java.awt.Component;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author Russel
 */
public class DirectoryExplorer extends javax.swing.JFrame {
    private static final long[] limit= new long[6];
    private static final String[] unit={"KB", "MB","GB","TB","PB","EB"};

    private static final FileSystemView fs = FileSystemView.getFileSystemView(); 
    private static final FileSystem fsys = FileSystems.getDefault();
        
    private static class ModifiedTreeRenderer extends DefaultTreeCellRenderer{
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, 
                                    boolean sel, boolean expanded, 
                                    boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, sel, 
                                            expanded, leaf, row, hasFocus);             
            
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            
            Path p = treeNodesToFilePath(node.getPath());
            setIcon(fs.getSystemIcon(p.toFile()));
            return this;
        }
        
    }
    private static class ModifiedCellRenderer extends DefaultTableCellRenderer{

        @Override
        public Component getTableCellRendererComponent(JTable table, 
                                                        Object value, 
                                                        boolean isSelected, 
                                                        boolean hasFocus, 
                                                        int row, int column) {
            setIcon(null);
            if(value instanceof JLabel){
                JLabel val = (JLabel) value;
                String newValue = val.getText();
                setIcon(val.getIcon());
                return super.getTableCellRendererComponent(table, newValue, 
                                                           isSelected,hasFocus, 
                                                           row, column); 
            }else if(value instanceof Long){
                String newValue = formatFileSize((Long)value);
                return super.getTableCellRendererComponent(table, newValue, 
                                                           isSelected,hasFocus, 
                                                           row, column);
            }else if(value instanceof FileTime){
                FileTime val = (FileTime) value;
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(val.toMillis());
                String newValue = String.format("%1$tm/%1$td/%1$tY %1$tr",cal);
                return super.getTableCellRendererComponent(table, newValue, 
                                                           isSelected,hasFocus, 
                                                           row, column);
            }            
            return super.getTableCellRendererComponent(table, value, isSelected, 
                                                        hasFocus, row, column);             
        }
        
    }
    private static String formatFileSize(long sz){
        int i=0;
        for(long l:limit){
            if(sz < l){
                break;
            }
            i++;
        }
        if(i==0)
            return sz + "bytes";
        else if(i==limit.length)
            i--;
        return String.format("%.2f %s",((double)sz/limit[i-1]) ,unit[i-1]);
    }
    
    private static Path treeNodesToFilePath(TreeNode[] nodes){
        Path p = Paths.get("");
        for(int i = 1;i<nodes.length;i++){
            p = p.resolve(nodes[i].toString());
        }        
        return p;
    } 
    
    private void fillFileList(TreePath tPath){
        DefaultMutableTreeNode cur = (DefaultMutableTreeNode) 
                                        tPath.getLastPathComponent();
        if(tPath.getPathCount()>1){      
            Path p = treeNodesToFilePath(cur.getPath());
            try {
                DefaultTableModel tblModel = (DefaultTableModel)jTableContent.getModel();
                while(tblModel.getRowCount()>0){
                    tblModel.removeRow(0);
                }
                int i = 0;
                for(Path o:Files.newDirectoryStream(p)){
                    List<Object> values = new ArrayList<>();
                    JLabel lbl = new JLabel(o.getFileName().toString(),
                                            fs.getSystemIcon(o.toFile()), 
                                            JLabel.LEFT);                    
                    values.add(lbl);
                    BasicFileAttributes attr = Files.readAttributes(o, 
                                            BasicFileAttributes.class,
                                            LinkOption.NOFOLLOW_LINKS);
                    if(Files.isDirectory(o, LinkOption.NOFOLLOW_LINKS)){
                        values.add("");
                    }else{
                        values.add(attr.size());
                    }
                    values.add(attr.creationTime());
                    values.add(attr.lastAccessTime());
                    values.add(attr.lastModifiedTime());
                    tblModel.addRow(values.toArray());
                    i++;                    
                }
            }catch (IOException ex) {
            }
        }          
    }
    
    private void fillDirectoryTree(TreePath tPath){
        DefaultMutableTreeNode cur = (DefaultMutableTreeNode) 
                                        tPath.getLastPathComponent();                     
        if(tPath.getPathCount()>1 && 
                ((DefaultMutableTreeNode)cur.getChildAt(0)).getUserObject()==null){      
            cur.remove(0);
            Path p = treeNodesToFilePath(cur.getPath());
            try {
                int i=0;
                for(Path o:Files.newDirectoryStream(p)){
                    if(Files.isDirectory(o, LinkOption.NOFOLLOW_LINKS)){
                        DefaultMutableTreeNode n = new DefaultMutableTreeNode();                            
                        n.add(new DefaultMutableTreeNode(null));
                        n.setUserObject(o.getFileName());
                        cur.insert(n, i++);                                
                    }                                
                }
            }catch (IOException ex) {
            }
        }          
    }
    private void initFileList(){
        String[] labels = {"Filename","Size","Created", 
                            "Accessed", "Modified","Attributes"};
        DefaultTableModel tblModel = new DefaultTableModel();
        jTableContent.setModel(tblModel);
        for(String s:labels){
            tblModel.addColumn(s);
        }        
        jTableContent.setDefaultEditor(Object.class, null);
        jTableContent.setDefaultRenderer(Object.class,new ModifiedCellRenderer());
    }
    
    private void initDirectoryTree(){
        DefaultMutableTreeNode nRoot = new DefaultMutableTreeNode("My Computer");
        DefaultTreeModel treeModel = new DefaultTreeModel(nRoot);        
        jTreeExplorer = new JTree(treeModel);
        jTreeExplorer.getSelectionModel().setSelectionMode(
                        TreeSelectionModel.SINGLE_TREE_SELECTION);
        jTreeExplorer.setCellRenderer(new ModifiedTreeRenderer());
        
        for(Path root:fsys.getRootDirectories()){
            DefaultMutableTreeNode nRootDir = new DefaultMutableTreeNode(root.toString(),true);
            nRoot.add(nRootDir);
            nRootDir.add(new DefaultMutableTreeNode(null));
        }
        jTreeExplorer.expandRow(0);
    }    
    
    /**
     * Creates new form Explorer
     */
    public DirectoryExplorer() {
        long x = 1;
        for(int i=0;i<limit.length;i++){
            x = x<<10;
            limit[i] = x;
        }
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTreeExplorer = null;
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableContent = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Explorer");

        jSplitPane1.setDividerSize(2);

        initDirectoryTree();
        jTreeExplorer.addTreeWillExpandListener(new javax.swing.event.TreeWillExpandListener() {
            public void treeWillCollapse(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException {
            }
            public void treeWillExpand(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException {
                jTreeExplorerTreeWillExpand(evt);
            }
        });
        jTreeExplorer.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTreeExplorerValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jTreeExplorer);

        jSplitPane1.setLeftComponent(jScrollPane1);

        jTableContent.setAutoCreateRowSorter(true);
        initFileList();
        jTableContent.setFillsViewportHeight(true);
        jTableContent.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jTableContent.setShowHorizontalLines(false);
        jTableContent.setShowVerticalLines(false);
        jScrollPane3.setViewportView(jTableContent);

        jSplitPane1.setRightComponent(jScrollPane3);

        getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);
        jSplitPane1.setDividerLocation(200);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTreeExplorerTreeWillExpand(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException {//GEN-FIRST:event_jTreeExplorerTreeWillExpand
        fillDirectoryTree(evt.getPath());
                
    }//GEN-LAST:event_jTreeExplorerTreeWillExpand

    private void jTreeExplorerValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTreeExplorerValueChanged
        fillFileList(evt.getPath());
    }//GEN-LAST:event_jTreeExplorerValueChanged

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DirectoryExplorer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DirectoryExplorer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DirectoryExplorer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DirectoryExplorer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DirectoryExplorer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTableContent;
    private javax.swing.JTree jTreeExplorer;
    // End of variables declaration//GEN-END:variables
}
