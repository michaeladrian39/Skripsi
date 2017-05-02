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
    private final int size;
    private final boolean[][] isCellFixed;
    private final Random randomGenerator;
    private int generationsNumber = 100;
    private int populationSize = 1000;
    private double elitismRate = 0.1;
    private double mutationRate = 0.1;
    private double crossoverRate = 0.9;
    private Grid solution;
    private ArrayList<Chromosome> currentGeneration = new ArrayList();
    private ArrayList<Chromosome> nextGeneration = new ArrayList();
    
    public SolverGenetic(Grid grid)
    {
        this.grid = grid;
        this.size = grid.getSize();
        this.isCellFixed = generateIsCellFixedArray();
        this.randomGenerator = new Random();   
        generatePopulation();
        for (int i = 0; i < currentGeneration.size(); i++)
        {
            printGrid(currentGeneration.get(i).getGrid().getGridContents());
            System.out.println(currentGeneration.get(i).getFitness());
        }
        solve();
    }
    
    public boolean solve()
    {
        for (int i = 0; i < generationsNumber; i++)
        {
            solveLoop();
            sortChromosomes();
            for (int j = 0; j < populationSize; j++)
            {
                printGrid(currentGeneration.get(j).getGrid().getGridContents());
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
        int nonElitismNumber = populationSize - elitismNumber;
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
        for (int i = 0; i < crossoverNumber; i++)
        {
            nonElitism.addAll(crossover(randomSelection(currentGeneration),
                    randomSelection(currentGeneration)));
        }
        while (nonElitism.size() < nonElitismNumber)
        {
            Chromosome c = randomSelection(currentGeneration);
            while (!nextGeneration.contains(c))
            {
                c = randomSelection(currentGeneration);
            }
            nonElitism.add(c);
        }
        for (int i = 0; i < mutationNumber; i++)
        {
            Chromosome parent = randomSelection(nonElitism);
            nextGeneration.add(mutation(parent));
            nonElitism.remove(parent);
        }
        nextGeneration.addAll(nonElitism);
        currentGeneration = nextGeneration;
        nextGeneration = new ArrayList();
    }

    private void setParameters(int generationsNumber, int populationSize, 
            double crossoverRate, double elitismRate, double mutationRate)
    {
        this.generationsNumber = generationsNumber;
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate;
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
