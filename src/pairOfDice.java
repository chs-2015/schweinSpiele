/**
 * Author: Brandon B.
 * Date: 9-25-15
 * Description: Rolls a pair of dice!
 */

import java.util.Random;
public class pairOfDice {
	// Set up our glorious variables
	private Random numberGenerator = new Random();
	public int die1, die2;

    public pairOfDice() {
    	// This has no real meaning to us.
    }

    public void roll() {
    	// Roll the dice!
    	// We have to add one since we don't want a 0.
    	die1 = numberGenerator.nextInt(6) + 1;
    	die2 = numberGenerator.nextInt(6) + 1;
    }
}