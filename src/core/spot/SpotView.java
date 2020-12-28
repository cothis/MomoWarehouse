package core.spot;

import java.util.List;

public interface SpotView {
    String spotIndex();
    Spot addUI() throws Exception;
    Spot deleteUI(List<Spot> list) throws Exception;
    void printList(List<Spot> list);
    Spot updateUI(List<Spot> list) throws Exception;
}
