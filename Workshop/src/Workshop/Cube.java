package Workshop;

public class Cube {

	public static final int DEFAULT_CUBE_SIZE = 4;
	
	private final int sizeCube;
	
	private final int numberOfLED;
	
	private final boolean isMonochrome;
	
	/**
	 * Default constructor
	 * Define a monochrome Cube.DEFAULT_CUBE_SIZE^3 cube
	 */
	public Cube()
	{
		this.sizeCube = DEFAULT_CUBE_SIZE;
		this.numberOfLED = sizeCube * sizeCube * sizeCube;
		this.isMonochrome = true;
	}
	
	public Cube(int size, boolean isMonochrome)
	{
		this.sizeCube = size;
		this.numberOfLED = sizeCube * sizeCube * sizeCube;
		this.isMonochrome = isMonochrome;
	}
	
	public int getSizeCube()
	{
		return this.sizeCube;
	}
}
