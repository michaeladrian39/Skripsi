package model;

import java.util.ArrayList;
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
        this.grid = new Cell[size][size];
        this.cages = new Cage[numberOfCages];
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
            if ((c.getOperator() == '-' || c.getOperator() == '/') 
                    && c.getSize() != 2)
            {
                return false;
            }
            if ((c.getOperator() == '+' || c.getOperator() == '*') 
                    && c.getSize() < 2)
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
    
    public ArrayList<Integer> getRow(int rowNumber)
    {
        ArrayList<Integer> row = new ArrayList<>();
        for (int i = 0; i < grid.length; i++)
        {
            if (grid[rowNumber][i].getValue() != null)
            {
                row.add(grid[rowNumber][i].getValue());
            }
        }
        return row;
    }
    
    public ArrayList<Integer> getColumn(int columnNumber)
    {
        ArrayList<Integer> column = new ArrayList<>();
        for (Cell[] row : grid) {
            if (row[columnNumber].getValue() != null) {
                column.add(row[columnNumber].getValue());
            }
        }
        return column;
    }
    
    public ArrayList<Integer> getCageValues(int cageNumber)
    {
        ArrayList<Integer> cage = new ArrayList<>();
        for (int i = 0; i < cages[cageNumber].getSize(); i++)
        {
            if (cages[cageNumber].getCells().get(i).getValue() != null)
            {
                cage.add(cages[cageNumber].getCells().get(i).getValue());
            }
        }
        return cage;
    }
    
    private boolean isArrayValid(ArrayList<Integer> array)
    {
        for (int i = 0; i < array.size(); i++)
        {
            for (int j = 0; j < array.size(); j++)
            {
                if (Objects.equals(array.get(i), array.get(j)) && (i != j))
                {
                    return false;
                }
            }
        }
        return true;
    }
    
    private Boolean isRowValid(int row)
    {
        ArrayList<Integer> array = getRow(row);
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
    
    private boolean solverIsRowValid(int row)
    {
        ArrayList<Integer> array = getRow(row);
        return isArrayValid(array);
    }
    
    private boolean isColumnValid(int column)
    {
        ArrayList<Integer> array = getColumn(column);
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
    
    private boolean solverIsColumnValid(int column)
    {
        ArrayList<Integer> array = getColumn(column);
        return isArrayValid(array);
    }
    
    private Boolean isCageValuesValid(int cageNumber)
    {
        if (cages[cageNumber].isCageValuesValid() == null)
        {
            return true;
        }
        else
        {
            return cages[cageNumber].isCageValuesValid();
        }
    }
    
    private boolean isCageValid(int row, int column)
    {
        ArrayList<Integer> cage = 
                getCageValues(getGridContents()[row][column].getCageID());
        if (isCageValuesValid(
                getGridContents()[row][column].getCageID()) == false)
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
    
    private boolean solverIsCageValid(int row, int column)
    {
        ArrayList<Integer> cage = 
                getCageValues(getGridContents()[row][column].getCageID());
        return isCageValuesValid(
                getGridContents()[row][column].getCageID()) == true;
    }
    
    public boolean isCellValueValid(int row, int column)
    {
        return (isRowValid(row) && isColumnValid(column) 
                    && isCageValid(row, column));
    }
    
    public boolean solverIsCellValueValid(int row, int column)
    {
        return (solverIsRowValid(row) && solverIsColumnValid(column) 
                    && solverIsCageValid(row, column));
    }
    
    public boolean setCellValue(int row, int column, Integer value)
    {
        if (value > 0 && value <= size)
        {
            getGridContents()[row][column].setValue(value);
            isWin();
            return isCellValueValid(row, column);
        }
        else
        {
            JOptionPane.showMessageDialog(null, 
                    "Cell value must be between 1 and " + size + ".",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }   
    }
    
    public boolean solverSetCellValue(int row, int column, Integer value)
    {
        if (value > 0 && value <= size)
        {
            getGridContents()[row][column].setValue(value);
            return (solverIsRowValid(row) && solverIsColumnValid(column) 
                    && solverIsCageValid(row, column));
        }
        else
        {
            return false;
        }   
    }
    
    public void unsetCellValue(int row, int column)
    {
        getGridContents()[row][column].setValue(null);
    }
    
    public Boolean isWin()
    {
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                if (getGridContents()[i][j].getValue() == null)
                {
                    return null;
                }
                if (isCellValueValid(i, j) == false)
                {
                    return false;
                }
            }
        }
        JOptionPane.showMessageDialog(null, 
                "Congratulations, you have successfully solved the puzzle!", 
                "Information", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }
    
    public Boolean checkGrid()
    {
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                if (getGridContents()[i][j].getValue() == null)
                {
                    JOptionPane.showMessageDialog(null, 
                            "There are empty cells in the grid.", 
                            "Information", JOptionPane.INFORMATION_MESSAGE);
                    return null;
                }
                if (solverIsCellValueValid(i, j) == false)
                {
                    JOptionPane.showMessageDialog(null,
                            "There are cells with incorrect values in the " 
                                    + "grid.", "Information", 
                                    JOptionPane.INFORMATION_MESSAGE);
                    return false;
                }
            }
        }
        JOptionPane.showMessageDialog(null, 
                "Congratulations, You have successfully solved the puzzle!", 
                "Information", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }
    
    public boolean isFilled()
    {
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                if (getGridContents()[i][j].getValue() == null)
                {
                    return false;
                }
            }
        }
        return true;
    }
    
    public Integer getCellValue(int row, int column)
    {
        return getGridContents()[row][column].getValue();
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
    
    public Cell[][] getGridContents()
    {
        return grid;
    }
    
    public Cage[] getCages()
    {
        return cages;
    }
    
    public Grid getGame()
    {
        return this;
    }
    
}