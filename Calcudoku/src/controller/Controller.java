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
    
    private int size;
    private int numberOfCages;
    private int[][] cageCells;
    private String[] cageObjectives;
    private Grid g;
    
    public Controller(int size, int numberOfCages, int[][] cageCells, 
            String[] cageObjectives)
    {
        this.size = size;
        this.numberOfCages = numberOfCages;
        this.cageCells = cageCells;
        this.cageObjectives = cageObjectives;
        g = new Grid(size, numberOfCages, cageCells, cageObjectives);
    }
    
    public boolean setCellValue(int row, int column, int value)
    {
        return g.setCellValue(row, column, value);
    }
    
    public void unsetCellValue(int row, int column)
    {
        g.unsetCellValue(row, column);
    }
    
    public Boolean checkGrid()
    {
        return g.checkGrid();
    }
    
    public Integer getCellValue(int row, int column)
    {
        return g.getCellValue(row, column);
    }
    
    public Integer getSize()
    {
        return g.getSize();
    }
    
    public Integer getNumberOfCages()
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
        return g.getGridContents();
    }
    
    public Cage[] getCages()
    {
        return g.getCages();
    }

    public Grid getGame()
    {
        return g.getGame();
    }
    
}