package model.type;

import model.value.IValue;
import model.value.IntValue;

public class IntType implements IType {
    public boolean equals(Object another) {
        return another instanceof IntType;
    }

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public IValue defaultValue() {
        return new IntValue(1);
    }

    @Override
    public IType deepCopy() {
        return new IntType();
    }
}
