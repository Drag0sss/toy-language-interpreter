package model.statements;

import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.expressions.IExp;
import model.types.IType;
import model.types.IntType;
import model.types.StringType;
import model.values.IValue;
import model.values.IntValue;
import model.values.StringValue;

import java.io.BufferedReader;

public class ReadFile implements IStmt {

    private IExp exp;
    private String varName;

    public ReadFile(IExp exp, String varName) {
        this.exp = exp;
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue expEval = exp.eval(state.getSymTable(), state.getHeap());
        if (state.getSymTable().isDefined(varName) && state.getSymTable().get(varName).getType().equals(new IntType())) {
            if (expEval.getType().equals(new StringType())) {
                StringValue value = (StringValue) expEval;
                try {
                    BufferedReader br = state.getFileTable().get(value);
                    String line = br.readLine();
                    if (line == null) {
                        IType val =  new IntType();
                        state.getSymTable().put(varName, val.defaultValue());
                    }
                    else {
                        IValue val = new IntValue(Integer.parseInt(line));
                        state.getSymTable().put(varName, val);
                    }

                } catch (Exception e) {
                    throw new MyException(e.getMessage());
                }
            }
            else {
                throw new MyException("file name must be a string value.");
            }
        }
        else {
            throw new MyException("Variable " + varName + " is not defined!");
        }

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFile(exp.deepCopy(), varName);
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typeExp = exp.typecheck(typeEnv);

        if (!typeExp.equals(new StringType())) {
            throw new MyException("ReadFile: expression is not of type string");
        }

        if (!typeEnv.isDefined(varName)) {
            throw new MyException("ReadFile: variable " + varName + " is not defined");
        }

        IType typeVar = typeEnv.get(varName);
        if (!typeVar.equals(new IntType())) {
            throw new MyException("ReadFile: variable " + varName + " is not of type int");
        }

        return typeEnv;
    }

    public String toString() {
        return "readFile(" + exp + ", " + varName + ")";
    }
}
