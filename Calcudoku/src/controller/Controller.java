package controller;

import model.Grid;
import model.Cage;
import model.Cell;

/**
 *
 * @author michaeladrian39
 */
public class Controller
{
    
    int size;
    int numberOfCages;
    int[][] cageCells;
    String[] cageObjectives;
    Grid g;
    
    public Controller(int size, int numberOfCages, int[][] cageCells, 
            String[] cageObjectives)
    {
        this.size = size;
        this.numberOfCages = numberOfCages;
        this.cageCells = cageCells;
        this.cageObjectives = cageObjectives;
        g = new Grid(size, numberOfCages, cageCells, cageObjectives);
    }
    
    public boolean isRowValid(int rowNumber)
    {
        return g.isRowValid(rowNumber);
    }
    
    public boolean isColumnValid(int columnNumber)
    {
        return g.isColumnValid(columnNumber);
    }
    
    public boolean isCageContentsValid(int cageNumber)
    {
        return g.isCageContentsValid(cageNumber);
    }
    
    public void setCellValue(int row, int column, int value)
    {
        g.setCellValue(row, column, value);
    }
    
    public int getCellValue(int row, int column)
    {
        return g.getCellValue(row, column);
    }
    
    public int getSize()
    {
        return g.getSize();
    }
    
    public int getNumberOfCages()
    {
        return g.getNumberOfCages();
    }
    
    public int[][] getCageCells()
    {
        return g.getCageCells();
    } 
    
    public String[] getCageObjectives()
    {
        return g.getCageObjectives();
    }
    
    public int getCageTargetNumber(int cageID)
    {
        return g.getCages()[cageID].getTargetNumber();
    }
    
    public char getCageOperator(int cageID)
    {
        return g.getCages()[cageID].getOperator();
    }
    
    public Cell[][] getGrid()
    {
        return g.getGrid();
    }
    
    public Cage[] getCages()
    {
        return g.getCages();
    }
    
}
