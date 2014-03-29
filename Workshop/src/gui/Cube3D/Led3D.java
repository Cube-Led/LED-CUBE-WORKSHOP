package gui.Cube3D;



import java.awt.Color;
import org.lwjgl.util.vector.Vector3f;


public class Led3D{

	private boolean isOn;
	public Vector3f pos;
	private Color color;
	public static final Color DEFAULT_COLOR = new Color(1, 10, 12);
	public static final Color OFF_COLOR = new Color(255, 255, 255);
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		
		this.color = color;
	}

	public Led3D(float x, float y,float z)
	{
		color = OFF_COLOR;
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
	public void switchLed(boolean switchLed, java.awt.Color currentSelectedColor)
	{
		if(switchLed)
		{
			if(currentSelectedColor != null)
				setColor(currentSelectedColor);
			else
				setColor( OFF_COLOR);
		}
		else
			setColor( OFF_COLOR);
		this.isOn = switchLed;
	}

}
