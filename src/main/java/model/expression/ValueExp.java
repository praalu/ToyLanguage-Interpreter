package model.expression;

import exception.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.type.IType;
import model.value.IValue;

public class ValueExp implements IExp{
    private IValue value;

    public ValueExp(IValue newValue){

        value = newValue;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> dictionary, MyIHeap<IValue> heapTbl) throws MyException {
        return value;
    }

    @Override
    public IExp deepCopy() {
        return new ValueExp(this.value.deepCopy());
    }

    @Override
    public String toString(){
        return value.toString();
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyException{
        return value.getType();
    }
}
