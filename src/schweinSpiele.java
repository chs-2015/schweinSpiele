/**
 * Author: Brandon B.
 * Date: 9-25-15
 * Description: Jeder liebt das Schwein! (EVERYONE LOVES THE PIG!)
 */

import java.util.Scanner;

public class schweinSpiele {
    private static schweinSpieleAI schweinAI = new schweinSpieleAI();

    public static void main(String[] args) {
        System.out.println("Welcome to the pig game!");
        System.out.println("Your goal is to roll two die and reach 100 points first.");
        System.out.println("You can roll as many times as you want.");
        System.out.println("However, if you roll a one, you will lose all points from that round!");
        System.out.println("If you roll a pair of ones, you will lose all of your points!");
        System.out.println("You will be playing against a computer; it will release the dice after having");
        System.out.println("twenty or more points in the round.\n");

		schweinAI.pcPlayerHandoff();
        playerRoll();
    }

    public static void playerRoll() {
    	// Roll the dice!
    	schweinAI.roll();

    	// Here's what we have.
        System.out.printf("You rolled a %d and a %d!%n", schweinAI.dice.die1, schweinAI.dice.die2);

        // Should we add onto their round points?
        schweinAI.pointQualifier();

        // Can they play again?
        if (schweinAI.isPermittedToContinue())
            queryReplay();
        else
            schweinAI.playerPcHandoff();
    }

    private static void queryReplay() {
        // Asks the player if they want to continue this round.
        Scanner kbReader = new Scanner(System.in);
        final String userReplayYes = "y", userReplayNo = "n";
        String userReplay;

        System.out.printf("You currently have %d points in this round, and %d total.%n",
                schweinAI.activeRoundPoints, schweinAI.player1Points);
        System.out.print("Would you like to continue playing this round (y/n)? ");

        userReplay = kbReader.nextLine();
        userReplay = userReplay.toLowerCase();

        if (userReplay.equals(userReplayYes)) {
        	System.out.println();
            playerRoll();
        }
        else if (userReplay.equals(userReplayNo)) {
        	schweinAI.playerPcHandoff();
    	}
        else {
        	System.out.println("Invalid input!");
        	System.exit(0);
        }
    }
}
