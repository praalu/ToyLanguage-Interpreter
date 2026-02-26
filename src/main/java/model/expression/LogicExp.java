package model.expression;

import exception.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.type.BoolType;
import model.type.IType;
import model.value.BoolValue;
import model.value.IValue;

public class LogicExp implements IExp {
    private final IExp exp1;
    private final IExp exp2;
    private final int operation;

    public LogicExp(IExp exp1, IExp exp2, int operation) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.operation = operation;
    }

    @Override
    public String toString() {
        return switch (operation) {
            case 1 -> exp1.toString() + "&&" + exp2.toString();
            case 2 -> exp1.toString() + "||" + exp2.toString();
            default -> throw new RuntimeException(new MyException("Operation not allowed: " + operation));
        };
    }

    @Override
    public IExp deepCopy() {
        return new LogicExp(this.exp1.deepCopy(),this.exp2.deepCopy(),operation);
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> dictionary, MyIHeap<IValue> heapTbl) throws MyException {
        IValue value1 = exp1.eval(dictionary, heapTbl);
        IValue value2 = exp2.eval(dictionary, heapTbl);

        if(value1.getType().equals(new BoolType())) {
            throw new MyException("First operand is not a boolean!");
        }

        if(value2.getType().equals(new BoolType())) {
            throw new MyException("Second operand is not a boolean!");
        }

        BoolValue b1 = (BoolValue) value1;
        BoolValue b2 = (BoolValue) value2;

        boolean bol1 = b1.getBool();
        boolean bol2 = b2.getBool();

        return switch (operation) {
            case 1 -> new BoolValue(bol1 && bol2);
            case 2 -> new BoolValue(bol1 || bol2);
            default -> throw new MyException("Unknown operator: " + operation);
        };
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typ1, typ2;
        typ1 = exp1.typecheck(typeEnv);
        typ2 = exp2.typecheck(typeEnv);

        if (typ1.equals(new BoolType())) {
            if (typ2.equals(new BoolType())) {
                return new BoolType();
            } else {
                throw new MyException("LogicExp: second operand is not a boolean");
            }
        } else {
            throw new MyException("LogicExp: first operand is not a boolean");
        }
    }
}