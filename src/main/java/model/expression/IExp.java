package model.expression;

import exception.MyException;
import model.adt.MyIHeap;
import model.type.IType;
import model.value.IValue;
import model.adt.MyIDictionary;

public interface IExp {
    IValue eval (MyIDictionary<String, IValue> dict, MyIHeap<IValue> heapTbl) throws MyException;
    public  IExp deepCopy();
    IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyException;
}
