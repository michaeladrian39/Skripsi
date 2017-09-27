package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author michaeladrian39
 */
public class SolverGenetic
{
    
    private final Grid grid;
    private final Integer size;
    private final boolean[][] isCellFixed;
    private final Random randomGenerator;
    private final Integer generations;
    private final Integer populationSize;
    private final Double elitismRate;
    private final Double mutationRate;
    private final Double crossoverRate;
    private Grid solution;
    private ArrayList<Chromosome> currentGeneration = new ArrayList();
    private ArrayList<Chromosome> nextGeneration = new ArrayList();
    
    public SolverGenetic(Grid grid, Integer generations, 
            Integer populationSize, double elitismRate, double crossoverRate, 
            double mutationRate)
    {
        this.grid = grid;
        this.size = grid.getSize();
        this.generations = generations;
        this.populationSize = populationSize;
        this.elitismRate = elitismRate;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.isCellFixed = generateIsCellFixedArray();
        this.randomGenerator = new Random(); 
        generatePopulation();
        for (int i = 0; i < currentGeneration.size(); i++)
        {
            printGrid(currentGeneration.get(i).getGrid().getGridContents());
            System.out.println(currentGeneration.get(i).getFitness());
        }
    }
    
    public boolean solve()
    {
        for (int i = 0; i < generations; i++)
        {
            solveLoop();
            sortChromosomes();
            for (int j = 0; j < populationSize; j++)
            {
                printGrid(
                        currentGeneration.get(j).getGrid().getGridContents());
                System.out.println(currentGeneration.get(j).getFitness());
                if (currentGeneration.get(j).getFitness() == 1.0)
                {
                    this.solution = currentGeneration.get(j).getGrid();
                    return true;
                }
            }
        }
        return false;
    }

    private void solveLoop()
    {      
        ArrayList<Chromosome> nonElitism = new ArrayList();
        int elitismNumber = (int) Math.round(populationSize * elitismRate);
        int mutationNumber = (int) Math.round(populationSize * mutationRate);
        int crossoverNumber
                =  (int) Math.round((populationSize * crossoverRate) / 2);
        sortChromosomes();
        for (int i = 0; i < populationSize; i++)
        {
            printGrid(currentGeneration.get(i).getGrid().getGridContents());
            System.out.println(currentGeneration.get(i).getFitness());
        }
        for (int i = 0; i < elitismNumber; i++)
        {
            if (!nextGeneration.contains(currentGeneration.get(i)))
            {
                nextGeneration.add(cloneChromosome(currentGeneration.get(i)));
            }
        }
        for (int i = 0; i < mutationNumber; i++)
        {
            Chromosome parent = randomSelection(currentGeneration);
            nextGeneration.add(mutation(parent));
        }
        for (int i = 0; i < crossoverNumber; i++)
        {
            nextGeneration.addAll(crossover(randomSelection(currentGeneration),
                    randomSelection(currentGeneration)));
        }
        if (nextGeneration.size() < populationSize)
        {
            while (nextGeneration.size() < populationSize)
            {
                nextGeneration.add(randomSelection(currentGeneration));
            }
        }
        if (nextGeneration.size() > populationSize)
        {
            int extraChromosomes = nextGeneration.size() - populationSize;
            for (int i = 0; i < extraChromosomes; i++)
            {
                int index = randomGenerator.nextInt(nextGeneration.size());
                nextGeneration.remove(index);
            }
        }
        System.out.println("size" + nextGeneration.size());
        currentGeneration = nextGeneration;
        nextGeneration = new ArrayList();
    }
    
    private boolean[][] generateIsCellFixedArray()
    {
        boolean[][] array = new boolean[size][size];
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                array[i][j] = grid.getCellValue(i, j) != null;
            }
        }
        return array;
    }
    
    private void generatePopulation()
    {
        for (int i = 0; i < populationSize; i++)
        {
            currentGeneration.add(generateChromosome());
        }
    }
    
    private Chromosome generateChromosome()
    {
        Grid chromosomeGrid = new Grid(size, grid.getNumberOfCages(), 
                grid.getCageCells(), grid.getCageObjectives());
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
        Chromosome c = new Chromosome(chromosomeGrid);
        return c;
    }
    
    private void sortChromosomes()
    {
        Collections.sort(currentGeneration, 
                new ChromosomeComparator().reversed());
    }
    
    private Chromosome randomSelection(ArrayList<Chromosome> chromosomes)
    {
        int randomIndex = randomGenerator.nextInt(chromosomes.size());
        Chromosome c = cloneChromosome(chromosomes.get(randomIndex));
        return c;
    }
    
    private Chromosome tournamentSelection(ArrayList<Chromosome> chromosomes)
    {
        Chromosome bestChromosome;
        ArrayList<Chromosome> randomChromosomes = new ArrayList();
        int randomNumberOfChromosomes = 
                randomGenerator.nextInt(populationSize) + 1;
        for (int i = 0; i < randomNumberOfChromosomes; i++)
        {
            int randomChromosomeIndex = 
                    randomGenerator.nextInt(populationSize);
            randomChromosomes.add(
                    chromosomes.get(randomChromosomeIndex));
        }
        bestChromosome = Collections.max(randomChromosomes, 
                new ChromosomeComparator());
        return bestChromosome;
     }
    
    private Chromosome cloneChromosome(Chromosome c)
    {
        Grid gridClone = new Grid(size, grid.getNumberOfCages(), 
                grid.getCageCells(), grid.getCageObjectives());
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                gridClone.solverSetCellValue(i, j, 
                        c.getGrid().getCellValue(i, j));
            }
        }
        Chromosome chromosomeClone = new Chromosome(gridClone);
        return chromosomeClone;
    }
    
    private ArrayList<Chromosome> crossover(Chromosome parent1, 
            Chromosome parent2)
    {
        while (parent1 == parent2 || parent1.equals(parent2))
        {
            parent1 = randomSelection(currentGeneration);
            parent2 = randomSelection(currentGeneration);
        }
        Grid childGrid1 = new Grid(size, grid.getNumberOfCages(), 
                grid.getCageCells(), grid.getCageObjectives());
        Grid childGrid2 = new Grid(size, grid.getNumberOfCages(), 
                grid.getCageCells(), grid.getCageObjectives());
        for (int i = 0; i < size; i++)
        {
            int randomIndex = randomGenerator.nextInt(2);
            for (int j = 0; j < size; j++)
            {
                if (randomIndex == 0)
                {
                    childGrid1.solverSetCellValue(i, j, 
                            parent1.getGrid().getCellValue(i, j));
                    childGrid2.solverSetCellValue(i, j, 
                            parent2.getGrid().getCellValue(i, j));
                }
                else
                {
                    childGrid1.solverSetCellValue(i, j, 
                            parent2.getGrid().getCellValue(i, j));
                    childGrid2.solverSetCellValue(i, j, 
                            parent1.getGrid().getCellValue(i, j));
                }
            }
        }
        ArrayList<Chromosome> childChromosomes = new ArrayList();
        Chromosome child1 = new Chromosome(childGrid1);
        Chromosome child2 = new Chromosome(childGrid2);
        childChromosomes.add(child1);
        childChromosomes.add(child2);
        return childChromosomes;
    }
    
    private Chromosome mutation(Chromosome parent)
    {
        Grid childGrid = new Grid(size, grid.getNumberOfCages(), 
                grid.getCageCells(), grid.getCageObjectives());
        int randomRow = randomGenerator.nextInt(size);
        int randomColumn1 = randomGenerator.nextInt(size);
        int randomColumn2 = randomGenerator.nextInt(size);
        while (isCellFixed[randomRow][randomColumn1] == true 
                || isCellFixed[randomRow][randomColumn2] == true 
                || randomColumn1 == randomColumn2)
        {
            randomRow = randomGenerator.nextInt(size);
            randomColumn1 = randomGenerator.nextInt(size);
            randomColumn2 = randomGenerator.nextInt(size);
        }
        for (int i = 0; i < size; i++)
        {
            if (i == randomRow)
            {
                childGrid.solverSetCellValue(i, randomColumn1, 
                        parent.getGrid().getCellValue(i, randomColumn2));
                childGrid.solverSetCellValue(i, randomColumn2, 
                        parent.getGrid().getCellValue(i, randomColumn1));
            }
            for (int j = 0; j < size; j++)
            {
                if (childGrid.getCellValue(i, j) == null)
                {
                    childGrid.solverSetCellValue(i, j, 
                        parent.getGrid().getCellValue(i, j));
                }
            }
        }
        Chromosome child = new Chromosome(childGrid);
        return child;
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
    
    private void printPossibleValues(ArrayList<Integer>[][] possibleValues)
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