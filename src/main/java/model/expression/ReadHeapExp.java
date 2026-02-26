package model.expression;

import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import exception.MyException;
import model.type.IType;
import model.type.RefType;
import model.value.RefValue;
import model.value.IValue;

public class ReadHeapExp implements IExp
{
    private IExp exp;
    public ReadHeapExp(IExp exp)
    {
        this.exp = exp;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl, MyIHeap<IValue> heapTbl) throws MyException
    {
        IValue val=exp.eval(tbl,heapTbl);
        if(val instanceof RefValue refVal)
        {
            int adr= refVal.getAddress();
            if(!heapTbl.isDefined(adr))
                throw new MyException("Invalid address accessed!");

            return heapTbl.lookUp(adr);
        }
        else throw new MyException("The expression is not a RefValue!");
    }

    @Override
    public IExp deepCopy()
    {
        return new ReadHeapExp(exp.deepCopy());
    }

    @Override
    public String toString()
    {
        return exp.toString();
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typ = exp.typecheck(typeEnv);
        if (typ instanceof RefType) {
            RefType reft = (RefType) typ;
            return reft.getInner();
        } else {
            throw new MyException("ReadHeap: argument is not a RefType");
        }
    }
}
