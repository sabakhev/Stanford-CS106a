
/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import java.util.ArrayList;
import java.io.BufferedReader;

import acm.util.*;
import acmx.export.java.io.FileReader;

public class HangmanLexicon {

	private ArrayList<String> lexicon;

	// This is the HangmanLexicon constructor
	public HangmanLexicon() {
		try {
			lexicon = new ArrayList<>();
			BufferedReader br = new BufferedReader(new FileReader("HangmanLexicon.txt"));
			while (true) {
				lexicon.add(br.readLine());
				if (br.readLine() == null) {
					break;
				}
			}
			br.close();
		} catch (Exception e) {
			System.out.println("There was an unexpected eroor, try again :(" + e);
		}
	}

	// rest of HangmanLexicon class...

	/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return lexicon.size();
	}

	/** Returns the word at the specified index. */
	public String getWord(int index) {
		return lexicon.get(index);
	}
}
