package model;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author michaeladrian39
 */
public class Cage
{
    
    private final int cageID;
    private final String objectives;
    private final int targetNumber;
    private final char operator;
    private final ArrayList<Cell> cells;
    
    public Cage(int cageID, String objectives)
    {
        this.cageID = cageID;
        if (isCageObjectiveValid(objectives))
        {
            this.objectives = objectives;
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Invalid cage objectives.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            throw new IllegalStateException("Invalid cage objectives.");
        }
        this.targetNumber = generateTargetNumber(objectives);
        this.operator = generateOperator(objectives);
        cells = new ArrayList<>();
    }
    
    private boolean isCageObjectiveValid(String cageObjective)
    {
        return cageObjective.matches("\\d+[*+-/=]");
    }
    
    private int generateTargetNumber(String objectives)
    {
        return Integer.parseInt(objectives.substring(0, 
                objectives.length() - 1));
    }
    
    private char generateOperator(String objectives)
    {
        return objectives.charAt(objectives.length() - 1);
    }
    
    public void addCell(Cell c)
    {
        cells.add(c);
    }
    
    public int getTargetNumber()
    {
        return targetNumber;
    }
    
    public char getOperator()
    {
        return operator;
    }
    
    public ArrayList<Cell> getCells()
    {
        return cells;
    }
    
    public int getSize()
    {
        return cells.size();
    }
             
}
