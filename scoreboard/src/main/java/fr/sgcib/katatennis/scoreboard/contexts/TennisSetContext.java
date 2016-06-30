package fr.sgcib.katatennis.scoreboard.contexts;

import static fr.sgcib.katatennis.scoreboard.pub.Players.PLAYER_1;
import static fr.sgcib.katatennis.scoreboard.pub.Players.PLAYER_2;

import fr.sgcib.katatennis.scoreboard.contexts.listener.ITennisEventListener;
import fr.sgcib.katatennis.scoreboard.contexts.listener.TennisListenerManager;
import fr.sgcib.katatennis.scoreboard.pub.IntScores;
import fr.sgcib.katatennis.scoreboard.pub.Players;

/**
 * A set contains the number of games won by each player in the set. A point is
 * added via {@link #scorePoint(Players)} when a player wins a game.
 * 
 * A set is won if a player :
 *  - has at least 6 games and 2 more than the other player
 *  - wins the tie-break game
 *  
 * This class can also determine if a tie-break happens. This situation can be
 * known thanks to {@link #isTieBreak()}.
 * 
 * The listeners are notified of tie-breaks ({@link ITennisEventListener#onTieBreak()})
 * and victory ({@link ITennisEventListener#onSetVictory(Players)} events.
 */
public class TennisSetContext extends TennisScoreManager<Integer> implements ITennisContextLifecycle<IntScores>, ITennisEventListener {
	private static final int MIN_GAMES_TO_WIN = 6;
	private static final int MIN_DIFF_TO_WIN = 2;
	private static final int TIE_BREAK_EQUALITY = 6;
	
	/**
	 * Indicates if this set is in tie-break
	 */
	private boolean tieBreak;
	
	/**
	 * Holds the points for the current game of the set
	 */
	@SuppressWarnings("rawtypes")
	private TennisGameContext currentGame;
	
	/**
	 * Manages the listeners to notify on game events
	 */
	private TennisListenerManager listeners;
	
	public TennisSetContext() {
		super(Integer.valueOf(0));
		listeners = new TennisListenerManager();
		prepareNext();
	}
	
	public TennisListenerManager getListeners() {
		return listeners;
	}
	
	public boolean isTieBreak() {
		return tieBreak;
	}

	public void setTieBreak(boolean tieBreak) {
		this.tieBreak = tieBreak;
	}
	
	@SuppressWarnings("rawtypes")
	public TennisGameContext getCurrentGame() {
		return currentGame;
	}

	@Override
	public void prepareNext() {
		// Checks if the next game is a tie-break
		checkTieBreak();
		
		// Creates a new TennisGame object
		if (tieBreak) {
			currentGame = new TieBreakGameContext();
		} else {
			currentGame = new StandardGameContext();
		}
		
		// Those who listen for this context are interested in the others too
		currentGame.getListeners().addAll(listeners);
		currentGame.getListeners().add(this);
	}

	/**
	 * Updates the tie-break flag depending on the current 
	 * A tie-break occurs if both players won 6 games.
	 */
	private void checkTieBreak() {
		tieBreak = getScore(PLAYER_1) == TIE_BREAK_EQUALITY
				&& getScore(PLAYER_2) == TIE_BREAK_EQUALITY;
		
		// Notify listeners
		if (tieBreak) {
			listeners.notifyTieBreak();
		}
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
		
		// Check if a player just won the set
		if (!checkFinished()) {
			
			// If the set continues, prepare the next game
			prepareNext();
		}
		
		return true;
	}

	@Override
	public void onGameVictory(Players winner) {
		// If the player won a game, add a point
		scorePoint(winner);
	}
	
	/**
	 * Checks if a player won and notifies the listener if that is
	 * the case.
	 * A set is won if a player :
	 *  - has at least 6 games and 2 more than the other player
	 *  - wins the tie-break game
	 *  @return true if the set is finished
	 */
	@Override
	public boolean checkFinished() {
		// If there is already a winner, nothing to do
		if (isFinished()) {
			return true;
		}
		
		final int player1Games = getScore(PLAYER_1);
		final int player2Games = getScore(PLAYER_2);
		
		// If in tie-break, then the winner is the one with
		// most games because it means he just won the
		// decisive game
		if (tieBreak) {
			if (player1Games > player2Games) {
				setWinner(PLAYER_1);
			} else if (player2Games > player1Games) {
				setWinner(PLAYER_2);
			}
		}
		
		// If not in tie-break, the winner is the one that
		// has at least 6 games and 2 more games than the other
		else {
			if (player1Games >= MIN_GAMES_TO_WIN && player1Games >= player2Games + MIN_DIFF_TO_WIN) {
				setWinner(PLAYER_1);
			} else {
				if (player2Games >= MIN_GAMES_TO_WIN && player2Games >= player1Games + MIN_DIFF_TO_WIN) {
					setWinner(PLAYER_2);
				}
			}
		}
		
		// Notify listeners
		boolean finished = getWinner() != null;
		if (finished) {
			listeners.notifySetVictory(getWinner());
		}
		
		return finished;
	}

	@Override
	public IntScores getScore() {
		return new IntScores(getScore(PLAYER_1), getScore(PLAYER_2));
	}
}
