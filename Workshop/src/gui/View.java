package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;


public class View extends JPanel implements ActionListener{

	protected GUIDisplay motherFrame;
	
	public View(GUIDisplay motherFrame)
	{
		this.motherFrame = motherFrame;
	}
	
	public void init(){}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
