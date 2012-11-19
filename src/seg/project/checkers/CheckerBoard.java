package seg.project.checkers;

import java.util.ArrayList;


public class CheckerBoard  {
	private CheckerSquare[][] grid; 
	private ArrayList<CheckerSquare> redPieces;
	private ArrayList<CheckerSquare> blackPieces;
	public CheckerBoard(){
		redPieces = new ArrayList<CheckerSquare>(12);
		blackPieces = new ArrayList<CheckerSquare>(12);
		grid = new CheckerSquare[8][8];
		for(int i =0; i < 8;i++){
			for(int u = 0;u<8;u++){
				if(i%2==0){
					if(u%2!=0)
						if(i>=5){
							grid[i][u]= new CheckerSquare(this,i,u,false);
							redPieces.add(grid[i][u]);
						}
						else if(i <3){
							grid[i][u] = new CheckerSquare(this,i,u,true);
							blackPieces.add(grid[i][u]);
							
						}
					}
				
				else{
					if(u%2 == 0){
						if(i>=5){
							grid[i][u]= new CheckerSquare(this,i,u,false);
							redPieces.add(grid[i][u]);
						}else if(i <3){
							grid[i][u] = new CheckerSquare(this,i,u,true);
							blackPieces.add(grid[i][u]);
						}
					}
				}
			}
		}
	}
		/*
		for(int i =0; i < 8;i++){
			for(int u = 0;u<8;u++){
				CheckerSquare temp = new CheckerSquare(this,i,u, false, false);
				temp.setIcon(new ImageIcon(temp.getImage()));
				grid[i][u]= temp;
				add(temp);
			}
		}
		fillGameBoard();
				
	}
	private void fillGameBoard(){
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// I will fill this out
	}
	*/
	public boolean validateMove(int oldX, int oldY, int newX, int newY){
		// Fill me out
		return false;
		/*
		 * Make sure to check:
		 * 1. the move is diagonal
		 * 2. it is only one square if it is a move
		 * 3. a friendly piece is not in the way
		 * 4. If it is a jump
		 * 5. If a piece blocks the jump 
		 * 6. If a piece selects itself, deselect it
		 */
	}
	public boolean performMove(int oldX, int oldY, int newX, int newY){
		// Fill me out
		boolean valid = validateMove(oldX, oldY, newX, newY);
		// Preform all moves
		return false;
		
	}
	public void removePiece(int x, int y){
		// Fill me out
	}
	public ArrayList<CheckerSquare> getBlackPieces() {
		return blackPieces;
	}
	public ArrayList<CheckerSquare> getRedPieces() {
		return redPieces;
	}
	public CheckerSquare[][] getGrid() {
		// TODO Auto-generated method stub
		return grid;
	}
	
	
	
	// Old generation code
	/*
	 * 
	 */
}
