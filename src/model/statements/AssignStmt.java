package model.statements;

import com.sun.jdi.Value;
import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.adt.MyIStack;
import model.expressions.IExp;
import model.types.IType;
import model.values.IValue;

public class AssignStmt implements IStmt{

    private String id;
    private IExp expression;

    public AssignStmt(String id, IExp expression) {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getExeStack();
        MyIDictionary<String, IValue> symTbl = state.getSymTable();
        MyIHeap<Integer, IValue> heap = state.getHeap();

        if (symTbl.isDefined(id)) {
            IValue value = expression.eval(symTbl, heap);
            IType type = symTbl.get(id).getType();
            if (value.getType().equals(type)) {
                symTbl.put(id, value);
            }
            else {
                throw new MyException("AssignStmt error");
            }
        }

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new AssignStmt(this.id, this.expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typeVar = typeEnv.get(id);
        IType typeExp = expression.typecheck(typeEnv);
        if (typeVar.equals(typeExp))
            return typeEnv;
        else
            throw new MyException("Assignment: right hand side and left hand side have different types ");
    }

    public String toString() {
        return id + " = " + expression.toString();
    }
}
