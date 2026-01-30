package view.gui;

import controller.Controller;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.PrgState;
import model.adt.MyIStack;
import model.statements.IStmt;
import model.values.IValue;
import model.values.StringValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainWindowController {

    private Controller controller;

    @FXML
    private TextField nrPrgStatesField;

    @FXML
    private TableView<HeapEntry> heapTable;
    @FXML
    private TableColumn<HeapEntry, Integer> heapAddressColumn;
    @FXML
    private TableColumn<HeapEntry, IValue> heapValueColumn;

    @FXML
    private ListView<IValue> outListView;

    @FXML
    private ListView<String> fileTableView;

    @FXML
    private ListView<Integer> prgStateIdsListView;

    @FXML
    private TableView<SymTableEntry> symTable;
    @FXML
    private TableColumn<SymTableEntry, String> symVarColumn;
    @FXML
    private TableColumn<SymTableEntry, IValue> symValueColumn;

    @FXML
    private ListView<String> exeStackListView;

    @FXML
    private Button runOneStepButton;

    @FXML
    public void initialize() {
        heapAddressColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getAddress()));
        heapValueColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getValue()));

        symVarColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getVarName()));
        symValueColumn.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getValue()));

        prgStateIdsListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> updatePrgStateDetails(newValue)
        );
    }

    public void setController(Controller controller) {
        this.controller = controller;
        updateUI();
    }

    private void updateUI() {
        if (controller == null) return;

        List<PrgState> prgStates = controller.getPrgList();

        nrPrgStatesField.setText(String.valueOf(prgStates.size()));

        Integer selectedId = prgStateIdsListView.getSelectionModel().getSelectedItem();
        ObservableList<Integer> ids = FXCollections.observableArrayList(
                prgStates.stream().map(PrgState::getId).collect(Collectors.toList())
        );
        prgStateIdsListView.setItems(ids);

        if (!prgStates.isEmpty()) {
            PrgState firstPrg = prgStates.get(0);
            updateHeap(firstPrg);
            updateOut(firstPrg);
            updateFileTable(firstPrg);

            if (selectedId != null && ids.contains(selectedId)) {
                prgStateIdsListView.getSelectionModel().select(selectedId);
            } else {
                prgStateIdsListView.getSelectionModel().select(0);
            }
        } else {
            exeStackListView.getItems().clear();
            symTable.getItems().clear();
        }
    }

    private void updatePrgStateDetails(Integer prgId) {
        if (prgId == null || controller == null) return;

        PrgState prg = controller.getPrgList().stream()
                .filter(p -> p.getId() == prgId)
                .findFirst()
                .orElse(null);

        if (prg != null) {
            updateSymTable(prg);
            updateExeStack(prg);
        }
    }

    private void updateHeap(PrgState prg) {
        ObservableList<HeapEntry> heapData = FXCollections.observableArrayList();
        for (Map.Entry<Integer, IValue> entry : prg.getHeap().getContent().entrySet()) {
            heapData.add(new HeapEntry(entry.getKey(), entry.getValue()));
        }
        heapTable.setItems(heapData);
        heapTable.refresh();
    }

    private void updateOut(PrgState prg) {
        outListView.setItems(FXCollections.observableArrayList(prg.getOut().getList()));
    }

    private void updateFileTable(PrgState prg) {
        List<String> files = prg.getFileTable().getAllEntries().keySet().stream()
                .map(StringValue::toString)
                .collect(Collectors.toList());
        fileTableView.setItems(FXCollections.observableArrayList(files));
    }

    private void updateSymTable(PrgState prg) {
        ObservableList<SymTableEntry> symData = FXCollections.observableArrayList();
        for (Map.Entry<String, IValue> entry : prg.getSymTable().getAllEntries().entrySet()) {
            symData.add(new SymTableEntry(entry.getKey(), entry.getValue()));
        }
        symTable.setItems(symData);
        symTable.refresh();
    }

    private void updateExeStack(PrgState prg) {
        List<IStmt> stackList = new ArrayList<>(prg.getExeStack().getStackAsList());
        Collections.reverse(stackList);

        ObservableList<String> exeData = FXCollections.observableArrayList(
                stackList.stream().map(Object::toString).collect(Collectors.toList())
        );
        exeStackListView.setItems(exeData);
    }

    @FXML
    private void handleRunOneStep() {
        try {
            List<PrgState> prgList = controller.removeCompletedPrg(controller.getPrgList());
            if (prgList.isEmpty()) {
                showError("Program finished.");
                return;
            }

            controller.executeGarbageCollector(prgList);
            controller.oneStepForAllPrg(prgList);

            updateUI();
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }
}