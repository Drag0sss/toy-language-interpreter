package repository;

import exception.MyException;
import exception.RepoException;
import model.PrgState;

import java.io.FileNotFoundException;
import java.util.List;

public interface IRepository {
    public void addPrgState(PrgState prgState);
    public void logPrgState(PrgState prg) throws MyException;
    public void clearPrgState();
    public List<PrgState> getPrgList();
    public void setPrgList(List<PrgState> prgStates);
}