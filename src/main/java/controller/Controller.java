package controller;

import exception.MyException;
import model.PrgState;
import model.adt.MyHeap;
import model.adt.MyIHeap;
import model.adt.MyIStack;
import model.statement.IStmt;
import model.value.IValue;
import model.value.RefValue;
import repository.IRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private final IRepository repo;
    private boolean displayFlag = false;
    private ExecutorService executor;


    public Controller(IRepository repo) {
        this.repo = repo;
    }

    public IRepository getRepo(){
        return repo;
    }

    public void setDisplayFlag(boolean value){
        this.displayFlag = value;
    }


    public void allStep() throws InterruptedException{
        executor = Executors.newFixedThreadPool(2); //create thread pool

        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());

        while(prgList.size() > 0){
            //colectam toate adresele de pe toate threadurile intr un singur heap
            //reciclam logica de la safe garbage colector

            //pt logica de pe heap, you could rethink it (as in trebuie sa ai acces nujmai la datele dintr-un punct dat, nu tot heap ul
            List<Integer> allSymTableAddrs = prgList.stream()
                    .flatMap(p -> getAddrFromSymTable(p.getSymTable().getContent().values()).stream())
                    .collect(Collectors.toList());
            List<Integer> allHeapAddrs = prgList.stream()
                    .flatMap(p -> getAddrFromHeap(p.getSymTable().getContent().values()).stream())
                    .collect(Collectors.toList());


            MyIHeap<IValue> heap = prgList.get(0).getHeapTable();
            Map<Integer, IValue> newHeapContent = safeGarbageCollector(allSymTableAddrs, allHeapAddrs, heap.getContent());
            heap.setContent(newHeapContent);

            oneStepForAllPrg(prgList);

            prgList = removeCompletedPrg(repo.getPrgList());
        }

        executor.shutdownNow();

        repo.setPrgList(prgList);
    }


    Map<Integer,IValue> safeGarbageCollector(List<Integer> symTableAddr, List<Integer> heapTableAddr, Map<Integer,IValue> heap)
    {

        return heap.entrySet().stream()
                .filter(e-> (symTableAddr.contains(e.getKey())) || heapTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues)
    {
        return symTableValues.stream()
                .filter(v->v instanceof RefValue)
                .map(v-> {RefValue v1=(RefValue)v; return v1.getAddress(); })
                .collect(Collectors.toList());
    }

    List<Integer> getAddrFromHeap(Collection<IValue> heapTableValues)
    {
        return heapTableValues.stream()
                .filter(v->v instanceof RefValue)
                .map(v->{RefValue v1=(RefValue)v; return v1.getAddress(); })
                .collect(Collectors.toList());
    }


    //de la A5 in colo
    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
        return inPrgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }


    public void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException{
        prgList.forEach(prg ->{
            try{
                repo.logPrgStateExec(prg);
                if(displayFlag) System.out.println(prg);
            }catch(MyException e){
                System.out.println(e.getMessage());
            }
        });

        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p)->(Callable<PrgState>)(()->{ return p.oneStep(); }))
                .collect(Collectors.toList());

        List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try{
                        return future.get();
                    }catch(Exception e){
                        System.out.println("Exception in thread: "+e.getMessage());
                        return null;
                    }
                })
                .filter(p->p!=null)
                .collect(Collectors.toList());


        prgList.addAll(newPrgList);

        prgList.forEach(prg ->{
            try{
                repo.logPrgStateExec(prg);
                if(displayFlag) System.out.println(prg);
            }catch(MyException e){
                System.out.println(e.getMessage());
            }
        });

        repo.setPrgList(prgList);
    }


    public void oneStepForAllPrg() throws InterruptedException {
        executor = Executors.newFixedThreadPool(2);

        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());
        if (prgList.isEmpty()) {
            executor.shutdownNow();
            return;
        }

        oneStepForAllPrg(prgList);

        executor.shutdownNow();
    }
}
