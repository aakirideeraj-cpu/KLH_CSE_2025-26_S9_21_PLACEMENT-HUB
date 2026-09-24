package com.placementhub.model;

/**
 * Encapsulates the algorithmic problem signature according to Course Outcome 1 (CO1).
 * Bloom's Taxonomy Level: BTL 5 (Evaluate).
 * Problem signatures include: Substring search, sequence alignment, network flow, NP-hard scheduling, parallel scan/reduce.
 */
public class ProblemSignature {
    public enum ProblemClass {
        SUBSTRING_SEARCH("Linear Pattern Matching / Substring Search", "CO2"),
        COMBINATORIAL_OPTIMIZATION("Dynamic Programming on Subsets / Bitmask DP", "CO3"),
        NETWORK_FLOW("Capacity-Constrained Assignment / Max-Flow Min-Cut", "CO4"),
        NP_HARD_SCHEDULING("NP-Hard Interview Conflict Resolution / Approximation", "CO5"),
        PARALLEL_BATCH_PROCESSING("Parallel Algorithm Primitives / Work-Span Analysis", "CO6");

        private final String displayName;
        private final String mappedCO;

        ProblemClass(String displayName, String mappedCO) {
            this.displayName = displayName;
            this.mappedCO = mappedCO;
        }

        public String getDisplayName() { return displayName; }
        public String getMappedCO() { return mappedCO; }
    }

    private String problemTitle;
    private ProblemClass problemClass;
    private String inputCharacteristics;
    private String mathematicalStructure;
    private String recommendedStrategy;
    private String worstCaseTimeComplexity;
    private String spaceComplexity;
    private String optimalityGuarantee;
    private String evaluationRationale;

    public ProblemSignature(String problemTitle, ProblemClass problemClass,
                            String inputCharacteristics, String mathematicalStructure,
                            String recommendedStrategy, String worstCaseTimeComplexity,
                            String spaceComplexity, String optimalityGuarantee,
                            String evaluationRationale) {
        this.problemTitle = problemTitle;
        this.problemClass = problemClass;
        this.inputCharacteristics = inputCharacteristics;
        this.mathematicalStructure = mathematicalStructure;
        this.recommendedStrategy = recommendedStrategy;
        this.worstCaseTimeComplexity = worstCaseTimeComplexity;
        this.spaceComplexity = spaceComplexity;
        this.optimalityGuarantee = optimalityGuarantee;
        this.evaluationRationale = evaluationRationale;
    }

    public String getProblemTitle() { return problemTitle; }
    public ProblemClass getProblemClass() { return problemClass; }
    public String getInputCharacteristics() { return inputCharacteristics; }
    public String getMathematicalStructure() { return mathematicalStructure; }
    public String getRecommendedStrategy() { return recommendedStrategy; }
    public String getWorstCaseTimeComplexity() { return worstCaseTimeComplexity; }
    public String getSpaceComplexity() { return spaceComplexity; }
    public String getOptimalityGuarantee() { return optimalityGuarantee; }
    public String getEvaluationRationale() { return evaluationRationale; }
}
