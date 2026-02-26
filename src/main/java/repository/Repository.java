package repository;

import model.PrgState;
import exception.MyException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private List<PrgState> programs;
    private final String logFilePath;


    public Repository(PrgState prg, String logFilePath){
        this.programs = new ArrayList<>();
        this.logFilePath = logFilePath;
    }

    @Override
    public void addPrg(PrgState state){
        this.programs.add(state);
    }

    @Override
    public List<PrgState> getPrgList() {
        return programs;
    }

    @Override
    public void setPrgList(List<PrgState> list) {
        this.programs = list;
    }

    @Override
    public void logPrgStateExec(PrgState prg) throws MyException{
        try(PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)))){
            logFile.println(prg.toString());
        } catch(IOException ioe){
            throw new MyException("Repo: can't write to log file: "+ioe.getMessage());
        }
    }

}
