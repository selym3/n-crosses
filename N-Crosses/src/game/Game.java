package game;

import env.Board;
import env.Difficulty;

public class Game {

	public static void main(String[] args) {
		Board.getInstance().initBoard(4,Difficulty.TWO_PLAYER);
	}

}
