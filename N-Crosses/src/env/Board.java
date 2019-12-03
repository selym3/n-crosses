package env;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;

public class Board extends JFrame implements ActionListener {
	private int size;
	private ArrayList<Quadrant> buttons;
	private static Board instance;
	private int[][] WIN_CONDITIONS;
	private boolean gameOver;
	private int alternateTurn;
	private Difficulty difficulty;
	
	private Board() {
		super("N-Crosses");
		size = -1; //nothing has been initialized yet
		gameOver = false;
		alternateTurn = 0;
	}

	public void initBoard(int size, Difficulty diff) {
		if (this.size == -1 && size > 1) {
			this.size = size;
			this.difficulty = diff;
			setSize(300,300);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setResizable(false);
			setLayout(new GridLayout(size,size));
			
			initWinCondition(); //Sets WIN_CONDITIONS to all possible win conditions based on the board size
			initButtons(); //Adds all the buttons to the board
			
			setVisible(true);
		}
	}
	
	private void initWinCondition() {
		WIN_CONDITIONS = new int[8][size]; //8 win conditions with size of the baord
		for (int wi = 0;wi < 8;wi++) {
			for (int i = 0; i < size;i++) {
				switch (wi) { //look away
					case 0:case 1:case 2: //three rows
						WIN_CONDITIONS[wi][i] = i + (size * wi);
					break;
					case 3:case 4:case 5:
						WIN_CONDITIONS[wi][i] = i * size + (wi - 3);
					break;
					case 6:
						WIN_CONDITIONS[wi][i] = i * (size + 1);
					break;
					case 7:
						WIN_CONDITIONS[wi][i] = (size-1) + (i * (size -1));
					break;
				}
			}
		}
	}

	public static Board getInstance() {
		if (instance == null) {
			instance = new Board();
		}
		return instance;
	}
	
	private void initButtons() {
		// add game buttons
		buttons = new ArrayList<Quadrant>();
		for (int n = 0;n < size*size;n++) {
			buttons.add(new Quadrant(this,size,difficulty));
			add(buttons.get(n));
		}
	}

	private boolean hasWon(Player player) {
		for (int[] wc : WIN_CONDITIONS) {
			String[] vals = new String[size];
			for (int i = 0;i < wc.length;i++) {
				vals[i] = buttons.get(wc[i]).getText();
			}
			//HashSet can only have a value once, so when it duplicaates the size maxes out at 1. Check that this value is "X" and not "" or "O"
			Set<String> newVals = new HashSet<String>(Arrays.asList(vals)); //look up HashSet and Arrays in depth
			if (newVals.size() == 1 && newVals.contains(player.toString())) {
				return true;
			}
		}
		
		return false; 
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (!gameOver) {
			Quadrant target = (Quadrant)e.getSource();
			switch (e.getActionCommand()) {
				case "TWO_PLAYER": // i want to put my enum.toString() here but i cant
					if (target.getText().equals("")) {
						if (alternateTurn%2 == 0) {
							target.setText(Player.X.toString());
							if(hasWon(Player.X)) {
								gameOver = true;
							} 
						} else {
							target.setText(Player.O.toString());
							if(hasWon(Player.O)) {
								gameOver = true;
							} 
						}
						alternateTurn++;
					}
				break;
			}
		}
	}
}
