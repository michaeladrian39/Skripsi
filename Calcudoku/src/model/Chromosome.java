/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author michaeladrian39
 */
public class Chromosome implements Comparable<Chromosome>
{
    
    private final Grid grid;
    private final int size;
    private double fitness;
    
    public Chromosome(Grid grid)
    {
        this.grid = grid;
        this.size = grid.getSize();
        printGrid();
        setFitness();
        System.out.println(getFitness());
    }
    
    private double setFitness()
    {
        int numberOfValidCells = 0;
        int numberOfCells = size * size;
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
        System.out.println(numberOfValidCells);
        double fitness = numberOfValidCells / numberOfCells;
        return fitness;
    }
    
    private double getFitness()
    {
        return fitness;
    }
    

    @Override
    public int compareTo(Chromosome otherChromosome)
    {
        if (this.getFitness() - otherChromosome.getFitness() > 0)
        {
            return 1;
        }
        else if (this.getFitness() - otherChromosome.getFitness() < 0)
        {
            return -1;
        }
        else
        {
            return 0;
        }
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
