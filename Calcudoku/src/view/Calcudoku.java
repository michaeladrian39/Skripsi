package view;

import controller.Controller;
import java.awt.Dimension;
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
public class Calcudoku extends JFrame
{
    
    private File puzzleFile;
    private String puzzleFileName;
    private int size;
    private int[][] cageCells;
    private int numberOfCages;
    private String[] cageObjectives;
    private Controller c;
    private final JMenuBar menuBar;
    private final JMenu menuFile;
    private final JMenu menuSolve;
    private final JMenuItem menuItemLoad;
    private final JMenuItem menuItemReset;
    private final JMenuItem menuItemClose;
    private final JMenuItem menuItemCheck;
    private final JMenuItem menuItemExit;
    private final JMenuItem menuItemBacktracking;
    private final JMenuItem menuItemHybridGenetic;
    private final JFileChooser fileChooser;
    private GUI gui;
    
    public Calcudoku()
    {        
        this.menuBar = new JMenuBar();
        this.menuFile = new JMenu();
        this.menuSolve = new JMenu();
        this.menuItemLoad = new JMenuItem();
        this.menuItemReset = new JMenuItem();
        this.menuItemClose = new JMenuItem();
        this.menuItemCheck = new JMenuItem();
        this.menuItemExit = new JMenuItem();
        this.menuItemBacktracking = new JMenuItem();
        this.menuItemHybridGenetic = new JMenuItem();
        this.fileChooser = new JFileChooser();
        initComponents();
    }
    
    public static void main(String args[])
    {
        EventQueue.invokeLater(() ->
        {
            new Calcudoku().setVisible(true);
        });
    }
    
    private void initComponents()
    { 
        this.setTitle("Calcudoku");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setMinimumSize(new Dimension(216, 216));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        initWindowListener();
        initActionListeners();
        initMenu();
        initMenuBar();
        this.validate();
        this.revalidate();
        this.pack();
    }

    private void initWindowListener()
    {
        this.addWindowListener(new WindowListener(this));
    }
    
    private void initActionListeners()
    {
        this.fileChooser.setFileFilter(new PuzzleFileFilter());      
        this.menuItemLoad.addActionListener(this::menuItemLoadActionPerformed);
        this.menuItemReset.addActionListener(
                this::menuItemResetActionPerformed);
        this.menuItemCheck.addActionListener(
                this::menuItemCheckActionPerformed);
        this.menuItemClose.addActionListener(
                this::menuItemCloseActionPerformed);
        this.menuItemExit.addActionListener(this::menuItemExitActionPerformed);
        this.menuItemBacktracking.addActionListener(
                this::menuItemBacktrackingActionPerformed);
        this.menuItemHybridGenetic.addActionListener(
                this::menuItemHybridGeneticActionPerformed);
    }
    
    private void initMenu()
    {
        File directory = new File(System.getProperty("user.dir"));
        this.menuFile.setText("File");
        this.menuSolve.setText("Solve");
        this.menuItemLoad.setText("Load Puzzle File");
        this.menuItemReset.setText("Reset Puzzle");
        this.menuItemClose.setText("Close Puzzle File");
        this.menuItemCheck.setText("Check Puzzle");
        this.menuItemExit.setText("Exit");
        this.menuItemBacktracking.setText("Backtracking");
        this.menuItemHybridGenetic.setText("Hybrid Genetic");
        this.fileChooser.setDialogTitle("Load Puzzle File");
        fileChooser.setCurrentDirectory(directory);
    }
    
    private void initMenuBar()
    {
        this.menuFile.add(menuItemLoad);
        this.menuFile.add(menuItemReset);
        this.menuFile.add(menuItemClose);
        this.menuFile.addSeparator();
        this.menuFile.add(menuItemCheck);
        this.menuFile.addSeparator();
        this.menuFile.add(menuItemExit);
        this.menuBar.add(menuFile);
        this.menuSolve.add(menuItemBacktracking);
        this.menuSolve.add(menuItemHybridGenetic);
        this.menuBar.add(menuSolve);
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
                    this.setTitle("Calcudoku (" + puzzleFile.getName() + ")");
                    try
                    {
                        loadPuzzleFile(puzzleFile);
                    }
                    catch (IllegalStateException ise)
                    {
                        resetFrame();
                        JOptionPane.showMessageDialog(null, 
                                "Error in loading puzzle file.", "Error", 
                                JOptionPane.ERROR_MESSAGE);
                        throw new IllegalStateException("Error in loading " 
                                + "puzzle file.");
                    }
                }
                else
                {
                    resetFrame();
                    JOptionPane.showMessageDialog(null, "Invalid puzzle file.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                    throw new IllegalStateException("Invalid puzzle file.");
                }
            }
            catch (FileNotFoundException fnfe)
            {                
                resetFrame();
                JOptionPane.showMessageDialog(null, "Puzzle file not found.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                throw new IllegalStateException("Puzzle file not found.");
            }
        }
    }

    private void menuItemResetActionPerformed(ActionEvent evt)
    {
        if (puzzleFile == null || puzzleFileName == null || c == null 
                || gui == null)
        {
            resetFrame();
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
            this.gui = new GUI(c);
            this.getContentPane().add(gui);
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
    
    private void menuItemCloseActionPerformed(ActionEvent evt)
    {
        resetFrame();
    }

    private void menuItemExitActionPerformed(ActionEvent evt)
    {
        if (JOptionPane.showConfirmDialog(null,
                "Are you sure you want to exit this application?", "Exit",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
        {
            destroyFrame();
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
                    resetFrame();
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
            this.gui = new GUI(c);
            this.getContentPane().add(gui);
            this.validate();
            this.revalidate();
            this.pack();
            this.setLocationRelativeTo(null);
        }
        catch (NoSuchElementException nsee)
        {
            resetFrame();
            JOptionPane.showMessageDialog(null, "Invalid puzzle file.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            throw new IllegalStateException("Invalid puzzle file.");
        }
    }
    
    private void resetFrame()
    {
        this.getContentPane().removeAll();
        this.puzzleFile = null;
        this.puzzleFileName = null;
        this.size = 0;
        this.cageCells = null;
        this.numberOfCages = 0;
        this.cageObjectives = null;
        this.c = null;
        this.setTitle("Calcudoku");
        this.validate();
        this.revalidate();
        this.pack();
        this.setLocationRelativeTo(null);
    }
    
    public void destroyFrame()
    {
        resetFrame();
        this.dispose();
    }
    
}

class WindowListener extends WindowAdapter
{

    private final Calcudoku frame;
    
    public WindowListener(Calcudoku frame)
    {
        this.frame = frame;
    }
    
    @Override
    public void windowClosing(WindowEvent e)
    {
        if (JOptionPane.showConfirmDialog(null,
                "Are you sure you want to exit the application?", "Exit", 
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
        {
            frame.destroyFrame();
        }
    }

    @Override
    public void windowClosed(WindowEvent e)
    {
        System.exit(0);
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