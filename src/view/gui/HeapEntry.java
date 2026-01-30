package view.gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import model.values.IValue;

public class HeapEntry {
    private final SimpleIntegerProperty address;
    private final SimpleObjectProperty<IValue> value;

    public HeapEntry(int address, IValue value) {
        this.address = new SimpleIntegerProperty(address);
        this.value = new SimpleObjectProperty<>(value);
    }

    public int getAddress() {
        return address.get();
    }

    public SimpleIntegerProperty addressProperty() {
        return address;
    }

    public IValue getValue() {
        return value.get();
    }

    public SimpleObjectProperty<IValue> valueProperty() {
        return value;
    }
}
