package fr.sgcib.katatennis.scoreboard.pub;

/**
 * Immutable object that stores the scores of a game
 */
public abstract class GameScores {
	public final boolean tieBreak;
	
	public GameScores(boolean tieBreak) {
		this.tieBreak = tieBreak;
	}
}
