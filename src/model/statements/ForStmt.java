package model.statements;

import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.expressions.IExp;
import model.expressions.RelationalExpression;
import model.expressions.RelationalOperation;
import model.types.IType;
import model.types.IntType;

public class ForStmt implements IStmt {
    private final String var;
    private final IExp exp1;
    private final IExp exp2;
    private final IExp exp3;
    private final IStmt stmt;

    public ForStmt(String var, IExp e1, IExp e2, IExp e3, IStmt s) {
        this.var = var;
        this.exp1 = e1;
        this.exp2 = e2;
        this.exp3 = e3;
        this.stmt = s;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IStmt newStmt = new CompStmt(
                new AssignStmt(var, exp1),
                new WhileStmt(
                        new RelationalExpression(new model.expressions.VariableExpression(var), exp2, RelationalOperation.LESS_THAN),
                        new CompStmt(stmt, new AssignStmt(var, exp3))
                )
        );

        state.getExeStack().push(newStmt);
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType t1 = exp1.typecheck(typeEnv);
        IType t2 = exp2.typecheck(typeEnv);
        IType t3 = exp3.typecheck(typeEnv);

        if (t1.equals(new IntType()) && t2.equals(new IntType()) && t3.equals(new IntType())) {
            return typeEnv;
        } else {
            throw new MyException("For error: expressions must be of type int.");
        }
    }

    @Override
    public IStmt deepCopy() {
        return new ForStmt(var, exp1.deepCopy(), exp2.deepCopy(), exp3.deepCopy(), stmt.deepCopy());
    }

    @Override
    public String toString() {
        return "for(" + var + "=" + exp1 + ";" + var + "<" + exp2 + ";" + var + "=" + exp3 + ") " + stmt;
    }
}