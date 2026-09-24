package com.placementhub.ui;

import com.placementhub.db.PlacementDAO;
import com.placementhub.dsa.co1.ProblemSignatureEvaluator;
import com.placementhub.dsa.co2.RabinKarpMatcher;
import com.placementhub.dsa.co3.BitmaskSkillCoverDP;
import com.placementhub.dsa.co4.PlacementFlowNetwork;
import com.placementhub.dsa.co5.ConflictGraph;
import com.placementhub.dsa.co5.VertexCoverApproximation;
import com.placementhub.dsa.co6.LasVegasQuickSelect;
import com.placementhub.model.CompanyDrive;
import com.placementhub.model.Student;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.*;

/**
 * Screen 4: Syllabus DSA Optimization Engines (Strictly 1 concept per CO).
 * Clean, standard, plain solid-color layout.
 */
public class DSAStudioView {
    private final PlacementDAO dao;

    public DSAStudioView(PlacementDAO dao) {
        this.dao = dao;
    }

    public VBox getView() {
        VBox root = new VBox(12);
        root.setPadding(new Insets(16));
        root.setStyle("-fx-background-color: " + UITheme.BG_COLOR + ";");

        Label title = new Label("Syllabus DSA Optimization Engines (CO1 to CO6)");
        title.setStyle(UITheme.getHeaderStyle());

        TabPane coTabs = new TabPane();
        coTabs.setStyle("-fx-background-color: transparent;");

        // CO1
        Tab tabCO1 = new Tab("CO1: Problem Signature", createCO1Pane());
        tabCO1.setClosable(false);

        // CO2: Only Rabin-Karp Rolling Hash
        Tab tabCO2 = new Tab("CO2: Rabin-Karp Rolling Hash", createCO2Pane());
        tabCO2.setClosable(false);

        // CO3: Only Bitmask DP
        Tab tabCO3 = new Tab("CO3: Bitmask DP", createCO3Pane());
        tabCO3.setClosable(false);

        // CO4: Only Edmonds-Karp Network Flow
        Tab tabCO4 = new Tab("CO4: Network Flow", createCO4Pane());
        tabCO4.setClosable(false);

        // CO5: Only 2-Approximation Vertex Cover
        Tab tabCO5 = new Tab("CO5: 2-Approximation", createCO5Pane());
        tabCO5.setClosable(false);

        // CO6: Only Las Vegas QuickSelect
        Tab tabCO6 = new Tab("CO6: Las Vegas QuickSelect", createCO6Pane());
        tabCO6.setClosable(false);

        coTabs.getTabs().addAll(tabCO1, tabCO2, tabCO3, tabCO4, tabCO5, tabCO6);
        VBox.setVgrow(coTabs, Priority.ALWAYS);

        root.getChildren().addAll(title, coTabs);
        return root;
    }

    // ================= CO1: PROBLEM-CLASS SIGNATURE EVALUATOR =================
    private VBox createCO1Pane() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(14));
        box.setStyle(UITheme.getCardStyle());

        Label title = new Label("CO1: Problem-Class Signature Evaluator (BTL 5)");
        title.setStyle(UITheme.getHeaderStyle());

        Label sub = new Label("Evaluates problem signature and selects the optimal algorithm strategy:");
        sub.setStyle(UITheme.getSubheaderStyle());

        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);

        ComboBox<String> sigCombo = new ComboBox<>();
        sigCombo.getItems().addAll(
                "Resume Skill Matching (Linear String)",
                "Interviewer Panel Selection (Bitmask DP)",
                "Drive Allocation with Quotas (Network Flow)",
                "Interview Slot Clash Resolution (NP-Hard)",
                "Batch Percentile Calculation (Randomized)"
        );
        sigCombo.setValue("Drive Allocation with Quotas (Network Flow)");
        sigCombo.setPrefWidth(320);

        Button evalBtn = new Button("Evaluate Problem Signature");
        evalBtn.setStyle(UITheme.getButtonStyle(UITheme.PRIMARY_BLUE));

        row.getChildren().addAll(new Label("Select Task:"), sigCombo, evalBtn);
        ((Label) row.getChildren().get(0)).setStyle("-fx-text-fill: #0f172a; -fx-font-weight: bold;");

        TextArea out = createCleanTextArea();
        VBox.setVgrow(out, Priority.ALWAYS);

        ProblemSignatureEvaluator evaluator = new ProblemSignatureEvaluator();

        Runnable doEval = () -> {
            var res = evaluator.evaluate(sigCombo.getValue());
            StringBuilder sb = new StringBuilder();
            sb.append("Problem Title:              ").append(res.signature.getProblemTitle()).append("\n");
            sb.append("Course Outcome:            ").append(res.signature.getProblemClass().getMappedCO()).append("\n");
            sb.append("Input Characteristics:     ").append(res.signature.getInputCharacteristics()).append("\n");
            sb.append("Selected Strategy:         ").append(res.signature.getRecommendedStrategy()).append("\n");
            sb.append("Worst-Case Time:           ").append(res.signature.getWorstCaseTimeComplexity()).append("\n");
            sb.append("Space Complexity:          ").append(res.signature.getSpaceComplexity()).append("\n\n");
            sb.append("Evaluation Rationale:\n").append(res.signature.getEvaluationRationale()).append("\n\n");
            sb.append("Proof Sketch:\n").append(res.theoreticalProofOutline).append("\n");
            out.setText(sb.toString());
        };

        evalBtn.setOnAction(e -> doEval.run());
        doEval.run();

        box.getChildren().addAll(title, sub, row, out);
        return box;
    }

    // ================= CO2: ONLY RABIN-KARP ROLLING HASH =================
    private VBox createCO2Pane() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(14));
        box.setStyle(UITheme.getCardStyle());

        Label title = new Label("CO2: Rabin-Karp Algorithm with Polynomial Rolling Hash");
        title.setStyle(UITheme.getHeaderStyle());

        Label sub = new Label("Searches for skills and keywords across resumes in linear O(N + M) expected time:");
        sub.setStyle(UITheme.getSubheaderStyle());

        HBox inputRow = new HBox(10);
        inputRow.setAlignment(Pos.CENTER_LEFT);

        TextField patField = new TextField("Distributed Systems");
        patField.setPrefWidth(220);

        Button runBtn = new Button("Search Keyword (Rolling Hash)");
        runBtn.setStyle(UITheme.getButtonStyle(UITheme.PRIMARY_BLUE));

        inputRow.getChildren().addAll(new Label("Keyword:"), patField, runBtn);
        ((Label) inputRow.getChildren().get(0)).setStyle("-fx-text-fill: #0f172a; -fx-font-weight: bold;");

        TextArea out = createCleanTextArea();
        VBox.setVgrow(out, Priority.ALWAYS);

        runBtn.setOnAction(e -> {
            String query = patField.getText().trim();
            if (query.isEmpty()) return;

            List<Student> students = dao.getAllStudents();
            StringBuilder sb = new StringBuilder();
            sb.append("Rabin-Karp Rolling Hash Search for: \"").append(query).append("\"\n");
            sb.append("Hash Formula: H = (sum s[i]*B^(m-1-i)) mod (10^9+7)\n\n");

            int totalMatches = 0;
            for (Student s : students) {
                List<Integer> matches = RabinKarpMatcher.search(s.getResumeText(), query);
                if (!matches.isEmpty()) {
                    totalMatches += matches.size();
                    sb.append(String.format(" • Found in %s (%s): %d match(es) at character index %s\n",
                            s.getName(), s.getRollNo(), matches.size(), matches));
                }
            }

            if (totalMatches == 0) {
                sb.append("No occurrences found in student resumes.\n");
            } else {
                sb.append(String.format("\nTotal matches across applicant pool: %d\n", totalMatches));
            }
            out.setText(sb.toString());
        });

        box.getChildren().addAll(title, sub, inputRow, out);
        return box;
    }

    // ================= CO3: ONLY BITMASK DP =================
    private VBox createCO3Pane() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(14));
        box.setStyle(UITheme.getCardStyle());

        Label title = new Label("CO3: Bitmask Dynamic Programming");
        title.setStyle(UITheme.getHeaderStyle());

        Label sub = new Label("Solves the minimum-cost team skill-coverage problem over powerset {0, 1}^K in O(M · 2^K) time:");
        sub.setStyle(UITheme.getSubheaderStyle());

        Button runBtn = new Button("Run Bitmask DP Coverage Solver");
        runBtn.setStyle(UITheme.getButtonStyle(UITheme.PRIMARY_BLUE));

        TextArea out = createCleanTextArea();
        VBox.setVgrow(out, Priority.ALWAYS);

        runBtn.setOnAction(e -> {
            List<String> skills = List.of("Java", "Algorithms", "SQL", "Cloud", "OS", "System Design");
            List<Student> students = dao.getAllStudents();
            List<BitmaskSkillCoverDP.CandidateProfile> profiles = new ArrayList<>();
            for (Student s : students) {
                profiles.add(new BitmaskSkillCoverDP.CandidateProfile(s.getId(), s.getName(), new ArrayList<>(s.getSkills()), 20));
            }
            var sol = BitmaskSkillCoverDP.solve(skills, profiles);

            StringBuilder sb = new StringBuilder();
            sb.append("Bitmask Dynamic Programming - Team Skill Coverage\n");
            sb.append("Required Skills: ").append(skills).append("\n");
            sb.append(sol.complexityAnalysis).append("\n");
            sb.append("States Evaluated: ").append(sol.statesEvaluated).append(" | Time: ").append(sol.executionTimeNanos / 1000).append(" µs\n\n");
            sb.append("Selected Minimal Team:\n");
            for (var c : sol.selectedCandidates) {
                sb.append(String.format(" • %s (Skills: %s)\n", c.name, c.skills));
            }
            out.setText(sb.toString());
        });

        box.getChildren().addAll(title, sub, runBtn, out);
        return box;
    }

    // ================= CO4: ONLY EDMONDS-KARP NETWORK FLOW =================
    private VBox createCO4Pane() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(14));
        box.setStyle(UITheme.getCardStyle());

        Label title = new Label("CO4: Edmonds-Karp Network Flow & Max-Flow / Min-Cut Duality");
        title.setStyle(UITheme.getHeaderStyle());

        Label sub = new Label("Allocates students to company drives subject to interview limits and intake quotas:");
        sub.setStyle(UITheme.getSubheaderStyle());

        HBox row = new HBox(12);
        row.setAlignment(Pos.CENTER_LEFT);
        Label capLbl = new Label("Max Interviews per Student:");
        capLbl.setStyle("-fx-text-fill: #0f172a; -fx-font-weight: bold;");
        Spinner<Integer> spinner = new Spinner<>(1, 5, 2);

        Button runBtn = new Button("Run Edmonds-Karp Allocation");
        runBtn.setStyle(UITheme.getButtonStyle(UITheme.PRIMARY_BLUE));

        row.getChildren().addAll(capLbl, spinner, runBtn);

        TextArea out = createCleanTextArea();
        VBox.setVgrow(out, Priority.ALWAYS);

        runBtn.setOnAction(e -> {
            List<Student> students = dao.getAllStudents();
            List<CompanyDrive> drives = dao.getAllDrives();

            var rep = PlacementFlowNetwork.allocate(students, drives, spinner.getValue());

            StringBuilder sb = new StringBuilder();
            sb.append("Edmonds-Karp Network Flow Allocation\n");
            sb.append(String.format("Total Students Placed / Max Flow: %d\n", rep.maxPlacements));
            sb.append(String.format("Computation Time: %d µs\n\n", rep.edmondsKarpTimeNanos / 1000));
            sb.append(rep.dualityExplanation).append("\n\n");
            sb.append("Saturated Min-Cut Bottlenecks:\n");
            for (String b : rep.minCutBottlenecks) {
                sb.append(" • ").append(b).append("\n");
            }
            sb.append("\nAssigned Matches:\n");
            for (var m : rep.matchedPairs) {
                sb.append(String.format(" • %s -> %s (%s)\n", m.student.getName(), m.drive.getCompanyName(), m.drive.getRoleTitle()));
            }
            out.setText(sb.toString());
        });

        box.getChildren().addAll(title, sub, row, out);
        return box;
    }

    // ================= CO5: ONLY 2-APPROXIMATION VERTEX COVER =================
    private VBox createCO5Pane() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(14));
        box.setStyle(UITheme.getCardStyle());

        Label title = new Label("CO5: 2-Approximation for Minimum Vertex Cover (NP-Hard)");
        title.setStyle(UITheme.getHeaderStyle());

        Label sub = new Label("Resolves interview schedule clashes with provable approximation ratio <= 2.0:");
        sub.setStyle(UITheme.getSubheaderStyle());

        Button runBtn = new Button("Run 2-Approximation Solver");
        runBtn.setStyle(UITheme.getButtonStyle(UITheme.PRIMARY_BLUE));

        TextArea out = createCleanTextArea();
        VBox.setVgrow(out, Priority.ALWAYS);

        runBtn.setOnAction(e -> {
            ConflictGraph graph = ConflictGraph.createSamplePlacementConflictGraph();
            var res = VertexCoverApproximation.solve(graph);

            StringBuilder sb = new StringBuilder();
            sb.append("Minimum Vertex Cover 2-Approximation on Conflict Graph\n");
            sb.append(String.format("Graph: %d interview slots, %d schedule clashes\n", graph.getNodeCount(), graph.getEdgeCount()));
            sb.append(String.format(" • 2-Approx Cover Size: |C_approx| = %d\n", res.approxCover.size()));
            sb.append(String.format(" • Exact Optimal Size:   |C*|        = %d\n", res.exactCover.size()));
            sb.append(String.format(" • Actual Ratio:         ρ           = %.2f (Provable Guarantee: ρ <= 2.00)\n\n", res.actualRatio));
            sb.append(res.proofText).append("\n\n");
            sb.append("Maximum Independent Set (Clash-Free Simultaneous Slots):\n");
            for (int id : res.maxIndependentSet) {
                sb.append(" • ").append(graph.getNodes().get(id)).append("\n");
            }
            out.setText(sb.toString());
        });

        box.getChildren().addAll(title, sub, runBtn, out);
        return box;
    }

    // ================= CO6: ONLY LAS VEGAS QUICKSELECT =================
    private VBox createCO6Pane() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(14));
        box.setStyle(UITheme.getCardStyle());

        Label title = new Label("CO6: Las Vegas Randomized QuickSelect Algorithm");
        title.setStyle(UITheme.getHeaderStyle());

        Label sub = new Label("Computes median/percentile CGPA cutoff in expected O(N) time with 100% exact result:");
        sub.setStyle(UITheme.getSubheaderStyle());

        Button runBtn = new Button("Find Median Cutoff (Las Vegas)");
        runBtn.setStyle(UITheme.getButtonStyle(UITheme.PRIMARY_BLUE));

        TextArea out = createCleanTextArea();
        VBox.setVgrow(out, Priority.ALWAYS);

        runBtn.setOnAction(e -> {
            List<Student> students = dao.getAllStudents();
            double[] cgpas = new double[students.size()];
            for (int i = 0; i < students.size(); i++) cgpas[i] = students.get(i).getCgpa();
            int medianK = cgpas.length / 2;

            var res = LasVegasQuickSelect.select(cgpas, medianK);

            StringBuilder sb = new StringBuilder();
            sb.append("Las Vegas QuickSelect Algorithm\n");
            sb.append(String.format("Applicant Pool Size: %d candidates\n", cgpas.length));
            sb.append(String.format("Calculated Median CGPA Cutoff: %.2f\n", res.value));
            sb.append(String.format("Comparisons: %d | Partitions: %d | Time: %d ns\n\n",
                    res.comparisons, res.partitionSteps, res.executionTimeNanos));
            sb.append("Las Vegas Property:\n").append(res.propertyNote).append("\n");
            out.setText(sb.toString());
        });

        box.getChildren().addAll(title, sub, runBtn, out);
        return box;
    }

    private TextArea createCleanTextArea() {
        TextArea area = new TextArea();
        area.setEditable(false);
        area.setWrapText(true);
        area.setPrefRowCount(10);
        area.setStyle("-fx-control-inner-background: #ffffff; -fx-text-fill: #0f172a; -fx-font-family: monospace; -fx-border-color: #cbd5e1; -fx-border-radius: 4px;");
        return area;
    }
}
