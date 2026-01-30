package view.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import controller.Controller;
import model.PrgState;
import model.adt.*;
import model.statements.IStmt;
import repository.IRepository;
import repository.Repository;

import java.util.List;

public class SelectProgramController {

    @FXML
    private ListView<IStmt> programList;

    @FXML
    private Button selectButton;

    private List<IStmt> allExampleStatements;

    public void setExampleStatements(List<IStmt> examples) {
        this.allExampleStatements = examples;
        programList.getItems().addAll(allExampleStatements);
    }

    @FXML
    private void handleSelect() {
        IStmt selected = programList.getSelectionModel().getSelectedItem();
        if(selected != null) {

            try {

                selected.typecheck(new MyDictionary<>());

                PrgState prg = new PrgState(new MyDictionary<>(), new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyHeap(), selected);

                IRepository repo = new Repository("log_" + System.currentTimeMillis() + ".txt");

                repo.addPrgState(prg);

                Controller controller = new Controller(repo);


                FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
                Parent root = loader.load();

                MainWindowController mainCtrl = loader.getController();
                mainCtrl.setController(controller);

                Stage stage = new Stage();
                stage.setTitle("Toy Language Interpreter");

                Scene scene = new Scene(root, 900, 600);

                String css = getClass().getResource("style.css").toExternalForm();
                scene.getStylesheets().add(css);

                stage.setScene(scene);
                stage.show();

                Stage current = (Stage) programList.getScene().getWindow();
                current.close();
            } catch(Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Type Checking Error");
                alert.setHeaderText("Eroare de validare a programului!");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void initialize() {
        List<IStmt> examples = view.gui.ProgramExamples.getAll();
        programList.getItems().addAll(examples);
    }
}
