package model;

/**
 *
 * @author michaeladrian39
 */
public class SolverHybridGenetic
{
    
    private final Grid grid;
    private Grid gridRuleBased;
    private final int size;
    private Grid solution;
    
    public SolverHybridGenetic(Grid grid)
    {
        this.grid = grid;
        this.size = grid.getSize();
    }
    
    public boolean solve()
    {
        SolverRuleBased srb = new SolverRuleBased(grid);
        boolean isFilled = srb.solve();
        this.gridRuleBased = srb.getGrid();
        if (isFilled)
        {
            this.solution = srb.getSolution();
            printGrid(solution.getGridContents());
            return true;
        }
        else
        {
            SolverGenetic sg = new SolverGenetic(gridRuleBased);
            if (sg.solve() == true)
            {
                this.solution = sg.getSolution();
                printGrid(solution.getGridContents());
                return true;
            }
        }
        return false;
    }
    
    public Grid getGrid()
    {
        return grid;
    }

    public Grid getSolution()
    {
        return solution;
    }
    
    private void printGrid(Cell[][] cells)
    {
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