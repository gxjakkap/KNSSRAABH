package structure;

import modnaverrors.KeyAlreadyExistedException;
import modnaverrors.KeyDoesNotExistException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Graph extends AdjacencyList {
    private Map<String, Node> nodeMap;

    public Graph(int verticesCount){
        super(verticesCount);
        this.nodeMap = new HashMap<String, Node>();
    }

    public void addVertex(Node n){
        if (this.list.containsKey(n)){
            throw new KeyAlreadyExistedException();
        }

        this.list.put(n, new ArrayList<>());
        this.nodeMap.put(n.id, n);
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
}
