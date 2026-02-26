package view;

import model.statement.IStmt;
import model.statement.*;

import model.type.*;
import model.value.*;
import model.expression.*;


public class Example {


    public static IStmt getEx1() {
        return new CompStmt(
                new VarDeclStmt("total_candy", new IntType()),
                new CompStmt(
                        new AssignStmt("total_candy",
                                new ArithExp(
                                        new ValueExp(new IntValue(100)),
                                        new ValueExp(new IntValue(4)),
                                        4
                                )
                        ),
                        new PrintStmt(new VarExp("total_candy"))
                )
        );
    }


    public static IStmt getEx2() {
        return new CompStmt(
                new VarDeclStmt("apples", new IntType()),
                new CompStmt(
                        new VarDeclStmt("baskets", new IntType()),
                        new CompStmt(
                                new AssignStmt("apples",
                                        new ArithExp(
                                                new ValueExp(new IntValue(5)),
                                                new ArithExp(
                                                        new ValueExp(new IntValue(20)),
                                                        new ValueExp(new IntValue(3)),
                                                        2
                                                ),
                                                3
                                        )
                                ),
                                new CompStmt(
                                        new AssignStmt("baskets",
                                                new ArithExp(
                                                        new VarExp("apples"),
                                                        new ValueExp(new IntValue(50)),
                                                        2
                                                )
                                        ),
                                        new PrintStmt(new VarExp("baskets"))
                                )
                        )
                )
        );
    }


    public static IStmt getEx3() {
        return new CompStmt(
                new VarDeclStmt("is_sunny", new BoolType()),
                new CompStmt(
                        new VarDeclStmt("mood", new IntType()),
                        new CompStmt(
                                new AssignStmt("is_sunny", new ValueExp(new BoolValue(false))),
                                new CompStmt(
                                        new IfStmt(
                                                new VarExp("is_sunny"),
                                                new AssignStmt("mood", new ValueExp(new IntValue(10))),
                                                new AssignStmt("mood", new ValueExp(new IntValue(1)))
                                        ),
                                        new PrintStmt(new VarExp("mood"))
                                )
                        )
                )
        );
    }



    public static IStmt getEx4() {
        return new CompStmt(
                new VarDeclStmt("varf", new StringType()),
                new CompStmt(
                        new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(
                                new OpenRFileStmt(new VarExp("varf")),
                                new CompStmt(
                                        new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(
                                                new ReadFileStmt(new VarExp("varf"), "varc"),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(
                                                                new ReadFileStmt(new VarExp("varf"), "varc"),
                                                                new CompStmt(
                                                                        new PrintStmt(new VarExp("varc")),
                                                                        new CloseRFileStmt(new VarExp("varf"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }


    public static IStmt getEx5() {
        return new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(
                                        new NewStmt("a", new VarExp("v")),
                                        new CompStmt(
                                                new PrintStmt(new VarExp("v")),
                                                new PrintStmt(new VarExp("a"))
                                        )
                                )
                        )
                )
        );
    }

    public static IStmt getEx6() {
        return new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(
                                        new NewStmt("a", new VarExp("v")),
                                        new CompStmt(
                                                new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                                new PrintStmt(
                                                        new ArithExp(
                                                                new ReadHeapExp(new ReadHeapExp(new VarExp("a"))),
                                                                new ValueExp(new IntValue(5)), 1
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    public static IStmt getEx7() {
        return new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                new CompStmt(
                                        new WriteHeapStmt("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(
                                                new ArithExp( new ReadHeapExp(new VarExp("v")), new ValueExp(new IntValue(5)), 1)
                                        )
                                )
                        )
                )
        );
    }

    public static IStmt getEx8() {
        return new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(
                                        new NewStmt("a", new VarExp("v")),
                                        new CompStmt(
                                                new NewStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a"))))
                                        )
                                )
                        )
                )
        );
    }

    public static IStmt getEx9() {
        return new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(
                                new WhileStmt(
                                        new RelExp(new VarExp("v"), new ValueExp(new IntValue(0)),">"),
                                        new CompStmt(
                                                new PrintStmt(new VarExp("v")),
                                                new AssignStmt("v",
                                                        new ArithExp( new VarExp("v"), new ValueExp(new IntValue(1)), 2)
                                                )
                                        )
                                ),
                                new PrintStmt(new VarExp("v"))
                        )
                )
        );
    }


    public static IStmt getEx10(){
        return new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(new NewStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(new model.statement.ForkStmt(new CompStmt(new WriteHeapStmt("a", new ValueExp(new IntValue(30))),
                                                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                                new PrintStmt(new ReadHeapExp(new VarExp("a"))))))),
                                                new CompStmt(new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new ReadHeapExp(new VarExp("a")))))))));
    }
}
