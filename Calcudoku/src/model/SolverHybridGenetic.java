package model;

/**
 *
 * @author michaeladrian39
 */
public class SolverHybridGenetic
{
    
    private final Grid grid;
    private final int size;
    
    public SolverHybridGenetic(Grid grid)
    {
        this.grid = grid;
        this.size = grid.getSize();
        solve();
    }
    
    public void solve()
    {
        SolverRuleBased srb = new SolverRuleBased(grid);
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