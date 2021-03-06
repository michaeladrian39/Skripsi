package model;

import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;

/**
 *
 * @author michaeladrian39
 */
public class SolverRuleBased
{
    
    private final Grid grid;
    private final Integer size;
    private Grid solution;
    private ArrayList<Integer>[][] possibleValues;
    
    public SolverRuleBased(Grid grid)
    {
        this.grid = grid;
        this.size = grid.getSize();
        this.possibleValues = generatePossibleValuesArray();
    }
    
    @SuppressWarnings("unchecked")
	private ArrayList<Integer>[][] generatePossibleValuesArray()
    {
        possibleValues = new ArrayList[size][size];
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                possibleValues[i][j] = new ArrayList<Integer>();
            }
        }
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                for (int k = 1; k <= size; k++)
                {
                    possibleValues[i][j].add(k);
                }
            }
        }
        return possibleValues;
    }
    
    public boolean solve()
    {
        singleSquare();
        killerCombination();
        ArrayList<ArrayList<Integer>> currentGridArrayList 
                = getGridArrayList();
        ArrayList<ArrayList<Integer>> newGridArrayList = solveLoop();
        while(!currentGridArrayList.equals(newGridArrayList))
        {
            printGrid();
            printPossibleValues();
            currentGridArrayList = newGridArrayList;
            newGridArrayList = solveLoop();
        }
        if (grid.isFilled())
        {
            this.solution = grid;
            return true;
        }
        else
        {
            return false;
        }
    }
    
    private ArrayList<ArrayList<Integer>> solveLoop()
    {
        nakedSingle();
        nakedDouble();
        hiddenSingle();
        return getGridArrayList();
    }
    
    @SuppressWarnings("unchecked")
	public ArrayList<Integer>[] getRowPossibleValues(int row)
    {
        ArrayList<Integer>[] array;
        array = new ArrayList[size];
        for (int i = 0; i < size; i++)
        {
            array[i] = possibleValues[row][i];
        }
        return array;
    }
    
    @SuppressWarnings("unchecked")
	public ArrayList<Integer>[] getColumnPossibleValues(int column)
    {
        ArrayList<Integer>[] array;
        array = new ArrayList[size];
        for (int i = 0; i < size; i++)
        {
            array[i] = possibleValues[i][column];
        }
        return array;
    }
    
    private void singleSquare()
    {
        for (Cage c : grid.getCages())
        {
            if (c.getSize() == 1)
            {
                setCellValue(c.getCells().get(0).getRow(), 
                        c.getCells().get(0).getColumn(), c.getTargetNumber());
            }
        }
    }
    
    private void killerCombination()
    {
        int cageSize;
        ArrayList<Integer> array = new ArrayList<Integer>();
        for (Cage c : grid.getCages())
        {
            cageSize = c.getSize();
            switch (cageSize)
            {
                case 2:
                    killerCombinationCageSize2(c);
                    break;
                default:
                    array = createRetainAllArray();
                    removeImpossibleValuesCage(c, array);
                    break;
            }
        }
    }
    
    private void killerCombinationCageSize2(Cage cage)
    {
        int gridSize = size;
        ArrayList<Integer> array = new ArrayList<Integer>();
        switch (gridSize)
        {
            case 3 :
                array = killerCombinationCageSize2GridSize3(cage);
                break;
            case 4 :
                array = killerCombinationCageSize2GridSize4(cage);
                break;
            case 5 :
                array = killerCombinationCageSize2GridSize5(cage);
                break;
            case 6 :
                array = killerCombinationCageSize2GridSize6(cage);
                break;
            case 7 :
                array = killerCombinationCageSize2GridSize7(cage);
                break;
            case 8 :
                array = killerCombinationCageSize2GridSize8(cage);
                break;
            case 9 :
                array = killerCombinationCageSize2GridSize9(cage);
                break;
            default :
                JOptionPane.showMessageDialog(null, "Invalid grid size.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                throw new IllegalStateException("Invalid grid size.");
        }
        removeImpossibleValuesCage(cage, array);
    }

    private ArrayList<Integer> killerCombinationCageSize2GridSize3(Cage cage)
    {
        char cageOperator = cage.getOperator();
        int cageTargetNumber = cage.getTargetNumber();
        ArrayList<Integer> array = new ArrayList<Integer>();
        switch (cageOperator)
        {
            case '+' :
                switch (cageTargetNumber)
                {
                    case 3 :
                        array.add(1);
                        array.add(2);
                        break;
                    case 4 :  
                        array.add(1);
                        array.add(3);
                        break;
                    case 5 :
                        array.add(2);
                        array.add(3);
                        break;
                    default :
                        array = createRetainAllArray();
                        break;
                }
                break;
            case '-' :
                switch (cageTargetNumber)
                {
                    case 2 :
                        array.add(1);
                        array.add(3);
                        break;
                    default :
                        array = createRetainAllArray();
                        break;
                }
                break;
            case '*' :
               switch (cageTargetNumber)
                {
                    case 2 :
                        array.add(1);
                        array.add(2);
                        break;
                    case 3 :
                        array.add(1);
                        array.add(3);
                        break;
                    case 6 :
                        array.add(2);
                        array.add(3);
                        break;
                    default :
                        array = createRetainAllArray();
                        break;
                }
                break;
            case '/' :
                switch (cageTargetNumber)
                {
                    case 2 :
                        array.add(1);
                        array.add(2);
                        break;
                    case 3 :
                        array.add(1);
                        array.add(3);
                        break;
                    default :
                        array = createRetainAllArray();
                        break;
                }
                break;
            default :
                JOptionPane.showMessageDialog(null, 
                        "Invalid operator.", "Error", 
                        JOptionPane.ERROR_MESSAGE);
                throw new IllegalStateException("Invalid operator.");
        }
        return array;
    }
    
    private ArrayList<Integer> killerCombinationCageSize2GridSize4(Cage cage)
    {
        char cageOperator = cage.getOperator();
        int cageTargetNumber = cage.getTargetNumber();
        ArrayList<Integer> array = new ArrayList<Integer>();
        switch (cageOperator)
        {
            case '+' :
                switch (cageTargetNumber)
                {
                    case 3 :
                        array.add(1);
                        array.add(2);
                        break;
                    case 4 :  
                        array.add(1);
                        array.add(3);
                        break;
                    case 6 :
                        array.add(2);
                        array.add(4);
                        break;
                    case 7 :
                        array.add(3);
                        array.add(4);
                        break;
                    default :
                        array = createRetainAllArray();
                        break;
                }
                break;
            case '-' :
                switch (cageTargetNumber)
                {
                    case 3 :
                        array.add(1);
                        array.add(4);
                        break;
                    default :
                        array = createRetainAllArray();
                        break;
                }
                break;
            case '*' :
               switch (cageTargetNumber)
                {
                    case 2 :
                        array.add(1);
                        array.add(2);
                        break;
                    case 3 :
                        array.add(1);
                        array.add(3);
                        break;
                    case 4 :
                        array.add(1);
                        array.add(4);
                        break;
                    case 6 :
                        array.add(2);
                        array.add(3);
                        break;
                    case 8 :
                        array.add(2);
                        array.add(4);
                        break;
                    case 12 :
                        array.add(3);
                        array.add(4);
                        break;
                    default :
                        array = createRetainAllArray();
                        break;
                }
                break;
            case '/' :
                switch (cageTargetNumber)
                {
                    case 3 :
                        array.add(1);
                        array.add(3);
                        break;
                    case 4 :
                        array.add(1);
                        array.add(4);
                        break;
                    default :
                        array = createRetainAllArray();
                        break;
                }
                break;
            default :
                JOptionPane.showMessageDialog(null, 
                        "Invalid operator.", "Error", 
                        JOptionPane.ERROR_MESSAGE);
                throw new IllegalStateException("Invalid operator.");
        }
        return array;
    }
    
    private ArrayList<Integer> killerCombinationCageSize2GridSize5(Cage cage)
    {
        char cageOperator = cage.getOperator();
        int cageTargetNumber = cage.getTargetNumber();
        ArrayList<Integer> array = new ArrayList<Integer>();
        switch (cageOperator)
        {
            case '+' :
                switch (cageTargetNumber)
                {
                    case 3 :
                        array.add(1);
                        array.add(2);
                        break;
                    case 4 :  
                        array.add(1);
                        array.add(3);
                        break;
                    case 8 :
                        array.add(3);
                        array.add(5);
                        break;
                    case 9 :
                        array.add(4);
                        array.add(5);
                        break;
                    default :
                        array = createRetainAllArray();
                        break;
                }
                break;
            case '-' :
                switch (cageTargetNumber)
                {
                    case 4 :
                        array.add(1);
                        array.add(5);
                        break;
                    default :
                        array = createRetainAllArray();
                        break;
                }
                break;
            case '*' :
               switch (cageTargetNumber)
                {
                    case 2 :
                        array.add(1);
                        array.add(2);
                        break;
                    case 3 :
                        array.add(1);
                        array.add(3);
                        break;
                    case 4 :
                        array.add(1);
                        array.add(4);
                        break;
                    case 5 :
                        array.add(1);
                        array.add(5);
                        break;
                    case 6 :
                        array.add(2);
                        array.add(3);
                        break;
                    case 8 :
                        array.add(2);
                        array.add(4);
                        break;
                    case 10 :
                        array.add(2);
                        array.add(5);
                        break;
                    case 12 :
                        array.add(3);
                        array.add(4);
                        break;
                    case 15 :
                        array.add(3);
                        array.add(5);
                        break;
                    case 20 :
                        array.add(4);
                        array.add(5);
                        break;
                    default :
                        array = createRetainAllArray();
                        break;
                }
                break;
            case '/' :
                switch (cageTargetNumber)
                {
                    case 3 :
                        array.add(1);
                        array.add(3);
                        break;
                    case 4 :
                        array.add(1);
                        array.add(4);
                        break;
                    case 5 :
                        array.add(1);
                        array.add(5);
                        break;
                    default :
                        array = createRetainAllArray();
                        break;
                }
                break;
            default :
                JOptionPane.showMessageDialog(null, 
                        "Invalid operator.", "Error", 
                        JOptionPane.ERROR_MESSAGE);
                throw new IllegalStateException("Invalid operator.");
        }
        return array;
    }
    
    private ArrayList<Integer> killerCombinationCageSize2GridSize6(Cage cage)
    {
        char cageOperator = cage.getOperator();
        int cageTargetNumber = cage.getTargetNumber();
        ArrayList<Integer> array = new ArrayList<Integer>();
        switch (cageOperator)
        {
            case '+' :
                switch (cageTargetNumber)
                {
                    case 3 :
                        array.add(1);
                        array.add(2);
                        break;
                    case 4 :  
                        array.add(1);
                        array.add(3);
                        break;
                    case 10 :
                        array.add(4);
                        array.add(6);
                        break;
                    case 11 :
                        array.add(5);
                        array.add(6);
                        break;
                    default :
                        array = createRetainAllArray();
                        break;
                }
                break;
            case '-' :
                switch (cageTargetNumber)
                {
                    case 5 :
                        array.add(1);
                        array.add(6);
                        break;
                    default :
                        array = createRetainAllArray();
                        break;
                }
                break;
            case '*' :
               switch (cageTargetNumber)
                {
                    case 2 :
                        array.add(1);
                        array.add(2);
                        break;
                    case 3 :
                        array.add(1);
                        array.add(3);
                        break;
                    case 4 :
                        array.add(1);
                        array.add(4);
                        break;
                    case 5 :
                        array.add(1);
                        array.add(5);
                        break;
                    case 8 :
                        array.add(2);
                        array.add(4);
                        break;
                    case 10 :
                        array.add(2);
                        array.add(5);
                        break;
                    case 15 :
                        array.add(3);
                        array.add(5);
                        break;
                    case 18 :
                        array.add(3);
                        array.add(6);
                        break;
                    case 20 :
                        array.add(4);
                        array.add(5);
                        break;
                    case 24 :
                        array.add(4);
                        array.add(6);
                        break;
                    case 30 :
                        array.add(5);
                        array.add(6);
                        break;
                    default :
                        array = createRetainAllArray();
                        break;
                }
                break;
            case '/' :
                switch (cageTargetNumber)
                {
                    case 4 :
                        array.add(1);
                        array.add(4);
                        break;
                    case 5 :
                        array.add(1);
                        array.add(5);
                        break;
                    case 6 :
                        array.add(1);
                        array.add(6);
                        break;
                    default :
                        array = createRetainAllArray();
                        break;
                }
                break;
            default :
                JOptionPane.showMessageDialog(null, 
                        "Invalid operator.", "Error", 
                        JOptionPane.ERROR_MESSAGE);
                throw new IllegalStateException("Invalid operator.");
        }
        return array;
    }
    
    private ArrayList<Integer> killerCombinationCageSize2GridSize7(Cage cage)
    {
        char cageOperator = cage.getOperator();
        int cageTargetNumber = cage.getTargetNumber();
        ArrayList<Integer> array = new ArrayList<Integer>();
        switch (cageOperator)
        {
            case '+' :
                switch (cageTargetNumber)
                {
                    case 3 :
                        array.add(1);
                        array.add(2);
                        break;
                    case 4 :  
                        array.add(1);
                        array.add(3);
                        break;
                    case 12 :
                        array.add(5);
                        array.add(7);
                        break;
                    case 13 :
                        array.add(6);
                        array.add(7);
                        break;
                    default :
                        array = createRetainAllArray();
                        break;
                }
                break;
            case '-' :
                switch (cageTargetNumber)
                {
                    case 6 :
                        array.add(1);
                        array.add(7);
                        break;
                    default :
                        array = createRetainAllArray();
                        break;
                }
                break;
            case '*' :
               switch (cageTargetNumber)
                {
                    case 2 :
                        array.add(1);
                        array.add(2);
                        break;
                    case 3 :
                        array.add(1);
                        array.add(3);
                        break;
                    case 4 :
                        array.add(1);
                        array.add(4);
                        break;
                    case 5 :
                        array.add(1);
                        array.add(5);
                        break;
                    case 7 :
                        array.add(1);
                        array.add(7);
                        break;
                    case 8 :
                        array.add(2);
                        array.add(4);
                        break;
                    case 10 :
                        array.add(2);
                        array.add(5);
                        break;
                    case 14 :
                        array.add(2);
                        array.add(7);
                        break;
                    case 15 :
                        array.add(3);
                        array.add(5);
                        break;
                    case 18 :
                        array.add(3);
                        array.add(6);
                        break;
                    case 20 :
                        array.add(4);
                        array.add(5);
                        break;
                    case 21 :
                        array.add(3);
                        array.add(7);
                        break;
                    case 24 :
                        array.add(4);
                        array.add(6);
                        break;
                    case 28 :
                        array.add(4);
                        array.add(7);
                        break;
                    case 30 :
                        array.add(5);
                        array.add(6);
                        break;
                    case 35 :
                        array.add(5);
                        array.add(7);
                        break;
                    case 42 :
                        array.add(6);
                        array.add(7);
                        break;
                    default :
                        array = createRetainAllArray();
                        break;
                }
                break;
            case '/' :
                switch (cageTargetNumber)
                {
                    case 4 :
                        array.add(1);
                        array.add(4);
                        break;
                    case 5 :
                        array.add(1);
                        array.add(5);
                        break;
                    case 6 :
                        array.add(1);
                        array.add(6);
                        break;
                    case 7 :
                        array.add(1);
                        array.add(7);
                        break;
                    default :
                        array = createRetainAllArray();
                        break;
                }
                break;
            default :
                JOptionPane.showMessageDialog(null, 
                        "Invalid operator.", "Error", 
                        JOptionPane.ERROR_MESSAGE);
                throw new IllegalStateException("Invalid operator.");
        }
        return array;
    }
    
    private ArrayList<Integer> killerCombinationCageSize2GridSize8(Cage cage)
    {
        char cageOperator = cage.getOperator();
        int cageTargetNumber = cage.getTargetNumber();
        ArrayList<Integer> array = new ArrayList<Integer>();
        switch (cageOperator)
        {
            case '+' :
                switch (cageTargetNumber)
                {
                    case 3 :
                        array.add(1);
                        array.add(2);
                        break;
                    case 4 :  
                        array.add(1);
                        array.add(3);
                        break;
                    case 14 :
                        array.add(6);
                        array.add(8);
                        break;
                    case 15 :
                        array.add(7);
                        array.add(8);
                        break;
                    default :
                        array = createRetainAllArray();
                        break;
                }
                break;
            case '-' :
                switch (cageTargetNumber)
                {
                    case 7 :
                        array.add(1);
                        array.add(8);
                        break;
                    default :
                        array = createRetainAllArray();
                        break;
                }
                break;
            case '*' :
               switch (cageTargetNumber)
                {
                    case 2 :
                        array.add(1);
                        array.add(2);
                        break;
                    case 3 :
                        array.add(1);
                        array.add(3);
                        break;
                    case 4 :
                        array.add(1);
                        array.add(4);
                        break;
                    case 5 :
                        array.add(1);
                        array.add(5);
                        break;
                    case 7 :
                        array.add(1);
                        array.add(7);
                        break;
                    case 10 :
                        array.add(2);
                        array.add(5);
                        break;
                    case 14 :
                        array.add(2);
                        array.add(7);
                        break;
                    case 15 :
                        array.add(3);
                        array.add(5);
                        break;
                    case 16 :
                        array.add(2);
                        array.add(8);
                        break;
                    case 18 :
                        array.add(3);
                        array.add(6);
                        break;
                    case 20 :
                        array.add(4);
                        array.add(5);
                        break;
                    case 21 :
                        array.add(3);
                        array.add(7);
                        break;
                    case 28 :
                        array.add(4);
                        array.add(7);
                        break;
                    case 30 :
                        array.add(5);
                        array.add(6);
                        break;
                    case 32 :
                        array.add(4);
                        array.add(8);
                        break;
                    case 35 :
                        array.add(5);
                        array.add(7);
                        break;
                    case 40 :
                        array.add(5);
                        array.add(8);
                        break;
                    case 42 :
                        array.add(6);
                        array.add(7);
                        break;
                    case 48 :
                        array.add(6);
                        array.add(8);
                        break;
                    case 56 :
                        array.add(7);
                        array.add(8);
                        break;
                    default :
                        array = createRetainAllArray();
                        break;
                }
                break;
            case '/' :
                switch (cageTargetNumber)
                {
                    case 5 :
                        array.add(1);
                        array.add(5);
                        break;
                    case 6 :
                        array.add(1);
                        array.add(6);
                        break;
                    case 7 :
                        array.add(1);
                        array.add(7);
                        break;
                    case 8 :
                        array.add(1);
                        array.add(8);
                        break;
                    default :
                        array = createRetainAllArray();
                        break;
                }
                break;
            default :
                JOptionPane.showMessageDialog(null, 
                        "Invalid operator.", "Error", 
                        JOptionPane.ERROR_MESSAGE);
                throw new IllegalStateException("Invalid operator.");
        }
        return array;
    }
    
    private ArrayList<Integer> killerCombinationCageSize2GridSize9(Cage cage)
    {
        char cageOperator = cage.getOperator();
        int cageTargetNumber = cage.getTargetNumber();
        ArrayList<Integer> array = new ArrayList<Integer>();
        switch (cageOperator)
        {
            case '+' :
                switch (cageTargetNumber)
                {
                    case 3 :
                        array.add(1);
                        array.add(2);
                        break;
                    case 4 :  
                        array.add(1);
                        array.add(3);
                        break;
                    case 16 :
                        array.add(7);
                        array.add(9);
                        break;
                    case 17 :
                        array.add(8);
                        array.add(9);
                        break;
                    default :
                        array = createRetainAllArray();
                        break;
                }
                break;
            case '-' :
                switch (cageTargetNumber)
                {
                    case 8 :
                        array.add(1);
                        array.add(9);
                        break;
                    default :
                        array = createRetainAllArray();
                        break;
                }
                break;
            case '*' :
               switch (cageTargetNumber)
                {
                    case 2 :
                        array.add(1);
                        array.add(2);
                        break;
                    case 3 :
                        array.add(1);
                        array.add(3);
                        break;
                    case 4 :
                        array.add(1);
                        array.add(4);
                        break;
                    case 5 :
                        array.add(1);
                        array.add(5);
                        break;
                    case 7 :
                        array.add(1);
                        array.add(7);
                        break;
                    case 9 :
                        array.add(1);
                        array.add(9);
                        break;
                    case 10 :
                        array.add(2);
                        array.add(5);
                        break;
                    case 14 :
                        array.add(2);
                        array.add(7);
                        break;
                    case 15 :
                        array.add(3);
                        array.add(5);
                        break;
                    case 16 :
                        array.add(2);
                        array.add(8);
                        break;
                    case 20 :
                        array.add(4);
                        array.add(5);
                        break;
                    case 21 :
                        array.add(3);
                        array.add(7);
                        break;
                    case 27 :
                        array.add(3);
                        array.add(9);
                        break;
                    case 28 :
                        array.add(4);
                        array.add(7);
                        break;
                    case 30 :
                        array.add(5);
                        array.add(6);
                        break;
                    case 32 :
                        array.add(4);
                        array.add(8);
                        break;
                    case 35 :
                        array.add(5);
                        array.add(7);
                        break;
                    case 36 :
                        array.add(4);
                        array.add(9);
                        break;
                    case 40 :
                        array.add(5);
                        array.add(8);
                        break;
                    case 42 :
                        array.add(6);
                        array.add(7);
                        break;
                    case 45 :
                        array.add(5);
                        array.add(9);
                        break;
                    case 48 :
                        array.add(6);
                        array.add(8);
                        break;
                    case 54 :
                        array.add(6);
                        array.add(9);
                        break;
                    case 56 :
                        array.add(7);
                        array.add(8);
                        break;
                    case 63 :
                        array.add(7);
                        array.add(9);
                        break;
                    case 72 :
                        array.add(8);
                        array.add(9);
                        break;
                    default :
                        array = createRetainAllArray();
                        break;
                }
                break;
            case '/' :
                switch (cageTargetNumber)
                {
                    case 5 :
                        array.add(1);
                        array.add(5);
                        break;
                    case 6 :
                        array.add(1);
                        array.add(6);
                        break;
                    case 7 :
                        array.add(1);
                        array.add(7);
                        break;
                    case 8 :
                        array.add(1);
                        array.add(8);
                        break;
                    case 9 :
                        array.add(1);
                        array.add(9);
                        break;
                    default :
                        array = createRetainAllArray();
                        break;
                }
                break;
            default :
                JOptionPane.showMessageDialog(null, 
                        "Invalid operator.", "Error", 
                        JOptionPane.ERROR_MESSAGE);
                throw new IllegalStateException("Invalid operator.");
        }
        return array;
    }
    
    private void nakedSingle()
    {
        nakedSingleRow();
        nakedSingleColumn();
    }
    
    private void nakedSingleRow()
    {
        for (int i = 0; i < size; i++)
        {
            nakedSingleRow(i);
        }
    }
    
    private void nakedSingleRow(int row)
    {
        ArrayList<Integer>[] rowPossibleValues = getRowPossibleValues(row);
        for (int i = 0; i < rowPossibleValues.length; i++)
        {
            if (rowPossibleValues[i].size() == 1)
            {
                nakedSingle(row, i);
            }
        }
    }
    
    private void nakedSingleColumn()
    {
        for (int i = 0; i < size; i++)
        {
            nakedSingleColumn(i);
        }
    }
    
    private void nakedSingleColumn(int column)
    {
        ArrayList<Integer>[] columnPossibleValues = 
                getColumnPossibleValues(column);
        for (int i = 0; i < columnPossibleValues.length; i++)
        {
            if (columnPossibleValues[i].size() == 1)
            {
                nakedSingle(i, column);
            }
        }
    }
    
    private void nakedSingle(int row, int column)
    {
        int value = possibleValues[row][column].get(0);
        setCellValue(row, column, value);
    }
    
    private void nakedDouble()
    {
        nakedDoubleRow();
        nakedDoubleColumn();
    }
    
    private void nakedDoubleRow()
    {
        for (int i = 0; i < size; i++)
        {
            nakedDoubleRow(i);
        }
    }
    
    private void nakedDoubleRow(int row)
    {
        ArrayList<Integer>[] rowPossibleValues = getRowPossibleValues(row);
        ArrayList<Integer> column2PossibleIndexes = new ArrayList<Integer>();
        ArrayList<ArrayList<Integer>> column2PossibleValues = 
                new ArrayList<>();
        ArrayList<ArrayList<Integer>> uniquePossibleValues = new ArrayList<>();
        ArrayList<Integer> uniquePossibleValuesFrequency = 
                new ArrayList<Integer>();
        for (int i = 0; i < rowPossibleValues.length; i++)
        {
            if (rowPossibleValues[i].size() == 2)
            {
                column2PossibleIndexes.add(i);
                column2PossibleValues.add(rowPossibleValues[i]);
            }
        }
        if (column2PossibleIndexes.size() >= 2
                && column2PossibleValues.size() >= 2)
        {
            for (int i = 0; i < column2PossibleValues.size(); i++)
            {
                if (!uniquePossibleValues.contains(
                        column2PossibleValues.get(i)))
                {
                    uniquePossibleValues.add(column2PossibleValues.get(i));
                }
            }
            for (int i = 0; i < uniquePossibleValues.size(); i++)
            {
                uniquePossibleValuesFrequency.add(Collections.frequency(
                        column2PossibleValues, uniquePossibleValues.get(i)));
            }
            for (int i = 0; i < uniquePossibleValuesFrequency.size(); i++)
            {
                if (uniquePossibleValuesFrequency.get(i) == 2)
                {
                    ArrayList<Integer> doublePossibleValues = 
                            uniquePossibleValues.get(i);
                    ArrayList<Integer> doublePossibleIndexes = 
                            new ArrayList<Integer>();
                    for (int j = 0; j < column2PossibleValues.size(); j++)
                    {
                        if (column2PossibleValues.get(j).equals(
                                uniquePossibleValues.get(i)))
                        {
                            doublePossibleIndexes.add(
                                    column2PossibleIndexes.get(j));
                        }
                    }
                    nakedDoubleRow(row, doublePossibleValues, 
                            doublePossibleIndexes);
                }
            }
        }
    }
    
    private void nakedDoubleRow(int row, 
            ArrayList<Integer> doublePossibleValues, 
            ArrayList<Integer> doublePossibleIndexes)
    {
        for (int i = 0; i < size; i++)
        {
            if (!doublePossibleIndexes.contains(i))
            {
                possibleValues[row][i].removeAll(doublePossibleValues);
            }
        }
    }
    
    private void nakedDoubleColumn()
    {
        for (int i = 0; i < size; i++)
        {
            nakedDoubleColumn(i);
        }
    }
    
    private void nakedDoubleColumn(int column)
    {
        ArrayList<Integer>[] columnPossibleValues = 
                getColumnPossibleValues(column);
        ArrayList<Integer> row2PossibleIndexes = new ArrayList<Integer>();
        ArrayList<ArrayList<Integer>> row2PossibleValues = new ArrayList<>();
        ArrayList<ArrayList<Integer>> uniquePossibleValues = new ArrayList<>();
        ArrayList<Integer> uniquePossibleValuesFrequency = 
                new ArrayList<Integer>();
        for (int i = 0; i < columnPossibleValues.length; i++)
        {
            if (columnPossibleValues[i].size() == 2)
            {
                row2PossibleIndexes.add(i);
                row2PossibleValues.add(columnPossibleValues[i]);
            }
        }
        if (row2PossibleIndexes.size() >= 2 
                && row2PossibleValues.size() >= 2)
        {
            for (int i = 0; i < row2PossibleValues.size(); i++)
            {
                if (!uniquePossibleValues.contains(
                        row2PossibleValues.get(i)))
                {
                    uniquePossibleValues.add(row2PossibleValues.get(i));
                }
            }
            for (int i = 0; i < uniquePossibleValues.size(); i++)
            {
                uniquePossibleValuesFrequency.add(Collections.frequency(
                        row2PossibleValues, uniquePossibleValues.get(i)));
            }
            for (int i = 0; i < uniquePossibleValuesFrequency.size(); i++)
            {
                if (uniquePossibleValuesFrequency.get(i) == 2)
                {
                    ArrayList<Integer> doublePossibleValues = 
                            uniquePossibleValues.get(i);
                    ArrayList<Integer> doublePossibleIndexes = 
                            new ArrayList<Integer>();
                    for (int j = 0; j < row2PossibleValues.size(); j++)
                    {
                        if (row2PossibleValues.get(j).equals(
                                uniquePossibleValues.get(i)))
                        {
                            doublePossibleIndexes.add(
                                    row2PossibleIndexes.get(j));
                        }
                    }
                    nakedDoubleColumn(column, doublePossibleValues,
                            doublePossibleIndexes);
                }
            }
        }
    }
    
    private void nakedDoubleColumn(int column,
            ArrayList<Integer> doublePossibleValues, 
            ArrayList<Integer> doublePossibleIndexes)
    {
        for (int i = 0; i < size; i++)
        {
            if (!doublePossibleIndexes.contains(i))
            {
                possibleValues[i][column].removeAll(doublePossibleValues);
            }
        }
    }
    
    private void hiddenSingle()
    {
        hiddenSingleRow();
        hiddenSingleColumn();
    }
    
    private void hiddenSingleRow()
    {
        for (int i = 0; i < size; i++)
        {
            hiddenSingleRow(i);
        }
    }
    
    private void hiddenSingleRow(int row)
    {
        ArrayList<Integer>[] rowPossibleValues = getRowPossibleValues(row);
        int[] possibleValuesFrequency = new int[size];
        ArrayList<Integer> columnValues = new ArrayList<Integer>();
        ArrayList<Integer> columnIndexes = new ArrayList<Integer>();
        for (ArrayList<Integer> rowPossibleValue : rowPossibleValues)
        {
            for (int i = 1; i <= possibleValuesFrequency.length; i++)
            {
                if (rowPossibleValue.contains(i))
                {
                    possibleValuesFrequency[i - 1]++;
                }
            }
        }
        for (int i = 0; i < possibleValuesFrequency.length; i++)
        {
            if (possibleValuesFrequency[i] == 1)
            {
                columnValues.add(i + 1);
            }
        }
        for (int i = 0; i < columnValues.size(); i++)
        {
            for (int j = 0; j < rowPossibleValues.length; j++)
            {
                if (rowPossibleValues[j].contains(columnValues.get(i)))
                {
                    columnIndexes.add(j);
                }
            }
        }
        for (int i = 0; i < columnValues.size(); i++)
        {
            if (rowPossibleValues[columnIndexes.get(i)].size() >= 2)
            {
                hiddenSingle(row, columnIndexes.get(i), columnValues.get(i));
            }
        }
    }
    
    private void hiddenSingleColumn()
    {
        for (int i = 0; i < size; i++)
        {
            hiddenSingleColumn(i);
        }
    }
    
    private void hiddenSingleColumn(int column)
    {
        ArrayList<Integer>[] columnPossibleValues 
                = getColumnPossibleValues(column);
        int[] possibleValuesFrequency = new int[size];
        ArrayList<Integer> rowValues = new ArrayList<Integer>();
        ArrayList<Integer> rowIndexes = new ArrayList<Integer>();
        for (ArrayList<Integer> columnPossibleValue : columnPossibleValues)
        {
            for (int i = 1; i <= possibleValuesFrequency.length; i++)
            {
                if (columnPossibleValue.contains(i))
                {
                    possibleValuesFrequency[i - 1]++;
                }
            }
        }
        for (int i = 0; i < possibleValuesFrequency.length; i++)
        {
            if (possibleValuesFrequency[i] == 1)
            {
                rowValues.add(i + 1);
            }
        }
        for (int i = 0; i < rowValues.size(); i++)
        {
            for (int j = 0; j < columnPossibleValues.length; j++)
            {
                if (columnPossibleValues[j].contains(rowValues.get(i)))
                {
                    rowIndexes.add(j);
                }
            }
        }
        for (int i = 0; i < rowValues.size(); i++)
        {
            if (columnPossibleValues[rowIndexes.get(i)].size() >= 2)
            {
                hiddenSingle(rowIndexes.get(i), column, rowValues.get(i));
            }
        }
    }
    
    private void hiddenSingle(int row, int column, int value)
    {
        setCellValue(row, column, value);
    }
    
    private void setCellValue(int row, int column, int value)
    {
        grid.solverSetCellValue(row, column, value);
        removePossibleValues(row, column, value);
    }
    
    private void removePossibleValues(int row, int column, int value)
    {
        possibleValues[row][column].clear();
        for (int i = 0; i < size; i++)
        {
            if (possibleValues[i][column].contains(value))
            {
                possibleValues[i][column].remove(
                        possibleValues[i][column].indexOf(value));
            }
        }
        for (int i = 0; i < size; i++)
        {
            if (possibleValues[row][i].contains(value))
            {
                possibleValues[row][i].remove(
                        possibleValues[row][i].indexOf(value));
            }
        }
    }
    
    private void removeImpossibleValuesCage(Cage cage, 
            ArrayList<Integer> values)
    {
        for (int i = 0; i < cage.getSize(); i++)
        {
            removeImpossibleValuesCell(cage.getCells().get(i).getRow(), 
                cage.getCells().get(i).getColumn(), values);
        }
    }
    
    private void removeImpossibleValuesCell(int row, int column, 
            ArrayList<Integer> values)
    {
        possibleValues[row][column].retainAll(values);
    }
    
    private ArrayList<Integer> createRetainAllArray()
    {
        ArrayList<Integer> array = new ArrayList<Integer>();
        for (int i = 1; i <= size; i++)
        {
            array.add(i);
        }
        return array;
    }
    
    public ArrayList<ArrayList<Integer>> getGridArrayList()
    {
        ArrayList<ArrayList<Integer>> gridArrayList = new ArrayList<>();
        for (int i = 0; i < size; i++)
        {
            ArrayList<Integer> gridArrayListRow = new ArrayList<Integer>();
            for (int j = 0; j < size; j++)
            {
                gridArrayListRow.add(grid.getCellValue(i, j));
            }
            gridArrayList.add(gridArrayListRow);
        }
        return gridArrayList;
    }
    
    public Grid getGrid()
    {
        return grid;
    }
    
    public Grid getSolution()
    {
        return solution;
    }
    
    private void printGrid()
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
    
    private void printPossibleValues()
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