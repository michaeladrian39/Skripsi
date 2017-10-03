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
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import model.Cage;
import model.Cell;
import model.Grid;
import model.SolverBacktracking;
import model.SolverHybridGenetic;

/**
 *
 * @author michaeladrian39
 */
public class GUI extends JPanel
{
    
    private final Controller c;
    private final Grid game;
    private final Integer size;
    private final Integer numberOfCages;
    private final Integer[][] cageCells;
    private final String[] cageObjectives;
    private final Cell[][] grid;
    private final Cage[] cages;
    private final JTextField[][] textFields;
    private final Map<JTextField, Point> textFieldCoordinates;
    private final CellTextFieldListener[][] cellTextFieldListeners;
    private final Font font;
    private final int cellSize;
    private final int cellBorderWidth;
    private final int cageBorderWidth;
    private Integer generations;
    private Integer populationSize;
    private Double elitismRate;
    private Double crossoverRate;
    private Double mutationRate;
    
    public GUI(Controller c)
    {
        this.c = c;
        this.game = c.getGame();
        this.size = c.getSize();
        this.cageCells = c.getCageCells();
        this.numberOfCages = c.getNumberOfCages();
        this.cageObjectives = c.getCageObjectives();
        this.grid = c.getGrid();
        this.cages = c.getCages();
        this.textFields = new JTextField[size][size];
        this.textFieldCoordinates = new HashMap<>();
        this.cellTextFieldListeners = new CellTextFieldListener[size][size];
        this.font = new Font("Courier New", Font.CENTER_BASELINE, 36);
        this.cellSize = 72;
        this.cellBorderWidth = 1;
        this.cageBorderWidth = 3;
        initComponents();
    }
    
    private void initComponents()
    {
        this.setPreferredSize(new Dimension(cellSize * size, cellSize * size));
        this.setLayout(new GridLayout(size, size));
        initTextFields();
    }
    
    public Controller getController()
    {
        return c;
    }
    
    public Integer getGridSize()
    {
        return size;
    }
    
    public Integer getNumberOfCages()
    {
        return numberOfCages;
    }
    
    public Integer[][] getCageCells()
    {
        return cageCells;
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
    
    public void setGeneticAlgorithmParameters(Integer generations, 
            Integer populationSize, Double elitismRate, Double crossoverRate, 
            Double mutationRate)
    {
        this.generations = generations;
        this.populationSize = populationSize;
        this.elitismRate = elitismRate;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
    }
    
    private void initTextFields()
    {
        for (int y = 0; y < size; y++)
        {
            for (int x = 0; x < size; x++)
            {
                JTextField textField = new JTextField();
                textField.addKeyListener(new CellKeyListener(this));
                cellTextFieldListeners[y][x] = new CellTextFieldListener(c, 
                        textField, y, x);
                textField.getDocument().addDocumentListener(
                        cellTextFieldListeners[y][x]);
                textFieldCoordinates.put(textField, new Point(x, y));
                textFields[y][x] = textField;
            }   
        }
        for (int y = 0; y < size; y++)
        {
            for (int x = 0; x < size; x++)
            {
                JTextField textField = textFields[y][x];
                int cageID;
                int topBorderWidth;
                int leftBorderWidth;
                int bottomBorderWidth;
                int rightBorderWidth;
                if (y == 0)
                {
                    topBorderWidth = cageBorderWidth;
                    if (Objects.equals(cageCells[y][x], cageCells[y + 1][x]))
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
                    if (Objects.equals(cageCells[y][x], cageCells[y - 1][x]))
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
                    if (Objects.equals(cageCells[y][x], cageCells[y + 1][x]))
                    {
                        bottomBorderWidth = cellBorderWidth;
                    }
                    else
                    {
                        bottomBorderWidth = cageBorderWidth;
                    }
                    if (Objects.equals(cageCells[y][x], cageCells[y - 1][x]))
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
                    if (Objects.equals(cageCells[y][x], cageCells[y][x + 1]))
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
                    if (Objects.equals(cageCells[y][x], cageCells[y][x - 1]))
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
                    if (Objects.equals(cageCells[y][x], cageCells[y][x + 1]))
                    {
                        rightBorderWidth = cellBorderWidth;
                    }
                    else
                    {
                        rightBorderWidth = cageBorderWidth;
                    }
                    if (Objects.equals(cageCells[y][x], cageCells[y][x - 1]))
                    {
                        leftBorderWidth = cellBorderWidth;
                    }
                    else
                    {
                        leftBorderWidth = cageBorderWidth;
                    }
                }
                cageID = grid[y][x].getCageID();
                textField.setToolTipText(cages[cageID].getObjective());
                textField.setBorder(BorderFactory.createMatteBorder(
                        topBorderWidth, leftBorderWidth, bottomBorderWidth, 
                        rightBorderWidth, Color.BLACK));
                textField.setFont(font);
                textField.setHorizontalAlignment(JTextField.CENTER);
                textField.setPreferredSize(new Dimension(cellSize, cellSize));                
                JPopupMenu popupMenu = new JPopupMenu();                
                for (int i = 1; i <= size; i++)
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
    
    public void solveBacktracking()
    {
        removeCellTextFieldListeners();
        clearGrid();
        Cell[][] solution;
        float startTime = System.nanoTime();
        float endTime;
        float duration;
        SolverBacktracking sb = new SolverBacktracking(game);
        if (sb.solve() == true)
        {
            solution = sb.getSolution().getGridContents();
            printGridToScreen(solution);
            endTime = System.nanoTime();
            duration = (endTime - startTime) / 1000000000;
            System.out.println("The backtracking algorithm has successfully "
                    + "solved the puzzle." + "\nTime elapsed: " + duration 
                    + " seconds");
            JOptionPane.showMessageDialog(null, 
                    "The backtracking algorithm has successfully solved the " 
                            + "puzzle." + "\nTime elapsed: " + duration 
                            + " seconds", "Information", 
                            JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            endTime = System.nanoTime();
            duration = (endTime - startTime) / 1000000000;
            System.out.println("The backtracking algorithm has failed to solve "
                    + "the puzzle." + "\nTime elapsed: " + duration 
                    + " seconds");
            JOptionPane.showMessageDialog(null, 
                    "The backtracking algorithm has failed to solve the " 
                            + "puzzle." + "\nTime elapsed: " + duration 
                            + " seconds", "Information", 
                            JOptionPane.INFORMATION_MESSAGE);
        }
        addCellTextFieldListeners();
    }
    
    public void solveHybridGenetic()
    {
        if (generations == null || populationSize == null 
                || elitismRate == null || crossoverRate == null 
                || mutationRate == null)
        {
            JOptionPane.showMessageDialog(null,
                    "Genetic algorithm parameters have not been set.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            throw new IllegalStateException(
                    "Genetic algorithm parameters have not been set.");
        }
        else
        {
            removeCellTextFieldListeners();
            clearGrid();
            Cell[][] solution;
            float startTime = System.nanoTime();
            float endTime;
            float duration;
            SolverHybridGenetic shg = new SolverHybridGenetic(game, generations, 
                    populationSize, elitismRate, crossoverRate, mutationRate);
            if (shg.solve() == true)
            {
                solution = shg.getSolution().getGridContents();
                printGridToScreen(solution);
                endTime = System.nanoTime();
                duration = (endTime - startTime) / 1000000000;
                System.out.println("The hybrid genetic algorithm has successfully "
                        + "solved the puzzle." + "\nTime elapsed: " + duration 
                        + " seconds");
                JOptionPane.showMessageDialog(null, 
                        "The hybrid genetic algorithm has successfully solved the " 
                                + "puzzle." + "\nTime elapsed: " + duration 
                                + " seconds", "Information", 
                                JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                endTime = System.nanoTime();
                duration = (endTime - startTime) / 1000000000;
                System.out.println("The hybrid genetic algorithm has failed to "
                        + "solve the puzzle." + "\nTime elapsed: " + duration 
                        + " seconds");
                JOptionPane.showMessageDialog(null, 
                        "The hybrid genetic algorithm has failed to solve the " + 
                                "puzzle." + "\nTime elapsed: " + duration 
                                + " seconds", "Information", 
                                JOptionPane.INFORMATION_MESSAGE);
            }
            addCellTextFieldListeners();
        }
    }
    
    private void printGridToScreen(Cell[][] grid)
    {
        for (int x = 0; x < size; x++)
        {
            for (int y = 0; y < size; y++)
            {
                String value = Integer.toString(grid[x][y].getValue());
                textFields[x][y].setText(value);
            }
        }
    }
    
    private void clearGrid()
    {
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                c.unsetCellValue(i, j);
            }
        }
    }
    
    private void addCellTextFieldListeners()
    {
        for (int x = 0; x < size; x++)
        {
            for (int y = 0; y < size; y++)
            {
                textFields[x][y].getDocument().addDocumentListener(
                        cellTextFieldListeners[x][y]);
            }
        }
    }
    
    private void removeCellTextFieldListeners()
    {
        for (int x = 0; x < size; x++)
        {
            for (int y = 0; y < size; y++)
            {
                textFields[x][y].getDocument().removeDocumentListener(
                        cellTextFieldListeners[x][y]);
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
                }
                break;
            case KeyEvent.VK_KP_LEFT:
                if (coordinates.x > 0)
                {
                    textFields[coordinates.y][coordinates.x - 1].requestFocus();
                }
                break;
            case KeyEvent.VK_UP:
                if (coordinates.y > 0)
                {
                    textFields[coordinates.y - 1][coordinates.x].requestFocus();
                }
                break;
            case KeyEvent.VK_KP_UP:
                if (coordinates.y > 0)
                {
                    textFields[coordinates.y - 1][coordinates.x].requestFocus();
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (coordinates.x < size - 1)
                {
                    textFields[coordinates.y][coordinates.x + 1].requestFocus();
                }
                break;
            case KeyEvent.VK_KP_RIGHT:
                if (coordinates.x < size - 1)
                {
                    textFields[coordinates.y][coordinates.x + 1].requestFocus();
                }
                break;
            case KeyEvent.VK_DOWN:
                if (coordinates.y < size - 1)
                {
                    textFields[coordinates.y + 1][coordinates.x].requestFocus();
                }
                break;
            case KeyEvent.VK_KP_DOWN:
                if (coordinates.y < size - 1)
                {
                    textFields[coordinates.y + 1][coordinates.x].requestFocus();
                }
                break;
        }
    }
    
}

class CellKeyListener implements KeyListener
{

    private final GUI gui;

    CellKeyListener(GUI gui)
    {
        this.gui = gui;
    }

    @Override
    public void keyPressed(KeyEvent e)
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
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
        JTextField textField = (JTextField) e.getSource();
        char c = e.getKeyChar();
        if (!Character.isDigit(c))
        {
            e.consume();
        }
        String gridSize = "" + gui.getGridSize();
        int gridSizeDigits = gridSize.length();
        if (textField.getText().length() >= gridSizeDigits)
        {
            e.consume();
        }
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

class CellTextFieldListener implements DocumentListener
{

    private final Controller c;
    private final JTextField textField;
    private final int x;
    private final int y;
    
    public CellTextFieldListener(Controller c, JTextField textField, int x, 
            int y)
    {
        this.c = c;
        this.textField = textField;
        this.x = x;
        this.y = y;
    }
    
    @Override
    public void insertUpdate(DocumentEvent e)
    {
        Integer value = Integer.parseInt(textField.getText());
        c.setCellValue(x, y, value);
    }

    @Override
    public void removeUpdate(DocumentEvent e)
    {
        c.unsetCellValue(x, y);
    }

    @Override
    public void changedUpdate(DocumentEvent e)
    {
        Integer value = Integer.parseInt(textField.getText());
        c.unsetCellValue(x, y);
        c.setCellValue(x, y, value);
    }
    
}