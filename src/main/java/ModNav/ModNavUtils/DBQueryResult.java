package ModNav.ModNavUtils;

import ModNav.ModNavStructure.Path;
import ModNav.ModNavStructure.Place;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBQueryResult {
    private Map<Place, List<Path>> map;
    private Map<String, Place> placeList;

    public DBQueryResult(){
        this.map = new HashMap<>();
        this.placeList = new HashMap<>();
    }

    public void addRow(String pId, String paths, String names){
        Place p = new Place(pId);

        List<String> nameList = JsonOperations.modNavNameListParse(names);
        List<Path> pth = JsonOperations.modNavMapParse(paths);

        p.setNames(nameList);

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
