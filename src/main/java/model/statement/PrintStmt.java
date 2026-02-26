package model.statement;

import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyIList;
import model.expression.IExp;
import model.type.IType;
import model.value.IValue;

public class PrintStmt implements IStmt{
    private final IExp exp;
    public PrintStmt(IExp exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "print(" + exp.toString() + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(this.exp.deepCopy());
    }

    @Override
    public PrgState execute(PrgState prgState) throws MyException {
        IValue val=exp.eval(prgState.getSymTable(),prgState.getHeapTable());
        prgState.getOut().add(val);
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }
}