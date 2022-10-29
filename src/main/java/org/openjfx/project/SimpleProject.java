package org.openjfx.project;

import java.time.LocalDate;

public class SimpleProject extends Project{

	public SimpleProject(String id, int lastStageNumber) {
		super(id, lastStageNumber);
	}

	@Override
	public void addStage(LocalDate stageDate, int stageNumber) {
		this.getStages().add(new SimpleStage(stageDate, stageNumber));
	}

}