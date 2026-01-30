package model.statements;

import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.expressions.IExp;
import model.types.IType;
import model.types.StringType;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;

public class CloseRFile implements IStmt{

    private IExp exp;

    public CloseRFile(IExp exp){
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue expEval = exp.eval(state.getSymTable(), state.getHeap());
        if (expEval.getType().equals(new StringType())) {
            StringValue value = (StringValue) expEval;
            try {
                BufferedReader br = state.getFileTable().get(value);
                br.close();
                state.getFileTable().remove(value);

            } catch (Exception ex) {
                throw new MyException(ex.getMessage());
            }
        }
        else {
            throw new MyException("The expression is not a string");
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CloseRFile(exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typeExp = exp.typecheck(typeEnv);
        if (typeExp.equals(new StringType())) {
            return typeEnv;
        } else {
            throw new MyException("CloseRFile: expression is not of type string");
        }
    }

}
