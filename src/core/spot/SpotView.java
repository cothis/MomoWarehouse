package core.spot;

import java.util.List;

public interface SpotView {
    String spotIndex();
    Spot addUI();
    Spot deleteUI(List<Spot> list);
    void printList(List<Spot> list);
    Spot updateUI(List<Spot> list);
}
