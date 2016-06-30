package fr.sgcib.tennis.scoreboard.test;

import static fr.sgcib.katatennis.scoreboard.pub.Players.PLAYER_1;
import static fr.sgcib.katatennis.scoreboard.pub.Players.PLAYER_2;
import static fr.sgcib.katatennis.scoreboard.pub.TennisPoints.FIFTEEN;
import static fr.sgcib.katatennis.scoreboard.pub.TennisPoints.FORTY;
import static fr.sgcib.katatennis.scoreboard.pub.TennisPoints.THIRTY;
import static fr.sgcib.katatennis.scoreboard.pub.TennisPoints.ZERO;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fr.sgcib.katatennis.scoreboard.ScoreBoard;
import fr.sgcib.katatennis.scoreboard.contexts.TennisGameContext;
import fr.sgcib.katatennis.scoreboard.contexts.TennisSetContext;
import fr.sgcib.katatennis.scoreboard.pub.Players;

/**
 * Tests the score board
 */
public class ScoreBoardTest {
	
	/**
	 * Ensures that scored points increment a player's points
	 * in the current game
	 */
	@Test
	public void testScorePoint() {
		ScoreBoard scoreBoard = new ScoreBoard();
		
		String p1Before = scoreBoard.getCurrentPoints(PLAYER_1);
		assertEquals(ZERO.toString(), p1Before);
		
		scoreBoard.scorePoint(PLAYER_1);
		
		String p1After = scoreBoard.getCurrentPoints(PLAYER_1);
		assertEquals(FIFTEEN.toString(), p1After);
		
		String p2Before = scoreBoard.getCurrentPoints(PLAYER_2);
		assertEquals(ZERO.toString(), p2Before);
		
		scoreBoard.scorePoint(PLAYER_2);
		
		String p2After = scoreBoard.getCurrentPoints(PLAYER_1);
		assertEquals(FIFTEEN.toString(), p2After);
	}

	/**
	 * Tests the points progression for a standard game, and that when the
	 * game is won a new one is started
	 */
	@Test
	public void testPointsProgression() {
		ScoreBoard scoreBoard = new ScoreBoard();
		assertEquals(ZERO.toString(), scoreBoard.getCurrentPoints(PLAYER_1));
		
		scoreBoard.scorePoint(PLAYER_1);
		assertEquals(FIFTEEN.toString(), scoreBoard.getCurrentPoints(PLAYER_1));
		
		scoreBoard.scorePoint(PLAYER_1);
		assertEquals(THIRTY.toString(), scoreBoard.getCurrentPoints(PLAYER_1));
		
		scoreBoard.scorePoint(PLAYER_1);
		assertEquals(FORTY.toString(), scoreBoard.getCurrentPoints(PLAYER_1));
		
		scoreBoard.scorePoint(PLAYER_1);
		// After this score, the player 1 wins and goes back to 0 points in a new game
		assertEquals(ZERO.toString(), scoreBoard.getCurrentPoints(PLAYER_1));
	}
	
	/**
	 * Tests that the deuce state is correctly detected
	 */
	@Test
	public void testDeuce() {
		ScoreBoard scoreBoard = new ScoreBoard();
		
		// Ensures the player 1 has 40 points
		scoreBoard.scorePoint(PLAYER_1); // P1 now has 15
		scoreBoard.scorePoint(PLAYER_1); // P1 now has 30
		scoreBoard.scorePoint(PLAYER_1); // P1 now has 40
		
		// Scores player 2 until he has 40 points too
		scoreBoard.scorePoint(PLAYER_2); // P1 now has 15
		scoreBoard.scorePoint(PLAYER_2); // P1 now has 30
		assertEquals(false, scoreBoard.isDeuce());
		scoreBoard.scorePoint(PLAYER_2); // P1 now has 40
		assertEquals(true, scoreBoard.isDeuce());
	}
	
	/**
	 * Tests that the end of the match is correctly detected, the winner is
	 * correctly selected and it is not possible anymore to score points. 
	 */
	@Test
	public void testMatchFinished() {
		ScoreBoard scoreBoard = new ScoreBoard();
		
		// Make P1 win 3 sets
		scoreSet(scoreBoard, PLAYER_1);
		scoreSet(scoreBoard, PLAYER_1);
		
		// Finished ?
		assertEquals(false, scoreBoard.isFinished());
		
		// Score the winning set for P1
		scoreSet(scoreBoard, PLAYER_1);
		assertEquals(Integer.valueOf(3), scoreBoard.getSetsWon(PLAYER_1));
		
		// Finished ?
		assertEquals(true, scoreBoard.isFinished());
		
		// P1 wins the match !
		assertEquals(PLAYER_1, scoreBoard.getWinner());
		
		// Cannot score anymore as the match is finished
		boolean couldScore = scoreBoard.scorePoint(PLAYER_2);
		assertEquals(false, couldScore);
	}

	/**
	 * Scores enough points so that the specified player wins a set.
	 * @param scoreBoard
	 * @param player
	 */
	private void scoreSet(ScoreBoard scoreBoard, Players player) {
		TennisSetContext set = scoreBoard.getMatch().getCurrentSet();
		while(!set.isFinished()) {
			@SuppressWarnings("rawtypes")
			TennisGameContext game = set.getCurrentGame();
			while (!game.isFinished()) {
				// Scores points through the scoreBoard, as it is it we want to test
				scoreBoard.scorePoint(player);
			}
		}
	}

}
