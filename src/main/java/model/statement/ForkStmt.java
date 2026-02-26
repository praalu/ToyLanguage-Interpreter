package model.statement;

import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyStack;
import model.adt.MyIStack;
import model.statement.IStmt;
import model.value.IValue;
import model.type.IType;

public class ForkStmt implements IStmt {
    private IStmt stmt;

    public ForkStmt(IStmt stmt){
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> newStack = new MyStack<>();

        MyIDictionary<String, IValue> newSymTable = state.getSymTable().deepCopy();

        return new PrgState(
                newStack,
                newSymTable,
                state.getOut(),
                state.getFileTable(),
                state.getHeapTable(),
                stmt
        );
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        stmt.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }

    @Override
    public IStmt deepCopy(){
        return new ForkStmt(stmt.deepCopy());
    }

    @Override
    public String toString(){
        return "fork(" + stmt.toString() + ")";
    }
}
