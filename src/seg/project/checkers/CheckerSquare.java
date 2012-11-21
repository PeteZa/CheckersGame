package seg.project.checkers;

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
		return ((xPos == x) && (yPos == y));
	}
	public String getImage(){
		String imageName = "";
		if (selected) {
			if (black) {
				imageName = "data/blackpieceSel.png";
			} else
				imageName = "data/redpieceSel.png";

			if (king) {
				if (black) {
					imageName = "data/blackkingSel.png";
				} else
					imageName = "data/redkingSel.png";
			}
		} else {
			if (black) {
				imageName = "data/blackpiece.png";
			} else
				imageName = "data/redpiece.png";
			if (king) {
				if (black) {
					imageName = "data/blackking.png";
				} else
					imageName = "data/redking.png";
			}	
		}

		return imageName;
	}
	public boolean isKing() {
		return king;
	}
	public void setKing(boolean king) {
		this.king = king;
	}

	public boolean isBlack() {
		return black;
	}
	public void setBlack(boolean black) {
		this.black = black;
	}
	public int getxPos() {
		return xPos;
	}
	public void setxPos(int xPos) {
		this.xPos = xPos;
	}
	public boolean isPieceSelected(){
		return selected;
	}
	public void setPieceSelected(boolean sel){
		selected = sel;
	}
	public int getyPos() {
		return yPos;
	}
	public void setyPos(int yPos) {
		this.yPos = yPos;
	}
	public CheckerBoard getBoard() {
		return board;
	}
	public void setBoard(CheckerBoard board) {
		this.board = board;
	}
}
