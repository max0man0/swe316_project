package org.openjfx.project;

import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Project {

	private final String id;
	private final int lastStageNumber;
	private final ArrayList<Stage> stages;

	public Project(String id, int lastStageNumber) {
		this.id = id;
		this.lastStageNumber = lastStageNumber;
		this.stages = new ArrayList<>();
	}

	public abstract void addStage(LocalDate stageDate, int stageNumber);

	public String getId() {
		return id;
	}

	public int getLastStageNumber() {
		return lastStageNumber;
	}

	public ArrayList<Stage> getStages() {
		return stages;
	}


}