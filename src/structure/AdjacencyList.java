package structure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AdjacencyList {
    protected int verticesCount;
    protected Map<String, List<Edge>> list;

    public AdjacencyList(int verticesCount){
        this.list = new HashMap<String, List<Edge>>();
        this.verticesCount = verticesCount;
    }
}
