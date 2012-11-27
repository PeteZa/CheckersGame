package seg.project.checkers.testCases;

import seg.project.checkers.CheckerBoard;
import seg.project.checkers.CheckerGame;
import seg.project.checkers.CheckerSquare;

public class TestBoard {

	public static void main(String[] args) {
		System.out.println("/////////////////////////////////");
		System.out.println("Test case 1: Attemping to move to a invalid posistion");
		System.out.println("/////////////////////////////////");
		CheckerGame.getInstance().blankText();
		CheckerBoard board = new CheckerBoard();
		CheckerGame.getInstance().setTurn(true);
		board.performMove(0, 1);
		CheckerSquare[][] grid = board.getGrid();
		System.out.println("Before moving");
		print(grid);
		System.out.println("Moving piece at 0,1 to 1,1. Expecting Message Invalid move at X-1 Y-1");
		board.performMove(1, 1);
		print(grid);
		System.out.println(CheckerGame.getInstance().getText());
		board.performMove(0, 1);
		
		System.out.println("/////////////////////////////////");
		System.out.println("Test case 2: Attempting valid move");
		System.out.println("/////////////////////////////////");
		CheckerGame.getInstance().blankText();
		System.out.println("Before moving");
		print(grid);
		System.out.println("Moving from 2,5 to 3,6, expecting You made move from X-2 Y-5 to X-3 Y-6");
		board.performMove(2, 5);
		board.performMove(3, 6);
		print(grid);
		System.out.println(CheckerGame.getInstance().getText());
		
		System.out.println("/////////////////////////////////");
		System.out.println("Test case 3: jumping");
		System.out.println("/////////////////////////////////");
		grid[0][1] = null;
		grid[2][3] = new CheckerSquare(board, 2,3,false);
		System.out.println("Before moving");
		print(grid);
		System.out.println("Attempting jump from 2,3 to 0,1, expecting You made jump from X-2 Y-3 to X-0 Y-1, and the piece to be made a king");
		CheckerGame.getInstance().setBlack(false);
		CheckerGame.getInstance().setTurn(true);
		CheckerGame.getInstance().blankText();
		board.performMove(2, 3);
		board.performMove(0, 1);
		print(grid);
		System.out.println(CheckerGame.getInstance().getText());
		
		System.out.println("/////////////////////////////////");
		System.out.println("Test case 4: Attempting double jump");
		System.out.println("/////////////////////////////////");
		board = new CheckerBoard();
		grid = board.getGrid();
		CheckerGame.getInstance().setTurn(true);
		CheckerGame.getInstance().blankText();
		System.out.println("Before moving");
		grid[0][1]=null;
		grid[2][3] =null;
		grid[3][4]= new CheckerSquare(board,3,4,true);
		grid[4][5] = new CheckerSquare(board,4,5,false); 
		print(grid);
		System.out.println("After move 4,5 to 2,3, expecting: You made jump from X-4 Y-5 to X-2 Y-3 it is still your turn");
		board.performMove(4, 5);
		board.performMove(2, 3);
		print(grid);
		System.out.println(CheckerGame.getInstance().getText());
		System.out.println("After move 2,3 to 0,1, expecting: You made jump from X-2 Y-3 to X-0 Y-1, and the piece to be made a king");
		board.performMove(0, 1);
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
