import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class GUIDisplay extends JFrame implements Display, ActionListener{

	private UserPolling polling;
	private JButton bt_save;
	private JButton bt_record;
	private JList cb_ReadOnlyInstructions;
	private JList cb_instructionsList;
	
	public GUIDisplay()
	{
		this.setBackground(Color.white);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setSize(600, 400);
		/*JPanel pan = new JPanel();
		pan.setLayout(new FlowLayout());
		this.setContentPane(pan);
		this.setResizable(true);
		this.configureComponents();*/
		
		this.setContentPane(new GUIMainMenu(this));
		
	}
	private void configureComponents()
	{
		this.bt_save = new JButton("Save");
		this.bt_save.addActionListener(this);
		
		this.bt_record = new JButton("Record Instructions");
		this.bt_record.addActionListener(this);
		
		cb_ReadOnlyInstructions=new JList();
		cb_instructionsList= new JList();
		
		this.getContentPane().add(bt_save);
		this.getContentPane().add(bt_record);
		this.getContentPane().add(cb_ReadOnlyInstructions);
		this.getContentPane().add(cb_instructionsList);
		this.getContentPane().validate();
	}
	public void setUserPolling(UserPolling poll)
	{
		this.polling = poll;
	}
	
	@Override
	public void displayChoiceOfInstruction(Instruction[] inst) {
		//this.remove(cb_ReadOnlyInstructions);
		String str[] = new String[inst.length];
		
		for(int i=0; i<inst.length; i++)
			str[i] = inst[i].toString();
		
		//this.cb_ReadOnlyInstructions = new JList(str);
		//this.add(cb_ReadOnlyInstructions);
		this.cb_ReadOnlyInstructions.setListData(str);
		this.getContentPane().validate();
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

		String str[] = new String[countInstructions+1];
		
		for(int i=0; i<=countInstructions; i++)
			str[i] = inst[i].toString();
		
		//this.cb_instructionsList = new JList(str);
		this.cb_instructionsList.setListData(str);
		this.getContentPane().validate();

		}
}