package model.types;
import model.values.IValue;
import model.values.RefValue;

public class RefType implements IType{
    IType inner;
    public RefType(IType inner) {this.inner=inner;}
    public IType getInner() {return inner;}
    public boolean equals(Object another){
        if (another instanceof RefType) {
            RefType anotherRef = (RefType) another;
            return inner.equals(anotherRef.getInner());
        }
        else
            return false;
    }
    public String toString() { return "Ref(" +inner.toString()+")";}

    @Override
    public IType deepCopy() {
        return new  RefType(this);
    }

    public IValue defaultValue() { return new RefValue(0,inner);}
}