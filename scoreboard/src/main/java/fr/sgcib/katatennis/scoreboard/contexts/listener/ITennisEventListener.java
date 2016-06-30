package fr.sgcib.katatennis.scoreboard.contexts.listener;

import fr.sgcib.katatennis.scoreboard.pub.Players;

/**
 * Listens all the events raised from the match/sets/games.
 */
public interface ITennisEventListener {
	/**
	 * Called when the match ends
	 * 
	 * @param winner
	 */
	default void onMatchVictory(Players winner) {
		// Nothing to do by default
	}
	
	/**
	 * Called when the game enters the deuce state
	 */
	default void onDeuce() {
		// Nothing to do by default
	}
	
	/**
	 * Called when the game ends
	 * @param winner
	 */
	default void onGameVictory(Players winner) {
		// Nothing to do by default
	}
	
	/**
	 * Called when a tie-break happens
	 */
	default void onTieBreak() {
		// Nothing to do by default
	}
	
	/**
	 * Called when the set ends
	 * @param winner
	 */
	default void onSetVictory(Players winner) {
		// Nothing to do by default
	}
}