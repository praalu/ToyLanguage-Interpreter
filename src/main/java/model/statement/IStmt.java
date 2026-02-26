package model.statement;
import exception.MyException;
import model.PrgState;
import model.adt.MyIDictionary;
import model.type.IType;

public interface IStmt {
    public PrgState execute(PrgState state) throws MyException;
    public IStmt deepCopy();
    MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws MyException;
}
