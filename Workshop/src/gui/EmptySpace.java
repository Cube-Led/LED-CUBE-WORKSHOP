package gui;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class EmptySpace extends JLabel {
	
	public EmptySpace(int width)
	{
		this.setBorder(BorderFactory.createEmptyBorder(0, width/2, 0, width/2));
	}
}
