/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author apple
 */
public class GUI extends javax.swing.JFrame {

    File puzzleFile;
    String puzzleFileName;
    int size;
    int[][] cageCells;
    int numberOfCages;
    String[] cageObjectives;
    Controller c;
    
    /**
     * Creates new form GUI
     */
    public GUI() {
        initComponents();
        addListeners();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser = new javax.swing.JFileChooser();
        jMenuBar = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemLoad = new javax.swing.JMenuItem();
        jMenuItemReset = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItemExit = new javax.swing.JMenuItem();
        jMenuSolve = new javax.swing.JMenu();
        jMenuItemBacktracking = new javax.swing.JMenuItem();
        jMenuItemHybrid = new javax.swing.JMenuItem();

        jFileChooser.setDialogTitle("Load Puzzle File");
        jFileChooser.setFileFilter(new PuzzleFileFilter());

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jMenuFile.setText("File");

        jMenuItemLoad.setText("Load Puzzle File");
        jMenuItemLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemLoadActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemLoad);

        jMenuItemReset.setText("Reset Puzzle");
        jMenuItemReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemResetActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemReset);
        jMenuFile.add(jSeparator1);

        jMenuItemExit.setText("Exit");
        jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExitActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemExit);

        jMenuBar.add(jMenuFile);

        jMenuSolve.setText("Solve");

        jMenuItemBacktracking.setText("Backtracking");
        jMenuItemBacktracking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemBacktrackingActionPerformed(evt);
            }
        });
        jMenuSolve.add(jMenuItemBacktracking);

        jMenuItemHybrid.setText("Hybrid Genetic");
        jMenuItemHybrid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemHybridActionPerformed(evt);
            }
        });
        jMenuSolve.add(jMenuItemHybrid);

        jMenuBar.add(jMenuSolve);

        setJMenuBar(jMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 278, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItemLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemLoadActionPerformed
        if (jFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            puzzleFile = jFileChooser.getSelectedFile();
            puzzleFileName = puzzleFile.getAbsolutePath();
            try
            {
                if (puzzleFile.getAbsolutePath().endsWith(".txt"))
                {
                    loadPuzzleFile(puzzleFile);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Invalid puzzle file.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            catch (FileNotFoundException fnfe)
            {
                JOptionPane.showMessageDialog(null, "Puzzle file not found.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jMenuItemLoadActionPerformed

    private void jMenuItemResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemResetActionPerformed
        if (puzzleFile == null)
        {
            JOptionPane.showMessageDialog(null,  "Puzzle file not loaded.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            throw new IllegalStateException("Puzzle file not loaded.");
        }
        else
        {
            c = new Controller(size, numberOfCages, cageCells, cageObjectives);
        }
    }//GEN-LAST:event_jMenuItemResetActionPerformed

    private void jMenuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemExitActionPerformed
        if (JOptionPane.showConfirmDialog(null,
                "Are you sure you want to exit this application?", "Exit", 
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
        {
            System.exit(0);
        }
    }//GEN-LAST:event_jMenuItemExitActionPerformed

    private void jMenuItemBacktrackingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemBacktrackingActionPerformed
        if (puzzleFile == null)
        {
            JOptionPane.showMessageDialog(null,  "Puzzle file not loaded.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            throw new IllegalStateException("Puzzle file not loaded.");
        }
        else
        {
            c.solveBacktracking();
        }
    }//GEN-LAST:event_jMenuItemBacktrackingActionPerformed

    private void jMenuItemHybridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemHybridActionPerformed
        if (puzzleFile == null)
        {
            JOptionPane.showMessageDialog(null,  "Puzzle file not loaded.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            throw new IllegalStateException("Puzzle file not loaded.");
        }
        else
        {
            c.solveHybridGenetic();
        }
    }//GEN-LAST:event_jMenuItemHybridActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }
    
    private void addListeners()
    {
        this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                if (JOptionPane.showConfirmDialog(null, 
                        "Are you sure you want to exit the application?", 
                        "Exit", JOptionPane.YES_NO_OPTION) 
                        == JOptionPane.YES_OPTION);
                {
                    System.exit(0);
                }
            }
        });
    }
    
    private void loadPuzzleFile(File puzzleFile) throws FileNotFoundException
    {
        try
        {
            Scanner sc = new Scanner(puzzleFile);
            size = sc.nextInt();
            cageCells = new int[size][size];
            numberOfCages = sc.nextInt();
            cageObjectives = new String[numberOfCages];
            for (int i = 0; i < size; i++)
            {
                for (int j = 0; j < size; j++)
                {
                    cageCells[i][j] = sc.nextInt();
                }
            }
            for (int i = 0; i < numberOfCages; i++)
            {
                cageObjectives[i] = sc.next();
            }
            if (sc.hasNext())
            {
                JOptionPane.showMessageDialog(null, "Invalid puzzle file.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                throw new IllegalStateException("Invalid puzzle file.");
            }
            sc.close();
            c = new Controller(size, numberOfCages, cageCells, cageObjectives);
        }
        catch (NoSuchElementException nsee)
        {
            JOptionPane.showMessageDialog(null, "Invalid puzzle file.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            throw new IllegalStateException("Invalid puzzle file.");
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser jFileChooser;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenuItem jMenuItemBacktracking;
    private javax.swing.JMenuItem jMenuItemExit;
    private javax.swing.JMenuItem jMenuItemHybrid;
    private javax.swing.JMenuItem jMenuItemLoad;
    private javax.swing.JMenuItem jMenuItemReset;
    private javax.swing.JMenu jMenuSolve;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
