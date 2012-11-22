package seg.project.checkers.gui;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import seg.project.checkers.CheckerBoard;
import seg.project.checkers.CheckerGame;
import seg.project.checkers.CheckerSquare;

public class CheckersBoardPanel extends JPanel  implements ActionListener{

	private CheckerBoard board;

	private static final long serialVersionUID = 1L;
	public CheckersBoardPanel(){
		board = CheckerGame.getInstance().getBoard();
		this.setLayout(new GridLayout(8, 8));
		for(int i =0; i < 8;i++){
			for(int u = 0;u<8;u++){
				JButton temp = new JButton();
				temp.setBorder(BorderFactory.createEmptyBorder());
				temp.setContentAreaFilled(false);
				temp.setBorderPainted(false);
				temp.addActionListener(this);
				if(i%2==0){
					if(u%2==0)
						temp.setIcon(new ImageIcon("data/white.png"));
					else{
						if(i>=5)
							temp.setIcon(new ImageIcon("data/redpiece.png"));
						else if(i <3){
							temp.setIcon(new ImageIcon("data/blackpiece.png"));
						}
						else{
							temp.setIcon(new ImageIcon("data/brown.png"));
						}
					}
				}
				else{
					if(u%2 == 0){
						if(i>=5)
							temp.setIcon(new ImageIcon("data/redpiece.png"));
						else if(i <3){
							temp.setIcon(new ImageIcon("data/blackpiece.png"));
						}
						else{
							temp.setIcon(new ImageIcon("data/brown.png"));
						}
					}
					else	
						temp.setIcon(new ImageIcon("data/white.png"));	
				}
				this.add(temp);
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton)
		{
			JButton button = (JButton) e.getSource();
			int [] pos = findLoc(button);
			if(pos != null)
				board.performMove(pos[0], pos[1]);
		}
	}
	
	public void reDraw(){
		CheckerSquare[][] squareGrid = board.getGrid();
		this.removeAll();
		this.setLayout(new GridLayout(8, 8));
		for(int i =0; i < 8;i++){
			for(int u = 0;u<8;u++){
				JButton temp = new JButton();
				temp.setBorder(BorderFactory.createEmptyBorder());
				temp.setContentAreaFilled(false);
				temp.setBorderPainted(false);
				temp.addActionListener(this);
				if(squareGrid[i][u] != null)
					temp.setIcon(new ImageIcon(squareGrid[i][u].getImage()));
				else
					temp.setIcon(getSquareImage(i,u));
				add(temp);
			}
		}
		this.validate();
	}
	private ImageIcon getSquareImage(int x, int y){
		if(x%2==0){
			if(y%2==0)
				return(new ImageIcon("data/white.png"));
			else
				return (new ImageIcon("data/brown.png"));
		}
		else{
			if(y%2 == 0)
				return (new ImageIcon("data/brown.png"));
			else
				return(new ImageIcon("data/white.png"));
		}	 
	}
	private int[] findLoc(JButton button){
		int [] ret=null;
		Component[] comps = this.getComponents();
		for(int i = 0; i < comps.length;i++){
			if(comps[i].equals(button)){
				ret = new int[2];
				ret[0]=i/8;
				ret[1]=i%8;
				break;
			}
		}
		return ret;
	}
}
