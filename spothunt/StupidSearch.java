package spothunt;

import java.util.ArrayList;
import java.util.List;

public class StupidSearch implements Search {
	Playfield playfield;
	
	public StupidSearch(Playfield playfield) {
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
		
		
		if(xGoal-xSpot==0) {
			xDirection = 0;
		} else if(xGoal-xSpot>0) {
			xDirection = -1;
		} else {
			xDirection = 1;
		}
		
		if(yGoal-ySpot==0) {
			yDirection = 0;
		} else if(yGoal-ySpot>0) {
			yDirection = -1;
		} else {
			yDirection = 1;
		}
		
		Cell nextMove = nextMove(xSpot, ySpot, xDirection, yDirection, 0, 0, spot);
		path.add(nextMove);
		boolean foundTarget = false;
		while(!foundTarget) {
			nextMove = nextMove(path.get(path.size()-1).getX(), path.get(path.size()-1).getY(), xDirection, yDirection, 0, 0, spot);
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
		if(y+1<spot.playfield.height) CellDanger.add(spot.playfield.cells[x][y+1]);
		if(y-1>=0) CellDanger.add(spot.playfield.cells[x][y-1]);
		if(x+1<spot.playfield.width) CellDanger.add(spot.playfield.cells[x+1][y]);
		if(x-1>=0) CellDanger.add(spot.playfield.cells[x-1][y]);
		if(x-1>=0 && y-1>=0) CellDanger.add(spot.playfield.cells[x-1][y-1]);
		if(x+1<spot.playfield.width && y+1<spot.playfield.height) CellDanger.add(spot.playfield.cells[x+1][y+1]);
		if(x-1>=0 && y+1<spot.playfield.height) CellDanger.add(spot.playfield.cells[x-1][y+1]);
		if(x+1<spot.playfield.width && y-1>=0) CellDanger.add(spot.playfield.cells[x+1][y-1]);
		int picked = (int) (Math.random()*(CellDanger.size()-1));
		Cell next = CellDanger.get(picked);
		return next;
	}
	


}
