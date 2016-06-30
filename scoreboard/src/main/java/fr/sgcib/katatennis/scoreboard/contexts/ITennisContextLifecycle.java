package fr.sgcib.katatennis.scoreboard.contexts;

import fr.sgcib.katatennis.scoreboard.pub.Players;

/**
 * Defines the steps a game context (match, set or game) goes through :
 *  1. First, the context is prepared for the next event using the {@link #prepareNext()}
 * method. This method is essentially meant to be called from the implementing
 * class itself when it need to ready itself after a "sub-context" victory.
 *  2. Then, points are scored using the {@link #scorePoint(Players)} method.
 *  3. Finally, the context can be questioned to know if it is finished using the
 * {@link #isFinished()} method. The winning player can then be retrieving through
 * the {@link #getWinner()} method.
 * At any time, one can retrieve the current score using the {@link #getScore()}
 * method.
 */
public interface ITennisContextLifecycle<ScoreType> {
	
	/**
	 * Prepare for the next "sub-context". For a game, prepares the next set.
	 * For a set, prepares the next game...
	 */
	default void prepareNext() {
		// By default, nothing to do to prepare the next sub-context
	}
	
	/**
	 * Called when the specified player scores a point.
	 * The meaning and the treatments depend on the
	 * context (match/set/game).
	 * @param scores The board to update
	 * @param player The scoring player
	 * @return true if the point was scored, else false, meaning for
	 * example that the context cannot score anymore points (maybe finished ?).
	 */
	boolean scorePoint(Players player);
	
	/**
	 * Checks if the context is finished, meaning that there is a winner
	 * @return
	 */
	boolean checkFinished();
	
	/**
	 * Returns the winner of this context, if any.
	 * @return null if no player has yet won the context.
	 */
	Players getWinner();
	
	/**
	 * Returns the current context score for both players
	 * @return
	 */
	ScoreType getScore();
}
