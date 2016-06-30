package fr.sgcib.katatennis.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import fr.sgcib.katatennis.scoreboard.ScoreBoard;
import fr.sgcib.katatennis.scoreboard.pub.IntScores;
import fr.sgcib.katatennis.scoreboard.pub.Players;

/**
 * Simple console UI to test the score board.
 */
public class TennisMatchUI {
	
	private ScoreBoard scoreBoard;
	
	/**
	 * Launch the UI
	 * @throws IOException 
	 */
	public void launch() throws IOException {
		printWelcome();
		
		printHelp();
		
		reset();
		
		processCommands();
	}
	
	/**
	 * Prints a welcome message
	 */
	public void printWelcome() {
		System.out.println("\n\n\n\n\n\n\n\n\n");
		System.out.println("Welcome to tennis score board UI !");
		System.out.println();
	}
	
	/**
	 * Resets the score board
	 */
	public void reset() {
		scoreBoard = new ScoreBoard();
	}
	
	/**
	 * Prints a message indicating the available commands
	 */
	public void printHelp() {
		System.out.println("Use the following commands :");
		System.out.println("  1\tScore one point for player 1");
		System.out.println("  2\tScore one point for player 2");
		System.out.println("  h\tPrint the list of available commands");
		System.out.println("  p\tPrint the score board");
		System.out.println("  q\tQuit the program");
		System.out.println("  r\tResets the board");
		System.out.println("Note : you can input multiple commands in one time. Example : '112p' scores 2 points for the player 1, 1 for player 2 and then prints the score board.");
		System.out.println();
	}
	
	private void processCommands() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String commands;
		while ((commands = reader.readLine()) != null) {
			
			commands.chars().forEach(command -> {
				switch (command) {
				// Quit
				case 'q':
					System.exit(0);
					break;
				// Score P1
				case '1':
					scoreBoard.scorePoint(Players.PLAYER_1);
					break;
				// Score P2
				case '2':
					scoreBoard.scorePoint(Players.PLAYER_2);
					break;
				// Print board
				case 'p':
					print();
					break;
				// Print help
				case 'h':
					printHelp();
					break;
				// Reset
				case 'r':
					reset();
					break;
				}
			});
		}
	}

	/**
	 * Prints the current score board
	 */
	public void print() {
		// Retrieve sets
		List<IntScores> sets = scoreBoard.getFinishedSets();
		
		// Retrieve won sets
		IntScores nbSetsWon = scoreBoard.getSetsWon();
		
		// Retrieve won games
		IntScores nbGamesWon = scoreBoard.getGamesWon();
		
		// Retrieve current points
		String gameScoreP1 = scoreBoard.getCurrentPoints(Players.PLAYER_1);
		String gameScoreP2 = scoreBoard.getCurrentPoints(Players.PLAYER_2);
		
		// Write header
		System.out.println("------------------------------------------");
		System.out.println("\tPoints\tGames\tSets\tPrev. sets");
		System.out.println("------------------------------------------");
		
		// Write P1
		System.out.print("P1\t");
		
		System.out.print(gameScoreP1 + "\t");
		
		System.out.print(nbGamesWon.player1 + "\t");
		
		System.out.print(nbSetsWon.player1 + "\t");
		
		sets.forEach(score -> System.out.print(score.player1 + " "));
		System.out.print("\t");
		
		System.out.println();
		
		// Write P2
		System.out.print("P2\t");
		
		System.out.print(gameScoreP2 + "\t");
		
		System.out.print(nbGamesWon.player2 + "\t");
		
		System.out.print(nbSetsWon.player2 + "\t");
		
		sets.forEach(score -> System.out.print(score.player2 + " "));;
		
		System.out.println();
		
		// Write flags
		System.out.print("\t");
		
		System.out.print(scoreBoard.isDeuce() ? "DCE\t" : "");
		
		System.out.print(scoreBoard.isTieBreak() ? "TBK\t" : "\t");
		
		System.out.println();
		
		if (scoreBoard.isFinished()) {
			System.out.println(scoreBoard.getWinner() + " WINS !!!");
		}
	}
	
	public static void main(String[] args) throws IOException {
		new TennisMatchUI().launch(); 
	}
}
