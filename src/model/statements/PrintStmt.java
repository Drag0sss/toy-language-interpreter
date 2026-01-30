package model.statements;

import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.adt.MyIList;
import model.expressions.IExp;
import model.types.IType;
import model.values.IValue;

public class PrintStmt implements IStmt {
    private IExp expression;

    public PrintStmt(IExp exp) {
        this.expression = exp;
    }

    public String toString() {
        return "print(" + expression.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> symTbl = state.getSymTable();
        MyIHeap<Integer, IValue> heap = state.getHeap();

        MyIList<IValue> out = state.getOut();
        IValue value = expression.eval(symTbl, heap);

        out.add(value);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        expression.typecheck(typeEnv);
        return typeEnv;

    }

}
