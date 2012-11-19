package seg.project.checkers;

import java.io.IOException;

import ocsf.client.AbstractClient;

public class CheckersClient extends AbstractClient {


	public CheckersClient(String host, int port) throws IOException {
		super(host, port);
		// TODO Auto-generated constructor stub
		openConnection();
		this.sendToServer("accept");
		CheckerGame.getInstance().setBlack(false);
		CheckerGame.getInstance().setTurn(true);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		if(msg instanceof String)
			CheckerGame.getInstance().handleCommand((String)msg);
		
	}

}
