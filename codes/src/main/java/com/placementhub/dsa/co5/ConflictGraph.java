package com.placementhub.dsa.co5;

import java.util.*;

/**
 * Course Outcome 5 (CO5): Graph representation for interview clashes.
 * Models conflicting interview slots between overlapping recruitment drives.
 */
public class ConflictGraph {
    public static class InterviewSlot {
        public final int id;
        public final String name;
        public final String company;
        public final String timeRange;

        public InterviewSlot(int id, String name, String company, String timeRange) {
            this.id = id;
            this.name = name;
            this.company = company;
            this.timeRange = timeRange;
        }

        @Override
        public String toString() {
            return String.format("[%d] %s (%s, %s)", id, name, company, timeRange);
        }
    }

    public static class Edge {
        public final int u;
        public final int v;

        public Edge(int u, int v) {
            this.u = Math.min(u, v);
            this.v = Math.max(u, v);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Edge edge)) return false;
            return u == edge.u && v == edge.v;
        }

        @Override
        public int hashCode() {
            return Objects.hash(u, v);
        }
    }

    private final Map<Integer, InterviewSlot> nodes = new HashMap<>();
    private final Set<Edge> edges = new HashSet<>();
    private final Map<Integer, Set<Integer>> adj = new HashMap<>();

    public void addSlot(InterviewSlot slot) {
        nodes.put(slot.id, slot);
        adj.putIfAbsent(slot.id, new HashSet<>());
    }

    public void addConflict(int u, int v) {
        if (u == v || !nodes.containsKey(u) || !nodes.containsKey(v)) return;
        Edge edge = new Edge(u, v);
        edges.add(edge);
        adj.get(u).add(v);
        adj.get(v).add(u);
    }

    public Map<Integer, InterviewSlot> getNodes() { return nodes; }
    public Set<Edge> getEdges() { return edges; }
    public Map<Integer, Set<Integer>> getAdj() { return adj; }
    public int getNodeCount() { return nodes.size(); }
    public int getEdgeCount() { return edges.size(); }

    public static ConflictGraph createSamplePlacementConflictGraph() {
        ConflictGraph g = new ConflictGraph();
        g.addSlot(new InterviewSlot(0, "Google Round 1 (Slot A)", "Google", "09:00 - 10:30"));
        g.addSlot(new InterviewSlot(1, "Microsoft Coding (Slot A)", "Microsoft", "09:30 - 11:00"));
        g.addSlot(new InterviewSlot(2, "Amazon Tech Discussion", "Amazon", "10:00 - 11:30"));
        g.addSlot(new InterviewSlot(3, "Cisco Systems Round", "Cisco", "11:00 - 12:30"));
        g.addSlot(new InterviewSlot(4, "TCS Digital Assessment", "TCS", "11:30 - 13:00"));
        g.addSlot(new InterviewSlot(5, "Infosys Specialist Interview", "Infosys", "12:00 - 13:30"));
        g.addSlot(new InterviewSlot(6, "Google System Design (Slot B)", "Google", "14:00 - 15:30"));
        g.addSlot(new InterviewSlot(7, "Microsoft Tech 2 (Slot B)", "Microsoft", "14:30 - 16:00"));

        // Inter-drive schedule clashes
        g.addConflict(0, 1); // Google Slot A overlaps Microsoft Slot A
        g.addConflict(1, 2); // Microsoft Slot A overlaps Amazon Tech
        g.addConflict(2, 3); // Amazon overlaps Cisco
        g.addConflict(3, 4); // Cisco overlaps TCS
        g.addConflict(4, 5); // TCS overlaps Infosys
        g.addConflict(0, 2); // Google Slot A overlaps Amazon Tech
        g.addConflict(6, 7); // Google Slot B overlaps Microsoft Slot B

        return g;
    }
}
