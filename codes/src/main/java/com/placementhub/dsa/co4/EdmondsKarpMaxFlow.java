package com.placementhub.dsa.co4;

import java.util.*;

/**
 * Course Outcome 4 (CO4): Ford-Fulkerson with Edmonds-Karp implementation.
 * Finds maximum flow using shortest augmenting paths via Breadth-First Search (BFS).
 * Time Complexity: O(V · E²).
 */
public class EdmondsKarpMaxFlow {

    public static class FlowResult {
        public final int maxFlow;
        public final int[][] flow;
        public final int[][] capacity;
        public final List<String> augmentationSteps;
        public final Set<Integer> sourceCutSet; // Vertices reachable from source in residual graph (Min-Cut)
        public final int totalAugmentingPaths;

        public FlowResult(int maxFlow, int[][] flow, int[][] capacity,
                          List<String> augmentationSteps, Set<Integer> sourceCutSet,
                          int totalAugmentingPaths) {
            this.maxFlow = maxFlow;
            this.flow = flow;
            this.capacity = capacity;
            this.augmentationSteps = augmentationSteps;
            this.sourceCutSet = sourceCutSet;
            this.totalAugmentingPaths = totalAugmentingPaths;
        }
    }

    public static FlowResult computeMaxFlow(int[][] capacity, int source, int sink) {
        int n = capacity.length;
        int[][] flow = new int[n][n];
        int maxFlow = 0;
        List<String> steps = new ArrayList<>();
        int pathCount = 0;

        while (true) {
            int[] parent = new int[n];
            Arrays.fill(parent, -1);
            parent[source] = source;

            Queue<Integer> queue = new LinkedList<>();
            queue.add(source);

            // BFS for shortest augmenting path
            while (!queue.isEmpty() && parent[sink] == -1) {
                int u = queue.poll();
                for (int v = 0; v < n; v++) {
                    int residual = capacity[u][v] - flow[u][v];
                    if (residual > 0 && parent[v] == -1) {
                        parent[v] = u;
                        queue.add(v);
                    }
                }
            }

            // No more augmenting path
            if (parent[sink] == -1) break;

            // Find bottleneck capacity along path
            int pathFlow = Integer.MAX_VALUE;
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, capacity[u][v] - flow[u][v]);
            }

            // Augment flow
            StringBuilder pathStr = new StringBuilder();
            List<Integer> pathNodes = new ArrayList<>();
            for (int v = sink; v != source; v = parent[v]) {
                pathNodes.add(v);
                int u = parent[v];
                flow[u][v] += pathFlow;
                flow[v][u] -= pathFlow;
            }
            pathNodes.add(source);
            Collections.reverse(pathNodes);

            for (int i = 0; i < pathNodes.size(); i++) {
                pathStr.append(pathNodes.get(i));
                if (i < pathNodes.size() - 1) pathStr.append(" -> ");
            }

            maxFlow += pathFlow;
            pathCount++;
            steps.add(String.format("Path #%d: [%s] augmented by %d units (Cumulative Flow = %d)",
                    pathCount, pathStr, pathFlow, maxFlow));
        }

        // Min-Cut computation: Vertices reachable from source in residual graph
        Set<Integer> sourceCutSet = new HashSet<>();
        Queue<Integer> residualQ = new LinkedList<>();
        boolean[] visited = new boolean[n];

        residualQ.add(source);
        visited[source] = true;
        sourceCutSet.add(source);

        while (!residualQ.isEmpty()) {
            int u = residualQ.poll();
            for (int v = 0; v < n; v++) {
                if (!visited[v] && (capacity[u][v] - flow[u][v] > 0)) {
                    visited[v] = true;
                    sourceCutSet.add(v);
                    residualQ.add(v);
                }
            }
        }

        return new FlowResult(maxFlow, flow, capacity, steps, sourceCutSet, pathCount);
    }
}
