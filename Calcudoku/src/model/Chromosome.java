package model;

import java.util.Comparator;

/**
 *
 * @author michaeladrian39
 */
public class Chromosome
{
    
    private final Grid grid;
    private final int size;
    private final double fitness;
    
    public Chromosome(Grid grid)
    {
        this.grid = grid;
        this.size = grid.getSize();
        this.fitness = setFitness();
    }
    
    private double setFitness()
    {
        double numberOfValidCells = 0;
        double numberOfCells = size * size;
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                if (grid.solverIsCellValueValid(i, j) == true)
                {
                    numberOfValidCells++;
                }
            }
        }
        double value = numberOfValidCells / numberOfCells;
        return value;
    }
    
    public double getFitness()
    {
        return fitness;
    }
    
    public Grid getGrid()
    {
        return grid;
    }

    public void printGrid()
    {
        Cell[][] cells = grid.getGridContents();
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

class ChromosomeComparator implements Comparator<Chromosome>
{

    @Override
    public int compare(Chromosome c1, Chromosome c2)
    {
        if (c1.getFitness() - c2.getFitness() > 0)
        {
            return 1;
        }
        else if (c1.getFitness() - c2.getFitness() < 0)
        {
            return -1;
        }
        else
        {
            return 0;
        }
    }
    
}

