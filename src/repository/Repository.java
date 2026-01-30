
package repository;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import exception.MyException;
import exception.RepoException;
import model.PrgState;
import model.statements.IStmt;
import model.values.IValue;

public class Repository implements IRepository {
    private List<PrgState> prgStates;
    private String fileName;
    public Repository(String fileName){
        this.fileName=fileName;
        this.prgStates=new ArrayList<>();

    }

    public void addPrgState(PrgState prgState){
        prgStates.add(prgState);
    }

    @Override
    public void logPrgState(PrgState prg) throws MyException {

        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)))) {

            logFile.println(prg.toString());


            logFile.println("\n------------------------------------------------------\n");

        } catch (IOException e) {
            throw new MyException("Error writing to log file: " + e.getMessage());
        }
    }

    @Override
    public void clearPrgState() {
        prgStates.clear();
    }

    @Override
    public List<PrgState> getPrgList() {
        return prgStates;
    }

    @Override
    public void setPrgList(List<PrgState> prgStates) {
        this.prgStates = prgStates;
    }
}
