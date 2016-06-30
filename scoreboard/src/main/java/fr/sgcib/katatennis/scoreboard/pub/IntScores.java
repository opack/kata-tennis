package fr.sgcib.katatennis.scoreboard.pub;

/**
 * Immutable object that stores the scores of a set
 */
public class IntScores {
	public final int player1;
	public final int player2;
	
	public IntScores(int player1, int player2) {
		this.player1 = player1;
		this.player2 = player2;
	}
}
