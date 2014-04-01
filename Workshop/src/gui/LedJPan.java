package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class LedJPan extends JPanel implements MouseListener,
		MouseMotionListener {
	private static final long serialVersionUID = 1L;

	private boolean initialised = false;

	private Led[] ledGrid;
	private int cube_size;
	private int led_size;

	private int half_picture_size;
	private int initial_position_width;
	private int initial_position_height;

	public LedJPan(int cube_size, int led_size, Led ledGrid[]) {
		super();
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.cube_size = cube_size;
		this.led_size = led_size;
		this.ledGrid = ledGrid;
	}

	private void init() {
		this.half_picture_size = (cube_size * led_size) / 2;
		this.initial_position_width = ((int) (this.getWidth() / 2))
				- this.half_picture_size;
		this.initial_position_height = ((int) (this.getHeight() / 2))
				- this.half_picture_size;
		int count = 0;
		for (int j = this.cube_size - 1; j >= 0; j--) {
			for (int i = 0; i < this.cube_size; i++) {
				ledGrid[count] = new Led(i, Led.OFF, new Rectangle(
						(i * this.led_size) + this.initial_position_width,
						(j * this.led_size) + this.initial_position_height,
						this.led_size, this.led_size));
				count++;
			}
		}
	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		if (!initialised) {
			init();
			initialised = true;
		}
		for (int i = 0; i < this.cube_size * this.cube_size; i++) {
			switch (this.ledGrid[i].getState()) {
			case Led.ON:
				g.setColor(Color.yellow);
				g.fillOval((int) this.ledGrid[i].getLed().getX(),
						(int) this.ledGrid[i].getLed().getY(), this.led_size,
						this.led_size);
				break;
			case Led.OFF:
				g.setColor(Color.gray);
				g.fillOval((int) this.ledGrid[i].getLed().getX(),
						(int) this.ledGrid[i].getLed().getY(), this.led_size,
						this.led_size);
				g.setColor(Color.white);
				g.drawString(
						"" + i,
						(int) (this.ledGrid[i].getLed().getX() + this.led_size / 2),
						(int) (this.ledGrid[i].getLed().getY()) + this.led_size
								/ 2);
				break;
			}
		}
	}

	public void changeState(Led currentLed) {
		if (currentLed.getState() == Led.ON)
			currentLed.setState(Led.OFF);
		else
			currentLed.setState(Led.ON);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		for (int i = 0; i < this.cube_size * this.cube_size; i++) {
			if (this.ledGrid[i].getLed().contains(e.getPoint()))
				changeState(this.ledGrid[i]);
		}
		this.repaint();
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
