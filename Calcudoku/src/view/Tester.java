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
            // 4
            size = sc.nextInt();
            cageCells = new int[size][size];
            // 9
            numberOfCages = sc.nextInt();
            cageObjectives = new String[numberOfCages];
            // 1 2 3 3
            // 1 4 4 5
            // 6 7 7 5
            // 8 8 9 9
            for (int i = 0; i < size; i++)
            {
                for (int j = 0; j < size; j++)
                {
                    cageCells[i][j] = sc.nextInt();
                }
            }
            // 7+
            // 2=
            // 2-
            // 3-
            // 2/
            // 1=
            // 6*
            // 3+
            // 7+
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
        
        c.setCellValue(0, 0, 4);
        c.setCellValue(0, 1, 2);
        c.setCellValue(0, 2, 3);
        c.setCellValue(0, 3, 1);
        
        c.setCellValue(1, 0, 3);
        c.setCellValue(1, 1, 4);
        c.setCellValue(1, 2, 1);
        c.setCellValue(1, 3, 2);
        
        c.setCellValue(2, 0, 1);
        c.setCellValue(2, 1, 3);
        c.setCellValue(2, 2, 2);
        c.setCellValue(2, 3, 4);
        
        c.setCellValue(3, 0, 2);
        c.setCellValue(3, 1, 1);
        c.setCellValue(3, 2, 4);
        c.setCellValue(3, 3, 3);
    }
    
}
