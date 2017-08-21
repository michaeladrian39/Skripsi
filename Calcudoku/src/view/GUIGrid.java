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
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.border.Border;
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
    private final Map<JTextField, Point> textFieldCoordinates = new HashMap<>();
    private static final Font FONT = new Font("Courier New", 
            Font.CENTER_BASELINE, 24);
    private final int cellSize = 48;
    private final int cellBorderWidth = 1;
    private final int cageBorderWidth = 1;
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
                textFieldCoordinates.put(textField, new Point(x, y));
                textFields[x][y] = textField;
            }   
        }
        for (int y = 0; y < size; y++)
        {
            for (int x = 0; x < size; x++)
            {
                JTextField textField = textFields[y][x];
                textField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 
                        cellBorderWidth));
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
    
    public void moveCursor(JTextField textField, int keyCode)
    {
        Point coordinates = textFieldCoordinates.get(textField);
        switch (keyCode)
        {
            case KeyEvent.VK_LEFT:
                if (coordinates.x > 0)
                {
                    textFields[coordinates.y][coordinates.x - 1].requestFocus();
                    addSpace(textFields[coordinates.y][coordinates.x - 1]);
                }
                break;
            case KeyEvent.VK_KP_LEFT:
                if (coordinates.x > 0)
                {
                    textFields[coordinates.y][coordinates.x - 1].requestFocus();
                    addSpace(textFields[coordinates.y][coordinates.x - 1]);
                }
                break;
            case KeyEvent.VK_UP:
                if (coordinates.y > 0)
                {
                    textFields[coordinates.y - 1][coordinates.x].requestFocus();
                    addSpace(textFields[coordinates.y - 1][coordinates.x]);
                }
                break;
            case KeyEvent.VK_KP_UP:
                if (coordinates.y > 0)
                {
                    textFields[coordinates.y - 1][coordinates.x].requestFocus();
                    addSpace(textFields[coordinates.y - 1][coordinates.x]);
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (coordinates.x < size - 1)
                {
                    textFields[coordinates.y][coordinates.x + 1].requestFocus();
                    addSpace(textFields[coordinates.y][coordinates.x + 1]);
                }
                break;
            case KeyEvent.VK_KP_RIGHT:
                if (coordinates.x < size - 1)
                {
                    textFields[coordinates.y][coordinates.x + 1].requestFocus();
                    addSpace(textFields[coordinates.y][coordinates.x + 1]);
                }
                break;
            case KeyEvent.VK_DOWN:
                if (coordinates.y < size - 1)
                {
                    textFields[coordinates.y + 1][coordinates.x].requestFocus();
                    addSpace(textFields[coordinates.y + 1][coordinates.x]);
                }
                break;
            case KeyEvent.VK_KP_DOWN:
                if (coordinates.y < size - 1)
                {
                    textFields[coordinates.y + 1][coordinates.x].requestFocus();
                    addSpace(textFields[coordinates.y + 1][coordinates.x]);
                }
                break;
        }
    }
    
    private void addSpace(JTextField textField)
    {
        if (textField.getText().isEmpty())
        {
            textField.setText(" ");
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
        int keyCode = e.getKeyCode();
        JTextField textField = (JTextField) e.getSource();        
        switch (keyCode)
        {
            case KeyEvent.VK_LEFT :
                e.consume();
                gui.moveCursor(textField, KeyEvent.VK_LEFT);
                break;
            case KeyEvent.VK_UP :
                e.consume();
                gui.moveCursor(textField, KeyEvent.VK_UP);
                break;
            case KeyEvent.VK_RIGHT :
                e.consume();
                gui.moveCursor(textField, KeyEvent.VK_RIGHT);
                break;
            case KeyEvent.VK_DOWN :
                e.consume();
                gui.moveCursor(textField, KeyEvent.VK_DOWN);
                break;
            case KeyEvent.VK_KP_LEFT :
                e.consume();
                gui.moveCursor(textField, KeyEvent.VK_KP_LEFT);
                break;
            case KeyEvent.VK_KP_UP :
                e.consume();
                gui.moveCursor(textField, KeyEvent.VK_KP_UP);
                break;
            case KeyEvent.VK_KP_RIGHT :
                e.consume();
                gui.moveCursor(textField, KeyEvent.VK_KP_RIGHT);
                break;
            case KeyEvent.VK_KP_DOWN :
                e.consume();
                gui.moveCursor(textField, KeyEvent.VK_KP_DOWN);
                break;
            default :
                String gridSize = "" + gui.getGridSize();
                int gridSizeDigits = gridSize.length();
                if (textField.getText().length() >= gridSizeDigits)
                {
                    e.consume();
                }
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

//            this.objectivePositionRow = new int[numberOfCages];
//            this.objectivePositionColumn = new int[numberOfCages];
//            Cage[] cages = c.getCages();
//            for (int i = 0; i < numberOfCages; i++)
//            {
//                this.objectivePositionRow[i] = 
//                        cages[i].getCells().get(0).getRow();
//                this.objectivePositionColumn[i] = 
//                        cages[i].getCells().get(0).getColumn();
//            }