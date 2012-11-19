package seg.project.checkers.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

import seg.project.checkers.CheckerGame;


public class CheckersFrame extends JFrame implements Observer{
	private static final long serialVersionUID = 1L;
	private JTextArea consoleText;
	private JScrollPane console;

	private JLabel turnIndicator;
	private BorderLayout layout;
	private CheckersBoardPanel board;
	public CheckersFrame(boolean black){
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
		// Final touches
		this.setSize(900, 620);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		consoleText = new JTextArea();
		consoleText.setAutoscrolls(true);
		consoleText.setEditable(false);
		consoleText.setColumns(25);
		consoleText.setRows(34);
		consoleText.setLineWrap(true);
		consoleText.setText("Welcome");
		console = new JScrollPane();
		console.setBorder(new LineBorder(Color.black, 1, true));
		console.setViewportView(consoleText);
		console.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		this.add(console, BorderLayout.EAST);
	}
	@Override
	public void update(Observable checkersGame, Object update) {
		if(! (checkersGame instanceof CheckerGame))
			return; // if the format is some how not correct
		updateText();
		board.reDraw();
	}
	private void updateText(){
		consoleText.setText(CheckerGame.getInstance().getText());
	}
}
