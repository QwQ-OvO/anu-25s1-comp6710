package ws10a;

import java.util.Collection;
import java.util.Map;


public interface GraphNode<T> {

    T getValue();
    Collection<GraphNode<T>> getNeighbours();
    Map<GraphNode<T>, Integer> getCostedNeighbours();
}
