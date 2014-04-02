package gui;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Workshop.Instruction;

public class ViewCreateImage extends View {

	private static final long serialVersionUID = 1L;
	public JLabel descriptionVue;
	public JLabel description;
	private JLabel text;
	private JLabel text2;
	private JLabel text3;
	private JLabel text4;
	public JTextField txt_nbSecondes;
	public JButton bt_enregistrer;
	private JButton bt_retourMenu;
	
	private final int BUTTON_WIDTH = 225;
	private final int BUTTON_HEIGHT = 35;	
	private final short CODE_OP_ITERATOR = 0x06;
	private final short DELAY = 3;
	
	public ViewCreateImage(GUIDisplay motherFrame) {
		super(motherFrame);
		init();
		this.motherFrame.setContentPane(this);
		this.updateUI();
	}
	
	public void init()
	{
		this.setLayout(null);
		
		this.descriptionVue = new JLabel("Création d'une image fixe : ");
		this.descriptionVue.setBounds(75, 50, this.BUTTON_WIDTH, this.BUTTON_HEIGHT);
		this.add(descriptionVue);
		
		this.text = new JLabel("Les " + motherFrame.getPolling().getTheCube().getSizeCube() *2 + " instructions suivantes devront composer l'image fixe");
		this.text.setBounds(100, 100, this.BUTTON_WIDTH*2, this.BUTTON_HEIGHT);
		this.add(this.text);
		
		this.text2 = new JLabel("Le but içi est de générer un itérator (boucle pour) contenant deux instructions par niveau.");
		this.text2.setBounds(100, 150, this.BUTTON_WIDTH*3, this.BUTTON_HEIGHT);
		this.add(this.text2);
		
		this.text3 = new JLabel("Ainsi, on a une instruction contenant les LED à allumer ");
		this.text3.setBounds(100, 200, this.BUTTON_WIDTH*3, this.BUTTON_HEIGHT);
		this.add(this.text3);
		
		this.text4 = new JLabel("et un délai générer par rapport au temps saisi dans le champ ci-dessous.");
		this.text4.setBounds(100, 250, this.BUTTON_WIDTH*3, this.BUTTON_HEIGHT);
		this.add(this.text4);
		
		this.description = new JLabel("Nombre de secondes :");
		this.description.setBounds(100, 300, this.BUTTON_WIDTH, this.BUTTON_HEIGHT);
		this.add(description);
		
		this.txt_nbSecondes = new JTextField("1.5");
		this.txt_nbSecondes.setBounds(100, 350, this.BUTTON_WIDTH, this.BUTTON_HEIGHT);
		this.add(txt_nbSecondes);
		
		this.bt_enregistrer = new JButton("Enregistrer");
		this.bt_enregistrer.setBounds(100, 400, this.BUTTON_WIDTH, this.BUTTON_HEIGHT);
		this.bt_enregistrer.addActionListener(this);
		this.add(bt_enregistrer);
		
		bt_retourMenu = new JButton("Retour au menu principal");
		bt_retourMenu.addActionListener(this);
		bt_retourMenu.setBounds(500, 450, this.BUTTON_WIDTH,this.BUTTON_HEIGHT);
		this.add(bt_retourMenu);
		
		this.setVisible(true);
	}
	
	public void createInstruction() {
		short nbTour = (short) (Float.parseFloat(this.txt_nbSecondes.getText())*1000 / DELAY);
		short nbPlayedInstruct = (short) (this.motherFrame.getPolling().getTheCube().getSizeCube() * 2);
		short address = (short) (5+this.motherFrame.getPolling().getListofInstruction().size());
		for(int i =0; i< this.motherFrame.getPolling().getListofInstruction().size(); i++)
		{
			address = (short) (address + this.motherFrame.getPolling().getListofInstruction().get(i).getSize());
		}
		List<Short> args = new ArrayList<Short>();
		args.add(nbTour);
		args.add(nbPlayedInstruct);
		args.add(address);
		Instruction iterator  = new Instruction(CODE_OP_ITERATOR,"Iterator pour image ("+ motherFrame.getPolling().getTheCube().getSizeCube()*2+")", 3);
		iterator.setArgs(args);
		this.motherFrame.getPolling().saveOneInstruction(iterator);		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (((JButton) e.getSource()).equals(this.bt_enregistrer))
			createInstruction();
		else if (((JButton) e.getSource()).equals(this.bt_retourMenu))
			this.motherFrame.setContentPane(new ViewMainMenu(this.motherFrame));
	}

}
