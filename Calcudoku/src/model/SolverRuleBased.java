package model;

import java.util.ArrayList;

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
        char cageOperator;
        int cageTargetNumber;
        ArrayList<Integer> array = new ArrayList();
        for (int i = 0; i < grid.getCages().length; i++)
        {
            cageSize = grid.getCages()[i].getSize();
            cageOperator = grid.getCages()[i].getOperator();
            cageTargetNumber = grid.getCages()[i].getTargetNumber();
            switch(cageSize)
            {
                case 2 :
                    switch(size)
                    {
                        case 3 :
                            switch(cageOperator)
                            {
                                case '+' :
                                    switch(cageTargetNumber)
                                    {
                                        case 3 :
                                            array.add(1);
                                            array.add(2);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        case 4 :  
                                            array.add(1);
                                            array.add(3);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        case 5 :
                                            array.add(2);
                                            array.add(3);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        default :
                                            array = createRetainAllArray();
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                    }
                                case '-' :
                                    switch(cageTargetNumber)
                                    {
                                        case 2 :
                                            array.add(1);
                                            array.add(3);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        default :
                                            array = createRetainAllArray();
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                    }
                                case '*' :
                                   switch(cageTargetNumber)
                                    {
                                        case 2 :
                                            array.add(1);
                                            array.add(2);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        case 3 :
                                            array.add(1);
                                            array.add(3);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        case 6 :
                                            array.add(2);
                                            array.add(3);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        default :
                                            array = createRetainAllArray();
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                    }
                                case '/' :
                                    switch(cageTargetNumber)
                                    {
                                        case 2 :
                                            array.add(1);
                                            array.add(2);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        case 3 :
                                            array.add(1);
                                            array.add(3);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        default :
                                            array = createRetainAllArray();
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                    }
                                default :
                                    array = createRetainAllArray();
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                            }
                        case 4 :
                            switch(cageOperator)
                            {
                                case '+' :
                                    switch(cageTargetNumber)
                                    {
                                        case 3 :
                                            array.add(1);
                                            array.add(2);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        case 4 :  
                                            array.add(1);
                                            array.add(3);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        case 6 :
                                            array.add(2);
                                            array.add(4);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        case 7 :
                                            array.add(2);
                                            array.add(4);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        default :
                                            array = createRetainAllArray();
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                    }
                                case '-' :
                                    switch(cageTargetNumber)
                                    {
                                        case 3 :
                                            array.add(1);
                                            array.add(4);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        default :
                                            array = createRetainAllArray();
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                    }
                                case '*' :
                                   switch(cageTargetNumber)
                                    {
                                        case 2 :
                                            array.add(1);
                                            array.add(2);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        case 3 :
                                            array.add(1);
                                            array.add(3);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        case 4 :
                                            array.add(1);
                                            array.add(4);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        case 6 :
                                            array.add(2);
                                            array.add(3);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        case 8 :
                                            array.add(2);
                                            array.add(4);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        case 12 :
                                            array.add(3);
                                            array.add(4);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        default :
                                            array = createRetainAllArray();
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                    }
                                case '/' :
                                    switch(cageTargetNumber)
                                    {
                                        case 3 :
                                            array.add(1);
                                            array.add(3);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        case 4 :
                                            array.add(1);
                                            array.add(4);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        default :
                                            array = createRetainAllArray();
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                    }
                                default :
                                    array = createRetainAllArray();
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                            }
                        case 5 :
                            switch(cageOperator)
                            {
                                case '+' :
                                    switch(cageTargetNumber)
                                    {
                                        case 3 :
                                            array.add(1);
                                            array.add(2);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        case 4 :  
                                            array.add(1);
                                            array.add(3);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        case 8 :
                                            array.add(3);
                                            array.add(5);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        case 9 :
                                            array.add(4);
                                            array.add(5);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        default :
                                            array = createRetainAllArray();
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                    }
                                case '-' :
                                    switch(cageTargetNumber)
                                    {
                                        case 4 :
                                            array.add(1);
                                            array.add(5);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        default :
                                            array = createRetainAllArray();
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                    }
                                case '*' :
                                   switch(cageTargetNumber)
                                    {
                                        case 2 :
                                            array.add(1);
                                            array.add(2);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        case 3 :
                                            array.add(1);
                                            array.add(3);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        case 4 :
                                            array.add(1);
                                            array.add(4);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        case 5 :
                                            array.add(1);
                                            array.add(5);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        case 6 :
                                            array.add(2);
                                            array.add(3);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        case 8 :
                                            array.add(2);
                                            array.add(4);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        case 10 :
                                            array.add(2);
                                            array.add(5);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        case 12 :
                                            array.add(3);
                                            array.add(4);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        case 15 :
                                            array.add(3);
                                            array.add(5);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        case 20 :
                                            array.add(4);
                                            array.add(5);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        default :
                                            array = createRetainAllArray();
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                    }
                                case '/' :
                                    switch(cageTargetNumber)
                                    {
                                        case 3 :
                                            array.add(1);
                                            array.add(3);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        case 4 :
                                            array.add(1);
                                            array.add(4);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        case 5 :
                                            array.add(1);
                                            array.add(5);
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                        default :
                                            array = createRetainAllArray();
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                                    }
                                default :
                                    array = createRetainAllArray();
                                            removeImpossibleValuesCage(grid.getCages()[i], 
                                                    array);
                            }
                    }
                default :
                    array = createRetainAllArray();
                    removeImpossibleValuesCage(grid.getCages()[i], array);
            }
        }
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
