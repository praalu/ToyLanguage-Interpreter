package model.value;

import model.type.RefType;
import model.type.IType;

public class RefValue implements IValue {
    int address;
    IType locationType;

    public int getAddress() {
        return this.address;
    }

    public IType getLocationType(){
        return this.locationType;
    }

    public RefValue(int address, IType locationType){
        this.address = address;
        this.locationType = locationType;
    }

    @Override
    public IType getType()
    {
        return new RefType(locationType);
    }

    @Override
    public String toString()
    {
        return "(" + address + "," + locationType + ")";
    }

    @Override
    public IValue deepCopy()
    {
        return new RefValue(address,locationType.deepCopy());
    }
}
