
/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import java.util.Arrays;

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {

	public static void main(String[] args) {
		new Yahtzee().start(args);
	}

	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		score = new int[N_CATEGORIES][nPlayers];
		setInitialScore();
		playGame();
	}

	// * This private method is responsible for conducting a game. It contains two
	// loops, one switches from one round to another, and second one switches from
	// one player to another.
	private void playGame() {
		int n = 0;
		while (n != N_SCORING_CATEGORIES) {
			for (int i = 1; i <= playerNames.length; i++) {
				display.printMessage(playerNames[i - 1] + "'s turn. Click \"Roll Dice\" button to roll the dice");
				int NUM_ROLLS = 3;
				int[] dice = new int[N_DICE];
				while (true) {
					display.waitForPlayerToClickRoll(i);
					if (NUM_ROLLS == 3) {
						dice = rollTheDiceForFirstTime();
						display.displayDice(dice);
					} else {
						dice = rollTheDice(dice);
						display.displayDice(dice);
					}
					NUM_ROLLS--;
					if (NUM_ROLLS == 0) {
						break;
					}
					display.printMessage(" select the dice you wish to re-roll and click ''ROLL AGAIN''");
					display.waitForPlayerToSelectDice();
				}
				display.printMessage(" select a category for this roll ");
				int category = display.waitForPlayerToSelectCategory();
				while(score[category-1][i-1]!=-1) {
					display.printMessage(" Please, select category that hasn't been chosen yet ");
					category = display.waitForPlayerToSelectCategory();
				}
				int scoreOfCategory = getScore(category, dice, i);
				display.updateScorecard(category, i, scoreOfCategory);
			}
			n++;
		}
		sumUpScores();
	}
	// * Sets starting value of a score matrix to help us determine whether some
	// category was already clicked on or not.
	private void setInitialScore() {
		for(int i=0; i<score.length; i++) {
			for(int j = 0; j<score[0].length; j++) {
				score[i][j]=-1;
			}
		}
	}

	// * The following two integers both return an array of dice after rolling.
	// Difference between them is that when rolling on a first time, program should
	// roll every dice, but after that only the selected dice should be rolled.
	private int[] rollTheDiceForFirstTime() {
		int[] dice = new int[N_DICE];
		for (int i = 0; i < dice.length; i++) {
			dice[i] = rgen.nextInt(1, 6);
		}
		return dice;
	}

	private int[] rollTheDice(int[] dice) {
		for (int i = 0; i < dice.length; i++) {
			if (display.isDieSelected(i)) {
				dice[i] = rgen.nextInt(1, 6);
			}
		}
		return dice;
	}

	// * This large method is responsible for two things: calculating the score for
	// indicated category that the player clicked on, and also updating the score
	// matrix (that keeps all of the scores) which I have defined as an instance
	// variable.

	private int getScore(int n, int[] dice, int player) {
		int categoryScore = 0;
		if (n == ONES) {
			categoryScore = sumOfFirstCategories(dice, 1);
			score[ONES - 1][player - 1] = categoryScore;
		} else if (n == TWOS) {
			categoryScore = sumOfFirstCategories(dice, 2);
			score[TWOS - 1][player - 1] = categoryScore;
		} else if (n == THREES) {
			categoryScore = sumOfFirstCategories(dice, 3);
			score[THREES - 1][player - 1] = categoryScore;
		} else if (n == FOURS) {
			categoryScore = sumOfFirstCategories(dice, 4);
			score[FOURS - 1][player - 1] = categoryScore;
		} else if (n == FIVES) {
			categoryScore = sumOfFirstCategories(dice, 5);
			score[FIVES - 1][player - 1] = categoryScore;
		} else if (n == SIXES) {
			categoryScore = sumOfFirstCategories(dice, 6);
			score[SIXES - 1][player - 1] = categoryScore;
		} else if (n == THREE_OF_A_KIND) {
			if (isSomethingOfAKind(dice, 3)) {
				categoryScore = sumUpDice(dice);
			}
			score[THREE_OF_A_KIND - 1][player - 1] = categoryScore;
		} else if (n == FOUR_OF_A_KIND) {
			if (isSomethingOfAKind(dice, 4)) {
				categoryScore = sumUpDice(dice);
			}
			score[FOUR_OF_A_KIND - 1][player - 1] = categoryScore;
		} else if (n == FULL_HOUSE) {
			if (isFullHouse(dice)) {
				categoryScore = 25;
			}
			score[FULL_HOUSE - 1][player - 1] = categoryScore;
		} else if (n == SMALL_STRAIGHT) {
			if (isStraight(dice, 4)) {
				categoryScore = 30;
			}
			score[SMALL_STRAIGHT - 1][player - 1] = categoryScore;
		} else if (n == LARGE_STRAIGHT) {
			if (isStraight(dice, 5)) {
				categoryScore = 40;
			}
			score[LARGE_STRAIGHT - 1][player - 1] = categoryScore;
		} else if (n == YAHTZEE) {
			if (isSomethingOfAKind(dice, 5)) {
				categoryScore = 50;
			}
			score[YAHTZEE - 1][player - 1] = categoryScore;
		} else if (n == CHANCE) {
			categoryScore = sumUpDice(dice);
			score[CHANCE - 1][player - 1] = categoryScore;
		}
		return categoryScore;
	}

	// * This simple method is called for the scores of first six categories. When
	// the given number occurs in dice, that number is added to the previous score.
	private int sumOfFirstCategories(int[] dice, int n) {
		int categoryScore = 0;
		for (int i = 0; i < dice.length; i++) {
			if (dice[i] == n) {
				categoryScore += n;
			}
		}

		return categoryScore;
	}

	// * Because we needed to sum the numbers on the dice quite often, it would be
	// convenient to have it as a method.
	private int sumUpDice(int[] dice) {
		int sum = 0;
		for (int i = 0; i < dice.length; i++) {
			sum += dice[i];
		}
		return sum;
	}

	// * This private method is called at the end of the game, to sum up the scores
	// in the instance matrix, update scores in the given categories and to decide
	// who will be the winner.
	private void sumUpScores() {
		int highestScore = 0;
		int winner = 0;
		for (int i = 0; i < nPlayers; i++) {
			int total = 0;
			int upperSum = 0;
			for (int j = 0; j < SIXES; j++) {
				upperSum += score[j][i];
			}
			display.updateScorecard(UPPER_SCORE, i + 1, upperSum);
			pause(500);
			int bonus = 0;
			if (upperSum >= 63) {
				bonus = 35;
				display.updateScorecard(UPPER_BONUS, i + 1, bonus);
			}
			pause(500);
			int lowerSum = 0;
			for (int j = SIXES; j < CHANCE; j++) {
				lowerSum += score[j][i];
			}
			display.updateScorecard(LOWER_SCORE, i + 1, lowerSum);
			pause(500);
			total = lowerSum + upperSum + bonus;
			if (total > highestScore) {
				highestScore = total;
				winner = i;
			}
			display.updateScorecard(TOTAL, i + 1, lowerSum + upperSum + bonus);
		}
		display.printMessage(" Congratulations, " + playerNames[winner] + ", you are the winner with a total score of "
				+ highestScore);

	}

	// * This method is called when checking dice for three different categories:
	// three of a kind, four of a kind and "Yahtzee". It checks for recurring
	// numbers after arranging the dice.
	private boolean isSomethingOfAKind(int[] dice, int something) {
		Arrays.sort(dice);
		int repeats = 1;
		int m = 0;
		for (int i = 0; i < dice.length; i++) {
			if (dice[i] == m) {
				repeats++;
			} else {
				repeats = 1;
			}
			if (repeats == something) {
				return true;
			}
			m = dice[i];
		}
		return false;
	}

	// * This private boolean checks if given dice satisfies criteria for it to be
	// qualified as a full house.
	private boolean isFullHouse(int[] dice) {
		Arrays.sort(dice);
		int previous = dice[0];
		int m = 1;
		int repeats = 1;
		for (int i = 1; i < dice.length; i++) {
			if (dice[i] != previous) {
				m++;
				repeats = 1;
			} else {
				repeats++;
			}
			previous = dice[i];
		}
		if (m == 2 & (repeats == 2 || repeats == 3)) {
			return true;
		}
		return false;
	}

	// * This method checks two categories, small and large straight. The logic
	// behind it is almost the same as boolean isSomethingOfAKind(), but instead of
	// checking for the same numbers, it checks for numbers different by one unit.
	private boolean isStraight(int[] dice, int something) {
		Arrays.sort(dice);
		int repeats = 1;
		int previous = dice[0];
		for (int i = 1; i < dice.length; i++) {
			if (dice[i] == previous + 1) {
				repeats++;
			} else {
				repeats = 1;
			}
			if (repeats == something) {
				return true;
			}
			previous = dice[i];
		}
		return false;
	}

	/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();

	private int[][] score;

}
