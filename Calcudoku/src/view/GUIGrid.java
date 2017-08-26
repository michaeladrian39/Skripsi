/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import model.Cage;
import model.Cell;

/**
 *
 * @author michaeladrian39
 */
public class GUIGrid extends JPanel
{
    
    private final Controller c;
    private final int size;
    private final int[][] cageCells;
    private final int numberOfCages;
    private final String[] cageObjectives;
    private final Cell[][] grid;
    private final Cage[] cages;
    private final JTextField[][] textFields;
    private static final Font FONT = new Font("Courier New", 
            Font.CENTER_BASELINE, 24);
    private final int cellSize = 48;
    private final int cellBorderWidth = 1;
    private final int cageBorderWidth = 2;
    private Dimension textFieldDimension;
    
    public GUIGrid(Controller c)
    {
        this.c = c;
        this.size = c.getSize();
        this.cageCells = c.getCageCells();
        this.numberOfCages = c.getNumberOfCages();
        this.cageObjectives = c.getCageObjectives();
        this.grid = c.getGrid();
        this.cages = c.getCages();
        this.textFields = new JTextField[size][size];
        Dimension textFieldDimension = new Dimension(cellSize, cellSize);
        this.setPreferredSize(new Dimension(cellSize * size, cellSize * size));
        this.setLayout(new GridLayout(size, size));
        generateTextFields();
    }
    
    public Controller getController()
    {
        return c;
    }
    
    public int getGridSize()
    {
        return size;
    }
    
    public int[][] getCageCells()
    {
        return cageCells;
    }
    
    public int getNumberOfCages()
    {
        return numberOfCages;
    }
    
    public String[] getCageObjectives()
    {
        return cageObjectives;
    }
    
    public Cell[][] getGrid()
    {
        return grid;
    }
    
    public Cage[] getCages()
    {
        return cages;
    }
    
    private void generateTextFields()
    {
        for (int y = 0; y < size; y++)
        {
            for (int x = 0; x < size; x++)
            {
                JTextField textField = new JTextField();
                textField.addKeyListener(new SudokuCellKeyListener(this));
                textFields[x][y] = textField;
            }   
        }
        for (int y = 0; y < size; y++)
        {
            for (int x = 0; x < size; x++)
            {
                JTextField textField = textFields[y][x];
                int topBorderWidth;
                int leftBorderWidth;
                int bottomBorderWidth;
                int rightBorderWidth;
                if (y == 0)
                {
                    topBorderWidth = cageBorderWidth;
                    if (cageCells[y][x] == cageCells[y + 1][x])
                    {
                        bottomBorderWidth = cellBorderWidth;
                    }
                    else
                    {
                        bottomBorderWidth = cageBorderWidth;
                    }
                }
                else if (y == size - 1)
                {
                    bottomBorderWidth = cageBorderWidth;
                    if (cageCells[y][x] == cageCells[y - 1][x])
                    {
                        topBorderWidth = cellBorderWidth;
                    }
                    else
                    {
                        topBorderWidth = cageBorderWidth;
                    }
                }
                else
                {
                    if (cageCells[y][x] == cageCells[y + 1][x])
                    {
                        bottomBorderWidth = cellBorderWidth;
                    }
                    else
                    {
                        bottomBorderWidth = cageBorderWidth;
                    }
                    if (cageCells[y][x] == cageCells[y - 1][x])
                    {
                        topBorderWidth = cellBorderWidth;
                    }
                    else
                    {
                        topBorderWidth = cageBorderWidth;
                    }
                }
                if (x == 0)
                {
                    leftBorderWidth = cageBorderWidth;
                    if (cageCells[y][x] == cageCells[y][x + 1])
                    {
                        rightBorderWidth = cellBorderWidth;
                    }
                    else
                    {
                        rightBorderWidth = cageBorderWidth;
                    }
                }
                else if (x == size - 1)
                {
                    rightBorderWidth = cageBorderWidth;
                    if (cageCells[y][x] == cageCells[y][x - 1])
                    {
                        leftBorderWidth = cellBorderWidth;
                    }
                    else
                    {
                        leftBorderWidth = cageBorderWidth;
                    }
                }
                else
                {
                    if (cageCells[y][x] == cageCells[y][x + 1])
                    {
                        rightBorderWidth = cellBorderWidth;
                    }
                    else
                    {
                        rightBorderWidth = cageBorderWidth;
                    }
                    if (cageCells[y][x] == cageCells[y][x - 1])
                    {
                        leftBorderWidth = cellBorderWidth;
                    }
                    else
                    {
                        leftBorderWidth = cageBorderWidth;
                    }
                }
                textField.setToolTipText(
                        cages[grid[y][x].getCageID()].getObjective());
                textField.setBorder(BorderFactory.createMatteBorder(
                        topBorderWidth, leftBorderWidth, bottomBorderWidth, 
                        rightBorderWidth, Color.BLACK));
                textField.setFont(FONT);
                textField.setHorizontalAlignment(JTextField.CENTER);
                textField.setPreferredSize(textFieldDimension);                
                JPopupMenu popupMenu = new JPopupMenu();                
                for (int i = 0; i <= size; i++)
                {
                    JMenuItem menuItem = new JMenuItem("" + i);
                    popupMenu.add(menuItem);
                    menuItem.addActionListener(new PopupMenuListener(textField, 
                            i));
                }                
                textField.add(popupMenu);
                textField.setComponentPopupMenu(popupMenu);
            }
        }
        for (int y = 0; y < size; y++)
        {
            for (int x = 0; x < size; x++)
            {
                this.add(textFields[y][x]);
            }
        }
    }
}

class SudokuCellKeyListener implements KeyListener
{

    private final GUIGrid gui;

    SudokuCellKeyListener(GUIGrid gui)
    {
        this.gui = gui;
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
        JTextField textField = (JTextField) e.getSource();        
        String gridSize = "" + gui.getGridSize();
        int gridSizeDigits = gridSize.length();
        if (textField.getText().length() >= gridSizeDigits)
        {
            e.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        
    }
    
}

class PopupMenuListener implements ActionListener
{

    private final JTextField textField;
    private final int number;

    PopupMenuListener(JTextField textField, int number)
    {
        this.textField  = textField;
        this.number = number; 
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        textField.setText(number + "");
    }
    
}