package model.values;
import model.types.IType;
import model.types.RefType;

public class RefValue implements IValue {

    private int address;
    private IType locationType;

    public RefValue(int address, IType locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public IType getLocationType() {
        return locationType;
    }

    public int getAddr() {return address;}

    @Override
    public boolean equals(Object obj) {
        return obj instanceof RefValue;
    }

    @Override
    public IType getType() {
        return new RefType(locationType);
    }

    @Override
    public IValue deepCopy() {
        return null;
    }

    public String toString()
    {
        return "Location: " + locationType.toString() + ",  Address: " + address + "; ";
    }


}
