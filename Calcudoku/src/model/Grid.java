package model;

import java.util.Objects;
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
    private Boolean[][] gridValidity;
    
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
        gridValidity = new Boolean[size][size];
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
    
    private int countAreas(int[][] array)
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

    private int countAreas(int[][] array, boolean[][] checked)
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

    private void floodFill(int i, int j, int[][] array, 
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
    
    public Integer[] getRow(int rowNumber)
    {
        Integer[] row = new Integer[size];
        for (int i = 0; i < grid.length; i++)
        {
            row[i] = grid[rowNumber][i].getValue();
        }
        return row;
    }
    
    public Integer[] getColumn(int columnNumber)
    {
        Integer[] column = new Integer[size];
        for (int i = 0; i < size; i++)
        {
            column[i] = grid[i][columnNumber].getValue();
        }
        return column;
    }
    
    public Integer[] getCageValues(int cageNumber)
    {
        Integer[] cage = new Integer[cages[cageNumber].getSize()];
        for (int i = 0; i < cage.length; i++)
        {
            cage[i] = cages[cageNumber].getCells().get(i).getValue();
        }
        return cage;
    }
    
    public Boolean getCellValidity(int row, int column)
    {
        return gridValidity[row][column];
    }
    
    private boolean isArrayContainNull(Integer[] array)
    {
        for (Integer item : array)
        {
            if (item == null)
            {
                return true;
            }
        }
        return false;
    }
    
    private boolean isArrayValid(Integer[] array)
    {
        for (int i = 0; i < array.length; i++)
        {
            for (int j = 0; j < array.length; j++)
            {
                if (Objects.equals(array[i], array[j]) && (i != j))
                {
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean isCageValuesValid(int cageNumber)
    {
        return cages[cageNumber].isCageValuesValid();
    }
    
    private boolean isRowValid(int row)
    {
        Integer[] array = getRow(row);
        if (!isArrayContainNull(array))
        {
            if (!isArrayValid(array))
            {
                JOptionPane.showMessageDialog(null, 
                    "Row " + row + " has duplicate numbers.",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return true;
        }
    }
    
    private boolean isColumnValid(int column)
    {
        Integer[] array = getColumn(column);
        if (!isArrayContainNull(array))
        {
            if (!isArrayValid(array))
            {
                JOptionPane.showMessageDialog(null, 
                    "Column " + column + " has duplicate numbers.",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return true;
        }
    }
    
    private boolean isCageValid(int row, int column)
    {
        Integer[] array = getCageValues(getGrid()[row][column].getCageID());
        if (!isArrayContainNull(array))
        {
            if (!isCageValuesValid(getGrid()[row][column].getCageID()))
            {
                JOptionPane.showMessageDialog(null, 
                    "Values of cells in the cage do not reach the target number.",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return true;
        }
    }
    
    private void isCellValid(int row, int column)
    {
        gridValidity[row][column] = isRowValid(row) && isColumnValid(column) 
                && isCageValid(row, column);
    }
    
    public boolean setCellValue(int row, int column, Integer value)
    {
        if (value > 0 && value <= size)
        {
            getGrid()[row][column].setValue(value);
            isCellValid(row, column);
            return getCellValidity(row, column);
        }
        else
        {
            JOptionPane.showMessageDialog(null, 
                    "Cell value must be between 1 and " + size + ".",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }   
    }
    
    public void unsetCellValue(int row, int column)
    {
        getGrid()[row][column].setValue(null);
        gridValidity[row][column] = null;
    }
    
    public Boolean isWin()
    {
        for (Boolean[] array : gridValidity)
        {
            for (Boolean item : array)
            {
                if (item == null)
                {
                    JOptionPane.showMessageDialog(null,
                            "There are empty cells in the grid.", 
                            "Information", JOptionPane.INFORMATION_MESSAGE);
                    return null;
                }
                if (item == false)
                {
                    JOptionPane.showMessageDialog(null,
                            "There are cells with incorrect values in the grid.", 
                            "Information", JOptionPane.INFORMATION_MESSAGE);
                    return false;
                }
            }
        }
        JOptionPane.showMessageDialog(null, "You Win!", "Information", 
                    JOptionPane.INFORMATION_MESSAGE);
        return true;
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
    
    public Boolean[][] getGridValidity()
    {
        return gridValidity;
    }
    
}
