package view.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.statements.IStmt;

import java.io.IOException;
import java.util.List;

public class MainFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SelectProgram.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 800, 600);

        String css = getClass().getResource("style.css").toExternalForm();
        scene.getStylesheets().add(css);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setTitle("Select a Program");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
