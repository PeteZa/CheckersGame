package seg.project.checkers.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.ListModel;

import ocsf.server.ConnectionToClient;

import seg.project.checkers.CheckerGame;
import seg.project.checkers.CheckersClient;
import seg.project.checkers.CheckersServer;

public class ConnecterGUI extends JFrame implements ActionListener, Observer{
	private static final long serialVersionUID = 4L;
	private CheckersServer server;
	private CheckersClient client;
	private JList<String> connectionRequests;
	private JFrame makeConnectionFrame;
	public ConnecterGUI() throws IOException{
		super("Connection creater");
		CheckerGame.getInstance().setServer(new CheckersServer());
		server = CheckerGame.getInstance().getServer();
		JButton makeConnection = new JButton("Connect to other player");
		makeConnection.addActionListener(this);
		connectionRequests = new JList<String>();
		connectionRequests.setToolTipText("This shows all the players who want to start a game with you");
		JButton accept = new JButton("Accept selected player");
		accept.addActionListener(this);
		this.add(connectionRequests, BorderLayout.CENTER);
		this.add(makeConnection, BorderLayout.NORTH);
		this.add(accept, BorderLayout.SOUTH);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if(command.equals("Accept selected player")){
			String req = connectionRequests.getSelectedValue();
			if(req == null)
				return;
			Thread [] temp = server.getClientConnections();
			ConnectionToClient [] connections = (ConnectionToClient[]) temp;
			int index = -1;
			for(int i = 0; i < connections.length; i++){
				if(req.equals(connections[i].getInetAddress().toString())){
					index =i;
				}
			}
			if(index == -1){
				return;
			}
			for(int i = 0; i < connections.length; i++){
				if(i != index){
					try {
						connections[i].close();
					} catch (IOException e1) {
						// I don't care if the closing fails, because the connection will be dead anyway
					}
				}
			}
			this.setVisible(false);
			new CheckersFrame(true);
		}
		else if(command.equals("Connect to other player")){
			setupFrame();
		}
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		
	}
	private void setupFrame(){
		
	}
	public void addConnection(String name){
		String [] connections;
		ListModel<String> temp = connectionRequests.getModel();
		connections = new String[temp.getSize()+1];
		for(int i =0; i < temp.getSize();i++ ){
			connections[i] = temp.getElementAt(i);// only way to get the value, no iterator 
		}
		connections[connections.length-1] = name;
		connectionRequests.setListData(connections);
	}
	public static void main(String[] args) throws IOException{
		ConnecterGUI s = new ConnecterGUI();
		s.setVisible(true);
	}

}
