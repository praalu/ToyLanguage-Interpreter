package model.statement;

import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.expression.IExp;
import model.type.IType;
import model.type.StringType;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStmt implements IStmt {
    private final IExp exp;

    public CloseRFileStmt(IExp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue val=exp.eval(state.getSymTable(),state.getHeapTable());
        if(!val.getType().equals(new StringType()))
            throw new MyException("Wrong type of expression");

        StringValue s=((StringValue)val);
        BufferedReader br=state.getFileTable().getValue(s);
        if(br==null)
            throw new MyException("File not found");

        try{
            br.close();
            state.getFileTable().remove(s);
        }
        catch(Exception e){
            throw new MyException("Error closing the file");
        }

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CloseRFileStmt(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "closeRFile(" + exp.toString() + ")";
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typexp = exp.typecheck(typeEnv);
        if (typexp.equals(new StringType())) {
            return typeEnv;
        } else {
            throw new MyException("CloseRFile: requires a string expression");
        }
    }
}
