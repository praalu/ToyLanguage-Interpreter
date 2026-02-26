package model.statement;

import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.type.IType;
import model.type.IntType;
import model.value.BoolValue;
import model.value.IValue;
import model.value.IntValue;

public class VarDeclStmt implements IStmt {
    private String id;
    private IType type;

    public VarDeclStmt(String newId, IType newType) {
        this.id = newId;
        this.type = newType;
    }

    @Override
    public String toString() {
        return this.type.toString() + " " + this.id;
    }

    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(this.id, this.type);
    }

    @Override
    public PrgState execute(PrgState state)throws MyException{
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        if(symTable.isDefined(this.id)){
            throw new MyException("variable '"+id+"' is already defined");
        }
        IValue defaultVal = type.defaultValue();
        symTable.put(id, defaultVal);

        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        typeEnv.put(id, type);
        return typeEnv;
    }
}
