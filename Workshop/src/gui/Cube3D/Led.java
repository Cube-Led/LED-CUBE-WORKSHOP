package gui.Cube3D;


import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;
import org.lwjgl.util.vector.Vector3f;


public class Led{

	private boolean isOn;
	public Vector3f pos;
	private ReadableColor color;
	
	public ReadableColor getColor() {
		return color;
	}

	public void setColor(ReadableColor color) {
		this.color = color;
	}

	public Led(float x, float y,float z)
	{
		color = Color.WHITE;
		pos = new Vector3f(x,y,z);
		this.isOn = false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return this.pos.toString();
		
	}
	
	/**
	 * Get the state of the led
	 * @return True if led is on, false however
	 */
	public boolean getIsOn()
	{
		return isOn;
	}
	
	/**
	 * Turn on or off the led
	 * @param switchLed Turn on if True, Turn off if false
	 *
	 */
	public void switchLed(boolean switchLed)
	{
		if(switchLed)
			setColor(Color.YELLOW);
		else
			setColor(Color.WHITE);
		this.isOn = switchLed;
	}

}
