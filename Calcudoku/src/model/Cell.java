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
    private Integer value;
    
    public Cell(int cellID, int row, int column)
    {
        this.cellID = cellID;
        this.row = row;
        this.column = column;
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
    
}
