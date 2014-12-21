package spothunt;

import java.util.ArrayList;
import java.util.List;

public class GreedySearch implements Search {
	Playfield playfield;
	
	public GreedySearch(Playfield playfield) {
		this.playfield = playfield;
	}

	public List<Cell> findPath(GoalSpot goal, MovingSpot spot) {
		System.out.println("MovingSpot: " + spot.toString());
		System.out.println("GoalSpot: " + goal.toString());

		List<Cell>path = new ArrayList<Cell>();
		int xGoal = goal.getX();
		int yGoal = goal.getY();
		int xSpot = spot.getX();
		int ySpot = spot.getY();
		int xDirection;
		int yDirection;
		int remainingX = Math.abs(goal.getX() - spot.getX());
		int remainingY = Math.abs(goal.getY() - spot.getY());

		System.out.println("RemainingY: " + remainingY);
		System.out.println("RemainingX: " + remainingX);
		
		if(xGoal-xSpot==0) {
			xDirection = 0;
		} else if(xGoal-xSpot>0) {
			xDirection = 1;
		} else {
			xDirection = -1;
		}
		
		if(yGoal-ySpot==0) {
			yDirection = 0;
		} else if(yGoal-ySpot>0) {
			yDirection = 1;
		} else {
			yDirection = -1;
		}
		
		Cell nextMove = nextMove(xSpot, ySpot, xDirection, yDirection, remainingX, remainingY, spot);
		path.add(nextMove);
		//System.out.println(nextMove.toString());
		boolean foundTarget = false;
		while(!foundTarget) {
			remainingX = Math.abs(goal.getX() - nextMove.getX());
			remainingY = Math.abs(goal.getY() - nextMove.getY());
			nextMove = nextMove(path.get(path.size()-1).getX(), path.get(path.size()-1).getY(), xDirection, yDirection, remainingX, remainingY, spot);
			path.add(nextMove);
			if(nextMove.getX()==goal.getX() && nextMove.getY()==goal.getY()) foundTarget = true;
		}
		System.out.println("Path Length: " + path.size());
		for(int i =0; i<path.size(); i++) {
			System.out.println("Step " + (i+1) + ": [" + path.get(i).getX() + ", " + path.get(i).getY() + "]");
		}
		return path;
	}
	
	public Cell nextMove (int x, int y, int xDirection, int yDirection, int remainingX, int remainingY, MovingSpot spot) {
		List<Cell> CellDanger = new ArrayList<Cell>();
		//System.out.println("RemainingY: " + remainingY);
		//System.out.println("RemainingX: " + remainingX);
		
		if(remainingY==0) return playfield.cells[x+xDirection][y];
		else if (remainingX==0) return playfield.cells[x][y+yDirection];
		
		if(y+yDirection<spot.playfield.height && y+yDirection>=0)
			CellDanger.add(0, spot.playfield.cells[x][y+yDirection]);
		if(x+xDirection<spot.playfield.width && x+xDirection>=0)
			CellDanger.add(1, spot.playfield.cells[x+xDirection][y]);
		if(x+xDirection<spot.playfield.width && x+xDirection>=0 && y+yDirection<spot.playfield.height && y+yDirection>=0)
			CellDanger.add(2, spot.playfield.cells[x+xDirection][y+yDirection]);
		
		for(int j=0; j<CellDanger.size();j++) {
			System.out.println("CellDanger of [" + CellDanger.get(j).getX() + ", " + CellDanger.get(j).getY() + "]: " + CellDanger.get(j).getDanger());
		}
		
		
		Cell best = CellDanger.get(0);
		remainingY--;
		String lastSet = "Y";
		for(int k = 1; k<CellDanger.size(); k++) {
			System.out.println("Best: " + best.toString());
				if(CellDanger.get(k)!=null) {
					if(best.getDanger() > CellDanger.get(k).getDanger()) {
						best = CellDanger.get(k);
						switch(lastSet) {
							case "Y": remainingY++; break;
							case "X": remainingX++; break;
							case "XY": remainingX++; remainingY++; break;
						}
						switch(k) {
							case 1: remainingX--; lastSet = "X"; break;
							case 2: remainingY--; remainingX--; lastSet = "XY"; break;
						}
					}
				}
			}
		return best;
	}
	


}
