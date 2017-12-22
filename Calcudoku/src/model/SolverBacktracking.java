package model;

/**
 *
 * @author michaeladrian39
 */
public class SolverBacktracking
{
    
    private final Grid grid;
    private final Integer size;
    private Grid solution;
    
    public SolverBacktracking(Grid grid)
    {
        this.grid = grid;
        this.size = grid.getSize();     
    }
    
    public boolean solve()
    {
        if (solve(0, 0) == true)
        {
            this.solution = grid;
            printGrid(solution.getGridContents());
            return true;
        }
        else
        {
            return false;
        }
    }
    
    private boolean solve(int row, int column)
    {
        if (column >= size)
        {
            column = 0;
            row++;
            if (row >= size)
            {
                printGrid(grid.getGridContents());
                return true;
            }
        }
        for (int value = 1; value <= size; value++)
        {
            printGrid(grid.getGridContents());
            if (grid.solverSetCellValue(row, column, value))
            {
                if (solve(row, column + 1))
                {
                    return true;
                }
            }
        }
        printGrid(grid.getGridContents());
        grid.unsetCellValue(row, column);
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