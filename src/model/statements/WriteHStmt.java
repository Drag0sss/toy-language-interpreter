package model.statements;

import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.expressions.IExp;
import model.types.IType;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;

public class WriteHStmt implements IStmt {

    private String varName;
    private IExp expression;

    public WriteHStmt(String varName, IExp expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> symTbl = state.getSymTable();
        MyIHeap<Integer, IValue> heap = state.getHeap();

        if (!symTbl.isDefined(varName)) {
            throw new MyException("Variable name not found");
        }

        IValue value = symTbl.get(varName);
        if (!(value instanceof RefValue)) {
            throw new MyException("Value is not a ref value");
        }

        int address = ((RefValue) value).getAddr();

        if (!heap.containsKey(address)) {
            throw new MyException("Address not found");
        }

//        IValue expEval = expression.eval(symTbl, heap);
//        IType locationType = value.getType();

        RefValue refVal = (RefValue) value;
        IValue expEval = expression.eval(symTbl, heap);

        IType locationType = refVal.getLocationType();

        if (!expEval.getType().equals(locationType)) {
            throw new MyException("Type mismatch");
        }

        heap.put(refVal.getAddr(), expEval);
        return null;

    }

    @Override
    public IStmt deepCopy() {
        return new WriteHStmt(varName, expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        if (!typeEnv.isDefined(varName)) {
            throw new MyException("WriteH: variable " + varName + " is not defined");
        }

        IType typeVar = typeEnv.get(varName);
        if (!(typeVar instanceof model.types.RefType)) {
            throw new MyException("WriteH: variable " + varName + " is not of RefType");
        }

        IType typeExp = expression.typecheck(typeEnv);

        RefType refType = (model.types.RefType) typeVar;
        if (!refType.getInner().equals(typeExp)) {
            throw new MyException("WriteH: right hand side and left hand side have different types");
        }

        return typeEnv;
    }

    public String toString() {
        return "wH(" + varName + ", " + expression + ")";
    }
}
