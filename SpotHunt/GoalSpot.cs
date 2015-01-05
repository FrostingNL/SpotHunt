using System;
/**
 * This class takes care of the <code>GoalSpot</code>.<br>
 * It controls a Player (e.g. <code>setLocation</code>, <code>getX</code>, <code>toString</code>) and 
 * it holds the value for <code>x</code> and <code>y</code>.
 * @author Jeroen
 * @version 1.0
 */
using SpotHunt;


public class GoalSpot : Spot {
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
		for(int g = 0; g < playfield.getPlayerAmount(); g++) {
			int diffX = Math.Abs(this.getX() - playfield.players[g].getX());
			int diffY = Math.Abs(this.getY() - playfield.players[g].getY());
			totalPlayerDistance = totalPlayerDistance + (int) Math.Sqrt(Math.Pow(diffX, 2)+Math.Pow(diffY, 2));
		}
		// System.out.println("Total Player Distance: " + totalPlayerDistance);
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
		int diffX = Math.Abs(this.getX() - playfield.spot.getX());
		int diffY = Math.Abs(this.getY() - playfield.spot.getY());
		spotDistance = (int) Math.Sqrt(Math.Pow(diffX, 2)+Math.Pow(diffY, 2));
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
	
	//TODO: Cut and clean this code
	/**
	 * Calculates the surround threat arround this spot. NOT FINISHED
	 * @param x			the x coordinate of the MovingSpot (or other object in the playfield)
	 * @param y			the y coordinate of the MovingSpot (or other object in the playfield)
	 * @return result	the value of the danger cost of the path from [x,y] to this GoalSpot
	 */
	public int calculateDangerCost(int spotX, int spotY, PossibleTarget current) {
		int possibleX = this.x - spotX;
		int possibleY = this.y - spotY;
		int dangerCost = 0;
		int highestDanger = 0;
		double calculatedCost = 0;
		// System.out.println("Y: " + possibleY);
		// System.out.println("X: " + possibleX);
		// System.out.println(possibleY==0);
		// System.out.println(possibleX==0);
		
		if(possibleX==0) {
			// System.out.println("in X==0");
			for(int i=1; i <= Math.Abs(possibleY); i++) {
				if(possibleY>0) {
					int nextCost = playfield.cells[spotX][spotY+i].getDanger();
					if(nextCost > highestDanger) highestDanger = nextCost;
					dangerCost = dangerCost + nextCost;
				} else {
					int nextCost = playfield.cells[spotX][spotY-i].getDanger();
					if(nextCost > highestDanger) highestDanger = nextCost;
					dangerCost = dangerCost + nextCost;
				}
			}
			calculatedCost = (int)Math.Pow(dangerCost, 2)/Math.Abs(possibleY);
		}else if(possibleY==0) {
			// System.out.println("in Y==0");
			for(int i=1; i <= Math.Abs(possibleX); i++) {
				if(possibleX>0) {
					// System.out.println("in Y==0 | if | i=" +i + " | Danger=" + playfield.cells[spotX+i][spotY].getDanger());
					int nextCost =  playfield.cells[spotX+i][spotY].getDanger();
					if(nextCost > highestDanger) highestDanger = nextCost;
					dangerCost = dangerCost + nextCost;
				} else {
					// System.out.println("in Y==0 | else | i="+i + " | Danger=" + playfield.cells[spotX-i][spotY].getDanger());
					int nextCost =  playfield.cells[spotX-i][spotY].getDanger();
					if(nextCost > highestDanger) highestDanger = nextCost;
					dangerCost = dangerCost + nextCost;
				}
			}
			calculatedCost = (int)Math.Pow(dangerCost, 2)/Math.Abs(possibleX);
		}else if((double)possibleX/(double)possibleY==1.0) {
			// System.out.println("in X/Y==1");
			//TODO: see if the last Else If and the last Else are correctly implemented! :)
			for(int i=1; i <= possibleY; i++) {
				if(possibleX>0 && possibleY>0) {
					int nextCost =  playfield.cells[spotX+i][spotY+i].getDanger();
					if(nextCost > highestDanger) highestDanger = nextCost;
					dangerCost = dangerCost + nextCost;
				} else if(possibleX<0 && possibleY<0) {
					int nextCost =  playfield.cells[spotX-i][spotY-i].getDanger();
					if(nextCost > highestDanger) highestDanger = nextCost;
					dangerCost = dangerCost + nextCost;
				} else if(possibleX<0 && possibleY>0) {
					int nextCost =  playfield.cells[spotX-i][spotY+i].getDanger();
					if(nextCost > highestDanger) highestDanger = nextCost;
					dangerCost = dangerCost + nextCost;
				} else {
					int nextCost =  playfield.cells[spotX+i][spotY-i].getDanger();
					if(nextCost > highestDanger) highestDanger = nextCost;
					dangerCost = dangerCost + nextCost;
				}
			}
			calculatedCost = (int)Math.Pow(dangerCost, 2)/Math.Abs(Math.Sqrt(Math.Pow(possibleX, 2)+Math.Pow(possibleY, 2)));
		} else {
			// System.out.println("PossibleX, PossibleY: " + possibleX + ", " + possibleY);
			if(possibleX>0 && possibleY>0) {
				if(possibleX > possibleY) {
					for(int i=1; i<possibleY+1; i++) {
						int nextCost =  playfield.cells[spotX+i][spotY+i].getDanger();
						if(nextCost > highestDanger) highestDanger = nextCost;
						dangerCost = dangerCost + nextCost;
						// System.out.println("X,Y: " + (spotX+i) + ", " + (spotY+i));
						// System.out.println("Cost: " + playfield.cells[spotX+i][spotY+i].getDanger());
					}
					int remaining = possibleX - possibleY + 1;
					for(int i = 1; i < remaining; i++) {
						int nextCost = playfield.cells[spotX+possibleY+i][spotY+possibleY].getDanger();
						if(nextCost > highestDanger) highestDanger = nextCost;
						dangerCost = dangerCost + nextCost;
						// System.out.println("X,Y: " + (spotX+possibleY+i) + ", " + (spotY+possibleY));
						// System.out.println("Cost: " + playfield.cells[spotX+possibleY+i][spotY+possibleY].getDanger());
					}
				} else {
					for(int i=1; i<possibleX+1; i++) {
						int nextCost =  playfield.cells[spotX+i][spotY+i].getDanger();
						if(nextCost > highestDanger) highestDanger = nextCost;
						dangerCost = dangerCost + nextCost;
						// System.out.println("X,Y: " + (spotX+i) + ", " + (spotY+i));
						// System.out.println("Cost: " + playfield.cells[spotX+i][spotY+i].getDanger());
					}
					int remaining = possibleY - possibleX + 1;
					for(int i = 1; i < remaining; i++) {
						int nextCost =  playfield.cells[spotX+possibleX][spotY+possibleX+1].getDanger();
						if(nextCost > highestDanger) highestDanger = nextCost;
						dangerCost = dangerCost + nextCost;
						// System.out.println("X,Y: " + (spotX+possibleX) + ", " + (spotY+possibleX+i));
						// System.out.println("Cost: " + playfield.cells[spotX+possibleX][spotY+possibleX+i].getDanger());
					}
				}
				calculatedCost = (int)Math.Pow(dangerCost, 2)/Math.Abs(Math.Sqrt(Math.Pow(possibleX, 2)+Math.Pow(possibleY, 2)));
			} else if(possibleX<0 && possibleY<0) {
				//NEGATIEFF!!
				if(possibleX > possibleY) {
					possibleX = Math.Abs(possibleX);
					possibleY = Math.Abs(possibleY);
					for(int i=1; i<possibleX+1; i++) {
						int nextCost =  playfield.cells[spotX-i][spotY-i].getDanger();
						if(nextCost > highestDanger) highestDanger = nextCost;
						dangerCost = dangerCost + nextCost;
						// System.out.println("X,Y: " + (spotX-i) + ", " + (spotY-i));
						// System.out.println("Cost: " + playfield.cells[spotX-i][spotY-i].getDanger());
					}
					int remaining = possibleY - possibleX + 1;
					for(int i = 1; i < remaining; i++) {
						int nextCost =  playfield.cells[spotX-possibleX][spotY-possibleX-i].getDanger();
						if(nextCost > highestDanger) highestDanger = nextCost;
						dangerCost = dangerCost + nextCost;
						// System.out.println("X,Y: " + (spotX-possibleX) + ", " + (spotY-possibleX-i));
						// System.out.println("Cost: " + playfield.cells[spotX-possibleX][spotY-possibleX-i].getDanger());
					}
				} else {
					possibleX = Math.Abs(possibleX);
					possibleY = Math.Abs(possibleY);
					for(int i=1; i<possibleY+1; i++) {
						int nextCost =  playfield.cells[spotX-i][spotY-i].getDanger();
						if(nextCost > highestDanger) highestDanger = nextCost;
						dangerCost = dangerCost + nextCost;
						// System.out.println("X,Y: " + (spotX-i) + ", " + (spotY-i));
						// System.out.println("Cost: " + playfield.cells[spotX-i][spotY-i].getDanger());
					}
					int remaining = possibleX - possibleY + 1;
					for(int i = 1; i < remaining; i++) {
						int nextCost =  playfield.cells[spotX-possibleY-i][spotY-possibleY].getDanger();
						if(nextCost > highestDanger) highestDanger = nextCost;
						dangerCost = dangerCost + nextCost;
						// System.out.println("X,Y: " + (spotX-possibleY-i) + ", " + (spotY-possibleY));
						// System.out.println("Cost: " + playfield.cells[spotX-possibleY-i][spotY-possibleY].getDanger());
					}
				}
				calculatedCost = (int)Math.Pow(dangerCost, 2)/Math.Abs(Math.Sqrt(Math.Pow(possibleX, 2)+Math.Pow(possibleY, 2)));
			} else if(possibleX<0 && possibleY>0) {
				if(Math.Abs(possibleX) < Math.Abs(possibleY)) {
					possibleX = Math.Abs(possibleX);
					possibleY = Math.Abs(possibleY);
					for(int i=1; i<possibleX+1; i++) {
						int nextCost =  playfield.cells[spotX-i][spotY+i].getDanger();
						if(nextCost > highestDanger) highestDanger = nextCost;
						dangerCost = dangerCost + nextCost;
						// System.out.println("X,Y: " + (spotX-i) + ", " + (spotY+i));
						// System.out.println("Cost: " + playfield.cells[spotX-i][spotY+i].getDanger());
					}
					int remaining = possibleY - possibleX + 1;
					for(int i = 1; i < remaining; i++) {
						int nextCost =  playfield.cells[spotX-possibleX][spotY+possibleX+i].getDanger();
						if(nextCost > highestDanger) highestDanger = nextCost;
						dangerCost = dangerCost + nextCost;
						// System.out.println("X,Y: " + (spotX-possibleX) + ", " + (spotY+possibleX+i));
						// System.out.println("Cost: " + playfield.cells[spotX-possibleX][spotY+possibleX+i].getDanger());
					}
				} else {
					possibleX = Math.Abs(possibleX);
					possibleY = Math.Abs(possibleY);
					for(int i=1; i<possibleY+1; i++) {
						int nextCost = playfield.cells[spotX-i][spotY+i].getDanger();
						if(nextCost > highestDanger) highestDanger = nextCost;
						dangerCost = dangerCost + nextCost;
						// System.out.println("X,Y: " + (spotX-i) + ", " + (spotY+i));
						// System.out.println("Cost: " + playfield.cells[spotX-i][spotY+i].getDanger());
					}
					int remaining = possibleX - possibleY + 1;
					// System.out.println("Remaining: " + remaining);
					for(int i = 1; i < remaining; i++) {
						int nextCost = playfield.cells[spotX-possibleY-i][spotY+possibleY].getDanger();
						if(nextCost > highestDanger) highestDanger = nextCost;
						dangerCost = dangerCost + nextCost;
						// System.out.println("X,Y: " + (spotX-possibleY-i) + ", " + (spotY+possibleY));
						// System.out.println("Cost: " + playfield.cells[spotX-possibleY-i][spotY+possibleY].getDanger());
					}
				}
				calculatedCost = (int)Math.Pow(dangerCost, 2)/Math.Abs(Math.Sqrt(Math.Pow(possibleX, 2)+Math.Pow(possibleY, 2)));
			} else { // possibleX>0 && possibleY<0
				if(Math.Abs(possibleX) < Math.Abs(possibleY)) {
					possibleX = Math.Abs(possibleX);
					possibleY = Math.Abs(possibleY);
					for(int i=1; i<possibleX+1; i++) {
						int nextCost = playfield.cells[spotX+i][spotY-i].getDanger();
						if(nextCost > highestDanger) highestDanger = nextCost;
						dangerCost = dangerCost + nextCost;
						// System.out.println("X,Y: " + (spotX+i) + ", " + (spotY-i));
						// System.out.println("Cost: " + playfield.cells[spotX+i][spotY-i].getDanger());
					}
					int remaining = possibleY - possibleX + 1;
					for(int i = 1; i < remaining; i++) {
						int nextCost = playfield.cells[spotX+possibleX][spotY+possibleX-i].getDanger();
						if(nextCost > highestDanger) highestDanger = nextCost;
						dangerCost = dangerCost + nextCost;
						// System.out.println("X,Y: " + (spotX+possibleX) + ", " + (spotY-possibleX-i));
						// System.out.println("Cost: " + playfield.cells[spotX+possibleX][spotY-possibleX-i].getDanger());
					}
				} else {
					possibleX = Math.Abs(possibleX);
					possibleY = Math.Abs(possibleY);
					// System.out.println("posY" + possibleY);
					for(int i=1; i<possibleY+1; i++) {
						int nextCost = playfield.cells[spotX+i][spotY-i].getDanger();
						if(nextCost > highestDanger) highestDanger = nextCost;
						dangerCost = dangerCost + nextCost;
						// System.out.println("X,Y: " + (spotX+i) + ", " + (spotY-i));
						// System.out.println("Cost: " + playfield.cells[spotX+i][spotY-i].getDanger());
					}
					int remaining = possibleX - possibleY + 1;
					for(int i = 1; i < remaining; i++) {
						int nextCost =  playfield.cells[spotX+possibleY+i][spotY-possibleY].getDanger();
						if(nextCost > highestDanger) highestDanger = nextCost;
						dangerCost = dangerCost + nextCost;
						// System.out.println("X,Y: " + (spotX+possibleY+i) + ", " + (spotY-possibleY));
						// System.out.println("Cost: " + playfield.cells[spotX+possibleY+i][spotY-possibleY].getDanger());
					}
				}
				calculatedCost = (int)Math.Pow(dangerCost, 2)/Math.Abs(Math.Sqrt(Math.Pow(possibleX, 2)+Math.Pow(possibleY, 2)));
			}
		}
		current.setHighestDanger(highestDanger);
		return (int) calculatedCost;
	}
	
	public String toString() {
		return "[" + this.x + "," + this.y + "]";
	}
}

