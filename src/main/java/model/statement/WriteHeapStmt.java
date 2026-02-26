package model.statement;

import model.adt.MyIDictionary;
import model.expression.IExp;
import model.PrgState;
import model.statement.IStmt;
import exception.MyException;
import model.type.IType;
import model.type.RefType;
import model.value.RefValue;
import model.value.IValue;

public class WriteHeapStmt implements IStmt {
    private String name;
    private IExp exp;

    public WriteHeapStmt(String name, IExp exp) {
        this.name = name;
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "writeHeap(" +  name + ", " + exp.toString() + ")";
    }

    @Override
    public IStmt deepCopy()
    {
        return new WriteHeapStmt(name, exp.deepCopy());
    }

    @Override
    public PrgState execute(PrgState prgState) throws MyException
    {
        IValue val=prgState.getSymTable().getValue(name);
        if(val==null)
            throw new MyException("Variable not declared!");

        if(val instanceof RefValue refV)
        {
            if(!prgState.getHeapTable().isDefined(refV.getAddress()))
                throw new MyException("Memory wasn't allocated!");

            IValue expValue=exp.eval(prgState.getSymTable(),prgState.getHeapTable());
            if(!expValue.getType().equals(refV.getLocationType()))
                throw new MyException("Inner type mismatch!");

            prgState.getHeapTable().update(refV.getAddress(),expValue);
        }
        else throw new MyException("The value is not a ref value, thus the type is not ref type!");

        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typevar = typeEnv.getValue(name);
        IType typexp = exp.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new MyException("WriteHeap: right hand side and left hand side have different types");
    }


}

