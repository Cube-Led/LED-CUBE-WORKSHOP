package gui;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class VerticalEmptySpace extends JLabel {
	
	private static final long serialVersionUID = 1L;
	
	public VerticalEmptySpace(int height)
	{
		this.setBorder(BorderFactory.createEmptyBorder(0, height/2, 0, height/2));
	}
	public VerticalEmptySpace(int top, int bottom)
	{
		this.setBorder(BorderFactory.createEmptyBorder(0, top, 0, bottom));
	}
}