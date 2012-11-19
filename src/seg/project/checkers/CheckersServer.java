package seg.project.checkers;

import java.io.IOException;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class CheckersServer extends AbstractServer {
	public static final int DEFAULT_PORT = 24563;
	public CheckersServer(int port) throws IOException {
		super(port);
		this.listen();
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
	public boolean acceptPlayer(String client){
		return false;
	}
/*	protected void clientConnected(ConnectionToClient client) {
		this.stopListening();
	}
	*/

}
