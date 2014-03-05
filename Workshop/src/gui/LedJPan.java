package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class LedJPan extends JPanel implements MouseListener, MouseMotionListener{
	private static final long serialVersionUID = 1L;
	
	private Led[] ledGrid;
	private int cube_size;
	private int led_size;
	
	private int half_picture_size;
	private int initial_position_width;
	private int initial_position_height;
		
	public LedJPan(int cube_size, int led_size, Led ledGrid[]){
		super();
		this.half_picture_size = (this.cube_size * this.led_size) /2;
		this.initial_position_width = ((int)(this.getSize().getWidth() / 2)) - this.half_picture_size;
		this.initial_position_height = ((int)(this.getHeight() / 2)) + this.half_picture_size;
		this.cube_size = cube_size;
		this.led_size = led_size;
		int count = 0;
		for (int j = this.cube_size -1; j >= 0; j--){
			for (int i = 0; i < this.cube_size; i++){
				System.out.println((this.cube_size * this.led_size) /2);
				System.out.println(this.half_picture_size);
				ledGrid[count] = new Led(i, Led.OFF, new Rectangle((i*this.led_size) + this.initial_position_width, (j*this.led_size) + this.initial_position_height, 
																	this.led_size, this.led_size));
				count++;
			}
		}
		this.ledGrid = ledGrid;
	}

	public void paintComponent(Graphics g){
		g.setColor(Color.red);
    	g.fillRect(0, 0, this.getWidth(), this.getHeight());
    	for( int i = 0; i < this.cube_size*this.cube_size; i++){
    			switch (this.ledGrid[i].getState()){
    			case Led.ON :
    				g.setColor(Color.yellow);
    		    	g.fillOval((int)this.ledGrid[i].getLed().getX(), (int)this.ledGrid[i].getLed().getY(), this.led_size, this.led_size);
    		    	break;
    			case Led.OFF :
    				g.setColor(Color.gray);
    		    	g.fillOval((int)this.ledGrid[i].getLed().getX(), (int)this.ledGrid[i].getLed().getY(), this.led_size, this.led_size);
    		    	g.setColor(Color.white);
    		    	g.drawString(""+i, (int)(this.ledGrid[i].getLed().getX()+this.led_size/2), (int)(this.ledGrid[i].getLed().getY())+this.led_size/2);
    		    	break;
    		}
    	}
    }

	@Override
	public void mouseClicked(MouseEvent arg0) {
		/*int numberColumn;
		int numberLine;
		switch((int)(arg0.getPoint().getX()) % this.initial_position_width){
			case 0 :
				numberColumn = 0;
				break;
			case 1 :
				numberColumn = 1;
				break;
			case 2 :
				numberColumn = 2;
				break;
			case 3 :
				numberColumn = 3;
				break;
			case 4 :
				numberColumn = 4;
				break;
			case 5 :
				numberColumn = 5;
				break;
			case 6 :
				numberColumn = 6;
				break;
		}*/
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
