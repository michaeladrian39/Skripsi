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
    
    public SolverHybridGenetic(Grid grid)
    {
        this.grid = grid;
        this.size = grid.getSize();
        float startTime = System.nanoTime();
        if (solve() == true)
        {
            float endTime = System.nanoTime();
            float duration = (endTime - startTime) / 1000000000;
            JOptionPane.showMessageDialog(null, 
                    "The hybrid genetic algorithm has successfully solved the puzzle." 
                            + "\nTime elapsed: " + duration + " seconds", 
                    "Information", JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            float endTime = System.nanoTime();
            float duration = (endTime - startTime) / 1000000000;
            JOptionPane.showMessageDialog(null, 
                    "The hybrid genetic algorithm has failed to solve the puzzle." 
                            + "\nTime elapsed: " + duration + " seconds", 
                    "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public boolean solve()
    {
        SolverRuleBased srb = new SolverRuleBased(grid);
        boolean isFilled = srb.solve();
        gridRuleBased = srb.getGrid();
        if (isFilled)
        {
            return true;
        }
        else
        {
            SolverGenetic sg = new SolverGenetic(gridRuleBased);
            if (sg.solve() == true)
            {
                return true;
            }
        }
        return false;
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
