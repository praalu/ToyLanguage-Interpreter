package model.statement;

import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.adt.MyIStack;
import model.expression.IExp;
import model.type.BoolType;
import model.type.IType;
import model.value.BoolValue;
import model.value.IValue;

public class IfStmt implements IStmt{
    private final IExp exp;
    private final IStmt thenStmt;
    private final IStmt elseStmt;

    public IfStmt(IExp expression, IStmt thenStmt, IStmt elseStmt){
        this.exp = expression;
        this.elseStmt = elseStmt;
        this.thenStmt = thenStmt;
    }

    @Override
    public String toString() {
        return "(IF(" + exp.toString() + ") THEN(" + thenStmt.toString()
                + ") ELSE(" + elseStmt.toString() + "))";
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(this.exp.deepCopy(), this.thenStmt.deepCopy(), this.elseStmt.deepCopy());
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk=state.getExeStack();
        MyIDictionary<String, IValue> symTable=state.getSymTable();
        MyIHeap<IValue> heapTable=state.getHeapTable();

        IValue condition=exp.eval(symTable,heapTable);
        if(!condition.getType().equals(new BoolType())) {
            throw new MyException("Condition is not boolean");
        }

        BoolValue boolCond=(BoolValue)condition;
        if(boolCond.getBool())
            stk.push(thenStmt);
        else stk.push(elseStmt);
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typexp = exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            thenStmt.typecheck(typeEnv.deepCopy());
            elseStmt.typecheck(typeEnv.deepCopy());
            return typeEnv;
        } else {
            throw new MyException("The condition of IF has not the type bool");
        }
    }
}
