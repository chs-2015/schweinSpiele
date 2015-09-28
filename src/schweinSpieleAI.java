/**
 * Author: Brandon B.
 * Date: 9-25-15
 * Description: AN AI FOR SCHWEIN SPIELE, BECAUSE I CAN.
 */


public class schweinSpieleAI {
    /**
     * The AI for the pig game.
     * This class handles logic for the game.
     * The computer will roll up to 20 points when possible,
     * but must end when they roll a one, and send a
     * signal back to the main game process.
     * This also handles checking if the user is allowed
     * to continue playing.
     */

    // Who is currently playing?
    private static String activePlayer,
            activePlayerPerson = "Player 1",
            activePlayerComputer = "Computer";

	// If a 1 is rolled, the game or round is lost!
	private static final int diceEnd = 1;

    // Here are our glorious dice.
    public static pairOfDice dice = new pairOfDice();

    // Current points in the round.
    public static int activeRoundPoints = 0,  player1Points = 0, playerComputerPoints = 0;

    public schweinSpieleAI() {
    	// Set our active player to the computer.
    	// Wait for the client to handoff to itself.
        activePlayer = activePlayerComputer;
    }

    // Point functions

    private static void pointRoundModifier (int points) {
        // Modify the current points for the round.
        activeRoundPoints += points;
    }

    private static void pointTotalModifier (int points) {
        // Modify the total points for a player.
        if (activePlayer.equals(activePlayerPerson))
            player1Points += points;
        else
            playerComputerPoints += points;
    }

    private static void pointTotalReset () {
        // Resets the active players points (and round points) back to zero.
        activeRoundPoints = 0;
        if (activePlayer.equals(activePlayerPerson))
            player1Points = 0;
        else
            playerComputerPoints = 0;
    }

    private static int activePlayerTotal () {
    	if (activePlayer.equals(activePlayerPerson))
    		return player1Points;
    	else
    		return playerComputerPoints;
    }

    public static void pointQualifier () {
    	// 100 points to win!
    	final int winningPoints = 100;

        // This checks if they qualify for points.
        if (hasLostRound()) {
            // They've potentially lost the round, or the game.

            if (hasLostGame()) {
                // They lost the game!
                System.out.printf("%s has lost all of their points!%n%n", activePlayer);
                pointTotalReset();
            }
            else {
                // They only lost the round.
                System.out.printf("%s has lost the round!%n%n", activePlayer);
                activeRoundPoints = 0;
            }
        }
        else {
            // They haven't lost the round! Add points!
            activeRoundPoints += dice.die1 + dice.die2;

            if ((activeRoundPoints + activePlayerTotal()) >= winningPoints) {
            	System.out.printf("%s has won!%n", activePlayer);
            	System.exit(0);
            }
        }
    }

    // Round/game lost verifiers

    private static boolean hasLostRound() {
        // Has the round been lost?

        if (((dice.die1 == diceEnd) && (dice.die2 != diceEnd)) || ((dice.die1 != diceEnd) && (dice.die2 == diceEnd))) {
            // They have rolled a SINGLE one.
            return true;
        } else if (dice.die1 == diceEnd && dice.die2 == diceEnd) {
            // They have lost the game!
            return true;
        } else {
            // Well, we got here. They won this round.
            return false;
        }
    }

    private static boolean hasLostGame() {
        // Has the game been lost?

        if (dice.die1 == diceEnd && dice.die2 == diceEnd) {
            if (activePlayer.equals(activePlayerPerson))
                player1Points = 0;
            else
                playerComputerPoints = 0;

            return true;
        } else {
            return false;
        }
    }

    public static boolean isPermittedToContinue() {
        if (hasLostRound() || hasLostGame())
            return false;
        else
            return true;
    }

    // Gameplay handoffs

    private static void handoffCleanup() {
        // Cleanup variables.
        pointTotalModifier(activeRoundPoints);
        System.out.printf("===> %s currently has %d total points, and %s has %d total points.%n",
        	activePlayerPerson, player1Points, activePlayerComputer, playerComputerPoints);
        activeRoundPoints = 0;
    }

    public static void pcPlayerHandoff() {
        // The gameplay is now going from the computer to the player.
        handoffCleanup();

        activePlayer = activePlayerPerson;
        System.out.println("====> It is now the player's turn!\n");
        schweinSpiele.playerRoll();
    }

    public static void playerPcHandoff() {
        // The gameplay is now going from the player to the computer.
        handoffCleanup();

        activePlayer = activePlayerComputer;
        System.out.println("====> It is now the computer's turn!\n");

        // Begin playing as the computer.
        pcPlay();
    }

	// Rolling
	public static void roll() {
		dice.roll();
	}

    // PC Player

    private static void pcPlay() {
    	// The computer will roll up to 20 points.
    	final int maxPointsComputer = 20;

        while (activeRoundPoints < maxPointsComputer) {
            roll();

            System.out.printf("The computer rolled a %d and a %d!%n", dice.die1, dice.die2);

            pointQualifier();

            if(!isPermittedToContinue())
                break;
        }

        if (activeRoundPoints >= maxPointsComputer)
            System.out.println("The computer has quit the round!\n");
        pcPlayerHandoff();
    }
}
