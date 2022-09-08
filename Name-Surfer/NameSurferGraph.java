
/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas implements NameSurferConstants, ComponentListener {

	/**
	 * Creates a new NameSurferGraph object that displays the data.
	 */
	public NameSurferGraph() {
		addComponentListener(this);
		list = new ArrayList<>();
	}

	/**
	 * Clears the list of name surfer entries stored inside this class.
	 */
	public void clear() {
		list.clear();
		update();
	}

	/* Method: addEntry(entry) */
	/**
	 * Adds a new NameSurferEntry to the list of entries on the display. Note that
	 * this method does not actually draw the graph, but simply stores the entry;
	 * the graph is drawn by calling update.
	 */
	public void addEntry(NameSurferEntry entry) {
		list.add(entry);
		update();
	}

	/**
	 * Updates the display image by deleting all the graphical objects from the
	 * canvas and then reassembling the display according to the list of entries.
	 * Your application must call update after calling either clear or addEntry;
	 * update is also called whenever the size of the canvas changes.
	 */
	public void update() {
		removeAll();
		drawVertically();
		drawHorizontally();
		if (!list.isEmpty()) {
			drawGraphs();
		}
	}

	// * Draws vertical lines to make graphs for different ranks throughout the
	// decade. It also adds a label of a decade in the lower part of the graph.
	private void drawVertically() {
		for (int i = 0; i < NDECADES; i++) {
			double x = i * (getWidth() / NDECADES);
			GLine line = new GLine(x, 0, x, getHeight());
			add(line);
			GLabel label = new GLabel(Integer.toString(START_DECADE + i * 10));
			add(label, i * (getWidth() / NDECADES) + (getWidth() / NDECADES) / 2 - label.getWidth() / 2,
					getHeight() - GRAPH_MARGIN_SIZE / 2 + label.getHeight() / 2);
		}
	}

	// * Draws two horizontal lines in the upper and lower parts of the graph.
	private void drawHorizontally() {
		for (int i = 0; i < 2; i++) {
			int y = GRAPH_MARGIN_SIZE + i * (getHeight() - 2 * GRAPH_MARGIN_SIZE);
			GLine line = new GLine(0, y, getWidth(), y);
			add(line);
		}
	}

	// * If the arraylist containing objects from namesurferentry class is not
	// empty, this method draws lines through appropriate ranks given in the decade.
	private void drawGraphs() {
		int n = 1;
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < NDECADES - 1; j++) {
				drawLine(i, j, n);
			}
			n += 1;
			if (n == 5) {
				n = 1;
			}
		}
	}

	// * this method draws one line and is called during each iteration in the for
	// loop.
	private void drawLine(int i, int j, int n) {
		NameSurferEntry entry = list.get(i);
		int a = entry.getRank(j);
		int b = entry.getRank(j + 1);
		double start_x = j * (getWidth() / NDECADES);
		double end_x = (j + 1) * (getWidth() / NDECADES);
		double start_y = GRAPH_MARGIN_SIZE + a * (getHeight() - 2 * GRAPH_MARGIN_SIZE) / MAX_RANK;
		double end_y = GRAPH_MARGIN_SIZE + b * (getHeight() - 2 * GRAPH_MARGIN_SIZE) / MAX_RANK;
		if (a == 0) {
			start_y = getHeight() - GRAPH_MARGIN_SIZE;
		}
		if (b == 0) {
			end_y = getHeight() - GRAPH_MARGIN_SIZE;
		}
		GLine line = new GLine(start_x, start_y, end_x, end_y);
		add(line);
		GLabel label = new GLabel(entry.getName() + " " + a);
		if (a == 0) {
			label.setLabel(entry.getName() + " " + '*');
		}
		add(label, start_x, start_y);
		if (j == NDECADES - 2) {
			GLabel lastLabel = new GLabel(entry.getName() + " " + b);
			if (b == 0) {
				lastLabel.setLabel(entry.getName() + " " + '*');
			}

			add(lastLabel, end_x, end_y);
			if (n == 2)
				lastLabel.setColor(Color.RED);
			if (n == 3)
				lastLabel.setColor(Color.BLUE);
			if (n == 4)
				lastLabel.setColor(Color.YELLOW);

		}
		if (n == 2) {
			line.setColor(Color.RED);
			label.setColor(Color.RED);
		}
		if (n == 3) {
			line.setColor(Color.BLUE);
			label.setColor(Color.BLUE);
		}
		if (n == 4) {
			line.setColor(Color.YELLOW);
			label.setColor(Color.YELLOW);
		}
	}

	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
		update();
	}

	public void componentShown(ComponentEvent e) {
	}

	private ArrayList<NameSurferEntry> list;
}
