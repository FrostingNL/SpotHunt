package spothunt;

import java.util.List;
/**
 * This interface takes care of the <code>StupidSearch</code> and other, futre <code>Search</code>s.
 * @author Jeroen
 * @version 1.0
 *
 */
public interface Search {
	/**
	 * <code>findPath is used to find the path from the <code>MovingSpot</code> to the <code>GoalSpot</code>
	 * @param goal	the GoalSpot to which it wants to try to find a path
	 * @param spot	the MovingSpot from which it wants to try to find a path
	 * @return	List that includes all the Cells in order to form a path
	 */
	public List<Cell> findPath(GoalSpot goal, MovingSpot spot);
	
	/**
	 * Used to determine the next move in a path
	 * @param x				current x coordinate
	 * @param y				current y coordinate
	 * @param xDirection	the direction in which the X should travel (-1,0,1) (NOT USED IN STUPID SEARCH)
	 * @param yDirection	the direction in which the Y should travel (-1,0,1) (NOT USED IN STUPID SEARCH)
	 * @param spot			the MovingSpot (to get the values of the playfield)
	 * @return	a Cell which is the next Cell to move to
	 */
	public Cell nextMove(int x, int y, int xDirection, int yDirection, int remainingX, int remainingY, MovingSpot spot);
}
