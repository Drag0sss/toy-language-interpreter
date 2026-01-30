package controller;

import exception.MyException;
import exception.RepoException;
import model.PrgState;
import model.values.IValue;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.List;

public interface IController {

    void executeAllSteps() throws FileNotFoundException, RepoException, MyException;

    void setDisplay(boolean display);

    boolean getDisplay();

    void addPrgState(PrgState prgState);

    void clearPrgState();

    List<Integer> getAddrFromValues(Collection<IValue> values);

    List<PrgState> removeCompletedPrg(List<PrgState> inPrgList);

    void oneStepForAllPrg(List<PrgState> prgList);

    List<Integer> getAddrFromSymTables(List<PrgState> prgStates);

    List<PrgState> getPrgList();

}