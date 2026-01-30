import controller.Controller;
import exception.ControllerException;
import exception.MyException;
import exception.RepoException;
import model.PrgState;
import model.adt.*;
import model.expressions.*;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.RefType;
import model.types.StringType;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;
import model.values.StringValue;
import repository.IRepository;
import repository.Repository;
import view.ExitCommand;
import view.RunExample;
import view.TextMenu;

import java.io.BufferedReader;
import java.util.Map;

class Interpreter {
    public static void main(String[] args) {
        MyIStack<IStmt> stack = new MyStack<IStmt>();
        MyIDictionary<String, IValue> symtbl = new MyDictionary<String, IValue>();
        MyIList<IValue> outputlist = new MyList<IValue>();
        MyIDictionary<StringValue, BufferedReader> filetbl = new MyDictionary<StringValue, BufferedReader>();
        MyHeap heap = new MyHeap();

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));

        IStmt ex1 = new CompStmt(
                new VarDeclStmt(new IntType(), "v"),
                new CompStmt(
                        new AssignStmt("v", new ValueExpression(new IntValue(2))),
                        new PrintStmt(new VariableExpression("v"))));
        try {
            ex1.typecheck(new MyDictionary<>());
            PrgState prg1 = new PrgState(new MyDictionary<>(), new MyStack<>(), new MyDictionary<>(), new MyList<>(), heap, ex1);
            IRepository repo1 = new Repository("log1.txt");
            Controller ctr1 = new Controller(repo1);
            ctr1.addPrgState(prg1);
            menu.addCommand(new RunExample("1", ex1.toString(), ctr1));

        } catch (MyException e) {
            System.out.println("Example 1: " + e.getMessage());
        }

        IStmt ex2 = new CompStmt(
                new VarDeclStmt(new IntType(), "a"),
                new CompStmt(
                        new VarDeclStmt(new IntType(), "b"),
                        new CompStmt
                                (
                                        new AssignStmt
                                                (
                                                        "a",
                                                        new ArithmeticalExpression
                                                                (
                                                                        new ValueExpression(new IntValue(2)),
                                                                        new ArithmeticalExpression
                                                                                (
                                                                                        new ValueExpression(new IntValue(3)),
                                                                                        new ValueExpression(new IntValue(5)),
                                                                                        ArithmeticalOperation.TIMES
                                                                                ),
                                                                        ArithmeticalOperation.PLUS
                                                                )
                                                ),
                                        new CompStmt
                                                (
                                                        new AssignStmt
                                                                (
                                                                        "b",
                                                                        new ArithmeticalExpression
                                                                                (
                                                                                        new VariableExpression("a"),
                                                                                        new ValueExpression(new IntValue(1)),
                                                                                        ArithmeticalOperation.PLUS
                                                                                )
                                                                ),
                                                        new PrintStmt(new VariableExpression("b"))
                                                )
                                )
                )
        );

        try {
            ex2.typecheck(new MyDictionary<>());
            PrgState prg2 = new PrgState(new MyDictionary<>(), new MyStack<>(), new MyDictionary<>(), new MyList<>(), heap, ex2);
            IRepository repo2 = new Repository("log2.txt");
            Controller ctr2 = new Controller(repo2);
            ctr2.addPrgState(prg2);
            menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
        } catch (MyException e) {
            System.out.println("Example 2: " + e.getMessage());
        }

        IStmt ex3 = new CompStmt(
                new VarDeclStmt(new BoolType(), "a"),
                new CompStmt(
                        new VarDeclStmt(new IntType(), "v"),
                        new CompStmt(
                                new AssignStmt(
                                        "a",
                                        new ValueExpression(new BoolValue(true))
                                ),
                                new CompStmt(
                                        new IfStmt(
                                                new VariableExpression("a"),
                                                new AssignStmt(
                                                        "v",
                                                        new ValueExpression(new IntValue(2))
                                                ),
                                                new AssignStmt(
                                                        "v",
                                                        new ValueExpression(new IntValue(3))
                                                )
                                        ),
                                        new PrintStmt(new VariableExpression("v"))
                                )
                        )
                )
        );

        try {
            ex3.typecheck(new MyDictionary<>());
            PrgState prg3 = new PrgState(new MyDictionary<>(), new MyStack<>(), new MyDictionary<>(), new MyList<>(), heap, ex3);
            IRepository repo3 = new Repository("log3.txt");
            Controller ctr3 = new Controller(repo3);
            ctr3.addPrgState(prg3);
            menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
        } catch (MyException e) {
            System.out.println("Example 3: " + e.getMessage());
        }

        IStmt ex4 = new CompStmt(
                new VarDeclStmt(new StringType(), "varf"),
                new CompStmt(
                        new AssignStmt("varf", new ValueExpression(new StringValue("test.in"))),
                        new CompStmt(
                                new OpenRFile(new VariableExpression("varf")),
                                new CompStmt(
                                        new VarDeclStmt(new IntType(), "varc"),
                                        new CompStmt(
                                                new ReadFile(new VariableExpression("varf"), "varc"),
                                                new CompStmt(
                                                        new PrintStmt(new VariableExpression("varc")),
                                                        new CompStmt(
                                                                new ReadFile(new VariableExpression("varf"), "varc"),
                                                                new CompStmt(
                                                                        new PrintStmt(new VariableExpression("varc")),
                                                                        new CloseRFile(new VariableExpression("varf"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        try {
            ex4.typecheck(new MyDictionary<>());
            PrgState prg4 = new PrgState(new MyDictionary<>(), new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyHeap(), ex4);
            IRepository repo4 = new Repository("log4.txt");
            Controller ctr4 = new Controller(repo4);
            ctr4.addPrgState(prg4);
            menu.addCommand(new RunExample("4", ex4.toString(), ctr4));
        } catch (MyException e) {
            System.out.println("Example 4: " + e.getMessage());
        }

        IStmt ex5 = new CompStmt(
                new VarDeclStmt(new RefType(new IntType()), "v"),
                new CompStmt(
                        new NewStmt("v", new ValueExpression(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt(new RefType(new RefType(new IntType())), "a"),
                                new CompStmt(
                                        new NewStmt("a", new VariableExpression("v")),
                                        new CompStmt(
                                                new NewStmt("v", new ValueExpression(new IntValue(30))),
                                                new NewStmt("v", new ValueExpression(new IntValue(50)))
                                        )
                                )
                        )
                )
        );

        try {
            ex5.typecheck(new MyDictionary<>());
            PrgState prg5 = new PrgState(new MyDictionary<>(), new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyHeap(), ex5);
            IRepository repo5 = new Repository("log5.txt");
            Controller ctr5 = new Controller(repo5);
            ctr5.addPrgState(prg5);
            menu.addCommand(new RunExample("5", ex5.toString(), ctr5));
        } catch (MyException e) {
            System.out.println("Example 5: " + e.getMessage());
        }

        IStmt ex6 = new CompStmt(
                new VarDeclStmt(new IntType(), "v"),
                new CompStmt(
                        new AssignStmt("v", new ValueExpression(new IntValue(4))),
                        new CompStmt(
                                new WhileStmt(
                                        new RelationalExpression(
                                                new VariableExpression("v"),
                                                new ValueExpression(new IntValue(0)),
                                                RelationalOperation.GREATER_THAN
                                        ),
                                        new CompStmt(
                                                new PrintStmt(new VariableExpression("v")),
                                                new AssignStmt("v", new ArithmeticalExpression(
                                                        new VariableExpression("v"),
                                                        new ValueExpression(new IntValue(1)),
                                                        ArithmeticalOperation.MINUS
                                                ))
                                        )
                                ),
                                new PrintStmt(new VariableExpression("v"))
                        )
                )
        );

        try {
            ex6.typecheck(new MyDictionary<>());
            PrgState prg6 = new PrgState(new MyDictionary<>(), new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyHeap(), ex6);
            IRepository repo6 = new Repository("log6.txt");
            Controller ctr6 = new Controller(repo6);
            ctr6.addPrgState(prg6);
            menu.addCommand(new RunExample("6", ex6.toString(), ctr6));
        } catch (MyException e) {
            System.out.println("Example 6: " + e.getMessage());
        }

        IStmt ex7 = new CompStmt(
                new VarDeclStmt(new IntType(), "v"),
                new CompStmt(
                        new VarDeclStmt(new RefType(new IntType()), "a"),
                        new CompStmt(
                                new AssignStmt("v", new ValueExpression(new IntValue(10))),
                                new CompStmt(
                                        new NewStmt("a", new ValueExpression(new IntValue(22))),
                                        new CompStmt(
                                                new ForkStmt(
                                                        new CompStmt(
                                                                new WriteHStmt("a", new ValueExpression(new IntValue(30))),
                                                                new CompStmt(
                                                                        new AssignStmt("v", new ValueExpression(new IntValue(32))),
                                                                        new CompStmt(
                                                                                new PrintStmt(new VariableExpression("v")),
                                                                                new PrintStmt(new ReadHExp(new VariableExpression("a")))
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new CompStmt(
                                                        new PrintStmt(new VariableExpression("v")),
                                                        new PrintStmt(new ReadHExp(new VariableExpression("a")))
                                                )
                                        )
                                )
                        )
                )
        );

        try {
            ex7.typecheck(new MyDictionary<>());
            PrgState prg7 = new PrgState(new MyDictionary<>(), new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyHeap(), ex7);
            IRepository repo7 = new Repository("log7.txt");
            Controller ctr7 = new Controller(repo7);
            ctr7.addPrgState(prg7);
            menu.addCommand(new RunExample("7", ex7.toString(), ctr7));
        } catch (MyException e) {
            System.out.println("Example 7: " + e.getMessage());
        }

        IStmt ex8 = new CompStmt(
                new VarDeclStmt(new IntType(), "v"),
                new CompStmt(
                        new AssignStmt("v", new ValueExpression(new IntValue(10))),
                        new IfStmt(
                                new VariableExpression("v"),
                                new PrintStmt(new ValueExpression(new IntValue(1))),
                                new PrintStmt(new ValueExpression(new IntValue(0)))
                        )
                )
        );

        try {
            ex8.typecheck(new MyDictionary<>());
            PrgState prg8 = new PrgState(new MyDictionary<>(), new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyHeap(), ex8);
            IRepository repo8 = new Repository("log8.txt");
            Controller ctr8 = new Controller(repo8);
            ctr8.addPrgState(prg8);
            menu.addCommand(new RunExample("8", ex8.toString(), ctr8));
        } catch (MyException e) {
            System.out.println("Example 8: " + e.getMessage());
        }

        IStmt ex9 = new CompStmt(
                new VarDeclStmt(new RefType(new IntType()), "a"),
                new CompStmt(
                        new NewStmt("a", new ValueExpression(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt(new IntType(), "v"),
                                new CompStmt(
                                        new ForStmt("v",
                                                new ValueExpression(new IntValue(0)),
                                                new ValueExpression(new IntValue(3)),
                                                new ArithmeticalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(1)), ArithmeticalOperation.PLUS),
                                                new ForkStmt(
                                                        new CompStmt(
                                                                new PrintStmt(new VariableExpression("v")),
                                                                new AssignStmt("v", new ArithmeticalExpression(new VariableExpression("v"), new ReadHExp(new VariableExpression("a")), ArithmeticalOperation.TIMES))
                                                        )
                                                )
                                        ),
                                        new PrintStmt(new ReadHExp(new VariableExpression("a")))
                                )
                        )
                )
        );

        try {
            ex9.typecheck(new MyDictionary<>());
            PrgState prgFor = new PrgState(new MyDictionary<>(), new MyStack<>(), new MyDictionary<>(),
                    new MyList<>(), new MyHeap(), ex9);
            IRepository repoFor = new Repository("log9.txt");
            Controller ctrFor = new Controller(repoFor);
            ctrFor.addPrgState(prgFor);
            menu.addCommand(new RunExample("9", ex9.toString(), ctrFor));
        } catch (MyException e) {
            System.out.println("Example 9: " + e.getMessage());
        }


        menu.show();

    }
}