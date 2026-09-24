package com.placementhub.dsa.co1;

import com.placementhub.model.ProblemSignature;
import com.placementhub.model.ProblemSignature.ProblemClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Course Outcome 1 (CO1) - Blooms Taxonomy Level 5 (Evaluate).
 * Evaluates problem-class signatures (substring search, sequence alignment, flow on a network,
 * NP-hard scheduling, parallel scan/reduce) and selects an optimal advanced-algorithm strategy.
 */
public class ProblemSignatureEvaluator {

    public static class EvaluationResult {
        public final ProblemSignature signature;
        public final String rationale;
        public final String alternativesRejected;
        public final String theoreticalProofOutline;

        public EvaluationResult(ProblemSignature signature, String rationale,
                                String alternativesRejected, String theoreticalProofOutline) {
            this.signature = signature;
            this.rationale = rationale;
            this.alternativesRejected = alternativesRejected;
            this.theoreticalProofOutline = theoreticalProofOutline;
        }
    }

    /**
     * Evaluates a scenario from the Placement Hub domain and derives its problem signature.
     */
    public EvaluationResult evaluate(String taskIdentifier) {
        String key = taskIdentifier.toLowerCase().trim();

        if (key.contains("resume") || key.contains("string") || key.contains("pattern") || key.contains("search")) {
            ProblemSignature sig = new ProblemSignature(
                    "Resume Skill & Keyword Pattern Matching",
                    ProblemClass.SUBSTRING_SEARCH,
                    "Text length N (resume ~5,000 chars), Pattern length M (skill ~15 chars), multiple queries",
                    "1D Character Array with Linear Structure",
                    "KMP (Knuth-Morris-Pratt) & Rabin-Karp Rolling Hash + Suffix Array",
                    "O(N + M) Deterministic Linear Time",
                    "O(M) for KMP π-table; O(N) for Suffix Array",
                    "100% Exact Matching without False Negatives",
                    "The problem signature requires linear-time substring detection across resumes without character backtracking. " +
                            "Naive search takes O(N·M) which degrades under heavy applicant pools. KMP exploits the deterministic finite automaton " +
                            "structure via π-table to guarantee O(N + M) worst-case time."
            );
            return new EvaluationResult(
                    sig,
                    "KMP eliminates redundant comparisons by preprocessing the failure function π[q] = max{k : P_k ⊐ P_q}.",
                    "Rejected Naive String Matching (O(N·M) worst case). Rejected Boyer-Moore because worst case without Galil rule is O(N·M).",
                    "Theorem: The while loop inside KMP amortizes to at most 2N comparisons because q is incremented at most N times."
            );
        }

        if (key.contains("panel") || key.contains("subset") || key.contains("bitmask") || key.contains("skills cover")) {
            ProblemSignature sig = new ProblemSignature(
                    "Optimal Interviewer Panel / Skill-Set Coverage",
                    ProblemClass.COMBINATORIAL_OPTIMIZATION,
                    "K required skills (K <= 16), M interviewers/candidates each possessing a skill subset",
                    "Exponential State Space on Subsets {0, 1}^K with Optimal Substructure",
                    "Bitmask Dynamic Programming (dp[mask])",
                    "O(M · 2^K) Polynomial in candidates, Fixed-Parameter Tractable in K",
                    "O(2^K) State Space Table",
                    "Exact Global Minimum Cost / Maximum Competency",
                    "The problem exhibits optimal substructure and overlapping subproblems over the powerset of required competencies. " +
                            "Because K <= 16, 2^K <= 65,536, which fits effortlessly in cache. Bitmask DP achieves polynomial time for fixed K, " +
                            "avoiding the astronomical O(2^M) brute force search over all interviewer combinations."
            );
            return new EvaluationResult(
                    sig,
                    "Recurrence dp[mask | candidateSkills] = min(dp[mask | candidateSkills], dp[mask] + cost). Subproblem graph is a DAG.",
                    "Rejected Naive Power Set search O(2^M) since M candidates can exceed 50. Rejected pure greedy because set-cover greedy is (ln |U| + 1) approximation, not exact.",
                    "Optimality follows by mathematical induction over the Hamming weight of the bitmasks."
            );
        }

        if (key.contains("quota") || key.contains("capacity") || key.contains("flow") || key.contains("network") || key.contains("allocation")) {
            ProblemSignature sig = new ProblemSignature(
                    "Capacity-Constrained Placement & Interview Slot Allocation",
                    ProblemClass.NETWORK_FLOW,
                    "V vertices (|Students| + |Drives| + Source/Sink), E bipartite edges, capacities on student interviews and drive quotas",
                    "Directed Flow Network G = (V, E) with Non-negative Capacities c(u, v)",
                    "Edmonds-Karp (BFS) and Dinic's Algorithm with Max-Flow / Min-Cut Duality",
                    "Edmonds-Karp: O(V · E²); Dinic: O(V² · E), on unit networks O(E √V)",
                    "O(V + E) for Adjacency List and Level Graph",
                    "Globally Optimal Maximum Matching / Maximum Flow",
                    "Matching students to drives subject to dual constraints (student application cap and company intake quota) is an instance " +
                            "of the Maximum Bipartite Matching with Capacities problem. The Max-Flow Min-Cut Theorem (Ford-Fulkerson 1956) guarantees that " +
                            "maximum flow equals the capacity of the minimum bottleneck cut, directly highlighting recruiter intake shortages."
            );
            return new EvaluationResult(
                    sig,
                    "Dinic's algorithm uses BFS to build level graphs and DFS to saturate blocking flows, reducing augmenting phases to at most V.",
                    "Rejected Greedy Matching because greedy choices produce suboptimal local matches without augmenting capability. Rejected Bellman-Ford as edge costs are zero/unit.",
                    "Theorem (Max-Flow Min-Cut): If f is a flow in G, the following are equivalent: (1) f is maximum, (2) residual G_f has no augmenting path, (3) |f| = c(S, T) for some cut."
            );
        }

        if (key.contains("conflict") || key.contains("clash") || key.contains("scheduling") || key.contains("np") || key.contains("vertex cover")) {
            ProblemSignature sig = new ProblemSignature(
                    "Interview Slot Conflict Resolution (NP-Hard)",
                    ProblemClass.NP_HARD_SCHEDULING,
                    "Graph G = (V, E) of conflicting interview sessions, where edges represent time/interviewer overlaps",
                    "Undirected Conflict Graph G = (V, E), Reducible from 3-SAT",
                    "2-Approximation Algorithm via Maximal Matching + Exact Branch & Bound for small N",
                    "2-Approximation: O(V + E) Linear Time; Exact: O(2^k · V) Fixed-Parameter Tractable",
                    "O(V + E) for Graph Representation",
                    "Provable 2-Approximation Ratio: |C| <= 2 · |C*|",
                    "Eliminating scheduling clashes by cancelling the minimum number of conflicting interview slots is Karp's NP-Complete Minimum Vertex Cover problem. " +
                            "Unless P = NP, no polynomial-time exact algorithm exists. The 2-approximation picks maximal matching edges and adds both endpoints, " +
                            "guaranteeing a solution within twice the theoretical optimum in O(V + E) time."
            );
            return new EvaluationResult(
                    sig,
                    "Maximal matching edges M are disjoint; any valid vertex cover must include at least one endpoint from each edge in M, so |C*| >= |M|. The algorithm selects 2|M| vertices, hence |C| = 2|M| <= 2|C*|.",
                    "Rejected Brute Force O(2^V) for large graphs due to combinatorial explosion. Rejected naive degree greedy as it achieves only O(log V) approximation.",
                    "Formal Proof: Let M be a maximal matching. (1) C = endpoints(M) covers all edges (otherwise M is not maximal). (2) |C*| >= |M| (edges in M share no vertices). (3) |C| = 2|M| <= 2|C*|."
            );
        }

        // Default: Parallel Batch Processing (CO6)
        ProblemSignature sig = new ProblemSignature(
                "Large-Scale Batch Percentile & Rank Computation",
                ProblemClass.PARALLEL_BATCH_PROCESSING,
                "N applicant scores (N up to 100,000+), associative operations, multi-core CPU architecture",
                "1D Array with Associative Monoid Algebra",
                "Parallel Prefix Sum (Scan) & Parallel Reduce with Work-Span Analysis",
                "Work T₁ = O(N), Span T_∞ = O(log N), Parallel Time T_P = O(N/P + log N)",
                "O(N) Auxiliary Tree Memory",
                "Deterministic Exact Prefix Sum & Score Statistics",
                "Computing running percentiles, cumulative rank distributions, and seat prefix offsets across tens of thousands of applicants. " +
                        "A sequential loop suffers from serial execution bottleneck T₁ = O(N). Parallel Prefix Sum constructs an up-sweep / down-sweep balanced binary tree, " +
                        "reducing the critical path span to T_∞ = O(log N). By Brent's Theorem, speedup approaches P processors."
        );
        return new EvaluationResult(
                sig,
                "ForkJoin framework splits work recursively until base threshold, executing parallel up-sweep and down-sweep phases.",
                "Rejected single-threaded stream for high-throughput batching. Rejected sorting-based rank when prefix scan suffices in linear work.",
                "Work-Span Analysis: Work T₁ = 2N additions, Span T_∞ = 2 lg N steps. Theoretical Speedup S_P = T₁ / T_P <= P."
        );
    }

    public List<ProblemSignature> getPresetSignatures() {
        List<ProblemSignature> list = new ArrayList<>();
        list.add(evaluate("resume pattern search").signature);
        list.add(evaluate("panel skills cover bitmask").signature);
        list.add(evaluate("capacity constrained quota flow").signature);
        list.add(evaluate("interview clash conflict scheduling").signature);
        list.add(evaluate("parallel batch percentile reduce").signature);
        return list;
    }
}
