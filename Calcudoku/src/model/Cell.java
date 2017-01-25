package model;

/**
 *
 * @author michaeladrian39
 */
public class Cell
{
    
    private final int cellID;
    private final int row;
    private final int column;
    private int value;
    
    public Cell(int cellID, int row, int column)
    {
        this.cellID = cellID;
        this.row = row;
        this.column = column;
    }
    
    public void setValue(int value)
    {
        this.value = value;
    }
    
    public int getValue()
    {
        return value;
    }
    
}
