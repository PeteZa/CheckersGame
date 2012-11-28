package seg.project.checkers.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

import seg.project.checkers.CheckerGame;
/**
 * This is the main GUI for diplaying the board and the console. It observers the checker game for changes and updates if it finds one
 * 
 */
class CheckersFrame extends JFrame implements Observer, ActionListener{
	private static final long serialVersionUID = 1L;
	private JTextArea consoleText;
	private JScrollPane console;
	private JLabel turnIndicator;
	private BorderLayout layout;
	private JCheckBox draw;
	private CheckersBoardPanel board;
	/**
	 * The main constructor for starting a game, it makes itself visible
	 */
	public CheckersFrame(){
		super("Checkers Game");
		CheckerGame game = CheckerGame.getInstance();
		game.addObserver(this);
		// setting up turn indicator
		String info = "Red's Turn"+ new String(new char[18]); // White spaces;
		turnIndicator = new JLabel( info);
		turnIndicator.setFont(new Font("Big", Font.TYPE1_FONT, 20));
		BorderLayout temp = new BorderLayout();
		turnIndicator.setLayout(temp);
		// Setting up layout
		layout = new BorderLayout();
		this.setLayout(layout);
		//Adding console and indicator
		temp.addLayoutComponent(turnIndicator,BorderLayout.EAST );
		this.add(turnIndicator, BorderLayout.NORTH);
		// Making new board
		board = new CheckersBoardPanel();
		this.add(board, BorderLayout.CENTER);
		// GUI code for making the interface look good
		this.setSize(900, 630);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		consoleText = new JTextArea();
		consoleText.setAutoscrolls(true);
		consoleText.setEditable(false);
		consoleText.setColumns(25);
		consoleText.setRows(34);
		consoleText.setLineWrap(true);
		CheckerGame.getInstance().blankText();
		updateText();
		console = new JScrollPane();
		console.setBorder(new LineBorder(Color.black, 1, true));
		console.setViewportView(consoleText);
		console.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		JPanel pane = new JPanel();
		draw = new JCheckBox("Request draw");
		draw.addActionListener(this);
		pane.setLayout(new BorderLayout());
		pane.add(console, BorderLayout.NORTH);
		pane.add(draw, BorderLayout.SOUTH);
		this.add(pane, BorderLayout.EAST);
		this.setVisible(true);
	}
	@Override
	public void update(Observable checkersGame, Object update) {
		if(! (checkersGame instanceof CheckerGame))
			return; // if the format is some how not correct
		updateText();// Update the console text
		board.reDraw(); // update the board data
		changeTurn(); // change the turn indicator
		this.repaint(); // repaint everything
		draw.setEnabled(true); // re enable the request draw button
	}
	/**
	 * Method for updating the console
	 */
	public void updateText(){
		consoleText.setText(CheckerGame.getInstance().getText());
	}
	/**
	 * Update the turn indicator
	 */
	public void changeTurn(){
		// Get if it is the user's turn
		boolean turn = CheckerGame.getInstance().isTurn(); 
		// Check to see what colour the user is
		boolean black = CheckerGame.getInstance().isBlack();
		String info; // what will be put into the indicator
		if( (turn && !black) || (black&&!turn) )
			info = "Red's Turn"+ new String(new char[18]); // White spaces
		else
			info = "Black's Turn"+ new String(new char[17]);
		turnIndicator.setText(info); // set up the new text
	}
	@Override
	public void actionPerformed(ActionEvent ev) {
		if(!(ev.getSource() instanceof JCheckBox)) // make sure it was a checkbox that called this method
			return;
		draw.setEnabled(false); // disable the draw request
		CheckerGame.getInstance().setDrawReq(true); // update the draw
		CheckerGame.getInstance().sendCommand(new String("draw")); // send the request to the other player
	}
}
