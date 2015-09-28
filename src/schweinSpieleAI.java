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

    // The computer will roll until 20 points max.
    private static final int maxPointsComputer = 20;

    // 100 points to win. Roll a 1 to lose the round or the game.
    public static final int winningPoints = 100, diceEnd = 1; // 100 points to win, roll a 1 to to lose the round/game.

    // Current points in the round.
    public static int activeRoundPoints = 0,  player1Points = 0, playerComputerPoints = 0;

    public schweinSpieleAI() {
        activePlayer = activePlayerComputer;
    }

    // Add/remove points as necessary

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
        // Resets the active players points back to zero.
        if (activePlayer.equals(activePlayerPerson))
            player1Points = 0;
        else
            playerComputerPoints = 0;
    }

    public static void pointQualifier (int die1, int die2) {
        // This checks if they qualify for points.
        if (hasLostRound(die1, die2)) {
            // They've potentially lost the round, or the game.

            if (hasLostGame(die1, die2)) {
                // They lost the game!
                System.out.printf("%s has lost all of their points!%n", activePlayer);
                pointTotalReset();
            }
            else {
                // They only lost the round.
                System.out.printf("%s has lost the round!%n", activePlayer);
                activeRoundPoints = 0;
            }
        }
        else {
            // They haven't lost the round! Add points!
            activeRoundPoints += die1 + die2;
        }
    }

    // Round/game lost verifiers

    public static boolean isPermittedToContinue(int die1, int die2) {
        if (hasLostRound(die1, die2) || hasLostGame(die1, die2))
            return false;
        else
            return true;
    }

    private static boolean hasLostRound(int die1, int die2) {
        // Has the round been lost?

        if (((die1 == diceEnd) && (die2 != diceEnd)) || ((die1 != diceEnd) && (die2 == diceEnd))) {
            // They have rolled a SINGLE one.
            return true;
        } else if (die1 == diceEnd && die2 == diceEnd) {
            // They have lost the game!
            return true;
        } else {
            // Well, we got here. They won this round.
            return false;
        }
    }

    private static boolean hasLostGame(int die1, int die2) {
        // Has the game been lost?

        if (die1 == diceEnd && die2 == diceEnd) {
            if (activePlayer.equals(activePlayerPerson))
                player1Points = 0;
            else
                playerComputerPoints = 0;

            return true;
        } else {
            return false;
        }
    }

    // Gameplay handoffs

    private static void handoffCleanup() {
        // Cleanup variables.
        pointTotalModifier(activeRoundPoints);
        activeRoundPoints = 0;
    }

    public static void pcPlayerHandoff() {
        // The gameplay is now going from the computer to the player.
        handoffCleanup();

        activePlayer = activePlayerPerson;
        System.out.println("===> It is now the player's turn!");
        schweinSpiele.playerRoll();
    }

    public static void playerPcHandoff() {
        // The gameplay is now going from the player to the computer.
        handoffCleanup();

        activePlayer = activePlayerComputer;
        System.out.println("===> It is now the computer's turn!");

        // Begin playing as the computer.
        pcPlay();
    }

    // PC Player

    private static void pcPlay() {
        pairOfDice dice = new pairOfDice();

        while (activeRoundPoints < maxPointsComputer) {
            dice.roll();
            System.out.printf("The computer rolled a %d and a %d!%n", dice.die1, dice.die2);

            pointQualifier(dice.die1, dice.die2);

            if(!isPermittedToContinue(dice.die1, dice.die2))
                break;
        }

        if (activeRoundPoints >= maxPointsComputer)
            System.out.println("The computer has quit the round!");
        pcPlayerHandoff();
    }
}
