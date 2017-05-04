package model;

import java.util.Objects;
import javax.swing.JOptionPane;

/**
 *
 * @author apple
 */
public class SolverBacktracking
{
    
    private final Grid grid;
    private final int size;
    private Grid solution;
    
    public SolverBacktracking(Grid grid)
    {
        this.grid = grid;
        this.size = grid.getSize();
        float startTime = System.nanoTime();
        if (solve() == true)
        {
            float endTime = System.nanoTime();
            this.solution = grid;
            printGrid(solution.getGridContents());
            float duration = (endTime - startTime) / 1000000000;
            System.out.println("The backtracking algorithm has successfully "
                    + "solved the puzzle." + "\nTime elapsed: " + duration 
                    + " seconds");
            JOptionPane.showMessageDialog(null, 
                    "The backtracking algorithm has successfully solved the puzzle." 
                            + "\nTime elapsed: " + duration + " seconds", 
                    "Information", JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            float endTime = System.nanoTime();
            float duration = (endTime - startTime) / 1000000000;
            System.out.println("The backtracking algorithm has failed to solve "
                    + "the puzzle." + "\nTime elapsed: " + duration 
                    + " seconds");
            JOptionPane.showMessageDialog(null, 
                    "The backtracking algorithm has failed to solve the puzzle." 
                            + "\nTime elapsed: " + duration + " seconds", 
                    "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public boolean solve()
    {
        return solve(0, 0);
    }
    
    public boolean solve(int row, int column)
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
    
    public void printGrid(Cell[][] cells)
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
