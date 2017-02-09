package model;

import javax.swing.JOptionPane;

/**
 *
 * @author michaeladrian39
 */
public class Grid
{
    
    private final int size;
    private final int numberOfCages;
    private final int[][] cageCells;
    private final String[] cageObjectives;
    private final Cell[][] grid;
    private final Cage[] cages;
    
    public Grid(int size, int numberOfCages, int[][] cageCells, 
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
        generateGrid(grid, cages);
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
    
    private void generateCages(Cage[] cages)
    {
        for (int i = 0; i < cages.length; i++)
        {
            cages[i] = new Cage(i, cageObjectives[i]);
        }
    }
    
    private void generateGrid(Cell[][] grid, Cage[] cages)
    {
        for (int i = 0; i < cageCells.length; i++)
        {
            for (int j = 0; j < cageCells[i].length; j++)
            {
                grid[i][j] = new Cell((i * size) + j, i, j, 
                        (cageCells[i][j] - 1));
                cages[cageCells[i][j] - 1].addCell(grid[i][j]);
            }
        }
    }
    
    private boolean isArrayContainZero(int[] array)
    {
        for (int i = 0; i < array.length; i++)
        {
            if (array[i] == 0)
            {
                return true;
            }
        }
        return false;
    }
    
    public int[] getRow(int rowNumber)
    {
        int[] row = new int[size];
        for (int i = 0; i < grid.length; i++)
        {
            row[i] = grid[rowNumber][i].getValue();
        }
        return row;
    }
    
    public int[] getColumn(int columnNumber)
    {
        int[] column = new int[size];
        for (int i = 0; i < size; i++)
        {
            column[i] = grid[i][columnNumber].getValue();
        }
        return column;
    }
    
    public int[] getCageValues(int cageNumber)
    {
        int[] cage = new int[cages[cageNumber].getSize()];
        for (int i = 0; i < cage.length; i++)
        {
            cage[i] = cages[cageNumber].getCells().get(i).getValue();
        }
        return cage;
    }
    
    private boolean isArrayValid(int[] array)
    {
        for (int i = 0; i < array.length; i++)
        {
            for (int j = 0; j < array.length; j++)
            {
                if (array[i] == array[j] && (i != j))
                {
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean isCageValuesValid(int cageNumber)
    {
        return cages[cageNumber].isCageValuesValid();
    }
    
    public void setCellValue(int row, int column, int value)
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
        int[] rowArray = getRow(row);
        if (!isArrayContainZero(rowArray))
        {
            if (!isArrayValid(rowArray))
            {
                JOptionPane.showMessageDialog(null, 
                    "Row " + row + " has duplicate numbers.",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        int[] columnArray = getColumn(column);
        if (!isArrayContainZero(columnArray))
        {
            if (!isArrayValid(columnArray))
            {
                JOptionPane.showMessageDialog(null, 
                    "Column " + column + " has duplicate numbers.",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        int[] cageArray = getCageValues(getGrid()[row][column].getCageID());
        if (!isArrayContainZero(cageArray))
        {
            if (!isCageValuesValid(getGrid()[row][column].getCageID()))
            {
                JOptionPane.showMessageDialog(null, 
                    "Values of cells in the cage do not reach the target number",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    public int getCellValue(int row, int column)
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
