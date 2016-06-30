package fr.sgcib.tennis.scoreboard.test.contexts;

import static fr.sgcib.katatennis.scoreboard.pub.Players.PLAYER_1;
import static fr.sgcib.katatennis.scoreboard.pub.Players.PLAYER_2;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fr.sgcib.katatennis.scoreboard.contexts.TieBreakGameContext;

/**
 * Tests the tie-break game context. In this context, points are counted
 * as integers.
 */
public class TieBreakGameContextTest {

	/**
	 * Tests that the deuce state is never raised, as it does not exist in
	 * tie-break.
	 */
	@Test
	public void testIsDeuce() {
		TieBreakGameContext game = new TieBreakGameContext();
		
		// Ensures the player 1 has 3 points
		game.scorePoint(PLAYER_1);
		game.scorePoint(PLAYER_1);
		game.scorePoint(PLAYER_1);
		
		// Scores player 2 until he has 2 points
		game.scorePoint(PLAYER_2);
		game.scorePoint(PLAYER_2);
		assertEquals(false, game.isDeuce());
		
		// Score the 3rd point for P2
		game.scorePoint(PLAYER_2);
		
		// Deuce ?
		assertEquals(false, game.isDeuce());
	}

	/**
	 * Ensures that scored points increment a player's points
	 */
	@Test
	public void testScorePoint() {
		TieBreakGameContext game = new TieBreakGameContext();
		
		assertEquals(Integer.valueOf(0), game.getScore(PLAYER_1));
		assertEquals(Integer.valueOf(0), game.getScore(PLAYER_2));
		
		// Scores some points
		game.scorePoint(PLAYER_1);
		assertEquals(Integer.valueOf(1), game.getScore(PLAYER_1));
		
		game.scorePoint(PLAYER_2);
		assertEquals(Integer.valueOf(1), game.getScore(PLAYER_2));
		
		game.scorePoint(PLAYER_1);
		assertEquals(Integer.valueOf(2), game.getScore(PLAYER_1));
		
		game.scorePoint(PLAYER_1);
		assertEquals(Integer.valueOf(3), game.getScore(PLAYER_1));
	}

	/**
	 * Tests that the end of the game is correctly detected, the winner is
	 * correctly selected and it is not possible anymore to score points.
	 * In this scenario, the game finishes with a score of 7 to 1. The end
	 * condition (having 7 points and 2 more than the opponent) is effectively
	 * met.
	 */
	@Test
	public void testCheckFinished7to1() {
		TieBreakGameContext game = new TieBreakGameContext();
		
		// Scores player 1 for 6 points
		game.scorePoint(PLAYER_1);
		game.scorePoint(PLAYER_1);
		game.scorePoint(PLAYER_1);
		game.scorePoint(PLAYER_1);
		game.scorePoint(PLAYER_1);
		game.scorePoint(PLAYER_1);
		assertEquals(false, game.isFinished());
		
		// Scores player 2 for 1 point
		game.scorePoint(PLAYER_2);
		assertEquals(false, game.isFinished());
		
		// Scores player 1 for a 7th point
		game.scorePoint(PLAYER_1);
		
		// Finished ? Score is 7 to 1, P1 should win
		assertEquals(true, game.isFinished());
		
		// Winner is P1
		assertEquals(PLAYER_1, game.getWinner());
		
		// Cannot score anymore as the game is finished
		boolean couldScore = game.scorePoint(PLAYER_1);
		assertEquals(false, couldScore);
	}
	
	/**
	 * Tests that the end of the game is correctly detected, the winner is
	 * correctly selected and it is not possible anymore to score points.
	 * In this scenario, the game finishes with a score of 8 to 6. The end
	 * condition (having 7 points and 2 more than the opponent) is effectively
	 * met.
	 */
	@Test
	public void testCheckFinished8to6() {
		TieBreakGameContext game = new TieBreakGameContext();
		
		// Scores player 1 for 5 points
		game.scorePoint(PLAYER_1);
		game.scorePoint(PLAYER_1);
		game.scorePoint(PLAYER_1);
		game.scorePoint(PLAYER_1);
		game.scorePoint(PLAYER_1);
		assertEquals(false, game.isFinished());
		
		// Scores player 2 for 5 points too
		game.scorePoint(PLAYER_2);
		game.scorePoint(PLAYER_2);
		game.scorePoint(PLAYER_2);
		game.scorePoint(PLAYER_2);
		game.scorePoint(PLAYER_2);
		assertEquals(false, game.isFinished());
		
		// Scores both players for a 6th point
		game.scorePoint(PLAYER_1);
		game.scorePoint(PLAYER_2);
		assertEquals(false, game.isFinished());
		
		// Scores player 1 for a 7th point
		game.scorePoint(PLAYER_1);
		assertEquals(false, game.isFinished());
		
		// Scores player 1 for a 8th point
		game.scorePoint(PLAYER_1);
		
		// Finished ? Score is 8 to 6, P1 should win
		assertEquals(true, game.isFinished());
		
		// Winner is P1
		assertEquals(PLAYER_1, game.getWinner());
		
		// Cannot score anymore as the game is finished
		boolean couldScore = game.scorePoint(PLAYER_1);
		assertEquals(false, couldScore);
	}

}
