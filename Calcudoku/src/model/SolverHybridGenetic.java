package model;

/**
 *
 * @author michaeladrian39
 */
public class SolverHybridGenetic
{
    
    private final Grid grid;
    private Grid gridRuleBased;
    private final Integer size;
    private Grid solution;
    private final Integer generations;
    private final Integer populationSize;
    private final Double elitismRate;
    private final Double mutationRate;
    private final Double crossoverRate;
    
    public SolverHybridGenetic(Grid grid, Integer generations, 
            Integer populationSize, Double elitismRate, Double crossoverRate, 
            Double mutationRate)
    {
        this.grid = grid;
        this.size = grid.getSize();
        this.generations = generations;
        this.populationSize = populationSize;
        this.elitismRate = elitismRate;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
    }
    
    public boolean solve()
    {
        // Solver akan mencoba menyelesaikan permainan dengan algoritma rule 
        // based.
        SolverRuleBased srb = new SolverRuleBased(grid);
        boolean isFilled = srb.solve();
        this.gridRuleBased = srb.getGrid();
        if (isFilled)
        {
            this.solution = srb.getSolution();
            printGrid(solution.getGridContents());
            return true;
        }
        
        // Jika algoritma rule based tidak berhasil mengisi semua sel dalam 
        // grid dengan benar, maka solver akan mencoba menyelesaikan permainan 
        // dengan algoritma genetik.
        else
        {
            SolverGenetic sg = new SolverGenetic(gridRuleBased, generations, 
                    populationSize, elitismRate, crossoverRate, mutationRate);
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