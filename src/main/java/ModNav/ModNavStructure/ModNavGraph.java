package ModNav.ModNavStructure;

import ModNav.Structure.Graph;
import ModNav.ModNavExceptions.KeyDoesNotExistException;
import ModNav.ModNavExceptions.EdgeAlreadyExistedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class ModNavGraph extends Graph<Place, Path> {

    public ModNavGraph(int verticesCount) {
        super(verticesCount);
    }

    public ModNavGraph() {
        super();
    }

    public Place getPlaceById(String id) {
        return super.getNodeById(id);
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
        List<Path> edges = super.getAdjacencyList(place);
        List<Path> paths = new ArrayList<>();
        for (Path edge : edges) {
            paths.add((Path) edge);
        }
        return paths;
    }

    public Map<Place, List<Path>> getAllPaths() {
        Map<Place, List<Path>> pathMap = new HashMap<>();
        Map<Place, List<Path>> originalMap = super.getAdjacencyList();

        for (Map.Entry<Place, List<Path>> entry : originalMap.entrySet()) {
            Place place = entry.getKey();
            List<Path> paths = new ArrayList<>(entry.getValue());
            pathMap.put(place, paths);
        }
        return pathMap;
    }
}
