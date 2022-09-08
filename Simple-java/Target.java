
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import javax.swing.*;

import acm.graphics.GCanvas;
import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.program.ConsoleProgram;
import acm.program.GraphicsProgram;
import acm.program.Program;
import acm.util.RandomGenerator;

public class Target extends ConsoleProgram {
	private RandomGenerator rgen = RandomGenerator.getInstance();

	public void run() {
	}
	private double simulateStrategy(int n) {
		int counter = 0;
		for(int i = 0; i <1000; i++){
			double money = (double)n;
			int guess = rgen.nextInt(0, 36);
			int number = rgen.nextInt(0, 36);
			double bet = 1;
			money -= bet;
			while(true){
				if(guess == number){
					money += bet*36; 
				} else {
					if(money==0){
						break;
					}
					money -= bet;
				}
				if(money>=n+1){
					counter++;
					break;
				}
				guess = rgen.nextInt(0, 36);
			    number = rgen.nextInt(0, 36);
				if(money >= (n + 1 - money)/36.0  ){
				   bet = (n + 1 - money)/36.0; 
				} else {
					bet = money;
				}
				money-=bet;
			}
		}
		return (double)(counter)/1000.0;
	}
}