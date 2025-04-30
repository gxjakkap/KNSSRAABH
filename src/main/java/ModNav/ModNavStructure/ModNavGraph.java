package ModNav.ModNavStructure;

import ModNav.Structure.Edge;
import ModNav.Structure.Graph;
import ModNav.ModNavExceptions.KeyDoesNotExistException;
import ModNav.ModNavExceptions.EdgeAlreadyExistedException;
import ModNav.Structure.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class ModNavGraph extends Graph {
    private Map<String, Place> placeMap;

    public ModNavGraph(int verticesCount) {
        super(verticesCount);
        this.placeMap = new HashMap<>();
    }

    public ModNavGraph() {
        super();
        this.placeMap = new HashMap<>();
    }

    public Place getPlaceById(String id) {
        return (Place) super.getNodeById(id);
    }

    public void addPlace(Place place) {
        super.addVertex(place);
    }

    public void removePlace(Place place) {
        super.removeVertex(place);
    }

    public void addPath(Place origin, Place dest, int distance) {
        if (!this.list.containsKey(origin)) {
            throw new KeyDoesNotExistException();
        }

        AtomicBoolean existed = new AtomicBoolean(false);
        this.list.get(origin).forEach(e -> {
            if (e.getDest().equals(dest)) {
                existed.set(true);
            }
        });

        if (existed.get()) {
            throw new EdgeAlreadyExistedException();
        }

        Path path = new Path(dest, distance);
        this.list.get(origin).add(path);
    }

    public List<Path> getPathsFromPlace(Place place) {
        List<Edge> edges = super.getAdjacencyList(place);
        List<Path> paths = new ArrayList<>();
        for (Edge edge : edges) {
            paths.add((Path) edge);
        }
        return paths;
    }

    public Map<Place, List<Path>> getAllPaths() {
        Map<Place, List<Path>> pathMap = new HashMap<>();
        Map<Node, List<Edge>> originalMap = super.getAdjacencyList();

        for (Map.Entry<Node, List<Edge>> entry : originalMap.entrySet()) {
            Place place = (Place) entry.getKey();
            List<Path> paths = new ArrayList<>();
            for (Edge edge : entry.getValue()) {
                paths.add((Path) edge);
            }
            pathMap.put(place, paths);
        }
        return pathMap;
    }

    public Path temp_getDirectPath(Place origin, Place dest) {
        // Implement your logic here
        return null;
    }
}
