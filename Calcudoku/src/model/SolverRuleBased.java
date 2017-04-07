package model;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author michaeladrian39
 */
public class SolverRuleBased
{
    
    private final Grid grid;
    private final int size;
    ArrayList<Integer>[][] possibleValues;
    
    public SolverRuleBased(Grid grid)
    {
        this.grid = grid;
        this.size = grid.getSize();
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
        solve();
    }
    
    public void solve()
    {
        singleSquare();
        killerCombination();
        printGrid();
        printPossibleValues();
    }
    
    private ArrayList<Integer>[] getRowPossibleValues(int row)
    {
        ArrayList<Integer>[] array;
        array = new ArrayList[size];
        for (int i = 0; i < size; i++)
        {
            array[i] = possibleValues[row][i];
        }
        return array;
    }
    
    private ArrayList<Integer>[] getColumnPossibleValues(int column)
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
        for (int i = 0; i < grid.getCages().length; i++)
        {
            if (grid.getCages()[i].getSize() == 1)
            {
                for (int j = 0; j < grid.getCages()[i].getCells().size(); j++)
                {
                    setCellValue(grid.getCages()[i].getCells().get(j).getRow(), 
                            grid.getCages()[i].getCells().get(j).getColumn(), 
                            grid.getCages()[i].getTargetNumber());
                }
            }
        }
    }
    
    private void killerCombination()
    {
        int cageSize;
        ArrayList<Integer> array = new ArrayList();
        for (int i = 0; i < grid.getCages().length; i++)
        {
            cageSize = grid.getCages()[i].getSize();
            switch(cageSize)
            {
                case 2 :
                    killerCombinationCageSize2(grid.getCages()[i]);
                    break;
                default :
                    System.out.println("cage size not 2");
                    array = createRetainAllArray();
                    removeImpossibleValuesCage(grid.getCages()[i], array);
                    break;
            }
        }
    }
    
    private void killerCombinationCageSize2(Cage cage)
    {
        int gridSize = size;
        char cageOperator = cage.getOperator();
        int cageTargetNumber = cage.getTargetNumber();
        ArrayList<Integer> array = new ArrayList();
        switch(gridSize)
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
        ArrayList<Integer> array = new ArrayList();
        switch(cageOperator)
        {
            case '+' :
                switch(cageTargetNumber)
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
                switch(cageTargetNumber)
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
               switch(cageTargetNumber)
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
                switch(cageTargetNumber)
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
        ArrayList<Integer> array = new ArrayList();
        switch(cageOperator)
        {
            case '+' :
                switch(cageTargetNumber)
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
                switch(cageTargetNumber)
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
               switch(cageTargetNumber)
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
                switch(cageTargetNumber)
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
        ArrayList<Integer> array = new ArrayList();
        switch(cageOperator)
        {
            case '+' :
                switch(cageTargetNumber)
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
                switch(cageTargetNumber)
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
               switch(cageTargetNumber)
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
                switch(cageTargetNumber)
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
        ArrayList<Integer> array = new ArrayList();
        switch(cageOperator)
        {
            case '+' :
                switch(cageTargetNumber)
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
                switch(cageTargetNumber)
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
               switch(cageTargetNumber)
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
                switch(cageTargetNumber)
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
        ArrayList<Integer> array = new ArrayList();
        switch(cageOperator)
        {
            case '+' :
                switch(cageTargetNumber)
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
                switch(cageTargetNumber)
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
               switch(cageTargetNumber)
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
                switch(cageTargetNumber)
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
        ArrayList<Integer> array = new ArrayList();
        switch(cageOperator)
        {
            case '+' :
                switch(cageTargetNumber)
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
                switch(cageTargetNumber)
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
               switch(cageTargetNumber)
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
                switch(cageTargetNumber)
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
        ArrayList<Integer> array = new ArrayList();
        switch(cageOperator)
        {
            case '+' :
                switch(cageTargetNumber)
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
                switch(cageTargetNumber)
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
               switch(cageTargetNumber)
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
                switch(cageTargetNumber)
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
    
    private void setCellValue(int row, int column, int value)
    {
        grid.setCellValue(row, column, value);
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
    
    private void removeImpossibleValuesCage(Cage cage, ArrayList<Integer> array)
    {
        for (int i = 0; i < cage.getSize(); i++)
        {
            removeImpossibleValuesCell(cage.getCells().get(i).getRow(), 
                cage.getCells().get(i).getColumn(), array);
        }
    }
    
    private void removeImpossibleValuesCell(int row, int column, 
            ArrayList<Integer> array)
    {
        possibleValues[row][column].retainAll(array);
    }
    
    public ArrayList<Integer> createRetainAllArray()
    {
        ArrayList<Integer> array = new ArrayList();
        for (int i = 1; i <= size; i++)
        {
            array.add(i);
        }
        return array;
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
    
    public void printPossibleValues()
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
