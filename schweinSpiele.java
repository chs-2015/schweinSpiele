/**
 * Author: Brandon B.
 * Date: 9-25-15
 * Description: Jeder liebt das Schwein! (EVERYONE LOVES THE PIG!)
 */

import java.util.Scanner;
public class schweinSpiele {
	private static schweinSpieleAI schweinAI = new schweinSpieleAI();
	private static pairOfDice dice = new pairOfDice();

    public static void main(String[] args) {
    	System.out.println("main: Add rules and begin rolling!");
    }

    private static void pointQualifier (int die1, int die2) {
		// This checks if they qualify for points.
		if (schweinAI.hasLostRound(die1, die2)) {
			// They've potentially lost the round, or the game.

			if (schweinAI.hasLostGame(die1, die2)) {
				// They lost the game!
				System.out.println("You've lost the game!");
			}
			else {
				// They only lost the round.
				System.out.println("You've lost the round!");
			}
		}
		else {
			// They haven't lost the round! Add points!
			schweinAI.roundPoints = die1 + die2;
		}
    }
}
