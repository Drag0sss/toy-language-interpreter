package model.expressions;

import com.sun.jdi.Value;
import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.expressions.IExp;
import model.types.IType;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;

public class ReadHExp implements IExp {

    private IExp expression;

    public ReadHExp(IExp expression) {
        this.expression = expression;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> table, MyIHeap<Integer, IValue> heap) throws MyException {
        IValue value = expression.eval(table, heap);
        if (!(value instanceof RefValue)) {
            throw new MyException("rH argument is not RefValue!");
        }
        int address = ((RefValue) value).getAddr();
        if (!heap.containsKey(address))
            throw new MyException("Invalid heap address: " + address);

        return heap.get(address);

    }

    @Override
    public IExp deepCopy() {
        return new ReadHExp(expression.deepCopy());
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typ = expression.typecheck(typeEnv);
        if (typ instanceof RefType) {
            RefType rType = (RefType) typ;
            return rType.getInner();
        } else
            throw new MyException("the rH argument is not a Ref Type");
    }

    public String toString() {
        return "rH(" + expression + ")";
    }
}
