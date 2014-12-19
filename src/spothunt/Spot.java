package spothunt;
/**
 * This interface takes care of the <code>MovingSpot</code> and <code>GoalSpot</code>.
 * @author Jeroen
 * @version 1.0
 *
 */
public interface Spot {
	/**
	 * <code>setLocation</code> moves the <code>MovingSpot</code> to a new location, given an <code>x</code> and <code>y</code>.
	 * @param newX	the new x coordinate of the spot.
	 * @param newY	the new y coordinate of the spot.
	 */
	public void setLocation(int newX, int newY);
	
	/**
	 * <code>getX</code> returns the x coordinate of the spot.
	 * @return x
	 */
	public int getX();
	
	/**
	 * <code>getY</code> returns the y coordinate of the spot.
	 * @return y
	 */
	public int getY();
	
	/**
	 * <code>toString</code> overwrites the default <code>toString()</code> method.
	 * @return [x,y]
	 */
	public String toString();
}
