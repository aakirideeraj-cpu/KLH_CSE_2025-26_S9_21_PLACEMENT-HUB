package com.placementhub.ui;

/**
 * Styling constants for Placement Hub UI.
 * Clean, standard, plain solid-color aesthetic suitable for a college software project.
 */
public class UITheme {
    public static final String BG_COLOR = "#f1f5f9";
    public static final String CARD_BG = "#ffffff";
    public static final String BORDER_COLOR = "#cbd5e1";
    public static final String PRIMARY_BLUE = "#2563eb";
    public static final String SUCCESS_GREEN = "#16a34a";
    public static final String SLATE_GRAY = "#475569";
    public static final String DANGER_RED = "#dc2626";
    public static final String TEXT_MAIN = "#0f172a";
    public static final String TEXT_MUTED = "#64748b";

    public static String getButtonStyle(String bgColor) {
        return String.format("-fx-background-color: %s; -fx-text-fill: #ffffff; -fx-font-weight: bold; " +
                "-fx-padding: 7 14; -fx-background-radius: 4; -fx-cursor: hand;", bgColor);
    }

    public static String getCardStyle() {
        return String.format("-fx-background-color: %s; -fx-background-radius: 6; -fx-padding: 14; " +
                "-fx-border-color: %s; -fx-border-width: 1px; -fx-border-radius: 6;", CARD_BG, BORDER_COLOR);
    }

    public static String getHeaderStyle() {
        return "-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_MAIN + ";";
    }

    public static String getSubheaderStyle() {
        return "-fx-font-size: 12px; -fx-text-fill: " + TEXT_MUTED + ";";
    }
}
