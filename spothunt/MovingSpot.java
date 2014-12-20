package spothunt;

import java.util.ArrayList;
import java.util.List;

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
	public static Factor[] factors = new Factor[]{Factor.TPD, Factor.SD, Factor.FDC, Factor.HD};
	
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
	
	/**
	 * <code>pickTarget</code> picks the best <code>GoalSpot</code> based on:<br>
	 * <ul>
	 * 		<li> TPD (Total Player Distance): How far are all players away from the <code>GoalSpot</code>?</li>
	 * 		<li> SD (Spot Distance): How far away is the MovingSpot away from the <code>GoalSpot</code>?</li>
	 * 		<li> FDC (Fastest Danger Cost): How dangerous is the fastest path to the <code>GoalSpot</code>?</li>
	 * 		<li> ST (Surround Threat): How dangerous is the area around the <code>GoalSpot</code>?</li>
	 * 		<li> HD (Highest Danger): What is the highest danger level while moving over the FDC-path to the <code>GoalSpot</code>?</li>
	 * </ul>
	 * @param allGoals	the array of all the GoalSpots on the Playfield
	 * @return target	the GoalSpot that is the best choice to move to
	 */
	public GoalSpot pickTarget(GoalSpot[] allGoals) {
		GoalSpot target = null;
		PossibleTarget[] possibleTargets = new PossibleTarget[allGoals.length];
		
		for(int i = 0; i < allGoals.length; i ++) {
			GoalSpot current = allGoals[i];
			possibleTargets[i] = new PossibleTarget(current);
			PossibleTarget possible = possibleTargets[i];
			int possibleX = current.getX() - this.x;
			int possibleY = current.getY() - this.y;
			int highestDanger = 0;
			int calculatedCost = 0;
			double penalty = 1;
			
			calculatedCost = current.calculateDangerCost(x, y);
			
			if(current.getX()==0 || current.getX()==playfield.width-1 
					|| current.getY()==playfield.height || current.getY()==0) {
				penalty = 1.6;
			}
			
			possible.setHighestDanger(highestDanger);
			possible.setTPD(current.getTPD());
			possible.setSD(current.getSD());
			possible.setPenalty(penalty);
			possible.setCalcCost(calculatedCost);
			
			int weightedSurThreat = (int) (current.calculateSurround() * penalty);
			possible.setSurThreat(weightedSurThreat);
		}
		
		for(Factor current : factors) {
			possibleTargets = rateFactor(possibleTargets, current);	
		}
		
		/*
		 * Further on -- still gotta implement.
		 */
		
		return target;
	}
	
	/**
	 * <code>rateFactor</code> will rate the given <code>factor</code> and will give the correct rating for each of the <code>PossibleTargets</code>.
	 * @param possibleTargets	an array of all the PossibleTargets on the Playfield
	 * @param factor			the name of the factor that will be rated
	 * @return possibleTargets	the updated array of all PossibleTargets with updated ratings
	 */
	public PossibleTarget[] rateFactor(PossibleTarget[] possibleTargets, Factor factor) {
		PossibleTarget best = possibleTargets[0];
		Boolean[] compares = new Boolean[2];
		double rating = (double) factor.getRating();
		List<PossibleTarget> equals = new ArrayList<PossibleTarget>();
		equals.add(best);
			for(int k = 1; k < possibleTargets.length; k++) {
				compares = comparePossibleTargets(factor, possibleTargets[k], best);
				if(compares[0]) {
					for(int j=0; j < equals.size(); j++) {
						equals.get(j).rating = equals.get(j).rating - rating;
					}
					equals.clear();
					best = possibleTargets[k];
					best.rating = best.rating + rating;
					equals.add(best);
				} else if (compares[1]) {
					best = possibleTargets[k];
					best.rating = best.rating + rating;
					equals.add(best);
				}
			}
		return possibleTargets;
	}
	
	/**
	 * <code>comparePossibleTargets</code> is used in <code>rateFactor</code> to compare two <code>PossibleTargets</code>'s factor values.
	 * @param factor	the factor of which the value has to be compared
	 * @param possible	the first PossibleSpot (the one that will be placed before the evaluation symbol)
	 * @param best		the second PossibleSpot (the one that will be placed behind the evaluation symbol)
	 * @return result	an Boolean[2] array. Index 0 will contain true/false for the >/< evaluator and Index 1 will contain the true/false for the == evaluator.
	 */
	public Boolean[] comparePossibleTargets(Factor factor, PossibleTarget possible, PossibleTarget best) {
		Boolean[] result = new Boolean[2];
		
		switch(factor) {
			case TPD:
				result[0] = possible.getTPD() > best.getTPD();
				result[1] = possible.getTPD() == best.getTPD();
				break;
			case ST:
				result[0] = possible.getSurThreat() < best.getSurThreat();
				result[1] = possible.getSurThreat() == best.getSurThreat();
				break;
			case SD:
				result[0] = possible.getSD() < best.getSD();
				result[1] = possible.getSD() == best.getSD();
				break;
			case FDC:
				result[0] =	possible.getCalcCost() < best.getCalcCost();
				result[1] = possible.getCalcCost() == best.getCalcCost();
				break;
			case HD: 
				result[0] = possible.getHighestDanger() < best.getHighestDanger();
				result[1] = possible.getHighestDanger() == best.getHighestDanger();
				break;
		}
		
		return result;
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
