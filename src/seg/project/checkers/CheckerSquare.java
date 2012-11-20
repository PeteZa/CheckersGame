package seg.project.checkers;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class CheckerSquare{
	private boolean king;
	private boolean black;
	private boolean selected;
	private int xPos;
	private int yPos;
	private CheckerBoard board;
	
	public CheckerSquare(CheckerBoard checkerBoard, int x, int y, boolean isBlack){
		xPos =x;
		setBoard(checkerBoard);
		yPos = y;
		
		
		if(!validPosistion(x,y))
				throw new IllegalArgumentException("x posistion " + x+" and y posistion " + y + " is not allowed");
				
		black= isBlack;
		king = false;
		selected = false;
	
	}
	public CheckerSquare(CheckerBoard checkerBoard,int x, int y, boolean isBlack, boolean isKing, boolean isSelected){
		xPos =x;
		setBoard(checkerBoard);
		yPos = y;
		
		
			if(!validPosistion(x,y))
				throw new IllegalArgumentException("x posistion " + x+" and y posistion " + y + " is not allowed");
		
		black= isBlack;
		king = isKing;
		selected = isSelected;
		}
	/**
	 * 
	 * @param x X pos on board
	 * @param y Y pos on board
	 * @return if the position is valid for a piece
	 */
	private boolean validPosistion(int x, int y){
		// Fill me out
		return true; 
	}
	public String getImage(){
		// Fill me
		return "Image location";
		/*
		 * Possible images:
		 * data/white.png
		 * data/brown.png
		 * data/blackpiece.png
		 * data/redpiece.png
		 * data/redking.png
		 * data/blackking.png
		 * data/blackpieceSel.png
		 * data/redpieceSel.png
		 * data/redkingSel.png
		 * data/blackkingSel.png
		 */
	}
	public boolean isKing() {
		return king;
	}
	public void setKing(boolean king) {
		// Fill me
	}

	public boolean isBlack() {
		return black;
	}
	public void setBlack(boolean black) {
		// Fill me
	}
	public int getxPos() {
		return xPos;
	}
	public void setxPos(int xPos) {
		// Fill me
	}
	public boolean isPieceSelected(){
		return selected;
	}
	public void setPieceSelected(boolean sel){
		// Fill me
	}
	public int getyPos() {
		return yPos;
	}
	public void setyPos(int yPos) {
		// Fill me
	}
	public CheckerBoard getBoard() {
		return board;
	}
	public void setBoard(CheckerBoard board) {
		this.board = board;
	}
}
