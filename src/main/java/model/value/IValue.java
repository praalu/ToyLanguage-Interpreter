package model.value;

import model.type.IType;

public interface IValue {
    public IType getType();
    public IValue deepCopy();
    public boolean equals(Object another);
}
