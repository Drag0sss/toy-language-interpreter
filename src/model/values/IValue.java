package model.values;

import model.types.IType;

public interface IValue {
    IType getType();

    public IValue deepCopy();

    public boolean equals(Object obj);
}

