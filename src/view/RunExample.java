package view;

import controller.Controller;
import exception.MyException;
import exception.RepoException;
import model.PrgState;

import java.io.FileNotFoundException;

public class RunExample extends Command {
    private Controller ctr;
    public RunExample(String key, String desc,Controller ctr){
        super(key, desc);
        this.ctr=ctr;
    }
    @Override
    public void execute() {
        try {
            ctr.executeAllSteps();
            for (PrgState prg : ctr.getPrgList()) {
                System.out.println(prg.toString());
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (RepoException e) {
            System.out.println("Repo exception.");
        } catch (MyException e) {
            System.out.println("My exception." + e.getMessage());
        }
    }
}
