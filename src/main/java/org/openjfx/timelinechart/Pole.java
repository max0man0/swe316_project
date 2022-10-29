package org.openjfx.timelinechart;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javax.annotation.Nonnull;

class Pole extends Group {
	private final ObservableList<NumberedNode> nodes;
	private final LocalDate date;
	private final double x;
	private final double startY;
	private Line verticalLine;
	private final double BEFORE_NODE_SPACE_LENGTH = 20;
	private final double TEXT_SPACE_LENGTH_BELOW_POLE = 50;
	private final double TEXT_FONT_SIZE = 10;
	
	Pole(@Nonnull LocalDate date, double x, double startY) {
		super();
		this.date = date;
		this.x = x;
		this.startY = startY;
		// create a dot instead of a line since there are no nodes yet
		verticalLine = new Line(x, startY, x, startY);
		verticalLine.setStroke(Color.ORANGE);
		nodes = FXCollections.observableArrayList();
	}
	
	void addNode(int number, Color nodeColor) {
		double poleNewHeight = (nodes.size()+1) * BEFORE_NODE_SPACE_LENGTH;
		// add the new node to the list
		nodes.add(new NumberedNode(number, nodeColor, x, startY - poleNewHeight));
		// extend the vertical line
		verticalLine = new Line(x, startY - poleNewHeight, x, startY);
		verticalLine.setStroke(Color.ORANGE);
		draw(nodeColor);
	}

	private void draw(Color textFontColor) {
		super.getChildren().clear();
		String formattedDate = date.format(DateTimeFormatter.ofPattern("MM/dd/uuuu"));
		Text dateText = new Text(formattedDate);
		dateText.setFill(textFontColor);
		dateText.setFont(Font.font(TEXT_FONT_SIZE));
		dateText.setRotate(50);
		dateText.relocate(x-25, startY + TEXT_SPACE_LENGTH_BELOW_POLE);
		super.getChildren().add(verticalLine);
		super.getChildren().addAll(nodes);
		super.getChildren().add(dateText);
	}

	LocalDate getDate() { return date; }

	double getX() { return x; }

	Line getVerticalLine() { return verticalLine; }


}

