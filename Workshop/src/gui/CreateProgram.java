package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;

import Workshop.Instruction;

public class CreateProgram extends JPanel implements ActionListener{

	private final GUIDisplay motherFrame;
	private JList list_instructionsList;
	private JList list_ReadOnlyInstructions;
	private JButton bt_start;
	private JLabel tx_argument;
	private JTextField tx_choix;
	
	public CreateProgram(GUIDisplay motherFrame)
	{
		this.motherFrame = motherFrame;
		this.motherFrame.setSize(600, 600);
		init();
		this.updateUI();
	}
	
	private void init()
	{
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		Box left = new Box(BoxLayout.PAGE_AXIS);
		Box right = new Box(BoxLayout.PAGE_AXIS);
		
		list_instructionsList= new JList();
		list_ReadOnlyInstructions=new JList();
		tx_argument = new JLabel("Choix");
		tx_choix = new JTextField();
		tx_choix.addActionListener(motherFrame.getChoice());
		
		right.add(list_instructionsList);
		//list_instructionsList.add(new Scrollbar());
		right.add(tx_argument);
		
		this.bt_start = new JButton("Start recording");
		this.bt_start.addActionListener(this);
		
		right.add(tx_choix);
		left.add(list_ReadOnlyInstructions);
		left.add(bt_start);
		
		
		this.add(left);
		this.add(right);
		
		
	}
	
	public void displayBuffer(Instruction[] inst, int countInstructions) {

		String str[] = new String[countInstructions+1];
		
		for(int i=0; i<=countInstructions; i++)
			str[i] = inst[i].toString();
		
		this.list_instructionsList.setListData(str);
		this.validate();

		}
	
	public void displayChoiceOfInstruction(Instruction[] inst) {
		
		String str[] = new String[inst.length];
		
		for(int i=0; i<inst.length; i++)
			str[i] = inst[i].toString();
		this.list_ReadOnlyInstructions.setListData(str);
		this.validate();
	}

	public void askArgument(String str) {
		this.tx_argument.setText(str);
		this.updateUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() instanceof JButton)
		{
			this.motherFrame.getPolling().recordInstructions();
		}
		else
			System.out.println("helpppppp");
		
	}
	
	
}
