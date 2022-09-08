
/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.graphics.GLabel;
import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {

	/* Method: init() */
	/**
	 * This method has the responsibility for reading in the data base and
	 * initializing the interactors at the bottom of the window.
	 */
	private JTextField field;
	private static final int FIELD_SIZE = 15;
	private JButton graphButton;
	private JButton clear;

	private NameSurferGraph graph;

	public void init() {

		graph = new NameSurferGraph();
		add(graph);
		graph.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);

		addActionListeners();
		JLabel label = new JLabel("Name");

		field = new JTextField(FIELD_SIZE);
		field.addActionListener(this);

		graphButton = new JButton("Graph");
		graphButton.addActionListener(this);

		clear = new JButton("Clear");
		clear.addActionListener(this);

		add(label, SOUTH);
		add(field, SOUTH);
		add(graphButton, SOUTH);
		add(clear, SOUTH);

	}

	/* Method: actionPerformed(e) */
	/**
	 * This class is responsible for detecting when the buttons are clicked, so you
	 * will have to define a method to respond to button actions.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == graphButton & !field.getText().isEmpty()) {
			String line = field.getText();
			NameSurferDataBase text = new NameSurferDataBase("names-data.txt");
			if (text.findEntry(line) != null) {
				graph.addEntry(text.findEntry(line));
			}
			field.setText("");
		}
		if (e.getSource() == clear) {
			graph.clear();
		}
	}
}
