package spothunt;
/**
 * This class takes care of the <code>GoalSpot</code>.<br>
 * It controls a Player (e.g. <code>setLocation</code>, <code>getX</code>, <code>toString</code>) and 
 * it holds the value for <code>x</code> and <code>y</code>.
 * @author Jeroen
 * @version 1.0
 */
public class GoalSpot implements Spot {
	int x = 0;
	int y = 0;
	Playfield playfield;
	int totalPlayerDistance;
	int spotDistance;
	int surroundThreat;
	
	/**
	 * The constructor of <code>GoalSpot</code>. This will link the spot to a <code>Playfield</code> and set it to its start location <code>[x,y]</code>.
	 * @param playfield	the Playfield it is linked to.
	 * @param x			the x coordinate of the GoalSpot.
	 * @param y			the y coordinate of the GoalSpot.
	 */
	public GoalSpot(Playfield playfield, int x, int y) {
		this.x = x;
		this.y = y;
			// Link GoalSpot to playfield
		this.playfield = playfield;
			// Set Cell[x][y] as a new Goal
		playfield.cells[x][y].setGoal();
	}
	
	public void setLocation(int newX, int newY) {
		x = newX;
		y = newY;
			// Set Cell[x][y] as a new Goal
		playfield.cells[x][y].setGoal();
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	/**
	 * Calculate the total <code>Player</code> distance to this spot.
	 */
	public void calculateTPD() {
		for(int g = 0; g < playfield.players.length; g++) {
			int diffX = Math.abs(this.getX() - playfield.players[g].getX());
			int diffY = Math.abs(this.getY() - playfield.players[g].getY());
			totalPlayerDistance = totalPlayerDistance + (int) Math.sqrt(Math.pow(diffX, 2)+Math.pow(diffY, 2));
		}
		System.out.println("Total Player Distance: " + totalPlayerDistance);
	}
	
	/**
	 * Returns the total <code>Player</code> distance to this spot.
	 * @return totalPlayerDistance;
	 */
	public int getTPD() {
		calculateTPD();
		return totalPlayerDistance;
	}
	
	/**
	 * Calculate the distance to the <code>MovingSpot</code> to this spot.
	 */
	public void calculateSD() {
		int diffX = Math.abs(this.getX() - playfield.spot.getX());
		int diffY = Math.abs(this.getY() - playfield.spot.getY());
		spotDistance = (int) Math.sqrt(Math.pow(diffX, 2)+Math.pow(diffY, 2));
	}
	
	/**
	 * Returns the distance to the <code>MovingSpot</code>
	 * @return spotDistance;
	 */
	public int getSD() {
		calculateSD();
		return spotDistance;
	}
	
	//TODO: Fix this function
	/**
	 * Calculates the surround threat arround this spot. Semi-works. (aka works enough to show idea)
	 * @return surroundThreat	the value of the surrounding threat
	 */
	public int calculateSurround() {
		int xUp = x;
		int xDown = x;
		surroundThreat = 0;
		for(int i=0;i<2; i++) {
			xUp = xUp - 1;
			if(xUp>=0 && xUp<playfield.height) {
				for(int j=-i; j<=i; j++) {
					if(y+j >= 0 && y+j < playfield.width) surroundThreat = surroundThreat + playfield.cells[xUp][y+j].getDanger();
				}
			}
			xDown = xDown + 1;
			if(xDown>=0 && xDown<playfield.height) {
				for(int j=-i; j<=i; j++) {
					if(y+j >= 0 && y+j < playfield.width) surroundThreat = surroundThreat + playfield.cells[xDown][y+j].getDanger();
				}
			}
		}
		int yLeft = y;
		int yRight = y;
		for(int i=1;i<=2; i++) {
			yLeft = yLeft - 1;
			if(yLeft>=0 && yLeft<playfield.width) {
				for(int j=-i+1; j<=i-1; j++) {
					if(x+j >= 0 && x+j < playfield.width) surroundThreat = surroundThreat + playfield.cells[x+j][yLeft].getDanger();
				}
			}
			yRight = yRight + 1;
			if(yRight>=0 && yRight<playfield.width) {
				for(int j=-i+1; j<=i-1; j++) {
					if(x+j >= 0 && x+j < playfield.width) surroundThreat = surroundThreat + playfield.cells[x+j][yRight].getDanger();
				}
			}
		}
		return surroundThreat;
	}
	
	//TODO: Finish this function
	/**
	 * Calculates the surround threat arround this spot. NOT FINISHED
	 * @param x			the x coordinate of the MovingSpot (or other object in the playfield)
	 * @param y			the y coordinate of the MovingSpot (or other object in the playfield)
	 * @return result	the value of the danger cost of the path from [x,y] to this GoalSpot
	 */
	public int calculateDangerCost(int x, int y) {
		int result = 0;
		
		/*
		 * CLEANED UP QUICKEST DANGER PATH COST THING HERE! 
		 */
		
		return result;
	}
	
	public String toString() {
		return "[" + this.x + "," + this.y + "]";
	}
}
