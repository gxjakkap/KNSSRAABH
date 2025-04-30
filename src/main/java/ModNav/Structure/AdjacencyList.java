package ModNav.Structure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AdjacencyList {
    protected int verticesCount;
    protected Map<Node, List<Edge>> list;

    public AdjacencyList(int verticesCount) {
        this.list = new HashMap<Node, List<Edge>>();
        this.verticesCount = verticesCount;
    }

    public AdjacencyList() {
        this.list = new HashMap<Node, List<Edge>>();
        this.verticesCount = 0;
    }

    public int getVerticesCount() {
        return this.verticesCount;
    }
}
