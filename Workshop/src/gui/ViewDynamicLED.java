package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import Workshop.Instruction;

public class ViewDynamicLED extends View implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private JPanel pictureLED;
	
	private JList list_instructionsList;
	private JButton bt_loadInstructions;
	private JButton bt_saveOneInstruction;
	private JButton bt_retourMenu;
	private JButton bt_upLevel;
	private JButton bt_downLevel;
	
	private final String saveOneInstructionIdentifier = "bt_saveInst";
	private final String loadInstructionsIdentifier = "bt_loadAll";
	private final String retourMenuIdentifier = "bt_return";
	private final String upLevelIdentifier = "bt_upLevel";
	private final String downLevelIdentifier = "bt_downLevel";
	
	
	/* ------------------ Les LEDs ------------------ */
	
	private Led[] ledGrid;
	private static final float RATIO_LED = 0.04f;
	private int led_size;
	public int cube_size;
	
	public int getCube_size() {
		return cube_size;
	}


	public ViewDynamicLED(GUIDisplay motherFrame) {
		super(motherFrame);
		this.motherFrame = motherFrame;
		this.motherFrame.setSize(800, 600);
		init();
		this.updateUI();
	}
	
	
	public void init() {
		
		this.cube_size = 8;
		this.ledGrid = new Led[this.cube_size*this.cube_size];
		
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		Box left = new Box(BoxLayout.PAGE_AXIS);
		Box right = new Box(BoxLayout.PAGE_AXIS);
		
		
		/* ------------------- Coté Droit ------------------- */
		
		
		JScrollPane scrollPane = new JScrollPane();
		list_instructionsList= new JList();
		scrollPane.setViewportView(list_instructionsList);
		right.add(scrollPane);
		
		/* ------------------- Coté Gauche ------------------- */
		
		
		/* ---------------- Haut ---------------- */
		
		bt_upLevel = new JButton("Monter au niveau suivant");
		bt_upLevel.addActionListener(this);
		bt_upLevel.setName(this.upLevelIdentifier);
		bt_upLevel.setAlignmentX(RIGHT_ALIGNMENT);
		left.add(bt_upLevel, BorderLayout.NORTH);
		
		bt_downLevel = new JButton("Descendre au niveau précédent");
		bt_downLevel.addActionListener(this);
		bt_downLevel.setName(this.downLevelIdentifier);
		bt_downLevel.setAlignmentX(RIGHT_ALIGNMENT);
		left.add(bt_downLevel, BorderLayout.NORTH);
		
		
		/* ---------------- Milieu ---------------- */
		
		this.led_size = (int)(this.motherFrame.getWidth()*ViewDynamicLED.RATIO_LED);
		
		pictureLED = new LedJPan(this.cube_size, this.led_size, this.ledGrid);
		left.add(pictureLED,BorderLayout.CENTER);
		
		
		/* ---------------- Bas ---------------- */
		
		bt_saveOneInstruction = new JButton("Enregistrer cette instruction");
		bt_saveOneInstruction.addActionListener(this);
		bt_saveOneInstruction.setName(this.saveOneInstructionIdentifier);
		bt_saveOneInstruction.setLocation(100, 600);
		left.add(bt_saveOneInstruction, BorderLayout.SOUTH);
		
		bt_loadInstructions = new JButton("Charger les animations");
		bt_loadInstructions.addActionListener(this);
		bt_loadInstructions.setName(this.loadInstructionsIdentifier);
		bt_loadInstructions.setPreferredSize(new Dimension(bt_saveOneInstruction.getWidth(), bt_saveOneInstruction.getHeight()));
		bt_loadInstructions.setLocation(100, 650);
		left.add(bt_loadInstructions, BorderLayout.SOUTH);
		
		bt_retourMenu = new JButton("Retour menu");
		bt_retourMenu.addActionListener(this);
		bt_retourMenu.setName(this.retourMenuIdentifier);
		bt_retourMenu.setSize(bt_saveOneInstruction.getWidth(), bt_saveOneInstruction.getHeight());
		bt_retourMenu.setLocation(100, 700);
		left.add(bt_retourMenu, BorderLayout.SOUTH);
		
		this.add(left);
		this.add(right, BorderLayout.EAST);
	}
	
}
