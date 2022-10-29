package org.openjfx.timelinechart;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

class EventLine extends Group {
    private final double EDGE_HEIGHT = 10;
    private final double TEXT_LINE_SEPARATION_LENGTH = 20;
    private final Line hLine;
    private final Line startEdge;
    private final Line endEdge;
    private final Text durationText;

    EventLine(int eventDayCount, double startX, double endX, double y) {
        super();
        this.hLine = new Line(startX, y, endX, y);
        this.startEdge = new Line(startX, y-EDGE_HEIGHT/2, startX, y+EDGE_HEIGHT/2);
        this.endEdge = new Line(endX, y-EDGE_HEIGHT/2, endX, y+EDGE_HEIGHT/2);
        var textY = y - TEXT_LINE_SEPARATION_LENGTH;
        var textX = (endX + startX) / 2 - 50;
        this.durationText = new Text(textX, textY, String.format("Duration = %s days", eventDayCount));
        draw(Color.RED);
    }

    private void draw(Color lineColor) {
        hLine.setStroke(lineColor);
        startEdge.setStroke(lineColor);
        endEdge.setStroke(lineColor);
        durationText.setFill(lineColor);
        super.getChildren().setAll(startEdge, hLine, endEdge, durationText);
    }
}
