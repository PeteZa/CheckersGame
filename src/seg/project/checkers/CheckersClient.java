package seg.project.checkers;

import java.io.IOException;

import javax.swing.JOptionPane;

import ocsf.client.AbstractClient;
/**
 *  This is a client the checkers game can use to communicate with the other player
 *
 */
public class CheckersClient extends AbstractClient {
	/**
	 * Basic constructor for the client, it attempts to connect to the server on creation
	 * @param host The host name
	 * @param port the port to connect to
	 * @throws IOException If the connection has a error
	 */
	public CheckersClient(String host, int port) throws IOException {
		super(host, port);
		// TODO Auto-generated constructor stub
		openConnection();
		CheckerGame.getInstance().setBlack(false);
		CheckerGame.getInstance().setTurn(true);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		if(msg instanceof String)
			CheckerGame.getInstance().handleCommand((String)msg);
		
	}
	protected void connectionException(Exception exception) {
		JOptionPane.showMessageDialog(null, "Connection to other player lost, exiting"); // If I loose connection end the game
		System.exit(0);
	}

}
