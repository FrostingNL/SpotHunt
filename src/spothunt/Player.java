package spothunt;
/**
 * This class takes care of the <code>Player</code>.<br>
 * It controls a Player (e.g. <code>getDangerLevel</code>, <code>setLocation</code>, <code>toString</code>) and
 * it holds the value for <code>danger</code>, <code>x</code> and <code>y</code>.
 * @author Jeroen
 * @version 1.0
 *
 */
public class Player {
	int danger = 5;
	int x = -1;
	int y = -1;
	Playfield playfield;
	
	/**
	 * The player constructor.
	 * @param playfield the <code>Playfield</code> the player is playing on.
	 */
	public Player(Playfield playfield) {
		assert playfield != null : "Playfield doesn't exist!";
		
		this.playfield = playfield;
	}
	
	/**
	 * <code>getDangerLevel</code> returns the danger level of the player.
	 * @return danger
	 */
	public int getDangerLevel() {
		return danger;
	}
	
	/**
	 * <code>setLocation</code> moves the Player to a new location, given an <code>x</code> and <code>y</code>.
	 * @param newX	the new x coordinate of the player.
	 * @param newY	the new y coordinate of the player.
	 */
	public void setLocation(int newX, int newY) {
		assert newY >= 0 && newX >= 0 : "NewX and newY have to be larger than 0! (NewX,NewY = " + newX + "," + newY + ")";
		assert newY < playfield.width && newX < playfield.height : "NewX and newY are not within the height and width of the field! (NewX,NewY = " + newX + "," + newY + ")";
		
		// Update cells
		if(x!=-1 && y!=-1) playfield.cells[x][y].removePlayer();
		x = newX;
		y = newY;
		playfield.cells[x][y].putPlayer();
	}
	
	/**
	 * <code>getX</code> returns the x coordinate of the player.
	 * @return x
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * <code>getY</code> returns the y coordinate of the player.
	 * @return y
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * <code>toString</code> overwrites the default <code>toString()</code> method.
	 * @return [x,y]
	 */
	public String toString() {
		return "[" + this.x + "," + this.y + "]";
	}
}
