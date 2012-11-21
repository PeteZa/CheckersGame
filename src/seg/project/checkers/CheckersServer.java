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
		gameMode = false;
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
	public boolean setToGameMode(String client) {
		if(gameMode)
			return false;
		Thread [] connections = this.getClientConnections();
		int index = -1;
		for(int i = 0; i < connections.length; i++){
			if(client.equals(((ConnectionToClient)connections[i]).getInetAddress().toString())){
				index =i;
			}
		}
		if(index == -1){
			return false;
		}
		for(int i = 0; i < connections.length; i++){
			if(i != index){
				try {
					((ConnectionToClient)connections[i]).close();
				} catch (IOException e1) {
					// I don't care if the closing fails, because the connection will be dead anyway
				}
			}
		}
		
		
		this.stopListening();
		this.gameMode = true;
		return true;
	}
	public void killServer(){
		try {
			this.close();
		} catch (IOException e) {
			// don't care if there is an issue
		}
		CheckerGame.getInstance().setServer(null);
	}

}
