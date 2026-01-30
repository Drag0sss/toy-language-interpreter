package model.statements;

import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.expressions.IExp;
import model.types.BoolType;
import model.types.IType;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;

public class NewStmt implements IStmt {

    private String varName;
    private IExp expression;

    public NewStmt(String varName, IExp expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        MyIHeap<Integer, IValue> heap = state.getHeap();

        if (!symTable.isDefined(varName)) {
            throw new MyException("Variable " + varName + " is not defined");
        }

        IValue value = symTable.get(varName);

        if (!(value.getType() instanceof RefType)) {
            throw new MyException("Variable " + varName + " is not a ref");
        }

        IValue evExp = expression.eval(symTable, heap);
        IType locationType = ((RefType) value.getType()).getInner();

        if (!evExp.getType().equals(locationType)) {
            throw new MyException("Variable " + varName + " is not a location");
        }

        int newAddress = heap.allocate(evExp);

        symTable.put(varName, new RefValue(newAddress, locationType));

        return null;
     }

    @Override
    public IStmt deepCopy() {
        return new NewStmt(varName, expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typeVar = typeEnv.get(varName);
        IType typeExp = expression.typecheck(typeEnv);
        if (typeVar.equals(new RefType(typeExp)))
            return typeEnv;
        else
            throw new MyException("NEW stmt: right hand side and left hand side have different types ");
    }

    public String toString() {
        return "new(" + varName + ", " + expression + ")";
    }
}
