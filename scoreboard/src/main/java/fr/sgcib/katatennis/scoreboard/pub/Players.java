package fr.sgcib.katatennis.scoreboard.pub;

/**
 * Permits easy manipulation of players without the need
 * to pass primitive values
 */
public enum Players {
	PLAYER_1,
	PLAYER_2;
	
	/**
	 * Returns the opponent of the current player
	 * @return PLAYER_2 if the current player is PLAYER_1,
	 * else returns PLAYER_1.
	 */
	public Players getOpponent() {
		if (this == PLAYER_1) {
			return PLAYER_2;
		} else {
			return PLAYER_1;
		}
	}
}
