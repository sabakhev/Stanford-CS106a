/*
 * File: Breakout.java
 * -------------------
 * Name: saba kheviashvili
 * Section Leader: 
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static int NTURNS = 3;
/** time of delay */
	private static final int DELAY=30;
	private static final int LOSING_DELAY=2000;
    private GRect paddle;
    private GOval ball;
    private static final double X_VELOCITY=0.0;
    private static final double Y_VELOCITY=3.0;
    private double vx=X_VELOCITY;
    private double vy=Y_VELOCITY;
    private int NUM_BLOCKS = NBRICKS_PER_ROW*NBRICK_ROWS;
    private final static double sep = (double)((WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) /(double) NBRICKS_PER_ROW)*NBRICKS_PER_ROW-BRICK_WIDTH*NBRICKS_PER_ROW;
    AudioClip bounceClip = MediaTools.loadAudioClip("bounce.au");
/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
		initialize();
		play();
	}
	// prepares the game to start
	private void initialize() {
		drawBricks();
		drawPaddle();
		addMouseListeners();
		drawBall();
	}
	// draws bricks by creating two loops 
	private void drawBricks() {
		for (int i=0; i<NBRICKS_PER_ROW; i++) {
			for (int x=0; x<NBRICK_ROWS; x++) {
			GRect rect = new GRect (BRICK_WIDTH, BRICK_HEIGHT);
			add(rect, sep/2 + i*(BRICK_WIDTH+BRICK_SEP), BRICK_Y_OFFSET+x*(BRICK_HEIGHT+BRICK_SEP) );
			pause(20);
			rect.setFilled(true);
			if(x<2) {
				rect.setFillColor(Color.RED); }
				else if (x<4) {
					rect.setFillColor(Color.ORANGE);
				}
				else if (x<6) {
					rect.setFillColor(Color.YELLOW);
				}
				else if (x<8) {
					rect.setFillColor(Color.GREEN);
				}
				else {
					rect.setFillColor(Color.CYAN);
				}
			}
		}
	}
	// draws paddle in the starting position 
	private void drawPaddle() {
		paddle = new GRect(PADDLE_WIDTH, PADDLE_HEIGHT) ;
		paddle.setFilled(true);
		add(paddle, WIDTH/2-PADDLE_WIDTH/2, HEIGHT -PADDLE_Y_OFFSET-PADDLE_HEIGHT);
	}
	// this method allows paddle to move along x direction
	public void mouseMoved (MouseEvent e) {
		paddle.setLocation(e.getX()-PADDLE_WIDTH/2, paddle.getY());
	}
    // draws the ball in the centre of the canvas 
	private void drawBall() {
		ball = new GOval(2*BALL_RADIUS, 2*BALL_RADIUS);
		ball.setFilled(true);
		add(ball, WIDTH/2, HEIGHT/2 );
	}
	private RandomGenerator rgen = RandomGenerator.getInstance();
	// this method describes the actual process of the game
	private void play() {
		startPlaying();
		defineMovementOfTheBall();
	}
	// startPlaying method initialises the ball movement 
	private void startPlaying() {
		vx=0;
		while(NTURNS!=0&NUM_BLOCKS!=0) {
			ball.move(vx, vy);
			pause(DELAY);
			if (getCollidingObject(ball.getX()+BALL_RADIUS, ball.getY()+2*BALL_RADIUS+vy) == paddle) {
				bounceBack();
				bounceClip.play();
				break;
			}
			if (ball.getY()+2*BALL_RADIUS+vy>HEIGHT) {
			defineLosing();
			break;
			}
		} 
	}
	// this method describes what happens when ball hits the paddle for the first time
	private void bounceBack() {
		vy = -vy;
		vx = rgen.nextDouble(1.0, 3.0);
		if (rgen.nextBoolean(0.5)) vx = -vx;
	}
	// general description of ball movement
	private void defineMovementOfTheBall() {
		while(ball.getY()+2*BALL_RADIUS+vy<HEIGHT&NTURNS!=0&NUM_BLOCKS!=0) {
			ball.move(vx, vy);
			pause(DELAY);
			defineCollision();
			defineWinning();
		}
		if(NTURNS!=0&NUM_BLOCKS!=0) {
		defineLosing();
		}
	}
	// this method defines three types od collisions
	private void defineCollision() {
		collidingVertically();
		collidingHorizontally();
		collidingWithWall();
	}
	// if ball collides on one of its vertical sides, this method changes Y velocity and removes colliding object if it's a block
	private void collidingVertically() {
		double X_CENTRE = ball.getX()+BALL_RADIUS;
		double Y = ball.getY();
		GObject collider1 = getCollidingObject(X_CENTRE, Y+vy); 
		 if (collider1 != null&collider1 != ball) {
			vy=-vy;
			remove(collider1);
			bounceClip.play();
			NUM_BLOCKS = NUM_BLOCKS-1;
			}
		GObject collider2 = getCollidingObject(X_CENTRE, Y+2*BALL_RADIUS+vy);
		if (collider2 == paddle) {
			vy=-vy;
			bounceClip.play();
		} else if (collider2 != null&collider2 != ball) {
			vy=-vy;
			remove(collider2);
			bounceClip.play();
			NUM_BLOCKS=NUM_BLOCKS-1;
			}
		
	}
	// this method checks east and west sides of the ball, bounces back if collision occurs and removes colliding object if it's a block
	private void collidingHorizontally() {
		double X = ball.getX();
		double Y_CENTER = ball.getY()+BALL_RADIUS;
		GObject collider1 = getCollidingObject(X+vx,Y_CENTER);
		if (collider1 == paddle) {
			vx=-vx;
			bounceClip.play();
		} else if (collider1 != null&collider1 != ball) {
			vx=-vx;
			remove(collider1);
			bounceClip.play();
			NUM_BLOCKS=NUM_BLOCKS-1;
			}
		GObject collider2 = getCollidingObject(X+2*BALL_RADIUS+vx,Y_CENTER);
		if (collider2 == paddle) {
			vx=-vx;
			bounceClip.play();
		} else if (collider2 != null&collider2 != ball) {
			vx=-vx;
			remove(collider2);
			NUM_BLOCKS=NUM_BLOCKS-1;
			bounceClip.play();
			}
	}
	// this private method returns object at the indicated point
	private GObject getCollidingObject(double x, double y) {
		GObject collidingObject;
		collidingObject = getElementAt(x, y); 
		return collidingObject;
	}
	// describes what happens when ball hits north, east and west sides of the canvas
	private void collidingWithWall() {
		if (ball.getX()+vx<=0) {
			vx=-vx;
		}
		if (ball.getX()+2*BALL_RADIUS+vx>=WIDTH) {
			vx=-vx;

		}
		if (ball.getY()+vy<=0) {
			vy=-vy;
		}
	}
	// this method describes what happens when ball hits the "floor", or when ball's south point is at the fourth side
	private void defineLosing() {
	        NTURNS = NTURNS - 1;
			remove(ball);
			GLabel label = new GLabel ("Remaining tries: " +NTURNS);
			label.getWidth();
			add(label,WIDTH/2-label.getWidth()/2, 3*HEIGHT/4);
			pause(LOSING_DELAY);
			remove(label);
			if(NTURNS!=0) {
				drawBall();
				play();
			}	else {
					GLabel label2 = new GLabel ("YOU HAVE LOST");
					label2.getWidth();
					add(label2,WIDTH/2-label2.getWidth()/2, 3*HEIGHT/4);
				}
	}
	// when there are no blocks left, defineWinning method is called and the appropriate message appears.
	private void defineWinning() {
		if (NUM_BLOCKS==0) {
			remove(ball);
			GLabel label = new GLabel ("CONGRATULATIONS, YOU HAVE WON!");
			label.getWidth();
			add(label,WIDTH/2-label.getWidth()/2, 3*HEIGHT/4);
		}
	}
}
