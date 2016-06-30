package fr.sgcib.katatennis.scoreboard.contexts;

import static fr.sgcib.katatennis.scoreboard.pub.Players.PLAYER_1;
import static fr.sgcib.katatennis.scoreboard.pub.Players.PLAYER_2;

import java.util.LinkedList;
import java.util.Map;

import fr.sgcib.katatennis.scoreboard.contexts.listener.ITennisEventListener;
import fr.sgcib.katatennis.scoreboard.contexts.listener.TennisListenerManager;
import fr.sgcib.katatennis.scoreboard.pub.IntScores;
import fr.sgcib.katatennis.scoreboard.pub.Players;

/**
 * A match contains the list of past sets, the current set and the number of
 * games won by each player in the set. A point is added via {@link #scorePoint(Players)}
 * when a player wins a set.
 * 
 * A match is won if a player won 3 sets.
 * 
 * The listeners are notified of victory ({@link ITennisEventListener#onMatchVictory(Players)} events.
 */
public class TennisMatchContext extends TennisScoreManager<Integer> implements ITennisContextLifecycle<IntScores>, ITennisEventListener {
	private static final int MIN_SETS_TO_WIN = 3;
	
	/**
	 * The played sets.
	 * The first element of the list is the oldest
	 * set and the last is the newest
	 */
	private LinkedList<TennisSetContext> sets;
	
	/**
	 * Manages the listeners to notify on game events
	 */
	private TennisListenerManager listeners;
	
	public TennisMatchContext () {
		super(Integer.valueOf(0));
		sets = new LinkedList<>();
		listeners = new TennisListenerManager();
		prepareNext();
	}
	
	public TennisListenerManager getListeners() {
		return listeners;
	}

	public LinkedList<TennisSetContext> getSets() {
		return sets;
	}

	public TennisSetContext getCurrentSet() {
		return sets.getLast();
	}
	
	@Override
	public void prepareNext() {
		// Preparing next Set consists in creating a new TennisSet instance
		TennisSetContext newSet = new TennisSetContext();
		
		// Those who listen for this context are interested in the others too
		newSet.getListeners().addAll(listeners);
		newSet.getListeners().add(this);
		
		sets.add(newSet);
	}

	@Override
	public boolean scorePoint(Players player) {
		// If the set is finished, cannot score anymore
		if (isFinished()) {
			return false;
		}
		
		// A new set has been won. Score a point for the corresponding player.
		Integer newScore = Integer.valueOf(getScore(player) + 1);
		setScore(player, newScore);
		
		// Check if a player just won the match
		if (!checkFinished()) {
			
			// If the match continues, prepare the next set
			prepareNext();
		}
		
		return true;
	}
	
	/**
	 * Checks if a player won and notifies the listener if that is
	 * the case.
	 * A match is won if a player has at least won 2 sets.
	 */
	@Override
	public boolean checkFinished() {
		// If there is already a winner, nothing to do
		if (isFinished()) {
			return true;
		}
		
		// If any player has 3 points, then he has won
		for (Map.Entry<Players, Integer> score : getScores().entrySet()) {
			if (score.getValue() >= MIN_SETS_TO_WIN) {
				setWinner(score.getKey());
				
				// Notify listeners
				listeners.notifyMatchVictory(getWinner());
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public void onSetVictory(Players winner) {
		// If the player won a set, add a point
		scorePoint(winner);
	}

	@Override
	public IntScores getScore() {
		return new IntScores(getScore(PLAYER_1), getScore(PLAYER_2));
	}
}
