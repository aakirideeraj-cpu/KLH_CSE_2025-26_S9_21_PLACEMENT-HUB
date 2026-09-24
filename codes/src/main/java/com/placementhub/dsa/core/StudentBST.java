package com.placementhub.dsa.core;

import com.placementhub.model.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom Binary Search Tree (BST) indexed by Student CGPA.
 * Demonstrates real use of BST as outlined in PPT expected outcomes.
 * Supports:
 * - Insert: O(log N) average
 * - Range Query [minCgpa, maxCgpa]: O(log N + K)
 * - In-Order Traversal: O(N) sorted order
 */
public class StudentBST {
    public static class BSTNode {
        public Student student;
        public BSTNode left;
        public BSTNode right;

        public BSTNode(Student student) {
            this.student = student;
        }
    }

    private BSTNode root;
    private int size;

    public StudentBST() {
        this.root = null;
        this.size = 0;
    }

    public void insert(Student student) {
        if (student == null) return;
        root = insertRec(root, student);
        size++;
    }

    private BSTNode insertRec(BSTNode current, Student student) {
        if (current == null) {
            return new BSTNode(student);
        }
        if (student.getCgpa() <= current.student.getCgpa()) {
            current.left = insertRec(current.left, student);
        } else {
            current.right = insertRec(current.right, student);
        }
        return current;
    }

    /**
     * Finds all students within a CGPA range [minCgpa, maxCgpa] in O(log N + K) time.
     */
    public List<Student> searchRange(double minCgpa, double maxCgpa) {
        List<Student> result = new ArrayList<>();
        searchRangeRec(root, minCgpa, maxCgpa, result);
        return result;
    }

    private void searchRangeRec(BSTNode current, double minCgpa, double maxCgpa, List<Student> result) {
        if (current == null) return;

        // If current CGPA is greater than minCgpa, there could be elements in left subtree
        if (current.student.getCgpa() > minCgpa) {
            searchRangeRec(current.left, minCgpa, maxCgpa, result);
        }

        // If current CGPA falls in range, add it
        if (current.student.getCgpa() >= minCgpa && current.student.getCgpa() <= maxCgpa) {
            result.add(current.student);
        }

        // If current CGPA is less than maxCgpa, there could be elements in right subtree
        if (current.student.getCgpa() < maxCgpa) {
            searchRangeRec(current.right, minCgpa, maxCgpa, result);
        }
    }

    /**
     * In-Order Traversal returning students sorted by CGPA ascending.
     */
    public List<Student> getInOrderTraversal() {
        List<Student> result = new ArrayList<>();
        inOrderRec(root, result);
        return result;
    }

    private void inOrderRec(BSTNode node, List<Student> result) {
        if (node == null) return;
        inOrderRec(node.left, result);
        result.add(node.student);
        inOrderRec(node.right, result);
    }

    public int getSize() {
        return size;
    }

    public void clear() {
        root = null;
        size = 0;
    }
}
