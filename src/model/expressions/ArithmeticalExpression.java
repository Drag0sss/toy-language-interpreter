package model.expressions;

import exception.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

public class ArithmeticalExpression implements IExp {
    private IExp nr1;
    private IExp nr2;
    private ArithmeticalOperation op;

    public ArithmeticalExpression(IExp nr1, IExp nr2, ArithmeticalOperation op) {
        this.nr1 = nr1;
        this.nr2 = nr2;
        this.op = op;
    }

    public String toString() {
        String operator = "";
        if (op == ArithmeticalOperation.PLUS) {
            operator = "+";
        } else if (op == ArithmeticalOperation.MINUS) {
            operator = "-";
        } else if (op == ArithmeticalOperation.TIMES) {
            operator = "*";
        } else if (op == ArithmeticalOperation.DIVIDE) {
            operator = "/";
        } else {
            operator = "?";
        }

        return "(" + nr1.toString() + " " + operator + " " + nr2.toString() + ")";
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> table, MyIHeap<Integer, IValue> heap) throws MyException {
        if (op.equals(ArithmeticalOperation.PLUS)) {
            IValue n1 = nr1.eval(table, heap);
            if (n1.getType() instanceof IntType) {
                IValue n2 = nr2.eval(table, heap);
                if (n2.getType() instanceof IntType) {

                    int v1 = ((IntValue)n1).getVal();
                    int v2 = ((IntValue)n2).getVal();
                    return new IntValue(v1 + v2);
                }
                else
                    throw new MyException("Operand 2 is not an integer");
            }
            else {
                throw new MyException("Operand 1 is not an integer");
            }

        }
        else if (op.equals(ArithmeticalOperation.MINUS)) {
            IValue n1 = nr1.eval(table, heap);
            if (n1.getType() instanceof IntType) {
                IValue n2 = nr2.eval(table, heap);
                if  (n2.getType() instanceof IntType) {
                    int v1 = ((IntValue)n1).getVal();
                    int v2 = ((IntValue)n2).getVal();
                    return new IntValue(v1 - v2);
                }
                else
                    throw new MyException("Operand 2 is not an integer");
            }
            else {
                throw new MyException("Operand 1 is not an integer");
            }
        }
        else if (op.equals(ArithmeticalOperation.TIMES)) {
            IValue n1 = nr1.eval(table, heap);
            if (n1.getType() instanceof IntType) {
                IValue n2 = nr2.eval(table, heap);
                if (n2.getType() instanceof IntType) {
                    int v1 = ((IntValue)n1).getVal();
                    int v2 = ((IntValue)n2).getVal();

                    return new IntValue(v1 * v2);
                }
                else
                    throw new MyException("Operand 2 is not an integer");
            }
            else {
                throw new MyException("Operand 1 is not an integer");
            }
        }
        else if (op.equals(ArithmeticalOperation.DIVIDE)) {
            IValue n1 = nr1.eval(table, heap);
            if (n1.getType() instanceof IntType) {
                IValue n2 = nr2.eval(table, heap);
                if (n2.getType() instanceof IntType) {
                    if (((IntValue)n2).getVal() != 0) {
                        int v1 = ((IntValue)n1).getVal();
                        int v2 = ((IntValue)n2).getVal();
                        return new IntValue(v1 / v2);
                    }
                    else
                        throw new MyException("Division by zero");
                }
                else
                    throw new MyException("Operand 2 is not an integer");
            }
            else {
                throw new MyException("Operand 1 is not an integer");
            }
        }
        else {
            throw new MyException("ArithmeticalOperation Not Implemented");
        }
    }

    @Override
    public IExp deepCopy() {
        return new ArithmeticalExpression(nr1.deepCopy(), nr2.deepCopy(), op);
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typ1, typ2;
        typ1 = nr1.typecheck(typeEnv);
        typ2 = nr2.typecheck(typeEnv);
        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new IntType();
            } else
                throw new MyException("second operand is not an integer");
        } else
            throw new MyException("first operand is not an integer");
    }
}
