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
            try (Scanner sc = new Scanner(new File("test9x9.txt")))
            {
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
                    JOptionPane.showMessageDialog(null, "Invalid puzzle file.", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    throw new IllegalStateException("Invalid puzzle file.");
                }
            }
            catch (FileNotFoundException fnfe)
            {
                JOptionPane.showMessageDialog(null, "Puzzle file not found.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                throw new FileNotFoundException("Puzzle file not found.");
            }
        }
        catch (NoSuchElementException nsee)
        {
            JOptionPane.showMessageDialog(null, "Invalid puzzle file.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            throw new IllegalStateException("Invalid puzzle file.");
        }
        Controller c = new Controller(size, numberOfCages, cageCells, 
                cageObjectives);
        c.solveHybridGenetic();
    }
    
}
