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
        this.targetNumber = generateTargetNumber(this.objectives);
        this.operator = generateOperator(this.objectives);
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
    
    public boolean isCageContentsValid()
    {
        return (countValue() == getTargetNumber());
    }
    
    private int countValue()
    {
        int value = 0;
        switch (getOperator())
        {
            case '+':
                for (int i = 0; i < cells.size(); i++)
                {
                    value += cells.get(i).getValue();
                }
                break;
            case '-':
                if (cells.get(0).getValue() > cells.get(1).getValue())
                {
                    value = cells.get(0).getValue() - cells.get(1).getValue();
                }
                else
                {
                    value = cells.get(1).getValue() - cells.get(0).getValue();
                }
                break;
            case '*':
                value = 1;
                for (int i = 0; i < cells.size(); i++)
                {
                    value *= cells.get(i).getValue();
                }
                break;
            case '/':
                if (cells.get(0).getValue() > cells.get(1).getValue())
                {
                    value = cells.get(0).getValue() / cells.get(1).getValue();
                }
                else
                {
                    value = cells.get(1).getValue() / cells.get(0).getValue();
                }
                break;
            case '=':
                value = cells.get(0).getValue();
        }
        return value;
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
