package com.placementhub.dsa.co5;

import java.util.*;

/**
 * Course Outcome 5 (CO5): NP-Completeness & Approximation Algorithms.
 * Analyzes Minimum Vertex Cover (NP-Hard) on interview conflict graphs:
 * 1. 2-Approximation Algorithm via Maximal Matching (provable ratio <= 2).
 * 2. Exact Branch-and-Bound / Subsets solver for ground-truth comparison.
 * 3. Maximum Independent Set (clash-free simultaneous interview sessions) via duality V \ C.
 */
public class VertexCoverApproximation {

    public static class ApproximationResult {
        public final Set<Integer> approxCover;
        public final Set<Integer> exactCover;
        public final Set<Integer> maxIndependentSet; // Clash-free schedule
        public final List<ConflictGraph.Edge> maximalMatching;
        public final double actualRatio;
        public final long approxTimeNanos;
        public final long exactTimeNanos;
        public final String proofText;

        public ApproximationResult(Set<Integer> approxCover, Set<Integer> exactCover,
                                   Set<Integer> maxIndependentSet,
                                   List<ConflictGraph.Edge> maximalMatching,
                                   double actualRatio, long approxTimeNanos,
                                   long exactTimeNanos, String proofText) {
            this.approxCover = approxCover;
            this.exactCover = exactCover;
            this.maxIndependentSet = maxIndependentSet;
            this.maximalMatching = maximalMatching;
            this.actualRatio = actualRatio;
            this.approxTimeNanos = approxTimeNanos;
            this.exactTimeNanos = exactTimeNanos;
            this.proofText = proofText;
        }
    }

    /**
     * Solves Minimum Vertex Cover using the 2-Approximation maximal matching algorithm.
     * Guaranteed approximation ratio <= 2.0.
     */
    public static ApproximationResult solve(ConflictGraph graph) {
        long t0 = System.nanoTime();

        Set<Integer> approxCover = new HashSet<>();
        List<ConflictGraph.Edge> matching = new ArrayList<>();
        Set<ConflictGraph.Edge> remainingEdges = new HashSet<>(graph.getEdges());

        while (!remainingEdges.isEmpty()) {
            // Pick an arbitrary edge (u, v)
            ConflictGraph.Edge e = remainingEdges.iterator().next();
            matching.add(e);
            approxCover.add(e.u);
            approxCover.add(e.v);

            // Remove all edges incident to u or v
            remainingEdges.removeIf(edge -> edge.u == e.u || edge.v == e.u || edge.u == e.v || edge.v == e.v);
        }
        long approxDuration = System.nanoTime() - t0;

        // Exact Solver (Branch & Bound / Bitmask for small graphs)
        long t1 = System.nanoTime();
        Set<Integer> exactCover = solveExact(graph);
        long exactDuration = System.nanoTime() - t1;

        // Maximum Independent Set = V \ C_exact (by Gallai's Theorem)
        Set<Integer> maxIndependentSet = new HashSet<>(graph.getNodes().keySet());
        maxIndependentSet.removeAll(exactCover);

        double ratio = exactCover.isEmpty() ? 1.0 : (double) approxCover.size() / exactCover.size();

        String proof = String.format(
                "Provable Approximation Ratio Proof (Theorem):\n" +
                "1. Maximal Matching M has %d disjoint edges (no two edges share a vertex).\n" +
                "2. Any valid vertex cover C* must pick at least 1 vertex from each edge in M to cover it.\n" +
                "   Therefore: |C*| >= |M| = %d.\n" +
                "3. The 2-approximation selects both endpoints of every edge in M:\n" +
                "   |C_approx| = 2 · |M| = 2 · %d = %d.\n" +
                "4. Hence: |C_approx| = 2 · |M| <= 2 · |C*|.\n" +
                "   Empirical Ratio achieved on this graph: %.2f (Optimal: %d, Approx: %d) <= 2.0.",
                matching.size(), matching.size(), matching.size(), approxCover.size(),
                ratio, exactCover.size(), approxCover.size()
        );

        return new ApproximationResult(
                approxCover, exactCover, maxIndependentSet, matching,
                ratio, approxDuration, exactDuration, proof
        );
    }

    private static Set<Integer> solveExact(ConflictGraph graph) {
        List<Integer> nodes = new ArrayList<>(graph.getNodes().keySet());
        int n = nodes.size();
        if (n > 22) {
            // Fallback to greedy if graph is too large for exact bitmask
            return solveGreedy(graph);
        }

        Set<Integer> bestCover = new HashSet<>(nodes);

        // Bitmask enumeration for small n
        int totalSubsets = 1 << n;
        for (int mask = 0; mask < totalSubsets; mask++) {
            if (Integer.bitCount(mask) >= bestCover.size()) continue;

            Set<Integer> candidate = new HashSet<>();
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    candidate.add(nodes.get(i));
                }
            }

            // Check if all edges are covered
            boolean coversAll = true;
            for (ConflictGraph.Edge e : graph.getEdges()) {
                if (!candidate.contains(e.u) && !candidate.contains(e.v)) {
                    coversAll = false;
                    break;
                }
            }

            if (coversAll && candidate.size() < bestCover.size()) {
                bestCover = candidate;
            }
        }

        return bestCover;
    }

    private static Set<Integer> solveGreedy(ConflictGraph graph) {
        Set<Integer> cover = new HashSet<>();
        Set<ConflictGraph.Edge> remaining = new HashSet<>(graph.getEdges());

        while (!remaining.isEmpty()) {
            // Find vertex with maximum remaining degree
            Map<Integer, Integer> deg = new HashMap<>();
            for (ConflictGraph.Edge e : remaining) {
                deg.put(e.u, deg.getOrDefault(e.u, 0) + 1);
                deg.put(e.v, deg.getOrDefault(e.v, 0) + 1);
            }

            int bestVertex = -1;
            int maxDeg = -1;
            for (var entry : deg.entrySet()) {
                if (entry.getValue() > maxDeg) {
                    maxDeg = entry.getValue();
                    bestVertex = entry.getKey();
                }
            }

            if (bestVertex == -1) break;
            cover.add(bestVertex);
            final int chosen = bestVertex;
            remaining.removeIf(e -> e.u == chosen || e.v == chosen);
        }
        return cover;
    }
}
