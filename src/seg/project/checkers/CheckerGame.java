package seg.project.checkers;

import java.io.IOException;
import java.util.Observable;

import javax.swing.JOptionPane;





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
		setBlack(true);
		turn = false;
		setDrawReq(false);
		board = new CheckerBoard();
		text="";
		
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
		String [] commands = msg.split(":");
		if(commands[0].equals("accept")){
			this.setChanged();
			this.notifyObservers();	
		}
		else if(commands[0].equals("move")){
			if(!board.validateMove(Integer.parseInt(commands[1]), Integer.parseInt(commands[2]), Integer.parseInt(commands[3]), Integer.parseInt(commands[4]))){
				JOptionPane.showMessageDialog(null,"Other player has made illegal move. Attempted to move from X-" +Integer.parseInt(commands[1]) + " Y-" +Integer.parseInt(commands[2]) + " to X-"+Integer.parseInt(commands[3]) +" Y-"+ Integer.parseInt(commands[4]) );
				System.exit(0);
			}
			else
				this.addText("The other player performed the move  X-" +Integer.parseInt(commands[1]) + " Y-" +Integer.parseInt(commands[2]) + " to X-"+Integer.parseInt(commands[3]) +" Y-"+ Integer.parseInt(commands[4]));
			
		}
		else if(commands[0].equals("error")){
			JOptionPane.showMessageDialog(null, commands[1]);
			System.exit(0);
		}
		else if(commands[0].equals("draw")){
			if(drawReq){
				JOptionPane.showMessageDialog(null, "A draw has been accepted");
				System.exit(0);
			}
			else{
				this.addText("The other player requests a draw");
				this.setChanged();
				this.notifyObservers();
			}
		}
		else if(commands[0].equals("done")){
			this.setTurn(true);
			
			this.addText("Your turn");
			this.notifyObservers(null);
			if(board.win(!black)){
				JOptionPane.showMessageDialog(null,"You lost!");
				System.exit(0);
			}
		}
		drawReq=false;
	}
	public void notifyObservers(Object o){
		this.setChanged();
		super.notifyObservers(o);
	}
	public void sendCommand(String msg){
		Object mes = msg;
		if(mes == null)
			return;
		if(mes.equals("done"))
			turn = false;
		if(server != null)
			server.sendToAllClients(mes);
		else if(client != null)
			try {
				client.sendToServer(mes);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Connection to other player lost, exiting");
				System.exit(0);
			}
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
		String c;
		if(black)
			c = "black";
		else
			c = "red";
		text = "You are " + c;
	}
	public boolean isBlack() {
		return black;
	}
	public void setBlack(boolean black) {
		this.black = black;
	}
	public boolean isDrawReq() {
		return drawReq;
	}
	public void setDrawReq(boolean drawReq) {
		this.drawReq = drawReq;
	}

	
}
