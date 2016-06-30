package fr.sgcib.katatennis.scoreboard.pub;

public class TieBreakGameScores extends GameScores {
	public final Integer player1;
	public final Integer player2;
	
	public TieBreakGameScores(Integer player1, Integer player2) {
		super(true);
		this.player1 = player1;
		this.player2 = player2;
	}
}
