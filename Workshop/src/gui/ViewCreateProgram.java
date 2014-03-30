package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import Workshop.Instruction;
import Workshop.Tools;

public class ViewCreateProgram extends View implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JList list_instructionsList;
	private JComboBox cb_ReadOnlyInstructions;
	private Box pan_enterArguments;
	
	private JButton bt_loadInstructions;
	private JButton bt_saveOneInstruction;
	private JButton bt_loadInstructionsOnCube;
	private JButton bt_retourMenu;
	
	private final String loadInstructionsIdentifier = "bt_loadInstructions";
	private final String loadInstructionOnCubeIdentifier = "bt_loadInstructionsOnCube";
	private final String saveOneInstructionIdentifier = "bt_saveOneInstruction";
	private final String retourMenuIdentifier = "bt_retourMenu";
	
	private final int LEFTPAN_WIDTH; 
	private final int BUTTON_WIDTH = 225;
	private final int BUTTON_HEIGHT = 35;
	private final int CENTER_LEFTPAN;
	
	public ViewCreateProgram(GUIDisplay motherFrame)
	{
		super(motherFrame);
		this.motherFrame = motherFrame;
		this.motherFrame.setSize(1000, 600);
		this.LEFTPAN_WIDTH = this.motherFrame.getWidth() /2; 
		this.CENTER_LEFTPAN = (this.LEFTPAN_WIDTH /2) - ((int)(this.BUTTON_WIDTH / 2));
		init();
		this.motherFrame.setContentPane(this);
		this.updateUI();
		this.setVisible(true);
	}
	
	public void init()
	{
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		Box right = new Box(BoxLayout.PAGE_AXIS);
		JPanel left = new JPanel();
		
		this.setLayout(null);
		left.setLayout(null);
		
		/* ------------------- Coté Droit ------------------- */
		
		JScrollPane scrollPane = new JScrollPane();
		list_instructionsList= new JList();
		scrollPane.setViewportView(list_instructionsList);
		right.add(scrollPane);
		
		
		/* ------------------- Coté Gauche ------------------- */
		
		/* ---------------- Haut ---------------- */
		cb_ReadOnlyInstructions=new JComboBox();
		cb_ReadOnlyInstructions.setMaximumSize(new Dimension(motherFrame.getSize().width/2 -10 ,26));
		cb_ReadOnlyInstructions.addActionListener(this);
		cb_ReadOnlyInstructions.setBounds(10 , 10, this.LEFTPAN_WIDTH -20, this.BUTTON_HEIGHT);
		left.add(cb_ReadOnlyInstructions);
		
		
		bt_loadInstructions = new JButton("Charger les instructions");
		bt_loadInstructions.addActionListener(this);
		bt_loadInstructions.setName(this.loadInstructionsIdentifier);
		bt_loadInstructions.setBounds(this.CENTER_LEFTPAN , 50, this.BUTTON_WIDTH, this.BUTTON_HEIGHT);
		left.add(bt_loadInstructions);
		
		/* ---------------- Milieu ---------------- */
		
		this.pan_enterArguments = new Box(BoxLayout.PAGE_AXIS);
		this.pan_enterArguments.setBounds(10, 200, this.LEFTPAN_WIDTH -20, 230);
		left.add(this.pan_enterArguments);
		
		/* ---------------- Bas ---------------- */
		
		bt_saveOneInstruction = new JButton("Enregistrer cette instruction");
		bt_saveOneInstruction.addActionListener(this);
		bt_saveOneInstruction.setName(this.saveOneInstructionIdentifier);
		bt_saveOneInstruction.setBounds(this.CENTER_LEFTPAN , 435, this.BUTTON_WIDTH, this.BUTTON_HEIGHT);
		left.add(bt_saveOneInstruction);

		bt_loadInstructionsOnCube = new JButton("Enregistrer l'animation sur un fichier");
		bt_loadInstructionsOnCube.addActionListener(this);
		bt_loadInstructionsOnCube.setName(this.loadInstructionOnCubeIdentifier);
		bt_loadInstructionsOnCube.setBounds(this.CENTER_LEFTPAN , 475, this.BUTTON_WIDTH, this.BUTTON_HEIGHT);
		left.add(bt_loadInstructionsOnCube);
		
		bt_retourMenu = new JButton("Retour au menu principal");
		bt_retourMenu.addActionListener(this);
		bt_retourMenu.setName(this.retourMenuIdentifier);
		bt_retourMenu.setBounds(this.CENTER_LEFTPAN , 515, this.BUTTON_WIDTH, this.BUTTON_HEIGHT);
		left.add(bt_retourMenu);		

		left.setBorder(BorderFactory.createLineBorder(Color.black));
		right.setBorder(BorderFactory.createLineBorder(Color.black));
		
		
		/* ------------------- Disposition des Box et JPanel Gauche et Droite ------------------- */
		
		left.setBounds(0, 0, this.LEFTPAN_WIDTH, this.motherFrame.getHeight());
		right.setBounds(this.motherFrame.getWidth() /2 +1, 0, 
						this.motherFrame.getWidth() /2, this.motherFrame.getHeight());
		
		this.add(left);
		this.add(right);	

		motherFrame.getPolling().requestDisplayOfPrimitiveInstructions();
	}
	
	public void displayBuffer(Instruction[] inst, int countInstructions) {
		this.list_instructionsList.setListData(inst);
		this.validate();
	}
	
	public void displayChoiceOfInstruction(Instruction[] inst) {
		for(int i=0; i<inst.length; i++)
			this.cb_ReadOnlyInstructions.addItem(inst[i]);
		this.validate();
	}

	private void recomposeInstruction()
	{
		Instruction current = (Instruction) this.cb_ReadOnlyInstructions.getSelectedItem();
		List<Short> args = new ArrayList<Short>();
		
		int j=1;
		for(int i =0; i < current.getNbArgs(); i++)
		{
			Long tempArg = (Long.valueOf(((JTextField)(pan_enterArguments.getComponent(j))).getText()));
			
			args.addAll(Tools.transformLongToShort(tempArg));

			j=j+2;
		}
		this.motherFrame.getPolling().saveOneInstruction(current.getCodeOp(), current.getDescription(), current.getNbArgs(),
														current.getDescriptionArguments(), args);
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
			else if(((JButton)e.getSource()).getName().equals(this.loadInstructionOnCubeIdentifier))
			{
				JFileChooser saveFile = new JFileChooser();
				saveFile.showOpenDialog(this);
				File saveInFile = saveFile.getSelectedFile();
				this.motherFrame.getPolling().writeSavedInstructionsInSavefile(saveInFile);
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
		this.updateUI();
	}
	
}
