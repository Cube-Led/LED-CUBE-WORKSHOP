package gui;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class HorizontalEmptySpace extends JLabel {
	
	public HorizontalEmptySpace(int width)
	{
		this.setBorder(BorderFactory.createEmptyBorder(0, width/2, 0, width/2));
	}
	public HorizontalEmptySpace(int left, int right)
	{
		this.setBorder(BorderFactory.createEmptyBorder(0, left, 0, right));
	}
}
