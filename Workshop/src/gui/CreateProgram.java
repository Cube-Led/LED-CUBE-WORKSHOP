package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Workshop.Instruction;

public class CreateProgram extends JPanel implements ActionListener{

	private final GUIDisplay motherFrame;
	private JList list_instructionsList;
	private JComboBox cb_ReadOnlyInstructions;
	private JButton bt_loadInstructions;
	private JButton bt_saveOneInstruction;
	private JLabel tx_argument;
	
	private final String saveOneInstructionIdentifier = "bt_saveInst";
	private final String loadInstructionsIdentifier = "bt_loadAll";
	
	public CreateProgram(GUIDisplay motherFrame)
	{
		this.motherFrame = motherFrame;
		this.motherFrame.setSize(600, 200);
		init();

		this.motherFrame.getPolling().requestDisplayOfPrimitiveInstructions();

		this.updateUI();
	}
	
	private void init()
	{
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		Box left = new Box(BoxLayout.PAGE_AXIS);
		Box right = new Box(BoxLayout.PAGE_AXIS);
		
		list_instructionsList= new JList();
		right.add(list_instructionsList);
		
		tx_argument = new JLabel("Choix");
		right.add(tx_argument);
		

		cb_ReadOnlyInstructions=new JComboBox();
		left.add(cb_ReadOnlyInstructions);
		
		bt_loadInstructions = new JButton("Charger les annimations");
		bt_loadInstructions.addActionListener(this);
		bt_loadInstructions.setName(this.loadInstructionsIdentifier);
		left.add(bt_loadInstructions);
		
		bt_saveOneInstruction = new JButton("Enregistrer cette instruction");
		bt_saveOneInstruction.addActionListener(this);
		bt_saveOneInstruction.setEnabled(false);
		left.add(bt_saveOneInstruction);
		
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
			this.cb_ReadOnlyInstructions.addItem(inst[i].toString());
			/*str[i] = inst[i].toString();*/
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
			if(((JButton)e.getSource()).getText().equals("Charger les annimations"))
			{
				this.motherFrame.getPolling().requestDisplayOfPrimitiveInstructions();
				this.bt_saveOneInstruction.setEnabled(true);
			}
			else
			{
				
			}
		}
		else
		{
			
		}
	}
	
	
}
