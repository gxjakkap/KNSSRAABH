package ModNav.ModNavStructure;

import ModNav.Structure.Edge;

public class Path extends Edge<Place> {
    public Path(Place dest, int weight){
        super(dest, weight);
    }

    @Override
    public Place getDest(){
        return this.dest;
    }
}
