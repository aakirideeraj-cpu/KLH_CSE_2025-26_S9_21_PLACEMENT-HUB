package com.placementhub.dsa.co2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Course Outcome 2 (CO2): Rabin-Karp Algorithm with Polynomial Rolling Hash.
 * Features:
 * - O(N + M) expected pattern search
 * - O(1) rolling hash update
 * - Multi-keyword scanning across candidate resumes
 * - Resume plagiarism / similarity hash calculation
 */
public class RabinKarpMatcher {
    private static final long BASE = 256;
    private static final long PRIME = 1000000007L;

    public static class MatchLocation {
        public final String pattern;
        public final int index;

        public MatchLocation(String pattern, int index) {
            this.pattern = pattern;
            this.index = index;
        }
    }

    /**
     * Searches for a single pattern using Rabin-Karp rolling hash.
     */
    public static List<Integer> search(String text, String pattern) {
        List<Integer> matches = new ArrayList<>();
        if (text == null || pattern == null || text.length() < pattern.length() || pattern.isEmpty()) {
            return matches;
        }

        int n = text.length();
        int m = pattern.length();

        // Calculate (BASE^(m-1)) % PRIME
        long h = 1;
        for (int i = 0; i < m - 1; i++) {
            h = (h * BASE) % PRIME;
        }

        long patternHash = 0;
        long windowHash = 0;

        for (int i = 0; i < m; i++) {
            patternHash = (BASE * patternHash + pattern.charAt(i)) % PRIME;
            windowHash = (BASE * windowHash + text.charAt(i)) % PRIME;
        }

        for (int i = 0; i <= n - m; i++) {
            if (patternHash == windowHash) {
                // Verify characters to guard against hash collisions
                boolean match = true;
                for (int j = 0; j < m; j++) {
                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    matches.add(i);
                }
            }

            // Roll hash to next window
            if (i < n - m) {
                windowHash = (BASE * (windowHash - text.charAt(i) * h) + text.charAt(i + m)) % PRIME;
                if (windowHash < 0) {
                    windowHash = (windowHash + PRIME);
                }
            }
        }

        return matches;
    }

    /**
     * Multi-pattern search: Scans resume text for multiple required skills simultaneously.
     */
    public static Map<String, List<Integer>> multiSearch(String text, List<String> patterns) {
        Map<String, List<Integer>> result = new HashMap<>();
        if (text == null || patterns == null) return result;

        for (String pattern : patterns) {
            if (pattern != null && !pattern.trim().isEmpty()) {
                List<Integer> occurrences = search(text.toLowerCase(), pattern.toLowerCase().trim());
                result.put(pattern, occurrences);
            }
        }
        return result;
    }

    /**
     * Computes similarity between two resume texts using rolling hash n-gram shingles (k-shingles).
     */
    public static double computeSimilarity(String doc1, String doc2, int k) {
        if (doc1 == null || doc2 == null || doc1.isEmpty() || doc2.isEmpty()) return 0.0;
        if (doc1.length() < k || doc2.length() < k) return doc1.equalsIgnoreCase(doc2) ? 1.0 : 0.0;

        java.util.Set<Long> set1 = getShingleHashes(doc1, k);
        java.util.Set<Long> set2 = getShingleHashes(doc2, k);

        int intersection = 0;
        for (Long hash : set1) {
            if (set2.contains(hash)) intersection++;
        }

        int union = set1.size() + set2.size() - intersection;
        return union == 0 ? 0.0 : (double) intersection / union;
    }

    private static java.util.Set<Long> getShingleHashes(String text, int k) {
        java.util.Set<Long> set = new java.util.HashSet<>();
        long h = 1;
        for (int i = 0; i < k - 1; i++) {
            h = (h * BASE) % PRIME;
        }

        long rolling = 0;
        for (int i = 0; i < k; i++) {
            rolling = (BASE * rolling + text.charAt(i)) % PRIME;
        }
        set.add(rolling);

        for (int i = 0; i < text.length() - k; i++) {
            rolling = (BASE * (rolling - text.charAt(i) * h) + text.charAt(i + k)) % PRIME;
            if (rolling < 0) rolling += PRIME;
            set.add(rolling);
        }
        return set;
    }
}
