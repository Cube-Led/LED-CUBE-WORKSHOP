package gui;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Workshop.Instruction;
import Workshop.Tools;

public class ViewDynamicLED extends View implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private int numberLayer;
	
	private LedJPan pictureLED;
	private Image img;
	
	private JList list_instructionsList;
	
	private JLabel lbl_currentLayer;
	private final String currentLayerText = "Vous êtes au niveau : ";
	
	private JButton bt_upLevel;
	private JButton bt_downLevel;
	private JButton bt_saveOneInstruction;
	private JButton bt_loadInstructionsOnCube;
	private JButton bt_retourMenu;
	private JButton bt_suppr;
	
	private final String upLevelIdentifier = "bt_upLevel";
	private final String downLevelIdentifier = "bt_downLevel";
	private final String saveOneInstructionIdentifier = "bt_saveInst";
	private final String loadInstructionsIdentifier = "bt_loadAll";
	private final String retourMenuIdentifier = "bt_return";
	
	private final int LEFTPAN_WIDTH = (this.motherFrame.getWidth() * 6) /11;
	private final int BUTTON_WIDTH = 225;
	private final int BUTTON_HEIGHT = 35;
	private final int CENTER_LEFTPAN = (this.LEFTPAN_WIDTH /2) - ((int)(this.BUTTON_WIDTH / 2));
	
	
	private final short LIGHT_LAYER_CODE_OP = 2;
	private final short LIGHT_ALL_LED_CODE_OP = 4;
	private String[] descript_Args;
	
	
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
		this.motherFrame.setContentPane(this);
		this.updateUI();
		this.setVisible(true);
	}
	
	public void initDisplay() {
		
		this.cube_size = this.motherFrame.getPolling().getTheCube().getSizeCube();
		this.ledGrid = new Led[this.cube_size*this.cube_size];
		this.numberLayer = 1;
		
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		Box right = new Box(BoxLayout.PAGE_AXIS);
		JPanel left = new JPanel();
		
		this.setLayout(null);
		left.setLayout(null);
		
		/* ------------------- Coté Droit ------------------- */
		
		JScrollPane scrollPane = new JScrollPane();
		this.list_instructionsList= new JList();
		scrollPane.setViewportView(this.list_instructionsList);
		right.add(scrollPane);
		
		
		/* ------------------- Coté Gauche ------------------- */
		
		/* ---------------- Haut ---------------- */
		
		bt_upLevel = new JButton("Monter d'un niveau");
		bt_upLevel.addActionListener(this);
		bt_upLevel.setName(this.upLevelIdentifier);
		bt_upLevel.setBounds(this.CENTER_LEFTPAN , 0, this.BUTTON_WIDTH, this.BUTTON_HEIGHT);
		left.add(bt_upLevel);
		
		lbl_currentLayer = new JLabel("Vous êtes au niveau : ");
		lbl_currentLayer.setText(this.currentLayerText + this.numberLayer);
		lbl_currentLayer.setBounds(this.CENTER_LEFTPAN + this.BUTTON_WIDTH / 4, 35, this.BUTTON_WIDTH, 60);
		left.add(lbl_currentLayer);
		
		bt_downLevel = new JButton("Descendre d'un niveau");
		bt_downLevel.addActionListener(this);
		bt_downLevel.setName(this.downLevelIdentifier);
		bt_downLevel.setBounds(this.CENTER_LEFTPAN , 95, this.BUTTON_WIDTH, this.BUTTON_HEIGHT);
		bt_downLevel.setEnabled(false);
		left.add(bt_downLevel);
		
		
		/* ---------------- Milieu ---------------- */
		
		pictureLED = new LedJPan(this.cube_size, (int)(this.motherFrame.getWidth()*ViewDynamicLED.RATIO_LED), this.ledGrid);
		pictureLED.setBounds(0, 135, this.LEFTPAN_WIDTH, 395);
		left.add(pictureLED);
		
		
		/* ---------------- Bas ---------------- */
		
		bt_saveOneInstruction = new JButton("Enregistrer cette instruction");
		bt_saveOneInstruction.addActionListener(this);
		bt_saveOneInstruction.setName(this.saveOneInstructionIdentifier);
		bt_saveOneInstruction.setBounds(this.CENTER_LEFTPAN , 535, this.BUTTON_WIDTH, this.BUTTON_HEIGHT);
		left.add(bt_saveOneInstruction);

		bt_loadInstructionsOnCube = new JButton("Enregistrer l'animation + envoi");
		bt_loadInstructionsOnCube.addActionListener(this);
		bt_loadInstructionsOnCube.setName(this.loadInstructionsIdentifier);
		bt_loadInstructionsOnCube.setBounds(this.CENTER_LEFTPAN , 575, this.BUTTON_WIDTH, this.BUTTON_HEIGHT);
		left.add(bt_loadInstructionsOnCube);
		
		bt_retourMenu = new JButton("Retour au menu principal");
		bt_retourMenu.addActionListener(this);
		bt_retourMenu.setName(this.retourMenuIdentifier);
		bt_retourMenu.setBounds(this.CENTER_LEFTPAN , 615, this.BUTTON_WIDTH, this.BUTTON_HEIGHT);
		left.add(bt_retourMenu);
		
		bt_suppr = new JButton();
		try {
			this.img = ImageIO.read(new File("ressources/poubelle.jpg"));
		    bt_suppr.setIcon(new ImageIcon(this.img));
		    } catch (IOException ex) {}
		bt_suppr.addActionListener(this);
		bt_suppr.setName("Suppression");
		bt_suppr.setBounds(this.CENTER_LEFTPAN + this.BUTTON_WIDTH + 40, 580, 40, 50);
		left.add(bt_suppr);
				
		
		/* ------------------- Disposition des Box Gauche et Droite ------------------- */
		
		left.setBounds(0, 0, this.LEFTPAN_WIDTH, this.motherFrame.getHeight());
		right.setBounds((this.motherFrame.getWidth() * 6) /11 +1, 0, 
						(this.motherFrame.getWidth() * 5)/11, this.motherFrame.getHeight());
		
		this.add(left);
		this.add(right);		
	}
	
	
	/* ------------------- Algorithme pour la liste d'instruction ------------------- */
	
	private Instruction createInstruction()
	{
		long number = 0;
		Instruction current;
		List<Short> args;
		for (int i = 0; i < this.cube_size * this.cube_size; i++){
			if (this.ledGrid[i].getState() == Led.ON)
				number += Math.pow(2, i);
		}
		
		if (number == (Math.pow(2, this.cube_size*this.cube_size) -1)){
			current = new Instruction(this.LIGHT_ALL_LED_CODE_OP,"lightAllLedOnLayer",1);
			args = new ArrayList<Short>();
			args.add((short)this.numberLayer);
			current.setArgs(args);
			this.descript_Args = new String[1];
			this.descript_Args[0] = "Layer";
			current.setDescriptionArguments(this.descript_Args);
		}
		else{
			current = new Instruction(this.LIGHT_LAYER_CODE_OP,"lightLayer",2);
			args = new ArrayList<Short>();
			args.add((short)this.numberLayer);
			args.addAll(Tools.transformLongToShort(number));
			current.setArgs(args);
			this.descript_Args = new String[2];
			this.descript_Args[0] = "Layer";
			this.descript_Args[1] = "Number";
			current.setDescriptionArguments(this.descript_Args);
		}
		this.motherFrame.getPolling().saveOneInstruction(current.getCodeOp(), current.getDescription(), current.getNbArgs(), current.getDescriptionArguments(), current.getArgs());
		return current;
	}
	
	public void displayBuffer(List<Instruction> inst, int countInstructions) {
		this.list_instructionsList.setListData(inst.toArray());
		this.validate();
	}
	
	public void resetStateLed(Led[] ledGrid){
		for (int i =0; i < this.cube_size*this.cube_size; i++){
			this.ledGrid[i].setState(Led.OFF);
		}
		this.repaint();
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
				this.resetStateLed(this.ledGrid);
			}
			else if(((JButton)e.getSource()).getName().equals(this.downLevelIdentifier))
			{
				this.numberLayer--;
				if (this.numberLayer == 1)
					this.bt_downLevel.setEnabled(false);
				this.bt_upLevel.setEnabled(true);
				this.lbl_currentLayer.setText(this.currentLayerText + this.numberLayer);
				this.resetStateLed(this.ledGrid);
			}
			else if(((JButton)e.getSource()).getName().equals(this.saveOneInstructionIdentifier))
			{
				createInstruction();
			}
			else if(((JButton)e.getSource()).getName().equals(this.loadInstructionsIdentifier))
			{
				JFileChooser saveFile = new JFileChooser();
				saveFile.setApproveButtonText("Sauvegarder");
				saveFile.showOpenDialog(this);
				File saveInFile = saveFile.getSelectedFile();
				if (saveInFile != null){
					this.motherFrame.getPolling().writeSavedInstructionsInSavefile(saveInFile);
				}
			}
			else if(((JButton)e.getSource()).getName().equals(this.retourMenuIdentifier))
			{
				this.motherFrame.setContentPane(new ViewMainMenu(motherFrame));
			}
			else if(((JButton)e.getSource()).getName().equals("Suppression"))
			{
				if(this.list_instructionsList.getSelectedIndex() == -1)
					this.motherFrame.getPolling().deleteListOfInstructions();
				else
					this.motherFrame.getPolling().deleteSelectedInstruction(this.list_instructionsList.getSelectedIndex());
			}
		}
	}
	
}
