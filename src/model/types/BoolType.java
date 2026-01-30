package model.types;

import com.sun.jdi.Value;
import model.values.BoolValue;
import model.values.IValue;

public class BoolType implements IType
{
    public boolean equals(Object another){
        if (another instanceof BoolType)
            return true;
        else
            return false;
    }

    public String toString() { return "bool";}

    @Override
    public IType deepCopy() {
        return new BoolType();
    }

    @Override
    public IValue defaultValue() {
        return new BoolValue(false);
    }
}
