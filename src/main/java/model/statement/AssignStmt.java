package model.statement;

import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.expression.IExp;
import model.type.IType;
import model.value.IValue;
import model.adt.MyIStack;

public class AssignStmt  implements IStmt{
    private final String id;
    private final IExp exp;

    public AssignStmt(String id, IExp exp) {
        this.id = id;
        this.exp = exp;
    }

    @Override
    public String toString() {
        return this.id + "=" + this.exp.toString();
    }

    @Override
    public IStmt deepCopy() {
        return new AssignStmt(this.id,this.exp.deepCopy());
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk=state.getExeStack();
        MyIDictionary<String, IValue> symTbl=state.getSymTable();
        MyIHeap<IValue> heapTable=state.getHeapTable();

        if(symTbl.isDefined(id))
        {
            IValue val=exp.eval(symTbl,heapTable);
            IType typId=(symTbl.getValue(id)).getType();
            if(val.getType().equals(typId))
            {
                symTbl.put(id,val);
            }
            else throw new MyException("declared type of variable: "+ id +
                    " and type of the assigned do not match!");
        }
        else throw new MyException("the used variable: " + id + " was never declared before!");

        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException {
        IType typevar = typeEnv.getValue(id);
        IType typexp = exp.typecheck(typeEnv);
        if (typevar.equals(typexp))
            return typeEnv;
        else
            throw new MyException("Assignment: right hand side and left hand side have different types for variable " + id);
    }
}
