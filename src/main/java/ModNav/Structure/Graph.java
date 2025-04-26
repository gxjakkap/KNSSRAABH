package ModNav.Structure;

import ModNav.ModNavExceptions.KeyAlreadyExistedException;
import ModNav.ModNavExceptions.KeyDoesNotExistException;
import ModNav.ModNavExceptions.EdgeAlreadyExistedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class Graph extends AdjacencyList {
    private Map<String, Node> nodeMap;

    public Graph(int verticesCount){
        super(verticesCount);
        this.nodeMap = new HashMap<String, Node>();
    }

    public Graph(){
        this.nodeMap = new HashMap<String, Node>();
    }

    public void addVertex(Node n){
        if (this.list.containsKey(n)){
            throw new KeyAlreadyExistedException();
        }
        this.list.put(n, new ArrayList<>());
        this.nodeMap.put(n.id, n);
        this.verticesCount = this.verticesCount + 1;
    }

    public void removeVertex(Node n){
        if (!this.list.containsKey(n)){
            throw new KeyDoesNotExistException();
        }
        this.list.remove(n);
        this.nodeMap.remove(n.id);
    }

    public Node getNodeById(String id){
        if (!this.nodeMap.containsKey(id)){
            return null;
        }
        return this.nodeMap.get(id);
    }

    public void addEdge(Node origin, Node dest, int weight){
        if (!this.list.containsKey(origin)){
            throw new KeyDoesNotExistException();
        }
        AtomicBoolean existed = new AtomicBoolean(false);
        this.list.get(origin).forEach(e -> {
            if (e.getDest().equals(dest)) {
                existed.set(true);
            }
        });

        if (existed.get()){
            throw new EdgeAlreadyExistedException();
        }

        Edge e = new Edge(dest, weight);

        this.list.get(origin).add(e);
    }

    public List<Edge> getAdjacencyList(Node target){
        return this.list.get(target);
    }

    public Map<Node, List<Edge>> getAdjacencyList(){
        return this.list;
    }
}
