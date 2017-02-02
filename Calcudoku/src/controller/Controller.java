package controller;

import model.Board;
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
    Board b;
    
    public Controller(int size, int numberOfCages, int[][] cageCells, 
            String[] cageObjectives)
    {
        this.size = size;
        this.numberOfCages = numberOfCages;
        this.cageCells = cageCells;
        this.cageObjectives = cageObjectives;
        b = new Board(size, numberOfCages, cageCells, cageObjectives);
    }
    
    public void setCellValue(int row, int column, int value)
    {
        b.setCellValue(row, column, value);
    }
    
    public int getCellValue(int row, int column)
    {
        return b.getCellValue(row, column);
    }
    
    public int getSize()
    {
        return b.getSize();
    }
    
    public int getNumberOfCages()
    {
        return b.getNumberOfCages();
    }
    
    public int[][] getCageCells()
    {
        return b.getCageCells();
    } 
    
    public String[] getCageObjectives()
    {
        return b.getCageObjectives();
    }
    
    public int getCageTargetNumber(int cageID)
    {
        return b.getCages()[cageID].getTargetNumber();
    }
    
    public char getCageOperator(int cageID)
    {
        return b.getCages()[cageID].getOperator();
    }
    
}
