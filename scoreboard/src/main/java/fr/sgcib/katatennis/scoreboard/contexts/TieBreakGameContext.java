package fr.sgcib.katatennis.scoreboard.contexts;

import static fr.sgcib.katatennis.scoreboard.pub.Players.PLAYER_1;
import static fr.sgcib.katatennis.scoreboard.pub.Players.PLAYER_2;

import fr.sgcib.katatennis.scoreboard.pub.Players;
import fr.sgcib.katatennis.scoreboard.pub.TieBreakGameScores;

/**
 * Represents a game in which points are counted using Integers.
 * In a tie-break game, each point increments the score by 1.
 * The game ends when a player has :
 *  - at least 7 points
 *  - 2 more points than the opponent.
 */
public class TieBreakGameContext extends TennisGameContext<Integer, TieBreakGameScores> {
	private static final int MIN_POINTS_TO_WIN = 7;
	private static final int MIN_DIFF_TO_WIN = 2;

	public TieBreakGameContext() {
		super(Integer.valueOf(0));
	}

	@Override
	protected void addPoint(Players player) {
		// In tie-break, the score is incremented by 1 each time
		Integer newScore = Integer.valueOf(getScore(player) + 1); // Use the supposed cache in Integer
		setScore(player, newScore);
	}

	@Override
	protected Players chooseWinner() {
		// In tie-break, a game is won if a player's game
		// points > 7 and greater than or equals opponents game points + 2
		final int score1 = getScore(PLAYER_1);
		final int score2 = getScore(PLAYER_2);
		final int diff = score1 - score2;
		
		if (score2 >= MIN_POINTS_TO_WIN
		&& diff <= -MIN_DIFF_TO_WIN) {
			return PLAYER_2;
			
		} else if (score1 >= MIN_POINTS_TO_WIN
			&& diff >= MIN_DIFF_TO_WIN) {
			return PLAYER_1;
		}
		return null;
	}

	@Override
	public TieBreakGameScores getScore() {
		return new TieBreakGameScores(getScore(PLAYER_1), getScore(PLAYER_2));
	}
	
	@Override
	public boolean isDeuce() {
		// There is no deuce in tie-break
		return false;
	}
}
