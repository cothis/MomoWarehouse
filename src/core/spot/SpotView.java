package core.spot;

import core.common.exception.EmptyListException;
import core.common.exception.ExitException;

import java.util.List;

public interface SpotView {
    String spotIndex();
    Spot addUI() throws ExitException;
    Spot deleteUI(List<Spot> list) throws ExitException, EmptyListException;
    void printList(List<Spot> list);
    Spot updateUI(List<Spot> list) throws ExitException, EmptyListException;
}
