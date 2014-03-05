package gui;



public class Led {
	
	public static final int ON = 1;
	public static final int OFF = 0;
	
	public int state;
	private final int number;
	private Position position;
	
	public Led(int number, int state, int x, int y){
		this.number = number;
		this.state = state;
		this.position = new Position((float)x, (float)y);
	}
	
	public int getNumber() {
		return number;
	}

	public Position getPosition() {
		return position;
	}
}
