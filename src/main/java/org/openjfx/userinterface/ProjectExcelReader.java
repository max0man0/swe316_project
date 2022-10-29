package org.openjfx.userinterface;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;

import org.apache.poi.ss.usermodel.*;
import org.openjfx.project.Project;
import org.openjfx.project.SimpleProject;

public class ProjectExcelReader implements Reader {

    private int currentProjectRow = 1;
    private int currentStageRow = 1;
    private Sheet xSheet_Project;
    private Sheet xSheet_Stages;
    private Sheet xSheet_Stages_Detailed;
    private String PROJECTS_EXCEL_FILE_NAME = "Projects.xls";
    private String STAGES_EXCEL_FILE_NAME = "Stages.xls";
    private String STAGES_DETAILED_EXCEL_FILE_NAME = "Stages_Detailed.xls";

    private static ProjectExcelReader sharedInstance;

    private ProjectExcelReader() throws IOException {
        String baseDirectory = System.getProperty("user.dir");
        String excelFile_Projects = baseDirectory + "\\" + PROJECTS_EXCEL_FILE_NAME;
        String excelFilePath_Stages = baseDirectory + "\\" + STAGES_EXCEL_FILE_NAME;
        String excelFilePath_Stages_Detailed = baseDirectory + "\\" + STAGES_DETAILED_EXCEL_FILE_NAME;

        try {
            FileInputStream inputStream = new FileInputStream(new File(excelFile_Projects));
            Workbook wb_Project = WorkbookFactory.create(inputStream);
            xSheet_Project = wb_Project.getSheetAt(0);


            FileInputStream inputStream1 = new FileInputStream(new File(excelFilePath_Stages));
            Workbook wb_Stages = WorkbookFactory.create(inputStream1);
            xSheet_Stages = wb_Stages.getSheetAt(0);

            FileInputStream inputStream2 = new FileInputStream(new File(excelFilePath_Stages_Detailed));
            Workbook wb_Stages_Detailed = WorkbookFactory.create(inputStream2);
            xSheet_Stages_Detailed = wb_Stages_Detailed.getSheetAt(0);
        } catch (IOException e) {
            throw new IOException("Excel reader error " + e.getMessage());
        }
    }

    public static ProjectExcelReader getInstance() throws IOException {
        if (sharedInstance == null) {
            synchronized (ProjectExcelReader.class) {
                if (sharedInstance == null) {
                    sharedInstance = new ProjectExcelReader();
                }
            }
        }

        return sharedInstance;

    }

    @Override
    public Project readNextProject() {
        String Id;
        int lastStageNumber;

        DataFormatter formatter = new DataFormatter();

        Cell idCell, stageCell;
        Row row = xSheet_Project.getRow(currentProjectRow);

        int stageIndex = 0, idIndex = 0;
        Row row0 = xSheet_Project.getRow(0);

        for (Cell cell : row0) {
            String f = formatter.formatCellValue(cell);
            if (f.equals("Stage")) {
                stageIndex = cell.getColumnIndex();
            }
            if (f.equals("Customer Project ID")) {
                idIndex = cell.getColumnIndex();
            }
        }
        idCell = row.getCell(idIndex);
        Id = formatter.formatCellValue(idCell);

        stageCell = row.getCell(stageIndex);
        String stage = formatter.formatCellValue(stageCell);
        lastStageNumber = Integer.parseInt(stage);
        currentProjectRow += 1;

        Project project = new SimpleProject(Id, lastStageNumber);
        // Add stages

        Cell object_valCell, dateCell, newStageCell;

        int objectValueIndex = 0, dateIndex = 0, newStageIndex = 0;

        Row row0_stages = xSheet_Stages.getRow(0);
        for (Cell cell : row0_stages) {
            String f = formatter.formatCellValue(cell);
            if (f.equals("Object Value")) {
                objectValueIndex = cell.getColumnIndex();
            }
            if (f.equals("New value")) {
                newStageIndex = cell.getColumnIndex();
            }
        }

        Row row0_detailed = xSheet_Stages_Detailed.getRow(0);
        for (Cell cell : row0_detailed) {
            String f = formatter.formatCellValue(cell);
            if (f.equals("Date")) {
                dateIndex = cell.getColumnIndex();
            }
        }

        Row row_Stages = xSheet_Stages.getRow(currentStageRow);
        String object_value;
        object_valCell = row_Stages.getCell(objectValueIndex);
        object_value = formatter.formatCellValue(object_valCell);

        int tmp = currentStageRow;
        for (int i = tmp; i <= xSheet_Stages.getLastRowNum(); i++) {
            Row rowStages = xSheet_Stages.getRow(i);
            Row rowStagesDetailed = xSheet_Stages_Detailed.getRow(i);
            if (object_value.equals(formatter.formatCellValue(rowStages.getCell(0)))) {
                newStageCell = rowStages.getCell(newStageIndex);
                String newStage_tmp = formatter.formatCellValue(newStageCell);
                int stageNumber = Integer.parseInt(newStage_tmp);

                dateCell = rowStagesDetailed.getCell(dateIndex);
                String date_tmp = formatter.formatCellValue(dateCell);

                String[] list = date_tmp.split("/");
                int mount = Integer.parseInt(list[0]);
                int day = Integer.parseInt(list[1]);
                int year = Integer.parseInt(list[2]) + 2000;
                LocalDate localDate = LocalDate.of(year, mount, day);

                project.addStage(localDate, stageNumber);
                currentStageRow++;
            }

        }
        return project;
    }

    @Override
    public void reset() {
        currentStageRow = 1;
        currentProjectRow = 1;
    }

    @Override
    public boolean hasNext() {
        return currentProjectRow <= xSheet_Project.getLastRowNum();
    }
}