package model.statement;

import model.adt.MyIDictionary;
import model.expression.IExp;
import model.PrgState;
import model.type.BoolType;
import exception.MyException;
import model.type.IType;
import model.value.BoolValue;
import model.value.IValue;

public class WhileStmt implements IStmt{
    private IExp condition;
    IStmt stmt;

    public WhileStmt(IExp condition, IStmt stmt){
        this.stmt=stmt;
        this.condition=condition;
    }

    @Override
    public PrgState execute(PrgState prgState) throws MyException {
        IValue val=condition.eval(prgState.getSymTable(),prgState.getHeapTable());
        if(val.getType().equals(new BoolType())){
            BoolValue bValue=(BoolValue)val;
            if(bValue.getBool())
            {
                prgState.getExeStack().push(this);
                prgState.getExeStack().push(stmt);
            }
        }
        else throw new MyException("Invalid condition!");
        return null;
    }

    @Override
    public IStmt deepCopy()
    {
        return new WhileStmt(condition.deepCopy(), stmt.deepCopy());
    }

    @Override
    public String toString() {
        return "While(" + condition.toString() + "){" + stmt.toString() + "}";
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typexp = condition.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            stmt.typecheck(typeEnv.deepCopy());
            return typeEnv;
        } else {
            throw new MyException("The condition of WHILE has not the type bool");
        }
    }
}
