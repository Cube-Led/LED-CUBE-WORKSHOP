package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;

import Workshop.Display;
import Workshop.Instruction;
import Workshop.UserPolling;


/**
 * Graphical implementation of the display
 * @author Clement
 *
 */
public class GUIDisplay extends JFrame implements Display, ActionListener{

	private UserPolling polling;
	private JButton bt_save;
	private JButton bt_record;
	private JList list_ReadOnlyInstructions;
	private JList list_instructionsList;
	private GuiChoiceAsker choiceComponent;
	
	public static final Dimension DEFAULT_DIMENSION = new Dimension(375,175);
	
	public GUIDisplay(GuiChoiceAsker choice)
	{
		this.choiceComponent = choice;
		this.setBackground(Color.white);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Worshop");
		this.setVisible(true);
		this.setSize(350, 175);
		
		/*JPanel pan = new JPanel();
		pan.setLayout(new FlowLayout());
		this.setContentPane(pan);
		this.setResizable(true);
		this.configureComponents();*/
		
		this.setContentPane(new ViewMainMenu(this));
	}
	private void configureComponents()
	{
		this.bt_save = new JButton("Save");
		this.bt_save.addActionListener(this);
		
		this.bt_record = new JButton("Record Instructions");
		this.bt_record.addActionListener(this);
		
		list_ReadOnlyInstructions=new JList();
		list_instructionsList= new JList();
		
		this.getContentPane().add(bt_save);
		this.getContentPane().add(bt_record);
		this.getContentPane().add(list_ReadOnlyInstructions);
		this.getContentPane().add(list_instructionsList);
		this.getContentPane().validate();
	}
	public void setUserPolling(UserPolling poll)
	{
		this.polling = poll;
	}
	
	@Override
	public void displayChoiceOfInstruction(Instruction[] inst) {
		if(this.getContentPane() instanceof ViewCreateProgram)
		{
			((ViewCreateProgram)this.getContentPane()).displayChoiceOfInstruction(inst);
		}
	}

	@Override
	public void println(String str) {
		JOptionPane.showMessageDialog(this.getContentPane(), str);
	}

	private void displayMessageInMsgBox(String str)
	{
		JOptionPane.showMessageDialog(this.getContentPane(), str);
	}
	
	@Override
	public void print(String str) {}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() instanceof JButton && ((JButton)arg0.getSource()).equals(this.bt_save))
		{
			displayMessageInMsgBox("Save");
		this.polling.writeSavedInstructionsInSavefile();
		}
		else if(arg0.getSource() instanceof JButton && ((JButton)arg0.getSource()).equals(this.bt_record))
			{
				this.bt_record.setText("Recording ...");
				this.polling.recordInstructions();
				this.bt_record.setText("Record instructions");
				this.getContentPane().validate();
			}
	}
	@Override
	public void displayBuffer(Instruction[] inst, int countInstructions) {

		if(this.getContentPane() instanceof ViewCreateProgram)
		{
			((ViewCreateProgram)this.getContentPane()).displayBuffer(inst, countInstructions);
		}

		}
	

	public UserPolling getPolling() {
		return polling;
	}

	public GuiChoiceAsker getChoice() {
		return choiceComponent;
	}
	@Override
	public void displayAskingOfAnArgument(String str) {
		if(this.getContentPane() instanceof ViewCreateProgram)
		{
			((ViewCreateProgram)this.getContentPane()).askArgument(str);
		}
	}
}