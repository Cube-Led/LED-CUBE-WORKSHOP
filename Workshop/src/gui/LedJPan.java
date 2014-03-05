package gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class LedJPan extends JPanel {
	private static final long serialVersionUID = 1L;

	private static final int ON = 1;
	private static final int OFF = 0;
	
	private int[][] ledGrid;
	private int cube_size;
	private int led_size;
		
	public LedJPan(int cube_size, int led_size, int ledGrid[][]){
		this.cube_size = cube_size;
		this.led_size = led_size;
		this.ledGrid = ledGrid;
		
	}

	public void paintComponent(Graphics g){
		
		g.setColor(Color.white);
    	g.fillRect(0, 0, this.getX(), this.getY());
    	
    	for( int i = 0; i < cube_size; i++){
    		for(int j = 0; j < this.cube_size; j++){
    			switch (this.ledGrid[i][j]){
    			case ON :
    				g.setColor(Color.yellow);
    		    	g.fillOval((int)(i*this.led_size + (this.getSize().getWidth()/4)), 
    		    			(int)(j*this.led_size + (this.getSize().getHeight()/4)), 
    		    			this.led_size, this.led_size);
    		    	break;
    			case OFF :
    				g.setColor(Color.gray);
    		    	g.fillOval((int)(i*this.led_size + (this.getSize().getWidth()/4)), 
    		    			(int)(j*this.led_size + (this.getSize().getHeight()/4)), 
    		    			this.led_size, this.led_size);
    		    	break;
    			}
    		}
    	}
    }

}
