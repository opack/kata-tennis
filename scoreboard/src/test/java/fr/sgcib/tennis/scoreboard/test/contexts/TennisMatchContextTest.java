package fr.sgcib.tennis.scoreboard.test.contexts;

import static fr.sgcib.katatennis.scoreboard.pub.Players.PLAYER_1;
import static fr.sgcib.katatennis.scoreboard.pub.Players.PLAYER_2;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fr.sgcib.katatennis.scoreboard.contexts.TennisMatchContext;

/**
 * Tests the match context, which scores sets.
 */
public class TennisMatchContextTest {

	/**
	 * Tests that sets are correctly scored in the match, and
	 * that it is forbidden to score more than 3 sets (because
	 * the match is finished by then).
	 */
	@Test
	public void testScorePoint() {
		TennisMatchContext match = new TennisMatchContext();
		
		assertEquals(Integer.valueOf(0), match.getScore(PLAYER_1));
		assertEquals(Integer.valueOf(0), match.getScore(PLAYER_2));
		
		// Scores some sets
		match.scorePoint(PLAYER_1);
		assertEquals(Integer.valueOf(1), match.getScore(PLAYER_1));
		
		match.scorePoint(PLAYER_2);
		assertEquals(Integer.valueOf(1), match.getScore(PLAYER_1));
		
		match.scorePoint(PLAYER_1);
		assertEquals(Integer.valueOf(2), match.getScore(PLAYER_1));
		
		// Scores the winning set
		match.scorePoint(PLAYER_1);
		assertEquals(Integer.valueOf(3), match.getScore(PLAYER_1));
		
		// Cannot score anymore as the match is finished
		boolean couldScore = match.scorePoint(PLAYER_1);
		assertEquals(false, couldScore);
	}
	
	/**
	 * Tests that if a players finishes and wins the match if he scores 3 sets,
	 * and that it is then forbidden to score more sets.
	 */
	@Test
	public void testCheckFinished3to0() {
		TennisMatchContext match = new TennisMatchContext();
		
		// Scores some sets for P1
		match.scorePoint(PLAYER_1);
		match.scorePoint(PLAYER_1);
		
		// Finished ?
		assertEquals(false, match.isFinished());
		
		// Score the winning set
		match.scorePoint(PLAYER_1);
		
		// Finished ?
		assertEquals(true, match.isFinished());
		
		// Winner is P1
		assertEquals(PLAYER_1, match.getWinner());
		
		// Cannot score anymore as the match is finished
		boolean couldScore = match.scorePoint(PLAYER_1);
		assertEquals(false, couldScore);
	}
	
	/**
	 * Tests that if a players finishes and wins the match is he scores 3 sets,
	 * and that it is then forbidden to score more sets.
	 * This test case proves that there is no need to have 2 more sets than the
	 * opponent : only condition for winning the match is having 3 sets.
	 */
	@Test
	public void testCheckFinished3to2() {
		TennisMatchContext match = new TennisMatchContext();
		
		// Scores some sets for P1 and P2
		match.scorePoint(PLAYER_1);
		match.scorePoint(PLAYER_1);
		
		match.scorePoint(PLAYER_2);
		match.scorePoint(PLAYER_2);
		
		// Finished ?
		assertEquals(false, match.isFinished());
		
		// Score the winning set
		match.scorePoint(PLAYER_2);
		
		// Finished ?
		assertEquals(true, match.isFinished());
		
		// Winner is P2
		assertEquals(PLAYER_2, match.getWinner());
		
		// Cannot score anymore as the match is finished
		boolean couldScore = match.scorePoint(PLAYER_1);
		assertEquals(false, couldScore);
	}

}
