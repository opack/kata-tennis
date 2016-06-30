package fr.sgcib.tennis.scoreboard.test.contexts;

import static fr.sgcib.katatennis.scoreboard.pub.Players.PLAYER_1;
import static fr.sgcib.katatennis.scoreboard.pub.Players.PLAYER_2;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fr.sgcib.katatennis.scoreboard.contexts.TennisSetContext;

/**
 * Tests the set context, which scores games.
 */
public class TennisSetContextTest {

	/**
	 * Tests the detection of the tie-break state. A tie-break should happen if
	 * both players have 6 games in the set. Then the following game is a tie-
	 * breaker.
	 */
	@Test
	public void testIsTieBreak() {
		TennisSetContext set = new TennisSetContext();
		
		// Score 5 points for P1 and P2
		set.scorePoint(PLAYER_1);
		set.scorePoint(PLAYER_1);
		set.scorePoint(PLAYER_1);
		set.scorePoint(PLAYER_1);
		set.scorePoint(PLAYER_1);
		
		set.scorePoint(PLAYER_2);
		set.scorePoint(PLAYER_2);
		set.scorePoint(PLAYER_2);
		set.scorePoint(PLAYER_2);
		set.scorePoint(PLAYER_2);
		assertEquals(false, set.isFinished());
		
		// Is tie-break ?
		assertEquals(false, set.isTieBreak());
		
		// Score the 6th point for P1 and P2
		set.scorePoint(PLAYER_1);
		set.scorePoint(PLAYER_2);
		assertEquals(false, set.isFinished());
		
		// Is tie-break ?
		assertEquals(true, set.isTieBreak());
	}

	/**
	 * Tests that games are correctly scored in the set, and that it is
	 * forbidden to score more games when the set is finished.
	 */
	@Test
	public void testScorePoint() {
		TennisSetContext set = new TennisSetContext();
		
		assertEquals(Integer.valueOf(0), set.getScore(PLAYER_1));
		
		// Scores some games
		set.scorePoint(PLAYER_1);
		assertEquals(Integer.valueOf(1), set.getScore(PLAYER_1));
		
		set.scorePoint(PLAYER_1);
		assertEquals(Integer.valueOf(2), set.getScore(PLAYER_1));
		
		set.scorePoint(PLAYER_1);
		assertEquals(Integer.valueOf(3), set.getScore(PLAYER_1));
		
		set.scorePoint(PLAYER_1);
		assertEquals(Integer.valueOf(4), set.getScore(PLAYER_1));
		
		set.scorePoint(PLAYER_1);
		assertEquals(Integer.valueOf(5), set.getScore(PLAYER_1));
		
		set.scorePoint(PLAYER_1);
		assertEquals(Integer.valueOf(6), set.getScore(PLAYER_1));
		
		// Cannot score anymore as the match is finished
		boolean couldScore = set.scorePoint(PLAYER_1);
		assertEquals(false, couldScore);
	}

	/**
	 * Tests the detection of the set end conditions if a player has 6 games
	 * and the opponent only 1. Also ensures that when the set is finished it
	 * is not possible to score more games.
	 */
	@Test
	public void testCheckFinished6to1() {
		TennisSetContext set = new TennisSetContext();
		
		// Score 5 points for P1
		set.scorePoint(PLAYER_1);
		set.scorePoint(PLAYER_1);
		set.scorePoint(PLAYER_1);
		set.scorePoint(PLAYER_1);
		set.scorePoint(PLAYER_1);
		assertEquals(false, set.isFinished());
		
		// Score 1 points for P2
		set.scorePoint(PLAYER_2);
		assertEquals(false, set.isFinished());
		
		// Score the winning point for P1
		set.scorePoint(PLAYER_1);
		assertEquals(true, set.isFinished());
		
		// Winner is P1
		assertEquals(PLAYER_1, set.getWinner());
		
		// Cannot score anymore as the set is finished
		boolean couldScore = set.scorePoint(PLAYER_1);
		assertEquals(false, couldScore);
	}
	
	/**
	 * Tests the detection of the set end conditions if a player has 7 games
	 * and the opponent 5. This illustrates the case when both players happen
	 * to have 5 games and must continue until one has 2 more games than the
	 * other.
	 * This test also ensures that when the set is finished it is not possible
	 * to score more games.
	 */
	@Test
	public void testCheckFinished7to5() {
		TennisSetContext set = new TennisSetContext();
		
		// Score 5 points for P1
		set.scorePoint(PLAYER_1);
		set.scorePoint(PLAYER_1);
		set.scorePoint(PLAYER_1);
		set.scorePoint(PLAYER_1);
		set.scorePoint(PLAYER_1);
		assertEquals(false, set.isFinished());
		
		// Score 5 points for P2
		set.scorePoint(PLAYER_2);
		set.scorePoint(PLAYER_2);
		set.scorePoint(PLAYER_2);
		set.scorePoint(PLAYER_2);
		set.scorePoint(PLAYER_2);
		assertEquals(false, set.isFinished());
		
		// Score the 6th point for P2
		set.scorePoint(PLAYER_2);
		assertEquals(false, set.isFinished());
		
		// Score the winning point for P2
		set.scorePoint(PLAYER_2);
		assertEquals(true, set.isFinished());
		
		// Winner is P2
		assertEquals(PLAYER_2, set.getWinner());
		
		// Cannot score anymore as the set is finished
		boolean couldScore = set.scorePoint(PLAYER_1);
		assertEquals(false, couldScore);
	}

}
