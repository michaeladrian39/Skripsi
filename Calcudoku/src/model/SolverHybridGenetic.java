package model;

import javax.swing.JOptionPane;

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
        float startTime = System.nanoTime();
        if (solve() == true)
        {
            float endTime = System.nanoTime();
            float duration = (endTime - startTime) / 1000000000;
            System.out.println("The hybrid genetic algorithm has successfully "
                    + "solved the puzzle." + "\nTime elapsed: " + duration 
                    + " seconds");
            JOptionPane.showMessageDialog(null, 
                    "The hybrid genetic algorithm has successfully solved the puzzle." 
                            + "\nTime elapsed: " + duration + " seconds", 
                    "Information", JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            float endTime = System.nanoTime();
            float duration = (endTime - startTime) / 1000000000;
            System.out.println("The hybrid genetic algorithm has failed to "
                    + "solve the puzzle." + "\nTime elapsed: " + duration 
                    + " seconds");
            JOptionPane.showMessageDialog(null, 
                    "The hybrid genetic algorithm has failed to solve the puzzle." 
                            + "\nTime elapsed: " + duration + " seconds", 
                    "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private boolean solve()
    {
        SolverRuleBased srb = new SolverRuleBased(grid);
        boolean isFilled = srb.solve();
        gridRuleBased = srb.getGrid();
        if (isFilled)
        {
            solution = srb.getSolution();
            printGrid(solution.getGridContents());
            return true;
        }
        else
        {
            SolverGenetic sg = new SolverGenetic(gridRuleBased);
            if (sg.solve() == true)
            {
                solution = sg.getSolution();
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
