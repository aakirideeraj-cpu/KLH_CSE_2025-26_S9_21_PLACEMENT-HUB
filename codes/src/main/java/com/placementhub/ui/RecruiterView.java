package com.placementhub.ui;

import com.placementhub.db.PlacementDAO;
import com.placementhub.dsa.co3.BitmaskSkillCoverDP;
import com.placementhub.dsa.core.StudentBST;
import com.placementhub.dsa.core.StudentPriorityQueue;
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
 * Screen 2: Recruiter Candidate Search, BST Range Queries, Priority Queue Ranking,
 * and CO3 Bitmask DP Skill Coverage Optimizer.
 * Fulfills PPT Recruiter Search functionality and Expected Outcomes.
 */
public class RecruiterView {
    private final PlacementDAO dao;
    private final StudentBST bst = new StudentBST();
    private final TableView<StudentRow> resultsTable = new TableView<>();
    private final ObservableList<StudentRow> tableData = FXCollections.observableArrayList();

    // Filters
    private final TextField minCgpaField = new TextField("7.5");
    private final TextField maxCgpaField = new TextField("10.0");
    private final TextField requiredSkillsField = new TextField("Java, SQL");
    private final Spinner<Integer> topKSpinner = new Spinner<>(1, 50, 10);

    // Bitmask DP Controls
    private final TextField bitmaskSkillsField = new TextField("Java, SQL, AWS, Algorithms, System Design");
    private final TextArea bitmaskOutput = new TextArea();

    public static class StudentRow {
        private final SimpleStringProperty roll;
        private final SimpleStringProperty name;
        private final SimpleStringProperty cgpa;
        private final SimpleStringProperty aptitude;
        private final SimpleStringProperty skills;
        private final SimpleStringProperty matchScore;

        public StudentRow(Student s, double score) {
            this.roll = new SimpleStringProperty(s.getRollNo());
            this.name = new SimpleStringProperty(s.getName());
            this.cgpa = new SimpleStringProperty(String.format("%.2f", s.getCgpa()));
            this.aptitude = new SimpleStringProperty(String.valueOf(s.getAptitudeScore()));
            this.skills = new SimpleStringProperty(s.getSkillsCsv());
            this.matchScore = new SimpleStringProperty(String.format("%.1f", score));
        }

        public String getRoll() { return roll.get(); }
        public String getName() { return name.get(); }
        public String getCgpa() { return cgpa.get(); }
        public String getAptitude() { return aptitude.get(); }
        public String getSkills() { return skills.get(); }
        public String getMatchScore() { return matchScore.get(); }
    }

    public RecruiterView(PlacementDAO dao) {
        this.dao = dao;
        loadBST();
    }

    public void loadBST() {
        bst.clear();
        List<Student> students = dao.getAllStudents();
        for (Student s : students) {
            bst.insert(s);
        }
    }

    public VBox getView() {
        VBox root = new VBox(14);
        root.setPadding(new Insets(16));
        root.setStyle("-fx-background-color: " + UITheme.BG_COLOR + ";");

        Label header = new Label("Recruiter Candidate Search & Algorithmic Ranking Studio");
        header.setStyle(UITheme.getHeaderStyle());

        // Recruiter Query Filter Panel (BST Range Search + PriorityQueue)
        VBox filterCard = new VBox(10);
        filterCard.setStyle(UITheme.getCardStyle());

        Label filterTitle = new Label("Recruiter Criteria (BST Range Query + Priority Queue Ranking):");
        filterTitle.setStyle(UITheme.getSubheaderStyle());

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(8);

        grid.add(new Label("Min CGPA:"), 0, 0);
        minCgpaField.setPrefWidth(80);
        grid.add(minCgpaField, 1, 0);

        grid.add(new Label("Max CGPA:"), 2, 0);
        maxCgpaField.setPrefWidth(80);
        grid.add(maxCgpaField, 3, 0);

        grid.add(new Label("Required Skills:"), 0, 1);
        requiredSkillsField.setPrefWidth(240);
        grid.add(requiredSkillsField, 1, 1, 3, 1);

        grid.add(new Label("Top-K Rank:"), 4, 0);
        topKSpinner.setPrefWidth(80);
        grid.add(topKSpinner, 5, 0);

        HBox btnRow = new HBox(12);
        btnRow.setAlignment(Pos.CENTER_LEFT);

        Button searchBstBtn = new Button("Search via BST Range");
        searchBstBtn.setStyle(UITheme.getButtonStyle(UITheme.PRIMARY_BLUE));
        searchBstBtn.setOnAction(e -> executeBSTRangeSearch());

        Button rankHeapBtn = new Button("Rank via Priority Queue (Max-Heap)");
        rankHeapBtn.setStyle(UITheme.getButtonStyle(UITheme.SUCCESS_GREEN));
        rankHeapBtn.setOnAction(e -> executePriorityRanking());

        btnRow.getChildren().addAll(searchBstBtn, rankHeapBtn);
        grid.add(btnRow, 4, 1, 2, 1);

        // Style all grid labels with dark readable text
        grid.getChildren().stream()
                .filter(n -> n instanceof Label)
                .forEach(l -> l.setStyle("-fx-text-fill: #0f172a; -fx-font-weight: bold;"));

        filterCard.getChildren().addAll(filterTitle, grid);

        // Results Table
        setupResultsTable();
        VBox tableCard = new VBox(8);
        tableCard.setStyle(UITheme.getCardStyle());
        Label tblTitle = new Label("Matching Candidate Pool:");
        tblTitle.setStyle(UITheme.getHeaderStyle());
        tableCard.getChildren().addAll(tblTitle, resultsTable);
        VBox.setVgrow(tableCard, Priority.ALWAYS);

        // CO3: Bitmask DP Panel
        VBox bitmaskCard = createBitmaskDPBox();

        root.getChildren().addAll(header, filterCard, tableCard, bitmaskCard);
        return root;
    }

    private void setupResultsTable() {
        TableColumn<StudentRow, String> colRoll = new TableColumn<>("Roll No");
        colRoll.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getRoll()));
        colRoll.setMinWidth(90);
        colRoll.setPrefWidth(100);

        TableColumn<StudentRow, String> colName = new TableColumn<>("Candidate Name");
        colName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
        colName.setMinWidth(140);
        colName.setPrefWidth(160);

        TableColumn<StudentRow, String> colCgpa = new TableColumn<>("CGPA");
        colCgpa.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCgpa()));
        colCgpa.setMinWidth(70);
        colCgpa.setPrefWidth(80);

        TableColumn<StudentRow, String> colApt = new TableColumn<>("Aptitude");
        colApt.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getAptitude()));
        colApt.setMinWidth(75);
        colApt.setPrefWidth(80);

        TableColumn<StudentRow, String> colScore = new TableColumn<>("Rank Score");
        colScore.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getMatchScore()));
        colScore.setMinWidth(85);
        colScore.setPrefWidth(90);

        TableColumn<StudentRow, String> colSkills = new TableColumn<>("Candidate Skills (HashSet)");
        colSkills.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSkills()));
        colSkills.setMinWidth(250);
        colSkills.setPrefWidth(350);
        colSkills.setCellFactory(tc -> createWrappingCell());

        resultsTable.getColumns().setAll(List.of(colRoll, colName, colCgpa, colApt, colScore, colSkills));
        resultsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        resultsTable.setItems(tableData);
        resultsTable.setPrefHeight(200);
    }

    private TableCell<StudentRow, String> createWrappingCell() {
        TableCell<StudentRow, String> cell = new TableCell<>() {
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

    private void executeBSTRangeSearch() {
        loadBST();
        tableData.clear();
        try {
            double minC = Double.parseDouble(minCgpaField.getText().trim());
            double maxC = Double.parseDouble(maxCgpaField.getText().trim());

            Set<String> reqSkills = parseSkills(requiredSkillsField.getText());

            // BST Range Query O(log N + K)
            List<Student> rangeMatches = bst.searchRange(minC, maxC);

            for (Student s : rangeMatches) {
                if (s.hasAllSkills(reqSkills)) {
                    double score = s.calculateRankingScore(reqSkills);
                    tableData.add(new StudentRow(s, score));
                }
            }
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid CGPA range values.");
            alert.show();
        }
    }

    private void executePriorityRanking() {
        tableData.clear();
        Set<String> reqSkills = parseSkills(requiredSkillsField.getText());
        int topK = topKSpinner.getValue();

        StudentPriorityQueue pq = new StudentPriorityQueue(reqSkills);
        List<Student> all = dao.getAllStudents();

        for (Student s : all) {
            pq.insert(s);
        }

        List<StudentPriorityQueue.ScoredStudent> topList = pq.extractTopK(topK);
        for (StudentPriorityQueue.ScoredStudent ss : topList) {
            tableData.add(new StudentRow(ss.student, ss.score));
        }
    }

    private VBox createBitmaskDPBox() {
        VBox box = new VBox(8);
        box.setStyle(UITheme.getCardStyle());

        Label title = new Label("Candidate Skill Coverage Optimizer (Bitmask DP - CO3)");
        title.setStyle(UITheme.getHeaderStyle());

        Label subtitle = new Label("Computes the minimum team size covering required skills using dynamic programming over bitmasks:");
        subtitle.setStyle(UITheme.getSubheaderStyle());

        HBox inputRow = new HBox(10);
        inputRow.setAlignment(Pos.CENTER_LEFT);

        Label sLbl = new Label("Core Skills to Cover:");
        sLbl.setStyle("-fx-text-fill: #0f172a; -fx-font-weight: bold;");
        bitmaskSkillsField.setPrefWidth(350);

        Button runDpBtn = new Button("Find Optimal Team (Bitmask DP)");
        runDpBtn.setStyle(UITheme.getButtonStyle(UITheme.PRIMARY_BLUE));
        runDpBtn.setOnAction(e -> runBitmaskSolver());

        inputRow.getChildren().addAll(sLbl, bitmaskSkillsField, runDpBtn);

        bitmaskOutput.setEditable(false);
        bitmaskOutput.setWrapText(true);
        bitmaskOutput.setPrefRowCount(5);
        bitmaskOutput.setStyle("-fx-control-inner-background: #ffffff; -fx-text-fill: #0f172a; -fx-font-family: monospace; -fx-border-color: #cbd5e1;");

        box.getChildren().addAll(title, subtitle, inputRow, bitmaskOutput);
        return box;
    }

    private void runBitmaskSolver() {
        String[] parts = bitmaskSkillsField.getText().split(",");
        List<String> requiredSkills = new ArrayList<>();
        for (String p : parts) {
            if (!p.trim().isEmpty()) requiredSkills.add(p.trim());
        }

        List<Student> students = dao.getAllStudents();
        List<BitmaskSkillCoverDP.CandidateProfile> profiles = new ArrayList<>();

        for (Student s : students) {
            // Assign cost inversely related to CGPA (or interview budget)
            int cost = Math.max(10, 100 - (int)(s.getCgpa() * 8));
            profiles.add(new BitmaskSkillCoverDP.CandidateProfile(s.getId(), s.getName(), new ArrayList<>(s.getSkills()), cost));
        }

        BitmaskSkillCoverDP.Solution sol = BitmaskSkillCoverDP.solve(requiredSkills, profiles);

        StringBuilder sb = new StringBuilder();
        sb.append("=== Bitmask Dynamic Programming Optimal Skill Cover ===\n");
        sb.append(String.format("Required Competencies (K = %d): %s\n", requiredSkills.size(), requiredSkills));
        sb.append(String.format("Candidate Pool (M = %d)\n", profiles.size()));
        sb.append(String.format("States Evaluated: %d | Time: %d µs\n", sol.statesEvaluated, sol.executionTimeNanos / 1000));
        sb.append(String.format("Theoretical Complexity: %s\n\n", sol.complexityAnalysis));

        if (sol.totalCost == -1) {
            sb.append("No combination of candidates can cover all requested skills.\n");
        } else {
            sb.append(String.format("Optimal Team Cost / Weight: %d\n", sol.totalCost));
            sb.append("Selected Minimal Covering Team:\n");
            for (BitmaskSkillCoverDP.CandidateProfile cp : sol.selectedCandidates) {
                sb.append(String.format(" • %s (Cost: %d) -> Skills Covered: %s\n", cp.name, cp.cost, cp.skills));
            }
        }

        bitmaskOutput.setText(sb.toString());
    }

    private Set<String> parseSkills(String txt) {
        Set<String> set = new HashSet<>();
        if (txt == null || txt.trim().isEmpty()) return set;
        for (String p : txt.split(",")) {
            if (!p.trim().isEmpty()) set.add(p.trim());
        }
        return set;
    }
}
