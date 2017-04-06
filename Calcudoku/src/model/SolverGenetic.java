package model;

/**
 *
 * @author michaeladrian39
 */
public class SolverGenetic
{
    
    private final Grid grid;
    private final int size;
    
    public SolverGenetic(Grid grid)
    {
        this.grid = grid;
        this.size = grid.getSize();
    }
    
    public void printGrid()
    {
        Cell[][] cells = grid.getGrid();
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                System.out.print(cells[i][j].getValue() + " ");
            }
            System.out.println("");
        }
        System.out.println("");
    }
    
}
