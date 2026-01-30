package model.values;

import model.types.IType;
import model.types.IntType;
import model.types.StringType;

public class StringValue implements IValue {
    public String value;
    public StringValue(String value) {
        this.value = value;
    }
    public String getValue(){
        return this.value;
    }

    public String toString(){
        return value;
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public IValue deepCopy() {
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof StringValue;
    }
}