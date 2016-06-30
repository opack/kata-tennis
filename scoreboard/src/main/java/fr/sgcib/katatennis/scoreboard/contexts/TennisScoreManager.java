package fr.sgcib.katatennis.scoreboard.contexts;

import static fr.sgcib.katatennis.scoreboard.pub.Players.PLAYER_1;
import static fr.sgcib.katatennis.scoreboard.pub.Players.PLAYER_2;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import fr.sgcib.katatennis.scoreboard.pub.Players;

/**
 * Every tennis scoring context (match, set or game) contains some common data.
 * This class contains this basic data which is related to holding
 * score information :
 *  - the current scores
 * 	- the winner.
 * This class has no business logic ; it's a dumb data class.
 * 
 * This class can be parameterized to indicate the type of score that is managed
 * (i.e. : is score an Integer ? TennisPoints ?).
 */
public class TennisScoreManager<ScoreType> {
	/**
	 * Contains the current scores for each player.
	 */
	private Map<Players, ScoreType> scores;
	
	/**
	 * The winner of this context. If not null, it means that the context
	 * (match, set or game) is finished.
	 */
	private Players winner;
	
	public TennisScoreManager(ScoreType initialValue) {
		scores = new HashMap<>();
		setScore(PLAYER_1, initialValue);
		setScore(PLAYER_2, initialValue);
	}
	
	/**
	 * Returns the score for the specified player
	 * @param player
	 * @return
	 */
	public ScoreType getScore(Players player) {
		if (scores == null) {
			throw new IllegalArgumentException("Cannot get score for null player");
		}
		
		return scores.get(player);
	}
	
	/**
	 * Sets the score for the specified player
	 * @param player
	 * @param score
	 */
	public void setScore(Players player, ScoreType score) {
		if (scores == null) {
			throw new IllegalArgumentException("Cannot set null for a points value");
		}
		
		scores.put(player, score);
	}
	
	/**
	 * Allow read-only access to the scores, useful to compute if
	 * any player wins in example.
	 * @return Unmodifiable map to the points
	 */
	public Map<Players, ScoreType> getScores() {
		return Collections.unmodifiableMap(scores);
	}

	public Players getWinner() {
		return winner;
	}

	public void setWinner(Players winner) {
		this.winner = winner;
	}
	
	/**
	 * Indicates if the context is finished. It is considered
	 * finished if there is a winner.
	 * @return
	 */
	public boolean isFinished() {
		return winner != null;
	}
}
