package seg.project.checkers;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

public class CheckerBoard {
	private CheckerSquare[][] grid;
	private ArrayList<CheckerSquare> redPieces;
	private ArrayList<CheckerSquare> blackPieces;
	private CheckerSquare selectedPiece;
	private boolean pieceJumped;

	/**
	 * The Constructor will creates the Checker Board 8x8 and add the red and
	 * black pieces
	 */
	public CheckerBoard() {
		redPieces = new ArrayList<CheckerSquare>(12);
		blackPieces = new ArrayList<CheckerSquare>(12);
		grid = new CheckerSquare[8][8];
		pieceJumped = false;
		for (int i = 0; i < 8; i++) {
			for (int u = 0; u < 8; u++) {
				if (i % 2 == 0) {
					if (u % 2 != 0)
						if (i >= 5) {
							grid[i][u] = new CheckerSquare(this, i, u, false);
							redPieces.add(grid[i][u]);
						} else if (i < 3) {
							grid[i][u] = new CheckerSquare(this, i, u, true);
							blackPieces.add(grid[i][u]);

						}
				}

				else {
					if (u % 2 == 0) {
						if (i >= 5) {
							grid[i][u] = new CheckerSquare(this, i, u, false);
							redPieces.add(grid[i][u]);
						} else if (i < 3) {
							grid[i][u] = new CheckerSquare(this, i, u, true);
							blackPieces.add(grid[i][u]);
						}
					}
				}
			}
		}
	}

	/**
	 * this method check to see if the move made by other player (red) is valid
	 * or not
	 * 
	 * @param oldX
	 *            the player's piece old x coordinate
	 * @param oldY
	 *            the player'spiece old y coordinate
	 * @param newX
	 *            the player's piece new x coordinate
	 * @param newY
	 *            the player's piece new y coordinate
	 * @return this will return true if the move is valid otherwise return false
	 */
	public boolean validateMove(int oldX, int oldY, int newX, int newY) {
		boolean black = !CheckerGame.getInstance().isBlack();// true for black
																// piece player
																// otherwise red
																// piece player
		// check to see the move is valid non jump
		if (isValidNonjump(oldX, oldY, newX, newY)) {
			if (canJump(black))
				return false;
			CheckerSquare square = grid[oldX][oldY];
			if (square == null)
				return false;
			if ((black && !square.isBlack()) || (!black && square.isBlack()))
				return false;

			grid[newX][newY] = square;
			square.setxPos(newX);
			square.setyPos(newY);
			square.setPieceSelected(false);
			this.crownKing(newX, newY);
			grid[oldX][oldY] = null;
			return true;
		}
		// check to see the move is valid jump.
		if (isValidjump(oldX, oldY, newX, newY)) {
			CheckerSquare square = grid[oldX][oldY];
			if (square == null)
				return false;
			if ((black && !square.isBlack()) || (!black && square.isBlack()))
				return false;
			int dX = sign(newX - oldX);
			int dY = sign(newY - oldY);
			CheckerSquare toRem = grid[oldX + dX][oldY + dY];
			if (square.isBlack())
				redPieces.remove(toRem);
			else
				blackPieces.remove(toRem);
			grid[oldX + dX][oldY + dY] = null;// Jump piece
			grid[oldX + 2 * dX][oldY + 2 * dY] = square; // move square
			square.setxPos(oldX + 2 * dX);
			square.setyPos(oldY + 2 * dY);
			square.setPieceSelected(false);
			this.crownKing(oldX + 2 * dX, oldY + 2 * dY);
			grid[oldX][oldY] = null;
			return true;
		}
		return false;
	}

	/**
	 * the method will perform the moves for the black's piece player
	 * 
	 * @param x
	 *            the piece's x coordinate
	 * @param y
	 *            the piece's y coordinate
	 */
	public void performMove(int x, int y) {
		// check for who's turn if not player turn it will display the message
		if (!CheckerGame.getInstance().isTurn()) {
			notify("It is not your turn, please wait");
			return;
		}
		int oldX = 0;
		int oldY = 0;
		// once the piece selected it copy the x and y coordinates of the piece
		if (selectedPiece != null) {
			oldX = selectedPiece.getxPos();
			oldY = selectedPiece.getyPos();
		}
		boolean black = CheckerGame.getInstance().isBlack();

		if (selectedPiece == null
				|| (selectedPiece != null && oldX == x && oldY == y)) {
			CheckerSquare square = grid[x][y];
			if (square == null) {
				notify("There is no piece at location X-" + x + " Y-" + y);
				return;
			}
			if ((black && !square.isBlack()) || (!black && square.isBlack())) {
				notify("That is not your piece at location X-" + x + " Y-" + y);
				return;
			}
			square.setPieceSelected(!square.isPieceSelected());
			if (selectedPiece == null)
				selectedPiece = square;
			else {
				selectedPiece = null;
				if (pieceJumped) {
					pieceJumped = false;
					CheckerGame.getInstance().sendCommand("done");
				}
			}
			notify(null);
			return;
		} else if (isValidNonjump(oldX, oldY, x, y)) {
			if (canJump(black)) {
				notify("You must make a jump, since it is possible.");
				return;
			}
			CheckerSquare square = grid[oldX][oldY];
			if (square == null) {
				notify("Invalid selected piece at X-" + oldX + " Y-" + oldY);
				return;
			}
			if ((black && !square.isBlack()) || (!black && square.isBlack())) {
				notify("It is not your piece selected at X-" + oldX + " Y-"
						+ oldY);
				return;
			}
			grid[x][y] = square;
			int ox = square.getxPos(), oy = square.getyPos();
			square.setxPos(x);
			square.setyPos(y);
			square.setPieceSelected(false);
			selectedPiece = null;
			this.crownKing(x, y);
			grid[oldX][oldY] = null;
			CheckerGame.getInstance().sendCommand(
					"move:" + ox + ":" + oy + ":" + x + ":" + y);
			CheckerGame.getInstance().sendCommand("done");
			notify("You made move from X-" + ox + " Y-" + oy + " to X-" + x
					+ " Y-" + y);
			return;
		} else if (isValidjump(oldX, oldY, x, y)) {
			CheckerSquare square = grid[oldX][oldY];
			if (square == null) {
				notify("Invalid selected piece at X-" + oldX + " Y-" + oldY);
				return;
			}
			if ((black && !square.isBlack()) || (!black && square.isBlack())) {
				notify("It is not your piece selected at X-" + oldX + " Y-"
						+ oldY);
				return;
			}
			int dX = sign(x - oldX);
			int dY = sign(y - oldY);
			int ox = oldX, oy = oldY;
			CheckerSquare toRem = grid[oldX + dX][oldY + dY];
			if (square.isBlack())
				redPieces.remove(toRem);
			else
				blackPieces.remove(toRem);
			grid[oldX + dX][oldY + dY] = null;// Jump piece
			grid[oldX + 2 * dX][oldY + 2 * dY] = square; // move square
			square.setxPos(oldX + 2 * dX);
			square.setyPos(oldY + 2 * dY);
			this.crownKing(oldX + 2 * dX, oldY + 2 * dY);
			grid[oldX][oldY] = null;
			if (canJump(square)) {
				pieceJumped = true;
				notify("You made jump from X-" + ox + " Y-" + oy + " to X-" + x
						+ " Y-" + y + " it is still your turn");
				CheckerGame.getInstance().sendCommand(
						"move:" + ox + ":" + oy + ":" + x + ":" + y);
				return;
			} else
				pieceJumped = false;
			square.setPieceSelected(false);
			selectedPiece = null;
			CheckerGame.getInstance().sendCommand(
					"move:" + ox + ":" + oy + ":" + x + ":" + y);
			CheckerGame.getInstance().sendCommand("done");
			notify("You made jump from X-" + ox + " Y-" + oy + " to X-" + x
					+ " Y-" + y);
			if (win(black)) {
				JOptionPane.showMessageDialog(null, "You won!");
				System.exit(0);
			}
			return;
		}
		notify("Invalid move at X-" + x + " Y-" + y);
		return;
	}

	/**
	 * this method is check to see can a player make double jump
	 * 
	 * @param black
	 *            is the player
	 * @return true if the player can make a double jump otherwise false
	 */
	public boolean canJump(boolean black) {
		boolean can = false;
		if (black) {
			Iterator<CheckerSquare> iter = blackPieces.iterator();
			while (iter.hasNext() && !can) {
				can = canJump(iter.next());
			}
		} else {
			Iterator<CheckerSquare> iter = redPieces.iterator();
			while (iter.hasNext() && !can) {
				can = canJump(iter.next());
			}
		}
		return can;
	}

	/**
	 * this method is check to see the double jump is valid or not
	 * 
	 * @param piece
	 *            the selected piece by the player
	 * @return true if the double jump is valid otherwise is false.
	 */
	public boolean canJump(CheckerSquare piece) {
		if (this.isValidjump(piece.getxPos(), piece.getyPos(),
				piece.getxPos() + 1, piece.getyPos() + 1))
			return true;
		else if (this.isValidjump(piece.getxPos(), piece.getyPos(),
				piece.getxPos() + 1, piece.getyPos() - 1))
			return true;
		else if (this.isValidjump(piece.getxPos(), piece.getyPos(),
				piece.getxPos() - 1, piece.getyPos() + 1))
			return true;
		else if (this.isValidjump(piece.getxPos(), piece.getyPos(),
				piece.getxPos() - 1, piece.getyPos() - 1))
			return true;
		return false;
	}

	/**
	 * this method is to copy the black pieces to an arraylist
	 * 
	 * @return the black pieces arraylist
	 */
	public ArrayList<CheckerSquare> getBlackPieces() {
		return blackPieces;
	}

	/**
	 * this method is to copy the red pieces to an arraylist
	 * 
	 * @return the red pieces arraylist
	 */
	public ArrayList<CheckerSquare> getRedPieces() {
		return redPieces;
	}

	/**
	 * this metod is to get the checker game board grid
	 * 
	 * @return the checker board grid
	 */
	public CheckerSquare[][] getGrid() {
		// TODO Auto-generated method stub
		return grid;
	}

	/**
	 * this method is to check see who win the game
	 * 
	 * @param black the black piece player
	 * @return true if the black piece player wins or red piece player wins otherwise return false
	 */
	public boolean win(boolean black) {
		if (blackPieces.isEmpty() && !black)
			return true;
		else if (redPieces.isEmpty() && black)
			return true;
		return false;

	}

	/**
	 * this method is check to see if the player made a valid jump move
	 * 
	 * @param oldX the player's selected piece old x coordinate
	 * @param oldY the player's selected piece old y coordinate
	 * @param newX  the player's selected piece new x coordinate
	 * @param newY the player's selected piece new y coordinate
	 * @return true if the move made by the player is valid otherwise return false
	 */
	private boolean isValidjump(int oldX, int oldY, int newX, int newY) {
		int xPos = newX - oldX;
		int yPos = newY - oldY;
		try {
			CheckerSquare square = grid[oldX][oldY];
			//the old x and y piece is null then not valid move 
			if (square == null)
				return false;

			int absX = Math.abs(xPos);
			int absY = Math.abs(yPos);

			if (absX != absY)
				return false;
			if (absX > 2)
				return false;
			if (absY > 2)
				return false;

			CheckerSquare jumpSquare = grid[oldX + sign(xPos)][oldY
					+ sign(yPos)];

			CheckerSquare landSquare = grid[oldX + 2 * sign(xPos)][oldY + 2
					* sign(yPos)];

			if (jumpSquare == null)
				return false;
			if (square.isBlack() == jumpSquare.isBlack())
				return false;

			boolean king = square.isKing();
			boolean invalidRedMove = ((!grid[oldX][oldY].isBlack()) && xPos > 0);
			boolean invalidBlackMove = grid[oldX][oldY].isBlack() && xPos < 0;
			if (!king && (invalidRedMove || invalidBlackMove))
				return false;

			if (landSquare != null)
				return false;
			return (true);
		} catch (java.lang.ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	/**
	 * this method is determine the sign of a given number
	 * 
	 * @param value
	 *            the number been compared
	 * @return 1 for positive value which greater than 0 otherwise return -1 for
	 *         negative value which greater than 0
	 */
	private int sign(int value) {
		// positive value
		if (value > 0)
			return 1;
		// negative value
		if (value < 0)
			return -1;
		return value;
	}

	/**
	 * this method will update the regular player's piece to king which has it
	 * own behaviors
	 * 
	 * @param newX
	 *            the player's piece x coordinate need to be updated
	 * @param newY
	 *            the player's piece y coordinate need to be updated
	 */
	private void crownKing(int newX, int newY) {
		// the selected black player piece is update to king piece 
		if ((newX == 7) && grid[newX][newY].isBlack()) {
			grid[newX][newY].setKing(true);
			return;
		}
		// the selected red player piece is update to king piece 
		if ((newX == 0) && !grid[newX][newY].isBlack()) {
			grid[newX][newY].setKing(true);
			return;
		}
	}

	/**
	 * this method is updates the messages like each players turn, player's
	 * moves, illegal moves, and etc.
	 * 
	 * @param mes
	 *            the given message need to display
	 */
	private void notify(String mes) {
		// update the message if its not null
		if (mes != null) {
			CheckerGame.getInstance().addText(mes);
		}
		CheckerGame.getInstance().notifyObservers(null);
	}

	/**
	 * this method is determine whether the player's move is valid but not a
	 * jump
	 * 
	 * @param oldX
	 *            the player selected piece x coordinates
	 * @param oldY
	 *            the player selected piece y coordinates
	 * @param newX
	 *            the player selected piece x coordinates
	 * @param newY
	 *            the player selected piece y coordinates
	 * @return true if the move is valid but not a jump otherwise return false
	 */
	private boolean isValidNonjump(int oldX, int oldY, int newX, int newY) {
		int xPos = newX - oldX;
		int yPos = newY - oldY;
		try {
			// Return false if the move is not a move to an adjacent row and
			// column,
			if (Math.abs(xPos) != 1)
				return false;
			if (Math.abs(yPos) != 1)
				return false;

			if (grid[oldX][oldY] == null)
				return false;
			if (grid[newX][newY] != null)
				return false;
			// Return true if this is a King
			if (grid[oldX][oldY].isKing())
				return true;

			// The piece is not a king. Return value of the piece moves forward
			return ((!grid[oldX][oldY].isBlack()) && xPos < 0)
					|| (grid[oldX][oldY].isBlack() && xPos > 0);
		} catch (java.lang.ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}
}
