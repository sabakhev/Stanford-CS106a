
/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import java.awt.Canvas;

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {

	private String errors = "";
	private int n = 9;
	private GOval head;
	private GLine body;
	private GLine upperArm1;
	private GLine lowerArm1;
	private GLine upperArm2;
	private GLine lowerArm2;
	private GLine leftHip;
	private GLine rightHip;
	private GLine leftLeg;
	private GLine rightLeg;
	private GLine leftFoot;
	private GLine rightFoot;

	/** Resets the display so that only the scaffold appears */
	// * This void is called in the console program at the end of the game to start
	// the game again. To avoid error of removing object that has value null, this
	// void checks every single object, if they aren't null, it removes them.
	public void reset() {
		initialize(SCAFFOLD_Y);
		if (rightFoot != null) {
			remove(rightFoot);
			pause(200);
		}
		if (leftFoot != null) {
			remove(leftFoot);
			pause(200);
		}
		if (rightLeg != null) {
			remove(rightLeg);
			pause(200);
		}
		if (leftLeg != null) {
			remove(leftLeg);
			pause(200);
		}
		if (rightHip != null) {
			remove(rightHip);
			pause(200);
		}
		if (leftHip != null) {
			remove(leftHip);
			pause(200);
		}
		if (lowerArm2 != null) {
			remove(lowerArm2);
			pause(200);
		}
		if (upperArm2 != null) {
			remove(upperArm2);
			pause(200);
		}
		if (lowerArm1 != null) {
			remove(lowerArm1);
			pause(200);
		}
		if (upperArm1 != null) {
			remove(upperArm1);
			pause(200);
		}
		if (body != null) {
			remove(body);
			pause(200);
		}
		if (head != null) {
			remove(head);
			pause(200);
		}
		if (getElementAt(DISPLAY_WORD_X, DISPLAY_WORD_Y) != null)
			remove(getElementAt(DISPLAY_WORD_X, DISPLAY_WORD_Y));
		if (getElementAt(ERRORS_X, ERRORS_Y) != null) {
			errors = "";
			remove(getElementAt(ERRORS_X, ERRORS_Y));
		}
		n = 9;
	}

	/**
	 * Updates the word on the screen to correspond to the current state of the
	 * game. The argument string shows what letters have been guessed so far;
	 * unguessed letters are indicated by hyphens.
	 */
	// * This void removes old label, and draws a new one with updated string.
	public void displayWord(String word) {
		GObject oldLabel = getElementAt(DISPLAY_WORD_X, DISPLAY_WORD_Y);
		if (oldLabel != null) {
			remove(oldLabel);
		}
		GLabel displayWord = new GLabel(word, DISPLAY_WORD_X, DISPLAY_WORD_Y);
		add(displayWord);
	}

	/**
	 * Updates the display to correspond to an incorrect guess by the user. Calling
	 * this method causes the next body part to appear on the scaffold and adds the
	 * letter to the list of incorrect guesses that appears at the bottom of the
	 * window.
	 */
	// * This void checks the guess. If it is incorrect, it updates the canvas as
	// necessary.
	public void noteIncorrectGuess(char letter) {
		GObject oldError = getElementAt(ERRORS_X, ERRORS_Y);
		if (oldError != null) {
			remove(oldError);
		}
		errors += letter;
		GLabel noteIncorrectGuess = new GLabel(errors, ERRORS_X, ERRORS_Y);
		add(noteIncorrectGuess);
		n--;
		if (n == 8)
			drawHead();
		if (n == 7)
			drawBody();
		if (n == 6)
			drawLeftArm();
		if (n == 5)
			drawRightArm();
		if (n == 4)
			drawLeftHip();
		if (n == 3)
			drawRightHip();
		if (n == 2)
			drawLeftLimb();
		if (n == 1) {
			drawRightLimb();
			remove(noteIncorrectGuess);
			errors = "";
		}

	}

	// * Draws the scaffold, beam and rope which remain unchanged throughout the
	// whole game.
	private void initialize(int y_offset) {
		GLine scaffold = new GLine(getWidth() / 2 - BEAM_LENGTH, y_offset, getWidth() / 2 - BEAM_LENGTH,
				y_offset + SCAFFOLD_HEIGHT);
		add(scaffold);
		GLine beam = new GLine(getWidth() / 2 - BEAM_LENGTH, y_offset, getWidth() / 2, y_offset);
		add(beam);
		GLine rope = new GLine(getWidth() / 2, y_offset, getWidth() / 2, y_offset + ROPE_LENGTH);
		add(rope);
	}

	// * This set of private voids just draws the appropriate body parts.
	private void drawHead() {
		head = new GOval(2 * HEAD_RADIUS, 2 * HEAD_RADIUS);
		add(head, getWidth() / 2 - HEAD_RADIUS, SCAFFOLD_Y + ROPE_LENGTH);
	}

	private void drawBody() {
		body = new GLine(getWidth() / 2, SCAFFOLD_Y + ROPE_LENGTH + 2 * HEAD_RADIUS, getWidth() / 2,
				SCAFFOLD_Y + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH);
		add(body);
	}

	private void drawLeftArm() {
		int X = getWidth() / 2 - UPPER_ARM_LENGTH / 2;
		upperArm1 = new GLine(X, SCAFFOLD_Y + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD,
				X + UPPER_ARM_LENGTH / 2, SCAFFOLD_Y + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD);
		lowerArm1 = new GLine(X, SCAFFOLD_Y + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD, X,
				SCAFFOLD_Y + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH);
		add(upperArm1);
		add(lowerArm1);
	}

	private void drawRightArm() {
		int X = getWidth() / 2 - UPPER_ARM_LENGTH / 2;
		upperArm2 = new GLine(X + UPPER_ARM_LENGTH / 2,
				SCAFFOLD_Y + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD, X + UPPER_ARM_LENGTH,
				SCAFFOLD_Y + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD);
		lowerArm2 = new GLine(X + UPPER_ARM_LENGTH, SCAFFOLD_Y + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD,
				X + UPPER_ARM_LENGTH,
				SCAFFOLD_Y + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH);
		add(upperArm2);
		add(lowerArm2);
	}

	private void drawLeftHip() {
		leftHip = new GLine(getWidth() / 2, SCAFFOLD_Y + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH,
				getWidth() / 2 - HIP_WIDTH / 2, SCAFFOLD_Y + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH);
		add(leftHip);
	}

	private void drawRightHip() {
		rightHip = new GLine(getWidth() / 2, SCAFFOLD_Y + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH,
				getWidth() / 2 + HIP_WIDTH / 2, SCAFFOLD_Y + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH);
		add(rightHip);
	}

	private void drawLeftLimb() {
		leftLeg = new GLine(getWidth() / 2 - HIP_WIDTH / 2, SCAFFOLD_Y + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH,
				getWidth() / 2 - HIP_WIDTH / 2, SCAFFOLD_Y + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
		leftFoot = new GLine(getWidth() / 2 - HIP_WIDTH / 2,
				SCAFFOLD_Y + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH,
				getWidth() / 2 - HIP_WIDTH / 2 - FOOT_LENGTH,
				SCAFFOLD_Y + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
		add(leftLeg);
		add(leftFoot);
	}

	private void drawRightLimb() {
		rightLeg = new GLine(getWidth() / 2 + HIP_WIDTH / 2, SCAFFOLD_Y + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH,
				getWidth() / 2 + HIP_WIDTH / 2, SCAFFOLD_Y + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
		rightFoot = new GLine(getWidth() / 2 + HIP_WIDTH / 2,
				SCAFFOLD_Y + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH,
				getWidth() / 2 + HIP_WIDTH / 2 + FOOT_LENGTH,
				SCAFFOLD_Y + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH);
		add(rightLeg);
		add(rightFoot);
	}

	private void pause(int n) {
		try {
			Thread.sleep(n);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;
	/* Some coordinates of the picture version (in pixels) */
	private static final int SCAFFOLD_Y = 25;
	private static final int DISPLAY_WORD_X = 30;
	private static final int DISPLAY_WORD_Y = SCAFFOLD_Y + SCAFFOLD_HEIGHT + 25;
	private static final int ERRORS_X = 30;
	private static final int ERRORS_Y = DISPLAY_WORD_Y + 25;
}
