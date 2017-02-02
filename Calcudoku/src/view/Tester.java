package view;

import controller.Controller;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author michaeladrian39
 */
public class Tester
{
    
    public static void main(String[] args) throws FileNotFoundException
    {
        int size;
        int[][] cageCells;
        int numberOfCages;
        String[] cageObjectives;
        try
        {
            Scanner sc = new Scanner(new File("test4x4.txt"));
            size = sc.nextInt();
            cageCells = new int[size][size];
            numberOfCages = sc.nextInt();
            cageObjectives = new String[numberOfCages];
            for (int i = 0; i < size; i++)
            {
                for (int j = 0; j < size; j++)
                {
                    cageCells[i][j] = sc.nextInt();
                }
            }
            for (int i = 0; i < numberOfCages; i++)
            {
                cageObjectives[i] = sc.next();
            }
            if (sc.hasNext())
            {
                JOptionPane.showMessageDialog(null, "Invalid puzzle file.", "Error", 
                    JOptionPane.ERROR_MESSAGE);
                throw new IllegalStateException("Invalid puzzle file.");
            }
            sc.close();
        }
        catch (NoSuchElementException nsee)
        {
            JOptionPane.showMessageDialog(null, "Invalid puzzle file.", "Error", 
                    JOptionPane.ERROR_MESSAGE);
            throw new IllegalStateException("Invalid puzzle file.");
        }
        Controller c = new Controller(size, numberOfCages, cageCells, 
                cageObjectives);
        //  4 2 3 1
        //  3 4 1 2
        //  1 3 2 4
        //  2 1 4 3
    }
    
}
