package seg.project.checkers;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;
/**
 *	This class will simulate the checker board, and pieces on the board. It will handle all the verification of the moves, and performing the updates to the pieces. 
 *
 */
public class CheckerBoard {
  private CheckerSquare[][] grid;
  private ArrayList<CheckerSquare> redPieces;
  private ArrayList<CheckerSquare> blackPieces;
  private CheckerSquare selectedPiece;
  private boolean pieceJumped;
  
  /**
   * The Constructor will creates the Checker Board 8x8 and add the red and
   * black pieces in the default checker positions
   */
  public CheckerBoard() {
    redPieces = new ArrayList<CheckerSquare>(12);
    blackPieces = new ArrayList<CheckerSquare>(12);
    grid = new CheckerSquare[8][8];
    pieceJumped = false;
    //add new red and black piece at every consecutive space in the 8x8 board to initialize the game board
    for (int i = 0; i < 8; i++) {
      for (int u = 0; u < 8; u++) {
        if (i % 2 == 0) {
          if (u % 2 != 0)
            if (i >= 5) {
            grid[i][u] = new CheckerSquare(i, u, false);
            redPieces.add(grid[i][u]);
          } else if (i < 3) {
            grid[i][u] = new CheckerSquare(i, u, true);
            blackPieces.add(grid[i][u]);
            
          }
        }
        
        else {
          if (u % 2 == 0) {
            if (i >= 5) {
              grid[i][u] = new CheckerSquare( i, u, false);
              redPieces.add(grid[i][u]);
            } else if (i < 3) {
              grid[i][u] = new CheckerSquare( i, u, true);
              blackPieces.add(grid[i][u]);
            }
          }
        }
      }
    }
  }
  
  /**
   * this method performs the other player's moves
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
      // the old x and y is piece is null then its not a valid move
      if (square == null)
        return false;
      //the square moving to is block by other player's piece then its not a valid move
      if ((black && !square.isBlack()) || (!black && square.isBlack()))
        return false;
      
      grid[newX][newY] = square;//update the move and place the piece into the new empty square
      square.setxPos(newX);// set the new piece x coordinate
      square.setyPos(newY);// set the new piece y coordinate
      square.setPieceSelected(false);// de-select the piece
      this.crownKing(newX, newY);//check if the new piece is a king or not
      grid[oldX][oldY] = null;// remove the piece from old square
      return true;
    }
    // check to see the move is valid jump.
    if (isValidjump(oldX, oldY, newX, newY)) {
      CheckerSquare square = grid[oldX][oldY];// saves the selected piece in new CheckerSqaure object
      //not valid jump when the old piece object found null
      if (square == null)
        return false;
      //the square jumping to is block by other player's piece then its not a valid jump move
      if ((black && !square.isBlack()) || (!black && square.isBlack()))
        return false;
      
      int dX = sign(newX - oldX);//the x coordinate for the piece to remove after a valid jump
      int dY = sign(newY - oldY);//the y coordinate for the piece to remove after a valid jump
      CheckerSquare toRem = grid[oldX + dX][oldY + dY];// creates object of the CheckerSquare to be remove piece after a valid jump
      //if the piece made the jump is black then remove the red piece otherwise remove the black piece
      if (square.isBlack())
        redPieces.remove(toRem);
      else
        blackPieces.remove(toRem);
      grid[oldX + dX][oldY + dY] = null;// Jump piece
      grid[oldX + 2 * dX][oldY + 2 * dY] = square; // move square
      square.setxPos(oldX + 2 * dX);// move square x coordinate
      square.setyPos(oldY + 2 * dY);// move square y coordinate
      square.setPieceSelected(false);// move square piece de-selected
      this.crownKing(oldX + 2 * dX, oldY + 2 * dY);//crown king if the piece is needed
      grid[oldX][oldY] = null;// set old square to null
      return true;
    }
    return false;
  }
  
  /**
   * the method will perform the moves for your pieces 
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
    //initialize the oldX and oldY coordinate
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
      // the square is null update the message
      if (square == null) {
        notify("There is no piece at location X-" + x + " Y-" + y);
        return;
      }
      // if the player clicked on other player's piece then give a message warning
      if ((black && !square.isBlack()) || (!black && square.isBlack())) {
        notify("That is not your piece at location X-" + x + " Y-" + y);
        return;
      }
      
      square.setPieceSelected(!square.isPieceSelected());//set square piece selected
      //if the selected piece is null then square is equals selected piece otherwise wait for the player to make the next jump
      if (selectedPiece == null)
        selectedPiece = square;
      else {
        selectedPiece = null;
        //once the player made the second jump where it exist then update that the player is 'done' message 
        if (pieceJumped) {
          pieceJumped = false;
          CheckerGame.getInstance().sendCommand("done");
        }
      }
      notify(null);
      return;
    } else if (isValidNonjump(oldX, oldY, x, y)) {
      //if player can make an jump he must
      if (canJump(black)) {
        notify("You must make a jump, since it is possible.");
        return;
      }
      CheckerSquare square = grid[oldX][oldY];// set the new square with oldX and oldY coordinates
      // the square is null then update that it is other player's piece
      if (square == null) {
        notify("Invalid selected piece at X-" + oldX + " Y-" + oldY);
        return;
      }
      //if the player's piece not seleceted then update with message
      if ((black && !square.isBlack()) || (!black && square.isBlack())) {
        notify("It is not your piece selected at X-" + oldX + " Y-"
                 + oldY);
        return;
      }
      grid[x][y] = square;
      int ox = square.getxPos(), oy = square.getyPos();
      square.setxPos(x);// set the square with new x coordinate
      square.setyPos(y);// set the square with new x coordinate
      square.setPieceSelected(false);// de-select the square piece
      selectedPiece = null;
      this.crownKing(x, y);// crown king the square piece if needed
      grid[oldX][oldY] = null;
      CheckerGame.getInstance().sendCommand(
                                            "move:" + ox + ":" + oy + ":" + x + ":" + y);//send the player moves to update the commands
      CheckerGame.getInstance().sendCommand("done");// send done after player finish his/her move
      notify("You made move from X-" + ox + " Y-" + oy + " to X-" + x
               + " Y-" + y);// updates the moves message
      return;
    } else if (isValidjump(oldX, oldY, x, y)) {//check for valid jump move
      CheckerSquare square = grid[oldX][oldY];// create a sqaure object with old x and y  coordiantes
      //if the square is null then update the message
      if (square == null) {
        notify("Invalid selected piece at X-" + oldX + " Y-" + oldY);
        return;
      }
      //if the player not selecet his/her own piece update the message
      if ((black && !square.isBlack()) || (!black && square.isBlack())) {
        notify("It is not your piece selected at X-" + oldX + " Y-"
                 + oldY);
        return;
      }
      
      int dX = sign(x - oldX);//
      int dY = sign(y - oldY);
      int ox = oldX, oy = oldY;
      CheckerSquare toRem = grid[oldX + dX][oldY + dY];// creates the square needed to be remove
      //if the square is black then remove the red piece otherwise remove the black piece
      if (square.isBlack())
        redPieces.remove(toRem);
      else
        blackPieces.remove(toRem);
      grid[oldX + dX][oldY + dY] = null;// Jump piece
      grid[oldX + 2 * dX][oldY + 2 * dY] = square; // move square
      square.setxPos(oldX + 2 * dX);//set the move square x coordinate
      square.setyPos(oldY + 2 * dY);//set the move square y coordinate
      this.crownKing(oldX + 2 * dX, oldY + 2 * dY);// check if the move square needs to change to king piece
      grid[oldX][oldY] = null;// set the before jump square to null
      //check after a jump is it possible to make a another jump
      if (canJump(square)) {
        pieceJumped = true;
        notify("You made jump from X-" + ox + " Y-" + oy + " to X-" + x
                 + " Y-" + y + " it is still your turn");// updates the message with the player moves
        // updates the command
        CheckerGame.getInstance().sendCommand(
                                              "move:" + ox + ":" + oy + ":" + x + ":" + y);
        return;
      } else //if cannot make another jump after 1st jump set the piece to de-select
        pieceJumped = false;
      square.setPieceSelected(false);
      selectedPiece = null;
      CheckerGame.getInstance().sendCommand(
                                            "move:" + ox + ":" + oy + ":" + x + ":" + y);// send the command
      CheckerGame.getInstance().sendCommand("done");// end the player's turn by updating the command with 'done'
      notify("You made jump from X-" + ox + " Y-" + oy + " to X-" + x
               + " Y-" + y);// updates the message
      //check if black wins otherwise red wins. whoever win display the result in a dialog message and exit the game
      if (win(black)) {
        JOptionPane.showMessageDialog(null, "You won!");
        System.exit(0);
      }
      return;
    }
    notify("Invalid move at X-" + x + " Y-" + y);// update the message if an invalid moves made
    return;
  }
  
  /**
   * this method is check to see can a player make a jump
   * 
   * @param black
   *            is the colour of the player
   * @return true if the player can make a jump otherwise false
   */
  public boolean canJump(boolean black) {
    boolean can = false;
    //if its a black turn check to see the player can make a jump
    if (black) {
      Iterator<CheckerSquare> iter = blackPieces.iterator();
      while (iter.hasNext() && !can) {
        can = canJump(iter.next());
      }
    } else {// check to see if red turn player can make a jump
      Iterator<CheckerSquare> iter = redPieces.iterator();
      while (iter.hasNext() && !can) {
        can = canJump(iter.next());
      }
    }
    return can;// return true if black or red can make an jump otherwise return false
  }
  
  /**
   * this method is check if a specific piece can jump or not
   * 
   * @param piece
   *            the selected piece by the player
   * @return true if the jump is valid otherwise is false.
   */
  public boolean canJump(CheckerSquare piece) {
    //check to see the next jump is a valid jump
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
   * @param black
   *            the colour of the player, true if the player is black, false if red
   * @return true if the the player with the colour passed won the game
   */
  public boolean win(boolean black) {
    //check if black piece in the borad is empty then return true which mean red won
    if (blackPieces.isEmpty() && !black)
      return true;
    // check if red piece in the borad is empty then return true which mean black won
    else if (redPieces.isEmpty() && black)
      return true;
    return false;// return false if non of the color player win
    
  }
  
  /**
   * this method is check to see if the player made a valid jump move
   * 
   * @param oldX
   *            the player's selected piece old x coordinate
   * @param oldY
   *            the player's selected piece old y coordinate
   * @param newX
   *            the player's selected piece new x coordinate
   * @param newY
   *            the player's selected piece new y coordinate
   * @return true if the move made by the player is valid otherwise return
   *         false
   */
  private boolean isValidjump(int oldX, int oldY, int newX, int newY) {
    int xPos = newX - oldX;
    int yPos = newY - oldY;
    try {
      CheckerSquare square = grid[oldX][oldY];
      //the old x and y piece is null then not valid move 
      if (square == null)
        return false;
      
      //if the move is not a move to an adjacent row and column,
      int absX = Math.abs(xPos);
      int absY = Math.abs(yPos);
      
      // Return false if the move is not a move to an adjacent row and column,
      if (absX != absY)
        return false;
      if (absX > 2)
        return false;
      if (absY > 2)
        return false;
      
      // Set jumpSquare to the square between oldSq and newSq --remember
      // that the rows and cols differ by an absolute value of 2
      CheckerSquare jumpSquare = grid[oldX + sign(xPos)][oldY
                                                           + sign(yPos)];
      
      CheckerSquare landSquare = grid[oldX + 2 * sign(xPos)][oldY + 2
                                                               * sign(yPos)];
      //if the jump square is null then its not a valid jump movie so retrun false 
      if (jumpSquare == null)
        return false;
      //if the square is jumping to blocked by the piece then its not a valid jump so return false
      if (square.isBlack() == jumpSquare.isBlack())
        return false;
      
      boolean king = square.isKing();// check the square is king if is king return true otherwise false
      boolean invalidRedMove = ((!grid[oldX][oldY].isBlack()) && xPos > 0);// the red invalid move
      boolean invalidBlackMove = grid[oldX][oldY].isBlack() && xPos < 0;// the black invalid move
      //if the piece is not king and invalid red or black more then its not a valid jump so return false
      if (!king && (invalidRedMove || invalidBlackMove))
        return false;
      
      //if landing square after jump found null then return false otherwise true
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
