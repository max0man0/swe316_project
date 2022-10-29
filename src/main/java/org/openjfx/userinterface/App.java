package org.openjfx.userinterface;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.openjfx.project.Project;
import org.openjfx.timelinechart.TimeLineChartFactory;

import java.io.IOException;

public class App extends Application {

    private Project currentProject;

    @Override
    public void start(Stage stage) throws IOException {

        final var listBox = new ProjectListBox(300,800);

        final var mainPane = new BorderPane();
        mainPane.setPadding(new Insets(50,20,50,20));
        mainPane.setBackground(new Background(new BackgroundFill(Color.valueOf("0xf4f4e9"), null, Insets.EMPTY)));

        mainPane.setLeft(listBox);

        listBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            currentProject = newSelection;

            final var projectIdText = new Label(currentProject.getId());
            projectIdText.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
            projectIdText.setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
                    BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE,
                    CornerRadii.EMPTY, null, Insets.EMPTY)));
            projectIdText.setPadding(new Insets(0,20,0,0));
            projectIdText.setFont(Font.font(null, FontWeight.BOLD, 30));

            final var projectIdTextLbl = new Label("Project ID");
            projectIdTextLbl.setFont(Font.font(null, FontWeight.BOLD, 20));
            projectIdTextLbl.setLabelFor(projectIdText);

            final var hBox = new HBox(projectIdTextLbl, projectIdText);
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setSpacing(50);
            final var timeLineChart = TimeLineChartFactory.createTimeLineChart(currentProject);

            final var vBox = new VBox(hBox, timeLineChart);
            vBox.setAlignment(Pos.CENTER);
            vBox.setPadding(new Insets(50));
            vBox.setSpacing(100);
            mainPane.setCenter(vBox);
            stage.show();
        });

        var scene = new Scene(mainPane, 1800, 800);
        stage.setTitle("Actual Product");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
