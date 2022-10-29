package org.openjfx.timelinechart;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import javax.annotation.Nonnull;

class NumberedNode extends Group {
	private final double SIDE_LENGTH = 5; 
	private final double NODE_NUMBER_SPACE_LENGTH = 5;
	
	NumberedNode(int number, @Nonnull Color color, double positionX, double positionY) {
		super();
		// create the square 
		var square = new Rectangle(positionX, positionY, SIDE_LENGTH, SIDE_LENGTH);
		square.setFill(color);
		// create the text node that has the number
		var textPositionX = positionX + SIDE_LENGTH + NODE_NUMBER_SPACE_LENGTH;
		var text = new Text(textPositionX, positionY, number+"");
		// adding the following into the region to be able to show them in the stage
		super.getChildren().addAll(square, text);
	}
}
