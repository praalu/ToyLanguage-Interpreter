package model.statement;

import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.expression.IExp;
import model.type.StringType;
import model.value.IValue;
import model.value.StringValue;
import model.adt.MyIStack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import model.type.IType;

public class OpenRFileStmt implements IStmt {
    private final IExp exp;

    public OpenRFileStmt(IExp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk=state.getExeStack();
        IValue val=exp.eval(state.getSymTable(),state.getHeapTable());
        if(val.getType().equals(new StringType()))
        {
            StringValue s=(StringValue)val;
            if(state.getFileTable().isDefined(s))
                throw new MyException("file already opened!");
            try
            {
                String path=s.getValue();
                BufferedReader reader=new BufferedReader(new FileReader(path));
                state.getFileTable().put(s,reader);
            }
            catch (IOException e){
                throw new MyException("file open failed!");
            }
        }
        else throw new MyException("Expression not of string type");


        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new OpenRFileStmt(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "OpenRFile(" + exp.toString() + ")";
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typexp = exp.typecheck(typeEnv);
        if (typexp.equals(new model.type.StringType()))
            return typeEnv;
        else
            throw new MyException("OpenRFile: requires string filename");
    }
}
