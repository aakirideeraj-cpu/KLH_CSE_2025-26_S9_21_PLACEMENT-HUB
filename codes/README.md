# PLACEMENT HUB
### Campus Recruitment Eligibility & DSA Optimization Platform

> **Pair Programming & DSA Project Submission**  
> **Course Outcome Coverage:** CO1, CO2, CO3, CO4, CO5, CO6 (Strictly 1 Concept Per Outcome)  
> **Frontend:** JavaFX Desktop Application  
> **Backend:** MySQL 8.0 / Embedded SQLite (JDBC)  

---

## 1. Project Overview & Abstract (From Slides)

**Placement Hub** connects students and recruiters through direct eligibility matching. Recruiters register recruitment drives with CGPA cutoffs and required skills. Students enter or select their profile (CGPA, skills, aptitude score) to see which companies they qualify for. Recruiters can search and filter students matching their drive requirements, eliminating manual cross-checking between placement cells, students, and companies.

---

## 2. Syllabus Course Outcomes (CO1 to CO6) — Exactly 1 Per CO

| CO No | Course Outcome | BTL | Chosen Algorithm & Structure | How It Is Used in Placement Hub |
| :---: | :--- | :---: | :--- | :--- |
| **CO1** | Evaluate problem-class signatures and select an appropriate strategy. | **5 (Evaluate)** | **Problem-Class Signature Evaluator** (`ProblemSignatureEvaluator`) | Evaluates problem signatures (input size, constraints, objective), computes worst-case time/space bounds, and selects the optimal algorithm strategy with decision-tree reasoning. |
| **CO2** | Linear-time string algorithms. | **3 (Apply)** | **Rabin-Karp Algorithm with Rolling Hash** (`RabinKarpMatcher`) | Performs linear $O(N + M)$ expected keyword & skill search across candidate resumes and job descriptions using polynomial rolling hash. |
| **CO3** | Advanced Dynamic Programming patterns. | **3 (Apply)** | **Bitmask Dynamic Programming** (`BitmaskSkillCoverDP`) | Solves the minimum team size / cost candidate skill-coverage problem over powerset $\{0, 1\}^K$ in $O(M \cdot 2^K)$ time. |
| **CO4** | Network-flow algorithms & max-flow / min-cut duality. | **3 (Apply)** | **Edmonds-Karp Network Flow Algorithm** (`EdmondsKarpMaxFlow`) | Capacity-constrained bipartite matching of students to drives under student interview limits and company quotas. Min-cut identifies hiring bottlenecks. |
| **CO5** | NP-completeness and approximation algorithms with provable ratios. | **4 (Analyze)** | **2-Approximation for Minimum Vertex Cover** (`VertexCoverApproximation`) | Resolves interview schedule clashes on the conflict graph with a provable approximation ratio $|C| \le 2|C^*|$ using maximal matching. |
| **CO6** | Randomised algorithms. | **3 (Apply)** | **Las Vegas Randomized QuickSelect** (`LasVegasQuickSelect`) | Computes the exact median and percentile CGPA cutoff across the candidate pool in expected $O(N)$ time with 100% exact result guarantee. |

---

## 3. Core Data Structures (From PPT Expected Outcomes)
- **`HashMap<String, Student>` / `HashMap<String, CompanyDrive>`:** $O(1)$ fast lookups for candidate roll numbers and drive codes.
- **`StudentBST` (Binary Search Tree):** Custom BST keyed on CGPA for $O(\log N + K)$ range queries (e.g. $[7.5, 9.0]$) and in-order sorted traversal.
- **`HashSet<String>`:** High-speed set operations for skill intersection and qualification checking.
- **`StudentPriorityQueue` (Max-Heap):** Max-heap ranking candidates by composite score (50% CGPA, 30% Aptitude, 20% Skill Match).

---

## 4. UI Screens (JavaFX)
1. **Screen 1: Student Search & Eligibility**  
   Students select their profile to see which drives they qualify for (`QUALIFIED` vs `NOT ELIGIBLE`) with exact verification details. Includes role/keyword search powered by Rabin-Karp rolling hash.
2. **Screen 2: Recruiter Search & Filter**  
   Recruiters filter candidates by CGPA range via BST, rank top candidates via Max-Heap Priority Queue, and run Bitmask DP team coverage.
3. **Screen 3: Company Registration**  
   Recruiters register new recruitment drives with CGPA cutoffs, required skills, intake quotas, and CTC.
4. **Screen 4: DSA Optimization Engines (CO1 to CO6)**  
   Clean interface to run and inspect the 1 algorithm per CO with real calculations and metrics.

---

## 5. How to Run the Project

### Prerequisites
- Java 17+
- Maven 3.8+

### Launch JavaFX Application
```powershell
cd d:\DSA3
mvn javafx:run
```

### Run Automated 1-per-CO Verification (Console)
```powershell
cd d:\DSA3
mvn exec:java "-Dexec.args=--cli"
```
