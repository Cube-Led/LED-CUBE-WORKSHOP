package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Workshop.Instruction;

public class ViewDynamicLED extends View implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private int numberLayer;
	
	private LedJPan pictureLED;
	
	private JList list_instructionsList;

	
	private JLabel lbl_currentLayer;
	private final String currentLayerText = "Vous êtes au niveau : ";
	
	private JButton bt_upLevel;
	private JButton bt_downLevel;
	private JButton bt_saveOneInstruction;
	private JButton bt_loadInstructions;
	private JButton bt_retourMenu;
	
	private final String upLevelIdentifier = "bt_upLevel";
	private final String downLevelIdentifier = "bt_downLevel";
	private final String saveOneInstructionIdentifier = "bt_saveInst";
	private final String loadInstructionsIdentifier = "bt_loadAll";
	private final String retourMenuIdentifier = "bt_return";
	
	private final int LEFTPAN_WIDTH = (this.motherFrame.getWidth() * 6) /11;
	private final int BUTTON_WIDTH = 225;
	private final int BUTTON_HEIGHT = 35;
	private final int CENTER_LEFTPAN = (this.LEFTPAN_WIDTH /2) - ((int)(this.BUTTON_WIDTH / 2));
	
	
	/* ------------------ Les LEDs ------------------ */
	
	private Led[] ledGrid;
	private static final float RATIO_LED = 0.04f;
	public int cube_size;
	
	public int getCube_size() {
		return cube_size;
	}

	public ViewDynamicLED(GUIDisplay motherFrame) {
		super(motherFrame);
		this.motherFrame = motherFrame;
		this.motherFrame.setSize(800, 700);
		initDisplay();
		this.updateUI();
	}
	
	public void initDisplay() {
		
		this.cube_size = 8;
		this.ledGrid = new Led[this.cube_size*this.cube_size];
		this.numberLayer = 1;
		
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		JPanel left = new JPanel();
		Box right = new Box(BoxLayout.PAGE_AXIS);
		
		this.setLayout(null);
		left.setLayout(null);
		
		/* ------------------- Coté Droit ------------------- */
		
		JScrollPane scrollPane = new JScrollPane();
		list_instructionsList= new JList();
		scrollPane.setViewportView(list_instructionsList);
		right.add(scrollPane);
		
		
		/* ------------------- Coté Gauche ------------------- */
		
		/* ---------------- Haut ---------------- */
		
		bt_upLevel = new JButton("Monter d'un niveau");
		bt_upLevel.addActionListener(this);
		bt_upLevel.setName(this.upLevelIdentifier);
		bt_upLevel.setBounds(this.CENTER_LEFTPAN , 0, this.BUTTON_WIDTH, this.BUTTON_HEIGHT);
		left.add(bt_upLevel, BorderLayout.NORTH);
		
		lbl_currentLayer = new JLabel("Vous êtes au niveau : ");
		lbl_currentLayer.setText(this.currentLayerText + this.numberLayer);
		lbl_currentLayer.setBounds(this.CENTER_LEFTPAN + this.BUTTON_WIDTH / 4, 35, this.BUTTON_WIDTH, 60);
		left.add(lbl_currentLayer);
		
		bt_downLevel = new JButton("Descendre d'un niveau");
		bt_downLevel.addActionListener(this);
		bt_downLevel.setName(this.downLevelIdentifier);
		bt_downLevel.setBounds(this.CENTER_LEFTPAN , 95, this.BUTTON_WIDTH, this.BUTTON_HEIGHT);
		bt_downLevel.setEnabled(false);
		left.add(bt_downLevel, BorderLayout.NORTH);
		
		
		/* ---------------- Milieu ---------------- */
		
		pictureLED = new LedJPan(this.cube_size, (int)(this.motherFrame.getWidth()*ViewDynamicLED.RATIO_LED), this.ledGrid);
		pictureLED.setBounds(0, 135, this.LEFTPAN_WIDTH, 420);
		left.add(pictureLED,BorderLayout.CENTER);
		
		
		/* ---------------- Bas ---------------- */
		
		bt_saveOneInstruction = new JButton("Enregistrer cette instruction");
		bt_saveOneInstruction.addActionListener(this);
		bt_saveOneInstruction.setName(this.saveOneInstructionIdentifier);
		bt_saveOneInstruction.setBounds(this.CENTER_LEFTPAN , 560, this.BUTTON_WIDTH, this.BUTTON_HEIGHT);
		left.add(bt_saveOneInstruction, BorderLayout.SOUTH);

		bt_loadInstructions = new JButton("Charger l'animation sur le cube");
		bt_loadInstructions.addActionListener(this);
		bt_loadInstructions.setName(this.loadInstructionsIdentifier);
		bt_loadInstructions.setBounds(this.CENTER_LEFTPAN , 595, this.BUTTON_WIDTH, this.BUTTON_HEIGHT);
		left.add(bt_loadInstructions, BorderLayout.SOUTH);
		
		bt_retourMenu = new JButton("Retour au menu principal");
		bt_retourMenu.addActionListener(this);
		bt_retourMenu.setName(this.retourMenuIdentifier);
		bt_retourMenu.setBounds(this.CENTER_LEFTPAN , 630, this.BUTTON_WIDTH, this.BUTTON_HEIGHT);
		left.add(bt_retourMenu, BorderLayout.SOUTH);
				
		
		/* ------------------- Disposition des Box Gauche et Droite ------------------- */
		
		left.setBounds(0, 0, this.LEFTPAN_WIDTH, this.motherFrame.getHeight());
		right.setBounds((this.motherFrame.getWidth() * 6) /11 +1, 0, 
						(this.motherFrame.getWidth() * 5)/11, this.motherFrame.getHeight());
		
		this.add(left);
		this.add(right);		
	}
	
	private void createInstruction()
	{
		double number = 0;
		for (int i = 0; i < this.cube_size * this.cube_size; i++){
			if (this.ledGrid[i].getState() == Led.ON)
				number += Math.pow(2, i);
		}
		/*Instruction current = (Instruction) this.cb_ReadOnlyInstructions.getSelectedItem();
		short[] b = new short[Application.MAX_LENGTH_BUFFER - 1];
		
		int j=1;
		for(int i =0; i < current.getNbArgs(); i++)
		{
			int tempArg = (Integer.valueOf(((JTextField)(pan_enterArguments.getComponent(j))).getText()));
			b[i] = (short) tempArg;
			j=j+2;
		}
		this.motherFrame.getPolling().saveOneInstruction(current.getCodeOp(), current.getDescription(), current.getNbArgs(),
														current.getDescriptionArguments(), b);*/
	}
	
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() instanceof JButton)
		{
			if(((JButton)e.getSource()).getName().equals(this.upLevelIdentifier))
			{
				this.numberLayer++;
				if (this.numberLayer == this.cube_size)
					this.bt_upLevel.setEnabled(false);
				this.bt_downLevel.setEnabled(true);
				this.lbl_currentLayer.setText(this.currentLayerText + this.numberLayer);
			}
			else if(((JButton)e.getSource()).getName().equals(this.downLevelIdentifier))
			{
				this.numberLayer--;
				if (this.numberLayer == 1)
					this.bt_downLevel.setEnabled(false);
				this.bt_upLevel.setEnabled(true);
				this.lbl_currentLayer.setText(this.currentLayerText + this.numberLayer);
			}
			else if(((JButton)e.getSource()).getName().equals(this.saveOneInstructionIdentifier))
			{
				createInstruction();
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
	}
}
