package model.value;

import model.type.IType;
import model.type.StringType;

import java.util.Objects;

public class StringValue implements IValue {
    private final String value;

    public StringValue(String val) { this.value = val; }

    public String getValue() {
        return this.value;
    }

    @Override
    public IType getType() { return new StringType(); }

    @Override
    public IValue deepCopy() {
        return new StringValue(this.value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object another) {
        if (this == another) return true;
        if (!(another instanceof StringValue)) return false;
        StringValue stringValue = (StringValue) another;
        return Objects.equals(this.value, stringValue.value);
    }

}

