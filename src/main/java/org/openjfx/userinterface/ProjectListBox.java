package org.openjfx.userinterface;




import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.openjfx.project.Project;

import java.io.IOException;

public class ProjectListBox extends TableView<Project>{

    private final Reader reader;

    public ProjectListBox(double width, double height) throws IOException {
        if (width < 250) {
            throw new IllegalArgumentException("Minimum accepted width for the project list box is 250");
        }
        setPrefHeight(height);
        setPrefWidth(width);
        reader = ProjectExcelReader.getInstance();
        super.setMaxHeight(height);
        super.setMaxWidth(width);
        TableColumn<Project, String> indexColumn = new TableColumn<>("");
        TableColumn<Project, String> projectIdColumn = new TableColumn<>("Project ID");
        TableColumn<Project, Integer> StageColumn = new TableColumn<>("Stage");
        getColumns().add(indexColumn);
        getColumns().add(projectIdColumn);
        getColumns().add(StageColumn);
        indexColumn.setPrefWidth(50);
        projectIdColumn.setPrefWidth((width*2/3)-25);
        StageColumn.setPrefWidth((width*1/3)-25);

        indexColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>((getItems().indexOf(p.getValue()) + 1) + ""));
        indexColumn.setSortable(false);

        projectIdColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().getId()));
        projectIdColumn.setSortable(false);

        StageColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().getLastStageNumber()));
        StageColumn.setSortable(false);

        refill();
    }

    public void refill() {

        getItems().clear();
        reader.reset();

        while (reader.hasNext()) {
            getItems().add(reader.readNextProject());
        }


    }

}