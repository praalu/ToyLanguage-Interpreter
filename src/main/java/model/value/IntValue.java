package model.value;

import model.type.IType;
import model.type.IntType;

public class IntValue implements IValue {
    private int value;

    public IntValue(int val) {
        this.value = val;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    @Override
    public IValue deepCopy() { return new IntValue(this.value); }

    @Override
    public boolean equals(Object another) {
        if (this == another) return true;
        if (!(another instanceof IntValue)) return false;
        IntValue otherInt = (IntValue) another;
        return this.value == otherInt.value;
    }

}