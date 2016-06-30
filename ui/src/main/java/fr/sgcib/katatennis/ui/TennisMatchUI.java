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
	
	private boolean autoPrint;
	
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
		autoPrint = false;
	}
	
	/**
	 * Prints a message indicating the available commands
	 */
	public void printHelp() {
		System.out.println("Use the following commands :\n");
		System.out.println("Scores manipulation");
		System.out.println("  a\tScore one point for player A");
		System.out.println("  b\tScore one point for player B");
		System.out.println("  A\tScore 4 points for player A");
		System.out.println("  B\tScore 4 points for player B");
		System.out.println("  0\tResets the scores");
		System.out.println("Program manipulation");
		System.out.println("  ?\tPrint the list of available commands");
		System.out.println("  :\tPrint the score board");
		System.out.println("  !\tSwitch on/off auto print scores after each score modification");
		System.out.println("  x\tExit the program");
		System.out.println("Notes :");
		System.out.println(" - You can construct a string with multiple commands in one time. Example : 'aaBp' scores 2 points for the player A, 4 for player B and then prints the score board.");
		System.out.println(" - Unknown commands are ignored. Use this to put some spaces ! Example : 0 AAAAA: BBBBB: A: B:");
		System.out.println();
	}
	
	/**
	 * Processes the input from the command line
	 * @throws IOException
	 */
	private void processCommands() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String commands;
		
		while ((commands = reader.readLine()) != null) {
			
			commands.chars().forEach(command -> {
				switch (command) {
				// Score P1
				case 'a':
					scoreBoard.scorePoint(Players.PLAYER_1);
					autoPrint();
					break;
					
				// Score P2
				case 'b':
					scoreBoard.scorePoint(Players.PLAYER_2);
					autoPrint();
					break;
					
				// Score 4 pts for P1
				case 'A':
					for (int score = 0; score < 4; score ++) {
						scoreBoard.scorePoint(Players.PLAYER_1);
					}
					autoPrint();
					break;
					
				// Score 4 pts for P1
				case 'B':
					for (int score = 0; score < 4; score ++) {
						scoreBoard.scorePoint(Players.PLAYER_2);
					}
					autoPrint();
					break;
				
				// Reset the scores
				case '0':
					reset();
					autoPrint();
					break;
					
				// Print help
				case '?':
					printHelp();
					break;
					
				// Print board
				case ':':
					printScoreBoard();
					break;
					
				// Switch auto-print
				case '!':
					autoPrint = !autoPrint;
					break;
					
				// Quit
				case 'x':
					System.exit(0);
					break;
					
				}
			});
		}
	}
	
	/**
	 * If autoPrint is enabled, prints the board
	 */
	private void autoPrint() {
		if (autoPrint) {
			printScoreBoard();
		}
	}

	/**
	 * Prints the current score board
	 */
	public void printScoreBoard() {
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
		System.out.print(scoreBoard.isDeuce() ? "DCE\t" : "\t");
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
