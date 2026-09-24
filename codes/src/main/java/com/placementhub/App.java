package com.placementhub;

import com.placementhub.db.PlacementDAO;
import com.placementhub.ui.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX Application entry point for Placement Hub.
 * Clean, simple desktop application.
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            System.out.println("[App] Step 1: Initializing PlacementDAO...");
            PlacementDAO dao = new PlacementDAO();

            System.out.println("[App] Step 2: Creating MainView...");
            MainView mainView = new MainView(dao);

            System.out.println("[App] Step 3: Building Scene...");
            Scene scene = new Scene(mainView.createRootPane(), 1100, 720);

            try {
                String cssPath = getClass().getResource("/css/style.css") != null ?
                        getClass().getResource("/css/style.css").toExternalForm() : null;
                if (cssPath != null) {
                    scene.getStylesheets().add(cssPath);
                }
            } catch (Exception e) {
                System.err.println("[App] Note: Using default stylesheet: " + e.getMessage());
            }

            System.out.println("[App] Step 4: Configuring Stage...");
            primaryStage.setTitle("Placement Hub - Campus Recruitment System");
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(950);
            primaryStage.setMinHeight(600);

            System.out.println("[App] Step 5: Showing Stage...");
            primaryStage.show();
            System.out.println("[App] Step 6: Stage is now visible on desktop!");
        } catch (Throwable t) {
            System.err.println("[App] CRITICAL ERROR IN START: " + t.getMessage());
            t.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
