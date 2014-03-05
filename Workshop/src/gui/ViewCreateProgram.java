package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import Workshop.Application;
import Workshop.Instruction;

public class ViewCreateProgram extends View implements ActionListener{

	private JList list_instructionsList;
	private JComboBox cb_ReadOnlyInstructions;
	private JButton bt_loadInstructions;
	private JButton bt_saveOneInstruction;
	private JButton bt_retourMenu;
	private JButton bt_saveAll;
	private Box pan_enterArguments;
	
	private final String saveOneInstructionIdentifier = "bt_saveInst";
	private final String loadInstructionsIdentifier = "bt_loadAll";
	private final String retourMenuIdentifier = "bt_return";
	
	public ViewCreateProgram(GUIDisplay motherFrame)
	{
		super(motherFrame);
		this.motherFrame = motherFrame;
		this.motherFrame.setSize(800, 600);
		init();

		
		this.updateUI();
	}
	
	public void init()
	{
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		Box left = new Box(BoxLayout.PAGE_AXIS);
		Box right = new Box(BoxLayout.PAGE_AXIS);
		
		JScrollPane scrollPane = new JScrollPane();
		
		list_instructionsList= new JList();
		/*list_instructionsList.setMaximumSize(new Dimension(motherFrame.getSize().width/2 -50, motherFrame.getSize().height-50));
		scrollPane.setMaximumSize(new Dimension(motherFrame.getSize().width/2 -50, motherFrame.getSize().height-50));*/
		scrollPane.setViewportView(list_instructionsList);
		right.add(scrollPane);
		

		cb_ReadOnlyInstructions=new JComboBox();
		cb_ReadOnlyInstructions.setMaximumSize(new Dimension(motherFrame.getSize().width/2 -10 ,26));
		cb_ReadOnlyInstructions.setAlignmentX(CENTER_ALIGNMENT);
		cb_ReadOnlyInstructions.addActionListener(this);
		left.add(cb_ReadOnlyInstructions);
		
		
		bt_loadInstructions = new JButton("Charger les animations");
		bt_loadInstructions.addActionListener(this);
		bt_loadInstructions.setName(this.loadInstructionsIdentifier);
		
		bt_saveAll = new JButton("save");
		bt_saveAll.addActionListener(this);
		bt_saveAll.setName("save");
		left.add(bt_saveAll);
		

		bt_loadInstructions.setAlignmentX(CENTER_ALIGNMENT);
		left.add(bt_loadInstructions);
		
		bt_saveOneInstruction = new JButton("Enregistrer cette instruction");
		bt_saveOneInstruction.addActionListener(this);
		bt_saveOneInstruction.setEnabled(false);
		bt_saveOneInstruction.setName(this.saveOneInstructionIdentifier);
		bt_saveOneInstruction.setAlignmentX(CENTER_ALIGNMENT);
		left.add(bt_saveOneInstruction);
		
		bt_retourMenu = new JButton("Retour menu");
		bt_retourMenu.addActionListener(this);
		bt_retourMenu.setName(this.retourMenuIdentifier);
		right.add(bt_retourMenu);
		

		left.setBorder(BorderFactory.createLineBorder(Color.black));
		left.setMaximumSize(new Dimension(motherFrame.getSize().width/2, motherFrame.getSize().height));
		right.setBorder(BorderFactory.createLineBorder(Color.black));
		right.setMaximumSize(new Dimension(motherFrame.getSize().width/2 , motherFrame.getSize().height));
		
		this.pan_enterArguments = new Box(BoxLayout.PAGE_AXIS);
		left.add(this.pan_enterArguments);
		
		this.add(left);
		this.add(right);

		motherFrame.getPolling().requestDisplayOfPrimitiveInstructions();
	}
	
	public void displayBuffer(Instruction[] inst, int countInstructions) {

		this.list_instructionsList.setListData(inst);
		this.validate();

		}
	
	public void displayChoiceOfInstruction(Instruction[] inst) {
		
		String str[] = new String[inst.length];
		
		for(int i=0; i<inst.length; i++)
			this.cb_ReadOnlyInstructions.addItem(inst[i]);
			/*str[i] = inst[i].toString();*/
		this.validate();
	}

	public void askArgument(String str) {
		
		this.updateUI();
	}
	
	private void recomposeInstruction()
	{
		Instruction current = (Instruction) this.cb_ReadOnlyInstructions.getSelectedItem();
		short[] b = new short[Application.MAX_LENGTH_BUFFER - 1];
		
		int j=1;
		for(int i =0; i < current.getNbArgs(); i++)
		{
			int tempArg = (Integer.valueOf(((JTextField)(pan_enterArguments.getComponent(j))).getText()));
			b[i] = (short) tempArg;
			j=j+2;
		}
		this.motherFrame.getPolling().saveOneInstruction(current.getCodeOp(), current.getDescription(), current.getNbArgs(),
														current.getDescriptionArguments(), b);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() instanceof JButton)
		{
			if(((JButton)e.getSource()).getName().equals(this.loadInstructionsIdentifier))
			{
				this.motherFrame.getPolling().requestDisplayOfPrimitiveInstructions();
				this.bt_saveOneInstruction.setEnabled(true);
			}
			else if(((JButton)e.getSource()).getName().equals(this.saveOneInstructionIdentifier))
			{
				
				recomposeInstruction();
			}
			else if(((JButton)e.getSource()).getName().equals("save"))
			{
				this.motherFrame.getPolling().writeSavedInstructionsInSavefile();
			}
			else if(((JButton)e.getSource()).getName().equals(this.retourMenuIdentifier))
			{
				this.motherFrame.setContentPane(new ViewMainMenu(motherFrame));
			}
		}
		else if(e.getSource() instanceof JComboBox)
		{
			createFormForArguments();
		}
	}
	
	private void createFormForArguments()
	{
		Instruction current = (Instruction) this.cb_ReadOnlyInstructions.getSelectedItem();
		this.pan_enterArguments.removeAll();
		for(int i = 0; i< current.getNbArgs(); i++)
		{
			JLabel temp = new JLabel(current.getDescriptionArguments()[i]);
			
			
			JTextField temp2 = new JTextField();
			
			temp.setAlignmentX(CENTER_ALIGNMENT);
			
			temp2.setMaximumSize(new Dimension(motherFrame.getSize().width/2, 26));
			temp2.setName(current.getDescriptionArguments()[i]);
			temp2.setAlignmentX(CENTER_ALIGNMENT);
			
			this.pan_enterArguments.add(temp);
			this.pan_enterArguments.add(temp2);
		}
		
		System.out.println("hhhh");
		this.updateUI();
	}
	
	
}
