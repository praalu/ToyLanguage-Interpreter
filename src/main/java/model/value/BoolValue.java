package model.value;

import model.type.BoolType;
import model.type.IType;

public class BoolValue implements IValue {
    private boolean bool;

    public BoolValue(boolean b) {
        this.bool = b;
    }

    @Override
    public IType getType() {
        return new BoolType();
    }

    public boolean getBool() {
        return this.bool;
    }

    @Override
    public String toString() {
        return String.valueOf(this.bool);
    }

    @Override
    public IValue deepCopy() { return new BoolValue(this.bool); }

    @Override
    public boolean equals(Object another) {
        if (this == another) return true;
        if (!(another instanceof BoolValue)) return false;
        BoolValue otherBool = (BoolValue) another;
        return this.bool == otherBool.bool;
    }
}
