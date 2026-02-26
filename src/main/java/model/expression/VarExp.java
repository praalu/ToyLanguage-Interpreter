package model.expression;

import exception.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.type.IType;
import model.value.IValue;

public class VarExp implements IExp{
    private String id;

    public VarExp(String givenId) {
        id = givenId;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> dictionary, MyIHeap<IValue> heapTbl) throws MyException {
        return dictionary.getValue(id);
    }

    @Override
    public IExp deepCopy() {
        return new VarExp(this.id);
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyException{
        return typeEnv.getValue(id);
    }
}
