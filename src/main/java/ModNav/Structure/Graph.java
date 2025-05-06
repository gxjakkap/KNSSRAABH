package ModNav.Structure;

import ModNav.ModNavExceptions.KeyAlreadyExistedException;
import ModNav.ModNavExceptions.KeyDoesNotExistException;
import ModNav.ModNavExceptions.EdgeAlreadyExistedException;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Graph <N extends  Node, E extends Edge<N>> extends AdjacencyList<N, E> {
    protected Map<String, N> nodeMap;

    public Graph(int verticesCount){
        super(verticesCount);
        this.nodeMap = new HashMap<String, N>();
    }

    public Graph(){
        super();
        this.nodeMap = new HashMap<String, N>();
    }

    public void addVertex(N n){
        if (this.list.containsKey(n) || this.nodeMap.containsKey(n.getId())){
            throw new KeyAlreadyExistedException();
        }
        this.list.put(n, new ArrayList<E>());
        this.nodeMap.put(n.id, n);
        this.verticesCount = this.verticesCount + 1;
    }

    public Optional<N> getNodeById(String id){
        if (!this.nodeMap.containsKey(id.toUpperCase())){
            return Optional.empty();
        }
        return Optional.ofNullable(this.nodeMap.get(id.toUpperCase()));
    }

    public List<E> getAdjacencyList(N target){
        return this.list.get(target);
    }

    public Map<N, List<E>> getAdjacencyList(){
        return this.list;
    }
}
