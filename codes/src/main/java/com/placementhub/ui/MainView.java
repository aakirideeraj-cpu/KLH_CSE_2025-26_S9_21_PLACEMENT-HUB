package com.placementhub.ui;

import com.placementhub.db.DatabaseManager;
import com.placementhub.db.PlacementDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * Main Shell Window hosting the 3 core screens from PPT plus the DSA Studio for CO1-CO6.
 * Clean, solid, human design.
 */
public class MainView {
    private final PlacementDAO dao;
    private final StudentView studentView;
    private final RecruiterView recruiterView;
    private final DriveRegistrationView registrationView;
    private final DSAStudioView dsaStudioView;

    private final Label dbStatusBadge = new Label();
    private final Label statsBadge = new Label();

    public MainView(PlacementDAO dao) {
        this.dao = dao;
        this.studentView = new StudentView(dao);
        this.recruiterView = new RecruiterView(dao);
        this.registrationView = new DriveRegistrationView(dao);
        this.dsaStudioView = new DSAStudioView(dao);
    }

    public BorderPane createRootPane() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + UITheme.BG_COLOR + ";");

        // Top Header
        root.setTop(createTopHeader());

        // Center TabPane
        TabPane mainTabs = new TabPane();
        mainTabs.setStyle("-fx-background-color: transparent; -fx-tab-min-height: 38px; -fx-tab-min-width: 140px;");

        // Screen 1: Student Search (from PPT)
        Tab tabStudent = new Tab("1. Student Search & Eligibility", studentView.getView());
        tabStudent.setClosable(false);

        // Screen 2: Recruiter Search (from PPT)
        Tab tabRecruiter = new Tab("2. Recruiter Search & Ranking", recruiterView.getView());
        tabRecruiter.setClosable(false);

        // Screen 3: Company Registration (from PPT)
        Tab tabRegistration = new Tab("3. Company Registration", registrationView.getView());
        tabRegistration.setClosable(false);

        // Screen 4: Syllabus DSA Optimization (1 per CO)
        Tab tabDSA = new Tab("4. DSA Optimization (CO1 - CO6)", dsaStudioView.getView());
        tabDSA.setClosable(false);

        mainTabs.getTabs().addAll(tabStudent, tabRecruiter, tabRegistration, tabDSA);

        // Synchronize when switching tabs
        mainTabs.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == tabStudent) studentView.reloadStudents();
            if (newVal == tabRecruiter) recruiterView.loadBST();
            if (newVal == tabRegistration) registrationView.reloadDrives();
            updateStats();
        });

        root.setCenter(mainTabs);
        updateStats();

        return root;
    }

    private HBox createTopHeader() {
        HBox bar = new HBox(16);
        bar.setPadding(new Insets(12, 20, 12, 20));
        bar.setAlignment(Pos.CENTER_LEFT);
        bar.setStyle("-fx-background-color: #0f172a;");

        VBox brandBox = new VBox(2);
        Label brandTitle = new Label("PLACEMENT HUB");
        brandTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #ffffff;");

        Label brandSubtitle = new Label("Campus Recruitment Eligibility & DSA Optimization System");
        brandSubtitle.setStyle("-fx-font-size: 11px; -fx-text-fill: #94a3b8;");
        brandBox.getChildren().addAll(brandTitle, brandSubtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Database status badge
        String dbType = DatabaseManager.getInstance().getDatabaseType();
        dbStatusBadge.setText("DB: " + dbType);
        dbStatusBadge.setStyle("-fx-background-color: #1e293b; -fx-text-fill: #38bdf8; -fx-padding: 5 12; " +
                "-fx-background-radius: 4; -fx-font-size: 11px; -fx-font-weight: bold;");

        // Stats badge
        statsBadge.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 12px; -fx-font-weight: bold;");

        bar.getChildren().addAll(brandBox, spacer, dbStatusBadge, statsBadge);
        return bar;
    }

    private void updateStats() {
        int studentCount = dao.getAllStudents().size();
        int driveCount = dao.getAllDrives().size();
        statsBadge.setText(String.format("Registered Students: %d  |  Active Drives: %d", studentCount, driveCount));
    }
}
