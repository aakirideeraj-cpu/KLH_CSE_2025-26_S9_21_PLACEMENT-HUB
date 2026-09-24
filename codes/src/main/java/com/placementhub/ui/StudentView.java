package com.placementhub.ui;

import com.placementhub.db.PlacementDAO;
import com.placementhub.dsa.co2.RabinKarpMatcher;
import com.placementhub.model.Application;
import com.placementhub.model.CompanyDrive;
import com.placementhub.model.Student;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.*;

/**
 * Screen 1: Student Search & Eligibility Checking.
 * Exactly matches the PPT: Students enter/select their CGPA and skills to see which companies they qualify for.
 */
public class StudentView {
    private final PlacementDAO dao;
    private final Map<String, Student> studentMap = new HashMap<>();
    private final ComboBox<String> studentSelector = new ComboBox<>();
    private final Label profileLabel = new Label();
    private final TableView<DriveEligibilityRow> eligibilityTable = new TableView<>();
    private final ObservableList<DriveEligibilityRow> eligibilityData = FXCollections.observableArrayList();
    private final TextField keywordSearchField = new TextField();
    private final Label statusMessage = new Label();

    private Student currentStudent;

    public static class DriveEligibilityRow {
        private final SimpleStringProperty code;
        private final SimpleStringProperty company;
        private final SimpleStringProperty role;
        private final SimpleStringProperty minCgpa;
        private final SimpleStringProperty ctc;
        private final SimpleStringProperty eligibleStatus;
        private final SimpleStringProperty reason;
        public final CompanyDrive drive;

        public DriveEligibilityRow(CompanyDrive d, boolean eligible, String reasonStr) {
            this.drive = d;
            this.code = new SimpleStringProperty(d.getDriveCode());
            this.company = new SimpleStringProperty(d.getCompanyName());
            this.role = new SimpleStringProperty(d.getRoleTitle());
            this.minCgpa = new SimpleStringProperty(String.format("%.2f", d.getMinCgpa()));
            this.ctc = new SimpleStringProperty(String.format("%.1f LPA", d.getCtcLpa()));
            this.eligibleStatus = new SimpleStringProperty(eligible ? "QUALIFIED" : "NOT ELIGIBLE");
            this.reason = new SimpleStringProperty(reasonStr);
        }

        public String getCode() { return code.get(); }
        public String getCompany() { return company.get(); }
        public String getRole() { return role.get(); }
        public String getMinCgpa() { return minCgpa.get(); }
        public String getCtc() { return ctc.get(); }
        public String getEligibleStatus() { return eligibleStatus.get(); }
        public String getReason() { return reason.get(); }
    }

    public StudentView(PlacementDAO dao) {
        this.dao = dao;
        reloadStudents();
    }

    public void reloadStudents() {
        studentMap.clear();
        studentSelector.getItems().clear();
        List<Student> students = dao.getAllStudents();
        for (Student s : students) {
            studentMap.put(s.getRollNo(), s);
            studentSelector.getItems().add(s.getRollNo() + " - " + s.getName());
        }
        if (!students.isEmpty() && currentStudent == null) {
            currentStudent = students.get(0);
            studentSelector.setValue(currentStudent.getRollNo() + " - " + currentStudent.getName());
            updateStudentProfile(currentStudent);
        }
    }

    public VBox getView() {
        VBox root = new VBox(12);
        root.setPadding(new Insets(16));
        root.setStyle("-fx-background-color: " + UITheme.BG_COLOR + ";");

        // Top Selection Card
        VBox topCard = new VBox(10);
        topCard.setStyle(UITheme.getCardStyle());

        Label screenTitle = new Label("Student Search & Eligibility Portal");
        screenTitle.setStyle(UITheme.getHeaderStyle());

        HBox selectRow = new HBox(12);
        selectRow.setAlignment(Pos.CENTER_LEFT);

        Label lbl = new Label("Select Student Profile:");
        lbl.setStyle("-fx-font-weight: bold; -fx-text-fill: " + UITheme.TEXT_MAIN + ";");

        studentSelector.setPrefWidth(260);
        studentSelector.setOnAction(e -> {
            String val = studentSelector.getValue();
            if (val != null && val.contains(" - ")) {
                String roll = val.split(" - ")[0].trim();
                Student s = studentMap.get(roll);
                if (s != null) {
                    currentStudent = s;
                    updateStudentProfile(s);
                }
            }
        });

        Button refreshBtn = new Button("Refresh List");
        refreshBtn.setStyle(UITheme.getButtonStyle(UITheme.SLATE_GRAY));
        refreshBtn.setOnAction(e -> reloadStudents());

        selectRow.getChildren().addAll(lbl, studentSelector, refreshBtn);

        // Student Info
        profileLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #334155;");

        topCard.getChildren().addAll(screenTitle, selectRow, profileLabel);

        // Table Card
        VBox tableCard = new VBox(10);
        tableCard.setStyle(UITheme.getCardStyle());

        HBox tableHeaderRow = new HBox(12);
        tableHeaderRow.setAlignment(Pos.CENTER_LEFT);

        Label tableTitle = new Label("Company Recruitment Drives & Direct Eligibility Results:");
        tableTitle.setStyle(UITheme.getHeaderStyle());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Simple Keyword Search (uses Rabin-Karp rolling hash under the hood)
        Label kwLbl = new Label("Search Role / Keyword:");
        kwLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: " + UITheme.TEXT_MAIN + ";");
        keywordSearchField.setPromptText("e.g. SDE, Cloud, Java");
        keywordSearchField.setPrefWidth(160);

        Button searchBtn = new Button("Search");
        searchBtn.setStyle(UITheme.getButtonStyle(UITheme.PRIMARY_BLUE));
        searchBtn.setOnAction(e -> filterDrivesByKeyword());

        Button resetBtn = new Button("Show All");
        resetBtn.setStyle(UITheme.getButtonStyle(UITheme.SLATE_GRAY));
        resetBtn.setOnAction(e -> {
            keywordSearchField.clear();
            if (currentStudent != null) updateStudentProfile(currentStudent);
        });

        tableHeaderRow.getChildren().addAll(tableTitle, spacer, kwLbl, keywordSearchField, searchBtn, resetBtn);

        setupTable();
        VBox.setVgrow(eligibilityTable, Priority.ALWAYS);

        // Action Bar (Apply button)
        HBox actionRow = new HBox(12);
        actionRow.setAlignment(Pos.CENTER_LEFT);

        Button applyBtn = new Button("Apply to Selected Drive");
        applyBtn.setStyle(UITheme.getButtonStyle(UITheme.SUCCESS_GREEN));
        applyBtn.setOnAction(e -> handleApply());

        statusMessage.setStyle("-fx-font-weight: bold; -fx-text-fill: " + UITheme.PRIMARY_BLUE + ";");

        actionRow.getChildren().addAll(applyBtn, statusMessage);

        tableCard.getChildren().addAll(tableHeaderRow, eligibilityTable, actionRow);
        VBox.setVgrow(tableCard, Priority.ALWAYS);

        root.getChildren().addAll(topCard, tableCard);
        return root;
    }

    private void setupTable() {
        TableColumn<DriveEligibilityRow, String> colCode = new TableColumn<>("Drive Code");
        colCode.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCode()));
        colCode.setMinWidth(90);
        colCode.setPrefWidth(100);

        TableColumn<DriveEligibilityRow, String> colComp = new TableColumn<>("Company");
        colComp.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCompany()));
        colComp.setMinWidth(110);
        colComp.setPrefWidth(130);

        TableColumn<DriveEligibilityRow, String> colRole = new TableColumn<>("Role Title");
        colRole.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getRole()));
        colRole.setMinWidth(180);
        colRole.setPrefWidth(220);
        colRole.setCellFactory(tc -> createWrappingCell());

        TableColumn<DriveEligibilityRow, String> colMin = new TableColumn<>("Cutoff");
        colMin.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getMinCgpa()));
        colMin.setMinWidth(70);
        colMin.setPrefWidth(80);

        TableColumn<DriveEligibilityRow, String> colCtc = new TableColumn<>("Package");
        colCtc.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCtc()));
        colCtc.setMinWidth(85);
        colCtc.setPrefWidth(100);

        TableColumn<DriveEligibilityRow, String> colStatus = new TableColumn<>("Eligibility");
        colStatus.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEligibleStatus()));
        colStatus.setMinWidth(110);
        colStatus.setPrefWidth(120);
        colStatus.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if ("QUALIFIED".equals(item)) {
                        setStyle("-fx-text-fill: #16a34a; -fx-font-weight: bold;");
                    } else {
                        setStyle("-fx-text-fill: #dc2626; -fx-font-weight: bold;");
                    }
                }
            }
        });

        TableColumn<DriveEligibilityRow, String> colReason = new TableColumn<>("Criteria Verification Details");
        colReason.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getReason()));
        colReason.setMinWidth(250);
        colReason.setPrefWidth(380);
        colReason.setCellFactory(tc -> createWrappingCell());

        eligibilityTable.getColumns().setAll(List.of(colCode, colComp, colRole, colMin, colCtc, colStatus, colReason));
        eligibilityTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        eligibilityTable.setItems(eligibilityData);
    }

    private TableCell<DriveEligibilityRow, String> createWrappingCell() {
        TableCell<DriveEligibilityRow, String> cell = new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                }
            }
        };
        cell.setWrapText(true);
        return cell;
    }

    private void updateStudentProfile(Student s) {
        profileLabel.setWrapText(true);
        profileLabel.setMaxWidth(Double.MAX_VALUE);
        profileLabel.setText(String.format(
                "Roll No: %s  |  Name: %s  |  CGPA: %.2f  |  Aptitude Score: %d/100\nCandidate Skills: %s",
                s.getRollNo(), s.getName(), s.getCgpa(), s.getAptitudeScore(), s.getSkillsCsv()
        ));

        eligibilityData.clear();
        List<CompanyDrive> drives = dao.getAllDrives();

        for (CompanyDrive d : drives) {
            boolean cgpaOk = s.getCgpa() >= d.getMinCgpa();
            boolean skillsOk = s.hasAllSkills(d.getRequiredSkills());
            boolean eligible = cgpaOk && skillsOk;

            String reason;
            if (eligible) {
                reason = "Eligible! Candidate meets CGPA cutoff and all required skills.";
            } else if (!cgpaOk && !skillsOk) {
                reason = String.format("Below cutoff (%.2f < %.2f) & Missing skills", s.getCgpa(), d.getMinCgpa());
            } else if (!cgpaOk) {
                reason = String.format("CGPA below cutoff (%.2f < %.2f)", s.getCgpa(), d.getMinCgpa());
            } else {
                Set<String> missing = new HashSet<>(d.getRequiredSkills());
                for (String sk : s.getSkills()) missing.remove(sk);
                reason = "Missing required skills: " + missing;
            }

            eligibilityData.add(new DriveEligibilityRow(d, eligible, reason));
        }
        statusMessage.setText("");
    }

    private void filterDrivesByKeyword() {
        String keyword = keywordSearchField.getText().trim();
        if (keyword.isEmpty()) {
            if (currentStudent != null) updateStudentProfile(currentStudent);
            return;
        }

        // Linear string search with Rabin-Karp Rolling Hash (CO2)
        ObservableList<DriveEligibilityRow> filtered = FXCollections.observableArrayList();
        for (DriveEligibilityRow row : eligibilityData) {
            String target = (row.getCompany() + " " + row.getRole() + " " + row.drive.getDescription()).toLowerCase();
            if (!RabinKarpMatcher.search(target, keyword.toLowerCase()).isEmpty()) {
                filtered.add(row);
            }
        }
        eligibilityTable.setItems(filtered);
    }

    private void handleApply() {
        DriveEligibilityRow sel = eligibilityTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            statusMessage.setText("Please select a company drive first.");
            statusMessage.setStyle("-fx-text-fill: #dc2626; -fx-font-weight: bold;");
            return;
        }
        if (!"QUALIFIED".equals(sel.getEligibleStatus())) {
            statusMessage.setText("Cannot apply: Student does not meet the eligibility cutoff.");
            statusMessage.setStyle("-fx-text-fill: #dc2626; -fx-font-weight: bold;");
            return;
        }

        Application app = new Application(0, currentStudent.getId(), sel.drive.getId(),
                currentStudent.getName(), sel.getCompany(),
                currentStudent.calculateRankingScore(sel.drive.getRequiredSkills()),
                "APPLIED", "Slot Assigned");
        boolean ok = dao.addApplication(app);
        if (ok) {
            statusMessage.setText("Application submitted successfully for " + sel.getCompany() + "!");
            statusMessage.setStyle("-fx-text-fill: #16a34a; -fx-font-weight: bold;");
        } else {
            statusMessage.setText("Application failed to save.");
            statusMessage.setStyle("-fx-text-fill: #dc2626; -fx-font-weight: bold;");
        }
    }
}
