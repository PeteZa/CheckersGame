package seg.project.checkers;
/**
 * This class will hold all the data each checker piece needs to have.
 * 
 */
public class CheckerSquare {
 private boolean king;
 private boolean black;
 private boolean selected;
 private int xPos;
 private int yPos;


 /**
  * the Constructor will initialize the own individual's square behaviors
  * instances
  * 
  * @param x
  *            the x coordinate of the square
  * @param y
  *            the y coordinate of the square
  * @param isBlack
  *            the square is black or not
  */
 public CheckerSquare(int x, int y,
   boolean isBlack) {
  xPos = x;// set square x coordinate with the argument x
  yPos = y;// set square y coordinate with the argument y
  black = isBlack; // set the square to black with the argument x
  king = false; // set the square king piece to false
  selected = false;// set the square selected to false

 }

 /**
  * the Constructor will initialize the own individual's square behaviors
  * instances
  * 
  * @param x
  *            the x coordinate of the square
  * @param y
  *            the y coordinate of the square
  * @param isBlack
  *            the square is black or not
  * @param isKing
  *            the square is king or not
  * @param isSelected
  *            the square is selected or not
  */
 public CheckerSquare( int x, int y,
   boolean isBlack, boolean isKing, boolean isSelected) {
  xPos = x;// set square x coordinate with the argument x
  yPos = y;// set square y coordinate with the argument y
  black = isBlack;// set the square to black with the argument x
  king = isKing;// set the square king piece to argument isKing
  selected = isSelected;// set the square selected to argument isSelected
 }

 /**
  * the method will get the location where the image for the piece will be. 
  * 
  * @return a location of the image
  */
 public String getImage() {
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

 /**
  * this method is check if the piece is king or not
  * 
  * @return king
  */
 public boolean isKing() {
  return king;
 }

 /**
  * this method is set the king instance of this class by the given argument
  * 
  * @param king
  */
 public void setKing(boolean king) {
  this.king = king;
 }

 /**
  * check whether is black piece or not
  * 
  * @return true if its a black piece otherwise is red piece
  */
 public boolean isBlack() {
  return black;
 }

 /**
  * this method is set the black piece
  * 
  * @param black
  *            the black piece
  */
 public void setBlack(boolean black) {
  this.black = black;
 }

 /**
  * gets the x coordinate of the piece
  * 
  * @return the x coordinate of the piece
  */
 public int getxPos() {
  return xPos;
 }

 /**
  * set the x coordinates of the piece
  * 
  * @param xPos
  *            the x coordinates of the piece
  */
 public void setxPos(int xPos) {
  this.xPos = xPos;
 }

 /**
  * get whether the piece is selected or not
  * 
  * @return true if the piece selected otherwise return false
  */
 public boolean isPieceSelected() {
  return selected;
 }

 /**
  * this method will set whether the piece is selected or not
  * 
  * @param sel
  *            the piece selected or not
  */
 public void setPieceSelected(boolean sel) {
  selected = sel;
 }

 /**
  * gets the y coordinate of the piece
  * 
  * @return the y coordinate of the piece
  */
 public int getyPos() {
  return yPos;
 }

 /**
  * set the x coordinates of the piece
  * 
  * @param xPos
  *            the x coordinates of the piece
  */
 public void setyPos(int yPos) {
  this.yPos = yPos;
 }


}
