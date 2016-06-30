package fr.sgcib.katatennis.scoreboard.pub;

/**
 * Holds the possible values for a normal game score.
 */
public enum TennisPoints {
	ZERO("0"),
	FIFTEEN("15"),
	THIRTY("30"),
	FORTY("40"),
	ADVANTAGE("A"),
	VICTORY("V");
	
	/**
	 * Label for the points
	 */
	private String label;
	
	private TennisPoints(String label) {
		this.label = label;
	}
	
	@Override
	public String toString() {
		return label;
	}
}
