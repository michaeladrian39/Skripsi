package model;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author michaeladrian39
 */
public class Cage
{
    
    private final String objective;
    private final int targetNumber;
    private final char operator;
    private final ArrayList<Cell> cells;
    
    public Cage(String objective)
    {
        if (isCageObjectiveValid(objective))
        {
            this.objective = objective;
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Invalid cage objectives.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            throw new IllegalStateException("Invalid cage objectives.");
        }
        this.targetNumber = generateTargetNumber(this.objective);
        this.operator = generateOperator(this.objective);
        this.cells = new ArrayList<>();
    }
    
    private boolean isCageObjectiveValid(String cageObjective)
    {
        return cageObjective.matches("\\d+[*+-/=]");
    }
    
    private int generateTargetNumber(String objective)
    {
        return Integer.parseInt(objective.substring(0, 
                objective.length() - 1));
    }
    
    private char generateOperator(String objective)
    {
        return objective.charAt(objective.length() - 1);
    }
    
    public void addCell(Cell c)
    {
        cells.add(c);
    }
    
    public boolean isCageContainsNull()
    {
        for (int i = 0; i < cells.size(); i++)
        {
            if (cells.get(i).getValue() == null)
            {
                return true;
            }
        }
        return false;
    }
    
    public Boolean isCageValuesValid()
    {
        if (countValue() == null)
        {
            return null;
        }
        else
        {
            return (countValue() == getTargetNumber());
        }
    }
    
    private Integer countValue()
    {
        Integer value = null;
        if (isCageContainsNull() == true)
        {
            value = null;
        }
        else
        {
            switch (getOperator())
            {
                case '+':
                    value = 0;
                    for (int i = 0; i < cells.size(); i++)
                    {
                        value += cells.get(i).getValue();
                    }
                    break;
                case '-':
                    if (cells.get(0).getValue() > cells.get(1).getValue())
                    {
                        value = cells.get(0).getValue() 
                                - cells.get(1).getValue();
                    }
                    else if (cells.get(1).getValue() > cells.get(0).getValue())
                    {
                        value = cells.get(1).getValue() 
                                - cells.get(0).getValue();
                    }
                    else
                    {
                        value = 0;
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
                        if (cells.get(0).getValue() 
                                % cells.get(1).getValue() == 0)
                        {
                            value = cells.get(0).getValue() 
                                    / cells.get(1).getValue();
                        }
                        else
                        {
                            value = 0;
                        }
                    }
                    else if(cells.get(1).getValue() > cells.get(0).getValue())
                    {
                        if (cells.get(1).getValue() 
                                % cells.get(0).getValue() == 0)
                        {
                            value = cells.get(1).getValue() 
                                    / cells.get(0).getValue();
                        }
                        else
                        {
                            value = 0;
                        }
                    }
                    else
                    {
                        value = 0;
                    }
                    break;
                case '=':
                    value = cells.get(0).getValue();
                    break;
                default :
                    value = null;
            }
        }
        return value;
    }
    
    public String getObjective()
    {
        return objective;
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