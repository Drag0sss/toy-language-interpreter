package model.statements;

import exception.MyException;
import model.PrgState;
import model.adt.*;
import model.types.IType;
import model.values.IValue;

public class ForkStmt implements IStmt{

    private IStmt stmt;

    public ForkStmt(IStmt stmt){
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> newStack = new MyStack<>();
        MyIDictionary<String, IValue> newSymTable = state.getSymTable().deepCopy();

        return new PrgState(
                state.getFileTable(),
                newStack,
                newSymTable,
                state.getOut(),
                state.getHeap(),
                stmt.deepCopy()   // !!! obligatoriu
        );
    }


    @Override
    public IStmt deepCopy() {
        return new ForkStmt(stmt.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        stmt.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }

    public String toString() {
        return stmt.toString();
    }
}
