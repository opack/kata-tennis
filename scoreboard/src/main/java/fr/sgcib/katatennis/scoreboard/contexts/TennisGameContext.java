package fr.sgcib.katatennis.scoreboard.contexts;

import fr.sgcib.katatennis.scoreboard.contexts.listener.ITennisEventListener;
import fr.sgcib.katatennis.scoreboard.contexts.listener.TennisListenerManager;
import fr.sgcib.katatennis.scoreboard.pub.Players;

/**
 * A game contains two scores : one for each player. A point is added via {@link #scorePoint(Players)}
 * when a player's opponent misses a ball. It is the smallest scoring unit.
 * 
 * A game can be a standard game (in which points are counted in the sequence 0
 * , 15, 30, 40) or a tie-break game (in which points are counted as incrementing
 * integers).
 * @see StandardGameContext
 * @see TieBreakGameContext
 * 
 * The listeners are notified of deuce ({@link ITennisEventListener#onDeuce()} and victory
 * ({@link ITennisEventListener#onGameVictory(Players)} events.
 */
public abstract class TennisGameContext<PointsType, ScoresType> extends TennisScoreManager<PointsType> implements ITennisContextLifecycle<ScoresType> {
	/**
	 * Manages the listeners to notify on game events
	 */
	protected TennisListenerManager listeners;
	
	public TennisGameContext(PointsType initialValue) {
		super(initialValue);
		listeners = new TennisListenerManager();
	}
	
	public TennisListenerManager getListeners() {
		return listeners;
	}
	
	@Override
	public boolean scorePoint(Players player) {
		// If the set is finished, cannot score anymore
		if (isFinished()) {
			return false;
		}
		
		// Adds a new point to the player's score
		addPoint(player);
		
		// Check if a player just won the game
		checkFinished();
		
		return true;
	}
	
	/**
	 * Checks if a player won and notifies the listener if that is
	 * the case.
	 */
	@Override
	public boolean checkFinished() {
		// If there is already a winner, nothing to do
		if (isFinished()) {
			return true;
		}
		
		// Effectively check for the winner
		setWinner(chooseWinner());
		
		// Notify listeners
		boolean finished = getWinner() != null;
		if (finished) {
			listeners.notifyGameVictory(getWinner());
		}
		
		return finished;
	}
	
	/**
	 * Indicates whether the game is in deuce
	 * @return
	 */
	public abstract boolean isDeuce();
	
	/**
	 * Adds one point to the specified player, following the
	 * right points sequence depending on the type of game.
	 * @param player
	 */
	protected abstract void addPoint(Players player);
	
	/**
	 * Determines if a player won the game, and return that player.
	 * @return null if no player won, otherwise returns the winner
	 */
	protected abstract Players chooseWinner();
}
