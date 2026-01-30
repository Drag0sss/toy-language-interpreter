package model.expressions;

import exception.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.IType;
import model.values.IValue;

public class ValueExpression implements IExp {
    private IValue val;
    public ValueExpression(IValue val) {
        this.val = val;
    }

    public IValue eval(MyIDictionary<String,IValue> tbl, MyIHeap<Integer, IValue> heap) throws MyException {return val;}

    @Override
    public String toString() {
        return "ValueExpression{" +
                "val=" + val +
                '}';
    }

    @Override
    public IExp deepCopy() {
        return new ValueExpression(val.deepCopy());
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        return val.getType();
    }
}
