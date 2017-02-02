package model;

import javax.swing.JOptionPane;

/**
 *
 * @author michaeladrian39
 */
public class Board
{
    
    private final int size;
    private final int numberOfCages;
    private final int[][] cageCells;
    private final String[] cageObjectives;
    private final Cell[][] grid;
    private final Cage[] cages;
    
    public Board(int size, int numberOfCages, int[][] cageCells, 
            String[] cageObjectives)
    {
        this.size = size;
        this.numberOfCages = numberOfCages;
        if (isCageCellsSizeValid(cageCells))
        {
            this.cageCells = cageCells;
        }
        else
        { 
            JOptionPane.showMessageDialog(null, "Invalid array size.", "Error", 
                    JOptionPane.ERROR_MESSAGE);
            throw new IllegalStateException("Invalid array size.");
        }
        if (isCageObjectivesSizeValid(cageObjectives))
        {
            this.cageObjectives = cageObjectives;
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Invalid array size.", "Error", 
                    JOptionPane.ERROR_MESSAGE);
            throw new IllegalStateException("Invalid array size.");
        }
        grid = new Cell[size][size];
        generateGrid(grid);
        cages = new Cage[numberOfCages];
        generateCages(cages);
        for (int i = 0; i < cages.length; i++)
        {
            int[][] array = new int[size][size];
            for (int j = 0; j < cageCells.length; j++)
            {
                for (int k = 0; k < cageCells[j].length; k++)
                {
                    if (cageCells[j][k] == i + 1)
                    {
                        array[j][k] = 1;
                    }
                    else
                    {
                        array[j][k] = 0;
                    }
                }
            }
            if (!isCageAssignmentValid(array))
            { 
                JOptionPane.showMessageDialog(null, "Invalid cage assignment.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                throw new IllegalStateException("Invalid cage assignment.");
            }
        }
        generateCageAssignment(cages);
        if (!isCagesValid(cages))
        {
            JOptionPane.showMessageDialog(null, "Invalid cages.", "Error", 
                    JOptionPane.ERROR_MESSAGE);
            throw new IllegalStateException("Invalid cages.");
        }
    }
    
    public int countAreas(int[][] array)
    {
        boolean[][] checked = new boolean[size][size];
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                checked[i][j] = false;
            }
        }
        return countAreas(array, checked);
    }

    public static int countAreas(int[][] array, boolean[][] checked)
    {
        int areas = 0;
        for (int i = 0; i < array.length; i++)
        {
            for (int j = 0; j < array.length; j++)
            {
                if (checked[i][j])
                {
                    continue;
                }
                if (array[i][j] == 0)
                {
                    checked[i][j] = true;
                    continue;
                }
                areas++;
                floodFill(i, j, array, checked); 
            }
        }
        return areas;
    }

    public static void floodFill(int i, int j, int[][] array, 
            boolean[][] checked)
    {
        if (array[i][j] == 0 || checked[i][j])
        {
            return;
        }
        checked[i][j] = true;
        if (j < array.length - 1)
        {
            floodFill(i, j + 1, array, checked);
        }
        if (i < array.length - 1)
        {
            floodFill(i + 1, j, array, checked);
        }
        if (j > 0)
        {
            floodFill(i, j - 1, array, checked);
        }
        if (i > 0)
        {
            floodFill(i - 1, j, array, checked);
        }
    }
    
    private boolean isCageCellsSizeValid(int[][] cageCells)
    {
        if (cageCells.length == size)
        {
            for (int i = 0; i < size; i++)
            {
                if (cageCells[i].length != size)
                {
                    return false;
                }
            }
        }
        else
        {
            return false;
        }
        return true;
    }
    
    private boolean isCageObjectivesSizeValid(String[] cageObjectives)
    {
        return cageCells.length == size;
    }
    
    private boolean isCageAssignmentValid(int[][] array)
    {
        return countAreas(array) == 1;
    }
    
    private boolean isCagesValid(Cage[] cages)
    {
        for (Cage c : cages)
        {
            if (c.getOperator() == '=' && c.getSize() != 1)
            {
                return false;
            }
            if ((c.getOperator() == '-' || c.getOperator() == '/') && c.getSize() != 2)
            {
                return false;
            }
            if ((c.getOperator() == '+' || c.getOperator() == '*') && c.getSize() < 2)
            {
                return false;
            }
        }
        return true;
    }
    
    private void generateGrid(Cell[][] grid)
    {
        for (int i = 0; i < grid.length; i++)
        {
            for (int j = 0; j < grid[i].length; j++)
            {
                grid[i][j] = new Cell((i * size) + j, i, j);
            }
        }
    }
    
    private void generateCages(Cage[] cages)
    {
        for (int i = 0; i < cages.length; i++)
        {
            cages[i] = new Cage(i, cageObjectives[i]);
        }
    }
    
    private void generateCageAssignment(Cage[] cages)
    {
        for (int j = 0; j < cageCells.length; j++)
        {
            for (int k = 0; k < cageCells[j].length; k++)
            {
                cages[cageCells[j][k] - 1].addCell(grid[j][k]);
            }
        }
    }
    
    public void setCellValue(int row, int column, Integer value)
    {
        if (value > 0 && value <= size)
        {
            getGrid()[row][column].setValue(value);
        }
        else
        {
            JOptionPane.showMessageDialog(null, 
                    "Cell value must be between 1 and " + size + ".",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public Integer getCellValue(int row, int column)
    {
        return getGrid()[row][column].getValue();
    }
    
    public int getSize()
    {
        return size;
    }
    
    public int getNumberOfCages()
    {
        return numberOfCages;
    }
    
    public int[][] getCageCells()
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
    
}
