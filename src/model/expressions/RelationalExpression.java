package model.expressions;

import exception.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.BoolType;
import model.types.IType;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;

public class RelationalExpression implements IExp {
    private final IExp exp1;
    private final IExp exp2;
    private final RelationalOperation op;

    public RelationalExpression(IExp exp1, IExp exp2, RelationalOperation op) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.op = op;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl, MyIHeap<Integer, IValue> heap) throws MyException {
        IValue val1 = exp1.eval(tbl, heap);
        IValue val2 = exp2.eval(tbl, heap);

        if (!val1.getType().equals(new IntType()) || !val2.getType().equals(new IntType())) {
            throw new MyException("Relational expression operands must be integers.");
        }

        int n1 = ((IntValue) val1).getVal();
        int n2 = ((IntValue) val2).getVal();
        boolean result;

        switch (op) {
            case LESS_THAN:
                result = n1 < n2;
                break;
            case LESS_EQUAL:
                result = n1 <= n2;
                break;
            case EQUAL:
                result = n1 == n2;
                break;
            case NOT_EQUAL:
                result = n1 != n2;
                break;
            case GREATER_THAN:
                result = n1 > n2;
                break;
            case GREATER_EQUAL:
                result = n1 >= n2;
                break;
            default:
                throw new MyException("Invalid relational operator.");
        }

        return new BoolValue(result);
    }

    @Override
    public IExp deepCopy() {
        return new RelationalExpression(exp1.deepCopy(), exp2.deepCopy(), op);
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typ1, typ2;
        typ1 = exp1.typecheck(typeEnv);
        typ2 = exp2.typecheck(typeEnv);
        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new BoolType();
            } else
                throw new MyException("second operand is not an integer");
        } else
            throw new MyException("first operand is not an integer");
    }

    @Override
    public String toString() {
        return "(" + exp1.toString() + " " + op.toString() + " " + exp2.toString() + ")";
    }
}