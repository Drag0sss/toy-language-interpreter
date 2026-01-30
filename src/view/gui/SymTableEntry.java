package view.gui;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import model.values.IValue;

public class SymTableEntry {
    private final SimpleStringProperty varName;
    private final SimpleObjectProperty<IValue> value;

    public SymTableEntry(String varName, IValue value) {
        this.varName = new SimpleStringProperty(varName);
        this.value = new SimpleObjectProperty<>(value);
    }

    public String getVarName() {
        return varName.get();
    }

    public SimpleStringProperty varNameProperty() {
        return varName;
    }

    public IValue getValue() {
        return value.get();
    }

    public SimpleObjectProperty<IValue> valueProperty() {
        return value;
    }
}
