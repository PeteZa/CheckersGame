package seg.project.checkers.testCases;

import seg.project.checkers.CheckerBoard;
import seg.project.checkers.CheckerGame;
import seg.project.checkers.CheckerSquare;

public class TestBoard {

	public static void main(String[] args) {
		// pre setup
		CheckerBoard board = new CheckerBoard();
		CheckerGame.getInstance().setBlack(false);
		CheckerGame.getInstance().blankText();
		CheckerGame.getInstance().setTurn(true);
		CheckerSquare[][] grid = board.getGrid();
		System.out.println("/////////////////////////////////");
		System.out.println("Test case 1: Attemping to move to a invalid posistion");
		System.out.println("/////////////////////////////////");
		// selection of piece
		board.performMove(5, 0);
		// printing
		System.out.println("Before moving");
		print(grid);
		System.out.println("Moving piece at 5,0 to 3,3. Expecting Message 'Invalid move at X-3 Y-3'");
		//making the move
		board.performMove(3, 3);
		print(grid);
		System.out.println(CheckerGame.getInstance().getText());
		// De-selection
		board.performMove(5, 0);
		
		System.out.println();
		System.out.println("/////////////////////////////////");
		System.out.println("Test case 2: Attempting to select the other player's piece");
		System.out.println("/////////////////////////////////");
		System.out.println("Before moving");
		print(grid);
		System.out.println("Seleting piece at 0,1. Expecting Message 'That is not your piece at location X-0 Y-1'");
		board.performMove(0, 1);
		System.out.println(CheckerGame.getInstance().getText());
		
		System.out.println();
		System.out.println("/////////////////////////////////");
		System.out.println("Test case 3: Attempting valid move");
		System.out.println("/////////////////////////////////");
		board.performMove(5, 2);
		System.out.println("Before moving");
		print(grid);
		System.out.println("Moving piece at 5,2 to 4,3. Expecting Message 'Invalid move at X-3 Y-3'");
		board.performMove(4, 3);
		print(grid);
		System.out.println(CheckerGame.getInstance().getText());
		
		System.out.println();
		System.out.println("/////////////////////////////////");
		System.out.println("Test case 4: jumping");
		System.out.println("/////////////////////////////////");
		System.out.println("Back makes move 2,1 to 3,2");
		board.validateMove(2, 1, 3, 2);
		print(grid);
		CheckerGame.getInstance().setTurn(true);
		System.out.println("Jumping piece at 3,2. Expecting Message 'You made jump from X-4 Y-3 to X-3 Y-2'");
		board.performMove(4, 3);
		board.performMove(3, 2);
		print(grid);
		System.out.println(CheckerGame.getInstance().getText());
		
		System.out.println();
		System.out.println("/////////////////////////////////");
		System.out.println("Test case 5: Attempting to make non jump move when move is possible");
		System.out.println("/////////////////////////////////");
		CheckerGame.getInstance().setBlack(true);
		CheckerGame.getInstance().blankText();
		CheckerGame.getInstance().setTurn(true);
		System.out.println("You are now black. \n Before moving");
		print(grid);
		System.out.println("Moving piece from 2,3 to 3,2. Expecting: 'You must make a jump, since it is possible.'");
		board.performMove(2, 3);
		board.performMove(3, 2);
		print(grid);
		System.out.println(CheckerGame.getInstance().getText());
		
		System.out.println();
		System.out.println("/////////////////////////////////");
		System.out.println("Test case 6: Attempting double jump");
		System.out.println("/////////////////////////////////");
		board = new CheckerBoard();
		grid = board.getGrid();
		System.out.println("Before moving, new board set up and you are red");
		// Black set up
		grid[0][3] = null;
		grid[3][0] = new CheckerSquare( 0, 3,true);
		grid[2][5] = null;
		grid[3][4] = new CheckerSquare( 3, 4,true);
		//red set up
		grid[4][3] =  new CheckerSquare( 4, 3,false);
		grid[5][2]=null;
		grid[6][3] = null;
		grid[5][6]=null;
		grid[4][5] = new CheckerSquare(4, 5,false);
		grid[4][7] = new CheckerSquare( 4, 7,false);
		CheckerGame.getInstance().setBlack(false);
		CheckerGame.getInstance().setTurn(true);
		CheckerGame.getInstance().blankText();
		System.out.println("Before moving");
		print(grid);
		System.out.println("After move 4,3 to 2,5, expecting: You made jump from X-4 Y-3 to X-2 Y-5 it is still your turn");
		board.performMove(4, 3);
		board.performMove(2, 5);
		print(grid);
		System.out.println(CheckerGame.getInstance().getText());
		System.out.println("After move 2,5 to 0,3, expecting: You made jump from X-2 Y-5 to X-0 Y-3, and the piece to be made a king");
		board.performMove(0, 3);
		print(grid);
		System.out.println(CheckerGame.getInstance().getText());
		
		System.out.println();
		System.out.println("/////////////////////////////////");
		System.out.println("Test case 7: Attempting to move king the other direction");
		System.out.println("/////////////////////////////////");
		board.validateMove(2, 3, 3, 2);
		CheckerGame.getInstance().setTurn(true);
		System.out.println("Before moving");
		print(grid);
		System.out.println("Attempting move from 0,3 to 1,4, expecting: 'You made move from X-0 Y-3 to X-1 Y-4'");
		board.performMove(0, 3);
		board.performMove(1, 4);
		print(grid);
		System.out.println(CheckerGame.getInstance().getText());
		
	}
	private static void print(CheckerSquare[][] grid)
	{
		System.out.println("  0 1 2 3 4 5 6 7");
		for(int i = 0; i< grid.length;i++){
			System.out.print(i +" " );
			for(int u=0;u<grid[0].length;u++){
				if(grid[i][u]!=null){
					if(grid[i][u].isBlack())
					{
						if(grid[i][u].isKing()){
							System.out.print("BK");
						}
						else{
							System.out.print("B ");
						}
					}
					else{
						if(grid[i][u].isKing()){
							System.out.print("RK");
						}
						else{
							System.out.print("R ");
						}
					}
				}
				else
					System.out.print("0 ");
			}
			System.out.println("");
			
		}
	}

}
