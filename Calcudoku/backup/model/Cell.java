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
    private final int cageID;
    private Integer value;
    
    public Cell(int cellID, int row, int column, int cageID)
    {
        this.cellID = cellID;
        this.row = row;
        this.column = column;
        this.cageID = cageID;
    }
    
    public void setValue(Integer value)
    {
        this.value = value;
    }
    
    public Integer getValue()
    {
        return value;
    }
    
    public int getRow()
    {
        return row;
    }
     
    public int getColumn()
    {
        return column;
    }
    
    public int getCageID()
    {
        return cageID;
    }
    
}