package model.types;

import model.values.IValue;

public interface IType {

    public IType deepCopy();

    public IValue defaultValue();
}

