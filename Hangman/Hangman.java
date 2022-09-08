
/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;

public class Hangman extends ConsoleProgram {
	private int TURNS = 8;
	private boolean p = false;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private HangmanCanvas canvas;

	public void init() {
		canvas = new HangmanCanvas();
		add(canvas);
	}

	public void run() {
		graphicsVersion();
		consoleVersion();
	}

	private void consoleVersion() {
		HangmanLexicon wordSet = new HangmanLexicon();
		println("Welcome to the Hangman game!");
		int n = rgen.nextInt(wordSet.getWordCount());
		String secretWord = wordSet.getWord(n);
		String cypheredText = "";
		for (int i = 0; i < secretWord.length(); i++) {
			cypheredText = cypheredText + "-";
		}
		println("The word now looks like this: " + cypheredText);
		while (TURNS != 0) {
			canvas.displayWord(cypheredText);
			char guess = returnChar(readLine("YOUR GUESS: "));
			if (!cypheredText.contains(guess + "") & !cypheredText.contains((guess + "").toUpperCase())) {
				cypheredText = checkTheGuess(guess, secretWord, cypheredText);
				printOut(cypheredText, guess, secretWord);
			}
			// * If there are no more "-"'s in the word, this means player has guessed all
			// of the characters and he has won.
			if (!cypheredText.contains("-")) {
				println("YOU HAVE WON!");
				pause(2000);
				canvas.reset();
				p = true;
				break;
			}
		}
		TURNS = 8;
		p = false;
		consoleVersion();
	}

	// * This method reads the input string from the console and returns appropriate
	// char.
	// * To avoid ambiguity and unnecessary errors, method asks the player to enter
	// the input again if it is empty or contains more than two characters.
	private char returnChar(String str) {
		if (str.isEmpty()) {
			println("You have not entered anything");
			char ch = returnChar(readLine("YOUR GUESS: "));
			return ch;
		} else if (str.length() > 1) {
			println("Please, enter only one character");
			char ch = returnChar(readLine("YOUR GUESS: "));
			return ch;
		} else {
			char ch = str.charAt(0);
			if (!((ch >= 65 & ch <= 90) || (ch >= 97 & ch <= 122))) {
				println("You have entered the wrong character");
				ch = returnChar(readLine("YOUR GUESS: "));
			}
			return ch;
		}
	}

	// * Given method checks if the returned char from previous method is in our
	// secret word and returns changed ciphered text if the player is correct. If
	// not, it returns exactly the same one
	// * We have to check both upper and lower case characters and treat them
	// equally.
	private String checkTheGuess(char guess, String secretWord, String cypheredText) {
		String newCypheredText = "";
		for (int i = 0; i < secretWord.length(); i++) {
			if (secretWord.charAt(i) == guess || (guess + "").toUpperCase().charAt(0) == secretWord.charAt(i)) {
				newCypheredText += secretWord.charAt(i);
				p = true;
			} else
				newCypheredText += cypheredText.charAt(i);
		}
		return (newCypheredText);
	}

	// * This method is used after every guess to update ciphered in the console and
	// print appropriate messages.
	// * If the guess is incorrect, the number of turns decreases by one. If it
	// becomes zero, player loses.
	private void printOut(String cypheredText, char guess, String secretWord) {
		if (p) {
			println("Your guess is correct!");
			println("The word now looks like this: " + cypheredText);
			println("You have " + TURNS + " tries remaining");
		}
		if (!p) {
			println("There are no " + guess + "'s in this word");
			canvas.noteIncorrectGuess(guess);
			TURNS--;
			if (TURNS == 0) {
				println("You are completely hung");
				println("The word was " + secretWord);
				println("YOU HAVE LOST!");
				pause(2000);
				canvas.reset();
			} else {
				println("The word now looks like this: " + cypheredText);
				println("You have " + TURNS + " tries remaining");
			}
		}
		p = false;
	}

	private void graphicsVersion() {
		canvas.reset();
	}
}
