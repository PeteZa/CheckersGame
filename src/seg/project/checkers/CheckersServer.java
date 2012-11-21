package seg.project.checkers;

import java.io.IOException;

import javax.swing.JOptionPane;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class CheckersServer extends AbstractServer {
	public static final int DEFAULT_PORT = 24563;
	private boolean gameMode;
	public CheckersServer(int port) throws IOException {
		super(port);
		this.listen();
		setGameMode(false);
	}
	public CheckersServer() throws IOException{
		super(DEFAULT_PORT);
		this.listen();
	}
	
	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		// TODO Auto-generated method stub
		if(msg instanceof String)
			CheckerGame.getInstance().handleCommand((String)msg);
		
	}
    protected void clientConnected(ConnectionToClient client) {
    	CheckerGame.getInstance().change();
    	CheckerGame.getInstance().notifyObservers(client);
	}
    synchronized protected void clientException(
    	    ConnectionToClient client, Throwable exception) {


    	if(gameMode)
    	{
    		JOptionPane.showMessageDialog(null, "Connection to other player lost, exiting");
			System.exit(0);
    	}
    }
    /*
    synchronized protected void clientDisconnected(
    	    ConnectionToClient client) {
    	if(gameMode)
    	{
    		JOptionPane.showMessageDialog(null, "Connection to other player lost, exiting");
			System.exit(0);
    	}
    }*/
	public boolean isGameMode() {
		return gameMode;
	}
	public void setGameMode(boolean gameMode) {
		this.gameMode = gameMode;
	}
	

}
