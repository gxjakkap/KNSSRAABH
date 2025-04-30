package ModNav.Structure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AdjacencyList <N extends Node, E extends Edge<N>> {
    protected int verticesCount;
    protected Map<N, List<E>> list;

    public AdjacencyList(int verticesCount) {
        this.list = new HashMap<N, List<E>>();
        this.verticesCount = verticesCount;
    }

    public AdjacencyList() {
        this.list = new HashMap<N, List<E>>();
        this.verticesCount = 0;
    }

    public int getVerticesCount() {
        return this.verticesCount;
    }
}
