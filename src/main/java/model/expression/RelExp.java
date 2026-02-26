package model.expression;

import exception.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.type.BoolType;
import model.type.IType;
import model.type.IntType;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.IValue;

public class RelExp implements IExp{
    private IExp e1;
    private IExp e2;
    private String op;

    public RelExp(IExp e1, IExp e2, String op){
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue>tbl, MyIHeap<IValue> heapTbl)throws MyException{
        IValue v1 = e1.eval(tbl, heapTbl);
        if(!v1.getType().equals(new IntType()))
            throw new MyException("first operand is not int");

        IValue v2 = e2.eval(tbl, heapTbl);
        if(!v2.getType().equals(new IntType()))
            throw new MyException("second operand is not int");

        int n1 = ((IntValue)v1).getValue();
        int n2 = ((IntValue)v2).getValue();

        boolean result;
        switch(op){
            case "<": result = n1 < n2; break;
            case "<=": result = n1 <= n2; break;
            case "==": result = n1 == n2; break;
            case "!=": result = n1 != n2; break;
            case ">": result = n1 > n2; break;
            case ">=": result = n1 >= n2; break;
            default:
                throw new MyException("unknown operation"+op);
        }
        return new BoolValue(result);
    }

    @Override
    public IExp deepCopy() {
        return new RelExp(e1.deepCopy(), e2.deepCopy(), op);
    }

    @Override
    public String toString(){
        return "("+e1.toString()+" "+op+" "+e2.toString()+")";
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typ1, typ2;
        typ1 = e1.typecheck(typeEnv);
        typ2 = e2.typecheck(typeEnv);

        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new BoolType();
            } else {
                throw new MyException("RelationalExp: second operand is not an integer");
            }
        } else {
            throw new MyException("RelationalExp: first operand is not an integer");
        }
    }
}

