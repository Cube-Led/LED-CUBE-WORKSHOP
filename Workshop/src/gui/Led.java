package gui;

import java.awt.Rectangle;

public class Led {

	public static final int ON = 1;
	public static final int OFF = 0;

	private int state;
	private final int number;
	private Rectangle led;

	public Led(int number, int state, Rectangle Led) {
		this.number = number;
		this.setState(state);
		this.setLed(Led);
	}

	public int getNumber() {
		return number;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Rectangle getLed() {
		return led;
	}

	public void setLed(Rectangle led) {
		this.led = led;
	}

}
