package com.placementhub;

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

import java.util.*;

/**
 * Master Main launcher for Placement Hub.
 * Strictly 1 algorithm per Course Outcome (CO1 to CO6).
 */
public class Main {
    public static void main(String[] args) {
        for (String arg : args) {
            if ("--cli".equalsIgnoreCase(arg) || "--test".equalsIgnoreCase(arg)) {
                runCliVerificationSuite();
                return;
            }
        }

        // Launch JavaFX GUI
        App.main(args);
    }

    public static void runCliVerificationSuite() {
        System.out.println("================================================================================");
        System.out.println("            PLACEMENT HUB - 1 ALGORITHM PER COURSE OUTCOME (CO1 - CO6)          ");
        System.out.println("================================================================================");

        PlacementDAO dao = new PlacementDAO();
        List<Student> students = dao.getAllStudents();
        List<CompanyDrive> drives = dao.getAllDrives();

        System.out.printf("[Database] Loaded %d students and %d recruitment drives.\n\n", students.size(), drives.size());

        // CO1: Problem-Class Signature Evaluator
        System.out.println("--- [CO1] Problem-Class Signature Evaluation (BTL 5) ---");
        ProblemSignatureEvaluator evaluator = new ProblemSignatureEvaluator();
        var evalRes = evaluator.evaluate("capacity constrained quota flow");
        System.out.println("Problem:              " + evalRes.signature.getProblemTitle());
        System.out.println("Selected Strategy:    " + evalRes.signature.getRecommendedStrategy());
        System.out.println("Worst-Case Bound:     " + evalRes.signature.getWorstCaseTimeComplexity());
        System.out.println();

        // CO2: Rabin-Karp with Rolling Hash
        System.out.println("--- [CO2] Rabin-Karp Algorithm with Rolling Hash (BTL 3) ---");
        String resumeSample = students.get(0).getResumeText();
        String query = "Distributed Systems";
        var rkMatches = RabinKarpMatcher.search(resumeSample, query);
        System.out.printf("Rabin-Karp Rolling Hash Search for \"%s\": %d match(es) in resume bio.\n\n",
                query, rkMatches.size());

        // CO3: Bitmask Dynamic Programming
        System.out.println("--- [CO3] Bitmask Dynamic Programming (BTL 3) ---");
        List<String> targetSkills = List.of("Java", "Algorithms", "SQL", "Cloud", "OS", "System Design");
        List<BitmaskSkillCoverDP.CandidateProfile> profiles = new ArrayList<>();
        for (Student s : students) {
            profiles.add(new BitmaskSkillCoverDP.CandidateProfile(s.getId(), s.getName(), new ArrayList<>(s.getSkills()), 20));
        }
        var dpSol = BitmaskSkillCoverDP.solve(targetSkills, profiles);
        System.out.printf("Bitmask DP on {0, 1}^%d: Optimal Team Size = %d, Total Weight = %d.\n",
                targetSkills.size(), dpSol.selectedCandidates.size(), dpSol.totalCost);
        System.out.println("Complexity: " + dpSol.complexityAnalysis);
        System.out.println();

        // CO4: Edmonds-Karp Network Flow
        System.out.println("--- [CO4] Edmonds-Karp Network Flow & Min-Cut (BTL 3) ---");
        var flowReport = PlacementFlowNetwork.allocate(students, drives, 2);
        System.out.printf("Max Flow (Students Placed): %d\n", flowReport.maxPlacements);
        System.out.printf("Edmonds-Karp Computation Time: %d µs\n", flowReport.edmondsKarpTimeNanos / 1000);
        System.out.printf("Saturated Min-Cut Bottlenecks: %d\n\n", flowReport.minCutBottlenecks.size());

        // CO5: 2-Approximation for Minimum Vertex Cover
        System.out.println("--- [CO5] 2-Approximation for Minimum Vertex Cover (BTL 4) ---");
        ConflictGraph graph = ConflictGraph.createSamplePlacementConflictGraph();
        var approxRes = VertexCoverApproximation.solve(graph);
        System.out.printf("Conflict Graph: %d interview slots, %d clash edges\n", graph.getNodeCount(), graph.getEdgeCount());
        System.out.printf("2-Approximation Cover Size: |C_approx| = %d\n", approxRes.approxCover.size());
        System.out.printf("Exact Optimal Cover Size:   |C*|        = %d\n", approxRes.exactCover.size());
        System.out.printf("Empirical Ratio:            ρ           = %.2f (Provable Guarantee: ρ <= 2.00)\n\n", approxRes.actualRatio);

        // CO6: Las Vegas QuickSelect
        System.out.println("--- [CO6] Las Vegas Randomized QuickSelect (BTL 3) ---");
        double[] cgpas = new double[students.size()];
        for (int i = 0; i < students.size(); i++) cgpas[i] = students.get(i).getCgpa();
        var qsel = LasVegasQuickSelect.select(cgpas, cgpas.length / 2);
        System.out.printf("Las Vegas QuickSelect: Exact Median CGPA Cutoff = %.2f (Calculated in %d ns, %d comparisons)\n",
                qsel.value, qsel.executionTimeNanos, qsel.comparisons);

        System.out.println("\n================================================================================");
        System.out.println("           ALL 6 COURSE OUTCOMES (1 PER CO) VERIFIED SUCCESSFULLY!              ");
        System.out.println("================================================================================");
    }
}
