package spothunt;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * This class takes care of the <code>Playfield</code>.<br>
 * It creates and initalises a playfield, it can add/move <code>Player</code>s and the <code>MovingSpot</code> and it can also set <code>GoalSpot</code>s. It also calculates
 * the new danger scores of the field after each player move.<br>
 * This class also includes the functionalities to show where each player is and the total field (including the danger score, 
 * coordinates and position of the <code>Spot</code>, <code>GoalSpots</code> and <code>Player</code>s). 
 * @author Jeroen
 * @version 1.1
 *
 */
public class Playfield {
	public Cell[][] cells;
	Player[] players;
	GoalSpot[] goals;
	int width = 0;
	int height = 0;
	MovingSpot spot;
	
	/**
	 * The playfield constructor. This will initalise the playfield with a given <code>width</code> and <code>height</code>.<br>
	 * It will create a 2D <code>array</code> (matrix) of the field and then call the function <code>setupPlayfield</code>.<br>
	 * After that is done it will also create a new <code>MovingSpot</code>.
	 * @param width		the width of the field.
	 * @param height	the height of the field.
	 * @param wall		the danger level of the edges of the field.
	 * @since 1.0
	 */
	public Playfield(int width, int height, int wall) {
		assert wall >= 0 : "Wall cannot be a negative number! (wall = " + wall + ")";
		assert width > 0 : "Width has to be larger than 0! (width = " + width + ")";
		assert height > 0: "Height has to be larger than 0! (height = " + height + ")";
		
		this.width = width;
		this.height = height;
			// Create a new 2D array called cells of type Cell.
		cells = new Cell[height][width];
			// Set the playfield up given the width and height.
		setupPlayfield(width, height, wall);
			// Create a new MovingSpot and link it to this playfield.
		spot = new MovingSpot(this, new GreedySearch(this));
	}
	
	/**
	 * <code>setupPlayfield</code> is used for initializing each cell of the 2D <code>array</code> (matrix) 
	 * with the a new instance of the class <code>Cell</code>.
	 * @param width		the width of the field.
	 * @param height	the height of the field.
	 * @param wall		the danger level of the edges of the field.
	 * @since 1.0
	 */
	public void setupPlayfield(int width, int height, int wall) {
			// Create an area for a given height and width
		for(int i = 0; i<height; i++) {
			for(int j = 0; j < width; j++) {
				cells[i][j] = new Cell(i,j);
				// Increases danger of edges of the field by 2.
				if(i==0 || j == 0 || i == height-1|| j == width-1) {
					cells[i][j].increaseDanger(wall);
				}
				
			}
		}
	}
	
	/**
	 * <code>createPlayers</code> makes a <code>array</code> of new instances of the class <code>Player</code>, given an amount of players.
	 * @param playerAmount	the amount of players that will be playing.
	 * @since 1.0
	 */
	public void createPlayers(int playerAmount) {
		assert playerAmount > 0 : "playerAmount has to be larger than 0! (playerAmount = " + playerAmount + ")";
		
			// Initialize the players variable as an array of type Player.
		players = new Player[playerAmount];
		for(int i = 0; i<playerAmount; i++) {
				// Create new players equal to the given player amount.
			players[i] = new Player(this);
		}
	}
	
	/**
	 * <code>movePlayer</code> will move a player to a new position, given an <code>x</code> and <code>y</code> coordinate and which <code>Player</code> to move.
	 * @param player	the player number. This is equal to the array index of player in the Players array.
	 * @param x			the new x coordinate.
	 * @param y			the new y coordinate.
	 * @since 1.0
	 */
	public void movePlayer(int player, int x, int y) {
		assert x >= 0 && x <= height : "x has to be larger or equal to 0 and smaller than the field height! (x = " + x + ")";
		assert y >= 0 && y <= width : "y has to be larger or equal to 0 and smaller than the field width! (y = " + y +")";
		assert player >= 0 && player < players.length : "Player index is out of range! (player = " + player + ")";
		
			// Move the player to a new location.
		players[player].setLocation(x, y);
			// Increase the danger level of that area by 5.
		cells[x][y].increaseDanger(players[player].getDangerLevel());
			// Calculate the new dangers levels of the playfield now that the player has moved.
		calculateDangers(players[player].getDangerLevel(), x,y);
	}
	
	/**
	 * <code>moveSpot</code> will move the spot to a new position, given an <code>x</code> and <code>y</code> coordinate.
	 * @param x the new x coordinate.
	 * @param y the new y coordinate.
	 * @since 1.0
	 */
	public void moveSpot(int x, int y) {
		assert x >= 0 && x <= height : "x has to be larger or equal to 0 and smaller than the field height(" + this.height +")! (x = " + height + ")";
		assert y >= 0 && y <= width : "y has to be larger or equal to 0 and smaller than the field width(" + this.width+ ")! (y = " + width + ")";
		
			// Move the spot to the new location.
		spot.setLocation(x, y);
	}
	
	/**
	 * <code>setGoals</code> is used to create the <code>GoalSpots</code> on the field, given an array of <code>GoalSpots</code>.
	 * @param goals an array that consist of type GoalSpots[] and therefore only contains GoalSpots.
	 * @since 1.0
	 */
	public void setGoals(GoalSpot[] goals) {
		this.goals = goals;
	}
	
	/**
	 * <code>getInformation</code> returns a text-based representation of where every important component is located.<br>
	 * It will first show where all Players are located using the following layout: <code>Player z: [x,y]</code>.<br>
	 * Secondly, it will show where the MovingSpot is located on the field as: <code>Spot: [x,y]</code>.<br>
	 * Lastly, it will show where all the GoalSpots are located on the field as: <code>Goal z: [x,y]</code>.
	 * @since 1.0 
	 */
	public void getInformation() {
			// Show the information for each player
		for(int i = 0; i < players.length; i++) {
			System.out.println("Player " + i + ": " + players[i].toString());
		}
			// Show the information of the moving spot
		System.out.println("Spot: " + spot.toString());
			// Show the information for each goal spot
		for(int i = 0; i < goals.length; i++) {
			System.out.println("Goal "+ i + ": " + goals[i].toString());
		}
	}
	
	/**
	 * Calculates the danger level for a given <code>x</code>,<code>y</code>-coordinate and adjacent coordinates. It also requires a <code>danger</code> level.
	 * @param danger	the danger level of the given x,y-coordinate.
	 * @param x			the x coordinate of the subarea.
	 * @param y			the y coordinate of the subarea.
	 * @since 1.0
	 */
	public void calculateDangers(int danger, int x, int y) {
		assert x >= 0 && x <= height : "x has to be larger or equal to 0 and smaller than the field height(" + this.height +")! (x = " + height + ")";
		assert y >= 0 && y <= width : "y has to be larger or equal to 0 and smaller than the field width(" + this.width+ ")! (y = " + width + ")";
		assert danger > 0 && danger < Integer.MAX_VALUE : "Danger has to be larger than 0 and smaller or equal to the Integer.MAX_Value! (danger = " + danger + ")";
		
			//Initalise xUp and xDown, these variables are needed to check the triangle area above and below the given coordinate.
		int xUp = x;
		int xDown = x;
			// How far do you have to need to go up and down? (Answer: The danger level - 1, as you need to calculate for all but the danger area of given coordinate).
		for(int i=1;i<=danger-1; i++) {
				// move xUp 1 up in the triangle (-1 as if you want to move up in the matrix, you need to lower the value).
			xUp = xUp - 1;
				// Check if xUp is still within the bounds of the playfield.
			if(xUp>=0 && xUp<height) {
					// As this is a triangle: if you move i steps up, you also need to move i to the left and i to the right, hence from j=-i to j=i. 
				for(int j=-i; j<=i; j++) {
						// Check if the y+j value is still within the bounds of the playfield.
					if(y+j >= 0 && y+j < width) cells[xUp][y+j].increaseDanger(danger-i);
				}
			}
				// move xDown 1 down in the triangle (+1 as if you want to move down in the matrix, you need to increase the value).
			xDown = xDown + 1;
				// Check if xDown is still within the bounds of the playfield.
			if(xDown>=0 && xDown<height) {
					// As this is a triangle: if you move i steps up, you also need to move i to the left and i to the right, hence from j=-i to j=i.
				for(int j=-i; j<=i; j++) {
						// Check if the y+j value is still within the bounds of the playfield.
					if(y+j >= 0 && y+j < width) cells[xDown][y+j].increaseDanger(danger-i);
				}
			}
		}
		
			//Initalize yLeft and yRight, these variables are needed to check the triangle area left and right of the given coordinate.
		int yLeft = y;
		int yRight = y;
			// How far do you have to need to go left and right? (Answer: The danger level - 1, as you need to calculate for all but the danger area of given coordinate).
		for(int i=1;i<=danger-1; i++) {
				// move yLeft 1 down in the triangle (-1 as if you want to the left in the matrix, you need to lower the value).
			yLeft = yLeft - 1;
				// Check if yLeft is still within the bounds of the playfield.
			if(yLeft>=0 && yLeft<width) {
					// As this is a triangle: if you move i steps up, you also need to move i to the left and i to the right. 
					// But as you already did the edges of the field, you have make sure you don't do these again. Hence from j=-i+1 to j=i-1.
				for(int j=-i+1; j<=i-1; j++) {
						// Check if the x+j value is still within the bounds of the playfield.
					if(x+j >= 0 && x+j < width) cells[x+j][yLeft].increaseDanger(danger-i);
				}
			}
				// move yRight 1 up in the triangle (+1 as if you want to the right in the matrix, you need to lower the value).
			yRight = yRight + 1;
				// Check if yRight is still within the bounds of the playfield.
			if(yRight>=0 && yRight<width) {
					// As this is a triangle: if you move i steps up, you also need to move i to the left and i to the right. 
					// But as you already did the edges of the field, you have make sure you don't do these again. Hence from j=-i+1 to j=i-1.
				for(int j=-i+1; j<=i-1; j++) {
						// Check if the x+j value is still within the bounds of the playfield.
					if(x+j >= 0 && x+j < width) cells[x+j][yRight].increaseDanger(danger-i);
				}
			}
		}
	}
	
	/**
	 * This shows a text-based representation of the field understandable to users.<br>
	 * Given the options, it will either just show the plain danger levels per area, a very informative view per area 
	 * or the danger levels and important components per area.<br>
	 * Set options 0 will result in the plain danger levels per area, for example:<br>
	 * <pre>
	 * 0	0	0	1
	 * 0	2	0	3
	 * 1	4	2	2
	 * </pre>
	 * Set options 1 will result in a very informative view per area, for example:<br>
	 * <pre>
	 * [0,0]0S[0,1]0	[0,2]0	[0,3]1		where S is the MovingSpot.
	 * [1,0]0	[1,1]2	[1,2]0	[1,3]5P		where P is a Player.
	 * [2,0]1	[2,1]4	[2,2]2	[2,2]2G		where G is a GoalSpot.
	 * </pre>
	 * Set options 2 will result in the danger levels per area and the important components, for example:<br>
	 * <pre>
	 * 0S	0	0	1	where S is the MovingSpot.	
	 * 0	2	0	3P	where P is a Player.
	 * 1	4	2	2G	where G is a GoalSpot.
	 * </pre>
	 * Set options 3 will result in the creation of a GUI instead of a TUI.
	 * @param options the options level. Depending on an integer varying between 0 and (including) 3, it will result a more extensive informative view.
	 * @since 1.1
	 */
	public void showPFValues(int options) {
		assert options >= 0 && options <=3 : "Invalid option, options range between 0 and (including) 3 (options = " + options + ")";
		if(options==3) {
			createGUI();
		} else {
			for(int i = 0; i < height; i++) {
					// Create a new line in the text-based matrix for each height
				StringBuilder matrixline = new StringBuilder();
				for(int j = 0; j < width; j++) {
						// Look at what options is set
					switch(options) {
							// If case 0 (options==0), then only show danger levels.
						case 0: matrixline.append(cells[i][j].getDanger() + "\t"); break;
							// If case 1 (options==1), then show coordinates and indicate if there is a Player (P), 
							// MovingSpot (S) or GoalSpot (G) located at this coordinate.
						case 1: matrixline.append(i + "," + j + "[" + cells[i][j].getDanger() + "]"); 
								if(cells[i][j].hasPlayer()) matrixline.append("P");
								if(cells[i][j].hasSpot()) matrixline.append("S");
								if(cells[i][j].isGoal()) matrixline.append("G");
								matrixline.append("\t");
								break;
							// If case 2 (options==2), then show the danger level and indicate if there is a Player (P),
							// MovingSpot (S) or GoalSpot (G) located at this coordinate.
						case 2: matrixline.append(cells[i][j].getDanger());
								if(cells[i][j].hasPlayer()) matrixline.append("P");
								if(cells[i][j].hasSpot()) matrixline.append("S");
								if(cells[i][j].isGoal()) matrixline.append("G");
								matrixline.append("\t");
								break;
					}
				}
				// Print the text-based matrix.
			System.out.println(matrixline.toString());
			}
		}
	}
	
	public int highestDangerValue() {
		int highestDanger = 5;
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
					// Check ALL the Cells in cells to see what the highest danger is, 
					// if one is higher that will be the new value of highestDanger
				highestDanger = cells[i][j].getDanger() > highestDanger ? cells[i][j].getDanger() : highestDanger; 
			}
		}
		return highestDanger;
	}
	
	/**
	 * Creates the a GUI of the playfield, showing the position of important components and the overall danger level.
	 * @since 1.1
	 */
	public void createGUI(){
			// Create a new matrix, this time of buttons.
		JButton[][] allCells = new JButton[height][width];
			// highestDanger will be used to see what the highest danger score on the field is.
		int highestDanger = highestDangerValue();
			// create new JFrame
		JFrame frame = new JFrame("Show me da danger, mon!");
			// Set some standard stuff
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(height, width));
		frame.setSize(500, 500);
		frame.setVisible(true);
			// create the raster in the GUI
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
					// create a JButton
				allCells[i][j] = new JButton();
					// set the text of the JButton to match the Danger
				allCells[i][j].setText(Integer.toString(cells[i][j].getDanger()));
					// set the background color to a RED/BLACK scale, where RED is dependent on the danger level
				allCells[i][j].setBackground(new Color((255/highestDanger)*cells[i][j].getDanger(),0,0));
					// disable borders of the buttons
				allCells[i][j].setBorderPainted(false);
					// set ALL texts to Bold
				allCells[i][j].setFont(allCells[i][j].getFont().deriveFont(Font.BOLD));
					// Check if the Cell contains a Player or Spot or is a Goal, else make the foreground white
				if(cells[i][j].hasPlayer()) {
					allCells[i][j].setForeground(Color.BLACK);
						// Check if the Player is standing on a Goal
					if(cells[i][j].isGoal()) {
						allCells[i][j].setForeground(Color.ORANGE);
					}
				} else if(cells[i][j].isGoal()) {
					allCells[i][j].setForeground(Color.GREEN);
				} else if(cells[i][j].hasSpot()) {
					allCells[i][j].setForeground(Color.YELLOW);
				} else {
						// If there's nothing special with the system, make the text normal again
					allCells[i][j].setFont(allCells[i][j].getFont().deriveFont(~Font.BOLD));
					allCells[i][j].setForeground(Color.WHITE);
				}
					// Add the button to the frame
				frame.add(allCells[i][j]);
			}
		}
			// Clean up time!
		frame.pack();
	}
}
