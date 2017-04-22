/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.text.DecimalFormat;

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
