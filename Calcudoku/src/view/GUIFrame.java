package view;

import controller.Controller;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author michaeladrian39
 */
public class GUIFrame extends JFrame
{
    
    private File puzzleFile;
    private String puzzleFileName;
    private int size;
    private int[][] cageCells;
    private int numberOfCages;
    private String[] cageObjectives;
    private Controller c;
    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu menuFile = new JMenu("File");
    private final JMenu menuSolve = new JMenu("Solve");
    private final JMenuItem menuItemLoad = new JMenuItem("Load Puzzle File");
    private final JMenuItem menuItemReset = new JMenuItem("Reset Puzzle");
    private final JMenuItem menuItemCheck = new JMenuItem("Check Puzzle");
    private final JMenuItem menuItemExit = new JMenuItem("Exit");
    private final JMenuItem menuItemBacktracking 
            = new JMenuItem("Backtracking");
    private final JMenuItem menuItemHybridGenetic 
            = new JMenuItem("Hybrid Genetic");
    private final JFileChooser fileChooser 
            = new JFileChooser("Load Puzzle File");
    private GUIGrid gui;
    
    public GUIFrame()
    {        
        initComponents();
    }
    
    public static void main(String args[])
    {
        EventQueue.invokeLater(() ->
        {
            new GUIFrame().setVisible(true);
        });
    }
    
    private void initComponents()
    {
        this.setTitle("Calcudoku");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
        initWindowListener();
        initActionListeners();
        initMenuBar();
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }

    private void initWindowListener()
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
                    getContentPane().removeAll();
                    dispose();
                }
            }
            
            @Override
            public void windowClosed(WindowEvent e)
            {
                System.exit(0);
            }

        });
    }
    
    private void initActionListeners()
    {
        fileChooser.setFileFilter(new PuzzleFileFilter());      
        menuItemLoad.addActionListener(this::menuItemLoadActionPerformed);
        menuItemReset.addActionListener(this::menuItemResetActionPerformed);
        menuItemCheck.addActionListener(this::menuItemCheckActionPerformed);
        menuItemExit.addActionListener(this::menuItemExitActionPerformed);
        menuItemBacktracking.addActionListener(
                this::menuItemBacktrackingActionPerformed);
        menuItemHybridGenetic.addActionListener(
                this::menuItemHybridGeneticActionPerformed);
    }
    
    private void initMenuBar()
    {
        menuFile.add(menuItemLoad);
        menuFile.add(menuItemReset);
        menuFile.addSeparator();
        menuFile.add(menuItemCheck);
        menuFile.addSeparator();
        menuFile.add(menuItemExit);
        menuBar.add(menuFile);
        menuSolve.add(menuItemBacktracking);
        menuSolve.add(menuItemHybridGenetic);
        menuBar.add(menuSolve);
        this.setJMenuBar(menuBar);
    }
    
    private void menuItemLoadActionPerformed(ActionEvent evt)
    {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            this.puzzleFile = fileChooser.getSelectedFile();
            try
            {
                if (puzzleFile.getAbsolutePath().endsWith(".txt"))
                {
                    this.puzzleFileName = puzzleFile.getAbsolutePath();
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
    }

    private void menuItemResetActionPerformed(ActionEvent evt)
    {
        if (puzzleFile == null || puzzleFileName == null || c == null 
                || gui == null)
        {
            JOptionPane.showMessageDialog(null,  "Puzzle file not loaded.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            throw new IllegalStateException("Puzzle file not loaded.");
        }
        else
        {
            this.c = null;
            this.c = new Controller(size, numberOfCages, cageCells,
                    cageObjectives);
            this.getContentPane().removeAll();
            this.getContentPane().add(gui = new GUIGrid(c));
            this.validate();
            this.revalidate();
            this.pack();
            this.setLocationRelativeTo(null);
        }
    }
    
    private void menuItemCheckActionPerformed(ActionEvent evt)
    {
        if (puzzleFile == null || puzzleFileName == null || c == null 
                || gui == null)
        {
            JOptionPane.showMessageDialog(null,  "Puzzle file not loaded.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            throw new IllegalStateException("Puzzle file not loaded.");
        }
        else
        {
            c.checkGrid();
        }
    }

    private void menuItemExitActionPerformed(ActionEvent evt)
    {
        if (JOptionPane.showConfirmDialog(null,
                "Are you sure you want to exit this application?", "Exit",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
        {
            this.getContentPane().removeAll();
            dispose();
        }
    }

    private void menuItemBacktrackingActionPerformed(ActionEvent evt)
    {
        if (puzzleFile == null || puzzleFileName == null || c == null 
                || gui == null)
        {
            JOptionPane.showMessageDialog(null,  "Puzzle file not loaded.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            throw new IllegalStateException("Puzzle file not loaded.");
        }
        else
        {
            gui.solveBacktracking();
        }
    }

    private void menuItemHybridGeneticActionPerformed(ActionEvent evt)
    {
        if (puzzleFile == null || puzzleFileName == null || c == null 
                || gui == null)
        {
            JOptionPane.showMessageDialog(null,  "Puzzle file not loaded.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            throw new IllegalStateException("Puzzle file not loaded.");
        }
        else
        {
            gui.solveHybridGenetic();
        }
    }

    private void loadPuzzleFile(File puzzleFile) throws FileNotFoundException
    {
        try
        {
            try (Scanner sc = new Scanner(puzzleFile))
            {
                this.size = sc.nextInt();
                this.cageCells = new int[size][size];
                this.numberOfCages = sc.nextInt();
                this.cageObjectives = new String[numberOfCages];
                for (int i = 0; i < size; i++)
                {
                    for (int j = 0; j < size; j++)
                    {
                        this.cageCells[i][j] = sc.nextInt();
                    }
                }
                for (int i = 0; i < numberOfCages; i++)
                {
                    this.cageObjectives[i] = sc.next();
                }
                if (sc.hasNext())
                {
                    JOptionPane.showMessageDialog(null, "Invalid puzzle file.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    throw new IllegalStateException("Invalid puzzle file.");
                }
                sc.close();
            }
            if (this.c != null)
            {
                this.c = null;
            }
            this.c = new Controller(size, numberOfCages, cageCells,
                    cageObjectives);
            this.getContentPane().removeAll();
            this.getContentPane().add(gui = new GUIGrid(c));
            this.validate();
            this.revalidate();
            this.pack();
            this.setLocationRelativeTo(null);
        }
        catch (NoSuchElementException nsee)
        {
            JOptionPane.showMessageDialog(null, "Invalid puzzle file.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            throw new IllegalStateException("Invalid puzzle file.");
        }
    }
    
}

class PuzzleFileFilter extends FileFilter
{

    @Override
    public boolean accept(File puzzleFile)
    {
        return puzzleFile.isDirectory()
                || puzzleFile.getAbsolutePath().endsWith(".txt");
    }

    @Override
    public String getDescription()
    {
        return "Text documents (*.txt)";
    }

}