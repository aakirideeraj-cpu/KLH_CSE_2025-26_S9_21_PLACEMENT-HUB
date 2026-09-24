package com.placementhub.dsa.core;

import com.placementhub.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Custom Max-Heap Priority Queue implementation for student ranking.
 * Fulfills PPT expected outcome of Priority Queue usage.
 * Ranks candidates by calculated eligibility & aptitude score in O(log N) push/pop.
 */
public class StudentPriorityQueue {
    public static class ScoredStudent {
        public final Student student;
        public final double score;

        public ScoredStudent(Student student, double score) {
            this.student = student;
            this.score = score;
        }
    }

    private final List<ScoredStudent> heap;
    private final Set<String> targetSkills;

    public StudentPriorityQueue(Set<String> targetSkills) {
        this.heap = new ArrayList<>();
        this.targetSkills = targetSkills;
    }

    public void insert(Student student) {
        if (student == null) return;
        double score = student.calculateRankingScore(targetSkills);
        ScoredStudent item = new ScoredStudent(student, score);
        heap.add(item);
        siftUp(heap.size() - 1);
    }

    public ScoredStudent extractMax() {
        if (heap.isEmpty()) return null;
        ScoredStudent max = heap.get(0);
        ScoredStudent last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            siftDown(0);
        }
        return max;
    }

    public ScoredStudent peek() {
        return heap.isEmpty() ? null : heap.get(0);
    }

    public int size() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    /**
     * Extracts top K ranked students.
     */
    public List<ScoredStudent> extractTopK(int k) {
        List<ScoredStudent> result = new ArrayList<>();
        int count = Math.min(k, size());
        for (int i = 0; i < count; i++) {
            result.add(extractMax());
        }
        return result;
    }

    private void siftUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (heap.get(index).score > heap.get(parent).score) {
                swap(index, parent);
                index = parent;
            } else {
                break;
            }
        }
    }

    private void siftDown(int index) {
        int half = heap.size() / 2;
        while (index < half) {
            int left = 2 * index + 1;
            int right = left + 1;
            int best = left;

            if (right < heap.size() && heap.get(right).score > heap.get(left).score) {
                best = right;
            }

            if (heap.get(best).score > heap.get(index).score) {
                swap(index, best);
                index = best;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        ScoredStudent tmp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, tmp);
    }
}
