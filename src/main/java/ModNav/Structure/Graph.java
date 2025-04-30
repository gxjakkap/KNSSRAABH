package ModNav.Structure;

import ModNav.ModNavExceptions.KeyAlreadyExistedException;
import ModNav.ModNavExceptions.KeyDoesNotExistException;
import ModNav.ModNavExceptions.EdgeAlreadyExistedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class Graph <N extends  Node, E extends Edge<N>> extends AdjacencyList<N, E> {
    private Map<String, N> nodeMap;

    public Graph(int verticesCount){
        super(verticesCount);
        this.nodeMap = new HashMap<String, N>();
    }

    public Graph(){
        super();
        this.nodeMap = new HashMap<String, N>();
    }

    public void addVertex(N n){
        if (this.list.containsKey(n)){
            throw new KeyAlreadyExistedException();
        }
        this.list.put(n, new ArrayList<E>());
        this.nodeMap.put(n.id, n);
        this.verticesCount = this.verticesCount + 1;
    }

    public void removeVertex(N n){
        if (!this.list.containsKey(n)){
            throw new KeyDoesNotExistException();
        }
        this.list.remove(n);
        this.nodeMap.remove(n.id);
    }

    public N getNodeById(String id){
        if (!this.nodeMap.containsKey(id)){
            return null;
        }
        return this.nodeMap.get(id);
    }

    public void addEdge(N origin, N dest, int weight){
        if (!this.list.containsKey(origin)){
            throw new KeyDoesNotExistException();
        }
        List<E> edges = this.list.get(origin);
        AtomicBoolean existed = new AtomicBoolean(false);
        edges.forEach(e -> {
            if (e.getDest().equals(dest)) {
                existed.set(true);
            }
        });

        if (existed.get()){
            throw new EdgeAlreadyExistedException();
        }

        Edge<N> e = new Edge<N>(dest, weight);
        edges.add((E) e);
    }

    public List<E> getAdjacencyList(N target){
        return this.list.get(target);
    }

    public Map<N, List<E>> getAdjacencyList(){
        return this.list;
    }
}
