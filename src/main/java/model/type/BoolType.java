package model.type;

import model.value.BoolValue;
import model.value.IValue;

public class BoolType implements IType {
    public boolean equals(Object another) {
        return another instanceof BoolType;
    }

    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public IValue defaultValue() {
        return new BoolValue(true);
    }

    @Override
    public IType deepCopy() {
        return new BoolType();
    }
}
