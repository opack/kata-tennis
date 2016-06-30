package fr.sgcib.tennis.scoreboard.test.contexts;

import static fr.sgcib.katatennis.scoreboard.pub.Players.PLAYER_1;
import static fr.sgcib.katatennis.scoreboard.pub.Players.PLAYER_2;
import static fr.sgcib.katatennis.scoreboard.pub.TennisPoints.FIFTEEN;
import static fr.sgcib.katatennis.scoreboard.pub.TennisPoints.FORTY;
import static fr.sgcib.katatennis.scoreboard.pub.TennisPoints.THIRTY;
import static fr.sgcib.katatennis.scoreboard.pub.TennisPoints.VICTORY;
import static fr.sgcib.katatennis.scoreboard.pub.TennisPoints.ZERO;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fr.sgcib.katatennis.scoreboard.contexts.StandardGameContext;

/**
 * Tests the standard game context, which is a normal game, not in tie-break.
 */
public class StandardGameContextTest {

	/**
	 * Tests that the deuce state is correctly detected
	 */
	@Test
	public void testIsDeuce() {
		StandardGameContext game = new StandardGameContext();
		
		// Ensures the player 1 has 40 points
		game.scorePoint(PLAYER_1); // P1 now has 15
		game.scorePoint(PLAYER_1); // P1 now has 30
		game.scorePoint(PLAYER_1); // P1 now has 40
		
		// Scores player 2 until he has 40 points too
		game.scorePoint(PLAYER_2); // P1 now has 15
		game.scorePoint(PLAYER_2); // P1 now has 30
		assertEquals(false, game.isDeuce());
		game.scorePoint(PLAYER_2); // P1 now has 40
		assertEquals(true, game.isDeuce());
	}

	/**
	 * Ensures that scored points increment a player's points
	 * in the correct order
	 */
	@Test
	public void testScorePoint() {
		StandardGameContext game = new StandardGameContext();
		assertEquals(ZERO, game.getScore(PLAYER_2));
		
		game.scorePoint(PLAYER_2);
		assertEquals(FIFTEEN, game.getScore(PLAYER_2));
		
		game.scorePoint(PLAYER_2);
		assertEquals(THIRTY, game.getScore(PLAYER_2));
		
		game.scorePoint(PLAYER_2);
		assertEquals(FORTY, game.getScore(PLAYER_2));
		
		game.scorePoint(PLAYER_2);
		assertEquals(VICTORY, game.getScore(PLAYER_2));
	}

	/**
	 * Tests that the end of the game is correctly detected, the winner is
	 * correctly selected and it is not possible anymore to score points. 
	 */
	@Test
	public void testCheckFinished() {
		StandardGameContext game = new StandardGameContext();
		
		// Scores player 1 until he has 30 points
		game.scorePoint(PLAYER_1);
		game.scorePoint(PLAYER_1);
		game.scorePoint(PLAYER_1);
		
		// Finished ?
		assertEquals(false, game.isFinished());
		
		// Scores the last point
		game.scorePoint(PLAYER_1);
		
		// Finished ?
		assertEquals(true, game.isFinished());
		
		// Winner is P1
		assertEquals(PLAYER_1, game.getWinner());
		
		// Cannot score anymore as the game is finished
		boolean couldScore = game.scorePoint(PLAYER_1);
		assertEquals(false, couldScore);
	}

}
