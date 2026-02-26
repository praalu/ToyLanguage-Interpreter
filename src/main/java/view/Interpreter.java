package view;

import controller.Controller;
import model.PrgState;
import model.adt.*;
import model.statement.*;
import repository.*;
import view.commands.ExitCommand;
import view.commands.RunExample;
import view.commands.TextMenu;

public class Interpreter {
    static void main(String[] args){
        try{
            IStmt ex1 = Example.getEx1();
            ex1.typecheck(new MyDictionary<>());
            PrgState prg1 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),
                    new MyHeap<>(), ex1);
            IRepository repo1 = new Repository(prg1, "log1.txt");
            repo1.addPrg(prg1);
            Controller ctr1 = new Controller(repo1);
            ctr1.setDisplayFlag(true);


            IStmt ex2 = Example.getEx2();
            ex2.typecheck(new MyDictionary<>());
            PrgState prg2 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),
                    new MyHeap<>(), ex2);
            IRepository repo2 = new Repository(prg2, "log2.txt");
            repo2.addPrg(prg2);
            Controller ctr2 = new Controller(repo2);
            ctr2.setDisplayFlag(true);


            IStmt ex3 = Example.getEx3();
            ex3.typecheck(new MyDictionary<>());
            PrgState prg3 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),
                    new MyHeap<>(), ex3);
            IRepository repo3 = new Repository(prg3, "log3.txt");
            repo3.addPrg(prg3);
            Controller ctr3 = new Controller(repo3);
            ctr3.setDisplayFlag(true);


            IStmt ex4 = Example.getEx4();
            ex4.typecheck(new MyDictionary<>());
            PrgState prg4 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),
                    new MyHeap<>(), ex4);
            IRepository repo4 = new Repository(prg4, "log4.txt");
            repo4.addPrg(prg4);
            Controller ctr4 = new Controller(repo4);
            ctr4.setDisplayFlag(true);


            IStmt ex5 = Example.getEx5();
            ex5.typecheck(new MyDictionary<>());
            PrgState prg5 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),
                    new MyHeap<>(), ex5);
            IRepository repo5 = new Repository(prg5, "log5.txt");
            repo5.addPrg(prg5);
            Controller ctr5 = new Controller(repo5);
            ctr5.setDisplayFlag(true);


            IStmt ex6 = Example.getEx6();
            ex6.typecheck(new MyDictionary<>());
            PrgState prg6 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),
                    new MyHeap<>(), ex6);
            IRepository repo6 = new Repository(prg6, "log6.txt");
            repo6.addPrg(prg6);
            Controller ctr6 = new Controller(repo6);
            ctr6.setDisplayFlag(true);


            IStmt ex7 = Example.getEx7();
            ex7.typecheck(new MyDictionary<>());
            PrgState prg7 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),
                    new MyHeap<>(), ex7);
            IRepository repo7 = new Repository(prg7, "log7.txt");
            repo7.addPrg(prg7);
            Controller ctr7 = new Controller(repo7);
            ctr7.setDisplayFlag(true);


            IStmt ex8 = Example.getEx8();
            ex8.typecheck(new MyDictionary<>());
            PrgState prg8 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),
                    new MyHeap<>(), ex8);
            IRepository repo8 = new Repository(prg8, "log8.txt");
            repo8.addPrg(prg8);
            Controller ctr8 = new Controller(repo8);
            ctr8.setDisplayFlag(true);

            IStmt ex9 = Example.getEx9();
            ex9.typecheck(new MyDictionary<>());
            PrgState prg9 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),
                    new MyHeap<>(), ex9);
            IRepository repo9 = new Repository(prg9, "log9.txt");
            repo9.addPrg(prg9);
            Controller ctr9 = new Controller(repo9);
            ctr9.setDisplayFlag(true);

            IStmt ex10 = Example.getEx10();
            ex10.typecheck(new MyDictionary<>());
            PrgState prg10 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),
                    new MyHeap<>(), ex10);
            IRepository repo10 = new Repository(prg10, "log10.txt");
            repo10.addPrg(prg10);
            Controller ctr10 = new Controller(repo10);
            ctr10.setDisplayFlag(true);



            TextMenu menu = new TextMenu();
            menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
            menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
            menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
            menu.addCommand(new RunExample("4", ex4.toString(), ctr4));
            menu.addCommand(new RunExample("5", ex5.toString(), ctr5));
            menu.addCommand(new RunExample("6", ex6.toString(), ctr6));
            menu.addCommand(new RunExample("7", ex7.toString(), ctr7));
            menu.addCommand(new RunExample("8", ex8.toString(), ctr8));
            menu.addCommand(new RunExample("9", ex9.toString(), ctr9));
            menu.addCommand(new RunExample("10", ex10.toString(), ctr10));
            menu.addCommand(new ExitCommand("0", "exit"));
            menu.show();
        } catch(Exception e){
            System.out.println("Prg initialization error: "+e.getMessage());
        }
    }
}
