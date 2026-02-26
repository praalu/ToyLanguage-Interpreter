package model.statement;

import model.adt.MyIDictionary;
import model.expression.IExp;
import model.PrgState;
import model.statement.IStmt;
import exception.MyException;
import model.type.RefType;
import model.value.RefValue;
import model.value.IValue;
import model.type.IType;

public class NewStmt implements IStmt{
    String name;
    IExp exp;

    public NewStmt(String name, IExp exp){
        this.name=name;
        this.exp=exp;
    }

    @Override
    public PrgState execute(PrgState prgState) throws MyException {
        IValue v=prgState.getSymTable().getValue(name);
        if(v==null) {
            throw new MyException("Symbol "+name+" not found");
        }

        if(v instanceof RefValue refValue){
            IValue val=exp.eval(prgState.getSymTable(),prgState.getHeapTable());
            if(val.getType().equals(refValue.getLocationType())){
                int address=prgState.getHeapTable().put(val);
                prgState.getSymTable().put(name,new RefValue(address,val.getType()));
            }
            else throw new MyException("Inner type does not match");
        }
        else throw new MyException("Variable "+name+" is not of refType");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NewStmt(name,exp.deepCopy());
    }

    @Override
    public String toString()
    {
        return "new(" + name + ", " + exp + ")";
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typevar = typeEnv.getValue(name);
        IType typexp = exp.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new MyException("NEW stmt: right hand side and left hand side have different types");
    }
}

