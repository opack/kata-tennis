package fr.sgcib.katatennis.scoreboard.contexts;

import static fr.sgcib.katatennis.scoreboard.pub.Players.PLAYER_1;
import static fr.sgcib.katatennis.scoreboard.pub.Players.PLAYER_2;
import static fr.sgcib.katatennis.scoreboard.pub.TennisPoints.ADVANTAGE;
import static fr.sgcib.katatennis.scoreboard.pub.TennisPoints.FIFTEEN;
import static fr.sgcib.katatennis.scoreboard.pub.TennisPoints.FORTY;
import static fr.sgcib.katatennis.scoreboard.pub.TennisPoints.THIRTY;
import static fr.sgcib.katatennis.scoreboard.pub.TennisPoints.VICTORY;

import java.util.Map;

import fr.sgcib.katatennis.scoreboard.pub.Players;
import fr.sgcib.katatennis.scoreboard.pub.StandardGameScores;
import fr.sgcib.katatennis.scoreboard.pub.TennisPoints;

/**
 * Represents a game in which points are in the enum TennisPoints : 0, 15, 30,
 * 40 and A.
 * In standard game, the game ends when a player scores and :
 *  - he is the only player with 40
 *  - he has the ADVANTAGE (meaning that the other player has 40)
 */
public class StandardGameContext extends TennisGameContext<TennisPoints, StandardGameScores> {

	public StandardGameContext() {
		super(TennisPoints.ZERO);
	}

	@Override
	protected void addPoint(Players player) {
		TennisPoints newScore = null;
		
		// In a normal game, the points follow this sequence :
		// 0, 15, 30, 40 and A.
		switch (getScore(player)) {
		case ADVANTAGE:
			newScore = VICTORY;
			break;
		case FIFTEEN:
			newScore = THIRTY;
			break;
		case FORTY:
			TennisPoints opponentScore = getScore(player.getOpponent());
			if (opponentScore == FORTY) {
				// If the other player has FORTY too, then this player takes the advantage
				newScore = ADVANTAGE;
			} if (opponentScore == ADVANTAGE) {
				// If the opponent has the advantage, then he looses it and
				// the game is back in deuce
				setScore(player.getOpponent(),  FORTY);
				listeners.notifyDeuce();
			}
			else {
				// Else, the player wins the game !
				newScore = VICTORY;
			}
			break;
		case THIRTY:
			newScore = FORTY;
			
			// If the other player has FORTY too, then the game goes in deuce
			if (getScore(player.getOpponent()) == FORTY) {
				listeners.notifyDeuce();
			}
			break;
		case VICTORY:
			break;
		case ZERO:
			newScore = FIFTEEN;
			break;
		default:
			break;
		}
		
		setScore(player, newScore);
	}

	@Override
	protected Players chooseWinner() {
		// If any player has VICTORY score, then he has won
		for (Map.Entry<Players, TennisPoints> score : getScores().entrySet()) {
			if (score.getValue() == VICTORY) {
				return score.getKey();
			}
		}
		
		return null;
	}

	@Override
	public StandardGameScores getScore() {
		return new StandardGameScores(getScore(PLAYER_1), getScore(PLAYER_2));
	}
	
	@Override
	public boolean isDeuce() {
		// Deuce if both players have 40
		return FORTY.equals(getScore(PLAYER_1))
			&& FORTY.equals(getScore(PLAYER_2));
	}
}
