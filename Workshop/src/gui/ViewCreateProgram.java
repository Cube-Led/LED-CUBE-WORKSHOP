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

public class ViewCreateProgram extends View implements ActionListener{

	private JList list_instructionsList;
	private JComboBox cb_ReadOnlyInstructions;
	private JButton bt_loadInstructions;
	private JButton bt_saveOneInstruction;
	private JButton bt_retourMenu;
	private JLabel tx_argument;
	
	private final String saveOneInstructionIdentifier = "bt_saveInst";
	private final String loadInstructionsIdentifier = "bt_loadAll";
	private final String retourMenuIdentifier = "bt_return";
	
	public ViewCreateProgram(GUIDisplay motherFrame)
	{
		super(motherFrame);
		this.motherFrame = motherFrame;
		this.motherFrame.setSize(600, 200);
		init();

		this.motherFrame.getPolling().requestDisplayOfPrimitiveInstructions();
		
		this.updateUI();
	}
	
	public void init()
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
		bt_saveOneInstruction.setName(this.saveOneInstructionIdentifier);
		left.add(bt_saveOneInstruction);
		
		bt_retourMenu = new JButton("Retour menu");
		bt_retourMenu.addActionListener(this);
		bt_retourMenu.setName(this.retourMenuIdentifier);
		right.add(bt_retourMenu);
		
		this.add(left);
		this.add(right);

		left.setSize(motherFrame.getSize().width/2, motherFrame.getSize().height);
		right.setSize(motherFrame.getSize().width/2, motherFrame.getSize().height);
		
	}
	
	public void displayBuffer(Instruction[] inst, int countInstructions) {

		this.list_instructionsList.setListData(inst);
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
			System.out.println(((JButton)e.getSource()).getName());
			if(((JButton)e.getSource()).getName().equals(this.loadInstructionsIdentifier))
			{
				this.motherFrame.getPolling().requestDisplayOfPrimitiveInstructions();
				this.bt_saveOneInstruction.setEnabled(true);
			}
			else if(((JButton)e.getSource()).getName().equals(this.saveOneInstructionIdentifier))
			{
				byte[] b = new byte[1];
				String[] s = new String[1];
				s[0] = "Couche";
				b[0] = 0x4;
				this.motherFrame.getPolling().saveOneInstruction((byte)0x4, "Allumer toutes les leds" ,1,s, b);
			}
			else if(((JButton)e.getSource()).getName().equals(this.retourMenuIdentifier))
			{
				this.motherFrame.setContentPane(new ViewMainMenu(motherFrame));
			}
		}
		else
		{
			
		}
	}
	
	
}
