package model.statements;

import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.types.IType;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;

public class VarDeclStmt implements IStmt{

    private String name;
    private IType type;

    public VarDeclStmt(IType type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getExeStack();
        MyIDictionary<String, IValue> symTbl = state.getSymTable();

        if (symTbl.isDefined(name))
            throw new MyException("VarDeclStmt already exists: " + name);
        else {
            if (type instanceof IntType){
                symTbl.put(name, type.defaultValue());
            }
            else {
                symTbl.put(name, type.defaultValue());
            }
        }

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(type.deepCopy(), name);
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        typeEnv.put(name, type);
        return typeEnv;
    }

    public String toString() {
        return "var " + name + ": " + type.toString();
    }
}
