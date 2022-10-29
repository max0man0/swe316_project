package org.openjfx.timelinechart;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import javax.annotation.Nonnull;


class TimeLine extends Group {
	private final LocalDate startDate;
	private final LocalDate endDate;
	private final double y;
	private final double lengthBetweenTicks;
	private final double MAJOR_TICK_HEIGHT = 20;
	private final double MINOR_TICK_HEIGHT = 5;
	private final double TICK_DATE_SEPARATION = 10;
	private final Color TIME_LINE_COLOR = Color.BLACK;
	
	TimeLine(@Nonnull LocalDate startDate,@Nonnull LocalDate endDate, double startX, double endX, double y) throws IllegalArgumentException {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.y = y;
		if (startDate.getDayOfMonth() != 1 || endDate.getDayOfMonth() != 1) {
			throw new IllegalArgumentException("Start date and end date must be the first day of a month");
		}
		if (startDate.compareTo(endDate) >= 0) {
			throw new IllegalArgumentException("Start date must be before end date");
		}
		
		long dayCount = ChronoUnit.DAYS.between(startDate, endDate);
		double lengthBetweenTicks = (endX - startX) /dayCount;
		this.lengthBetweenTicks = lengthBetweenTicks;
		var hLine = new Line(startX, y, endX, y);
		hLine.setStroke(TIME_LINE_COLOR);
		super.getChildren().add(hLine);
		var currentDate = startDate;
		var currentX = startX;
		for (int i = 0; i <= dayCount; i++) {
			if (currentDate.getDayOfMonth() == 1) {
				// add the major tick and the date to the region
				var majorTick = createMajorTick(currentX);
				var tickBottomEndY = majorTick.getEndY();
				String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("LLL uuuu"));
				var dateText = new Text(currentX, tickBottomEndY + TICK_DATE_SEPARATION, formattedDate);
				super.getChildren().addAll(majorTick, dateText);
			}
			else {
				// add the minor tick to the region
				var minorTick = createMinorTick(currentX);
				super.getChildren().add(minorTick);
			}
			currentX += lengthBetweenTicks;
			currentDate = currentDate.plusDays(1);
		}
	}
	
	private Line createMajorTick(double x) {
		var majorTick = new Line(x, this.y - (MAJOR_TICK_HEIGHT/2), x, this.y + (MAJOR_TICK_HEIGHT/2));
		majorTick.setStroke(TIME_LINE_COLOR);
		return majorTick;
	}
	
	private Line createMinorTick(double x) {
		var minorTick = new Line(x, this.y - MINOR_TICK_HEIGHT, x, this.y);
		minorTick.setStroke(TIME_LINE_COLOR);
		return minorTick;
	}

	double getLengthBetweenTicks() {
		return lengthBetweenTicks;
	}

	LocalDate getStartDate() {
		return startDate;
	}

	LocalDate getEndDate() {
		return endDate;
	}
}
