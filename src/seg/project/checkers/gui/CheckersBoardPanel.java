package seg.project.checkers.gui;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import seg.project.checkers.CheckerBoard;
import seg.project.checkers.CheckerGame;
import seg.project.checkers.CheckerSquare;
/**
 * This class will handle rendering of the board and the input from the user
 */
public class CheckersBoardPanel extends JPanel  implements ActionListener{

	private CheckerBoard board;

	private static final long serialVersionUID = 1L;
	/**
	 * Constructor that sets up the board with default values
	 */
	public CheckersBoardPanel(){
		board = CheckerGame.getInstance().getBoard();
		this.setLayout(new GridLayout(8, 8)); // Set layout so it renders nicely
		for(int i =0; i < 8;i++){
			for(int u = 0;u<8;u++){
				// Setting up the button that will represent a checker square
				JButton temp = new JButton();
				temp.setBorder(BorderFactory.createEmptyBorder());
				temp.setContentAreaFilled(false);
				temp.setBorderPainted(false);
				temp.addActionListener(this);
				// Conditions to make the board render with the correct images
				if(i%2==0){
					if(u%2==0)
						temp.setIcon(new ImageIcon("data/white.png"));
					else{
						if(i>=5)
							temp.setIcon(new ImageIcon("data/redpiece.png"));
						else if(i <3){
							temp.setIcon(new ImageIcon("data/blackpiece.png"));
						}
						else{
							temp.setIcon(new ImageIcon("data/brown.png"));
						}
					}
				}
				else{
					if(u%2 == 0){
						if(i>=5)
							temp.setIcon(new ImageIcon("data/redpiece.png"));
						else if(i <3){
							temp.setIcon(new ImageIcon("data/blackpiece.png"));
						}
						else{
							temp.setIcon(new ImageIcon("data/brown.png"));
						}
					}
					else	
						temp.setIcon(new ImageIcon("data/white.png"));	
				}
				this.add(temp);
			}
		}
	}
	/**
	 * This will be called when a user clicks on a button, and it will send the move to the board for processing
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton) // make sure the event is a button
		{
			JButton button = (JButton) e.getSource(); // make it a button
			int [] pos = findLoc(button); // find the x and y location on the board
			if(pos != null) // make sure the button was on the board
				board.performMove(pos[0], pos[1]); // tell the board to make the move
		}
	}
	/**
	 * This method is used to re draw all the pieces on the board
	 */
	public void reDraw(){
		CheckerSquare[][] squareGrid = board.getGrid();
		this.removeAll(); // Remove all the old buttons
		this.setLayout(new GridLayout(8, 8));
		for(int i =0; i < 8;i++){ // Loop through the grid
			for(int u = 0;u<8;u++){
				// Button set up
				JButton temp = new JButton(); 
				temp.setBorder(BorderFactory.createEmptyBorder());
				temp.setContentAreaFilled(false);
				temp.setBorderPainted(false);
				temp.addActionListener(this);
				// Adding the images
				if(squareGrid[i][u] != null) // If there is a square on the grid location
					temp.setIcon(new ImageIcon(squareGrid[i][u].getImage()));
				else // if the grid location is empty
					temp.setIcon(getSquareImage(i,u));
				add(temp); // add the button to the board
			}
		}
		this.validate(); // Requirement for re drawing the new board properly
	}
	private ImageIcon getSquareImage(int x, int y){
		// Find the correct empty square for the given x and y posistion
		if(x%2==0){
			if(y%2==0)
				return(new ImageIcon("data/white.png"));
			else
				return (new ImageIcon("data/brown.png"));
		}
		else{
			if(y%2 == 0)
				return (new ImageIcon("data/brown.png"));
			else
				return(new ImageIcon("data/white.png"));
		}	 
	}
	private int[] findLoc(JButton button){
		int [] ret=null;
		// The buttons are added in order when they are added to the board so I can locate the button, the x position will be the number /8 and the y will be %8
		Component[] comps = this.getComponents();
		for(int i = 0; i < comps.length;i++){
			if(comps[i].equals(button)){ 
				ret = new int[2];
				ret[0]=i/8;
				ret[1]=i%8;
				break;
			}
		}
		return ret;// will return null if it did not exist
	}
}
