package com.placementhub.dsa.co6;

import java.util.Random;

/**
 * Course Outcome 6 (CO6): Randomised Algorithm - Las Vegas Type.
 * Implements Randomized QuickSelect to determine rank percentiles & CGPA cutoffs.
 * Las Vegas Property: Always produces the exact, correct result; running time is a random variable with E[T] = O(N).
 */
public class LasVegasQuickSelect {
    private static final Random rand = new Random();

    public static class SelectionResult {
        public final double value;
        public final int rankK;
        public final int comparisons;
        public final int partitionSteps;
        public final long executionTimeNanos;
        public final String propertyNote;

        public SelectionResult(double value, int rankK, int comparisons,
                               int partitionSteps, long executionTimeNanos, String propertyNote) {
            this.value = value;
            this.rankK = rankK;
            this.comparisons = comparisons;
            this.partitionSteps = partitionSteps;
            this.executionTimeNanos = executionTimeNanos;
            this.propertyNote = propertyNote;
        }
    }

    /**
     * Finds the k-th smallest element in array (0-indexed).
     */
    public static SelectionResult select(double[] originalArr, int k) {
        long t0 = System.nanoTime();
        if (originalArr == null || originalArr.length == 0 || k < 0 || k >= originalArr.length) {
            throw new IllegalArgumentException("Invalid array or rank k");
        }

        double[] arr = originalArr.clone();
        int[] metrics = new int[2]; // [comparisons, partitions]
        double val = quickSelect(arr, 0, arr.length - 1, k, metrics);
        long duration = System.nanoTime() - t0;

        String note = "Las Vegas Guarantee: Output is 100% exact. Expected time: O(N), worst-case: O(N²). " +
                "Random pivot selection avoids adversarial worst-case inputs.";

        return new SelectionResult(val, k, metrics[0], metrics[1], duration, note);
    }

    private static double quickSelect(double[] arr, int left, int right, int k, int[] metrics) {
        if (left == right) return arr[left];

        metrics[1]++; // partition step
        int pivotIndex = partition(arr, left, right, metrics);

        if (k == pivotIndex) {
            return arr[k];
        } else if (k < pivotIndex) {
            return quickSelect(arr, left, pivotIndex - 1, k, metrics);
        } else {
            return quickSelect(arr, pivotIndex + 1, right, k, metrics);
        }
    }

    private static int partition(double[] arr, int left, int right, int[] metrics) {
        // Las Vegas: Select uniformly random pivot
        int randomPivotIdx = left + rand.nextInt(right - left + 1);
        swap(arr, randomPivotIdx, right);
        double pivot = arr[right];

        int i = left;
        for (int j = left; j < right; j++) {
            metrics[0]++; // comparison
            if (arr[j] <= pivot) {
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, i, right);
        return i;
    }

    private static void swap(double[] arr, int i, int j) {
        double tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
