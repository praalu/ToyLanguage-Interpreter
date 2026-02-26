package view.commands;

import controller.Controller;
import exception.MyException;

public class RunExample extends Command{
    private Controller ctr;

    public RunExample(String key, String desc, Controller ctr) {
        super(key, desc);
        this.ctr = ctr;
    }

    @Override
    public void execute(){
        try{
            ctr.allStep();
        }catch(InterruptedException e){
            System.out.println("error during execution: "+e.getMessage());
        }catch(Exception e){
            System.out.println("unexpected error: " +e);
        }
    }
}
