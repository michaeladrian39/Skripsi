package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author michaeladrian39
 */
public class SolverGenetic
{
    
    private final Grid grid;
    private final int size;
    private final boolean[][] isCellFixed;
    private final Random randomGenerator;
    private int numberOfGenerations = 1000;
    private int sizeOfPopulation = 1000;
    private double elitismRate = 0.1;
    private double mutationRate = 0.1;
    ArrayList<Chromosome> currentGeneration = new ArrayList();
    ArrayList<Chromosome> nextGeneration = new ArrayList();
    
    public SolverGenetic(Grid grid)
    {
        this.grid = grid;
        this.size = grid.getSize();
        this.isCellFixed = generateIsCellFixedArray();
        this.randomGenerator = new Random();
        for (int i = 0; i < sizeOfPopulation; i++)
        {
            generateChromosome();
        }
    }

    private void setParameters(int numberOfGenerations, 
            int sizeOfPopulation, double elitismRate, double mutationRate)
    {
        this.numberOfGenerations = numberOfGenerations;
        this.sizeOfPopulation = sizeOfPopulation;
        this.elitismRate = elitismRate;
        this.mutationRate = mutationRate;
    }
    
    private boolean[][] generateIsCellFixedArray()
    {
        boolean[][] array = new boolean[size][size];
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                if (grid.getCellValue(i, j) == null)
                {
                    array[i][j] = false;
                }
                else
                {
                    array[i][j] = true;
                }
            }
        }
        return array;
    }
    
    private void generateChromosome()
    {
//        Grid chromosomeGrid = new Grid(size, grid.getNumberOfCages(), 
//                grid.getCageCells(), grid.getCageObjectives());
        Grid chromosomeGrid = grid;
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                if (grid.getCellValue(i, j) != null)
                {
                    chromosomeGrid.solverSetCellValue(i, j, 
                            grid.getCellValue(i, j));
                }
            }
        }
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                if (!grid.getRow(i).contains(j + 1))
                {
                    int index = randomGenerator.nextInt(size);
                    while (chromosomeGrid.getCellValue(i, index) != null)
                    {
                        index = randomGenerator.nextInt(size);
                    }
                    chromosomeGrid.solverSetCellValue(i, index, j + 1);
                }
            }
        }
        printGrid(chromosomeGrid.getGrid());
        Chromosome c = new Chromosome(chromosomeGrid);
        currentGeneration.add(c);
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
    
    public void printPossibleValues(ArrayList<Integer>[][] possibleValues)
    {
        for (int i = 0; i < possibleValues.length; i++)
        {
            for (int j = 0; j < possibleValues[i].length; j++)
            {
                System.out.println("Row " + i + ", Column " + j);
                for (int k = 0; k < possibleValues[i][j].size(); k++)
                {
                    System.out.print(possibleValues[i][j].get(k) + " ");
                }
                System.out.println("");
            }
            System.out.println("");
        }
        System.out.println("");
    }
    
}
