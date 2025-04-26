package ModNav.ModNavStructure;

import ModNav.Structure.Graph;

public class ModNavGraph extends Graph {
    public Place getPlaceById(String id){
        return (Place) this.getNodeById(id);
    }

    public void addPlace(Place n){
        this.addVertex(n);
    }

    public void removePlace(Place n){
        this.removeVertex(n);
    }


}
