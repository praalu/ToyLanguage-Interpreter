package model.type;


import model.value.RefValue;
import model.value.IValue;

public class RefType implements IType {
    IType inner;

    public RefType(IType inner) {
        this.inner = inner;
    }

    public IType getInner()
    {
        return inner;
    }


    @Override
    public boolean equals(Object another){
        if(another instanceof RefType){
            return inner.equals(((RefType)another).getInner());
        }

        return false;
    }

    @Override
    public String toString(){
        return "Ref(" + inner.toString() + ")";
    }

    @Override
    public IValue defaultValue()
    {
        return new RefValue(1,inner);
    }

    @Override
    public IType deepCopy()
    {
        return new RefType(inner);
    }


}
