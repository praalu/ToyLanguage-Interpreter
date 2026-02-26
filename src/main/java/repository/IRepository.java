package repository;

import model.PrgState;
import exception.MyException;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    List<PrgState> getPrgList();

    void setPrgList(List<PrgState> list);

    void logPrgStateExec(PrgState prg) throws MyException;

    void addPrg(PrgState prg);
}

