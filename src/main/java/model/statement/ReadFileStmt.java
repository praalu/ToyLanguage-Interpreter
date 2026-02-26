package model.statement;

import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.expression.IExp;
import model.type.IType;
import model.type.IntType;
import model.type.StringType;
import model.value.IValue;
import model.value.IntValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStmt implements IStmt {
    private final IExp exp;
    private final String varName;

    public ReadFileStmt(IExp exp, String varName) {
        this.exp = exp;
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        if(!state.getSymTable().isDefined(varName))
            throw new MyException("Variable " + varName + " is not defined");

        IType typ=state.getSymTable().getValue(varName).getType();

        if(!typ.equals(new IntType()))
            throw new MyException("Variable " + varName + " is not of type Int");

        IValue val=exp.eval(state.getSymTable(), state.getHeapTable());
        if(!val.getType().equals(new StringType()))
            throw new MyException("Expression " + exp.toString() + " is not of type String");

        StringValue v=(StringValue)val;
        BufferedReader br=state.getFileTable().getValue(v);

        try{
            String line=br.readLine();
            if(line==null)
                state.getSymTable().put(varName,new IntValue(0));
            else{
                state.getSymTable().put(varName,new IntValue(Integer.parseInt(line)));
            }

        }catch(Exception e){
            throw new MyException("Error reading file " + v.toString());
        }

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFileStmt(exp.deepCopy(), varName);
    }

    @Override
    public String toString() {
        return "readFile(" + exp.toString() + ", " + varName + ")";
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException{
        IType typexp = exp.typecheck(typeEnv);
        if (!typexp.equals(new StringType())) {
            throw new MyException("ReadFile: filename expression must be a string");
        }

        if (!typeEnv.isDefined(varName)) {
            throw new MyException("ReadFile: variable '" + varName + "' is not defined");
        }
        IType typevar = typeEnv.getValue(varName);
        if (!typevar.equals(new IntType())) {
            throw new MyException("ReadFile: variable '" + varName + "' must be of type int");
        }

        return typeEnv;
    }
}
