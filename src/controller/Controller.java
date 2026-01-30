
package controller;

import exception.MyException;
import exception.RepoException;
import model.PrgState;
import model.adt.MyIList;
import model.adt.MyIStack;
import model.statements.IStmt;
import model.values.IValue;
import model.values.RefValue;
import repository.IRepository;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


public class Controller implements IController {
    private IRepository repository;
    private boolean displayFlag;
    private ExecutorService executor;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    @Override
    public void executeAllSteps() throws FileNotFoundException, RepoException, MyException {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrg(repository.getPrgList());
        while (!prgList.isEmpty()) {
            prgList.forEach(prgState -> {
                try {
                    repository.logPrgState(prgState);
                } catch (MyException e) {
                    throw new RuntimeException(e);
                }
            });

            Map<Integer, IValue> newHeap = safeGarbageCollector(
                    getAddrFromSymTables(prgList),
                    prgList.get(0).getHeap().getContent());

            prgList.forEach(prgState -> {prgState.getHeap().setContent(newHeap);});

            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(repository.getPrgList());

        }
        executor.shutdownNow();
        repository.setPrgList(prgList);
    }

    @Override
    public void setDisplay(boolean display) {
        this.displayFlag = display;
    }

    @Override
    public boolean getDisplay() {
        return displayFlag;
    }

    @Override
    public void addPrgState(PrgState prgState) {
        repository.addPrgState(prgState);
    }

    @Override
    public void clearPrgState() {
        repository.clearPrgState();
    }

    @Override
    public List<Integer> getAddrFromSymTables(List<PrgState> prgStates) {
        return prgStates.stream()
                .flatMap(prg -> getAddrFromValues(prg.getSymTable().getAll()).stream())
                .collect(Collectors.toList());
    }

    @Override
    public List<PrgState> getPrgList() {
        return repository.getPrgList();
    }


    @Override
    public List<Integer> getAddrFromValues(Collection<IValue> values) {
        return values.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {
                    RefValue v1 = (RefValue) v;
                    return v1.getAddr();
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public void oneStepForAllPrg(List<PrgState> prgList) {
        if (executor == null || executor.isShutdown()) {
            executor = Executors.newFixedThreadPool(2);
        }
        prgList.forEach(prg -> {
            try {
                repository.logPrgState(prg);
            } catch (MyException e) {
                throw new RuntimeException(e);
            }
        });

        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(p::executeOneStep))
                .toList();

        try {
            List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            throw new RuntimeException(e.getMessage());
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            prgList.addAll(newPrgList);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        prgList.forEach(prg -> {
            try {
                repository.logPrgState(prg);
            } catch (MyException e) {
                throw new RuntimeException(e);
            }
        });

        repository.setPrgList(prgList);

    }

    public void executeGarbageCollector(List<PrgState> prgList) {
        Map<Integer, IValue> newHeap = safeGarbageCollector(
                getAddrFromSymTables(prgList),
                prgList.get(0).getHeap().getContent());

        prgList.forEach(prgState -> {
            prgState.getHeap().setContent(newHeap);
        });
    }

    public Map<Integer, IValue> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer, IValue> heap) {

        Set<Integer> currentActiveAddresses = new HashSet<>(symTableAddr);

        boolean changed;
        do {
            changed = false;
            Collection<IValue> activeHeapValues = currentActiveAddresses.stream()
                    .filter(heap::containsKey)
                    .map(heap::get)
                    .collect(Collectors.toList());

            List<Integer> newlyReferencedAddresses = getAddrFromValues(activeHeapValues);

            for (Integer addr : newlyReferencedAddresses) {
                if (currentActiveAddresses.add(addr)) {
                    changed = true;
                }
            }
        } while (changed);

        return heap.entrySet().stream()
                .filter(e -> currentActiveAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
