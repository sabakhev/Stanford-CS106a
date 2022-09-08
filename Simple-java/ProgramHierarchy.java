/*
 * File: ProgramHierarchy.java
 * Name: 
 * Section Leader: 
 * ---------------------------
 * This file is the starter file for the ProgramHierarchy problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class ProgramHierarchy extends GraphicsProgram {
	private static final int Block_Height = 40;
	private static final int Block_Width = 120;
	public void run() {
		drawRect1();
		drawRect2();
		drawRect3();
		drawRect4();
		}
	private void drawRect1(){
		GRect rect1 = new GRect (Block_Width, Block_Height);
		add(rect1,getWidth()/2 -Block_Width/2 , getHeight()/2-2*Block_Height );
		GLabel label1 = new GLabel ("Program");
		label1.getWidth();
		label1.getAscent() ;		
		add(label1,getWidth()/2-label1.getWidth()/2 , getHeight()/2-1.5*Block_Height +label1.getAscent()/2 );
	}
	private void drawRect2() {
		GRect rect2 = new GRect (Block_Width, Block_Height);
		add(rect2, getWidth()/2 -Block_Width/2 , getHeight()/2 );
		GLine line2 = new GLine (getWidth()/2, getHeight()/2-Block_Height,getWidth()/2,getHeight()/2);
		add(line2);
		GLabel label2 = new GLabel ("Console Program");
		label2.getWidth();
		label2.getAscent() ;		
		add(label2,getWidth()/2-label2.getWidth()/2 , getHeight()/2+0.5*Block_Height +label2.getAscent()/2 );
	}
	private void drawRect3() {
		GRect rect3 = new GRect (Block_Width, Block_Height);
		add(rect3,getWidth()/2 -Block_Width/2- 1.5*Block_Width , getHeight()/2 );
		GLine line3 = new GLine (getWidth()/2, getHeight()/2-Block_Height, getWidth()/2-1.5*Block_Width, getHeight()/2 );
		add (line3);
		GLabel label3 = new GLabel ("Graphics Program");
		label3.getWidth();
		label3.getAscent() ;		
		add(label3,getWidth()/2-label3.getWidth()/2-Block_Width*1.5 , getHeight()/2+0.5*Block_Height +label3.getAscent()/2 );
	}
	private void drawRect4() {
		GRect rect4 = new GRect (Block_Width, Block_Height);
		add(rect4,getWidth()/2 -Block_Width/2+ 1.5*Block_Width , getHeight()/2 );
		GLine line4 = new GLine (getWidth()/2, getHeight()/2-Block_Height, getWidth()/2+1.5*Block_Width, getHeight()/2 );
		add (line4);
		GLabel label4 = new GLabel ("Dialog Program");
		label4.getWidth();
		label4.getAscent() ;		
		add(label4,getWidth()/2-label4.getWidth()/2+Block_Width*1.5 , getHeight()/2+0.5*Block_Height +label4.getAscent()/2 );
    }
}

