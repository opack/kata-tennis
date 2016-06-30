package fr.sgcib.katatennis.scoreboard;

import static fr.sgcib.katatennis.scoreboard.pub.Players.PLAYER_1;
import static fr.sgcib.katatennis.scoreboard.pub.Players.PLAYER_2;

import java.util.ArrayList;
import java.util.List;

import fr.sgcib.katatennis.scoreboard.contexts.ITennisContextLifecycle;
import fr.sgcib.katatennis.scoreboard.contexts.TennisMatchContext;
import fr.sgcib.katatennis.scoreboard.contexts.listener.ITennisEventListener;
import fr.sgcib.katatennis.scoreboard.pub.GameScores;
import fr.sgcib.katatennis.scoreboard.pub.IntScores;
import fr.sgcib.katatennis.scoreboard.pub.Players;

/**
 * Entry class for score board handling.
 * 
 * To begin a new match, just create a new instance of this object.
 * Then, when a player scores, call the {@link #scorePoint(Players)} method.
 * You can retrieve the current score at any time using the various getters.
 * 
 * You can also be notified of game events by registering a listener via
 * the constructor {@link #ScoreBoard(ITennisEventListener...)}.
 * 
 * This class provides some utility methods that act as shortcuts to the
 * current match, set or game to offer some abstraction of the underlying
 * complexity.
 */
public class ScoreBoard implements ITennisContextLifecycle<IntScores> {
	/**
	 * Tracks the score of the match
	 */
	private TennisMatchContext match;
	
	public ScoreBoard() {
		this(null);
	}
	
	public ScoreBoard(ITennisEventListener listener) {
		match = new TennisMatchContext();
		if (listener != null) {
			match.getListeners().add(listener);
		}
	}
	
	public TennisMatchContext getMatch() {
		return match;
	}

	/**
	 * Called when a player scores a point. This method updates
	 * the score for the current game, set and match.
	 */
	@Override
	public boolean scorePoint(Players player) {
		return match.getCurrentSet().getCurrentGame().scorePoint(player);
	}
	
	@Override
	public Players getWinner() {
		return match.getWinner();
	}

	@Override
	public boolean checkFinished() {
		return match.checkFinished();
	}

	@Override
	public IntScores getScore() {
		return match.getScore();
	}
	
	/**
	 * Retrieves the points for each player in the current game.
	 * The points are returned as a String and is aimed for display
	 * purposes. It can contain the string representation of :
	 *  - An enum value of TennisPoints if the game is standard
	 *  - A integer value if the game is a tie-break
	 * @param player
	 */
	public String getCurrentPoints(Players player) {
		return match.getCurrentSet().getCurrentGame().getScore(player).toString();
	}
	
	/**
	 * Returns the points of both players in the current game.
	 * @return The content of the returned object depends on the type of
	 * game. If it's a tie-break, then the scores are Integer
	 * objects ; otherwise, scores are expressed as TennisPoints enum values.
	 */
	@SuppressWarnings("unchecked")
	public <T extends GameScores> T getCurrentPoints() {
		return (T)match.getCurrentSet().getCurrentGame().getScore();
	}
	
	/**
	 * Retrieves the number of sets won by the specified player
	 * @param player
	 * @return
	 */
	public Integer getSetsWon(Players player) {
		return match.getScore(player);
	}
	
	/**
	 * Retrieves the number of sets won by both players
	 * @return
	 */
	public IntScores getSetsWon() {
		return match.getScore();
	}
	
	/**
	 * Retrieves the number of games won by both players in the current set
	 * @return
	 */
	public IntScores getGamesWon() {
		return match.getCurrentSet().getScore();
	}
	
	/**
	 * Retrieves the list of all finished sets results, ordered chronologically.
	 * @return Never null, but may be empty
	 */
	public List<IntScores> getFinishedSets() {
		final List<IntScores> scoresList = new ArrayList<>();
		
		// Put each score of finished sets in the list
		match.getSets()
			.stream()
			.filter(set -> set.isFinished())
			.forEach(set -> scoresList.add(new IntScores(set.getScore(PLAYER_1), set.getScore(PLAYER_2))));
		
		return scoresList;
	}
	
	/**
	 * Indicates if the current game is in deuce
	 * @return
	 */
	public boolean isDeuce() {
		return match.getCurrentSet().getCurrentGame().isDeuce();
	}
	
	/**
	 * Indicates if the current game is a tie-breaker
	 * @return
	 */
	public boolean isTieBreak() {
		return match.getCurrentSet().isTieBreak();
	}
	
	/**
	 * Indicates if the match is finished
	 * @return
	 */
	public boolean isFinished() {
		return match.isFinished();
	}
}
