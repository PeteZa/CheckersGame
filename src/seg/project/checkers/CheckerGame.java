package seg.project.checkers;

import java.io.IOException;
import java.util.Observable;

import javax.swing.JOptionPane;

/**
 * This is a Singleton class for the game, it is observable, and will notify when the board updates
 * 
 */
public class CheckerGame extends Observable {
	private static CheckerGame game;
	
	private CheckerBoard board;
	private String text;
	private boolean turn;
	private boolean black;
	private boolean drawReq;
	private CheckersServer server;
	private CheckersClient client;
	
	private CheckerGame(){
		setBlack(true); // Basic setup
		turn = false;
		setDrawReq(false);
		board = new CheckerBoard();
		text="";
		
	}
	// Getters
	/**
	 * Getter for the board being used by the game
	 * @return The board being currently used
	 */
	public CheckerBoard getBoard(){
		return board;
	}
	/**
	 * So you are able to check who's turn it is
	 * @return If it is the player's turn or not
	 */
	public boolean isTurn(){
		return turn;
	}
	// Turn setter
	/**
	 * Method for changing who's turn it is
	 * @param t If it is the user's turn this will return false
	 */
	public void setTurn(boolean t){
		turn = t;
	}
	/**
	 * Accesses method for the game
	 * @return The checker's game
	 */
	public static CheckerGame getInstance() { // Singleton method
		if(game == null)
			game = new CheckerGame();
		return game;
	}
	/**
	 * The method for handling a command from the other player
	 * @param msg the command sent
	 */
	public void handleCommand(String msg) {
		String [] commands = msg.split(":"); // The different args are split up by a :
		if(commands[0].equals("accept")){
			this.notifyObservers(null);	// Tell the observers something happened
		}
		else if(commands[0].equals("move")){
			if(!board.validateMove(Integer.parseInt(commands[1]), Integer.parseInt(commands[2]), Integer.parseInt(commands[3]), Integer.parseInt(commands[4]))){ // Check to see if it was a valid move
				// If it was invalid, I need to exit
				JOptionPane.showMessageDialog(null,"Other player has made illegal move. Attempted to move from X-" +Integer.parseInt(commands[1]) + " Y-" +Integer.parseInt(commands[2]) + " to X-"+Integer.parseInt(commands[3]) +" Y-"+ Integer.parseInt(commands[4]) );
				this.sendCommand("error:You made illegal move. Attempted to move from X-" +Integer.parseInt(commands[1]) + " Y-" +Integer.parseInt(commands[2]) + " to X-"+Integer.parseInt(commands[3]) +" Y-"+ Integer.parseInt(commands[4]));
				System.exit(0);
			}
			else
				// If it was valid
				this.addText("The other player performed the move  X-" +Integer.parseInt(commands[1]) + " Y-" +Integer.parseInt(commands[2]) + " to X-"+Integer.parseInt(commands[3]) +" Y-"+ Integer.parseInt(commands[4]));
			
		}
		else if(commands[0].equals("error")){
			JOptionPane.showMessageDialog(null, commands[1]); // show message
			System.exit(0);// end game
		}
		else if(commands[0].equals("draw")){ // A draw is being requiested or accepeted
			if(drawReq){ // You have already asked for a draw
				JOptionPane.showMessageDialog(null, "A draw has been accepted");
				this.sendCommand("draw"); // Tell the other user you accept
				System.exit(0);
			}
			else{
				this.addText("The other player requests a draw"); // add text
				this.notifyObservers(null); // update gui
			}
		}
		else if(commands[0].equals("done")){ // if the other user is done
			this.setTurn(true); // it will now be the current user's turn
			this.addText("Your turn"); // add text
			this.notifyObservers(null); // tell the GUI
			if(board.win(!black)){ // Check to see if the user lost, since you can only lose the game when it is not your turn
				JOptionPane.showMessageDialog(null,"You lost!");
				System.exit(0);
			}
		}
		drawReq=false; // reset draw request
	}
	/**
	 * Overriding method for updating gui, since the protected setChanged method needs to be called, and this needs to be called from outside the class 
	 */
	public void notifyObservers(Object o){
		this.setChanged();
		super.notifyObservers(o);
	}
	/**
	 * Send the command to the other player, if both a server and a client exist, the server will send the message and the client is ignored
	 * @param msg the command to send
	 */
	public void sendCommand(String msg){
		Object mes = msg; // make it a object
		if(mes == null) // null check it
			return;
		if(mes.equals("done")) // if it is a done command, set the current turn to be false
			turn = false;
		if(server != null) // check to see if there is a server
			server.sendToAllClients(mes);
		else if(client != null) // check to see if there is a client
			try {
				client.sendToServer(mes);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Connection to other player lost, exiting");
				System.exit(0);
			}
	}
	/**
	 * Get the current server
	 * @return the server being used
	 */
	public CheckersServer getServer() {
		return server;
	}
	/**
	 * So you can set the server to be used
	 * @param server the server you want to use
	 */
	public void setServer(CheckersServer server) {
		this.server = server;
	}
	/**
	 * Get the client being used
	 * @return The current client
	 */
	public CheckersClient getClient() {
		return client;
	}
	/**
	 * set the client you want to use 
	 * @param client the client you want to use
	 */
	public void setClient(CheckersClient client) {
		this.client = client;
	}
	/**
	 * The current console text
	 * @return all the text that was added
	 */
	public String getText() {
		return text;
	}
	/**
	 * Adds a line of text to console
	 * @param text The line to add
	 */
	public void addText(String text) {
		
		this.text = this.text + System.lineSeparator() + text;
	}
	/**
	 * Wipe the text clear and display what colour the player is on the first line
	 */
	public void blankText(){
		String c;
		if(black)
			c = "black";
		else
			c = "red";
		text = "You are " + c;
	}
	/**
	 * Check if the player is black
	 * @return True if the player is black, false if red
	 */
	public boolean isBlack() {
		return black;
	}
	/**
	 * Set the player's colour
	 * @param black  True if the player is black, false if red
	 */
	public void setBlack(boolean black) {
		this.black = black;
	}
	/**
	 * Check if the player requested a draw
	 * @return If a draw was requested
	 */
	public boolean isDrawReq() {
		return drawReq;
	}
	/**
	 * Set if a draw was requested
	 * @param drawReq true if it was requested, false otherwise
	 */
	public void setDrawReq(boolean drawReq) {
		this.drawReq = drawReq;
	}

	
}
