package org.openjfx.timelinechart;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.paint.Color;

import javax.annotation.Nonnull;

public class TimeLineChart extends Group {
	private final ObservableList<Pole> poles;
	private TimeLine timeLine;
	private EventLine eventLine;
	private final LocalDate eventStartDate;
	private final LocalDate eventEndDate;
	private final double TIME_LINE_Y = 300;
	private final double TIME_LINE_START_X = 50;
	private final double TIME_LINE_LENGTH = 1200;
	private final double TIME_LINE_END_X = TIME_LINE_START_X + TIME_LINE_LENGTH;
	private double eventLineY = 200;
	private final double EVENT_LINE_Y_RAISE_AMOUNT = 100;

	TimeLineChart(@Nonnull LocalDate eventStartDate, @Nonnull LocalDate eventEndDate) throws IllegalArgumentException {
		super();
		this.eventStartDate = eventStartDate;
		this.eventEndDate = eventEndDate;

		poles = FXCollections.observableArrayList();
		draw();
	}

	void addNode(LocalDate date, int number, Color nodeColor) throws IllegalArgumentException {
		// add the node to an existing pole
		for (Pole pole : poles) {
			if (pole.getDate().equals(date)) {
				pole.addNode(number, nodeColor);
				draw();
				// raise the event line y if the pole is cutting through it
				if (pole.getVerticalLine().getStartY() <= eventLineY) {
					eventLineY = pole.getVerticalLine().getStartY() - EVENT_LINE_Y_RAISE_AMOUNT;
				}
				return;
			}
		}
		double newPoleX;
		// check if there are any existing poles
		if (poles.size() != 0) {
			var firstPole = poles.get(0);
			// the number of days between the first pole and the current pole
			var currentEventDayCount = ChronoUnit.DAYS.between(firstPole.getDate(), date);
			newPoleX = firstPole.getX() + currentEventDayCount * timeLine.getLengthBetweenTicks();
		}
		// if there are no poles
		else {
			// the number of days between the start of the time line and the first pole
			var dayCount = ChronoUnit.DAYS.between(timeLine.getStartDate(), date);
			newPoleX = TIME_LINE_START_X + dayCount * timeLine.getLengthBetweenTicks();
		}
		var newPole = new Pole(date, newPoleX, TIME_LINE_Y);
		newPole.addNode(number, nodeColor);
		poles.add(newPole);
		draw();
	}

	private void draw() throws IllegalArgumentException {
		// creating the time line
		LocalDate timeLineStartDate = LocalDate.of(eventStartDate.getYear(), eventStartDate.getMonthValue(), 1);

		int timeLineEndDateMonthValue;
		int timeLineEndDateYear = eventEndDate.getYear();
		if (eventEndDate.getMonthValue() == 12) {
			timeLineEndDateMonthValue = 1;
			timeLineEndDateYear++;
		}
		else {
			timeLineEndDateMonthValue = eventEndDate.getMonthValue() + 1;
		}
		LocalDate timeLineEndDate = LocalDate.of(timeLineEndDateYear, timeLineEndDateMonthValue, 1);

		this.timeLine = new TimeLine(timeLineStartDate, timeLineEndDate, TIME_LINE_START_X, TIME_LINE_END_X, TIME_LINE_Y);

		// creating the event line
		// the number of days between the start of the time line and the start of the event
		var startGapDayCount = ChronoUnit.DAYS.between(timeLine.getStartDate(), eventStartDate);
		var eventLineStartX = TIME_LINE_START_X + startGapDayCount * timeLine.getLengthBetweenTicks();

		// the number of days between the end of the event and the end of the time line
		var endGapDayCount = ChronoUnit.DAYS.between(eventEndDate, timeLine.getEndDate());
		var eventLineEndX = TIME_LINE_END_X - endGapDayCount * timeLine.getLengthBetweenTicks();

		var eventDayCount = ChronoUnit.DAYS.between(eventStartDate, eventEndDate);
		this.eventLine = new EventLine((int)eventDayCount, eventLineStartX, eventLineEndX, eventLineY);

		super.getChildren().clear();
		super.getChildren().addAll(poles);
		super.getChildren().add(timeLine);
		super.getChildren().add(eventLine);
	}
	
}