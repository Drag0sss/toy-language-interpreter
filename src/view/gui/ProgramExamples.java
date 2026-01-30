package view.gui;

import model.statements.*;
import model.expressions.*;
import model.types.*;
import model.values.*;

import java.util.List;

public class ProgramExamples {

    public static List<IStmt> getAll() {

        IStmt ex1 = new CompStmt(
                new VarDeclStmt(new IntType(), "v"),
                new CompStmt(
                        new AssignStmt("v", new ValueExpression(new IntValue(2))),
                        new PrintStmt(new VariableExpression("v"))));

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
                                                        new CompStmt (new PrintStmt(new ReadHExp(new VariableExpression("a"))), new NopStmt())
                                                )
                                        )
                                )
                        )
                )
        );

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

        return List.of(ex1, ex2, ex3, ex4, ex5, ex6, ex7, ex8, ex9);
    }
}
