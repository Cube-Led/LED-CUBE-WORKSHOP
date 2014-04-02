package Workshop;

/**
 * This class represent a cube of led configuration
 * @author Clement
 *
 */
public class Cube {

	/**
	 * The size by default of a cube
	 */
	public static final int DEFAULT_CUBE_SIZE = 8;
	
	/**
	 * The size of the current cube, the size is the number of LEDs on one edge
	 */
	private final int sizeCube;
	
	/**
	 * Tell if the LEDs of the cube are monochrome or RGB
	 */
	private final boolean isMonochrome;
	
	/**
	 * Default constructor
	 * Define a monochrome Cube.DEFAULT_CUBE_SIZE^3 cube
	 */
	public Cube()
	{
		this.sizeCube = DEFAULT_CUBE_SIZE;
		this.isMonochrome = true;
	}
	
	/**
	 * Define a monochrome or RBG cube with the specified size
	 * @param size
	 * @param isMonochrome
	 */
	public Cube(int size, boolean isMonochrome)
	{
		this.sizeCube = size;
		this.isMonochrome = isMonochrome;
	}
	
	/**
	 * Retrun the size of this cube (number of LEDs on one edge)
	 * @return The cube size
	 */
	public int getSizeCube()
	{
		return this.sizeCube;
	}

	/**
	 * Tell if the cube is monochrome or RGB
	 * @return True if monochrome, false if RBG or else
	 */
	public boolean isMonochrome() {
		return isMonochrome;
	}
}
