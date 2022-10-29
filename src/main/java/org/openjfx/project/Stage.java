package org.openjfx.project;

import java.time.LocalDate;

public abstract class Stage {

	private final LocalDate date;
	private final int number;

	public Stage(LocalDate date, int number) {
		this.date = date;
		this.number = number;
	}

	public LocalDate getDate() {
		return date;
	}

	public int getNumber() {
		return number;
	}

}