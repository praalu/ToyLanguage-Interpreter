package model;

import exception.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIList;
import model.adt.MyIStack;
import model.adt.MyIHeap;
import model.statement.IStmt;
import model.value.IValue;
import model.value.StringValue;


import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class PrgState {
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, IValue> symTable;
    private MyIList<IValue> out;
    private MyIHeap<IValue> heapTable;
    private MyIDictionary<StringValue, BufferedReader> fileTable;
    private IStmt originalProgram;
    private static int staticId = 0;
    private int id;


    public PrgState(MyIStack<IStmt> stack, MyIDictionary<String, IValue> symTable, MyIList<IValue> out, MyIDictionary<StringValue,BufferedReader> fileTable, MyIHeap<IValue> heap, IStmt program){
        this.exeStack = stack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heapTable=heap;
        this.id=getNewId();
        this.originalProgram = program.deepCopy();
        this.exeStack.push(program);
    }

    public static synchronized int getNewId(){
        staticId++;
        return staticId;
    }

    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public MyIDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public MyIList<IValue> getOut() {
        return out;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable(){return this.fileTable;}

    public MyIHeap<IValue> getHeapTable() {return this.heapTable;}

    public void addFile(StringValue filename, BufferedReader reader){
        fileTable.put(filename, reader);
    }

    public void removeFile(StringValue filename) throws MyException { fileTable.remove(filename);}

    public BufferedReader getReader(StringValue filename){
        return fileTable.getValue(filename);
    }

    public void setExeStack(MyIStack<IStmt> stack) { this.exeStack = stack; }

    public void setSymTable(MyIDictionary<String,IValue> symTable) { this.symTable = symTable; }

    public void setOut(MyIList<IValue> out) { this.out = out; }

    public void setOriginalProgram(IStmt program) { this.originalProgram = program.deepCopy(); }

    public void setFileTable(MyIDictionary<StringValue, BufferedReader> fileTable){this.fileTable = fileTable;}



    //alea noi de la A5
    public int getId(){ return id; }

    public Boolean isNotCompleted(){
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() throws MyException{
        if(exeStack.isEmpty()) throw new MyException("PrgState stack is empty");
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }


    @Override
    public String toString(){
        return  "Id: " + id + "\n" +
                "ExeStack: " + exeStack + "\n" +
                "SymTable: " + symTable + "\n" +
                "Out: " + out + "\n" +
                "FileTable: " + fileTable + "\n" +
                "HeapTable: " + heapTable + "\n"+
                "-----------------------------------------------------------";
    }


    public void restoreOriginal()
    {
        exeStack.clear();
        exeStack.push(originalProgram.deepCopy());
        symTable.clear();
        out.clear();
        fileTable.clear();
        heapTable.clear();
    }


}
