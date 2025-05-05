package ModNav.ModNavUtils;

import ModNav.ModNavStructure.Path;
import ModNav.ModNavStructure.Place;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBMapQueryResult {
    private Map<Place, List<Path>> map;
    private Map<String, Place> placeList;

    public DBMapQueryResult(){
        this.map = new HashMap<>();
        this.placeList = new HashMap<>();
    }

    public void addRow(Place p, String paths, Map<String, Place> pm){
        List<Path> pth = JsonOperations.modNavMapParse(paths, pm);

        this.map.put(p, pth);
        this.placeList.put(p.getId(), p);
    }

    public Map<Place, List<Path>> getMap() {
        return this.map;
    }

    public Map<String, Place> getPlaceList() {
        return this.placeList;
    }
}
