package view;

import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/**
 *
 * @author michaeladrian39
 */
public class GeneticParameters extends JFrame {

    private final GUI gui;
    private final JLabel labelGenerations;
    private final JLabel labelPopulation;
    private final JLabel labelElitism;
    private final JLabel labelCrossover;
    private final JLabel labelMutation;
    private final JTextField textFieldGenerations;
    private final JTextField textFieldPopulation;
    private final JTextField textFieldElitism;
    private final JTextField textFieldCrossover;
    private final JTextField textFieldMutation;
    private final JButton buttonOK;
    private final JButton buttonCancel;

    /**
     * Creates new form Test
     */
    public GeneticParameters(GUI gui)
    {
        this.gui = gui;
        labelGenerations = new JLabel();
        labelPopulation = new JLabel();
        labelElitism = new JLabel();
        labelCrossover = new JLabel();
        labelMutation = new JLabel();
        textFieldGenerations = new JTextField();
        textFieldPopulation = new JTextField();
        textFieldElitism = new JTextField();
        textFieldCrossover = new JTextField();
        textFieldMutation = new JTextField();
        buttonOK = new JButton();
        buttonCancel = new JButton();
        initComponents();
        this.setVisible(true);
    }

    private void initComponents()
    {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        labelGenerations.setText("Generations");
        labelPopulation.setText("Population Size");
        labelElitism.setText("Elitism Rate");
        labelCrossover.setText("Crossover Rate");
        labelMutation.setText("Mutation Rate");
        buttonOK.setText("OK");
        buttonCancel.setText("Cancel");
        initGUI();
        initActionListeners();
    }
    
    private void initActionListeners()
    {   
        this.buttonOK.addActionListener(this::buttonOKActionPerformed);
        this.buttonCancel.addActionListener(this::buttonCancelActionPerformed);
    }

    private void initGUI()
    {
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(
                                        GroupLayout.Alignment.LEADING)
                                        .addComponent(labelGenerations)
                                        .addComponent(labelPopulation)
                                        .addComponent(labelElitism)
                                        .addComponent(labelCrossover)
                                        .addComponent(labelMutation)
                                        .addComponent(buttonOK))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(
                                        GroupLayout.Alignment.LEADING)
                                        .addComponent(textFieldGenerations, 
                                                GroupLayout.PREFERRED_SIZE, 
                                                100, 
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textFieldPopulation,
                                                GroupLayout.PREFERRED_SIZE, 
                                                100, 
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textFieldElitism,
                                                GroupLayout.PREFERRED_SIZE, 
                                                100, 
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textFieldCrossover,  
                                                GroupLayout.PREFERRED_SIZE, 
                                                100, 
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textFieldMutation, 
                                                 GroupLayout.PREFERRED_SIZE, 
                                                100, 
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(buttonCancel))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE)));
        layout.linkSize(SwingConstants.HORIZONTAL, buttonOK, buttonCancel);
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(
                                        GroupLayout.Alignment.BASELINE)
                                        .addComponent(textFieldGenerations,
                                                GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labelGenerations))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(
                                        GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelPopulation)
                                        .addComponent(textFieldPopulation,
                                                GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(
                                        GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelElitism)
                                        .addComponent(textFieldElitism,
                                                GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(
                                        GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelCrossover)
                                        .addComponent(textFieldCrossover,
                                                GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(
                                        GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelMutation)
                                        .addComponent(textFieldMutation,
                                                GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(
                                        GroupLayout.Alignment.BASELINE)
                                        .addComponent(buttonOK)
                                        .addComponent(buttonCancel))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE)));
        this.validate();
        this.revalidate();
        this.pack();
        this.setLocationRelativeTo(null);
    }
    
    private void buttonOKActionPerformed(ActionEvent evt)
    {
        try
        {
            Integer generations = Integer.parseInt(textFieldGenerations.getText());
            Integer population = Integer.parseInt(textFieldPopulation.getText());
            Double elitism = Double.parseDouble(textFieldElitism.getText());
            Double crossover = Double.parseDouble(textFieldCrossover.getText());
            Double mutation = Double.parseDouble(textFieldMutation.getText());
            gui.setGeneticAlgorithmParameters(generations, population, elitism, 
                crossover, mutation);
            destroyFrame();
        }
        catch (NumberFormatException nfe)
        {
            JOptionPane.showMessageDialog(null, "Invalid number format.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            throw new IllegalStateException("Invalid number format.");
        }
    }
    
    private void buttonCancelActionPerformed(ActionEvent evt)
    {
        destroyFrame();
    }
    
    private void destroyFrame()
    {
        this.getContentPane().removeAll();
        this.validate();
        this.revalidate();
        this.pack();
        this.setLocationRelativeTo(null);
        this.dispose();
    }
    
}
