/*
 * File: FindRange.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the FindRange problem.
 */

import acm.program.*;

public class FindRange extends ConsoleProgram {
	private static final int sentinel=0;
	public void run() {
	println("This program finds the maximum and minimum from the numbers");
	int n = readInt("enter value:");
	if (n==sentinel) {
		println("you have only entered the sentinel");
	}
	else {
	  int min=n;
	  int max=n;
	  while(true) {
		int x = readInt("enter value:");
		if(x==sentinel) break;
		if(x>max) {
			max=x;
		}
		if(x<min) {
			min=x; 
		}
	  }
	  println("The maximum is "+ max);
	  println("The minimum is "+ min);
	}
  }	
}

