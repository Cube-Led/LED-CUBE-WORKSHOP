package gui;
 
/**
 * Describe a position in 2D
 * 
 * @author soulierc
 * 
 */
public class Position
{
    private float x;
    private float y;

    public Position(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public float getX()
    {
        return this.x;
    }

    public float getY()
    {
        return this.y;
    }

    public Position translate(float deltaX, float deltaY)
    {
        return new Position(this.x + deltaX, this.y + deltaY);
    }

    public String toString()
    {
        return "[" + this.x + ", " + this.y + "]";
    }

}
