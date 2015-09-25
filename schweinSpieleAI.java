/**
 * Author: Brandon B.
 * Date: 9-25-15
 * Description: AN AI FOR SCHWEIN SPIELE, BECAUSE I CAN.
 */


public class schweinSpeileAI {
	/** The AI for the pig game.
	 * This class handles logic for the game.
	 * The computer will roll up to 20 points when possible,
	 * but must end when they roll a one, and send a
	 * signal back to the main game process.
	 * This also handles checking if the user is allowed
	 * to continue playing.
	 */

	// Who is currently playing?
	private String activePlayer,
	activePlayerPerson = "Player 1",
	activePlayerComputer = "Computer";

	// Number generator
	private pairOfDice dice = new pairofDice();

	// The computer will roll until 20 points max.
	private final int maxPointsComputer = 20;

	// 100 points to win. Roll a 1 to lose the round or the game.
	public final int winningPoints = 100, diceEnd = 1; // 100 points to win, roll a 1 to to lose the round/game.

	// Current points in the round.
	public int activeRoundPoints = 0,
	player1Points = 0, playerComputerPoints = 0;

    public schweinSpieleAI() {
    	// This has no purpose.
    }

    private void pcPointQualifier (int die1, int die2) {
    	// This checks if the computer qualifies for points.
    	if (hasLostRound(die1, die2)) {
    		// The computer potentially lost the round, or the game.

    		if (hasLostGame(die1, die2)) {
    			// The computer lost the game!
    			System.out.println("The computer has lost the game!");
    		}
    		else {
    			// They only lost the round.
    			System.out.println("The computer has lost the round!");
    		}
    	}
    	else {
    		// They haven't lost the round! Add points!
			activeRoundPoints = die1 + die2;
    	}
    }

	public boolean hasLostRound(int die1, int die2) {
		// Has the round been lost?

		if (((die1 == diceEnd) && (die2 != diceEnd)) || ((die1 != diceEnd) && (die2 == diceEnd))) {
			// They have rolled a SINGLE one.
			return true;
		}
		else if (die1 == diceEnd && die2 == diceEnd) {
			// They have lost the game!
			return true;
		}
		else {
			// Well, we got here. They won this round.
			return false;
		}
	}

	public boolean hasLostGame(int die1, int die2) {
		// Has the game been lost?

		if (die1 == diceEnd && die2 == diceEnd) {
			return true;
		}
		else {
			return false;
		}
	}

	private void handoffCleanup () {
		// Cleanup variables.
		if activePlayer.equals(activePlayerPerson)
			player1Points += activeRoundPoints;
		else if activePlayer.equals(activePlayerComputer)
			playerComputerPoints += activeRoundPoints;
		else
			throw new schweinSpieleAIException("Invalid player in cleanup!");

		activeRoundPoints = 0;
	}

    public void pcPlayerHandoff () {
    	// The gameplay is now going from the computer to the player.
    	handoffCleanup();

    	activePlayer = activePlayerPerson;
    	System.out.println("===> It is now the player's turn!");
    }

    public void playerPcHandoff () {
    	// The gameplay is now going from the player to the computer.
    	handoffCleanup();

    	activePlayer = activePlayerComputer;
    	System.out.println("===> It is now the computer's turn!");

    	// Begin playing as the computer.
    	pcPlay();
    }

    private void pcPlay() {
    	// Plays for the computer.
    }
}

private class schweinSpieleAIException extends Exception {
	public schweinSpieleAIException(String message) {
		super(message)
	}
}