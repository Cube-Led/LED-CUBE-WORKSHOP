package gui;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Workshop.Instruction;

public class ViewCreateImage extends View {

	public JLabel descriptionVue;
	public JLabel description;
	public JTextField txt_nbSecondes;
	public JButton bt_enregistrer;
	
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
		this.descriptionVue = new JLabel("Création d'une image fixe : les " + motherFrame.getPolling().getTheCube().getSizeCube() *2 + " instructions suivantes devront composer l'image fixe");
		
		this.description = new JLabel("Nombre de secondes");
		
		this.txt_nbSecondes = new JTextField("1.5");
		
		this.bt_enregistrer = new JButton("Enregistrer");
		this.bt_enregistrer.addActionListener(this);
		
		
		this.add(descriptionVue);
		this.add(description);
		this.add(txt_nbSecondes);
		this.add(bt_enregistrer);
	}
	
	public void createInstruction()
	{
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
		
		createInstruction();
		
	}

}
