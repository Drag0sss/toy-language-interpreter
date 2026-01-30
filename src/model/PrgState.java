package model;

import exception.MyException;
import exception.RepoException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.adt.MyIList;
import model.adt.MyIStack;
import model.statements.IStmt;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;

public class PrgState {
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, IValue> symTable;
    private MyIList<IValue> out;
    private IStmt originalProgram; //optional field, but good to have
    private MyIDictionary<StringValue, BufferedReader> fileTable;
    private MyIHeap<Integer, IValue> heap;
    private static int nextId = 0;
    private int id;

    public PrgState(MyIDictionary<StringValue, BufferedReader> filetbl ,MyIStack<IStmt> stk, MyIDictionary<String, IValue> symtbl, MyIList<IValue> ot, MyIHeap<Integer, IValue> heap, IStmt prg){
        this.exeStack = stk;
        this.symTable = symtbl;
        this.out = ot;
        this.originalProgram = prg;
        this.fileTable = filetbl;
        this.heap = heap;
        this.id = getNextId();
        this.exeStack.push(prg);
    }

    public int getId() {
        return this.id;
    }

    public synchronized int getNextId() {
        return ++this.nextId;
    }

    public PrgState executeOneStep() throws FileNotFoundException, RepoException, MyException {
        if (exeStack.isEmpty()) throw new MyException("exeStack is empty");{
            IStmt crtStmt = exeStack.pop();
            return crtStmt.execute(this);
        }
    }

    public MyIList<IValue> getOut() {
        return out;
    }

    public MyIDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }
    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public MyIHeap<Integer, IValue> getHeap() {
        return heap;
    }

    @Override
    public String toString() {
        return "ID:\n" + id + "\n" + "Exe Stack:\n" + exeStack.toString()+"\n"+ "SymTable:\n" + symTable.toString()+ "\n" + "Out:\n" + out.toString() + "\n" +"FileTable:\n" + fileTable.toString() + "\n" + "Heap:\n" + heap.toString();
    }

    public boolean isNotCompleted() {
        return !exeStack.isEmpty();
    }
}