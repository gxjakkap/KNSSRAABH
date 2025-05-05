package ModNav.ModNavStructure;

import ModNav.Structure.Graph;
import ModNav.ModNavExceptions.KeyDoesNotExistException;
import ModNav.ModNavExceptions.EdgeAlreadyExistedException;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class ModNavGraph extends Graph<Place, Path> {
    private Map<String, ModNavSubject> subjectMap;

    public ModNavGraph(int verticesCount) {
        super(verticesCount);
        this.subjectMap = new HashMap<>();
    }

    public ModNavGraph() {
        super();
        this.subjectMap = new HashMap<>();
    }

    public Optional<Place> getPlaceById(String id) {
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

        Path originToDest = new Path(dest, distance);
        Path destToOrigin = new Path(origin, distance); // undirected graph
        this.list.get(origin).add(originToDest);
        this.list.get(dest).add(destToOrigin);
    }

    public List<Path> getPathsFromPlace(Place place) {
        List<Path> edges = super.getAdjacencyList(place);
        return new ArrayList<>(edges);
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

    public Optional<Place> findPlaceWithName(String name){
        StringBuilder sb = new StringBuilder();
        AtomicBoolean found = new AtomicBoolean(false);

        for (Place p : this.nodeMap.values()){
            for (String n : p.getNames()){
                if (n.equalsIgnoreCase(name)){
                    sb.append(p.getId());
                    found.set(true);
                    break;
                }
            }
            if (found.get()) break;
        }

        String id = sb.toString();

        return this.getPlaceById(id);
    }

    public void setPlaceMap(Map<String, Place> mp){
        this.nodeMap = mp;
    }

    public void setList(Map<Place, List<Path>> l){
        this.list = l;
    }

    public Map<String, Place> getPlaceMap(){
        return Collections.unmodifiableMap(this.nodeMap);
    }

    public Optional<Place> getPlaceFromSubjectID(String sub){
        if (!subjectMap.containsKey(sub)){
            return Optional.empty();
        }

        return Optional.ofNullable(subjectMap.get(sub).getBuilding());
    }

    public void setSubjectMap(Map<String, ModNavSubject> sm){
        this.subjectMap = sm;
    }

    public Map<String, ModNavSubject> getSubjectMap(){
        return Collections.unmodifiableMap(this.subjectMap);
    }

    public Optional<ModNavSubject> getSubjectData(String sub){
        if (!this.subjectMap.containsKey(sub.toUpperCase())){
            return Optional.empty();
        }

        return Optional.ofNullable(this.subjectMap.get(sub.toUpperCase()));
    }

    public void addSubject(ModNavSubject sub){
        this.subjectMap.put(sub.getSubjectID(), sub);
    }

    public void removeSubject(String sid){
        this.subjectMap.remove(sid);
    }

    public void removePath(Place origin, Place dest){
        this.list.get(origin).forEach((pth) -> {
            if (pth.getDest().equals(dest)){
                this.list.get(origin).remove(pth);
            }
        });
    }
}
