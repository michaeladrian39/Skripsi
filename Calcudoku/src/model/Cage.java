package model;

import java.util.ArrayList;

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
    private ArrayList<Cell> cells;
    
    public Cage(int cageID, String objectives)
    {
        this.cageID = cageID;
        this.objectives = objectives;
        this.targetNumber = generateTargetNumber(objectives);
        this.operator = generateOperator(objectives);
        cells = new ArrayList<Cell>();
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
    
    private void addCell(Cell c)
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
             
}
