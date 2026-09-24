package com.placementhub.ui;

import com.placementhub.db.PlacementDAO;
import com.placementhub.model.CompanyDrive;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.*;

/**
 * Screen 3: Company Registration & Drive Management Portal.
 * Fulfills PPT Proposed Functionality 1 (Company Registration).
 * Sets eligibility criteria, hiring quotas (for CO4 network flow), and package details.
 */
public class DriveRegistrationView {
    private final PlacementDAO dao;
    private final TableView<CompanyDrive> drivesTable = new TableView<>();
    private final ObservableList<CompanyDrive> drivesData = FXCollections.observableArrayList();

    // Form inputs
    private final TextField codeField = new TextField();
    private final TextField nameField = new TextField();
    private final TextField roleField = new TextField();
    private final TextField minCgpaField = new TextField("7.5");
    private final TextField skillsField = new TextField("Java, Algorithms");
    private final TextField maxIntakeField = new TextField("4");
    private final TextField ctcField = new TextField("18.0");
    private final TextField interviewDateField = new TextField("2026-11-05");
    private final TextArea descField = new TextArea();
    private final Label statusMessage = new Label();

    public DriveRegistrationView(PlacementDAO dao) {
        this.dao = dao;
        reloadDrives();
    }

    public void reloadDrives() {
        drivesData.clear();
        drivesData.addAll(dao.getAllDrives());
    }

    public VBox getView() {
        VBox root = new VBox(14);
        root.setPadding(new Insets(16));
        root.setStyle("-fx-background-color: " + UITheme.BG_COLOR + ";");

        Label header = new Label("Company & Drive Registration Portal");
        header.setStyle(UITheme.getHeaderStyle());

        // Registration Form Card
        VBox formCard = new VBox(10);
        formCard.setStyle(UITheme.getCardStyle());

        Label formTitle = new Label("Register New Recruitment Drive & Eligibility Constraints:");
        formTitle.setStyle(UITheme.getSubheaderStyle());

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(8);

        // Column 0, 1: Basic Info
        grid.add(new Label("Drive Code:"), 0, 0);
        codeField.setPromptText("e.g. DRV-META-07");
        grid.add(codeField, 1, 0);

        grid.add(new Label("Company Name:"), 0, 1);
        nameField.setPromptText("e.g. Meta / Oracle");
        grid.add(nameField, 1, 1);

        grid.add(new Label("Role Title:"), 0, 2);
        roleField.setPromptText("e.g. SDE 1 - Distributed Systems");
        grid.add(roleField, 1, 2);

        // Column 2, 3: Constraints
        grid.add(new Label("Min CGPA Cutoff:"), 2, 0);
        grid.add(minCgpaField, 3, 0);

        grid.add(new Label("Max Intake Quota:"), 2, 1);
        maxIntakeField.setPromptText("Capacity for Network Flow (CO4)");
        grid.add(maxIntakeField, 3, 1);

        grid.add(new Label("CTC (LPA):"), 2, 2);
        grid.add(ctcField, 3, 2);

        // Row 3: Required skills
        grid.add(new Label("Mandatory Skills:"), 0, 3);
        skillsField.setPromptText("Comma-separated (e.g. Java, Python, SQL)");
        grid.add(skillsField, 1, 3, 3, 1);

        // Row 4: Date
        grid.add(new Label("Interview Date:"), 0, 4);
        grid.add(interviewDateField, 1, 4);

        // Row 5: Description
        grid.add(new Label("Job Description:"), 0, 5);
        descField.setPromptText("Key responsibilities, domain details, etc.");
        descField.setPrefRowCount(2);
        grid.add(descField, 1, 5, 3, 1);

        // Style labels
        grid.getChildren().stream()
                .filter(n -> n instanceof Label)
                .forEach(l -> l.setStyle("-fx-text-fill: #0f172a; -fx-font-weight: bold;"));

        // Buttons
        HBox btnBox = new HBox(12);
        btnBox.setAlignment(Pos.CENTER_LEFT);

        Button submitBtn = new Button("Register Recruitment Drive");
        submitBtn.setStyle(UITheme.getButtonStyle(UITheme.PRIMARY_BLUE));
        submitBtn.setOnAction(e -> handleRegistration());

        Button clearBtn = new Button("Clear Form");
        clearBtn.setStyle(UITheme.getButtonStyle(UITheme.SLATE_GRAY));
        clearBtn.setOnAction(e -> clearFields());

        statusMessage.setStyle("-fx-text-fill: #16a34a; -fx-font-weight: bold;");

        btnBox.getChildren().addAll(submitBtn, clearBtn, statusMessage);
        formCard.getChildren().addAll(formTitle, grid, btnBox);

        // Drives Table
        VBox tableCard = new VBox(8);
        tableCard.setStyle(UITheme.getCardStyle());
        Label tblTitle = new Label("Active Registered Recruitment Drives:");
        tblTitle.setStyle(UITheme.getHeaderStyle());

        setupDrivesTable();
        tableCard.getChildren().addAll(tblTitle, drivesTable);
        VBox.setVgrow(tableCard, Priority.ALWAYS);

        root.getChildren().addAll(header, formCard, tableCard);
        return root;
    }

    private void setupDrivesTable() {
        TableColumn<CompanyDrive, String> colCode = new TableColumn<>("Drive Code");
        colCode.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDriveCode()));
        colCode.setMinWidth(90);
        colCode.setPrefWidth(100);

        TableColumn<CompanyDrive, String> colComp = new TableColumn<>("Company");
        colComp.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCompanyName()));
        colComp.setMinWidth(110);
        colComp.setPrefWidth(130);

        TableColumn<CompanyDrive, String> colRole = new TableColumn<>("Role Title");
        colRole.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getRoleTitle()));
        colRole.setMinWidth(160);
        colRole.setPrefWidth(200);
        colRole.setCellFactory(tc -> createWrappingCell());

        TableColumn<CompanyDrive, String> colCgpa = new TableColumn<>("Cutoff");
        colCgpa.setCellValueFactory(c -> new SimpleStringProperty(String.format("%.2f", c.getValue().getMinCgpa())));
        colCgpa.setMinWidth(70);
        colCgpa.setPrefWidth(80);

        TableColumn<CompanyDrive, String> colQuota = new TableColumn<>("Quota");
        colQuota.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getMaxIntake())));
        colQuota.setMinWidth(70);
        colQuota.setPrefWidth(80);

        TableColumn<CompanyDrive, String> colCtc = new TableColumn<>("Package");
        colCtc.setCellValueFactory(c -> new SimpleStringProperty(String.format("%.1f LPA", c.getValue().getCtcLpa())));
        colCtc.setMinWidth(85);
        colCtc.setPrefWidth(95);

        TableColumn<CompanyDrive, String> colSkills = new TableColumn<>("Required Skills (HashSet)");
        colSkills.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getRequiredSkillsCsv()));
        colSkills.setMinWidth(240);
        colSkills.setPrefWidth(350);
        colSkills.setCellFactory(tc -> createWrappingCell());

        drivesTable.getColumns().setAll(List.of(colCode, colComp, colRole, colCgpa, colQuota, colCtc, colSkills));
        drivesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        drivesTable.setItems(drivesData);
    }

    private TableCell<CompanyDrive, String> createWrappingCell() {
        TableCell<CompanyDrive, String> cell = new TableCell<>() {
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

    private void handleRegistration() {
        String code = codeField.getText().trim();
        String name = nameField.getText().trim();
        String role = roleField.getText().trim();

        if (code.isEmpty() || name.isEmpty() || role.isEmpty()) {
            statusMessage.setText("Error: Drive code, company name, and role are required.");
            statusMessage.setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold;");
            return;
        }

        try {
            double minCgpa = Double.parseDouble(minCgpaField.getText().trim());
            int maxIntake = Integer.parseInt(maxIntakeField.getText().trim());
            double ctc = Double.parseDouble(ctcField.getText().trim());
            String date = interviewDateField.getText().trim();
            String desc = descField.getText().trim();

            Set<String> skills = new HashSet<>();
            for (String s : skillsField.getText().split(",")) {
                if (!s.trim().isEmpty()) skills.add(s.trim());
            }

            CompanyDrive drive = new CompanyDrive(0, code, name, role, minCgpa, skills, maxIntake, ctc, desc, date);
            boolean ok = dao.addDrive(drive);

            if (ok) {
                statusMessage.setText("Success: Registered drive " + code + " for " + name + "!");
                statusMessage.setStyle("-fx-text-fill: #10b981; -fx-font-weight: bold;");
                reloadDrives();
                clearFields();
            } else {
                statusMessage.setText("Registration failed. Duplicate drive code?");
                statusMessage.setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold;");
            }
        } catch (NumberFormatException ex) {
            statusMessage.setText("Error: Min CGPA, Quota, or CTC have invalid numeric formats.");
            statusMessage.setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold;");
        }
    }

    private void clearFields() {
        codeField.clear();
        nameField.clear();
        roleField.clear();
        descField.clear();
    }
}
