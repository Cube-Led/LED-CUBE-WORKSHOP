package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

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
	
	private int[][] ledGrid;
	private static final int ON = 1;
	private static final int OFF = 0;
	private static final float RATIO_LED = 0.04f;
	private int led_size;
	public int cube_size;
	
	public ViewDynamicLED(GUIDisplay motherFrame) {
		super(motherFrame);
		this.motherFrame = motherFrame;
		this.motherFrame.setSize(800, 600);
		init();
		this.updateUI();
	}
	
	
	public void init() {
		
		this.cube_size = 4;
		this.ledGrid = new int[cube_size][cube_size];
		
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
		left.add(bt_upLevel, BorderLayout.NORTH);
		
		bt_downLevel = new JButton("Descendre au niveau précédent");
		bt_downLevel.addActionListener(this);
		bt_downLevel.setName(this.downLevelIdentifier);
		left.add(bt_downLevel, BorderLayout.NORTH);
		
		
		/* ---------------- Milieu ---------------- */
		
		this.led_size = (int)(this.motherFrame.getWidth()*ViewDynamicLED.RATIO_LED);
		
		pictureLED = new JPanel();
		pictureLED.paint(getGraphics());
		left.add(pictureLED,BorderLayout.CENTER);
		
		
		/* ---------------- Bas ---------------- */
		
		bt_saveOneInstruction = new JButton("Enregistrer cette instruction");
		bt_saveOneInstruction.addActionListener(this);
		bt_saveOneInstruction.setEnabled(false);
		bt_saveOneInstruction.setName(this.saveOneInstructionIdentifier);
		bt_saveOneInstruction.setAlignmentX(CENTER_ALIGNMENT);
		bt_saveOneInstruction.setLocation(100, 600);
		left.add(bt_saveOneInstruction, BorderLayout.SOUTH);
		
		bt_loadInstructions = new JButton("Charger les animations");
		bt_loadInstructions.addActionListener(this);
		bt_loadInstructions.setName(this.loadInstructionsIdentifier);
		bt_loadInstructions.setLocation(100, 650);
		left.add(bt_loadInstructions, BorderLayout.SOUTH);
		
		bt_retourMenu = new JButton("Retour menu");
		bt_retourMenu.addActionListener(this);
		bt_retourMenu.setName(this.retourMenuIdentifier);
		bt_retourMenu.setLocation(100, 700);
		left.add(bt_retourMenu, BorderLayout.SOUTH);
		
		
		
		this.add(left);
		this.add(right, BorderLayout.EAST);
	}
	
	public void paintComponent(Graphics g){
		
		g.setColor(Color.red);
    	g.fillRect(0, 0, 800, 600);
    	
    	for( int i = 0; i < this.cube_size; i++){
    		for(int j = 0; j < this.cube_size; j++){
    			switch (this.ledGrid[i][j]){
    			case ON :
    				g.setColor(Color.yellow);
    		    	g.fillOval(i*this.led_size + pictureLED.getX(), j*this.led_size + pictureLED.getY(), this.led_size, this.led_size);
    		    	break;
    			case OFF :
    				g.setColor(Color.gray);
    		    	g.fillOval(i*this.led_size + pictureLED.getX(), j*this.led_size + pictureLED.getY(), this.led_size, this.led_size);
    		    	break;
    			}
    		}
    	}
    }
	
	
	
}
