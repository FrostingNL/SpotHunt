package spothunt;
/**
 * This class takes care of the <code>MovingSpot</code>.<br>
 * It controls a Player (e.g. <code>setLocation</code>, <code>getX</code>, <code>toString</code>) and 
 * it holds the value for <code>x</code> and <code>y</code>.<br>
 * <b>It implements Spot.</b>
 * @author Jeroen
 * @version 1.0
 *
 */
public class MovingSpot implements Spot {
	int x = 0;
	int y = 0;
	Playfield playfield;
	
	/**
	 * The constructor of <code>MovingSpot</code>. This will link the spot to a <code>Playfield</code> and set it to its start location <code>[0,0]</code>.
	 * @param playfield	the Playfield it is linked to.
	 */
	public MovingSpot(Playfield playfield) {
		assert playfield != null : "Playfield not found!";
		this.playfield = playfield;
			// Set MovingSpot at its start position at [0,0]
		playfield.cells[0][0].putSpot();
	}
	
	
	public void setLocation(int newX, int newY) {
		assert newY >= 0 && newX >= 0 : "NewX and newY have to be larger than 0! (NewX,NewY = " + newX + "," + newY + ")";
		assert newY < playfield.width && newX < playfield.height : "NewX and newY are not within the height and width of the field! (NewX,NewY = " + newX + "," + newY + ")";
		
			// Update cells
		playfield.cells[x][y].removeSpot();
		x = newX;
		y = newY;
		playfield.cells[x][y].putSpot();
	}
	
	public GoalSpot pickTarget(GoalSpot[] allGoals) {
		GoalSpot target = null;
		PossibleTarget[] possibleTargets = new PossibleTarget[allGoals.length];
		
		
		
		
		for(int i = 0; i < allGoals.length; i ++) {
			GoalSpot current = allGoals[i];
			possibleTargets[i] = new PossibleTarget(current);
			PossibleTarget possible = possibleTargets[i];
			int possibleX = current.getX() - this.x;
			int possibleY = current.getY() - this.y;
			int dangerCost = 0;
			int highestDanger = 0;
			int calculatedCost = 0;
			/*
			 * CLEANED UP QUICKEST DANGER PATH COST THING HERE! 
			 */
			
			double penalty = 1;
			if(current.getX()==0 || current.getX()==playfield.width-1 
					|| current.getY()==playfield.height || current.getY()==0) {
				penalty = 1.6;
			}
			possible.setHighestDanger(highestDanger);
			possible.setTPD(current.getTPD());
			possible.setSD(current.getSD());
			possible.setPenalty(penalty);
			possible.setCalcCost(calculatedCost);
			possible.setSurThreat(current.calculateSurround());
		}
		double TPDrating = 0.5;
		double SurRating = 1;
		double SDRating = 0.5;
		double CalcRating = 1;
		double HDRating = 1;
		
		
		
		return target;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public String toString() {
		return "[" + this.x + "," + this.y + "]";
	}
}
