package com.placementhub.dsa.co3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Course Outcome 3 (CO3): Advanced Dynamic Programming - Bitmask DP.
 * Solves the Minimum Cost Skill-Set Coverage problem for interview panels / finalist candidate teams.
 * Given K required competencies (K <= 16) and M candidates/interviewers with skill subsets and costs,
 * computes the exact optimal team covering all 2^K - 1 skills in O(M · 2^K) polynomial time.
 */
public class BitmaskSkillCoverDP {

    public static class CandidateProfile {
        public final int id;
        public final String name;
        public final List<String> skills;
        public final int cost; // e.g. interview hours, hiring budget, or panel slot cost

        public CandidateProfile(int id, String name, List<String> skills, int cost) {
            this.id = id;
            this.name = name;
            this.skills = skills;
            this.cost = cost;
        }
    }

    public static class Solution {
        public final int totalCost;
        public final List<CandidateProfile> selectedCandidates;
        public final int statesEvaluated;
        public final long executionTimeNanos;
        public final String complexityAnalysis;

        public Solution(int totalCost, List<CandidateProfile> selectedCandidates,
                        int statesEvaluated, long executionTimeNanos, String complexityAnalysis) {
            this.totalCost = totalCost;
            this.selectedCandidates = selectedCandidates;
            this.statesEvaluated = statesEvaluated;
            this.executionTimeNanos = executionTimeNanos;
            this.complexityAnalysis = complexityAnalysis;
        }
    }

    public static Solution solve(List<String> requiredSkills, List<CandidateProfile> candidates) {
        long start = System.nanoTime();
        int k = requiredSkills.size();
        if (k > 20) {
            throw new IllegalArgumentException("Bitmask DP supports up to 20 skills (2^20 states). Received: " + k);
        }

        int targetMask = (1 << k) - 1;
        int numStates = 1 << k;

        // Convert candidate skills to bitmasks
        int m = candidates.size();
        int[] candMasks = new int[m];
        int[] candCosts = new int[m];

        for (int i = 0; i < m; i++) {
            CandidateProfile c = candidates.get(i);
            int mask = 0;
            for (int bit = 0; bit < k; bit++) {
                String req = requiredSkills.get(bit).toLowerCase().trim();
                for (String s : c.skills) {
                    if (s.equalsIgnoreCase(req)) {
                        mask |= (1 << bit);
                        break;
                    }
                }
            }
            candMasks[i] = mask;
            candCosts[i] = c.cost;
        }

        // dp[mask] = min cost to achieve the covered skill set represented by mask
        int[] dp = new int[numStates];
        int[] parentMask = new int[numStates];
        int[] parentCand = new int[numStates];

        Arrays.fill(dp, Integer.MAX_VALUE / 2);
        Arrays.fill(parentMask, -1);
        Arrays.fill(parentCand, -1);

        dp[0] = 0;

        int statesEvaluated = 0;

        // Dynamic Programming transitions
        for (int i = 0; i < m; i++) {
            int cMask = candMasks[i];
            int cost = candCosts[i];

            for (int state = numStates - 1; state >= 0; state--) {
                statesEvaluated++;
                if (dp[state] == Integer.MAX_VALUE / 2) continue;

                int nextState = state | cMask;
                if (dp[state] + cost < dp[nextState]) {
                    dp[nextState] = dp[state] + cost;
                    parentMask[nextState] = state;
                    parentCand[nextState] = i;
                }
            }
        }

        // Reconstruct solution
        List<CandidateProfile> selected = new ArrayList<>();
        int curr = targetMask;

        if (dp[curr] < Integer.MAX_VALUE / 2) {
            while (curr > 0 && parentCand[curr] != -1) {
                int candIdx = parentCand[curr];
                selected.add(candidates.get(candIdx));
                curr = parentMask[curr];
            }
        }

        long timeTaken = System.nanoTime() - start;
        String complexity = String.format("Time: O(M · 2^K) = O(%d · 2^%d) = %d ops | Space: O(2^K) = %d integers",
                m, k, m * numStates, numStates);

        return new Solution(
                dp[targetMask] >= Integer.MAX_VALUE / 2 ? -1 : dp[targetMask],
                selected,
                statesEvaluated,
                timeTaken,
                complexity
        );
    }
}
