package seg.project.checkers;

import java.util.Observable;



public class CheckerGame extends Observable {
	private static CheckerGame game;
	
	private CheckerBoard board;
	private String text;
	private boolean turn;
	private boolean black;
	private CheckersServer server;
	private CheckersClient client;
	
	private CheckerGame(){
		setBlack(true);
		turn = false;
	}
	// Getters
	public CheckerBoard getBoard(){
		return board;
	}
	public boolean isTurn(){
		return turn;
	}
	// Turn setter
	public void setTurn(boolean t){
		turn = t;
	}
	
	public static CheckerGame getInstance() {
		if(game == null)
			game = new CheckerGame();
		return game;
	}
	public void handleCommand(String msg) {
		// TODO Auto-generated method stub
		
	}
	public void sendCommand(String msg){
		
	}
	public CheckersServer getServer() {
		return server;
	}
	public void setServer(CheckersServer server) {
		this.server = server;
	}
	public CheckersClient getClient() {
		return client;
	}
	public void setClient(CheckersClient client) {
		this.client = client;
	}
	public String getText() {
		return text;
	}
	public void addText(String text) {
		this.text = this.text + System.lineSeparator() + text;
	}
	public void blankText(){
		text = "";
	}
	public boolean isBlack() {
		return black;
	}
	public void setBlack(boolean black) {
		this.black = black;
	}
	
}
