/**
 * This class takes care of the <code>Cell</code>.<br>
 * It controls a Cell (e.g. <code>increaseDanger</code>, <code>putSpot</code>, <code>isGoal</code>), 
 * it holds the value for <code>danger</code> and it keeps track whether or not there is a <code>Player</code>,
 * <code>MovingSpot</code> or <code>GoalSpot</code> set/located on this cell.
 * @author Jeroen
 * @version 1.0
 *
 */
public class Cell {
	int danger = 0;
	bool player = false;
	bool spot = false;
	bool goal = false;
	int x = -1;
	int y = -1;
	
	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * <code>increaseDanger</code> increases (or decreases) the danger level of a <code>Cell</code>.
	 * @param increase the amount by which the danger level is increased/decreased.
	 * @since 1.0
	 */
	public void increaseDanger(int increase) {
		//assert danger + increase >= 0 : "Danger cannot be deceased to lower than 0, use reset to set it to 0! (danger + increase = " + (danger + increase) +")";
		//assert danger + increase <= Integer.MAX_VALUE : "Cannot further increase danger, Integer.MAX_VALUE has been reached! (danger + increase = " + (danger + increase) +")";
		
		danger = danger + increase;
	}
	
	/**
	 * <code>removePlayer</code> sets the <code>player</code> boolean to <code>false</code>. Indicating that a player left the cell.
	 * @since 1.0
	 */
	public void removePlayer() {
		player = false;
	}
	
	/**
	 * <code>putPlayer</code> sets the <code>player</code> boolean to <code>true</code>. Indicating that a player entered the cell.
	 * @since 1.0
	 */
	public void putPlayer() {
		player = true;
	}
	
	/**
	 * <code>hasPlayer</code> checks if the field has a <code>Player</code>.
	 * @return player
	 * @since 1.0
	 */
	public bool hasPlayer() {
		return player;
	}
	
	/**
	 * <code>removeSpot</code> sets the <code>spot</code> boolean to <code>false</code>. Indicating that the spot left the cell.
	 * @since 1.0
	 */
	public void removeSpot() {
		spot = false;
	}
	
	/**
	 * <code>putSpot</code> sets the <code>spot</code> boolean to <code>true</code>. Indicating that the spot entered the cell.
	 * @since 1.0
	 */
	public void putSpot() {
		spot = true;
	}
	
	/**
	 * <code>hasSpot</code> checks if the <code>MovingSpot</code> is currently in this field.
	 * @return spot
	 * @since 1.0
	 */
	public bool hasSpot() {
		return spot;
	}
	
	/**
	 * <code>setGoal</code> sets the <code>goal</code> boolean to <code>true</code>. Indicating this cell is now a goal.
	 * @since 1.0
	 */
	public void setGoal() {
		goal = true;
	}
	
	/**
	 * <code>isGoal</code> checks if the cell is a goal.
	 * @return goal
	 * @since 1.0
	 */
	public bool isGoal() {
		return goal;
	}
	
	/**
	 * <code>reset</code> sets the <code>danger</code> of the cell to <code> 0 </code>.
	 * @since 1.0
	 */
	public void reset() {
		danger = 0;
	}
	
	/**
	 * <code>getDanger</code> returns the <code>danger</code> value of this cell.
	 * @return danger
	 * @since 1.0
	 */
	public int getDanger() {
		return danger;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public string toString() {
		return "[" + this.x + ", " + this.y + "]";
	}
	
}


