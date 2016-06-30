package fr.sgcib.katatennis.scoreboard.pub;

public class StandardGameScores extends GameScores {
	public final TennisPoints player1;
	public final TennisPoints player2;
	
	public StandardGameScores(TennisPoints player1, TennisPoints player2) {
		super(false);
		this.player1 = player1;
		this.player2 = player2;
	}
}
